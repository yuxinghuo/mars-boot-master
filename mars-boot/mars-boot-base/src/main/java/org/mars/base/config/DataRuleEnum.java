package org.mars.base.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 数据权限规则
 * 
 * @author 薛超
 * @since 2021年3月9日
 * @version 1.0.0
 */
@RequiredArgsConstructor
@Getter
public enum DataRuleEnum {

//    /**
//     * 分库标识；当前部门
//     */
//    SHARDING_CODE_EQ((params) -> {
//        String shardingCode = getParam(String.class, params);
//        if (StrUtil.isBlank(shardingCode)) {
//            return null;
//        }
//        // 构造条件
//        return MybatisUtil.buildSql(Wrappers.<TInvoicePrepare>lambdaQuery()//
//                .eq(TInvoicePrepare::getShardingCode, shardingCode), TInvoicePrepare.class);
//    }, false),
//
//    /**
//     * 分库标识；当前部门及下级部门
//     */
//    SHARDING_CODE_LIKE((params) -> {
//        String shardingCode = getParam(String.class, params);
//        if (StrUtil.isBlank(shardingCode)) {
//            return null;
//        }
//        // 构造条件
//        return MybatisUtil.buildSql(Wrappers.<TInvoicePrepare>lambdaQuery()//
//                .likeRight(TInvoicePrepare::getShardingCode, shardingCode), TInvoicePrepare.class);
//    }, false),
//    /**
//     * 纳税人识别号
//     */
//    SELLER_TAX_ID((params) -> {
//        List<TaxpayerOrganiseModel> list = getListParam(TaxpayerOrganiseModel.class, params);
//        if (CollUtil.isEmpty(list)) {
//            return null;
//        }
//        // 获取税号
//        List<String> taxIds = list.stream().map(TaxpayerOrganiseModel::getTaxpayerId).distinct()
//                .collect(Collectors.toList());
//        // 生成sql
//        return generateSql(TInvoicePrepare::getSellerTaxId, taxIds);
//    }, true),
//    /**
//     * 发票类型代码
//     **/
//    INVOICE_TYPE_CODE((params) -> {
//        List<TaxpayerOrganiseModel> list = getListParam(TaxpayerOrganiseModel.class, params);
//        if (CollUtil.isEmpty(list)) {
//            return null;
//        }
//        // 获取类型代码
//        List<String> typeCodes = list.stream().map(TaxpayerOrganiseModel::getInvoicetypeCode).distinct()
//                .collect(Collectors.toList());
//        // 生成sql
//        return generateSql(TInvoicePrepare::getInvoiceTypeCode, typeCodes);
//    }, true),
//    /**
//     * 纳税人识别号 和 发票类型代码
//     */
//    SELLER_TAX_ID_AND_INVOICE_TYPE_CODE((params) -> {
//        List<TaxpayerOrganiseModel> list = getListParam(TaxpayerOrganiseModel.class, params);
//        if (CollUtil.isEmpty(list)) {
//            return null;
//        }
//        // 构造条件
//        LambdaQueryWrapper<TInvoicePrepare> w = Wrappers.<TInvoicePrepare>lambdaQuery();
//        list.forEach(l -> {
//            w.or(q -> q.eq(TInvoicePrepare::getSellerTaxId, l.getTaxpayerId())//
//                    .eq(TInvoicePrepare::getInvoiceTypeCode, l.getInvoicetypeCode()));
//        });
//        // 生成sql
//        return MybatisUtil.buildSql(w, TInvoicePrepare.class);
//    }, true);
//
//    private final Func<Object, String> sqlFunc;// 生成sql片段
//    private final boolean needOrgTaxId;// 是否需要机构税号
//
//    /**
//     * 匹配数据权限
//     *
//     * @param ruleSql 规则sql
//     * @return {@link DataRuleEnum}
//     */
//    public static DataRuleEnum matcher(String ruleSql) {
//        DataRuleEnum r = null;
//        DataRuleEnum[] ruleEnums = DataRuleEnum.values();
//        for (DataRuleEnum du : ruleEnums) {
//            if (StrUtil.equalsIgnoreCase(ruleSql, du.name())) {
//                r = du;
//                break;
//            }
//        }
//        return r;
//    }
//
//    /**
//     * in 查询-构造sql
//     *
//     * @param list 机构税号配置
//     * @return sql片段
//     */
//    public String buildSql(Object... val) {
//        try {
//            return this.sqlFunc.call(val);
//        } catch (Exception e) {
//            throw ExceptionUtil.wrapRuntime(e);
//        }
//    }
//
//    private static <T> String generateSql(SFunction<T, ?> func, List<String> vals) {
//        // 加载实体类
//        Class<T> beanType = FunctionUtil.getBeanClass(func);
//        // 构造条件
//        LambdaQueryWrapper<T> w = Wrappers.<T>lambdaQuery().in(func, vals);
//        return MybatisUtil.buildSql(w, beanType);
//    }
//
//    @SuppressWarnings("unchecked")
//    private static <T> T getParam(Class<T> paramType, Object... params) {
//        if (ArrayUtil.isEmpty(params)) {
//            return null;
//        }
//        for (Object p : params) {
//            if (ClassUtil.isAssignable(paramType, p.getClass())) {
//                return (T) p;
//            }
//        }
//        return null;
//    }
//
//    @SuppressWarnings("unchecked")
//    private static <T> List<T> getListParam(Class<T> paramType, Object... params) {
//        if (ArrayUtil.isEmpty(params)) {
//            return null;
//        }
//        for (Object p : params) {
//            if (p instanceof List<?>) {
//                if (ClassUtil.isAssignable(paramType, CollUtil.getElementType((List<?>) p))) {
//                    return (List<T>) p;
//                }
//            }
//        }
//        return null;
//    }

}