package com.example.administrator.jipinshop.bean;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/10/29
 * @Describe
 */
public class Action11Bean {

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
        private List<CategoryListBean> categoryList;
        private List<GoodsDataListBean> goodsDataList;
        private List<EvaluationTabBean.DataBean.AdListBean> adList;

        public List<CategoryListBean> getCategoryList() {
            return categoryList;
        }

        public void setCategoryList(List<CategoryListBean> categoryList) {
            this.categoryList = categoryList;
        }

        public List<GoodsDataListBean> getGoodsDataList() {
            return goodsDataList;
        }

        public void setGoodsDataList(List<GoodsDataListBean> goodsDataList) {
            this.goodsDataList = goodsDataList;
        }

        public List<EvaluationTabBean.DataBean.AdListBean> getAdList() {
            return adList;
        }

        public void setAdList(List<EvaluationTabBean.DataBean.AdListBean> adList) {
            this.adList = adList;
        }

        public static class CategoryListBean {
            /**
             * categoryId : 9af8a74a98c84c7d9e01e5010e1d923b
             * type : 4
             * categoryName : 个户健康
             * orderNum : 1
             * iconImg : http://jipincheng.cn/category/img/20191030/4c04900bc1664deea922b1f84d33c3f6
             * adList : [{"img":"http://jipincheng.cn/activity/img/20191029/af85438769a846f99211fe8331bd76b3","type":2,"objectId":"9b3f96b39c5448a98562033991d90ca5"},{"img":"http://jipincheng.cn/activity/img/20190704/6612525c3eba45779f078b8287348a2f","type":2,"objectId":"b416e03e27e8451892420a1822a3e281"}]
             */

            private String categoryId;
            private int type;
            private String categoryName;
            private int orderNum;
            private String iconImg;
            private List<AdListBean> adList;

            public String getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(String categoryId) {
                this.categoryId = categoryId;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getCategoryName() {
                return categoryName;
            }

            public void setCategoryName(String categoryName) {
                this.categoryName = categoryName;
            }

            public int getOrderNum() {
                return orderNum;
            }

            public void setOrderNum(int orderNum) {
                this.orderNum = orderNum;
            }

            public String getIconImg() {
                return iconImg;
            }

            public void setIconImg(String iconImg) {
                this.iconImg = iconImg;
            }

            public List<AdListBean> getAdList() {
                return adList;
            }

            public void setAdList(List<AdListBean> adList) {
                this.adList = adList;
            }

            public static class AdListBean {
                /**
                 * img : http://jipincheng.cn/activity/img/20191029/af85438769a846f99211fe8331bd76b3
                 * type : 2
                 * objectId : 9b3f96b39c5448a98562033991d90ca5
                 */

                private String img;
                private int type;
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

                public String getObjectId() {
                    return objectId;
                }

                public void setObjectId(String objectId) {
                    this.objectId = objectId;
                }
            }
        }

        public static class GoodsDataListBean {
            /**
             * type : 1
             * ad : null
             * goods : {"goodsId":"dfc1bfee7a86495c9ce4c8cee3098425","goodsName":"ANDIS电推剪73010","img":"http://jipincheng.cn/c42a1e401de6471e9fabb27a6b2dc3f1","imgs":"http://jipincheng.cn/c42a1e401de6471e9fabb27a6b2dc3f1","goodsTags":"[{\"name\":\"成人\"},{\"name\":\"700元档\"},{\"name\":\"渐变\"},{\"name\":\"全头剪\"}]","actualPrice":780,"fee":39,"buyPrice":741,"couponPrice":0,"otherGoodsId":"560439197072","otherName":"美国原装进口andis电推剪 发廊油头电推剪安迪斯73010Supreme电推","otherPrice":780,"recommendReason":"ANDIS 73010电推剪外观漂亮，品质优秀，尤其是理发能力非常突出，而且美发师深恶痛觉的噪音、发热问题都控制得当，很不错哒~~","score":9,"scoreOptions":"[{\"name\":\"外观操作\",\"score\":\"9\"},{\"name\":\"理发效果\",\"score\":\"10\"},{\"name\":\"噪音水平\",\"score\":\"8\"},{\"name\":\"发热水平\",\"score\":\"9\"}]","pv":10,"source":2,"goodsBrokerage":78,"voteCount":null,"vote":null,"collect":null,"goodsTagsList":[{"name":"成人"},{"name":"700元档"},{"name":"渐变"},{"name":"全头剪"}],"scoreOptionsList":[{"name":"外观操作","score":"9"},{"name":"理发效果","score":"10"},{"name":"噪音水平","score":"8"},{"name":"发热水平","score":"9"}],"imgList":null,"relatedArticleList":null,"articleId":"0a722b462e0a4b2a99aa64689b87893e"}
             */

            private int type;
            private AdBean ad;
            private GoodsBean goods;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public AdBean getAd() {
                return ad;
            }

            public void setAd(AdBean ad) {
                this.ad = ad;
            }

            public GoodsBean getGoods() {
                return goods;
            }

            public void setGoods(GoodsBean goods) {
                this.goods = goods;
            }

            public static class GoodsBean {

                private String goodsId;
                private String goodsName;
                private String img;
                private String imgs;
                private String goodsTags;
                private String actualPrice;
                private String fee;
                private String buyPrice;
                private String couponPrice;
                private String otherGoodsId;
                private String otherName;
                private String otherPrice;
                private String recommendReason;
                private String score;
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

                public String getActualPrice() {
                    return actualPrice;
                }

                public void setActualPrice(String actualPrice) {
                    this.actualPrice = actualPrice;
                }

                public String getFee() {
                    return new BigDecimal(fee).stripTrailingZeros().toPlainString();
                }

                public void setFee(String fee) {
                    this.fee = fee;
                }

                public String getBuyPrice() {
                    return new BigDecimal(buyPrice).stripTrailingZeros().toPlainString();
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
                    return new BigDecimal(otherPrice).stripTrailingZeros().toPlainString();
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

                public String getScore() {
                    return score;
                }

                public void setScore(String score) {
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
                     * name : 外观操作
                     * score : 9
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

            public static class AdBean {
                private String img;
                private String name;
                private String objectId;

                public String getObjectId() {
                    return objectId;
                }

                public void setObjectId(String objectId) {
                    this.objectId = objectId;
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
            }
        }
    }
}
