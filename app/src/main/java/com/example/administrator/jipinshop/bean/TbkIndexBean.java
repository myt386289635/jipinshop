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

        private HotActivityBean hotActivity;
        private Ad2Bean ad2;
        private List<Ad1ListBean> ad1List;
        private List<BoxListBean> boxList;
        private List<MessageListBean> messageList;
        private List<FreeGoodsListBean> freeGoodsList;
        private List<ActivityListBean> activityList;
        private List<HotGoodsListBean> hotGoodsList;

        public HotActivityBean getHotActivity() {
            return hotActivity;
        }

        public void setHotActivity(HotActivityBean hotActivity) {
            this.hotActivity = hotActivity;
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

        public List<BoxListBean> getBoxList() {
            return boxList;
        }

        public void setBoxList(List<BoxListBean> boxList) {
            this.boxList = boxList;
        }

        public List<MessageListBean> getMessageList() {
            return messageList;
        }

        public void setMessageList(List<MessageListBean> messageList) {
            this.messageList = messageList;
        }

        public List<FreeGoodsListBean> getFreeGoodsList() {
            return freeGoodsList;
        }

        public void setFreeGoodsList(List<FreeGoodsListBean> freeGoodsList) {
            this.freeGoodsList = freeGoodsList;
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

        public static class HotActivityBean {
            /**
             * id : e3c30532597a4d3e938ace85a3620c7d
             * title : 每日爆款
             * smallTitle : 1元抢购
             * img1 :
             * img2 :
             * type : 1
             * targetId : 1
             * status : 1
             * goodsList1 : [{"goodsType":1,"img":"https://img.alicdn.com/bao/uploaded/i1/2167235472/O1CN01bcsBza1qIDqhL7Thr_!!0-item_pic.jpg","actualPrice":"679.00","couponPrice":0,"otherGoodsId":"525734921657","otherName":"飞利浦电动剃须刀充电式刮胡刀S5000胡须刀多功能男士洁面器S5095","otherPrice":"679.00","shopName":"philips飞浦专卖店","volume":8,"commissionRate":2.1,"fee":"7.13","buyPrice":"671.88","buyRate":"9.9"},{"goodsType":1,"img":"https://img.alicdn.com/bao/uploaded/i1/441622457/O1CN01ONHPWu1U1LiBuIl7F_!!0-item_pic.jpg","actualPrice":"150.00","couponPrice":30,"otherGoodsId":"25297616066","otherName":"欧诗漫珍珠白淡斑赋活霜美白面霜补水保湿滋润面部精华护肤品国货","otherPrice":"180.00","shopName":"欧诗漫旗舰店","volume":275,"commissionRate":4,"fee":"3.00","buyPrice":"147.00","buyRate":"8.2"},{"goodsType":1,"img":"https://img.alicdn.com/bao/uploaded/i4/2956213582/O1CN01aB6mdp1cKbDJEsLAt_!!0-item_pic.jpg","actualPrice":"59.00","couponPrice":0,"otherGoodsId":"565132953791","otherName":"苏泊尔儿童小学生水杯子男女孩夏天防耐摔便携简约小巧可爱塑料杯","otherPrice":"59.00","shopName":"苏泊尔畅卓专卖店","volume":36,"commissionRate":3,"fee":"0.89","buyPrice":"58.12","buyRate":"9.9"},{"goodsType":1,"img":"https://img.alicdn.com/bao/uploaded/i4/740529586/O1CN01a5CMcS2KgRGIruwRN_!!0-item_pic.jpg","actualPrice":"59.00","couponPrice":50,"otherGoodsId":"12476497339","otherName":"飞科剃须刀电动智能刮胡刀男士充电式正品全身水洗正品刮胡子刀头","otherPrice":"109.00","shopName":"豪翔电器专营店","volume":1566,"commissionRate":30,"fee":"8.85","buyPrice":"50.15","buyRate":"4.7"},{"goodsType":1,"img":"https://img.alicdn.com/bao/uploaded/i3/1069640918/O1CN01ll46Lh1IeUAKFVafE_!!0-item_pic.jpg","actualPrice":"54.90","couponPrice":5,"otherGoodsId":"555119829089","otherName":"小熊加湿器家用静音大容量卧室孕妇婴儿室内空气香薰净化喷雾小型","otherPrice":"59.90","shopName":"小熊哥登专卖店","volume":262978,"commissionRate":20.01,"fee":"5.50","buyPrice":"49.41","buyRate":"8.3"}]
             * goodsList2 : [{"goodsType":1,"img":"https://img.alicdn.com/bao/uploaded/i2/1645598135/O1CN01PgLOSR29xsZX9mYmB_!!0-item_pic.jpg","actualPrice":"428.00","couponPrice":0,"otherGoodsId":"568651473452","otherName":"科德士980电推子理发器电推剪充电式电动剃头刀剪头发廊专业专用","otherPrice":"428.00","shopName":"codos科德士旗舰店","volume":60,"commissionRate":2,"fee":"4.28","buyPrice":"423.72","buyRate":"9.9"},{"goodsType":1,"img":"https://img.alicdn.com/tfscom/i2/729434847/O1CN019wKd3B1lfyEC4bYUq_!!0-item_pic.jpg","actualPrice":"899.00","couponPrice":0,"otherGoodsId":"576171631200","otherName":"飞利浦剃须刀sw5700星球大战充电三刀头刮胡刀剃须刀电动全身水洗","otherPrice":"899.00","shopName":"飞利浦新顶华专卖店","volume":4,"commissionRate":1.5,"fee":"6.75","buyPrice":"892.26","buyRate":"10.0"},{"goodsType":1,"img":"https://img.alicdn.com/bao/uploaded/i1/1770547036/O1CN01tMex7r21qXIMzPBgT_!!1770547036-0-pixelsss.jpg","actualPrice":"149.00","couponPrice":20,"otherGoodsId":"551248082062","otherName":"美的大容量电火锅家用多功能电炒蒸煮热菜锅插电一体锅2-3-4-6人","otherPrice":"169.00","shopName":"美的骏驰专卖店","volume":11,"commissionRate":1.8,"fee":"1.35","buyPrice":"147.66","buyRate":"8.8"},{"goodsType":1,"img":"https://img.alicdn.com/bao/uploaded/i1/3002204399/O1CN01bzxiV91iMms6FGbXk_!!3002204399-0-pixelsss.jpg","actualPrice":"178.00","couponPrice":0,"otherGoodsId":"566827913248","otherName":"小米米家电动剃须刀男三头刀头防水充电便携式迷你S500刮胡刀正品","otherPrice":"178.00","shopName":"小米征诚创新专卖店","volume":103,"commissionRate":1.9,"fee":"1.70","buyPrice":"176.31","buyRate":"10.0"},{"goodsType":1,"img":"https://img.alicdn.com/bao/uploaded/i4/2823345492/O1CN01tiy50Y1qRNlrHsZzU_!!0-item_pic.jpg","actualPrice":"96.00","couponPrice":15,"otherGoodsId":"566732941046","otherName":"美宝莲fit me粉底液女fitme遮瑕保湿控油皮李佳琦推荐官方旗舰店","otherPrice":"111.00","shopName":"美宝莲美洁金信专卖店","volume":7620,"commissionRate":4,"fee":"1.92","buyPrice":"94.08","buyRate":"8.5"}]
             */

            private String id;
            private String title;
            private String smallTitle;
            private String img1;
            private String img2;
            private String type;
            private String targetId;
            private int status;
            private List<GoodsList1Bean> goodsList1;
            private List<GoodsList2Bean> goodsList2;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSmallTitle() {
                return smallTitle;
            }

            public void setSmallTitle(String smallTitle) {
                this.smallTitle = smallTitle;
            }

            public String getImg1() {
                return img1;
            }

            public void setImg1(String img1) {
                this.img1 = img1;
            }

            public String getImg2() {
                return img2;
            }

            public void setImg2(String img2) {
                this.img2 = img2;
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

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public List<GoodsList1Bean> getGoodsList1() {
                return goodsList1;
            }

            public void setGoodsList1(List<GoodsList1Bean> goodsList1) {
                this.goodsList1 = goodsList1;
            }

            public List<GoodsList2Bean> getGoodsList2() {
                return goodsList2;
            }

            public void setGoodsList2(List<GoodsList2Bean> goodsList2) {
                this.goodsList2 = goodsList2;
            }

            public static class GoodsList1Bean {
                /**
                 * goodsType : 1
                 * img : https://img.alicdn.com/bao/uploaded/i1/2167235472/O1CN01bcsBza1qIDqhL7Thr_!!0-item_pic.jpg
                 * actualPrice : 679.00
                 * couponPrice : 0
                 * otherGoodsId : 525734921657
                 * otherName : 飞利浦电动剃须刀充电式刮胡刀S5000胡须刀多功能男士洁面器S5095
                 * otherPrice : 679.00
                 * shopName : philips飞浦专卖店
                 * volume : 8
                 * commissionRate : 2.1
                 * fee : 7.13
                 * buyPrice : 671.88
                 * buyRate : 9.9
                 */

                private int goodsType;
                private String img;
                private String actualPrice;
                private String couponPrice;
                private String otherGoodsId;
                private String otherName;
                private String otherPrice;
                private String shopName;
                private int volume;
                private String commissionRate;
                private String fee;
                private String buyPrice;
                private String buyRate;

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

                public int getVolume() {
                    return volume;
                }

                public void setVolume(int volume) {
                    this.volume = volume;
                }

                public String getCommissionRate() {
                    return commissionRate;
                }

                public void setCommissionRate(String commissionRate) {
                    this.commissionRate = commissionRate;
                }

                public String getFee() {
                    return fee;
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

            public static class GoodsList2Bean {
                /**
                 * goodsType : 1
                 * img : https://img.alicdn.com/bao/uploaded/i2/1645598135/O1CN01PgLOSR29xsZX9mYmB_!!0-item_pic.jpg
                 * actualPrice : 428.00
                 * couponPrice : 0
                 * otherGoodsId : 568651473452
                 * otherName : 科德士980电推子理发器电推剪充电式电动剃头刀剪头发廊专业专用
                 * otherPrice : 428.00
                 * shopName : codos科德士旗舰店
                 * volume : 60
                 * commissionRate : 2
                 * fee : 4.28
                 * buyPrice : 423.72
                 * buyRate : 9.9
                 */

                private int goodsType;
                private String img;
                private String actualPrice;
                private String couponPrice;
                private String otherGoodsId;
                private String otherName;
                private String otherPrice;
                private String shopName;
                private int volume;
                private String commissionRate;
                private String fee;
                private String buyPrice;
                private String buyRate;

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

                public int getVolume() {
                    return volume;
                }

                public void setVolume(int volume) {
                    this.volume = volume;
                }

                public String getCommissionRate() {
                    return commissionRate;
                }

                public void setCommissionRate(String commissionRate) {
                    this.commissionRate = commissionRate;
                }

                public String getFee() {
                    return fee;
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

        public static class BoxListBean {
            /**
             * id : 1b06bce3023042d4a96f67b99a81fe1e
             * iconUrl : http://jipincheng.cn/category/img/20191214/eb48cffce5bc4a0e93338421a10ea2a4
             * title : 品质大牌
             * orderNum : 1
             * status : 1
             * type : 1
             * targetId : de9657d51a3a4653925fff405d96deb9
             */

            private String id;
            private String iconUrl;
            private String title;
            private int orderNum;
            private int status;
            private String type;
            private String targetId;

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

        public static class FreeGoodsListBean {

            private String id;
            private String name;
            private String img;
            private String imgs;
            private String goodsId;
            private String goodsContent;
            private int count;
            private int status;
            private String confirmTime;
            private String freePrice;
            private String freeRate;
            private String freeNote;
            private String shareTitle;
            private String shareContent;
            private String shareUrl;
            private String shareImg;
            private String createTime;
            private int inviteUserCount;
            private int type;
            private int virtualCount;
            private String otherPrice;
            private String myInviteUserCount;
            private String actualPrice;
            private String buyPrice;
            private String imgList;
            private String goodsContentList;
            private String applyed;
            private String applyStatus;
            private String dendlineTime;
            private String shopImg;
            private String shopName;
            private int userCount;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public String getImgs() {
                return imgs;
            }

            public void setImgs(String imgs) {
                this.imgs = imgs;
            }

            public String getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(String goodsId) {
                this.goodsId = goodsId;
            }

            public String getGoodsContent() {
                return goodsContent;
            }

            public void setGoodsContent(String goodsContent) {
                this.goodsContent = goodsContent;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getConfirmTime() {
                return confirmTime;
            }

            public void setConfirmTime(String confirmTime) {
                this.confirmTime = confirmTime;
            }

            public String getFreePrice() {
                return freePrice;
            }

            public void setFreePrice(String freePrice) {
                this.freePrice = freePrice;
            }

            public String getFreeRate() {
                return freeRate;
            }

            public void setFreeRate(String freeRate) {
                this.freeRate = freeRate;
            }

            public String getFreeNote() {
                return freeNote;
            }

            public void setFreeNote(String freeNote) {
                this.freeNote = freeNote;
            }

            public String getShareTitle() {
                return shareTitle;
            }

            public void setShareTitle(String shareTitle) {
                this.shareTitle = shareTitle;
            }

            public String getShareContent() {
                return shareContent;
            }

            public void setShareContent(String shareContent) {
                this.shareContent = shareContent;
            }

            public String getShareUrl() {
                return shareUrl;
            }

            public void setShareUrl(String shareUrl) {
                this.shareUrl = shareUrl;
            }

            public String getShareImg() {
                return shareImg;
            }

            public void setShareImg(String shareImg) {
                this.shareImg = shareImg;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getInviteUserCount() {
                return inviteUserCount;
            }

            public void setInviteUserCount(int inviteUserCount) {
                this.inviteUserCount = inviteUserCount;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getVirtualCount() {
                return virtualCount;
            }

            public void setVirtualCount(int virtualCount) {
                this.virtualCount = virtualCount;
            }

            public String getOtherPrice() {
                return new BigDecimal(otherPrice.trim()).stripTrailingZeros().toPlainString();
            }

            public void setOtherPrice(String otherPrice) {
                this.otherPrice = otherPrice;
            }

            public String getMyInviteUserCount() {
                return myInviteUserCount;
            }

            public void setMyInviteUserCount(String myInviteUserCount) {
                this.myInviteUserCount = myInviteUserCount;
            }

            public String getActualPrice() {
                return actualPrice;
            }

            public void setActualPrice(String actualPrice) {
                this.actualPrice = actualPrice;
            }

            public String getBuyPrice() {
                return  new BigDecimal(buyPrice.trim()).stripTrailingZeros().toPlainString();
            }

            public void setBuyPrice(String buyPrice) {
                this.buyPrice = buyPrice;
            }

            public String getImgList() {
                return imgList;
            }

            public void setImgList(String imgList) {
                this.imgList = imgList;
            }

            public String getGoodsContentList() {
                return goodsContentList;
            }

            public void setGoodsContentList(String goodsContentList) {
                this.goodsContentList = goodsContentList;
            }

            public String getApplyed() {
                return applyed;
            }

            public void setApplyed(String applyed) {
                this.applyed = applyed;
            }

            public String getApplyStatus() {
                return applyStatus;
            }

            public void setApplyStatus(String applyStatus) {
                this.applyStatus = applyStatus;
            }

            public String getDendlineTime() {
                return dendlineTime;
            }

            public void setDendlineTime(String dendlineTime) {
                this.dendlineTime = dendlineTime;
            }

            public String getShopImg() {
                return shopImg;
            }

            public void setShopImg(String shopImg) {
                this.shopImg = shopImg;
            }

            public String getShopName() {
                return shopName;
            }

            public void setShopName(String shopName) {
                this.shopName = shopName;
            }

            public int getUserCount() {
                return userCount;
            }

            public void setUserCount(int userCount) {
                this.userCount = userCount;
            }
        }

        public static class ActivityListBean {
            /**
             * id : a00e2b88baba48219e9756154fbf1511
             * title : 高返专区
             * smallTitle : 人气零食
             * img1 : https://img.alicdn.com/bao/uploaded/TB1UhusouH2gK0jSZFEXXcqMpXa.png
             * img2 : https://img.alicdn.com/bao/uploaded/TB2PtRoxBjTBKNjSZFuXXb0HFXa_!!2891835436.jpg
             * type : 1
             * targetId : 688d6708b8b64816ae52713c4f291b71
             * status : 1
             * goodsList1 : null
             * goodsList2 : null
             */

            private String id;
            private String title;
            private String smallTitle;
            private String img1;
            private String img2;
            private String type;
            private String targetId;
            private int status;
            private String goodsList1;
            private String goodsList2;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSmallTitle() {
                return smallTitle;
            }

            public void setSmallTitle(String smallTitle) {
                this.smallTitle = smallTitle;
            }

            public String getImg1() {
                return img1;
            }

            public void setImg1(String img1) {
                this.img1 = img1;
            }

            public String getImg2() {
                return img2;
            }

            public void setImg2(String img2) {
                this.img2 = img2;
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

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getGoodsList1() {
                return goodsList1;
            }

            public void setGoodsList1(String goodsList1) {
                this.goodsList1 = goodsList1;
            }

            public String getGoodsList2() {
                return goodsList2;
            }

            public void setGoodsList2(String goodsList2) {
                this.goodsList2 = goodsList2;
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
            private int volume;
            private String commissionRate;
            private String fee;
            private String buyPrice;
            private String buyRate;

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

            public int getVolume() {
                return volume;
            }

            public void setVolume(int volume) {
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
    }
}
