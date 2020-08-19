package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/5/25
 * @Describe
 */
public class NewFreeBean {

    private String msg;
    private int code;
    private String officialWechat;
    private int endTime;
    private Ad2Bean ad2;
    private Ad1Bean ad1;
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

    public String getOfficialWechat() {
        return officialWechat;
    }

    public void setOfficialWechat(String officialWechat) {
        this.officialWechat = officialWechat;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public Ad2Bean getAd2() {
        return ad2;
    }

    public void setAd2(Ad2Bean ad2) {
        this.ad2 = ad2;
    }

    public Ad1Bean getAd1() {
        return ad1;
    }

    public void setAd1(Ad1Bean ad1) {
        this.ad1 = ad1;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class Ad2Bean {
        /**
         * img : http://jipincheng.cn/activity/img/20200422/11e29670446b4644a20a541e2af8e482
         * type : 11
         * name : 高返专区
         * objectId : 688d6708b8b64816ae52713c4f291b71
         * color : null
         */

        private String img;
        private String type;
        private String name;
        private String objectId;
        private Object color;
        private String source;

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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public Object getColor() {
            return color;
        }

        public void setColor(Object color) {
            this.color = color;
        }
    }

    public static class Ad1Bean {
        /**
         * img : http://jipincheng.cn/activity/img/20200422/0ef1dd68c7d940e7bdfa81e045bc0a1c
         * type : 11
         * name : 每日爆款
         * objectId : 1
         * color : null
         */

        private String img;
        private String type;
        private String name;
        private String objectId;
        private Object color;
        private String source;

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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public Object getColor() {
            return color;
        }

        public void setColor(Object color) {
            this.color = color;
        }
    }

    public static class DataBean {
        /**
         * id : 1
         * fee : 2.0
         * otherGoodsId : 547853725417
         * status : 1
         * total : 100
         * createTime : 2020-05-25 11:05:06
         * img : https://img.alicdn.com/bao/uploaded/i1/1049594641/O1CN0161vICw1k9cgrNVY4U_!!0-item_pic.jpg
         * actualPrice : 9.9
         * couponPrice : 0
         * otherName : 楼尚 304不锈钢烧烤夹子烤肉面包食品防烫食物餐菜厨房煎牛排专用
         * otherPrice : 9.9
         * buyPrice : 7.9
         */

        private String id;
        private String fee;
        private String otherGoodsId;
        private int status;
        private int total;
        private String createTime;
        private String img;
        private String actualPrice;
        private String couponPrice;
        private String otherName;
        private String otherPrice;
        private String buyPrice;
        private String soldTotal;
        private String isBuy; //0 没买 1买过

        public String getIsBuy() {
            return isBuy;
        }

        public void setIsBuy(String isBuy) {
            this.isBuy = isBuy;
        }

        public String getSoldTotal() {
            return soldTotal;
        }

        public void setSoldTotal(String soldTotal) {
            this.soldTotal = soldTotal;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getOtherGoodsId() {
            return otherGoodsId;
        }

        public void setOtherGoodsId(String otherGoodsId) {
            this.otherGoodsId = otherGoodsId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
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

        public String getBuyPrice() {
            return buyPrice;
        }

        public void setBuyPrice(String buyPrice) {
            this.buyPrice = buyPrice;
        }
    }
}
