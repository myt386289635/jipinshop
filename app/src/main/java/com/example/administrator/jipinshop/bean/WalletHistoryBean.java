package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/6/16
 * @Describe
 */
public class WalletHistoryBean {

    private String msg;
    private int code;
    private int month;
    private int year;
    private int day;
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

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * year : 2020
         * month : 6
         * day : 3
         * preFee : 1.08
         * orderCount : 1
         * selfPreFee : 0
         * selfOrderCount : 0
         * subPreFee : 1.08
         * subOrderCount : 1
         */

        private int year;
        private int month;
        private int day;
        private String preFee;
        private String orderCount;
        private String selfPreFee;
        private String selfOrderCount;
        private String subPreFee;
        private String subOrderCount;

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

        public String getSelfPreFee() {
            return selfPreFee;
        }

        public void setSelfPreFee(String selfPreFee) {
            this.selfPreFee = selfPreFee;
        }

        public String getSelfOrderCount() {
            return selfOrderCount;
        }

        public void setSelfOrderCount(String selfOrderCount) {
            this.selfOrderCount = selfOrderCount;
        }

        public String getSubPreFee() {
            return subPreFee;
        }

        public void setSubPreFee(String subPreFee) {
            this.subPreFee = subPreFee;
        }

        public String getSubOrderCount() {
            return subOrderCount;
        }

        public void setSubOrderCount(String subOrderCount) {
            this.subOrderCount = subOrderCount;
        }
    }
}
