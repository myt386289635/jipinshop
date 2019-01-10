package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/11/15
 * @Describe 评测tab
 */
public class EvaluationTabBean {

    /**
     * msg : success
     * code : 0
     * data : [{"categoryId":"2","type":2,"categoryName":"精选","orderNum":0},{"categoryId":"da8076106388493fbf5e38dd8926f41d","type":2,"categoryName":"个护健康","orderNum":1},{"categoryId":"06a89c505c9e42f88ba11e3592f759ed","type":2,"categoryName":"厨房电器","orderNum":2},{"categoryId":"06d488224d5a4850a8c652a24eb5ef30","type":2,"categoryName":"生活电器","orderNum":3}]
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
         * categoryId : 2
         * type : 2
         * categoryName : 精选
         * orderNum : 0
         */

        private String categoryId;
        private int type;
        private String categoryName;
        private int orderNum;

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public int getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(int orderNum) {
            this.orderNum = orderNum;
        }
    }
}
