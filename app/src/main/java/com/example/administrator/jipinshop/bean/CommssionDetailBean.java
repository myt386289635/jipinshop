package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2020/6/16
 * @Describe
 */
public class CommssionDetailBean {

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

        private int year;
        private int month;
        private int day;
        private String preFee;
        private String orderCount;
        private String tbSelfPreFee;
        private String tbSelfOrderCount;
        private String jdSelfPreFee;
        private String jdSelfOrderCount;
        private String pddSelfPreFee;
        private String pddSelfOrderCount;
        private String tbSubPreFee;
        private String tbSubOrderCount;
        private String jdSubPreFee;
        private String jdSubOrderCount;
        private String pddSubPreFee;
        private String pddSubOrderCount;

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public String getPreFee() {
            return preFee;
        }

        public void setPreFee(String preFee) {
            this.preFee = preFee;
        }

        public String getOrderCount() {
            return orderCount;
        }

        public void setOrderCount(String orderCount) {
            this.orderCount = orderCount;
        }

        public String getTbSelfPreFee() {
            return tbSelfPreFee;
        }

        public void setTbSelfPreFee(String tbSelfPreFee) {
            this.tbSelfPreFee = tbSelfPreFee;
        }

        public String getTbSelfOrderCount() {
            return tbSelfOrderCount;
        }

        public void setTbSelfOrderCount(String tbSelfOrderCount) {
            this.tbSelfOrderCount = tbSelfOrderCount;
        }

        public String getJdSelfPreFee() {
            return jdSelfPreFee;
        }

        public void setJdSelfPreFee(String jdSelfPreFee) {
            this.jdSelfPreFee = jdSelfPreFee;
        }

        public String getJdSelfOrderCount() {
            return jdSelfOrderCount;
        }

        public void setJdSelfOrderCount(String jdSelfOrderCount) {
            this.jdSelfOrderCount = jdSelfOrderCount;
        }

        public String getPddSelfPreFee() {
            return pddSelfPreFee;
        }

        public void setPddSelfPreFee(String pddSelfPreFee) {
            this.pddSelfPreFee = pddSelfPreFee;
        }

        public String getPddSelfOrderCount() {
            return pddSelfOrderCount;
        }

        public void setPddSelfOrderCount(String pddSelfOrderCount) {
            this.pddSelfOrderCount = pddSelfOrderCount;
        }

        public String getTbSubPreFee() {
            return tbSubPreFee;
        }

        public void setTbSubPreFee(String tbSubPreFee) {
            this.tbSubPreFee = tbSubPreFee;
        }

        public String getTbSubOrderCount() {
            return tbSubOrderCount;
        }

        public void setTbSubOrderCount(String tbSubOrderCount) {
            this.tbSubOrderCount = tbSubOrderCount;
        }

        public String getJdSubPreFee() {
            return jdSubPreFee;
        }

        public void setJdSubPreFee(String jdSubPreFee) {
            this.jdSubPreFee = jdSubPreFee;
        }

        public String getJdSubOrderCount() {
            return jdSubOrderCount;
        }

        public void setJdSubOrderCount(String jdSubOrderCount) {
            this.jdSubOrderCount = jdSubOrderCount;
        }

        public String getPddSubPreFee() {
            return pddSubPreFee;
        }

        public void setPddSubPreFee(String pddSubPreFee) {
            this.pddSubPreFee = pddSubPreFee;
        }

        public String getPddSubOrderCount() {
            return pddSubOrderCount;
        }

        public void setPddSubOrderCount(String pddSubOrderCount) {
            this.pddSubOrderCount = pddSubOrderCount;
        }
    }
}
