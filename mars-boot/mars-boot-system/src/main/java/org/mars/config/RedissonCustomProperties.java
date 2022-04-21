package org.mars.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.redisson.custom-config")
public class RedissonCustomProperties {

    /**
     * 客户端间隔时间
     */
    private int pingConnectionInterval = 3000;
    /**
     * 保持连接
     */
    private boolean keepAlive = false;
    private boolean tcpNoDelay = false;

}
