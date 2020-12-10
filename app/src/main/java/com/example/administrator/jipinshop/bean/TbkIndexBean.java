package com.example.administrator.jipinshop.bean;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/12/16
 * @Describe
 */
public class TbkIndexBean {

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

        private Ad2Bean ad2; //活动图
        private List<Ad1ListBean> ad1List;//轮播图
        private List<BoxCategoryListBean> boxCategoryList;//宫格
        private List<MessageListBean> messageList;//人物轮播
        private List<AllowanceGoodsListBean> allowanceGoodsList;//新人专区
        private List<AllowanceGoodsListBean> allowanceGoodsList2;//老人专区
        private List<ActivityListBean> activityList;//活动位
        private List<HotGoodsListBean> hotGoodsList;//热销榜单
        private Boolean newUser; //是否是新人
        private List<TBSreachResultBean.DataBean> commendGoodsList;//今日推荐
        private MessageBean message;//首页横向跑马灯
        private long userEndTime;//倒计时

        public long getUserEndTime() {
            return userEndTime;
        }

        public void setUserEndTime(long userEndTime) {
            this.userEndTime = userEndTime;
        }

        public MessageBean getMessage() {
            return message;
        }

        public void setMessage(MessageBean message) {
            this.message = message;
        }

        public List<TBSreachResultBean.DataBean> getCommendGoodsList() {
            return commendGoodsList;
        }

        public void setCommendGoodsList(List<TBSreachResultBean.DataBean> commendGoodsList) {
            this.commendGoodsList = commendGoodsList;
        }

        public Boolean getNewUser() {
            return newUser;
        }

        public void setNewUser(Boolean newUser) {
            this.newUser = newUser;
        }

        public List<AllowanceGoodsListBean> getAllowanceGoodsList() {
            return allowanceGoodsList;
        }

        public void setAllowanceGoodsList(List<AllowanceGoodsListBean> allowanceGoodsList) {
            this.allowanceGoodsList = allowanceGoodsList;
        }

        public Ad2Bean getAd2() {
            return ad2;
        }

        public void setAd2(Ad2Bean ad2) {
            this.ad2 = ad2;
        }

        public List<Ad1ListBean> getAd1List() {
            return ad1List;
        }

        public void setAd1List(List<Ad1ListBean> ad1List) {
            this.ad1List = ad1List;
        }

        public List<MessageListBean> getMessageList() {
            return messageList;
        }

        public void setMessageList(List<MessageListBean> messageList) {
            this.messageList = messageList;
        }

        public List<ActivityListBean> getActivityList() {
            return activityList;
        }

        public void setActivityList(List<ActivityListBean> activityList) {
            this.activityList = activityList;
        }

        public List<HotGoodsListBean> getHotGoodsList() {
            return hotGoodsList;
        }

        public void setHotGoodsList(List<HotGoodsListBean> hotGoodsList) {
            this.hotGoodsList = hotGoodsList;
        }

        public List<BoxCategoryListBean> getBoxCategoryList() {
            return boxCategoryList;
        }

        public void setBoxCategoryList(List<BoxCategoryListBean> boxCategoryList) {
            this.boxCategoryList = boxCategoryList;
        }

        public List<AllowanceGoodsListBean> getAllowanceGoodsList2() {
            return allowanceGoodsList2;
        }

        public void setAllowanceGoodsList2(List<AllowanceGoodsListBean> allowanceGoodsList2) {
            this.allowanceGoodsList2 = allowanceGoodsList2;
        }

        public static class Ad2Bean {
            /**
             * img : http://jipincheng.cn/activity/img/20191213/48224878971d4df2a32e595de45458e2
             * type : 0
             * name : 活动图片
             * StringId : null
             * color :
             */

            private String img;
            private String type;
            private String name;
            private String objectId;
            private String color;
            private String source;
            private String remark;

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
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

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }
        }

        public static class Ad1ListBean {
            /**
             * img : http://jipincheng.cn/activity/img/20191118/9bb328bcbfa1478386c77e039cd35d72
             * type : 0
             * name : 母婴用品
             * StringId : null
             * color : fb93b6
             */

            private String img;
            private String type;
            private String name;
            private String objectId;
            private String color;
            private String source;
            private String remark;

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
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

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }
        }

        public static class BoxCategoryListBean {
            /**
             * categoryId : null
             * categoryTitle : null
             * list : [{"id":"287ca208b592495e97c659b3acad9100","iconUrl":"http://jipincheng.cn/category/img/20200811/116be0f9778c4716bed8c902a348c0a7","title":"淘宝","subTitle":null,"orderNum":1,"status":1,"type":13,"targetId":"42","source":"11","categoryId":null}]
             */

            private String categoryId;
            private String categoryTitle;
            private List<ListBean> list;

            public String getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(String categoryId) {
                this.categoryId = categoryId;
            }

            public String getCategoryTitle() {
                return categoryTitle;
            }

            public void setCategoryTitle(String categoryTitle) {
                this.categoryTitle = categoryTitle;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * id : 287ca208b592495e97c659b3acad9100
                 * iconUrl : http://jipincheng.cn/category/img/20200811/116be0f9778c4716bed8c902a348c0a7
                 * title : 淘宝
                 * subTitle : null
                 * orderNum : 1
                 * status : 1
                 * type : 13
                 * targetId : 42
                 * source : 11
                 * categoryId : null
                 */

                private String id;
                private String iconUrl;
                private String title;
                private String subTitle;
                private String orderNum;
                private String status;
                private String type;
                private String targetId;
                private String source;
                private String categoryId;
                private String remark;
                private String popStatus;//1显示弹框，0不显示

                public String getPopStatus() {
                    return popStatus;
                }

                public void setPopStatus(String popStatus) {
                    this.popStatus = popStatus;
                }

                public String getRemark() {
                    return remark;
                }

                public void setRemark(String remark) {
                    this.remark = remark;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getIconUrl() {
                    return iconUrl;
                }

                public void setIconUrl(String iconUrl) {
                    this.iconUrl = iconUrl;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getSubTitle() {
                    return subTitle;
                }

                public void setSubTitle(String subTitle) {
                    this.subTitle = subTitle;
                }

                public String getOrderNum() {
                    return orderNum;
                }

                public void setOrderNum(String orderNum) {
                    this.orderNum = orderNum;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getTargetId() {
                    return targetId;
                }

                public void setTargetId(String targetId) {
                    this.targetId = targetId;
                }

                public String getSource() {
                    return source;
                }

                public void setSource(String source) {
                    this.source = source;
                }

                public String getCategoryId() {
                    return categoryId;
                }

                public void setCategoryId(String categoryId) {
                    this.categoryId = categoryId;
                }
            }
        }

        public static class MessageListBean {
            /**
             * id : 0
             * avatar : http://jipincheng.cn/avatar/img/20191214/6655522824c346d98c68bc5339b4b216
             * content : 恭喜你获得了1亿元现金奖励
             */

            private String id;
            private String avatar;
            private String content;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }

        public static class AllowanceGoodsListBean {

            private String img;
            private String otherPrice;
            private String totalCouponPrice;
            private String buyPrice;
            private String useAllowancePrice;

            public String getUseAllowancePrice() {
                return useAllowancePrice;
            }

            public void setUseAllowancePrice(String useAllowancePrice) {
                this.useAllowancePrice = useAllowancePrice;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getOtherPrice() {
                return otherPrice;
            }

            public void setOtherPrice(String otherPrice) {
                this.otherPrice = otherPrice;
            }

            public String getTotalCouponPrice() {
                return totalCouponPrice;
            }

            public void setTotalCouponPrice(String totalCouponPrice) {
                this.totalCouponPrice = totalCouponPrice;
            }

            public String getBuyPrice() {
                return buyPrice;
            }

            public void setBuyPrice(String buyPrice) {
                this.buyPrice = buyPrice;
            }
        }

        public static class ActivityListBean {

            private String img;
            private String name;
            private String objectId;
            private String type;
            private String source;
            private String remark;

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
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

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class HotGoodsListBean {
           
            private int goodsType;
            private String img;
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

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
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

            public String getActualPrice() {
                return actualPrice;
            }

            public void setActualPrice(String actualPrice) {
                this.actualPrice = actualPrice;
            }

            public String getCouponPrice() {
                return new BigDecimal(couponPrice.trim()).stripTrailingZeros().toPlainString();
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
                return new BigDecimal(otherPrice.trim()).stripTrailingZeros().toPlainString();
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
                return new BigDecimal(fee.trim()).stripTrailingZeros().toPlainString();
            }

            public void setFee(String fee) {
                this.fee = fee;
            }

            public String getBuyPrice() {
                return new BigDecimal(buyPrice.trim()).stripTrailingZeros().toPlainString();
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

        public static class MessageBean {

            private String content;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
