package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/10/10
 * @Describe 关注列表bean
 */
public class FollowBean {

    /**
     * msg : success
     * code : 200
     * list : [{"concernId":"dcb44c33159b4563aeee2b54158293c6","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","attentionUserId":"786afd1ad252451bbae5ee0790c7d577","createTime":"2018-11-16 15:18:33","status":1,"userShopmember":{"userId":"786afd1ad252451bbae5ee0790c7d577","userNickName":"Wowo","userNickImg":"","userAcutalName":null,"userMemberGrade":null,"userGender":null,"userBirthday":null,"userPhone":"13841284944","createTime":null,"openId":null,"alipayName":null,"alipayAccount":null,"fansCount":1}},{"concernId":"b841d435f1884db79679f3c19b4ac24a","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","attentionUserId":"58f3f9223d324558aa965dd97c69b532","createTime":"2018-11-16 15:18:22","status":1,"userShopmember":{"userId":"58f3f9223d324558aa965dd97c69b532","userNickName":"Chona","userNickImg":"https://wx.qlogo.cn/mmopen/vi_32/n8c8j5rrsN9XMKdTzhh6dXWdyA1ZEl2Nh8RQTBiaVoYPj1uXONuVwN2VGM3xbMB5wWOKiamZwCBnbLL6GMh0GASA/132","userAcutalName":null,"userMemberGrade":null,"userGender":null,"userBirthday":null,"userPhone":null,"createTime":null,"openId":null,"alipayName":null,"alipayAccount":null,"fansCount":1}}]
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
         * concernId : dcb44c33159b4563aeee2b54158293c6
         * userId : 7d67892cb02f4766aa72fd5b08b8d8d1
         * attentionUserId : 786afd1ad252451bbae5ee0790c7d577
         * createTime : 2018-11-16 15:18:33
         * status : 1
         * userShopmember : {"userId":"786afd1ad252451bbae5ee0790c7d577","userNickName":"Wowo","userNickImg":"","userAcutalName":null,"userMemberGrade":null,"userGender":null,"userBirthday":null,"userPhone":"13841284944","createTime":null,"openId":null,"alipayName":null,"alipayAccount":null,"fansCount":1}
         */

        private String concernId;
        private String userId;
        private String attentionUserId;
        private String createTime;
        private int status;
        private UserShopmemberBean userShopmember;
        private int fans;//自己添加的标示，标志有没有关注这个人

        public int getFans() {
            return fans;
        }

        public void setFans(int fans) {
            this.fans = fans;
        }

        public String getConcernId() {
            return concernId;
        }

        public void setConcernId(String concernId) {
            this.concernId = concernId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getAttentionUserId() {
            return attentionUserId;
        }

        public void setAttentionUserId(String attentionUserId) {
            this.attentionUserId = attentionUserId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public UserShopmemberBean getUserShopmember() {
            return userShopmember;
        }

        public void setUserShopmember(UserShopmemberBean userShopmember) {
            this.userShopmember = userShopmember;
        }

        public static class UserShopmemberBean {
            /**
             * userId : 786afd1ad252451bbae5ee0790c7d577
             * userNickName : Wowo
             * userNickImg :
             * userAcutalName : null
             * userMemberGrade : null
             * userGender : null
             * userBirthday : null
             * userPhone : 13841284944
             * createTime : null
             * openId : null
             * alipayName : null
             * alipayAccount : null
             * fansCount : 1
             */

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
