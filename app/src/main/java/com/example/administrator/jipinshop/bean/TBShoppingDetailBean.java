package com.example.administrator.jipinshop.bean;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/11/28
 * @Describe
 */
public class TBShoppingDetailBean {

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

        private String img;
        private String imgs;
        private String actualPrice;
        private String couponPrice;
        private String couponStartTime;
        private String couponEndTime;
        private String otherGoodsId;
        private String otherName;
        private String otherPrice;
        private String provcity;
        private String descImgs;
        private String recommendReason;
        private String score;
        private String scoreOptions;
        private String parameters;
        private String commissionRate;
        private String shopName;
        private String shopIcon;
        private String volume;
        private String evaluates;
        private String fee;
        private String buyPrice;
        private String collect;
        private List<ScoreOptionsListBean> scoreOptionsList;
        private List<ParametersListBean> parametersList;
        private List<EvaluateListBean> evaluateList;
        private List<String> imgList;
        private List<String> descImgList;

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

        public String getActualPrice() {
            return new BigDecimal(actualPrice).stripTrailingZeros().toPlainString();
        }

        public void setActualPrice(String actualPrice) {
            this.actualPrice = actualPrice;
        }

        public String getCouponPrice() {
            return new BigDecimal(couponPrice).stripTrailingZeros().toPlainString();
        }

        public void setCouponPrice(String couponPrice) {
            this.couponPrice = couponPrice;
        }

        public String getCouponStartTime() {
            return couponStartTime;
        }

        public void setCouponStartTime(String couponStartTime) {
            this.couponStartTime = couponStartTime;
        }

        public String getCouponEndTime() {
            return couponEndTime;
        }

        public void setCouponEndTime(String couponEndTime) {
            this.couponEndTime = couponEndTime;
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

        public String getProvcity() {
            return provcity;
        }

        public void setProvcity(String provcity) {
            this.provcity = provcity;
        }

        public String getDescImgs() {
            return descImgs;
        }

        public void setDescImgs(String descImgs) {
            this.descImgs = descImgs;
        }

        public String getRecommendReason() {
            return recommendReason;
        }

        public void setRecommendReason(String recommendReason) {
            this.recommendReason = recommendReason;
        }

        public String getScore() {
            return new BigDecimal(score).stripTrailingZeros().toPlainString();
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

        public String getParameters() {
            return parameters;
        }

        public void setParameters(String parameters) {
            this.parameters = parameters;
        }

        public String getCommissionRate() {
            return commissionRate;
        }

        public void setCommissionRate(String commissionRate) {
            this.commissionRate = commissionRate;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getShopIcon() {
            return shopIcon;
        }

        public void setShopIcon(String shopIcon) {
            this.shopIcon = shopIcon;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public String getEvaluates() {
            return evaluates;
        }

        public void setEvaluates(String evaluates) {
            this.evaluates = evaluates;
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

        public String getCollect() {
            return collect;
        }

        public void setCollect(String collect) {
            this.collect = collect;
        }

        public List<ScoreOptionsListBean> getScoreOptionsList() {
            return scoreOptionsList;
        }

        public void setScoreOptionsList(List<ScoreOptionsListBean> scoreOptionsList) {
            this.scoreOptionsList = scoreOptionsList;
        }

        public List<ParametersListBean> getParametersList() {
            return parametersList;
        }

        public void setParametersList(List<ParametersListBean> parametersList) {
            this.parametersList = parametersList;
        }

        public List<EvaluateListBean> getEvaluateList() {
            return evaluateList;
        }

        public void setEvaluateList(List<EvaluateListBean> evaluateList) {
            this.evaluateList = evaluateList;
        }

        public List<String> getImgList() {
            return imgList;
        }

        public void setImgList(List<String> imgList) {
            this.imgList = imgList;
        }

        public List<String> getDescImgList() {
            return descImgList;
        }

        public void setDescImgList(List<String> descImgList) {
            this.descImgList = descImgList;
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
                return new BigDecimal(score).stripTrailingZeros().toPlainString();
            }

            public void setScore(String score) {
                this.score = score;
            }
        }

        public static class ParametersListBean {
            /**
             * name : 品牌
             * value : ANDIS
             */

            private String name;
            private String value;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public static class EvaluateListBean {
            /**
             * title : 宝贝描述
             * score : 4.9
             */

            private String title;
            private String score;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getScore() {
                return new BigDecimal(score).stripTrailingZeros().toPlainString();
            }

            public void setScore(String score) {
                this.score = score;
            }
        }
    }
}
