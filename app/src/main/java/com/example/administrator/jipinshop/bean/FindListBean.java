package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/11/17
 * @Describe
 */
public class FindListBean {

    /**
     * msg : success
     * code : 200
     * list : [{"findgoodsId":"4","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","title":"主标题","smallTitle":"副标题","imgId":"http://pi6611u5d.bkt.clouddn.com/a007a458b51141f88a15ab24e034596a","publishTime":"2018-11-16 17:41:43","showTime":null,"visitCount":"788","commentNum":0,"content":null,"createTime":null,"categoryId":"06d488224d5a4850a8c652a24eb5ef38","status":1,"concernNum":0,"userShopmember":{"userId":"7d67892cb02f4766aa72fd5b08b8d8d1","userNickName":"哦哦哦","userNickImg":"http://pi6611u5d.bkt.clouddn.com/d61bf54391b64b289e72208654168bd1","userAcutalName":null,"userMemberGrade":null,"userGender":null,"userBirthday":null,"userPhone":"18240011200","createTime":null,"openId":null,"alipayName":null,"alipayAccount":null,"fansCount":2}},{"findgoodsId":"3","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","title":"主标题","smallTitle":"副标题","imgId":"http://pi6611u5d.bkt.clouddn.com/a007a458b51141f88a15ab24e034596a","publishTime":"2018-11-16 17:41:45","showTime":null,"visitCount":"547","commentNum":0,"content":null,"createTime":null,"categoryId":"06d488224d5a4850a8c652a24eb5ef38","status":1,"concernNum":0,"userShopmember":{"userId":"7d67892cb02f4766aa72fd5b08b8d8d1","userNickName":"哦哦哦","userNickImg":"http://pi6611u5d.bkt.clouddn.com/d61bf54391b64b289e72208654168bd1","userAcutalName":null,"userMemberGrade":null,"userGender":null,"userBirthday":null,"userPhone":"18240011200","createTime":null,"openId":null,"alipayName":null,"alipayAccount":null,"fansCount":2}},{"findgoodsId":"1","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","title":"主标题","smallTitle":"副标题","imgId":"http://pi6611u5d.bkt.clouddn.com/a007a458b51141f88a15ab24e034596a","publishTime":"2018-11-16 17:41:49","showTime":null,"visitCount":"452","commentNum":0,"content":null,"createTime":null,"categoryId":"06d488224d5a4850a8c652a24eb5ef38","status":1,"concernNum":0,"userShopmember":{"userId":"7d67892cb02f4766aa72fd5b08b8d8d1","userNickName":"哦哦哦","userNickImg":"http://pi6611u5d.bkt.clouddn.com/d61bf54391b64b289e72208654168bd1","userAcutalName":null,"userMemberGrade":null,"userGender":null,"userBirthday":null,"userPhone":"18240011200","createTime":null,"openId":null,"alipayName":null,"alipayAccount":null,"fansCount":2}},{"findgoodsId":"2","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","title":"有值的","smallTitle":"副标题","imgId":"http://pi6611u5d.bkt.clouddn.com/a007a458b51141f88a15ab24e034596a","publishTime":"2018-11-16 17:41:47","showTime":null,"visitCount":"252","commentNum":0,"content":null,"createTime":null,"categoryId":"06d488224d5a4850a8c652a24eb5ef38","status":1,"concernNum":0,"userShopmember":{"userId":"7d67892cb02f4766aa72fd5b08b8d8d1","userNickName":"哦哦哦","userNickImg":"http://pi6611u5d.bkt.clouddn.com/d61bf54391b64b289e72208654168bd1","userAcutalName":null,"userMemberGrade":null,"userGender":null,"userBirthday":null,"userPhone":"18240011200","createTime":null,"openId":null,"alipayName":null,"alipayAccount":null,"fansCount":2}},{"findgoodsId":"5","userId":null,"title":"主标题","smallTitle":"副标题","imgId":null,"publishTime":null,"showTime":null,"visitCount":"0","commentNum":0,"content":null,"createTime":null,"categoryId":"06d488224d5a4850a8c652a24eb5ef38","status":1,"concernNum":null,"userShopmember":null}]
     */

    private String msg;
    private int code;
    private List<ListBean> list;

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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * findgoodsId : 4
         * userId : 7d67892cb02f4766aa72fd5b08b8d8d1
         * title : 主标题
         * smallTitle : 副标题
         * imgId : http://pi6611u5d.bkt.clouddn.com/a007a458b51141f88a15ab24e034596a
         * publishTime : 2018-11-16 17:41:43
         * showTime : null
         * visitCount : 788
         * commentNum : 0
         * content : null
         * createTime : null
         * categoryId : 06d488224d5a4850a8c652a24eb5ef38
         * status : 1
         * concernNum : 0
         * userShopmember : {"userId":"7d67892cb02f4766aa72fd5b08b8d8d1","userNickName":"哦哦哦","userNickImg":"http://pi6611u5d.bkt.clouddn.com/d61bf54391b64b289e72208654168bd1","userAcutalName":null,"userMemberGrade":null,"userGender":null,"userBirthday":null,"userPhone":"18240011200","createTime":null,"openId":null,"alipayName":null,"alipayAccount":null,"fansCount":2}
         */

        private String findgoodsId;
        private String userId;
        private String title;
        private String smallTitle;
        private String imgId;
        private String publishTime;
        private String showTime;
        private String visitCount;
        private int commentNum;
        private Object content;
        private Object createTime;
        private String categoryId;
        private int status;
        private int concernNum;
        private UserShopmemberBean userShopmember;

        public String getFindgoodsId() {
            return findgoodsId;
        }

        public void setFindgoodsId(String findgoodsId) {
            this.findgoodsId = findgoodsId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSmallTitle() {
            return smallTitle;
        }

        public void setSmallTitle(String smallTitle) {
            this.smallTitle = smallTitle;
        }

        public String getImgId() {
            return imgId;
        }

        public void setImgId(String imgId) {
            this.imgId = imgId;
        }

        public String getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(String publishTime) {
            this.publishTime = publishTime;
        }

        public String getShowTime() {
            return showTime;
        }

        public void setShowTime(String showTime) {
            this.showTime = showTime;
        }

        public String getVisitCount() {
            return visitCount;
        }

        public void setVisitCount(String visitCount) {
            this.visitCount = visitCount;
        }

        public int getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(int commentNum) {
            this.commentNum = commentNum;
        }

        public Object getContent() {
            return content;
        }

        public void setContent(Object content) {
            this.content = content;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getConcernNum() {
            return concernNum;
        }

        public void setConcernNum(int concernNum) {
            this.concernNum = concernNum;
        }

        public UserShopmemberBean getUserShopmember() {
            return userShopmember;
        }

        public void setUserShopmember(UserShopmemberBean userShopmember) {
            this.userShopmember = userShopmember;
        }

        public static class UserShopmemberBean {
            /**
             * userId : 7d67892cb02f4766aa72fd5b08b8d8d1
             * userNickName : 哦哦哦
             * userNickImg : http://pi6611u5d.bkt.clouddn.com/d61bf54391b64b289e72208654168bd1
             * userAcutalName : null
             * userMemberGrade : null
             * userGender : null
             * userBirthday : null
             * userPhone : 18240011200
             * createTime : null
             * openId : null
             * alipayName : null
             * alipayAccount : null
             * fansCount : 2
             */

            private String userId;
            private String userNickName;
            private String userNickImg;
            private String userPhone;
            private int fansCount;

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getUserNickName() {
                return userNickName;
            }

            public void setUserNickName(String userNickName) {
                this.userNickName = userNickName;
            }

            public String getUserNickImg() {
                return userNickImg;
            }

            public void setUserNickImg(String userNickImg) {
                this.userNickImg = userNickImg;
            }

            public String getUserPhone() {
                return userPhone;
            }

            public void setUserPhone(String userPhone) {
                this.userPhone = userPhone;
            }

            public int getFansCount() {
                return fansCount;
            }

            public void setFansCount(int fansCount) {
                this.fansCount = fansCount;
            }
        }
    }
}
