package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/6/10
 * @Describe
 */
public class FansBean {

    private String msg;
    private int code;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * subUserId : 7d67892cb02f4766aa72fd5b08b8d8d1
         * nickname : 我真的爱奇异
         * avatar : http://jipincheng.cn/avatar/img/20190412/b3c178237d624bb192a6e087039a8928
         * mobile : 18760552173
         * createTime : 2019-05-13 13:55:29
         * preFee : 10.81
         * preMonthFee : 0.0
         * subTotal : 0
         */

        private String subUserId;
        private String nickname;
        private String avatar;
        private String mobile;
        private String createTime;
        private String preFee;
        private String preMonthFee;
        private String subTotal;

        public String getSubUserId() {
            return subUserId;
        }

        public void setSubUserId(String subUserId) {
            this.subUserId = subUserId;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getPreFee() {
            return preFee;
        }

        public void setPreFee(String preFee) {
            this.preFee = preFee;
        }

        public String getPreMonthFee() {
            return preMonthFee;
        }

        public void setPreMonthFee(String preMonthFee) {
            this.preMonthFee = preMonthFee;
        }

        public String getSubTotal() {
            return subTotal;
        }

        public void setSubTotal(String subTotal) {
            this.subTotal = subTotal;
        }
    }
}
