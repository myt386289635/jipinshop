package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/12/30
 * @Describe
 */
public class SeckillBean {

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
         * otherGoodsId : 629270071635
         * startTime : 2020-12-28 11:00:00
         * endTime : 2020-12-30 15:32:34
         * soldTotal : 10
         * total : 100
         * status : 1
         * source : 2
         * img : https://img.alicdn.com/bao/uploaded/i4/3339200673/O1CN01qSpTvq1GqHB4n23H5_!!0-item_pic.jpg
         * whiteImage : null
         * actualPrice : 7.8
         * couponPrice : 5
         * otherName : 帽子女冬天骑电动车防风防寒雷锋帽男加厚保暖护耳护眼东北棉帽子
         * otherPrice : 12.8
         * shopIcon : null
         * shopName : 橙影旗舰店
         * commissionRate : 1.6
         * fee : 0.13
         * buyPrice : 7.67
         * buyRate : 6.0
         */

        private String otherGoodsId;
        private String startTime;
        private String endTime;
        private int soldTotal;
        private int total;
        private int status;
        private String source;
        private String img;
        private String whiteImage;
        private String actualPrice;
        private String couponPrice;
        private String otherName;
        private String otherPrice;
        private String shopIcon;
        private String shopName;
        private String commissionRate;
        private String fee;
        private String buyPrice;
        private String buyRate;
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOtherGoodsId() {
            return otherGoodsId;
        }

        public void setOtherGoodsId(String otherGoodsId) {
            this.otherGoodsId = otherGoodsId;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getSoldTotal() {
            return soldTotal;
        }

        public void setSoldTotal(int soldTotal) {
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

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getWhiteImage() {
            return whiteImage;
        }

        public void setWhiteImage(String whiteImage) {
            this.whiteImage = whiteImage;
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

        public String getShopIcon() {
            return shopIcon;
        }

        public void setShopIcon(String shopIcon) {
            this.shopIcon = shopIcon;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
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
