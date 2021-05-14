package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/10/10
 * @Describe 我的订单（淘宝订单）
 */
public class OrderTBBean {

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
         * tradeId : 932581440960129491
         * payPrice : 0.0
         * preFee : 3.92
         * fee : 0.0
         * itemTitle : 免洗懒人抹布干湿两用厨房一次性洗碗布加厚家用无纺布可水洗纸巾
         * itemImg : https://img.alicdn.com/tfscom/i3/2206328805449/O1CN01hnRG3w1q7gkCcpDLp_!!2206328805449.png
         * tkPaidTime : 2020-04-08 15:13:14
         * appEarningTime : null
         */

        private int source;
        private int status;
        private String tradeId;
        private String payPrice;
        private String preFee;
        private String fee;
        private String itemTitle;
        private String itemImg;
        private String itemNum;
        private String tkPaidTime;
        private String appEarningTime;
        private int type;
        private int level;
        private String isgroup;//0不拼团，1拼团
        private String otherGoodsId;

        public String getOtherGoodsId() {
            return otherGoodsId;
        }

        public void setOtherGoodsId(String otherGoodsId) {
            this.otherGoodsId = otherGoodsId;
        }

        public String getIsgroup() {
            return isgroup;
        }

        public void setIsgroup(String isgroup) {
            this.isgroup = isgroup;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getItemNum() {
            return itemNum;
        }

        public void setItemNum(String itemNum) {
            this.itemNum = itemNum;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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
