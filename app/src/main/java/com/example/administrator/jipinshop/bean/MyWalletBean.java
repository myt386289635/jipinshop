package com.example.administrator.jipinshop.bean;

import java.util.List;

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
         * balanceFee : 920.2
         * preFee : 0
         * allowance : 532
         * point : 0
         * title1 : 极品会员尊享8大特权
         * title2 : 已加入58天为您节省2090元
         * img : http://jipincheng.cn/activity/img/20201020/fdb470f054f048eaa81143a3e15aef32
         * adList : [{"img":"http://jipincheng.cn/activity/img/20200422/26f00ea80b9446ff9f9cb66fc1f57fbb","type":19,"name":"新人专区","objectId":null,"color":null,"source":"0"},{"img":"http://jipincheng.cn/activity/img/20200902/26c743d375724f16a3168d11aa5d4557","type":15,"name":"极币签到","objectId":"","color":"","source":"0"},{"img":"http://jipincheng.cn/activity/img/20200924/9d5d92780413443d9a2d9a66f6a0ef92","type":36,"name":"休闲食品","objectId":"https://s.click.taobao.com/BjVWDwu","color":"","source":"2"},{"img":"http://jipincheng.cn/activity/img/20201009/c1cf4016f5e24f3288a10ba5d548863f","type":36,"name":"健康滋补","objectId":"https://s.click.taobao.com/bSSRjvu","color":"","source":"2"},{"img":"http://jipincheng.cn/activity/img/20200924/4fe7092271af40d08b7f34150fc5db3a","type":36,"name":"个护家清","objectId":"https://s.click.taobao.com/U3IWDwu","color":"","source":"2"},{"img":"http://jipincheng.cn/activity/img/20200924/a15035bd22d949ca89d86d1c7bb5c35e","type":36,"name":"日用百货","objectId":"https://s.click.taobao.com/T3nVDwu","color":"","source":"2"},{"img":"http://jipincheng.cn/activity/img/20200916/0531bebcf59246899a5a055b2d97cd20","type":36,"name":"好货9.9元起","objectId":"https://s.click.taobao.com/vkctUwu","color":"","source":"2"},{"img":"http://jipincheng.cn/activity/img/20200810/95278c4ef1324f4d898438b11357b95a","type":13,"name":"外卖红包","objectId":"http://share.jipincheng.cn/share/ele.html?back=1","color":null,"source":"2"}]
         */

        private String balanceFee; //余额
        private String preFee; //待结算金额
        private String allowance;
        private String point;
        private String title1;
        private String title2;
        private String img;
        private List<AdListBean> adList;
        private String finalFee;
        private String withdraw;
        private String totalFee;
        private String preTodayFee;
        private String preMonthFee;
        private String currentMonthFee;
        private TodayCommissionViewBean todayCommissionView;
        private MonthCommissionViewBean monthCommissionView;

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

        public String getAllowance() {
            return allowance;
        }

        public void setAllowance(String allowance) {
            this.allowance = allowance;
        }

        public String getPoint() {
            return point;
        }

        public void setPoint(String point) {
            this.point = point;
        }

        public String getTitle1() {
            return title1;
        }

        public void setTitle1(String title1) {
            this.title1 = title1;
        }

        public String getTitle2() {
            return title2;
        }

        public void setTitle2(String title2) {
            this.title2 = title2;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public List<AdListBean> getAdList() {
            return adList;
        }

        public void setAdList(List<AdListBean> adList) {
            this.adList = adList;
        }

        public static class AdListBean {

            private String id;
            private String iconUrl;
            private String title;
            private String subTitle;
            private String orderNum;
            private String status;
            private String type;
            private String targetId;
            private String source;
            private String categoryId;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getIconUrl() {
                return iconUrl;
            }

            public void setIconUrl(String iconUrl) {
                this.iconUrl = iconUrl;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSubTitle() {
                return subTitle;
            }

            public void setSubTitle(String subTitle) {
                this.subTitle = subTitle;
            }

            public String getOrderNum() {
                return orderNum;
            }

            public void setOrderNum(String orderNum) {
                this.orderNum = orderNum;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getTargetId() {
                return targetId;
            }

            public void setTargetId(String targetId) {
                this.targetId = targetId;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(String categoryId) {
                this.categoryId = categoryId;
            }
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
