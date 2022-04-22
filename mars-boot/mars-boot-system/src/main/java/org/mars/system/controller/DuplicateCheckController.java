package org.mars.system.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.mars.base.model.Result;
import org.mars.system.mapper.SysDictMapper;
import org.mars.system.model.DuplicateCheckVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Title: DuplicateCheckAction
 * @Description: 重复校验工具
 * @Author 张代浩
 * @Date 2019-03-25
 * @Version V1.0
 */
@Slf4j
@RestController
@RequestMapping("/sys/duplicate")
public class DuplicateCheckController {

    @Autowired
    SysDictMapper sysDictMapper;

    /**
     * 校验数据是否在系统中是否存在
     * 重复校验接口
     *
     * @return
     */
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public Result<Object> doDuplicateCheck(DuplicateCheckVo duplicateCheckVo, HttpServletRequest request) {
        Long num = null;

        log.info("----duplicate check------：" + duplicateCheckVo.toString());
        if (StringUtils.isNotBlank(duplicateCheckVo.getDataId())) {
            // [2].编辑页面校验
            num = sysDictMapper.duplicateCheckCountSql(duplicateCheckVo);
        } else {
            // [1].添加页面校验
            num = sysDictMapper.duplicateCheckCountSqlNoDataId(duplicateCheckVo);
        }

        if (num == null || num == 0) {
            // 该值可用
            return Result.ok("该值可用！");
        } else {
            // 该值不可用
            log.info("该值不可用，系统中已存在！");
            return Result.error("该值不可用，系统中已存在！");
        }
    }
}
