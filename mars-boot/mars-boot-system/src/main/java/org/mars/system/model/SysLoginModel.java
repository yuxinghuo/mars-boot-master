package org.mars.system.model;

import lombok.Data;

/**
 * 登录表单
 *
 * @Author scott
 * @since 2019-01-18
 */
@Data
public class SysLoginModel {
    /**
     * 账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 验证码
     */
    private String captcha;
    /**
     * 验证码key
     */
    private String checkKey;
}