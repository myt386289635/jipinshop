package com.example.administrator.jipinshop.bean;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/12/3
 * @Describe
 */
public class TBSreachResultBean {

    private String msg;
    private int code;
    private List<DataBean> data;
    private String shareUrl;

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

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

        private int goodsType;
        private String img;
        private String actualPrice;
        private String couponPrice;
        private String otherGoodsId;
        private String otherName;
        private String otherPrice;
        private String shopName;
        private String volume;
        private String commissionRate;
        private String fee;
        private String buyPrice;
        private String buyRate;
        private String shopIcon;
        private String source;
        private String isBuy;//0 没买 1买过
        private String soldTotal;
        private int total;
        private int status;
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSoldTotal() {
            return soldTotal;
        }

        public void setSoldTotal(String soldTotal) {
            this.soldTotal = soldTotal;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getIsBuy() {
            return isBuy;
        }

        public void setIsBuy(String isBuy) {
            this.isBuy = isBuy;
        }

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

        public int getGoodsType() {
            return goodsType;
        }

        public void setGoodsType(int goodsType) {
            this.goodsType = goodsType;
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
            return new BigDecimal(otherPrice.trim()).stripTrailingZeros().toPlainString();
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

        public String getCommissionRate() {
            return commissionRate;
        }

        public void setCommissionRate(String commissionRate) {
            this.commissionRate = commissionRate;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getBuyPrice() {
            return new BigDecimal(buyPrice.trim()).stripTrailingZeros().toPlainString();
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
