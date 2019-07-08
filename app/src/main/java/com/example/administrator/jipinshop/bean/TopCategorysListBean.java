package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/7/3
 * @Describe
 */
public class TopCategorysListBean {

    /**
     * msg : success
     * code : 0
     * ad : {"img":"http://jipincheng.cn/activity/img/20190531/9e91e7e097be4d82b73783061966b401","type":0,"objectId":null}
     * data : [{"categoryId":"3a9da7e316914052ab207c6162a59ae6","parentId":"c38c97e81c3142779f9e085902c9423d","categoryName":"电动牙刷","img":"http://jipincheng.cn/category/img/20190302/b552b004f492478f95b1393e9a5541ac","orderNum":1,"top":1,"topTitle":"最佳电动牙刷推荐榜","topImg":"http://jipincheng.cn/category/img/20190701/b5fc30cb0ae54cd985d29f37dd1e175a","topOrderNum":2,"children":null,"adList":null,"relatedItemList":null,"relatedGoodsList":null,"relatedPedia":null,"relatedQuestion":null,"relatedItemCategotyList":null,"relatedArticleList":null},{"categoryId":"bb269c5d3fe94b1cb1d5df68afcda140","parentId":"c38c97e81c3142779f9e085902c9423d","categoryName":"剃须刀","img":"http://jipincheng.cn/category/img/20190302/1b817aea733b42aab3ce0767b9837ee6","orderNum":2,"top":1,"topTitle":"最佳剃须刀推荐榜","topImg":"http://jipincheng.cn/category/img/20190701/319737a0bcb049b5905024aa4524e218","topOrderNum":3,"children":null,"adList":null,"relatedItemList":null,"relatedGoodsList":null,"relatedPedia":null,"relatedQuestion":null,"relatedItemCategotyList":null,"relatedArticleList":null},{"categoryId":"b4cb5713c5d548f5a9196ccc8bb0f245","parentId":"c38c97e81c3142779f9e085902c9423d","categoryName":"电吹风","img":"http://jipincheng.cn/category/img/20190302/03f2dc5788304ea1a9a293c275db4154","orderNum":3,"top":1,"topTitle":"最佳电吹风推荐榜","topImg":"http://jipincheng.cn/category/img/20190701/969ff1e856944ae1a1854f2e9d43df3e","topOrderNum":4,"children":null,"adList":null,"relatedItemList":null,"relatedGoodsList":null,"relatedPedia":null,"relatedQuestion":null,"relatedItemCategotyList":null,"relatedArticleList":null}]
     */

    private String msg;
    private int code;
    private AdBean ad;
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

    public AdBean getAd() {
        return ad;
    }

    public void setAd(AdBean ad) {
        this.ad = ad;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class AdBean {
        /**
         * img : http://jipincheng.cn/activity/img/20190531/9e91e7e097be4d82b73783061966b401
         * type : 0
         * objectId : null
         */

        private String img;
        private int type;
        private String objectId;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }
    }

    public static class DataBean {
        /**
         * categoryId : 3a9da7e316914052ab207c6162a59ae6
         * parentId : c38c97e81c3142779f9e085902c9423d
         * categoryName : 电动牙刷
         * img : http://jipincheng.cn/category/img/20190302/b552b004f492478f95b1393e9a5541ac
         * orderNum : 1
         * top : 1
         * topTitle : 最佳电动牙刷推荐榜
         * topImg : http://jipincheng.cn/category/img/20190701/b5fc30cb0ae54cd985d29f37dd1e175a
         * topOrderNum : 2
         * children : null
         * adList : null
         * relatedItemList : null
         * relatedGoodsList : null
         * relatedPedia : null
         * relatedQuestion : null
         * relatedItemCategotyList : null
         * relatedArticleList : null
         */

        private String categoryId;
        private String parentId;
        private String categoryName;
        private String img;
        private int orderNum;
        private int top;
        private String topTitle;
        private String topImg;
        private int topOrderNum;
        private Object children;
        private Object adList;
        private Object relatedItemList;
        private Object relatedGoodsList;
        private Object relatedPedia;
        private Object relatedQuestion;
        private Object relatedItemCategotyList;
        private Object relatedArticleList;

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

        public int getTop() {
            return top;
        }

        public void setTop(int top) {
            this.top = top;
        }

        public String getTopTitle() {
            return topTitle;
        }

        public void setTopTitle(String topTitle) {
            this.topTitle = topTitle;
        }

        public String getTopImg() {
            return topImg;
        }

        public void setTopImg(String topImg) {
            this.topImg = topImg;
        }

        public int getTopOrderNum() {
            return topOrderNum;
        }

        public void setTopOrderNum(int topOrderNum) {
            this.topOrderNum = topOrderNum;
        }

        public Object getChildren() {
            return children;
        }

        public void setChildren(Object children) {
            this.children = children;
        }

        public Object getAdList() {
            return adList;
        }

        public void setAdList(Object adList) {
            this.adList = adList;
        }

        public Object getRelatedItemList() {
            return relatedItemList;
        }

        public void setRelatedItemList(Object relatedItemList) {
            this.relatedItemList = relatedItemList;
        }

        public Object getRelatedGoodsList() {
            return relatedGoodsList;
        }

        public void setRelatedGoodsList(Object relatedGoodsList) {
            this.relatedGoodsList = relatedGoodsList;
        }

        public Object getRelatedPedia() {
            return relatedPedia;
        }

        public void setRelatedPedia(Object relatedPedia) {
            this.relatedPedia = relatedPedia;
        }

        public Object getRelatedQuestion() {
            return relatedQuestion;
        }

        public void setRelatedQuestion(Object relatedQuestion) {
            this.relatedQuestion = relatedQuestion;
        }

        public Object getRelatedItemCategotyList() {
            return relatedItemCategotyList;
        }

        public void setRelatedItemCategotyList(Object relatedItemCategotyList) {
            this.relatedItemCategotyList = relatedItemCategotyList;
        }

        public Object getRelatedArticleList() {
            return relatedArticleList;
        }

        public void setRelatedArticleList(Object relatedArticleList) {
            this.relatedArticleList = relatedArticleList;
        }
    }
}
