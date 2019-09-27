package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/9/26
 * @Describe
 */
public class MyFreeBean {

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
        private String smallName;
        private String img;
        private String imgs;
        private Object imgList;
        private int point;
        private int count;
        private String goodsId;
        private String goodsContent;
        private int status;
        private String createTime;
        private String activitiesStartTime;
        private String activitiesEndTime;
        private int confirmTime;
        private String preTime;
        private String dendlineTime;
        private double freeRate;
        private String freeRule;
        private String freeNote;
        private Object shareTitle;
        private Object shareContent;
        private Object shareUrl;
        private String shareImg;
        private String shopImg;
        private String shopName;
        private Object articleTags;
        private Object articleTagList;
        private Object relatedArticleId;
        private int applyStatus;
        private Object freeGuide;
        private String userCount;
        private double actualPrice;
        private double otherPrice;
        private double freePrice;
        private Object goodsContentList;
        private int timeToStart;
        private ArticleBean article;
        private Object remind;
        private Object preStatus;
        private Object fromId;

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

        public String getSmallName() {
            return smallName;
        }

        public void setSmallName(String smallName) {
            this.smallName = smallName;
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

        public Object getImgList() {
            return imgList;
        }

        public void setImgList(Object imgList) {
            this.imgList = imgList;
        }

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getActivitiesStartTime() {
            return activitiesStartTime;
        }

        public void setActivitiesStartTime(String activitiesStartTime) {
            this.activitiesStartTime = activitiesStartTime;
        }

        public String getActivitiesEndTime() {
            return activitiesEndTime;
        }

        public void setActivitiesEndTime(String activitiesEndTime) {
            this.activitiesEndTime = activitiesEndTime;
        }

        public int getConfirmTime() {
            return confirmTime;
        }

        public void setConfirmTime(int confirmTime) {
            this.confirmTime = confirmTime;
        }

        public String getPreTime() {
            return preTime;
        }

        public void setPreTime(String preTime) {
            this.preTime = preTime;
        }

        public String getDendlineTime() {
            return dendlineTime;
        }

        public void setDendlineTime(String dendlineTime) {
            this.dendlineTime = dendlineTime;
        }

        public double getFreeRate() {
            return freeRate;
        }

        public void setFreeRate(double freeRate) {
            this.freeRate = freeRate;
        }

        public String getFreeRule() {
            return freeRule;
        }

        public void setFreeRule(String freeRule) {
            this.freeRule = freeRule;
        }

        public String getFreeNote() {
            return freeNote;
        }

        public void setFreeNote(String freeNote) {
            this.freeNote = freeNote;
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

        public String getShareImg() {
            return shareImg;
        }

        public void setShareImg(String shareImg) {
            this.shareImg = shareImg;
        }

        public String getShopImg() {
            return shopImg;
        }

        public void setShopImg(String shopImg) {
            this.shopImg = shopImg;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public Object getArticleTags() {
            return articleTags;
        }

        public void setArticleTags(Object articleTags) {
            this.articleTags = articleTags;
        }

        public Object getArticleTagList() {
            return articleTagList;
        }

        public void setArticleTagList(Object articleTagList) {
            this.articleTagList = articleTagList;
        }

        public Object getRelatedArticleId() {
            return relatedArticleId;
        }

        public void setRelatedArticleId(Object relatedArticleId) {
            this.relatedArticleId = relatedArticleId;
        }

        public int getApplyStatus() {
            return applyStatus;
        }

        public void setApplyStatus(int applyStatus) {
            this.applyStatus = applyStatus;
        }

        public Object getFreeGuide() {
            return freeGuide;
        }

        public void setFreeGuide(Object freeGuide) {
            this.freeGuide = freeGuide;
        }

        public String getUserCount() {
            return userCount;
        }

        public void setUserCount(String userCount) {
            this.userCount = userCount;
        }

        public double getActualPrice() {
            return actualPrice;
        }

        public void setActualPrice(double actualPrice) {
            this.actualPrice = actualPrice;
        }

        public double getOtherPrice() {
            return otherPrice;
        }

        public void setOtherPrice(double otherPrice) {
            this.otherPrice = otherPrice;
        }

        public double getFreePrice() {
            return freePrice;
        }

        public void setFreePrice(double freePrice) {
            this.freePrice = freePrice;
        }

        public Object getGoodsContentList() {
            return goodsContentList;
        }

        public void setGoodsContentList(Object goodsContentList) {
            this.goodsContentList = goodsContentList;
        }

        public int getTimeToStart() {
            return timeToStart;
        }

        public void setTimeToStart(int timeToStart) {
            this.timeToStart = timeToStart;
        }

        public ArticleBean getArticle() {
            return article;
        }

        public void setArticle(ArticleBean article) {
            this.article = article;
        }

        public Object getRemind() {
            return remind;
        }

        public void setRemind(Object remind) {
            this.remind = remind;
        }

        public Object getPreStatus() {
            return preStatus;
        }

        public void setPreStatus(Object preStatus) {
            this.preStatus = preStatus;
        }

        public Object getFromId() {
            return fromId;
        }

        public void setFromId(Object fromId) {
            this.fromId = fromId;
        }

        public static class ArticleBean {
            /**
             * articleId : 9116843d514e40fa837d1b7006a4acef
             * img : null
             * title : null
             * contentType : null
             * type : null
             * size : null
             * pv : null
             * publishTime : null
             * createTime : null
             * subtitle : null
             * user : null
             */

            private String articleId;
            private Object img;
            private Object title;
            private Object contentType;
            private Object type;
            private Object size;
            private Object pv;
            private Object publishTime;
            private Object createTime;
            private Object subtitle;
            private Object user;

            public String getArticleId() {
                return articleId;
            }

            public void setArticleId(String articleId) {
                this.articleId = articleId;
            }

            public Object getImg() {
                return img;
            }

            public void setImg(Object img) {
                this.img = img;
            }

            public Object getTitle() {
                return title;
            }

            public void setTitle(Object title) {
                this.title = title;
            }

            public Object getContentType() {
                return contentType;
            }

            public void setContentType(Object contentType) {
                this.contentType = contentType;
            }

            public Object getType() {
                return type;
            }

            public void setType(Object type) {
                this.type = type;
            }

            public Object getSize() {
                return size;
            }

            public void setSize(Object size) {
                this.size = size;
            }

            public Object getPv() {
                return pv;
            }

            public void setPv(Object pv) {
                this.pv = pv;
            }

            public Object getPublishTime() {
                return publishTime;
            }

            public void setPublishTime(Object publishTime) {
                this.publishTime = publishTime;
            }

            public Object getCreateTime() {
                return createTime;
            }

            public void setCreateTime(Object createTime) {
                this.createTime = createTime;
            }

            public Object getSubtitle() {
                return subtitle;
            }

            public void setSubtitle(Object subtitle) {
                this.subtitle = subtitle;
            }

            public Object getUser() {
                return user;
            }

            public void setUser(Object user) {
                this.user = user;
            }
        }
    }
}
