package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/11/16
 * @Describe 评测列表
 */
public class EvaluationListBean {

    private String msg;
    private String code;
    private List<ListBean> list;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {

        private String evalWayId;
        private String userId;
        private String imgId;
        private String visitCount;
        private String evalWayName;
        private Object content;
        private String commentId;
        private String publishTime;
        private String createTime;
        private String categoryId;
        private int status;
        private String goodsId;
        private int concernNum;
        private UserShopmemberBean userShopmember;
        private String showTime;
        private String commentNum;

        public String getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(String commentNum) {
            this.commentNum = commentNum;
        }

        public String getShowTime() {
            return showTime;
        }

        public void setShowTime(String showTime) {
            this.showTime = showTime;
        }

        public String getEvalWayId() {
            return evalWayId;
        }

        public void setEvalWayId(String evalWayId) {
            this.evalWayId = evalWayId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getImgId() {
            return imgId;
        }

        public void setImgId(String imgId) {
            this.imgId = imgId;
        }

        public String getVisitCount() {
            return visitCount;
        }

        public void setVisitCount(String visitCount) {
            this.visitCount = visitCount;
        }

        public String getEvalWayName() {
            return evalWayName;
        }

        public void setEvalWayName(String evalWayName) {
            this.evalWayName = evalWayName;
        }

        public Object getContent() {
            return content;
        }

        public void setContent(Object content) {
            this.content = content;
        }

        public String getCommentId() {
            return commentId;
        }

        public void setCommentId(String commentId) {
            this.commentId = commentId;
        }

        public String getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(String publishTime) {
            this.publishTime = publishTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
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

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
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

            private String userId;
            private String userNickName;
            private String userNickImg;
            private Object userAcutalName;
            private Object userMemberGrade;
            private Object userGender;
            private Object userBirthday;
            private String userPhone;
            private Object createTime;
            private Object openId;
            private Object alipayName;
            private Object alipayAccount;
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

            public Object getUserAcutalName() {
                return userAcutalName;
            }

            public void setUserAcutalName(Object userAcutalName) {
                this.userAcutalName = userAcutalName;
            }

            public Object getUserMemberGrade() {
                return userMemberGrade;
            }

            public void setUserMemberGrade(Object userMemberGrade) {
                this.userMemberGrade = userMemberGrade;
            }

            public Object getUserGender() {
                return userGender;
            }

            public void setUserGender(Object userGender) {
                this.userGender = userGender;
            }

            public Object getUserBirthday() {
                return userBirthday;
            }

            public void setUserBirthday(Object userBirthday) {
                this.userBirthday = userBirthday;
            }

            public String getUserPhone() {
                return userPhone;
            }

            public void setUserPhone(String userPhone) {
                this.userPhone = userPhone;
            }

            public Object getCreateTime() {
                return createTime;
            }

            public void setCreateTime(Object createTime) {
                this.createTime = createTime;
            }

            public Object getOpenId() {
                return openId;
            }

            public void setOpenId(Object openId) {
                this.openId = openId;
            }

            public Object getAlipayName() {
                return alipayName;
            }

            public void setAlipayName(Object alipayName) {
                this.alipayName = alipayName;
            }

            public Object getAlipayAccount() {
                return alipayAccount;
            }

            public void setAlipayAccount(Object alipayAccount) {
                this.alipayAccount = alipayAccount;
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
