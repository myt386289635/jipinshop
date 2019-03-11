package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/3/11
 * @Describe
 */
public class MyOrderBean {

    /**
     * msg : success
     * code : 0
     * data : [{"id":"1","ordersn":"100001","goodsType":1,"point":1,"status":1,"createTime":"2019-03-11 09:20:09","payType":1,"payTime":"2019-03-08 16:20:27","address":"1","expresscom":"顺丰","expresssn":"10001","sendTime":null,"finishTime":"2019-03-08 16:20:15","remark":"2212","total":1,"goodsName":"牙刷","img":"http://jipincheng.cn/0809381537a049eaa226d6b41ca35e3e","price":1},{"id":"1","ordersn":"100001","goodsType":1,"point":1,"status":1,"createTime":"2019-03-11 09:20:09","payType":1,"payTime":"2019-03-08 16:20:27","address":"1","expresscom":"顺丰","expresssn":"10001","sendTime":null,"finishTime":"2019-03-08 16:20:15","remark":"2212","total":3,"goodsName":"电动牙刷","img":"http://jipincheng.cn/0809381537a049eaa226d6b41ca35e3e","price":1}]
     */

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
        /**
         * id : 1
         * ordersn : 100001
         * goodsType : 1
         * point : 1
         * status : 1
         * createTime : 2019-03-11 09:20:09
         * payType : 1
         * payTime : 2019-03-08 16:20:27
         * address : 1
         * expresscom : 顺丰
         * expresssn : 10001
         * sendTime : null
         * finishTime : 2019-03-08 16:20:15
         * remark : 2212
         * total : 1
         * goodsName : 牙刷
         * img : http://jipincheng.cn/0809381537a049eaa226d6b41ca35e3e
         * price : 1
         */

        private String id;
        private String ordersn;
        private int goodsType;
        private String point;
        private int status;
        private String createTime;
        private int payType;
        private String payTime;
        private String address;
        private String expresscom;
        private String expresssn;
        private String sendTime;
        private String finishTime;
        private String remark;
        private String total;
        private String goodsName;
        private String img;
        private String price;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrdersn() {
            return ordersn;
        }

        public void setOrdersn(String ordersn) {
            this.ordersn = ordersn;
        }

        public int getGoodsType() {
            return goodsType;
        }

        public void setGoodsType(int goodsType) {
            this.goodsType = goodsType;
        }

        public String getPoint() {
            return point;
        }

        public void setPoint(String point) {
            this.point = point;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getExpresscom() {
            return expresscom;
        }

        public void setExpresscom(String expresscom) {
            this.expresscom = expresscom;
        }

        public String getExpresssn() {
            return expresssn;
        }

        public void setExpresssn(String expresssn) {
            this.expresssn = expresssn;
        }

        public String getSendTime() {
            return sendTime;
        }

        public void setSendTime(String sendTime) {
            this.sendTime = sendTime;
        }

        public String getFinishTime() {
            return finishTime;
        }

        public void setFinishTime(String finishTime) {
            this.finishTime = finishTime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
