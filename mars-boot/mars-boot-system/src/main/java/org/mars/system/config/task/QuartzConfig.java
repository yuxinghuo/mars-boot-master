package org.mars.system.config.task;

import org.quartz.Scheduler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig implements InitializingBean {
    @Value("${taxcodeitem.down.cron:0 0 0 * * ?}")
    private String cron;
    @Autowired
    private Scheduler scheduler;
    @Value("${taxcodeitem.down.flag:}")
    private String projectFlag;

    @Override
    public void afterPropertiesSet() throws Exception {
        //执行此定时任务
        if (!"1".equals(projectFlag)) {
            return;
        }
        //QuartzManager.addJob(scheduler, TaxCodeItemDownQuartz.class.getSimpleName(), TaxCodeItemDownQuartz.class, cron);
    }
}
