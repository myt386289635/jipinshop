package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/7/17
 * @Describe
 */
public class TbOrderBean {

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
         * source : 2
         * status : 1
         * type : null
         * tradeId : 1071855459055664324
         * payPrice : 30.55
         * preFee : 0.37
         * fee : 0.49
         * itemTitle : 【狂欢价】优乐麦干烙蛋糕酪鸡蛋煎饼干散装整箱宿舍吃的网红零食耐吃3斤装
         * itemImg : https://img.alicdn.com/tfscom/i1/4070068451/O1CN01JQMkpz2CIbl2Lg2m9_!!4070068451-0-pixelsss.jpg
         * itemNum : 1
         * tkPaidTime : 2020-06-18 11:32:26
         * appEarningTime : null
         */

        private String source;
        private String status;
        private String type;
        private String tradeId;
        private String payPrice;
        private String preFee;
        private String fee;
        private String itemTitle;
        private String itemImg;
        private String itemNum;
        private String tkPaidTime;
        private String appEarningTime;

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
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

        public String getTradeId() {
            return tradeId;
        }

        public void setTradeId(String tradeId) {
            this.tradeId = tradeId;
        }

        public String getPayPrice() {
            return payPrice;
        }

        public void setPayPrice(String payPrice) {
            this.payPrice = payPrice;
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

        public String getItemNum() {
            return itemNum;
        }

        public void setItemNum(String itemNum) {
            this.itemNum = itemNum;
        }

        public String getTkPaidTime() {
            return tkPaidTime;
        }

        public void setTkPaidTime(String tkPaidTime) {
            this.tkPaidTime = tkPaidTime;
        }

        public String getAppEarningTime() {
            return appEarningTime;
        }

        public void setAppEarningTime(String appEarningTime) {
            this.appEarningTime = appEarningTime;
        }
    }
}
