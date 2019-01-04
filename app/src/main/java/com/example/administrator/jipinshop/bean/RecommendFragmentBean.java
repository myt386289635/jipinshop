package com.example.administrator.jipinshop.bean;

import java.util.List;

public class RecommendFragmentBean {

    private String msg;
    private int code;
    private List<DataBean> data;
    private List<AdListBean> adList;

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

    public List<AdListBean> getAdList() {
        return adList;
    }

    public void setAdList(List<AdListBean> adList) {
        this.adList = adList;
    }

    public static class DataBean {
        /**
         * goodsId : 19563699049
         * goodsName : 科德士理发器CHC-912
         * category1Id : null
         * category2Id : ea8455cc01b643cb86c43437c4a566ff
         * img : http://jipincheng.cn/59bf7339476e495b838288fbac77258c
         * imgs : http://jipincheng.cn/5062e94208b745eea73a295bcf5bf743,http://jipincheng.cn/5062e94208b745eea73a295bcf5bf743,http://jipincheng.cn/5062e94208b745eea73a295bcf5bf743
         * goodsTags : [{"name":"成人"},{"name":"100元档"},{"name":"数显"},{"name":"快速充电"}]
         * actualPrice : 154
         * couponPrice : 5
         * otherGoodsId : 19563699049
         * otherName : codos科德士理发器CHC 912充电式专业美发店电推剪成人电推子工具
         * otherPrice : 159
         * recommendReason : 科德士CHC-912理发器，做工比较精致，手感舒适，理发能力相当不错。同时，配有数显，充电快，续航较久。总的来说，综合性能相当优秀。
         * score : 7
         * scoreOptions : [{"name":"外观操作","score":"8"},{"name":"理发效果","score":"6"},{"name":"噪音水平","score":"8"},{"name":"发热水平","score":"6"}]
         * pv : 333
         * upToTop : 1
         * upToFound : 1
         * upToEvaluation : 1
         * upToPointShop : 1
         * upToTrial : 0
         * source : 2
         * status : 1
         * voteCount : 0
         * exchangePoint : 15400
         * orderNum : null
         * createTime : 2018-12-09 23:40:57
         * updateTime : 2019-01-02 18:01:58
         * goodsTagsList : [{"name":"成人"},{"name":"100元档"},{"name":"数显"},{"name":"快速充电"}]
         * scoreOptionsList : [{"name":"外观操作","score":"8"},{"name":"理发效果","score":"6"},{"name":"噪音水平","score":"8"},{"name":"发热水平","score":"6"}]
         * imgList : ["http://jipincheng.cn/5062e94208b745eea73a295bcf5bf743","http://jipincheng.cn/5062e94208b745eea73a295bcf5bf743","http://jipincheng.cn/5062e94208b745eea73a295bcf5bf743"]
         */

        private String goodsId;
        private String goodsName;
        private Object category1Id;
        private String category2Id;
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
        private String upToTop;
        private String upToFound;
        private String upToEvaluation;
        private String upToPointShop;
        private String upToTrial;
        private int source;
        private String status;
        private String voteCount;
        private String exchangePoint;
        private Object orderNum;
        private String createTime;
        private String updateTime;
        private List<GoodsTagsListBean> goodsTagsList;
        private List<ScoreOptionsListBean> scoreOptionsList;
        private List<String> imgList;

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

        public Object getCategory1Id() {
            return category1Id;
        }

        public void setCategory1Id(Object category1Id) {
            this.category1Id = category1Id;
        }

        public String getCategory2Id() {
            return category2Id;
        }

        public void setCategory2Id(String category2Id) {
            this.category2Id = category2Id;
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

        public String getUpToTop() {
            return upToTop;
        }

        public void setUpToTop(String upToTop) {
            this.upToTop = upToTop;
        }

        public String getUpToFound() {
            return upToFound;
        }

        public void setUpToFound(String upToFound) {
            this.upToFound = upToFound;
        }

        public String getUpToEvaluation() {
            return upToEvaluation;
        }

        public void setUpToEvaluation(String upToEvaluation) {
            this.upToEvaluation = upToEvaluation;
        }

        public String getUpToPointShop() {
            return upToPointShop;
        }

        public void setUpToPointShop(String upToPointShop) {
            this.upToPointShop = upToPointShop;
        }

        public String getUpToTrial() {
            return upToTrial;
        }

        public void setUpToTrial(String upToTrial) {
            this.upToTrial = upToTrial;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getVoteCount() {
            return voteCount;
        }

        public void setVoteCount(String voteCount) {
            this.voteCount = voteCount;
        }

        public String getExchangePoint() {
            return exchangePoint;
        }

        public void setExchangePoint(String exchangePoint) {
            this.exchangePoint = exchangePoint;
        }

        public Object getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(Object orderNum) {
            this.orderNum = orderNum;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
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

        public List<String> getImgList() {
            return imgList;
        }

        public void setImgList(List<String> imgList) {
            this.imgList = imgList;
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

    public static class AdListBean {
        private String img;
        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
