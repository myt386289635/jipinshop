package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2020/6/11
 * @Describe
 */
public class SubUserBean {

    /**
     * msg : success
     * code : 0
     * data : {"level":0,"mobile":null,"nickname":"🍃 莫小婷","wechat":null,"preMonthFee":0,"todayFee":null,"subTotal":"0","levelInvitedUser":2,"lastOrderTime":null}
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
         * level : 0
         * mobile : null
         * nickname : 🍃 莫小婷
         * wechat : null
         * preMonthFee : 0.0
         * todayFee : null
         * subTotal : 0
         * levelInvitedUser : 2
         * lastOrderTime : null
         */

        private int level;
        private String mobile;
        private String nickname;
        private String wechat;
        private String preMonthFee;
        private String todayFee;
        private String subTotal;
        private String levelInvitedUser;
        private String lastOrderTime;

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getWechat() {
            return wechat;
        }

        public void setWechat(String wechat) {
            this.wechat = wechat;
        }

        public String getPreMonthFee() {
            return preMonthFee;
        }

        public void setPreMonthFee(String preMonthFee) {
            this.preMonthFee = preMonthFee;
        }

        public String getTodayFee() {
            return todayFee;
        }

        public void setTodayFee(String todayFee) {
            this.todayFee = todayFee;
        }

        public String getSubTotal() {
            return subTotal;
        }

        public void setSubTotal(String subTotal) {
            this.subTotal = subTotal;
        }

        public String getLevelInvitedUser() {
            return levelInvitedUser;
        }

        public void setLevelInvitedUser(String levelInvitedUser) {
            this.levelInvitedUser = levelInvitedUser;
        }

        public String getLastOrderTime() {
            return lastOrderTime;
        }

        public void setLastOrderTime(String lastOrderTime) {
            this.lastOrderTime = lastOrderTime;
        }
    }
}
