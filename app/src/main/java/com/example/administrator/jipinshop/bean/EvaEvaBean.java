package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/8/12
 * @Describe
 */
public class EvaEvaBean {

    private String msg;
    private int code;
    private List<DataBean> data;
    private List<List2Bean> list2;

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

    public List<List2Bean> getList2() {
        return list2;
    }

    public void setList2(List<List2Bean> list2) {
        this.list2 = list2;
    }

    public static class DataBean {

        private String articleId;
        private String userId;
        private UserBean user;
        private String goodsId;
        private String img;
        private String title;
        private String content;
        private int contentType;
        private Object status;
        private int type;
        private String size;
        private int pv;
        private String shareTitle;
        private String shareContent;
        private String shareImg;
        private String shareUrl;
        private Object publishTime;
        private String createTime;
        private Object createTimeStr;
        private Object updateTime;
        private Object vote;
        private Object voteCount;
        private Object collect;
        private Object relatedArticleList;
        private Object relatedGoodsList;
        private ArticleReadDataBean articleReadData;

        public ArticleReadDataBean getArticleReadData() {
            return articleReadData;
        }

        public void setArticleReadData(ArticleReadDataBean articleReadData) {
            this.articleReadData = articleReadData;
        }

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

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
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

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
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

        public String getShareImg() {
            return shareImg;
        }

        public void setShareImg(String shareImg) {
            this.shareImg = shareImg;
        }

        public String getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(String shareUrl) {
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

        public static class UserBean {

            private String userId;
            private String nickname;
            private String gender;
            private String avatar;
            private int authentication;
            private Object fansCount;
            private int follow;
            private Object bgImg;
            private String detail;
            private Object imgs;

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public int getAuthentication() {
                return authentication;
            }

            public void setAuthentication(int authentication) {
                this.authentication = authentication;
            }

            public Object getFansCount() {
                return fansCount;
            }

            public void setFansCount(Object fansCount) {
                this.fansCount = fansCount;
            }

            public int getFollow() {
                return follow;
            }

            public void setFollow(int follow) {
                this.follow = follow;
            }

            public Object getBgImg() {
                return bgImg;
            }

            public void setBgImg(Object bgImg) {
                this.bgImg = bgImg;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public Object getImgs() {
                return imgs;
            }

            public void setImgs(Object imgs) {
                this.imgs = imgs;
            }
        }

        public static class ArticleReadDataBean{

            private int clickCount;//clickCount数值大于0就是已读  等于0就是未读

            public int getClickCount() {
                return clickCount;
            }

            public void setClickCount(int clickCount) {
                this.clickCount = clickCount;
            }
        }
    }

    public static class List2Bean {

        private String articleId;
        private String userId;
        private UserBeanX user;
        private String goodsId;
        private String img;
        private String title;
        private String content;
        private int contentType;
        private Object status;
        private int type;
        private String size;
        private int pv;
        private String shareTitle;
        private String shareContent;
        private String shareImg;
        private String shareUrl;
        private Object publishTime;
        private String createTime;
        private Object createTimeStr;
        private Object updateTime;
        private Object vote;
        private Object voteCount;
        private Object collect;
        private Object relatedArticleList;
        private Object relatedGoodsList;
        private ArticleReadDataBean articleReadData;

        public ArticleReadDataBean getArticleReadData() {
            return articleReadData;
        }

        public void setArticleReadData(ArticleReadDataBean articleReadData) {
            this.articleReadData = articleReadData;
        }

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

        public UserBeanX getUser() {
            return user;
        }

        public void setUser(UserBeanX user) {
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

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
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

        public String getShareImg() {
            return shareImg;
        }

        public void setShareImg(String shareImg) {
            this.shareImg = shareImg;
        }

        public String getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(String shareUrl) {
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

        public static class UserBeanX {

            private String userId;
            private String nickname;
            private String gender;
            private String avatar;
            private int authentication;
            private Object fansCount;
            private int follow;
            private Object bgImg;
            private String detail;
            private Object imgs;

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public int getAuthentication() {
                return authentication;
            }

            public void setAuthentication(int authentication) {
                this.authentication = authentication;
            }

            public Object getFansCount() {
                return fansCount;
            }

            public void setFansCount(Object fansCount) {
                this.fansCount = fansCount;
            }

            public int getFollow() {
                return follow;
            }

            public void setFollow(int follow) {
                this.follow = follow;
            }

            public Object getBgImg() {
                return bgImg;
            }

            public void setBgImg(Object bgImg) {
                this.bgImg = bgImg;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public Object getImgs() {
                return imgs;
            }

            public void setImgs(Object imgs) {
                this.imgs = imgs;
            }
        }

        public static class ArticleReadDataBean{

            private int clickCount;//clickCount数值大于0就是已读  等于0就是未读

            public int getClickCount() {
                return clickCount;
            }

            public void setClickCount(int clickCount) {
                this.clickCount = clickCount;
            }
        }
    }
}
