package org.mars.config.task;

import org.quartz.*;

public class QuartzManager {

	//任务组名
	public static final String JOB_GROUP_NAME = "WEB_JOBGROUP_NAME";
	//触发器组名
	public static final String TRIGGER_GROUP_NAME = "WEB_TRIGGERGROUP_NAME";

    /**
     * @Description: 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
     *
     * @param sched
     *            调度器
     *
     * @param jobName
     *            任务名
     * @param cls
     *            任务
     * @param time
     *            时间设置，参考quartz说明文档
     */
    public static void addJob(Scheduler sched, String jobName, Class<? extends Job> jobClass, String time) {
        try {
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, JOB_GROUP_NAME).build();
            // 触发器
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, TRIGGER_GROUP_NAME).startNow().withSchedule(CronScheduleBuilder.cronSchedule(time)).build();
            sched.scheduleJob(jobDetail, trigger);
            // 启动
            QuartzManager.startJobs(sched);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description: 添加一个定时任务
     *
     * @param sched
     *            调度器
     *
     * @param jobName
     *            任务名
     * @param jobGroupName
     *            任务组名
     * @param triggerName
     *            触发器名
     * @param triggerGroupName
     *            触发器组名
     * @param jobClass
     *            任务
     * @param time
     *            时间设置，参考quartz说明文档
     */
    public static void addJob(Scheduler sched, String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class<? extends Job> jobClass, String time) {
        try {
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
            // 触发器
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroupName).startNow().withSchedule(CronScheduleBuilder.cronSchedule(time)).build();
            sched.scheduleJob(jobDetail, trigger);
            // 启动
            QuartzManager.startJobs(sched);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description: 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
     *
     * @param sched
     *            调度器
     * @param jobName
     * @param time
     */
    public static void modifyJobTime(Scheduler sched, String jobName, String time) {
        try {
            CronTrigger trigger = (CronTrigger) sched.getTrigger(TriggerKey.triggerKey(jobName, TRIGGER_GROUP_NAME));
            if (trigger == null) {
                return;
            }
            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(time)) {
                trigger.getTriggerBuilder().withIdentity(jobName, TRIGGER_GROUP_NAME).startNow().withSchedule(CronScheduleBuilder.cronSchedule(time)).build();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description: 修改一个任务的触发时间
     *
     * @param sched
     *            调度器
     * @param triggerName
     * @param triggerGroupName
     * @param time
     */
    public static void modifyJobTime(Scheduler sched, String triggerName, String triggerGroupName, String time) {
        try {
            CronTrigger trigger = (CronTrigger) sched.getTrigger(TriggerKey.triggerKey(triggerName, triggerGroupName));
            if (trigger == null) {
                return;
            }
            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(time)) {
            	trigger.getTriggerBuilder().withIdentity(triggerName, triggerGroupName).startNow().withSchedule(CronScheduleBuilder.cronSchedule(time)).build();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description: 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
     *
     * @param sched
     *            调度器
     * @param jobName
     */
    public static void removeJob(Scheduler sched, String jobName) {
        try {
        	sched.pauseJob(JobKey.jobKey(jobName, JOB_GROUP_NAME));// 停止任务
            sched.pauseTrigger(TriggerKey.triggerKey(jobName, TRIGGER_GROUP_NAME));// 停止触发器
            sched.unscheduleJob(TriggerKey.triggerKey(jobName, TRIGGER_GROUP_NAME));// 移除触发器
            sched.deleteJob(JobKey.jobKey(jobName, JOB_GROUP_NAME));// 删除任务
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description: 移除一个任务
     *
     * @param sched
     *            调度器
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     */
    public static void removeJob(Scheduler sched, String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
        try {
        	sched.pauseJob(JobKey.jobKey(jobName, jobGroupName));// 停止任务
            sched.pauseTrigger(TriggerKey.triggerKey(triggerName, triggerGroupName));// 停止触发器
            sched.unscheduleJob(TriggerKey.triggerKey(triggerName, triggerGroupName));// 移除触发器
            sched.deleteJob(JobKey.jobKey(jobName, jobGroupName));// 删除任务
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description:启动所有定时任务
     *
     * @param sched
     *            调度器
     */
    public static void startJobs(Scheduler sched) {
        try {
        	if(!sched.isStarted()) {
                sched.start();
        	}
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description:关闭所有定时任务
     *
     * @param sched
     *            调度器
     */
    public static void shutdownJobs(Scheduler sched) {
        try {
            if (!sched.isShutdown()) {
                sched.shutdown(true);
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
