package com.example.administrator.jipinshop.bean;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/1/3
 * @Describe
 */
public class NewPeopleBean {

    private String msg;
    private int code;
    private DataBean data;
    private String addPoint;

    public String getAddPoint() {
        return addPoint;
    }

    public void setAddPoint(String addPoint) {
        this.addPoint = addPoint;
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
        /**
         * newAllowanceGoodsList : [{"id":"1","goodsName":"新洁丽简易包装软抽纸巾 100抽高品质面巾纸2层原木浆一箱包邮","otherGoodsId":"16900058608","count":10,"validDay":10,"img":"https://img.alicdn.com/bao/uploaded/i3/13416030965327878/T1B22GFhNXXXXXXXXX_!!0-item_pic.jpg","useAllowancePrice":1,"type":1,"status":1,"otherPrice":2,"buyPrice":null}]
         * allowanceGoodsList : [{"id":"1","goodsName":"新洁丽简易包装软抽纸巾 100抽高品质面巾纸2层原木浆一箱包邮","otherGoodsId":"16900058608","count":10,"validDay":10,"img":"https://img.alicdn.com/bao/uploaded/i3/13416030965327878/T1B22GFhNXXXXXXXXX_!!0-item_pic.jpg","useAllowancePrice":1,"type":1,"status":1,"otherPrice":2,"buyPrice":null}]
         * allowance : 0
         * officialWeChat : jpkele
         */

        private String totalUsedAllowance;
        private String allowance;
        private String officialWeChat;
        private List<NewAllowanceGoodsListBean> newAllowanceGoodsList;
        private List<AllowanceGoodsListBean> allowanceGoodsList;
        private String tmpAllowance;
        private long endTime;

        public String getTmpAllowance() {
            return new BigDecimal(tmpAllowance).stripTrailingZeros().toPlainString();
        }

        public void setTmpAllowance(String tmpAllowance) {
            this.tmpAllowance = tmpAllowance;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public String getTotalUsedAllowance() {
            return totalUsedAllowance;
        }

        public void setTotalUsedAllowance(String totalUsedAllowance) {
            this.totalUsedAllowance = totalUsedAllowance;
        }

        public String getAllowance() {
            return new BigDecimal(allowance).stripTrailingZeros().toPlainString();
        }

        public void setAllowance(String allowance) {
            this.allowance = allowance;
        }

        public String getOfficialWeChat() {
            return officialWeChat;
        }

        public void setOfficialWeChat(String officialWeChat) {
            this.officialWeChat = officialWeChat;
        }

        public List<NewAllowanceGoodsListBean> getNewAllowanceGoodsList() {
            return newAllowanceGoodsList;
        }

        public void setNewAllowanceGoodsList(List<NewAllowanceGoodsListBean> newAllowanceGoodsList) {
            this.newAllowanceGoodsList = newAllowanceGoodsList;
        }

        public List<AllowanceGoodsListBean> getAllowanceGoodsList() {
            return allowanceGoodsList;
        }

        public void setAllowanceGoodsList(List<AllowanceGoodsListBean> allowanceGoodsList) {
            this.allowanceGoodsList = allowanceGoodsList;
        }

        public static class NewAllowanceGoodsListBean {
            /**
             * id : 1
             * goodsName : 新洁丽简易包装软抽纸巾 100抽高品质面巾纸2层原木浆一箱包邮
             * otherGoodsId : 16900058608
             * count : 10
             * validDay : 10
             * img : https://img.alicdn.com/bao/uploaded/i3/13416030965327878/T1B22GFhNXXXXXXXXX_!!0-item_pic.jpg
             * useAllowancePrice : 1
             * type : 1
             * status : 1
             * otherPrice : 2
             * buyPrice : null
             */

            private String id;
            private String goodsName;
            private String otherGoodsId;
            private int count;
            private int validDay;
            private String img;
            private String useAllowancePrice;
            private int type;
            private int status;
            private String otherPrice;
            private String buyPrice;
            private int total;

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

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

            public int getValidDay() {
                return validDay;
            }

            public void setValidDay(int validDay) {
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

            public String getBuyPrice() {
                return buyPrice;
            }

            public void setBuyPrice(String buyPrice) {
                this.buyPrice = buyPrice;
            }
        }

        public static class AllowanceGoodsListBean {
            /**
             * id : 1
             * goodsName : 新洁丽简易包装软抽纸巾 100抽高品质面巾纸2层原木浆一箱包邮
             * otherGoodsId : 16900058608
             * count : 10
             * validDay : 10
             * img : https://img.alicdn.com/bao/uploaded/i3/13416030965327878/T1B22GFhNXXXXXXXXX_!!0-item_pic.jpg
             * useAllowancePrice : 1
             * type : 1
             * status : 1
             * otherPrice : 2
             * buyPrice : null
             */

            private String id;
            private String goodsName;
            private String otherGoodsId;
            private int count;
            private int validDay;
            private String img;
            private String useAllowancePrice;//津贴补贴
            private int type;
            private int status;
            private String otherPrice;
            private String buyPrice;//到手价（补贴后）
            private String couponPrice;
            private String isBuy; //0 没买 1买过
            private String fee;//返现金额
            private String soldTotal;//销售数量

            public String getFee() {
                return fee;
            }

            public void setFee(String fee) {
                this.fee = fee;
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

            public String getCouponPrice() {
                return couponPrice;
            }

            public void setCouponPrice(String couponPrice) {
                this.couponPrice = couponPrice;
            }

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

            public int getValidDay() {
                return validDay;
            }

            public void setValidDay(int validDay) {
                this.validDay = validDay;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getUseAllowancePrice() {
                return new BigDecimal(useAllowancePrice).stripTrailingZeros().toPlainString();
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

            public String getBuyPrice() {
                return buyPrice;
            }

            public void setBuyPrice(String buyPrice) {
                this.buyPrice = buyPrice;
            }
        }
    }
}
