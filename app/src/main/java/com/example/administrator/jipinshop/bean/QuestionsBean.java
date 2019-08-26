package com.example.administrator.jipinshop.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/7/5
 * @Describe 问题回答
 */
public class QuestionsBean implements Serializable {

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

    public static class DataBean implements Serializable{

        private String id;
        private String title;
        private String goodsCategoryId;
        private String userId;
        private UserBean user;
        private AnswerBean answer;
        private String createTime;
        private int answerCount;
        private int collect;
        private String remark;

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getCollect() {
            return collect;
        }

        public void setCollect(int collect) {
            this.collect = collect;
        }

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

        public static class UserBean implements Serializable{
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
            private String nickname;
            private String gender;
            private String avatar;
            private String authentication;
            private String fansCount;
            private String follow;

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

            public String getAuthentication() {
                return authentication;
            }

            public void setAuthentication(String authentication) {
                this.authentication = authentication;
            }

            public String getFansCount() {
                return fansCount;
            }

            public void setFansCount(String fansCount) {
                this.fansCount = fansCount;
            }

            public String getFollow() {
                return follow;
            }

            public void setFollow(String follow) {
                this.follow = follow;
            }
        }

        public static class AnswerBean implements Serializable{
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

            private String id;
            private String content;
            private String questionId;
            private String userId;
            private String userAvatar;
            private String userNickname;
            private String voteCount;
            private String createTime;
            private String vote;

            public String getVote() {
                return vote;
            }

            public void setVote(String vote) {
                this.vote = vote;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getQuestionId() {
                return questionId;
            }

            public void setQuestionId(String questionId) {
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

            public String getVoteCount() {
                return voteCount;
            }

            public void setVoteCount(String voteCount) {
                this.voteCount = voteCount;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }
    }
}
