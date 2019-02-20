package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/1/5
 * @Describe 榜单公共类列表
 */
public class HomeCommenBean {

    private String msg;
    private int code;
    private String category1Id;
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

    public String getCategory1Id() {
        return category1Id;
    }

    public void setCategory1Id(String category1Id) {
        this.category1Id = category1Id;
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
        /**
         * goodsId : 1e94570c7cbd468bb694e606468a8f88
         * goodsName : 米家电动剃须刀
         * img : http://jipincheng.cn/f3d46be87ae445b7848b001516c261ae
         * imgs : http://jipincheng.cn/f3d46be87ae445b7848b001516c261ae
         * goodsTags : [{"name":"男性"},{"name":"100元档"},{"name":"旋转式"},{"name":"双层刀"}]
         * actualPrice : 206.2
         * couponPrice : 5
         * otherGoodsId : 585697724045
         * otherName : 小米 米家电动剃须刀男士刮胡刀全身水洗充电式胡须刀正品现货
         * otherPrice : 211.2
         * recommendReason : 米家电动剃须刀外观简约、时尚，很适合年轻人使用。剃须能力强劲，双层刀片剃得更干净，全向浮动，舒适性、贴面效果更佳。
         * score : 9.8
         * scoreOptions : [{"name":"外观操作","score":"10"},{"name":"剃须性能","score":"9"},{"name":"舒适程度","score":"10"},{"name":"噪音水平","score":"10"}]
         * pv : 763
         * source : 3
         * voteCount : null
         * exchangePoint : 3000
         * vote : null
         * collect : null
         * goodsTagsList : [{"name":"男性"},{"name":"100元档"},{"name":"旋转式"},{"name":"双层刀"}]
         * scoreOptionsList : [{"name":"外观操作","score":"10"},{"name":"剃须性能","score":"9"},{"name":"舒适程度","score":"10"},{"name":"噪音水平","score":"10"}]
         * imgList : null
         */

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
        private String score;
        private String scoreOptions;
        private String pv;
        private int source;
        private String voteCount;
        private int exchangePoint;
        private String vote;
        private String collect;
        private Object imgList;
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

        public String getPv() {
            return pv;
        }

        public void setPv(String pv) {
            this.pv = pv;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public String getVoteCount() {
            return voteCount;
        }

        public void setVoteCount(String voteCount) {
            this.voteCount = voteCount;
        }

        public int getExchangePoint() {
            return exchangePoint;
        }

        public void setExchangePoint(int exchangePoint) {
            this.exchangePoint = exchangePoint;
        }

        public String getVote() {
            return vote;
        }

        public void setVote(String vote) {
            this.vote = vote;
        }

        public String getCollect() {
            return collect;
        }

        public void setCollect(String collect) {
            this.collect = collect;
        }

        public Object getImgList() {
            return imgList;
        }

        public void setImgList(Object imgList) {
            this.imgList = imgList;
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
         * categoryId : 3a9da7e316914052ab207c6162a59ae6
         * parentId : c38c97e81c3142779f9e085902c9423d
         * categoryName : 电动牙刷
         * img : http://jipincheng.cn/category/img/20190219/778dd126247b44dc874060f22d671b22
         * orderNum : 1
         * children : null
         * adList : null
         */

        private String categoryId;
        private String parentId;
        private String categoryName;
        private String img;
        private int orderNum;
        private Object children;
        private Object adList;

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
    }
}
