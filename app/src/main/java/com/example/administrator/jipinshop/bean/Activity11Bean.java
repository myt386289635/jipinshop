package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/10/30
 * @Describe
 */
public class Activity11Bean {

    private String msg;
    private int code;
    private AdBean ad;
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

    public AdBean getAd() {
        return ad;
    }

    public void setAd(AdBean ad) {
        this.ad = ad;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class AdBean {

        private String img;
        private int type;
        private String name;
        private String objectId;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
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
    }

    public static class DataBean {

        private String goodsId;
        private String goodsName;
        private String img;
        private String imgs;
        private String goodsTags;
        private Object type;
        private String actualPrice;
        private String fee;
        private String buyPrice;
        private String couponPrice;
        private String otherGoodsId;
        private String otherName;
        private String otherPrice;
        private String recommendReason;
        private double score;
        private String scoreOptions;
        private int pv;
        private int source;
        private String goodsBrokerage;
        private Object voteCount;
        private Object vote;
        private Object collect;
        private Object imgList;
        private Object relatedArticleList;
        private String articleId;
        private List<GoodsTagsListBean> goodsTagsList;
        private List<ScoreOptionsListBean> scoreOptionsList;

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
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

        public String getGoodsTags() {
            return goodsTags;
        }

        public void setGoodsTags(String goodsTags) {
            this.goodsTags = goodsTags;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public String getActualPrice() {
            return actualPrice;
        }

        public void setActualPrice(String actualPrice) {
            this.actualPrice = actualPrice;
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

        public String getRecommendReason() {
            return recommendReason;
        }

        public void setRecommendReason(String recommendReason) {
            this.recommendReason = recommendReason;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public String getScoreOptions() {
            return scoreOptions;
        }

        public void setScoreOptions(String scoreOptions) {
            this.scoreOptions = scoreOptions;
        }

        public int getPv() {
            return pv;
        }

        public void setPv(int pv) {
            this.pv = pv;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public String getGoodsBrokerage() {
            return goodsBrokerage;
        }

        public void setGoodsBrokerage(String goodsBrokerage) {
            this.goodsBrokerage = goodsBrokerage;
        }

        public Object getVoteCount() {
            return voteCount;
        }

        public void setVoteCount(Object voteCount) {
            this.voteCount = voteCount;
        }

        public Object getVote() {
            return vote;
        }

        public void setVote(Object vote) {
            this.vote = vote;
        }

        public Object getCollect() {
            return collect;
        }

        public void setCollect(Object collect) {
            this.collect = collect;
        }

        public Object getImgList() {
            return imgList;
        }

        public void setImgList(Object imgList) {
            this.imgList = imgList;
        }

        public Object getRelatedArticleList() {
            return relatedArticleList;
        }

        public void setRelatedArticleList(Object relatedArticleList) {
            this.relatedArticleList = relatedArticleList;
        }

        public String getArticleId() {
            return articleId;
        }

        public void setArticleId(String articleId) {
            this.articleId = articleId;
        }

        public List<GoodsTagsListBean> getGoodsTagsList() {
            return goodsTagsList;
        }

        public void setGoodsTagsList(List<GoodsTagsListBean> goodsTagsList) {
            this.goodsTagsList = goodsTagsList;
        }

        public List<ScoreOptionsListBean> getScoreOptionsList() {
            return scoreOptionsList;
        }

        public void setScoreOptionsList(List<ScoreOptionsListBean> scoreOptionsList) {
            this.scoreOptionsList = scoreOptionsList;
        }

        public static class GoodsTagsListBean {
            /**
             * name : 成人
             */

            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class ScoreOptionsListBean {
            /**
             * name : 功率
             * score : 8
             */

            private String name;
            private String score;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }
        }
    }
}
