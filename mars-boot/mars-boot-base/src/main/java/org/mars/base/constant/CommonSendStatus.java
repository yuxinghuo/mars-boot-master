package org.mars.base.constant;

/**
 * @Author: Mars
 * @Description: 系统通告 - 发布状态
 * @Date: 2022年4月23日
 */
public interface CommonSendStatus {

    String UNPUBLISHED_STATUS_0 = "0";    //未发布

    String PUBLISHED_STATUS_1 = "1";        //已发布

    String REVOKE_STATUS_2 = "2";            //撤销


    /**
     * 流程催办——系统通知消息模板
     */
    String TZMB_BPM_CUIBAN = "bpm_cuiban";
    /**
     * 标准模板—系统消息通知
     */
    String TZMB_SYS_TS_NOTE = "sys_ts_note";
    /**
     * 流程超时提醒——系统通知消息模板
     */
    String TZMB_BPM_CHAOSHI_TIP = "bpm_chaoshi_tip";
}
