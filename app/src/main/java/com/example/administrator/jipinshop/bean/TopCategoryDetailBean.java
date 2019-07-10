package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/7/3
 * @Describe 小分类榜单
 */
public class TopCategoryDetailBean {

    private String msg;
    private int code;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

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
        private List<RelatedItemListBean> relatedItemList;
        private List<RelatedGoodsListBean> relatedGoodsList;
        private RelatedPediaBean relatedPedia;
        private List<RelatedQuestionListBean> relatedQuestionList;
        private List<RelatedItemCategotyListBean> relatedItemCategotyList;
        private List<RelatedArticleListBean> relatedArticleList;

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

        public List<RelatedItemListBean> getRelatedItemList() {
            return relatedItemList;
        }

        public void setRelatedItemList(List<RelatedItemListBean> relatedItemList) {
            this.relatedItemList = relatedItemList;
        }

        public List<RelatedGoodsListBean> getRelatedGoodsList() {
            return relatedGoodsList;
        }

        public void setRelatedGoodsList(List<RelatedGoodsListBean> relatedGoodsList) {
            this.relatedGoodsList = relatedGoodsList;
        }

        public RelatedPediaBean getRelatedPedia() {
            return relatedPedia;
        }

        public void setRelatedPedia(RelatedPediaBean relatedPedia) {
            this.relatedPedia = relatedPedia;
        }

        public List<RelatedQuestionListBean> getRelatedQuestionList() {
            return relatedQuestionList;
        }

        public void setRelatedQuestionList(List<RelatedQuestionListBean> relatedQuestionList) {
            this.relatedQuestionList = relatedQuestionList;
        }

        public List<RelatedItemCategotyListBean> getRelatedItemCategotyList() {
            return relatedItemCategotyList;
        }

        public void setRelatedItemCategotyList(List<RelatedItemCategotyListBean> relatedItemCategotyList) {
            this.relatedItemCategotyList = relatedItemCategotyList;
        }

        public List<RelatedArticleListBean> getRelatedArticleList() {
            return relatedArticleList;
        }

        public void setRelatedArticleList(List<RelatedArticleListBean> relatedArticleList) {
            this.relatedArticleList = relatedArticleList;
        }

        public static class RelatedItemListBean {

            private String orderbyCategoryId;
            private String name;
            private Object img;
            private Object orderbyType;
            private int orderNum;
            private int status;
            private Object goodsList;

            public String getOrderbyCategoryId() {
                return orderbyCategoryId;
            }

            public void setOrderbyCategoryId(String orderbyCategoryId) {
                this.orderbyCategoryId = orderbyCategoryId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Object getImg() {
                return img;
            }

            public void setImg(Object img) {
                this.img = img;
            }

            public Object getOrderbyType() {
                return orderbyType;
            }

            public void setOrderbyType(Object orderbyType) {
                this.orderbyType = orderbyType;
            }

            public int getOrderNum() {
                return orderNum;
            }

            public void setOrderNum(int orderNum) {
                this.orderNum = orderNum;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public Object getGoodsList() {
                return goodsList;
            }

            public void setGoodsList(Object goodsList) {
                this.goodsList = goodsList;
            }
        }

        public static class RelatedGoodsListBean {

            private String goodsId;
            private String goodsName;
            private String img;
            private String imgs;
            private String goodsTags;
            private String actualPrice;
            private String couponPrice;
            private String otherGoodsId;
            private String otherName;
            private String otherPrice;
            private String recommendReason;
            private double score;
            private String scoreOptions;
            private int pv;
            private String source;
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

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
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
                 * name : 11
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
                 * name : 外观设计
                 * score : 7
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

        public static class RelatedItemCategotyListBean {

            private String orderbyCategoryId;
            private String name;
            private Object img;
            private Object orderbyType;
            private int orderNum;
            private int status;
            private List<GoodsListBean> goodsList;

            public String getOrderbyCategoryId() {
                return orderbyCategoryId;
            }

            public void setOrderbyCategoryId(String orderbyCategoryId) {
                this.orderbyCategoryId = orderbyCategoryId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Object getImg() {
                return img;
            }

            public void setImg(Object img) {
                this.img = img;
            }

            public Object getOrderbyType() {
                return orderbyType;
            }

            public void setOrderbyType(Object orderbyType) {
                this.orderbyType = orderbyType;
            }

            public int getOrderNum() {
                return orderNum;
            }

            public void setOrderNum(int orderNum) {
                this.orderNum = orderNum;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public List<GoodsListBean> getGoodsList() {
                return goodsList;
            }

            public void setGoodsList(List<GoodsListBean> goodsList) {
                this.goodsList = goodsList;
            }

            public static class GoodsListBean {

                private String goodsId;
                private String goodsName;
                private String img;
                private String imgs;
                private String goodsTags;
                private String actualPrice;
                private String couponPrice;
                private String otherGoodsId;
                private String otherName;
                private String otherPrice;
                private String recommendReason;
                private double score;
                private String scoreOptions;
                private int pv;
                private String source;
                private Object voteCount;
                private Object vote;
                private Object collect;
                private Object goodsTagsList;
                private Object scoreOptionsList;
                private Object imgList;
                private Object relatedArticleList;

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

                public String getSource() {
                    return source;
                }

                public void setSource(String source) {
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

                public Object getGoodsTagsList() {
                    return goodsTagsList;
                }

                public void setGoodsTagsList(Object goodsTagsList) {
                    this.goodsTagsList = goodsTagsList;
                }

                public Object getScoreOptionsList() {
                    return scoreOptionsList;
                }

                public void setScoreOptionsList(Object scoreOptionsList) {
                    this.scoreOptionsList = scoreOptionsList;
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
            }
        }

        public static class RelatedArticleListBean {
            private String articleId;
            private String img;
            private String title;
            private int contentType;
            private String type;
            private Object size;
            private int pv;
            private UserBeanX user;
            private String createTime;

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getArticleId() {
                return articleId;
            }

            public void setArticleId(String articleId) {
                this.articleId = articleId;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getContentType() {
                return contentType;
            }

            public void setContentType(int contentType) {
                this.contentType = contentType;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public Object getSize() {
                return size;
            }

            public void setSize(Object size) {
                this.size = size;
            }

            public int getPv() {
                return pv;
            }

            public void setPv(int pv) {
                this.pv = pv;
            }

            public UserBeanX getUser() {
                return user;
            }

            public void setUser(UserBeanX user) {
                this.user = user;
            }

            public static class UserBeanX {
                /**
                 * userId : 7d67892cb02f4766aa72fd5b08b8d8d1
                 * nickname : 皮Sir
                 * gender : 男
                 * avatar : http://jipincheng.cn/dafca5b3d66a43dc9cc00e07acd7bdb0
                 * authentication : 0
                 * fansCount : null
                 * follow : null
                 */

                private String userId;
                private String nickname;
                private String gender;
                private String avatar;
                private int authentication;
                private Object fansCount;
                private Object follow;

                public String getUserId() {
                    return userId;
                }

                public void setUserId(String userId) {
                    this.userId = userId;
                }

                public String getNickname() {
                    return nickname;
                }

                public void setNickname(String nickname) {
                    this.nickname = nickname;
                }

                public String getGender() {
                    return gender;
                }

                public void setGender(String gender) {
                    this.gender = gender;
                }

                public String getAvatar() {
                    return avatar;
                }

                public void setAvatar(String avatar) {
                    this.avatar = avatar;
                }

                public int getAuthentication() {
                    return authentication;
                }

                public void setAuthentication(int authentication) {
                    this.authentication = authentication;
                }

                public Object getFansCount() {
                    return fansCount;
                }

                public void setFansCount(Object fansCount) {
                    this.fansCount = fansCount;
                }

                public Object getFollow() {
                    return follow;
                }

                public void setFollow(Object follow) {
                    this.follow = follow;
                }
            }
        }

        public static class RelatedQuestionListBean{

            private String title;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }

        public static class RelatedPediaBean{

            private String articleId;
            private String subtitle;

            public String getArticleId() {
                return articleId;
            }

            public void setArticleId(String articleId) {
                this.articleId = articleId;
            }

            public String getSubtitle() {
                return subtitle;
            }

            public void setSubtitle(String subtitle) {
                this.subtitle = subtitle;
            }
        }
    }
}
