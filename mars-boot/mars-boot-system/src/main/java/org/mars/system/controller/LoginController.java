package org.mars.system.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.mars.base.constant.CacheConstant;
import org.mars.base.constant.CommonConstant;
import org.mars.base.system.vo.LoginUser;
import org.mars.base.model.Result;
import org.mars.base.system.api.ISysBaseAPI;
import org.mars.base.util.*;
import org.mars.shiro.vo.DefContants;
import org.mars.system.config.GlobalConfig;
import org.mars.system.entity.SysDepart;
import org.mars.system.entity.SysUser;
import org.mars.system.model.SysLoginModel;
import org.mars.system.service.ISysDepartService;
import org.mars.system.service.ISysDictService;
import org.mars.system.service.ISysLogService;
import org.mars.system.service.ISysUserService;
import org.mars.system.util.RandImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 用户登录
 *
 * @Author hyx
 * @since 2022-4-21
 */
@RestController
@RequestMapping("/sys")
@Slf4j
public class LoginController {

    @Value("${mars.version:1.0.0}")
    private String version;

    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysBaseAPI sysBaseAPI;
    @Autowired
    private ISysLogService logService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ISysDepartService sysDepartService;
    @Autowired
    private ISysDictService sysDictService;
    @Autowired
    private GlobalConfig globalConfig;

    private static final String BASE_CHECK_CODES = "qwertyuiplkjhgfdsazxcvbnmQWERTYUPLKJHGFDSAZXCVBNM1234567890";

    /**
     * 登录接口
     *
     * @param sysLoginModel
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result<JSONObject> login(@RequestBody SysLoginModel sysLoginModel) {
        Result<JSONObject> result = new Result<>();
        String username = sysLoginModel.getUsername();
        String password = sysLoginModel.getPassword();

        //update-begin-author:taoyan date:20190828 for:校验验证码
        String realKey = StrUtil.NULL;
        if (!globalConfig.isIgnoreCaptcha()) {//是否忽略验证码校验
            String captcha = sysLoginModel.getCaptcha();
            if (captcha == null) {
                result.error500("验证码无效");
                return result;
            }
            String lowerCaseCaptcha = captcha.toLowerCase();
            realKey = MD5Util.MD5Encode(lowerCaseCaptcha + sysLoginModel.getCheckKey(), "utf-8");
            Object checkCode = redisUtil.get(realKey);
            if (checkCode == null || !checkCode.equals(lowerCaseCaptcha)) {
                result.error500("验证码错误");
                return result;
            }
        }
        //update-end-author:taoyan date:20190828 for:校验验证码
        //1. 校验用户是否有效
        SysUser sysUser = sysUserService.getUserByName(username);
        result = sysUserService.checkUserIsEffective(sysUser);
        if (!result.isSuccess()) {
            //result.error500("用户名或密码错误");
            //清除验证码缓存
            redisUtil.del(realKey);
            return result;
        }

        //2. 校验用户名或密码是否正确
        String userpassword = PasswordUtil.encrypt(username, password, sysUser.getSalt());
        String syspassword = sysUser.getPassword();

        if (!syspassword.equals(userpassword)) {
            // 设置登录错误次数
            Integer count = ObjectUtil.defaultIfNull(sysUser.getPasswordErrorCount(), 0) + 1;
            // 超过最大错误次数冻结之
            Integer status = null;
            if (count >= globalConfig.getLoginRetryMaxCount()) {
                count = 0;
                status = CommonConstant.USER_FREEZE;
            }
            sysUserService.update(Wrappers.<SysUser>lambdaUpdate()//
                    .set(status != null, SysUser::getStatus, status)//
                    .set(SysUser::getPasswordErrorCount, count)//
                    .eq(SysUser::getId, sysUser.getId()));
            //清除验证码缓存
            redisUtil.del(realKey);
            result.error500("用户名或密码错误");
            return result;
        }
        //判断密码是否过期
        if ((sysUser.getPasswordEffectiveDays() != null) && (sysUser.getPasswordUpdateTime() != null)) {
            Date nowDate = new Date();
            Long days = DateUtil.betweenDay(sysUser.getPasswordUpdateTime(), nowDate, true);
            if (days > sysUser.getPasswordEffectiveDays()) {
                sysUser.setPasswordEffective(false);
            } else {
                sysUser.setPasswordEffective(true);
            }

        }
        //用户登录信息
        userInfo(sysUser, result);
        sysBaseAPI.addLog("用户名: " + username + ",登录成功！", CommonConstant.LOG_TYPE_1, null);
        return result;
    }

    /**
     * 退出登录
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/logout")
    public Result<Object> logout(HttpServletRequest request, HttpServletResponse response) {
        //用户退出逻辑
        String token = request.getHeader(DefContants.X_ACCESS_TOKEN);
        if (StrUtil.isEmpty(token)) {
            return Result.error("退出登录失败！");
        }
        String username = JwtUtil.getUsername(token);
        LoginUser sysUser = sysBaseAPI.getUserByName(username);
        if (sysUser != null) {
            sysBaseAPI.addLog("用户名: " + sysUser.getRealname() + ",退出成功！", CommonConstant.LOG_TYPE_1, null);
            log.info(" 用户名:  " + sysUser.getRealname() + ",退出成功！ ");
            //清空用户登录Token缓存
            redisUtil.del(CommonConstant.PREFIX_USER_TOKEN + token);
            //清空用户登录Shiro权限缓存
            redisUtil.del(CommonConstant.PREFIX_USER_SHIRO_CACHE + sysUser.getId());
            //清空用户的缓存信息（包括部门信息），例如sys:cache:user::<username>
            redisUtil.del(String.format("%s::%s", CacheConstant.SYS_USERS_CACHE, sysUser.getUsername()));
            //调用shiro的logout
            SecurityUtils.getSubject().logout();
            return Result.ok("退出登录成功！");
        } else {
            return Result.error("Token无效!");
        }
    }

    /**
     * 获取访问量
     *
     * @return
     */
    @GetMapping("loginfo")
    public Result<JSONObject> loginfo() {
        Result<JSONObject> result = new Result<JSONObject>();
        JSONObject obj = new JSONObject();
        //update-begin--Author:zhangweijian  Date:20190428 for：传入开始时间，结束时间参数
        // 获取一天的开始和结束时间
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date dayStart = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date dayEnd = calendar.getTime();
        // 获取系统访问记录
        Long totalVisitCount = logService.findTotalVisitCount();
        obj.put("totalVisitCount", totalVisitCount);
        Long todayVisitCount = logService.findTodayVisitCount(dayStart, dayEnd);
        obj.put("todayVisitCount", todayVisitCount);
        Long todayIp = logService.findTodayIp(dayStart, dayEnd);
        //update-end--Author:zhangweijian  Date:20190428 for：传入开始时间，结束时间参数
        obj.put("todayIp", todayIp);
        result.setResult(obj);
        result.success("登录成功");
        return result;
    }

    /**
     * 获取访问量
     *
     * @return
     */
    @GetMapping("visitInfo")
    public Result<List<Map<String, Object>>> visitInfo() {
        Result<List<Map<String, Object>>> result = new Result<List<Map<String, Object>>>();
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date dayEnd = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        Date dayStart = calendar.getTime();
        List<Map<String, Object>> list = logService.findVisitCount(dayStart, dayEnd);
        result.setResult(oConvertUtils.toLowerCasePageList(list));
        return result;
    }
    /**
     * 登陆成功选择用户当前部门
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/selectDepart", method = RequestMethod.PUT)
    public Result<JSONObject> selectDepart(@RequestBody SysUser user) {
        Result<JSONObject> result = new Result<JSONObject>();
        String username = user.getUsername();
        if (oConvertUtils.isEmpty(username)) {
            LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            username = sysUser.getUsername();
        }
        String orgCode = user.getOrgCode();
        this.sysUserService.updateUserDepart(username, orgCode);
        SysUser sysUser = sysUserService.getUserByName(username);
        JSONObject obj = new JSONObject();
        obj.put("userInfo", sysUser);
        result.setResult(obj);
        return result;
    }

    /**
     * 用户信息
     *
     * @param sysUser
     * @param result
     * @return
     */
    private Result<JSONObject> userInfo(SysUser sysUser, Result<JSONObject> result) {
        String syspassword = sysUser.getPassword();
        String username = sysUser.getUsername();
        // 生成token
        String token = JwtUtil.sign(username, syspassword);
        String userToken = String.valueOf(redisUtil.get(CommonConstant.PREFIX_USER_TOKEN + username));
        if (StrUtil.isNotBlank(userToken)) {
            //1.删除旧的token
            redisUtil.del(CommonConstant.PREFIX_USER_TOKEN + userToken);
            //2.删除旧的shiro缓存
        }
        // 设置token缓存有效时间
        redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
        redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME * 2 / 1000);
        //绑定最新用户
        TokenUtils.bindUserToken(username, token, redisUtil);
        // 获取用户部门信息
        JSONObject obj = new JSONObject();
        List<SysDepart> departs = sysDepartService.queryUserDeparts(sysUser.getId());
        obj.put("departs", departs);
        if (departs == null || departs.size() == 0) {
            obj.put("multi_depart", 0);
            sysUserService.updateUserDepart(username, "");
        } else if (departs.size() == 1) {
            sysUserService.updateUserDepart(username, departs.get(0).getOrgCode());
            obj.put("multi_depart", 1);
        } else {
            obj.put("multi_depart", 2);
        }
        obj.put("token", token);
        obj.put("userInfo", sysUser);
        obj.put("sysAllDictItems", sysDictService.queryAllDictItems());
        result.setResult(obj);
        result.success("登录成功");
        return result;
    }


    /**
     * 后台生成图形验证码 ：有效
     * 获取验证码
     *
     * @param response
     * @param key
     */
    @GetMapping(value = "/randomImage/{key}")
    public Result<String> randomImage(HttpServletResponse response, @PathVariable String key) {
        Result<String> res = new Result<String>();
        try {
            String code = RandomUtil.randomString(BASE_CHECK_CODES, 4);
            String lowerCaseCode = code.toLowerCase();
            String realKey = MD5Util.MD5Encode(lowerCaseCode + key, "utf-8");
            redisUtil.set(realKey, lowerCaseCode, 60);
            String base64 = RandImageUtil.generate(code);
            res.setSuccess(true);
            res.setResult(base64);
        } catch (Exception e) {
            res.error500("获取验证码出错" + e.getMessage());
            e.printStackTrace();
        }
        return res;
    }


    /**
     * 图形验证码
     *
     * @param sysLoginModel
     * @return
     */
    @RequestMapping(value = "/checkCaptcha", method = RequestMethod.POST)
    public Result<?> checkCaptcha(@RequestBody SysLoginModel sysLoginModel) {
        String captcha = sysLoginModel.getCaptcha();
        String checkKey = sysLoginModel.getCheckKey();
        if (captcha == null) {
            return Result.error("验证码无效");
        }
        String lowerCaseCaptcha = captcha.toLowerCase();
        String realKey = MD5Util.MD5Encode(lowerCaseCaptcha + checkKey, "utf-8");
        Object checkCode = redisUtil.get(realKey);
        if (checkCode == null || !checkCode.equals(lowerCaseCaptcha)) {
            return Result.error("验证码错误");
        }
        return Result.ok();
    }


    /**
     * 版本号
     */
    @GetMapping(value = "/version")
    public Result<?> version() {
        String s = version;
        return Result.ok(s);
    }

}
