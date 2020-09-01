package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2020/9/1
 * @Describe
 */
public class WxPayBean {

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
         * appid : wxfd2e92db2568030a
         * noncestr : 54072f485cdb7897ebbcaf7525139561
         * packageValue : Sign=WXPay
         * partnerid : 1602271453
         * prepayid : wx011012348598597702e4ad7fd4b6700000
         * sign : D0D14F95934DAEB4D9F5105A40A88AC8
         * timestamp : 1598926353
         * outTradeNo : null
         */

        private String appid;
        private String noncestr;
        private String packageValue;
        private String partnerid;
        private String prepayid;
        private String sign;
        private String timestamp;
        private String outTradeNo;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPackageValue() {
            return packageValue;
        }

        public void setPackageValue(String packageValue) {
            this.packageValue = packageValue;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getOutTradeNo() {
            return outTradeNo;
        }

        public void setOutTradeNo(String outTradeNo) {
            this.outTradeNo = outTradeNo;
        }
    }
}
