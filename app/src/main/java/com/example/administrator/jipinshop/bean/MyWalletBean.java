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
     * data : {"preFee":354,"finalFee":-81.9,"withdraw":7518,"totalFee":272.1}
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
         * preFee : 354.0
         * finalFee : -81.9
         * withdraw : 7518.0
         * totalFee : 272.1
         */

        private double preFee;
        private double finalFee;
        private double withdraw;
        private double totalFee;

        private String preTodayFee;
        private String preMonthFee;
        private String currentMonthFee;
        private String balanceFee;

        public String getPreTodayFee() {
            return preTodayFee;
        }

        public void setPreTodayFee(String preTodayFee) {
            this.preTodayFee = preTodayFee;
        }

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

        public String getBalanceFee() {
            return balanceFee;
        }

        public void setBalanceFee(String balanceFee) {
            this.balanceFee = balanceFee;
        }

        public double getPreFee() {
            return preFee;
        }

        public void setPreFee(double preFee) {
            this.preFee = preFee;
        }

        public double getFinalFee() {
            return finalFee;
        }

        public void setFinalFee(double finalFee) {
            this.finalFee = finalFee;
        }

        public double getWithdraw() {
            return withdraw;
        }

        public void setWithdraw(double withdraw) {
            this.withdraw = withdraw;
        }

        public double getTotalFee() {
            return totalFee;
        }

        public void setTotalFee(double totalFee) {
            this.totalFee = totalFee;
        }
    }
}
