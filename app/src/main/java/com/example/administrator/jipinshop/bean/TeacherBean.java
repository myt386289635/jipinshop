package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2020/4/24
 * @Describe
 */
public class TeacherBean {

    private String msg;
    private int code;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * avatar : http://jipincheng.cn/avatar/img/20190820/a35de78ad0b143e7b7d5ba0b1c70728a
         * wechat : null
         */

        private String avatar;
        private String wechat;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getWechat() {
            return wechat;
        }

        public void setWechat(String wechat) {
            this.wechat = wechat;
        }
    }
}
