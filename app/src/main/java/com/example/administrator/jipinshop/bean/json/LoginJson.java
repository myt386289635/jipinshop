package com.example.administrator.jipinshop.bean.json;

/**
 * @author 莫小婷
 * @create 2018/9/27
 * @Describe
 */
public class LoginJson {

    private String mobile;
    private String code;

    public LoginJson(String mobile, String code) {
        this.mobile = mobile;
        this.code = code;
    }
}
