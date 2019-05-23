package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2019/5/22
 * @Describe
 */
public class ReportBean {

    /**
     * msg : success
     * code : 0
     * data : {"articleId":"1c2709f045154e5f81a82b93f63f1800","userId":"2","user":null,"goodsId":"9e187764d29d419296c5f77028732d4b","img":"http://jipincheng.cn/avatar/img/20190522/0babbf9cda174ef4b951094463b2c088","title":"主要还是阅读一下安卓吧。。。","content":"[{\"height\":0,\"type\":\"1\",\"value\":\"Glide 是 Google 官方推荐的一款图片加载库，使用起来也非常的简单便利，Glide 它帮我们完成了很多很重要，但是却通用的功能，例如：图片的加载压缩、展示、加载图片的内存管理等等。\\n对 Glide 还不熟悉的朋友，可以参考 《一篇好文，助你上手 Glide》\\n但是，在使用 Glide 的时候，有一些小技巧，可以让你的内存更优化，避免可能出现的 OOM。例如：虽然 Glide 会根据加载的控件大小，优化加载后的图片尺寸，可如果加载的是一张全屏的大图，依然会是一个占用内存空间非常大的操作。\\n具体一张 Bitmap 到底占用了多少内存空间，可以参考《Bitmap 比你想的更费内存 | 吊打 OOM》\",\"width\":0},{\"height\":5903,\"type\":\"2\",\"value\":\"http://jipincheng.cn/avatar/img/20190522/e395ac2fbfbb4b499ddff6014755e9a6\",\"width\":750}]","contentType":3,"status":0,"type":4,"size":"0","pv":0,"shareTitle":null,"shareContent":null,"shareImg":null,"shareUrl":null,"publishTime":null,"createTime":"2019.05.22","createTimeStr":null,"updateTime":null,"vote":null,"voteCount":null,"collect":null,"relatedArticleList":null,"relatedGoodsList":null}
     */

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
        /**
         * articleId : 1c2709f045154e5f81a82b93f63f1800
         * userId : 2
         * user : null
         * goodsId : 9e187764d29d419296c5f77028732d4b
         * img : http://jipincheng.cn/avatar/img/20190522/0babbf9cda174ef4b951094463b2c088
         * title : 主要还是阅读一下安卓吧。。。
         * content : [{"height":0,"type":"1","value":"Glide 是 Google 官方推荐的一款图片加载库，使用起来也非常的简单便利，Glide 它帮我们完成了很多很重要，但是却通用的功能，例如：图片的加载压缩、展示、加载图片的内存管理等等。\n对 Glide 还不熟悉的朋友，可以参考 《一篇好文，助你上手 Glide》\n但是，在使用 Glide 的时候，有一些小技巧，可以让你的内存更优化，避免可能出现的 OOM。例如：虽然 Glide 会根据加载的控件大小，优化加载后的图片尺寸，可如果加载的是一张全屏的大图，依然会是一个占用内存空间非常大的操作。\n具体一张 Bitmap 到底占用了多少内存空间，可以参考《Bitmap 比你想的更费内存 | 吊打 OOM》","width":0},{"height":5903,"type":"2","value":"http://jipincheng.cn/avatar/img/20190522/e395ac2fbfbb4b499ddff6014755e9a6","width":750}]
         * contentType : 3
         * status : 0
         * type : 4
         * size : 0
         * pv : 0
         * shareTitle : null
         * shareContent : null
         * shareImg : null
         * shareUrl : null
         * publishTime : null
         * createTime : 2019.05.22
         * createTimeStr : null
         * updateTime : null
         * vote : null
         * voteCount : null
         * collect : null
         * relatedArticleList : null
         * relatedGoodsList : null
         */

        private String articleId;
        private String userId;
        private Object user;
        private String goodsId;
        private String img;
        private String title;
        private String content;
        private int contentType;
        private int status;
        private int type;
        private String size;
        private int pv;
        private Object shareTitle;
        private Object shareContent;
        private Object shareImg;
        private Object shareUrl;
        private Object publishTime;
        private String createTime;
        private Object createTimeStr;
        private Object updateTime;
        private Object vote;
        private Object voteCount;
        private Object collect;
        private Object relatedArticleList;
        private Object relatedGoodsList;

        public String getArticleId() {
            return articleId;
        }

        public void setArticleId(String articleId) {
            this.articleId = articleId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public Object getUser() {
            return user;
        }

        public void setUser(Object user) {
            this.user = user;
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

        public int getContentType() {
            return contentType;
        }

        public void setContentType(int contentType) {
            this.contentType = contentType;
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

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public int getPv() {
            return pv;
        }

        public void setPv(int pv) {
            this.pv = pv;
        }

        public Object getShareTitle() {
            return shareTitle;
        }

        public void setShareTitle(Object shareTitle) {
            this.shareTitle = shareTitle;
        }

        public Object getShareContent() {
            return shareContent;
        }

        public void setShareContent(Object shareContent) {
            this.shareContent = shareContent;
        }

        public Object getShareImg() {
            return shareImg;
        }

        public void setShareImg(Object shareImg) {
            this.shareImg = shareImg;
        }

        public Object getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(Object shareUrl) {
            this.shareUrl = shareUrl;
        }

        public Object getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(Object publishTime) {
            this.publishTime = publishTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Object getCreateTimeStr() {
            return createTimeStr;
        }

        public void setCreateTimeStr(Object createTimeStr) {
            this.createTimeStr = createTimeStr;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public Object getVote() {
            return vote;
        }

        public void setVote(Object vote) {
            this.vote = vote;
        }

        public Object getVoteCount() {
            return voteCount;
        }

        public void setVoteCount(Object voteCount) {
            this.voteCount = voteCount;
        }

        public Object getCollect() {
            return collect;
        }

        public void setCollect(Object collect) {
            this.collect = collect;
        }

        public Object getRelatedArticleList() {
            return relatedArticleList;
        }

        public void setRelatedArticleList(Object relatedArticleList) {
            this.relatedArticleList = relatedArticleList;
        }

        public Object getRelatedGoodsList() {
            return relatedGoodsList;
        }

        public void setRelatedGoodsList(Object relatedGoodsList) {
            this.relatedGoodsList = relatedGoodsList;
        }
    }
}
