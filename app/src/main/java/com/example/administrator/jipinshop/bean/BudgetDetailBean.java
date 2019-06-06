package com.example.administrator.jipinshop.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/6/6
 * @Describe
 */
public class BudgetDetailBean implements Serializable{


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

    public static class DataBean implements Serializable{


        private int year;
        private int month;
        private String totalPreFee;
        private List<CommissionDetailListBean> commissionDetailList;

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

        public String getTotalPreFee() {
            return totalPreFee;
        }

        public void setTotalPreFee(String totalPreFee) {
            this.totalPreFee = totalPreFee;
        }

        public List<CommissionDetailListBean> getCommissionDetailList() {
            return commissionDetailList;
        }

        public void setCommissionDetailList(List<CommissionDetailListBean> commissionDetailList) {
            this.commissionDetailList = commissionDetailList;
        }

        public static class CommissionDetailListBean implements Serializable{
            /**
             * id : 4
             * userId : 2
             * pid : 0
             * tradeId : 4
             * preFee : 200
             * fee : 4
             * type : 4
             * itemTitle : 4
             * itemImg : http://jipincheng.cn/4074f0cb11c940aaa3f2ff90bb28c01f
             * tkStatus : 13
             * tkPaidTime : 2019-06-05 10:28:48.0
             * createTime : 2019-06-06 10:28:51
             */

            private String id;
            private String userId;
            private String pid;
            private String tradeId;
            private String preFee;
            private String fee;
            private int type;
            private String itemTitle;
            private String itemImg;
            private int tkStatus;
            private String tkPaidTime;
            private String createTime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getTradeId() {
                return tradeId;
            }

            public void setTradeId(String tradeId) {
                this.tradeId = tradeId;
            }

            public String getPreFee() {
                return preFee;
            }

            public void setPreFee(String preFee) {
                this.preFee = preFee;
            }

            public String getFee() {
                return fee;
            }

            public void setFee(String fee) {
                this.fee = fee;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getItemTitle() {
                return itemTitle;
            }

            public void setItemTitle(String itemTitle) {
                this.itemTitle = itemTitle;
            }

            public String getItemImg() {
                return itemImg;
            }

            public void setItemImg(String itemImg) {
                this.itemImg = itemImg;
            }

            public int getTkStatus() {
                return tkStatus;
            }

            public void setTkStatus(int tkStatus) {
                this.tkStatus = tkStatus;
            }

            public String getTkPaidTime() {
                return tkPaidTime;
            }

            public void setTkPaidTime(String tkPaidTime) {
                this.tkPaidTime = tkPaidTime;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }
    }
}
