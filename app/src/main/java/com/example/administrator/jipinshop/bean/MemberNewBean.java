package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/8/26
 * @Describe
 */
public class MemberNewBean {

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

        private int level;
        private String avatar;
        private String nickname;
        private String levelEndTime;
        private String monthPrice;
        private String monthPriceBefore;
        private String yearPrice;
        private String yearPriceBefore;
        private OrderLevelDataBean orderLevelData;
        private LevelDetail1Bean levelDetail1;
        private LevelDetail2Bean levelDetail2;
        private String vipMsg;
        private String officialAvatar;
        private String officialWechat;
        private String preMonthEndTime;
        private String preYearEndTime;
        private List<MessageListBean> messageList;
        private List<VipBoxListBean> vipBoxList;

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getLevelEndTime() {
            return levelEndTime;
        }

        public void setLevelEndTime(String levelEndTime) {
            this.levelEndTime = levelEndTime;
        }

        public String getMonthPrice() {
            return monthPrice;
        }

        public void setMonthPrice(String monthPrice) {
            this.monthPrice = monthPrice;
        }

        public String getMonthPriceBefore() {
            return monthPriceBefore;
        }

        public void setMonthPriceBefore(String monthPriceBefore) {
            this.monthPriceBefore = monthPriceBefore;
        }

        public String getYearPrice() {
            return yearPrice;
        }

        public void setYearPrice(String yearPrice) {
            this.yearPrice = yearPrice;
        }

        public String getYearPriceBefore() {
            return yearPriceBefore;
        }

        public void setYearPriceBefore(String yearPriceBefore) {
            this.yearPriceBefore = yearPriceBefore;
        }

        public OrderLevelDataBean getOrderLevelData() {
            return orderLevelData;
        }

        public void setOrderLevelData(OrderLevelDataBean orderLevelData) {
            this.orderLevelData = orderLevelData;
        }

        public LevelDetail1Bean getLevelDetail1() {
            return levelDetail1;
        }

        public void setLevelDetail1(LevelDetail1Bean levelDetail1) {
            this.levelDetail1 = levelDetail1;
        }

        public LevelDetail2Bean getLevelDetail2() {
            return levelDetail2;
        }

        public void setLevelDetail2(LevelDetail2Bean levelDetail2) {
            this.levelDetail2 = levelDetail2;
        }

        public String getVipMsg() {
            return vipMsg;
        }

        public void setVipMsg(String vipMsg) {
            this.vipMsg = vipMsg;
        }

        public String getOfficialAvatar() {
            return officialAvatar;
        }

        public void setOfficialAvatar(String officialAvatar) {
            this.officialAvatar = officialAvatar;
        }

        public String getOfficialWechat() {
            return officialWechat;
        }

        public void setOfficialWechat(String officialWechat) {
            this.officialWechat = officialWechat;
        }

        public String getPreMonthEndTime() {
            return preMonthEndTime;
        }

        public void setPreMonthEndTime(String preMonthEndTime) {
            this.preMonthEndTime = preMonthEndTime;
        }

        public String getPreYearEndTime() {
            return preYearEndTime;
        }

        public void setPreYearEndTime(String preYearEndTime) {
            this.preYearEndTime = preYearEndTime;
        }

        public List<MessageListBean> getMessageList() {
            return messageList;
        }

        public void setMessageList(List<MessageListBean> messageList) {
            this.messageList = messageList;
        }

        public List<VipBoxListBean> getVipBoxList() {
            return vipBoxList;
        }

        public void setVipBoxList(List<VipBoxListBean> vipBoxList) {
            this.vipBoxList = vipBoxList;
        }

        public static class OrderLevelDataBean {
            /**
             * orderList : [{"itemTitle":"安踏官网旗舰运动卫衣女2020秋季新款宽松连帽衫休闲针织套头上衣","itemImg":"https://img.alicdn.com/bao/uploaded/i3/385132127/O1CN01SA79iS1RaD332dV6Q_!!0-item_pic.jpg","fee":12},{"itemTitle":"㊙小牛凯西5份成品披萨套餐6/7英寸匹萨速冻半成品比萨饼加热即食","itemImg":"https://img.alicdn.com/bao/uploaded/i4/2074450097/O1CN018SJ2E91CaT1yOH1CW_!!0-item_pic.jpg","fee":16},{"itemTitle":"安踏运动卫衣女子2020秋季新款套头圆领上衣休闲修身长袖官网旗舰","itemImg":"https://img.alicdn.com/bao/uploaded/i2/385132127/O1CN018lKUqy1RaD36sgYBB-385132127.jpg","fee":12}]
             * topTitle : 未办理VIP，上月错过800元优惠
             * bottomTitle : 办理VIP可省：
             * sumOrderFee : 9600
             */

            private String topTitle;
            private String bottomTitle;
            private String sumOrderFee;
            private List<OrderListBean> orderList;

            public String getTopTitle() {
                return topTitle;
            }

            public void setTopTitle(String topTitle) {
                this.topTitle = topTitle;
            }

            public String getBottomTitle() {
                return bottomTitle;
            }

            public void setBottomTitle(String bottomTitle) {
                this.bottomTitle = bottomTitle;
            }

            public String getSumOrderFee() {
                return sumOrderFee;
            }

            public void setSumOrderFee(String sumOrderFee) {
                this.sumOrderFee = sumOrderFee;
            }

            public List<OrderListBean> getOrderList() {
                return orderList;
            }

            public void setOrderList(List<OrderListBean> orderList) {
                this.orderList = orderList;
            }

            public static class OrderListBean {
                /**
                 * itemTitle : 安踏官网旗舰运动卫衣女2020秋季新款宽松连帽衫休闲针织套头上衣
                 * itemImg : https://img.alicdn.com/bao/uploaded/i3/385132127/O1CN01SA79iS1RaD332dV6Q_!!0-item_pic.jpg
                 * fee : 12.0
                 */

                private String itemTitle;
                private String itemImg;
                private String fee;

                public String getItemTitle() {
                    return itemTitle;
                }

                public void setItemTitle(String itemTitle) {
                    this.itemTitle = itemTitle;
                }

                public String getItemImg() {
                    return itemImg;
                }

                public void setItemImg(String itemImg) {
                    this.itemImg = itemImg;
                }

                public String getFee() {
                    return fee;
                }

                public void setFee(String fee) {
                    this.fee = fee;
                }
            }
        }

        public static class LevelDetail1Bean {
            /**
             * title1 : 自购佣金100%返现
             * title2 : 全场商品0利润，自购佣金100%返现
             * title3 : 9600元
             * itemTitle : 安踏官网旗舰运动卫衣女2020秋季新款宽松连帽衫休闲针织套头上衣
             * itemImg : https://img.alicdn.com/bao/uploaded/i3/385132127/O1CN01SA79iS1RaD332dV6Q_!!0-item_pic.jpg
             * otherPrice : 139.0
             * price : 109.0
             * lowPrice : 97.0
             * fee : 12.0
             */

            private String title1;
            private String title2;
            private String title3;
            private String itemTitle;
            private String itemImg;
            private String otherPrice;
            private String price;
            private String lowPrice;
            private String fee;

            public String getTitle1() {
                return title1;
            }

            public void setTitle1(String title1) {
                this.title1 = title1;
            }

            public String getTitle2() {
                return title2;
            }

            public void setTitle2(String title2) {
                this.title2 = title2;
            }

            public String getTitle3() {
                return title3;
            }

            public void setTitle3(String title3) {
                this.title3 = title3;
            }

            public String getItemTitle() {
                return itemTitle;
            }

            public void setItemTitle(String itemTitle) {
                this.itemTitle = itemTitle;
            }

            public String getItemImg() {
                return itemImg;
            }

            public void setItemImg(String itemImg) {
                this.itemImg = itemImg;
            }

            public String getOtherPrice() {
                return otherPrice;
            }

            public void setOtherPrice(String otherPrice) {
                this.otherPrice = otherPrice;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getLowPrice() {
                return lowPrice;
            }

            public void setLowPrice(String lowPrice) {
                this.lowPrice = lowPrice;
            }

            public String getFee() {
                return fee;
            }

            public void setFee(String fee) {
                this.fee = fee;
            }
        }

        public static class LevelDetail2Bean {
            /**
             * title1 : 会员专享购物补贴
             * title2 : 下单直接抵扣，无需等待返现
             * title3 : 108元*12月
             * itemTitle : 楼尚烧烤夹子烤肉面包食品防烫不锈钢食物餐菜煎牛排专用厨房工具
             * itemImg : https://img.alicdn.com/bao/uploaded/i1/1049594641/O1CN0161vICw1k9cgrNVY4U_!!0-item_pic.jpg
             * otherPrice : 6.5
             * price : 6.5
             * lowPrice : 5.5
             * fee : 1.0
             */

            private String title1;
            private String title2;
            private String title3;
            private String itemTitle;
            private String itemImg;
            private String otherPrice;
            private String price;
            private String lowPrice;
            private String fee;

            public String getTitle1() {
                return title1;
            }

            public void setTitle1(String title1) {
                this.title1 = title1;
            }

            public String getTitle2() {
                return title2;
            }

            public void setTitle2(String title2) {
                this.title2 = title2;
            }

            public String getTitle3() {
                return title3;
            }

            public void setTitle3(String title3) {
                this.title3 = title3;
            }

            public String getItemTitle() {
                return itemTitle;
            }

            public void setItemTitle(String itemTitle) {
                this.itemTitle = itemTitle;
            }

            public String getItemImg() {
                return itemImg;
            }

            public void setItemImg(String itemImg) {
                this.itemImg = itemImg;
            }

            public String getOtherPrice() {
                return otherPrice;
            }

            public void setOtherPrice(String otherPrice) {
                this.otherPrice = otherPrice;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getLowPrice() {
                return lowPrice;
            }

            public void setLowPrice(String lowPrice) {
                this.lowPrice = lowPrice;
            }

            public String getFee() {
                return fee;
            }

            public void setFee(String fee) {
                this.fee = fee;
            }
        }

        public static class MessageListBean {
            /**
             * id : 11
             * avatar : http://jipincheng.cn/avatar/img/20191223/61b3cbc30fa140f99f45b0b97aba6565
             * content : 嘿马小丸子刚刚成为极品城vip
             * type : 2
             */

            private String id;
            private String avatar;
            private String content;
            private int type;

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

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }

        public static class VipBoxListBean {
            /**
             * id : f6ca6718639d4227ad59549b5d4cb9f8
             * iconUrl : http://jipincheng.cn/category/img/20200826/1d13ac24ab3f4ba29cb492d9738ea5d8
             * title : 自购100%佣金
             * orderNum : 1
             * status : 1
             * type : 0
             * targetId : null
             * source : 0
             */

            private String id;
            private String iconUrl;
            private String title;
            private int orderNum;
            private int status;
            private int type;
            private String targetId;
            private String source;

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

            public int getType() {
                return type;
            }

            public void setType(int type) {
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
        }
    }
}
