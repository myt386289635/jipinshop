package com.example.administrator.jipinshop.bean;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/11/12
 * @Describe
 */
public class V2FreeListBean {

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

        private String id;
        private String name;
        private String img;
        private String imgs;
        private String goodsId;
        private String goodsContent;
        private String count;
        private int status;
        private String confirmTime;
        private String freePrice;
        private String freeRate;
        private Object shareTitle;
        private Object shareContent;
        private Object shareUrl;
        private Object shareImg;
        private String createTime;
        private int inviteUserCount;
        private int type;
        private int myInviteUserCount;
        private String otherPrice;
        private String actualPrice;
        private String freeGuide;
        private String buyPrice;

        public String getBuyPrice() {
            return new BigDecimal(buyPrice).stripTrailingZeros().toPlainString();
        }

        public void setBuyPrice(String buyPrice) {
            this.buyPrice = buyPrice;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getImgs() {
            return imgs;
        }

        public void setImgs(String imgs) {
            this.imgs = imgs;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public String getGoodsContent() {
            return goodsContent;
        }

        public void setGoodsContent(String goodsContent) {
            this.goodsContent = goodsContent;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getConfirmTime() {
            return confirmTime;
        }

        public void setConfirmTime(String confirmTime) {
            this.confirmTime = confirmTime;
        }

        public String getFreePrice() {
            return freePrice;
        }

        public void setFreePrice(String freePrice) {
            this.freePrice = freePrice;
        }

        public String getFreeRate() {
            return freeRate;
        }

        public void setFreeRate(String freeRate) {
            this.freeRate = freeRate;
        }

        public Object getShareTitle() {
            return shareTitle;
        }

        public void setShareTitle(Object shareTitle) {
            this.shareTitle = shareTitle;
        }

        public Object getShareContent() {
            return shareContent;
        }

        public void setShareContent(Object shareContent) {
            this.shareContent = shareContent;
        }

        public Object getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(Object shareUrl) {
            this.shareUrl = shareUrl;
        }

        public Object getShareImg() {
            return shareImg;
        }

        public void setShareImg(Object shareImg) {
            this.shareImg = shareImg;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getInviteUserCount() {
            return inviteUserCount;
        }

        public void setInviteUserCount(int inviteUserCount) {
            this.inviteUserCount = inviteUserCount;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getMyInviteUserCount() {
            return myInviteUserCount;
        }

        public void setMyInviteUserCount(int myInviteUserCount) {
            this.myInviteUserCount = myInviteUserCount;
        }

        public String getOtherPrice() {
            return otherPrice;
        }

        public void setOtherPrice(String otherPrice) {
            this.otherPrice = otherPrice;
        }

        public String getActualPrice() {
            return new BigDecimal(actualPrice).stripTrailingZeros().toPlainString();
        }

        public void setActualPrice(String actualPrice) {
            this.actualPrice = actualPrice;
        }

        public String getFreeGuide() {
            return freeGuide;
        }

        public void setFreeGuide(String freeGuide) {
            this.freeGuide = freeGuide;
        }
    }
}
