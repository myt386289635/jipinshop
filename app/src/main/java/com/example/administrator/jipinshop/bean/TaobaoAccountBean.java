package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2019/6/14
 * @Describe
 */
public class TaobaoAccountBean {

    /**
     * msg : success
     * code : 0
     * data : {"account":"b","realname":"b"}
     */

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
         * account : b
         * realname : b
         */

        private String account;
        private String realname;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }
    }
}
