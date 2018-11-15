package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/11/9
 * @Describe 评论列表 bean
 */
public class CommentBean {

    private String msg;
    private int code;
    private int count;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {

        private String commentId;
        private String articId;
        private String userId;
        private String content;
        private String createTime;
        private String showTime;
        private String parentId;
        private int status;
        private String snapNum;
        private int userSnap;
        private int secondNum;
        private UserShopmemberBean userShopmember;
        private List<UserCommentListBean> userCommentList;

        public String getCommentId() {
            return commentId;
        }

        public void setCommentId(String commentId) {
            this.commentId = commentId;
        }

        public String getArticId() {
            return articId;
        }

        public void setArticId(String articId) {
            this.articId = articId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getShowTime() {
            return showTime;
        }

        public void setShowTime(String showTime) {
            this.showTime = showTime;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getSnapNum() {
            return snapNum;
        }

        public void setSnapNum(String snapNum) {
            this.snapNum = snapNum;
        }

        public int isUserSnap() {
            return userSnap;
        }

        public void setUserSnap(int userSnap) {
            this.userSnap = userSnap;
        }

        public int getSecondNum() {
            return secondNum;
        }

        public void setSecondNum(int secondNum) {
            this.secondNum = secondNum;
        }

        public UserShopmemberBean getUserShopmember() {
            return userShopmember;
        }

        public void setUserShopmember(UserShopmemberBean userShopmember) {
            this.userShopmember = userShopmember;
        }

        public List<UserCommentListBean> getUserCommentList() {
            return userCommentList;
        }

        public void setUserCommentList(List<UserCommentListBean> userCommentList) {
            this.userCommentList = userCommentList;
        }

        public static class UserShopmemberBean {

            private String userId;
            private String userNickName;
            private String userNickImg;
            private String userPhone;
            private String openId;

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

            public String getOpenId() {
                return openId;
            }

            public void setOpenId(String openId) {
                this.openId = openId;
            }
        }

        public static class UserCommentListBean {

            private String commentId;
            private String articId;
            private String userId;
            private String content;
            private String createTime;
            private String showTime;
            private String parentId;
            private String status;
            private String snapNum;
            private int userSnap;
            private Object secondNum;
            private UserShopmemberBeanX userShopmember;

            public String getCommentId() {
                return commentId;
            }

            public void setCommentId(String commentId) {
                this.commentId = commentId;
            }

            public String getArticId() {
                return articId;
            }

            public void setArticId(String articId) {
                this.articId = articId;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getShowTime() {
                return showTime;
            }

            public void setShowTime(String showTime) {
                this.showTime = showTime;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getSnapNum() {
                return snapNum;
            }

            public void setSnapNum(String snapNum) {
                this.snapNum = snapNum;
            }

            public int getUserSnap() {
                return userSnap;
            }

            public void setUserSnap(int userSnap) {
                this.userSnap = userSnap;
            }

            public Object getSecondNum() {
                return secondNum;
            }

            public void setSecondNum(Object secondNum) {
                this.secondNum = secondNum;
            }

            public UserShopmemberBeanX getUserShopmember() {
                return userShopmember;
            }

            public void setUserShopmember(UserShopmemberBeanX userShopmember) {
                this.userShopmember = userShopmember;
            }

            public static class UserShopmemberBeanX {

                private String userId;
                private String userNickName;
                private String userNickImg;
                private String userPhone;
                private String openId;

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

                public String getOpenId() {
                    return openId;
                }

                public void setOpenId(String openId) {
                    this.openId = openId;
                }
            }
        }
    }
}
