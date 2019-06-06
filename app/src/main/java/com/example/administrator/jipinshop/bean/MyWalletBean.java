package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2019/6/6
 * @Describe
 */
public class MyWalletBean {

    /**
     * msg : success
     * code : 0
     * data : {"preMonthFee":0,"currentMonthFee":0,"withdraw":0,"balanceFee":2}
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
         * preMonthFee : 0
         * currentMonthFee : 0
         * withdraw : 0
         * balanceFee : 2
         */

        private String preMonthFee;
        private String currentMonthFee;
        private String withdraw;
        private String balanceFee;

        public String getPreMonthFee() {
            return preMonthFee;
        }

        public void setPreMonthFee(String preMonthFee) {
            this.preMonthFee = preMonthFee;
        }

        public String getCurrentMonthFee() {
            return currentMonthFee;
        }

        public void setCurrentMonthFee(String currentMonthFee) {
            this.currentMonthFee = currentMonthFee;
        }

        public String getWithdraw() {
            return withdraw;
        }

        public void setWithdraw(String withdraw) {
            this.withdraw = withdraw;
        }

        public String getBalanceFee() {
            return balanceFee;
        }

        public void setBalanceFee(String balanceFee) {
            this.balanceFee = balanceFee;
        }
    }
}
