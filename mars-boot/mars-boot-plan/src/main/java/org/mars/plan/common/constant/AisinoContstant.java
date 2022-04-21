package org.mars.plan.common.constant;

/**
 * 公共常量
 *
 * @author 薛超
 * @version V1.0
 * @since 2020年3月9日
 */
public interface AisinoContstant {

    String CGLIB_FLAG = "$$EnhancerBySpringCGLIB$$";
    /**
     * 税控动态数据源-@DS 前缀<br>
     * 如:@DS(AisinoContstant.DS_DQBM_PREFIX)<br>
     * 根据用户id获取dqbm->获取相应的数据源
     */
    String DS_DQBM_PREFIX = "#dqbm";
    /**
     * 前端切换数据源的参数-优先级最高
     */
    String DS_DQBM_PARAM = "dsDqbm";

    /**
     * sql 查询只取一条
     */
    String LIMIT_1 = "LIMIT 1";
    /**
     * 数据权限-org_code 模糊查询
     */
    String DATA_RULE_ORG_CODE_LIKE = "org_code like '#{sys_org_code}%'";
    /**
     * 是
     */
    String Y = "Y";
    String OK = "true";
    /**
     * 否
     */
    String N = "N";
    /**
     * 否否（判断两个变量） 当前状态不可用（没有停用，不可用）
     */
    String NN = "NN";

    /**
     * 否是（判断两个变量） 当前状态可用（没有停用，可用）
     */
    String NY = "NY";

    /**
     * 省前缀截取有效位
     */
    int PROV_SUB_PREFIX = 5;
    /**
     * 省后缀补零
     */
    char PROV_SUFFIX = '0';

    /**
     * 成功 失败
     */
    String SUCCESS = "success";
    String ERROR = "error";
    /**
     * 终端维护停用状态描述
     */
    String ZTMX_STOP = "--";

    /**
     * 动态数据源名称税控名
     */
    String TAX_CONTROL_NAME = "taxcontrol";

    /**
     * 单位毫秒
     */
    int HTTP_TIMEOUT = 3000;
    /**
     * 电票普票
     */
    String DZPP = "026";
    /**
     * 电票专票
     */
    String DZZP = "028";
    /**
     * 普票卷票
     */
    String PPJP = "025";
    /**
     * 普通发票
     */
    String PPFP = "007";
    /**
     * 专用发票
     */
    String ZYFP = "004";
    /**
     * 直开标识
     */
    String ZKBZ = "0";
    /*
     * 审核通过
     */
    String SHTG = "1";
    /*
     * 审核不通过
     */
    String SHBTG = "2";
    /*
     * 审核撤销
     */
    String SHCH = "3";
    /*
     * 审核不存在
     */
    String SHBCZ = "0";
    /*
     * 正数发票
     */
    String ZSFP = "0";
    /*
     * 负数发票
     */
    String FSFP = "1";
    /*
     * 正常冲红
     */
    String ZCCH = "0";
}
