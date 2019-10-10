package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/10/10
 * @Describe 我的订单（淘宝订单）
 */
public class OrderTBBean {

    /**
     * msg : success
     * code : 0
     * data : [{"tradeId":"561593295850271520","tkCreateTime":"2019-09-26 17:04:32","tkPaidTime":"2019-09-26 17:04:35","tkEarningTime":"2019-10-07 09:53:09","orderType":null,"sellerShopTitle":"苏泊尔德盛合专卖店","sellerNick":"苏泊尔德盛合专卖店","itemId":595795579390,"itemTitle":"苏泊尔电热水壶家用烧水壶不锈钢自动断电保温开水茶壶官方旗舰店","itemImg":"https://img.alicdn.com/bao/uploaded/i4/290393158/O1CN01UW2tYk1ZCPKvxXaDL_!!290393158.jpg","itemPrice":"399.00","itemNum":1,"alipayTotalPrice":"59.00","relationId":"2258500086","goodsType":1,"preFee":2.36,"fee":0,"appEarningTime":null,"status":1},{"tradeId":"557871108244711788","tkCreateTime":"2019-09-26 17:06:05","tkPaidTime":"2019-09-26 17:06:19","tkEarningTime":"2019-09-29 20:56:08","orderType":null,"sellerShopTitle":"苏泊尔德盛合专卖店","sellerNick":"苏泊尔德盛合专卖店","itemId":595795579390,"itemTitle":"苏泊尔电热水壶家用烧水壶不锈钢自动断电保温开水茶壶官方旗舰店","itemImg":"https://img.alicdn.com/bao/uploaded/i4/290393158/O1CN01UW2tYk1ZCPKvxXaDL_!!290393158.jpg","itemPrice":"399.00","itemNum":1,"alipayTotalPrice":"59.00","relationId":"2258491245","goodsType":1,"preFee":2.36,"fee":0,"appEarningTime":null,"status":1}]
     */

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
         * tradeId : 561593295850271520
         * tkCreateTime : 2019-09-26 17:04:32
         * tkPaidTime : 2019-09-26 17:04:35
         * tkEarningTime : 2019-10-07 09:53:09
         * orderType : null
         * sellerShopTitle : 苏泊尔德盛合专卖店
         * sellerNick : 苏泊尔德盛合专卖店
         * itemId : 595795579390
         * itemTitle : 苏泊尔电热水壶家用烧水壶不锈钢自动断电保温开水茶壶官方旗舰店
         * itemImg : https://img.alicdn.com/bao/uploaded/i4/290393158/O1CN01UW2tYk1ZCPKvxXaDL_!!290393158.jpg
         * itemPrice : 399.00
         * itemNum : 1
         * alipayTotalPrice : 59.00
         * relationId : 2258500086
         * goodsType : 1
         * preFee : 2.36
         * fee : 0.0
         * appEarningTime : null
         * status : 1
         */

        private String tradeId;
        private String tkCreateTime;
        private String tkPaidTime;
        private String tkEarningTime;
        private String orderType;
        private String sellerShopTitle;
        private String sellerNick;
        private long itemId;
        private String itemTitle;
        private String itemImg;
        private String itemPrice;
        private int itemNum;
        private String alipayTotalPrice;
        private String relationId;
        private int goodsType;
        private double preFee;
        private double fee;
        private String appEarningTime;
        private int status;

        public String getTradeId() {
            return tradeId;
        }

        public void setTradeId(String tradeId) {
            this.tradeId = tradeId;
        }

        public String getTkCreateTime() {
            return tkCreateTime;
        }

        public void setTkCreateTime(String tkCreateTime) {
            this.tkCreateTime = tkCreateTime;
        }

        public String getTkPaidTime() {
            return tkPaidTime;
        }

        public void setTkPaidTime(String tkPaidTime) {
            this.tkPaidTime = tkPaidTime;
        }

        public String getTkEarningTime() {
            return tkEarningTime;
        }

        public void setTkEarningTime(String tkEarningTime) {
            this.tkEarningTime = tkEarningTime;
        }

        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public String getSellerShopTitle() {
            return sellerShopTitle;
        }

        public void setSellerShopTitle(String sellerShopTitle) {
            this.sellerShopTitle = sellerShopTitle;
        }

        public String getSellerNick() {
            return sellerNick;
        }

        public void setSellerNick(String sellerNick) {
            this.sellerNick = sellerNick;
        }

        public long getItemId() {
            return itemId;
        }

        public void setItemId(long itemId) {
            this.itemId = itemId;
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

        public String getItemPrice() {
            return itemPrice;
        }

        public void setItemPrice(String itemPrice) {
            this.itemPrice = itemPrice;
        }

        public int getItemNum() {
            return itemNum;
        }

        public void setItemNum(int itemNum) {
            this.itemNum = itemNum;
        }

        public String getAlipayTotalPrice() {
            return alipayTotalPrice;
        }

        public void setAlipayTotalPrice(String alipayTotalPrice) {
            this.alipayTotalPrice = alipayTotalPrice;
        }

        public String getRelationId() {
            return relationId;
        }

        public void setRelationId(String relationId) {
            this.relationId = relationId;
        }

        public int getGoodsType() {
            return goodsType;
        }

        public void setGoodsType(int goodsType) {
            this.goodsType = goodsType;
        }

        public double getPreFee() {
            return preFee;
        }

        public void setPreFee(double preFee) {
            this.preFee = preFee;
        }

        public double getFee() {
            return fee;
        }

        public void setFee(double fee) {
            this.fee = fee;
        }

        public String getAppEarningTime() {
            return appEarningTime;
        }

        public void setAppEarningTime(String appEarningTime) {
            this.appEarningTime = appEarningTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
