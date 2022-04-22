package org.mars.base.extra.config;

import java.util.Collections;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * xss 过滤规则
 * 
 * @author 薛超
 * @since 2021年1月25日
 * @version 1.0.0
 */
@Data
@Configuration
@ConfigurationProperties("aisino.xss")
public class XssConfig {
    /**
     * 排除路径
     */
    private List<String> excludeUrls = Collections.emptyList();
}
