package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/7/5
 * @Describe 问题回答
 */
public class QuestionsBean {

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

        private String id;
        private String title;
        private String goodsCategoryId;
        private String userId;
        private UserBean user;
        private AnswerBean answer;
        private String createTime;
        private int answerCount;

        public int getAnswerCount() {
            return answerCount;
        }

        public void setAnswerCount(int answerCount) {
            this.answerCount = answerCount;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getGoodsCategoryId() {
            return goodsCategoryId;
        }

        public void setGoodsCategoryId(String goodsCategoryId) {
            this.goodsCategoryId = goodsCategoryId;
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

        public AnswerBean getAnswer() {
            return answer;
        }

        public void setAnswer(AnswerBean answer) {
            this.answer = answer;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public static class UserBean {
            /**
             * userId : 2
             * nickname : null
             * gender : null
             * avatar : null
             * authentication : null
             * fansCount : null
             * follow : null
             */

            private String userId;
            private Object nickname;
            private Object gender;
            private Object avatar;
            private Object authentication;
            private Object fansCount;
            private Object follow;

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public Object getNickname() {
                return nickname;
            }

            public void setNickname(Object nickname) {
                this.nickname = nickname;
            }

            public Object getGender() {
                return gender;
            }

            public void setGender(Object gender) {
                this.gender = gender;
            }

            public Object getAvatar() {
                return avatar;
            }

            public void setAvatar(Object avatar) {
                this.avatar = avatar;
            }

            public Object getAuthentication() {
                return authentication;
            }

            public void setAuthentication(Object authentication) {
                this.authentication = authentication;
            }

            public Object getFansCount() {
                return fansCount;
            }

            public void setFansCount(Object fansCount) {
                this.fansCount = fansCount;
            }

            public Object getFollow() {
                return follow;
            }

            public void setFollow(Object follow) {
                this.follow = follow;
            }
        }

        public static class AnswerBean {
            /**
             * id : null
             * content : 你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好
             * questionId : null
             * userId : 2
             * userAvatar : http://jipincheng.cn/avatar/img/20190603/0b97ccc1392745a3bde6deef9c2391af
             * userNickname : 小胖子
             * voteCount : null
             * createTime : null
             */

            private Object id;
            private String content;
            private Object questionId;
            private String userId;
            private String userAvatar;
            private String userNickname;
            private Object voteCount;
            private Object createTime;

            public Object getId() {
                return id;
            }

            public void setId(Object id) {
                this.id = id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public Object getQuestionId() {
                return questionId;
            }

            public void setQuestionId(Object questionId) {
                this.questionId = questionId;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getUserAvatar() {
                return userAvatar;
            }

            public void setUserAvatar(String userAvatar) {
                this.userAvatar = userAvatar;
            }

            public String getUserNickname() {
                return userNickname;
            }

            public void setUserNickname(String userNickname) {
                this.userNickname = userNickname;
            }

            public Object getVoteCount() {
                return voteCount;
            }

            public void setVoteCount(Object voteCount) {
                this.voteCount = voteCount;
            }

            public Object getCreateTime() {
                return createTime;
            }

            public void setCreateTime(Object createTime) {
                this.createTime = createTime;
            }
        }
    }
}
