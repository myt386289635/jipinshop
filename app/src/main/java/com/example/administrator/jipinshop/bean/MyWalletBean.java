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

        private String preFee;
        private String finalFee;
        private String withdraw;
        private String totalFee;

        private String preTodayFee;
        private String preMonthFee;
        private String currentMonthFee;
        private String balanceFee;

        private TodayCommissionViewBean todayCommissionView;
        private MonthCommissionViewBean monthCommissionView;

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

        public String getPreFee() {
            return preFee;
        }

        public void setPreFee(String preFee) {
            this.preFee = preFee;
        }

        public String getFinalFee() {
            return finalFee;
        }

        public void setFinalFee(String finalFee) {
            this.finalFee = finalFee;
        }

        public String getWithdraw() {
            return withdraw;
        }

        public void setWithdraw(String withdraw) {
            this.withdraw = withdraw;
        }

        public String getTotalFee() {
            return totalFee;
        }

        public void setTotalFee(String totalFee) {
            this.totalFee = totalFee;
        }

        public TodayCommissionViewBean getTodayCommissionView() {
            return todayCommissionView;
        }

        public void setTodayCommissionView(TodayCommissionViewBean todayCommissionView) {
            this.todayCommissionView = todayCommissionView;
        }

        public MonthCommissionViewBean getMonthCommissionView() {
            return monthCommissionView;
        }

        public void setMonthCommissionView(MonthCommissionViewBean monthCommissionView) {
            this.monthCommissionView = monthCommissionView;
        }

        public static class TodayCommissionViewBean {
            /**
             * preFee : 0
             * orderCount : 0
             * selfPreFee : 0
             * selfOrderCount : 0
             * subPreFee : 0
             * subOrderCount : 0
             */
            private String preFee;
            private String orderCount;
            private String selfPreFee;
            private String selfOrderCount;
            private String subPreFee;
            private String subOrderCount;

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

        public static class MonthCommissionViewBean {
            /**
             * preFee : 1.15
             * orderCount : 2
             * selfPreFee : 0
             * selfOrderCount : 0
             * subPreFee : 1.15
             * subOrderCount : 2
             */

            private String preFee;
            private String orderCount;
            private String selfPreFee;
            private String selfOrderCount;
            private String subPreFee;
            private String subOrderCount;

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
}
