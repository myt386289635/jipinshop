package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/2/21
 * @Describe
 */
public class OrderbyTypeBean {

    /**
     * msg : success
     * code : 0
     * data : [{"orderbyCategoryId":"95bbfe34835440c88d6a644becd7eda2","name":"综合榜","img":"","orderbyType":0,"orderNum":0,"status":0},{"orderbyCategoryId":"2299a52b63af4ac999653fbf0bed94f8","name":"热卖榜","img":"http://jipincheng.cn/category/img/20190220/51d0a3be131b41ea85a83208c46b9bec","orderbyType":1,"orderNum":1,"status":1},{"orderbyCategoryId":"f819f8722eca458da1f6ee9c2fb293e2","name":"轻奢榜","img":"http://jipincheng.cn/category/img/20190220/1f4804c04f514deb9dca341a75f4ef3b","orderbyType":2,"orderNum":2,"status":1},{"orderbyCategoryId":"48d6da6463fe4f579225ff989934d9e3","name":"新品榜","img":"http://jipincheng.cn/category/img/20190220/85532bb522af499c95cf84940b88900a","orderbyType":3,"orderNum":3,"status":1},{"orderbyCategoryId":"35fbdf8d4ea44d74a8b3cb421772f04a","name":"性价比榜","img":"http://jipincheng.cn/category/img/20190220/1eb52fb6d21a42988803b4ee0bea546c","orderbyType":4,"orderNum":4,"status":1}]
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
         * orderbyCategoryId : 95bbfe34835440c88d6a644becd7eda2
         * name : 综合榜
         * img :
         * orderbyType : 0
         * orderNum : 0
         * status : 0
         */

        private String orderbyCategoryId;
        private String name;
        private String img;
        private int orderbyType;
        private int orderNum;
        private int status;

        public String getOrderbyCategoryId() {
            return orderbyCategoryId;
        }

        public void setOrderbyCategoryId(String orderbyCategoryId) {
            this.orderbyCategoryId = orderbyCategoryId;
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

        public int getOrderbyType() {
            return orderbyType;
        }

        public void setOrderbyType(int orderbyType) {
            this.orderbyType = orderbyType;
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
    }
}
