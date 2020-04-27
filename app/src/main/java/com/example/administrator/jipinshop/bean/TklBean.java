package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2019/12/5
 * @Describe
 */
public class TklBean {

    private String addPoint;
    private String msg;
    private int code;
    private DataBeanX data;

    public String getAddPoint() {
        return addPoint;
    }

    public void setAddPoint(String addPoint) {
        this.addPoint = addPoint;
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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {

        private int type;
        private DataBean data;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {

            private int goodsType;
            private String img;
            private String whiteImage;
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
            private String source;

            //邀请好友时返的bean
            private String avatar;
            private String nickname;
            private String invitationCode;
            private String addPoint;

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getInvitationCode() {
                return invitationCode;
            }

            public void setInvitationCode(String invitationCode) {
                this.invitationCode = invitationCode;
            }

            public String getAddPoint() {
                return addPoint;
            }

            public void setAddPoint(String addPoint) {
                this.addPoint = addPoint;
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
}
