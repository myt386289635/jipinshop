package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2019/6/11
 * @Describe
 */
public class TeamBean {

    private String msg;
    private int code;
    private DataBean data;
    private TeacherBean.DataBean parentInfo;

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
         * total : 12
         * todayTotal : null
         * yesterdayTotal : 0
         * subTotal : 0
         * sub2Total : 0
         */

        private String total;
        private String todayTotal;
        private String yesterdayTotal;
        private String subTotal;
        private String sub2Total;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getTodayTotal() {
            return todayTotal;
        }

        public void setTodayTotal(String todayTotal) {
            this.todayTotal = todayTotal;
        }

        public String getYesterdayTotal() {
            return yesterdayTotal;
        }

        public void setYesterdayTotal(String yesterdayTotal) {
            this.yesterdayTotal = yesterdayTotal;
        }

        public String getSubTotal() {
            return subTotal;
        }

        public void setSubTotal(String subTotal) {
            this.subTotal = subTotal;
        }

        public String getSub2Total() {
            return sub2Total;
        }

        public void setSub2Total(String sub2Total) {
            this.sub2Total = sub2Total;
        }
    }

    public TeacherBean.DataBean getParentInfo() {
        return parentInfo;
    }

    public void setParentInfo(TeacherBean.DataBean parentInfo) {
        this.parentInfo = parentInfo;
    }
}
