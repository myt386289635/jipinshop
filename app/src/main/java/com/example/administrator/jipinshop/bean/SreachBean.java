package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/8/15
 * @Describe
 */
public class SreachBean {

    private String msg;
    private int code;
    private List<DataBean> data;
    private List<GoodsCategoryListBean> goodsCategoryList;

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

    public List<GoodsCategoryListBean> getGoodsCategoryList() {
        return goodsCategoryList;
    }

    public void setGoodsCategoryList(List<GoodsCategoryListBean> goodsCategoryList) {
        this.goodsCategoryList = goodsCategoryList;
    }

    public static class DataBean {

        private String goodsId;
        private String goodsName;
        private String img;
        private String imgs;
        private String goodsTags;
        private int actualPrice;
        private int couponPrice;
        private String otherGoodsId;
        private String otherName;
        private int otherPrice;
        private String recommendReason;
        private double score;
        private String scoreOptions;
        private int pv;
        private int source;
        private Object voteCount;
        private Object vote;
        private Object collect;
        private Object imgList;
        private Object relatedArticleList;
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

        public int getActualPrice() {
            return actualPrice;
        }

        public void setActualPrice(int actualPrice) {
            this.actualPrice = actualPrice;
        }

        public int getCouponPrice() {
            return couponPrice;
        }

        public void setCouponPrice(int couponPrice) {
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

        public int getOtherPrice() {
            return otherPrice;
        }

        public void setOtherPrice(int otherPrice) {
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
             * name : 男性
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
             * name : 外观操作
             * score : 10
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

    public static class GoodsCategoryListBean {
        /**
         * categoryId : bb269c5d3fe94b1cb1d5df68afcda140
         * parentId : c38c97e81c3142779f9e085902c9423d
         * categoryName : 剃须刀
         * img : http://jipincheng.cn/category/img/20190302/1b817aea733b42aab3ce0767b9837ee6
         * orderNum : 2
         * top : 1
         * topTitle : 最佳剃须刀推荐榜
         * topImg : http://jipincheng.cn/category/img/20190729/1c1584159fd84ef6aefbb9e9942068d8
         * topOrderNum : 3
         * children : null
         * adList : null
         * relatedItemList : null
         * relatedGoodsList : null
         * relatedPedia : null
         * relatedQuestionList : null
         * relatedItemCategotyList : null
         * relatedArticleList : null
         */

        private String categoryId;
        private String parentId;
        private String categoryName;
        private String img;
        private int orderNum;
        private int top;
        private String topTitle;
        private String topImg;
        private int topOrderNum;
        private Object children;
        private Object adList;
        private Object relatedItemList;
        private Object relatedGoodsList;
        private Object relatedPedia;
        private Object relatedQuestionList;
        private Object relatedItemCategotyList;
        private Object relatedArticleList;

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(int orderNum) {
            this.orderNum = orderNum;
        }

        public int getTop() {
            return top;
        }

        public void setTop(int top) {
            this.top = top;
        }

        public String getTopTitle() {
            return topTitle;
        }

        public void setTopTitle(String topTitle) {
            this.topTitle = topTitle;
        }

        public String getTopImg() {
            return topImg;
        }

        public void setTopImg(String topImg) {
            this.topImg = topImg;
        }

        public int getTopOrderNum() {
            return topOrderNum;
        }

        public void setTopOrderNum(int topOrderNum) {
            this.topOrderNum = topOrderNum;
        }

        public Object getChildren() {
            return children;
        }

        public void setChildren(Object children) {
            this.children = children;
        }

        public Object getAdList() {
            return adList;
        }

        public void setAdList(Object adList) {
            this.adList = adList;
        }

        public Object getRelatedItemList() {
            return relatedItemList;
        }

        public void setRelatedItemList(Object relatedItemList) {
            this.relatedItemList = relatedItemList;
        }

        public Object getRelatedGoodsList() {
            return relatedGoodsList;
        }

        public void setRelatedGoodsList(Object relatedGoodsList) {
            this.relatedGoodsList = relatedGoodsList;
        }

        public Object getRelatedPedia() {
            return relatedPedia;
        }

        public void setRelatedPedia(Object relatedPedia) {
            this.relatedPedia = relatedPedia;
        }

        public Object getRelatedQuestionList() {
            return relatedQuestionList;
        }

        public void setRelatedQuestionList(Object relatedQuestionList) {
            this.relatedQuestionList = relatedQuestionList;
        }

        public Object getRelatedItemCategotyList() {
            return relatedItemCategotyList;
        }

        public void setRelatedItemCategotyList(Object relatedItemCategotyList) {
            this.relatedItemCategotyList = relatedItemCategotyList;
        }

        public Object getRelatedArticleList() {
            return relatedArticleList;
        }

        public void setRelatedArticleList(Object relatedArticleList) {
            this.relatedArticleList = relatedArticleList;
        }
    }
}
