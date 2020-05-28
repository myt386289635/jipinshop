package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2020/5/27
 * @Describe
 */
public class WithdrawInfoBean {


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
         * currentMoney : 0.0
         * alipayNickname :
         * realname :
         */

        private String currentMoney;
        private String alipayNickname;
        private String realname;

        public String getCurrentMoney() {
            return currentMoney;
        }

        public void setCurrentMoney(String currentMoney) {
            this.currentMoney = currentMoney;
        }

        public String getAlipayNickname() {
            return alipayNickname;
        }

        public void setAlipayNickname(String alipayNickname) {
            this.alipayNickname = alipayNickname;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }
    }
}
