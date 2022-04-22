package org.mars.system.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

//import com.aisino.groupinvoice.baseservice.encrypt.AesEncryptTypeHandler;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.StaticLog;
import lombok.Data;

/**
 * 全局配置
 * 
 * @author 薛超
 * @since 2021年7月6日
 * @version 1.0.0
 */
@Configuration
@ConfigurationProperties("global")
@Data
public class GlobalConfig implements InitializingBean {
    /**
     * 忽略验证码
     */
    private boolean ignoreCaptcha = false;
    /**
     * 最大登录重试次数
     */
    private int loginRetryMaxCount = 5;
    /**
     * aes密钥用户数据库字段加密
     */
    private String aesKey;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (StrUtil.isBlank(aesKey)) {
            StaticLog.warn("aesKey 为空，将使用默认密钥");
            return;
        }
        //AesEncryptTypeHandler.setAesKey(aesKey);
    }

}
