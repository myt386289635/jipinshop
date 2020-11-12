package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/12/17
 * @Describe
 */
public class TbCommonBean {

    private String msg;
    private int code;
    private List<TBSreachResultBean.DataBean> data;
    private List<CategoryListBean> categoryList;
    private List<AdListBean> adList;

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

    public List<TBSreachResultBean.DataBean> getData() {
        return data;
    }

    public void setData(List<TBSreachResultBean.DataBean> data) {
        this.data = data;
    }

    public List<CategoryListBean> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryListBean> categoryList) {
        this.categoryList = categoryList;
    }

    public List<AdListBean> getAdList() {
        return adList;
    }

    public void setAdList(List<AdListBean> adList) {
        this.adList = adList;
    }

    public static class CategoryListBean {
        /**
         * categoryId : 40d4e725f4644002a452866ce33e63b0
         * parentId : 12adcd21cfbb4958851198a7a98c22ae
         * categoryName : 电动牙刷
         * img : http://jipincheng.cn/category/img/20191216/2229c49bb7d34789a03521cbca8f0cb8
         * orderNum : 1
         * status : 1
         * type : 10
         */

        private String categoryId;
        private String parentId;
        private String categoryName;
        private String img;
        private int orderNum;
        private int status;
        private int type;

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
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
    }

    public static class AdListBean {
        /**
         * img : http://jipincheng.cn/activity/img/20191118/d4500fd4b4544befa7fa5b1261b20d68
         * type : 0
         * name : 个护
         * objectId : null
         * color : null
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
}
