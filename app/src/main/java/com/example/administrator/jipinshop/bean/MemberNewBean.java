package com.example.administrator.jipinshop.bean;

import java.io.Serializable;
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
    private int openFamily; //0关闭，1开启

    public int getOpenFamily() {
        return openFamily;
    }

    public void setOpenFamily(int openFamily) {
        this.openFamily = openFamily;
    }

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

        private int level;//0 普通 ， 1 月卡 ，2年卡
        private String boxImg;//更多权益
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
        private LevelDetail3Bean levelDetail3;
        private LevelDetail4Bean levelDetail4;
        private LevelDetail5Bean levelDetail5;
        private LevelDetail6Bean levelDetail6;
        private LevelDetail7Bean levelDetail7;
        private LevelDetail8Bean levelDetail8;
        private String vipMsg;
        private String officialAvatar;
        private String officialWechat;
        private String preMonthEndTime;
        private String preYearEndTime;
        private List<MessageListBean> messageList;
        private List<VipBoxListBean> vipBoxList;
        private List<VipBoxListBean> monthBoxList;
        private List<VipBoxListBean> yearBoxList;

        public String getBoxImg() {
            return boxImg;
        }

        public void setBoxImg(String boxImg) {
            this.boxImg = boxImg;
        }

        public List<VipBoxListBean> getMonthBoxList() {
            return monthBoxList;
        }

        public void setMonthBoxList(List<VipBoxListBean> monthBoxList) {
            this.monthBoxList = monthBoxList;
        }

        public List<VipBoxListBean> getYearBoxList() {
            return yearBoxList;
        }

        public void setYearBoxList(List<VipBoxListBean> yearBoxList) {
            this.yearBoxList = yearBoxList;
        }

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

        public LevelDetail3Bean getLevelDetail3() {
            return levelDetail3;
        }

        public void setLevelDetail3(LevelDetail3Bean levelDetail3) {
            this.levelDetail3 = levelDetail3;
        }

        public LevelDetail4Bean getLevelDetail4() {
            return levelDetail4;
        }

        public void setLevelDetail4(LevelDetail4Bean levelDetail4) {
            this.levelDetail4 = levelDetail4;
        }

        public LevelDetail5Bean getLevelDetail5() {
            return levelDetail5;
        }

        public void setLevelDetail5(LevelDetail5Bean levelDetail5) {
            this.levelDetail5 = levelDetail5;
        }

        public LevelDetail6Bean getLevelDetail6() {
            return levelDetail6;
        }

        public void setLevelDetail6(LevelDetail6Bean levelDetail6) {
            this.levelDetail6 = levelDetail6;
        }

        public LevelDetail7Bean getLevelDetail7() {
            return levelDetail7;
        }

        public void setLevelDetail7(LevelDetail7Bean levelDetail7) {
            this.levelDetail7 = levelDetail7;
        }

        public LevelDetail8Bean getLevelDetail8() {
            return levelDetail8;
        }

        public void setLevelDetail8(LevelDetail8Bean levelDetail8) {
            this.levelDetail8 = levelDetail8;
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
             * orderList : [{"tag":"错过vip优惠一：1次免单","itemTitle":"好吉利30包手帕纸便携式小包装整箱纸巾面巾纸餐巾纸随身卫生抽纸","itemImg":"https://img.alicdn.com/bao/uploaded/i1/3087155153/O1CN01cJ5yBQ1nw7USUMw9h_!!0-item_pic.jpg","fee":1},{"tag":"错过vip优惠二：购物100%返现","itemTitle":"安踏官网旗舰运动卫衣女士2020秋冬新款宽松长袖休闲连帽套头上衣","itemImg":"https://img.alicdn.com/bao/uploaded/i1/385132127/O1CN01ttnCbf1RaD3Pu1RZl_!!0-item_pic.jpg","fee":13.08},{"tag":null,"itemTitle":"安踏官网旗舰运动卫衣女士2020秋冬新款宽松长袖休闲连帽套头上衣","itemImg":"https://img.alicdn.com/bao/uploaded/i1/385132127/O1CN01ttnCbf1RaD3Pu1RZl_!!0-item_pic.jpg","fee":13.08}]
             * topTitle : 未办理VIP，上月错过800元优惠
             * bottomTitle : 办理VIP可省：
             * sumOrderFee : 26.16
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
                 * tag : 错过vip优惠一：1次免单
                 * itemTitle : 好吉利30包手帕纸便携式小包装整箱纸巾面巾纸餐巾纸随身卫生抽纸
                 * itemImg : https://img.alicdn.com/bao/uploaded/i1/3087155153/O1CN01cJ5yBQ1nw7USUMw9h_!!0-item_pic.jpg
                 * fee : 1.0
                 */

                private String tag;
                private String itemTitle;
                private String itemImg;
                private String fee;
                private String itemTag;

                public String getItemTag() {
                    return itemTag;
                }

                public void setItemTag(String itemTag) {
                    this.itemTag = itemTag;
                }

                public String getTag() {
                    return tag;
                }

                public void setTag(String tag) {
                    this.tag = tag;
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
             * itemTitle : 安踏官网旗舰店运动卫衣女2020秋季新款宽松长袖休闲针织套头上衣
             * itemImg : https://img.alicdn.com/bao/uploaded/i4/385132127/O1CN01ecCzE01RaD3GHkNU9-385132127.jpg
             * otherPrice : 159.0
             * price : 147.51
             * lowPrice : 146.61
             * fee : 12.39
             * otherGoodsId : 575195150424
             * source : 2
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
            private String otherGoodsId;
            private String source;

            public String getOtherGoodsId() {
                return otherGoodsId;
            }

            public void setOtherGoodsId(String otherGoodsId) {
                this.otherGoodsId = otherGoodsId;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

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
             * price : null
             * lowPrice : 5.5
             * fee : 1.0
             * otherGoodsId : null
             * source : null
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
            private String otherGoodsId;
            private String source;

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

            public String getOtherGoodsId() {
                return otherGoodsId;
            }

            public void setOtherGoodsId(String otherGoodsId) {
                this.otherGoodsId = otherGoodsId;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }
        }

        public static class LevelDetail3Bean {
            /**
             * title1 : 每月0元好礼
             * title2 : 每日更新0元商品，数量有限，全年可领12件
             * title3 : 0元领*12件
             * title4 : 每月
             * list : [{"id":"1b7a89e263b844dbade31c0234991fa0","fee":"1","otherGoodsId":"599307721542","status":1,"total":0,"createTime":"2020-05-27 14:44:57","img":"https://img.alicdn.com/bao/uploaded/i1/3087155153/O1CN01cJ5yBQ1nw7USUMw9h_!!0-item_pic.jpg","actualPrice":"7.9","couponPrice":2,"otherName":"好吉利30包手帕纸便携式小包装整箱纸巾面巾纸餐巾纸随身卫生抽纸","otherPrice":"9.9","buyPrice":null,"soldTotal":1,"isBuy":0},{"id":"94c730b2a6624f9892bddb2d69d0b85a","fee":"16.8","otherGoodsId":"577471170259","status":1,"total":23,"createTime":"2020-05-29 15:05:24","img":"https://img.alicdn.com/bao/uploaded/i3/1048442642/O1CN019kfUNZ1VO51RK2Svl_!!0-item_pic.jpg","actualPrice":"19.8","couponPrice":0,"otherName":"乐德牙线牙签家庭装超细牙线棒剔牙线便携式一次性牙线牙签300支","otherPrice":"19.8","buyPrice":null,"soldTotal":2,"isBuy":0},{"id":"58c77a2ab4fb4d79b3053ced1c8e3b2f","fee":"6.9","otherGoodsId":"594378959662","status":1,"total":66,"createTime":"2020-05-29 15:08:58","img":"https://img.alicdn.com/bao/uploaded/i2/711139120/O1CN01kSXM022HF0e7qLcHC_!!711139120.jpg","actualPrice":"18","couponPrice":40,"otherName":"毛衣服修剪器剃毛机打去除毛器剃吸除刮毛球充电动式打球起求。","otherPrice":"58","buyPrice":null,"soldTotal":2,"isBuy":0},{"id":"d4298231bbd048e48b33aa7f46e185b5","fee":"1","otherGoodsId":"611663777983","status":1,"total":0,"createTime":"2020-05-27 16:14:46","img":"https://img.alicdn.com/bao/uploaded/i2/2206356376097/O1CN01z1itN71uuTPf2qyR1_!!0-item_pic.jpg","actualPrice":"11.88","couponPrice":0,"otherName":"山凌户外移动拉杆电瓶音响大功率便携式音箱 充电器2孔8字电源线","otherPrice":"11.88","buyPrice":null,"soldTotal":2,"isBuy":0},{"id":"3715c37d5ed14e078013ef430fc15400","fee":"99","otherGoodsId":"577518854317","status":1,"total":22,"createTime":"2020-05-29 15:08:32","img":"http://jipincheng.cn/76b3bd77b3464a3dad7ec3dfa77ad22e","actualPrice":"99","couponPrice":0,"otherName":"康夫直板夹卷发棒内扣空气刘海夹板直发卷发两用拉直板不伤发529","otherPrice":"99","buyPrice":null,"soldTotal":2,"isBuy":0},{"id":"ad1a5260f4b844dba9cd19380bf8ed17","fee":"11","otherGoodsId":"566732941046","status":1,"total":55,"createTime":"2020-05-29 15:04:03","img":"https://img.alicdn.com/bao/uploaded/i4/2823345492/O1CN01k0BW3j1qRNoOpBQz1_!!0-item_pic.jpg","actualPrice":"89","couponPrice":20,"otherName":"美宝莲fit me粉底液女fitme遮瑕保湿控油皮李佳琦推荐官方旗舰店","otherPrice":"109","buyPrice":null,"soldTotal":2,"isBuy":0}]
             */

            private String title1;
            private String title2;
            private String title3;
            private String title4;
            private List<ListBean> list;

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

            public String getTitle4() {
                return title4;
            }

            public void setTitle4(String title4) {
                this.title4 = title4;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * id : 1b7a89e263b844dbade31c0234991fa0
                 * fee : 1
                 * otherGoodsId : 599307721542
                 * status : 1
                 * total : 0
                 * createTime : 2020-05-27 14:44:57
                 * img : https://img.alicdn.com/bao/uploaded/i1/3087155153/O1CN01cJ5yBQ1nw7USUMw9h_!!0-item_pic.jpg
                 * actualPrice : 7.9
                 * couponPrice : 2
                 * otherName : 好吉利30包手帕纸便携式小包装整箱纸巾面巾纸餐巾纸随身卫生抽纸
                 * otherPrice : 9.9
                 * buyPrice : null
                 * soldTotal : 1
                 * isBuy : 0
                 */

                private String id;
                private String fee;
                private String otherGoodsId;
                private int status;
                private String total;
                private String createTime;
                private String img;
                private String actualPrice;
                private String couponPrice;
                private String otherName;
                private String otherPrice;
                private String buyPrice;
                private String soldTotal;
                private String isBuy;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getFee() {
                    return fee;
                }

                public void setFee(String fee) {
                    this.fee = fee;
                }

                public String getOtherGoodsId() {
                    return otherGoodsId;
                }

                public void setOtherGoodsId(String otherGoodsId) {
                    this.otherGoodsId = otherGoodsId;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getTotal() {
                    return total;
                }

                public void setTotal(String total) {
                    this.total = total;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
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
                    return couponPrice;
                }

                public void setCouponPrice(String couponPrice) {
                    this.couponPrice = couponPrice;
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

                public String getBuyPrice() {
                    return buyPrice;
                }

                public void setBuyPrice(String buyPrice) {
                    this.buyPrice = buyPrice;
                }

                public String getSoldTotal() {
                    return soldTotal;
                }

                public void setSoldTotal(String soldTotal) {
                    this.soldTotal = soldTotal;
                }

                public String getIsBuy() {
                    return isBuy;
                }

                public void setIsBuy(String isBuy) {
                    this.isBuy = isBuy;
                }
            }
        }

        public static class LevelDetail4Bean {
            /**
             * title1 : 官方购物补贴
             * title2 : 下单直接抵扣，无需等待返现
             * title3 : 98元
             * title4 : 补贴金额
             * list : [{"id":"1","month":1,"allowance":108,"status":2,"userId":"1"},{"id":"10","month":10,"allowance":108,"status":2,"userId":"1"},{"id":"11","month":11,"allowance":108,"status":2,"userId":"1"},{"id":"12","month":12,"allowance":108,"status":2,"userId":"1"},{"id":"2","month":2,"allowance":108,"status":2,"userId":"1"},{"id":"3","month":3,"allowance":108,"status":2,"userId":"1"},{"id":"4","month":4,"allowance":108,"status":2,"userId":"1"},{"id":"5","month":5,"allowance":108,"status":2,"userId":"1"},{"id":"6","month":6,"allowance":108,"status":2,"userId":"1"},{"id":"7","month":7,"allowance":108,"status":2,"userId":"1"},{"id":"8","month":8,"allowance":108,"status":2,"userId":"1"},{"id":"9","month":9,"allowance":108,"status":2,"userId":"1"}]
             */

            private String title1;
            private String title2;
            private String title3;
            private String title4;
            private List<ListBeanX> list;

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

            public String getTitle4() {
                return title4;
            }

            public void setTitle4(String title4) {
                this.title4 = title4;
            }

            public List<ListBeanX> getList() {
                return list;
            }

            public void setList(List<ListBeanX> list) {
                this.list = list;
            }

            public static class ListBeanX implements Serializable {
                /**
                 * id : 1
                 * month : 1
                 * allowance : 108
                 * status : 2
                 * userId : 1
                 */

                private String id;
                private String month;
                private String allowance;
                private int status;
                private String userId;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getMonth() {
                    return month;
                }

                public void setMonth(String month) {
                    this.month = month;
                }

                public String getAllowance() {
                    return allowance;
                }

                public void setAllowance(String allowance) {
                    this.allowance = allowance;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getUserId() {
                    return userId;
                }

                public void setUserId(String userId) {
                    this.userId = userId;
                }
            }
        }

        public static class LevelDetail5Bean {
            /**
             * title1 : 一人开卡全家共享
             * title2 : 邀请三位家人可享受全额返现特权，每年再省28800元
             * title3 : 可添加
             * title4 : 3位家人
             * status : 0
             * list : ["http://jipincheng.cn/defaultImg.png","http://jipincheng.cn/defaultImg.png","http://jipincheng.cn/defaultImg.png","http://jipincheng.cn/defaultImg.png"]
             */

            private String title1;
            private String title2;
            private String title3;
            private String title4;
            private int status;
            private List<String> list;

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

            public String getTitle4() {
                return title4;
            }

            public void setTitle4(String title4) {
                this.title4 = title4;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public List<String> getList() {
                return list;
            }

            public void setList(List<String> list) {
                this.list = list;
            }
        }

        public static class LevelDetail6Bean {
            /**
             * title1 : 会员双倍极币
             * title2 : 加速兑换好礼
             * title3 : null
             * list : [{"id":"10","type":10,"point":2,"title":"签到(0/1)","content":"极币+2","location":9,"locationId":null,"maxPoint":2,"iconUrl":"http://jipincheng.cn/category/img/20200630/dda3ebc263ff49d1a38322efa55260ad","buttonName":"去签到","allCount":1,"todayCount":0,"status":0,"isshow":1},{"id":"80b163f12e1743aaa736d4e0671817b6","type":25,"point":5,"title":"分享发圈(0/2)","content":"每次极币+5","location":11,"locationId":null,"maxPoint":10,"iconUrl":"http://jipincheng.cn/category/img/20200630/294dedb6df1749f7aada627b7b4ecf27","buttonName":"去分享","allCount":2,"todayCount":0,"status":0,"isshow":1},{"id":"8dd26bd1ceb94e63b4e7d00a5ad90287","type":24,"point":10,"title":"购买商品(0/3)","content":"每次极币+10","location":1,"locationId":null,"maxPoint":30,"iconUrl":"http://jipincheng.cn/category/img/20200630/7377904df0cb4a868f2fa8b8dd8f65b5","buttonName":"去购买","allCount":3,"todayCount":0,"status":0,"isshow":1},{"id":"ebe15672d7b9405bb7adfbf262a526d0"," f27 type":23,"point":2,"title":"搜索商品(0/4)","content":"每次极币+2","location":10,"locationId":null,"maxPoint":8,"iconUrl":"http://jipincheng.cn/category/img/20200630/90b9cec44c3a4066b1766639b2f2009b","buttonName":"去搜索","allCount":4,"todayCount":0,"status":0,"isshow":1},{"id":"fa99dff0b65248519156f2d2fde3b29e","type":20,"point":5,"title":"分享商品(0/2)","content":"每次极币+5","location":1,"locationId":null,"maxPoint":10,"iconUrl":"http://jipincheng.cn/category/img/20200630/eed833fb2a8b4238b7a6c1df73c1fce0","buttonName":"去分享","allCount":2,"todayCount":0,"status":0,"isshow":1}]
             */

            private String title1;
            private String title2;
            private String title3;
            private List<ListBeanXX> list;

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

            public List<ListBeanXX> getList() {
                return list;
            }

            public void setList(List<ListBeanXX> list) {
                this.list = list;
            }

            public static class ListBeanXX {

                private String title;
                private String content;
                private String levelContent;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public String getLevelContent() {
                    return levelContent;
                }

                public void setLevelContent(String levelContent) {
                    this.levelContent = levelContent;
                }
            }
        }

        public static class LevelDetail7Bean {
            /**
             * title1 : 视频会员月卡
             * title2 : 四大视频会员任选其一
             * title3 : 会员福利
             * title4 : 年卡
             * status : 0
             * list : [{"id":"2a545d657fe84cf09c8b609e530f496a","iconUrl":"http://jipincheng.cn/category/img/20200910/ca77fc6654cc43c5b989316b9bd39917","title":"腾讯视频","orderNum":1,"status":1,"type":0,"targetId":null,"source":"0"},{"id":"6eafe7d2010e4cb8a060a54c6c157239","iconUrl":"http://jipincheng.cn/category/img/20200910/cb077c7a3cff413dafecf65f81df5d84","title":"爱奇异","orderNum":2,"status":1,"type":0,"targetId":null,"source":"0"},{"id":"080f6f1130124a488334efe0a17d0c47","iconUrl":"http://jipincheng.cn/category/img/20200910/93ce27ca559340a391f089ffd2f0ec52","title":"优酷视频","orderNum":3,"status":1,"type":0,"targetId":null,"source":"0"},{"id":"a21a7b430d8c4861b38f5adf94fda62b","iconUrl":"http://jipincheng.cn/category/img/20200910/430fb2ecd1284fe6a139342cf48e1c48","title":"芒果TV","orderNum":4,"status":1,"type":0,"targetId":null,"source":"0"}]
             */

            private String title1;
            private String title2;
            private String title3;
            private String title4;
            private int status;
            private List<ListBeanXXX> list;

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

            public String getTitle4() {
                return title4;
            }

            public void setTitle4(String title4) {
                this.title4 = title4;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public List<ListBeanXXX> getList() {
                return list;
            }

            public void setList(List<ListBeanXXX> list) {
                this.list = list;
            }

            public static class ListBeanXXX {
                /**
                 * id : 2a545d657fe84cf09c8b609e530f496a
                 * iconUrl : http://jipincheng.cn/category/img/20200910/ca77fc6654cc43c5b989316b9bd39917
                 * title : 腾讯视频
                 * orderNum : 1
                 * status : 1
                 * type : 0
                 * targetId : null
                 * source : 0
                 */

                private String id;
                private String iconUrl;
                private String title;
                private String orderNum;
                private String status;
                private String type;
                private String targetId;
                private String source;
                private String subTitle;

                public String getSubTitle() {
                    return subTitle;
                }

                public void setSubTitle(String subTitle) {
                    this.subTitle = subTitle;
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
            }
        }

        public static class LevelDetail8Bean {
            /**
             * title1 : 吃喝玩乐
             * title2 : 生活周边 样样省钱
             * title3 : null
             * title4 : null
             * status : 0
             * list : [{"id":"121","iconUrl":"http://jipincheng.cn/category/img/20200910/ca77fc6654cc43c5b989316b9bd39917","title":"腾讯视频","orderNum":1,"status":1,"type":0,"targetId":null,"source":"0"},{"id":"3123","iconUrl":"http://jipincheng.cn/category/img/20200910/cb077c7a3cff413dafecf65f81df5d84","title":"爱奇异","orderNum":2,"status":1,"type":0,"targetId":null,"source":"0"},{"id":"111111","iconUrl":"http://jipincheng.cn/category/img/20200910/93ce27ca559340a391f089ffd2f0ec52","title":"优酷视频","orderNum":3,"status":1,"type":0,"targetId":null,"source":"0"},{"id":"4234","iconUrl":"http://jipincheng.cn/category/img/20200910/430fb2ecd1284fe6a139342cf48e1c48","title":"芒果TV","orderNum":4,"status":1,"type":0,"targetId":null,"source":"0"}]
             */

            private String title1;
            private String title2;
            private String title3;
            private String title4;
            private int status;
            private List<LevelDetail7Bean.ListBeanXXX> list;

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

            public String getTitle4() {
                return title4;
            }

            public void setTitle4(String title4) {
                this.title4 = title4;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public List<LevelDetail7Bean.ListBeanXXX> getList() {
                return list;
            }

            public void setList(List<LevelDetail7Bean.ListBeanXXX> list) {
                this.list = list;
            }

        }

        public static class MessageListBean {
            /**
             * id : 222
             * avatar : http://jipincheng.cn/avatar/img/20191223/ba4eecf20e6f43b3b85acc30cf71be7c
             * content : 果果子刚刚成为极品城vip
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
