package org.mars.plan.common.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import lombok.extern.slf4j.Slf4j;
import org.mars.base.model.Result;
import org.mars.base.system.api.ISysBaseAPI;
import org.mars.base.system.vo.DictModel;
import org.mars.base.util.SqlInjectionUtil;
import org.mars.plan.common.constant.AisinoContstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * 字典控制器
 *
 * @author 薛超
 * @version V1.0
 * @since 2020年3月10日
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/common/dict")
@DS(AisinoContstant.DS_DQBM_PREFIX)
public class DictController {

    private static final int DICT_ALL = 3;// 全部查询
    private static final int DICT_FILTER = 4;// 条件查询

    @Autowired
    private ISysBaseAPI sysBaseAPI;

    /**
     * 获取字典数据
     *
     * @param dictCode 表名,文本字段,code字段 | 举例：t_wbxt,wbxtmc,wbxtbm
     */
    @GetMapping("/getDictItems/{dictCode}")
    public Result<List<DictModel>> getDictItems(@PathVariable String dictCode) {
        log.info("dictCode={}", dictCode);
        String[] params = dictCode.split(StrUtil.COMMA);
        Assert.isTrue(params.length == DICT_ALL || params.length == DICT_FILTER, "字典Code格式不正确！");
        List<DictModel> list = Collections.emptyList();
        switch (params.length) {
            case DICT_ALL:// 全部 查询
                list = sysBaseAPI.queryTableDictItemsByCode(params[0], params[1], params[2]);
                break;
            case DICT_FILTER:
                // SQL注入校验（查询条件SQL 特殊check，此方法仅供此处使用）
                SqlInjectionUtil.specialFilterContent(params[3]);
                list = sysBaseAPI.queryFilterTableDictInfo(params[0], params[1], params[2], params[3]);
                break;
            default:
                break;
        }
        return Result.data(list);
    }

}
