package org.mars.config;

import java.util.Properties;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.incrementer.OracleKeyGenerator;

/**
 * 单数据源配置（jeecg.datasource.open = false时生效）
 *
 * @Author zhoujf
 */
@Configuration
@MapperScan(value = { "org.mars.**.mapper*" })
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
//    @Bean
//    public PaginationInterceptor paginationInterceptor() {
//        // 设置sql的limit为无限制，默认是500
//        return new PaginationInterceptor().setLimit(-1);
//    }
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 设置sql的limit为无限制，默认是500
        PaginationInnerInterceptor pageInterceptor = new PaginationInnerInterceptor();
        pageInterceptor.setMaxLimit(-1L);
        interceptor.addInnerInterceptor(pageInterceptor);
        return interceptor;
    }

//    /**
//     * mybatis-plus SQL执行效率插件【生产环境可以关闭】
//     */
//    @Bean
//    public PerformanceInterceptor performanceInterceptor() {
//        return new PerformanceInterceptor();
//    }

    @Bean
    @ConditionalOnProperty(prefix = Constants.MYBATIS_PLUS, name = "db-type", havingValue = "oracle")
    public IKeyGenerator keyGenerator() {
        return new OracleKeyGenerator();
    }

    /**
     * 数据库兼容,在mybatis里设置相应的databaseId即可
     */
    @Bean
    public DatabaseIdProvider databaseIdProvider() {
        DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
        Properties properties = new Properties();
        properties.setProperty("Oracle", "oracle");
        properties.setProperty("MySQL", "mysql");
        databaseIdProvider.setProperties(properties);
        return databaseIdProvider;
    }
}
