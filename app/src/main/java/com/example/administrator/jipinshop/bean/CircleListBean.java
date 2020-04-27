package com.example.administrator.jipinshop.bean;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/3/18
 * @Describe
 */
public class CircleListBean {

    private String msg;
    private int code;
    private List<DataBean> data;

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

    public static class DataBean {

        private String id;
        private String categoryId;
        private String categoryPid;
        private String shareNumber;
        private String createUserId;
        private String userAvatar;
        private String userNickname;
        private String content;
        private String imgs;
        private String goodsId;
        private String createTime;
        private GoodsInfoBean goodsInfo;
        private List<String> imgList;
        private List<CommentListBean> commentList;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryPid() {
            return categoryPid;
        }

        public void setCategoryPid(String categoryPid) {
            this.categoryPid = categoryPid;
        }

        public String getShareNumber() {
            return shareNumber;
        }

        public void setShareNumber(String shareNumber) {
            this.shareNumber = shareNumber;
        }

        public String getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(String createUserId) {
            this.createUserId = createUserId;
        }

        public String getUserAvatar() {
            return userAvatar;
        }

        public void setUserAvatar(String userAvatar) {
            this.userAvatar = userAvatar;
        }

        public String getUserNickname() {
            return userNickname;
        }

        public void setUserNickname(String userNickname) {
            this.userNickname = userNickname;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public GoodsInfoBean getGoodsInfo() {
            return goodsInfo;
        }

        public void setGoodsInfo(GoodsInfoBean goodsInfo) {
            this.goodsInfo = goodsInfo;
        }

        public List<String> getImgList() {
            return imgList;
        }

        public void setImgList(List<String> imgList) {
            this.imgList = imgList;
        }

        public List<CommentListBean> getCommentList() {
            return commentList;
        }

        public void setCommentList(List<CommentListBean> commentList) {
            this.commentList = commentList;
        }

        public static class GoodsInfoBean {
            /**
             * goodsId : a3dcf1e798be41739d11b56b1e34d6e4
             * img : https://img.alicdn.com/tfscom/i2/729434847/O1CN019wKd3B1lfyEC4bYUq_!!0-item_pic.jpg
             * actualPrice : 899
             * couponPrice : 0
             * otherGoodsId : 576171631200
             * otherName : 飞利浦剃须刀sw5700星球大战充电三刀头刮胡刀剃须刀电动全身水洗
             * otherPrice : 899
             * commissionRate : 1.5
             * goodsBuyLink : https://s.click.taobao.com/t?e=m%3D2%26s%3DwDRTO0ltfm5w4vFB6t2Z2ueEDrYVVa64r4ll3HtqqoxyINtkUhsv0DECqh2TzKiQ1sWNMVtsBvkANk0wxpU4xZjo0s17wAXd6cQzJwaO0H%2Ben7ha7nK6n9i9fnTjlrICSBaygToy7XlhNVYLXVHHtixFbahNh6EF%2FVJqtNeEg5ML8zORzELl0N0DKxWmxtxOXxxwCVxqD4kOx67xgdf7uZc7grTjjuW4omfkDJRs%2BhU%3D&scm=null&pvid=100_11.179.221.80_35445_7161583806939639297&app_pvid=59590_11.88.143.70_774_1583806939635&ptl=floorId:2836;originalFloorId:2836;pvid:100_11.179.221.80_35445_7161583806939639297;app_pvid:59590_11.88.143.70_774_1583806939635&xId=1z0jLNjWqicLbE3A0QhIOVBjPezO78561CfCpl36r9tAPTPzEsnjEtkqwOY1jIwerww2ftE836Tr8cTrBcJmeUJiXwBtJlAp8ReTObpXMYam&union_lens=lensId%3AMAPI%401583806939%400b588f46_0e5f_170c2400203_0ceb%4001
             * fee : 809.1
             */

            private String goodsId;
            private String img;
            private String actualPrice;
            private String couponPrice;
            private String otherGoodsId;
            private String otherName;
            private String otherPrice;
            private String commissionRate;
            private String goodsBuyLink;
            private String fee;
            private String source;

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(String goodsId) {
                this.goodsId = goodsId;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
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

            public String getCommissionRate() {
                return commissionRate;
            }

            public void setCommissionRate(String commissionRate) {
                this.commissionRate = commissionRate;
            }

            public String getGoodsBuyLink() {
                return goodsBuyLink;
            }

            public void setGoodsBuyLink(String goodsBuyLink) {
                this.goodsBuyLink = goodsBuyLink;
            }

            public String getFee() {
                return new BigDecimal(fee).stripTrailingZeros().toPlainString();
            }

            public void setFee(String fee) {
                this.fee = fee;
            }
        }

        public static class CommentListBean{

            private String content;
            private String copyContent;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getCopyContent() {
                return copyContent;
            }

            public void setCopyContent(String copyContent) {
                this.copyContent = copyContent;
            }
        }
    }
}
