package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/11/28
 * @Describe
 */
public class SimilerGoodsBean {

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

        private String img;
        private String actualPrice;
        private String couponPrice;
        private String otherGoodsId;
        private String otherName;
        private String otherPrice;
        private String shopName;
        private String volume;
        private String fee;
        private String buyPrice;
        private String buyRate;
        private String shopIcon;
        private String source;

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getShopIcon() {
            return shopIcon;
        }

        public void setShopIcon(String shopIcon) {
            this.shopIcon = shopIcon;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getActualPrice() {
            return actualPrice;
        }

        public void setActualPrice(String actualPrice) {
            this.actualPrice = actualPrice;
        }

        public String getCouponPrice() {
            return couponPrice;
        }

        public void setCouponPrice(String couponPrice) {
            this.couponPrice = couponPrice;
        }

        public String getOtherGoodsId() {
            return otherGoodsId;
        }

        public void setOtherGoodsId(String otherGoodsId) {
            this.otherGoodsId = otherGoodsId;
        }

        public String getOtherName() {
            return otherName;
        }

        public void setOtherName(String otherName) {
            this.otherName = otherName;
        }

        public String getOtherPrice() {
            return otherPrice;
        }

        public void setOtherPrice(String otherPrice) {
            this.otherPrice = otherPrice;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getBuyPrice() {
            return buyPrice;
        }

        public void setBuyPrice(String buyPrice) {
            this.buyPrice = buyPrice;
        }

        public String getBuyRate() {
            return buyRate;
        }

        public void setBuyRate(String buyRate) {
            this.buyRate = buyRate;
        }
    }
}
