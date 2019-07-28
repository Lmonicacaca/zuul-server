package com.example.zuulserver.commons;

/**
 * 类名称：SaltEnum
 * 类描述：
 *
 * @version 1.0.0
 * @author: Zhugh
 * @since: 2017/5/22
 */
public enum SaltEnum {


    LOGIN_SALT("60258233b2293b33f6a9e521e482ff8d","用户登录密码-盐"),
    MD5_SALT("QSFETTUGBVNEREWR","用户md5-盐"),
    ;

    private String code;
    private String desc;

    SaltEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
