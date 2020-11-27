package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/1/6
 * @Describe
 */
public class PopBean {

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

        private String type;
        private String popId;
        private String addAllowancePrice;
        private List<AllowanceGoodsListBean> allowanceGoodsList;
        private GroupInfoBean.DataBean groupGoods;

        public GroupInfoBean.DataBean getGroupGoods() {
            return groupGoods;
        }

        public void setGroupGoods(GroupInfoBean.DataBean groupGoods) {
            this.groupGoods = groupGoods;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPopId() {
            return popId;
        }

        public void setPopId(String popId) {
            this.popId = popId;
        }

        public String getAddAllowancePrice() {
            return addAllowancePrice;
        }

        public void setAddAllowancePrice(String addAllowancePrice) {
            this.addAllowancePrice = addAllowancePrice;
        }

        public List<AllowanceGoodsListBean> getAllowanceGoodsList() {
            return allowanceGoodsList;
        }

        public void setAllowanceGoodsList(List<AllowanceGoodsListBean> allowanceGoodsList) {
            this.allowanceGoodsList = allowanceGoodsList;
        }

        public static class AllowanceGoodsListBean {
            /**
             * id : 3
             * goodsName : 3新洁丽简易包装软抽纸巾 100抽高品质面巾纸2层原木浆一箱包邮
             * otherGoodsId : 16900058608
             * count : 98
             * validDay : 1
             * img : https://img.alicdn.com/bao/uploaded/i3/13416030965327878/T1B22GFhNXXXXXXXXX_!!0-item_pic.jpg
             * useAllowancePrice : 1.0
             * type : 2
             * status : 1
             * otherPrice : 2.0
             * actualPrice : 2.0
             * couponPrice : 0
             * buyPrice : null
             */

            private String id;
            private String goodsName;
            private String otherGoodsId;
            private int count;
            private String validDay;
            private String img;
            private String useAllowancePrice;
            private int type;
            private int status;
            private String otherPrice;
            private String actualPrice;
            private String couponPrice;
            private String buyPrice;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getOtherGoodsId() {
                return otherGoodsId;
            }

            public void setOtherGoodsId(String otherGoodsId) {
                this.otherGoodsId = otherGoodsId;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getValidDay() {
                return validDay;
            }

            public void setValidDay(String validDay) {
                this.validDay = validDay;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getUseAllowancePrice() {
                return useAllowancePrice;
            }

            public void setUseAllowancePrice(String useAllowancePrice) {
                this.useAllowancePrice = useAllowancePrice;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getOtherPrice() {
                return otherPrice;
            }

            public void setOtherPrice(String otherPrice) {
                this.otherPrice = otherPrice;
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

            public String getBuyPrice() {
                return buyPrice;
            }

            public void setBuyPrice(String buyPrice) {
                this.buyPrice = buyPrice;
            }
        }
    }
}
