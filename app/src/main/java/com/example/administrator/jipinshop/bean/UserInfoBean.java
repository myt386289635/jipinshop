package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/10/10
 * @Describe
 */
public class UserInfoBean {


    /**
     * msg : success
     * code : 200
     * list : [{"userId":"7d67892cb02f4766aa72fd5b08b8d8d1","userNickName":"陌陌","userNickImg":"","userAcutalName":"","userMemberGrade":"0","userGender":"女","userBirthday":"1994-06-30","userPhone":"18240011200","createTime":"2018-10-09 21:01:45","openId":" "}]
     * points : 1234
     */

    private String msg;
    private int code;
    private String points;
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

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * userId : 7d67892cb02f4766aa72fd5b08b8d8d1
         * userNickName : 陌陌
         * userNickImg :
         * userAcutalName :
         * userMemberGrade : 0
         * userGender : 女
         * userBirthday : 1994-06-30
         * userPhone : 18240011200
         * createTime : 2018-10-09 21:01:45
         * openId :
         */

        private String userId;
        private String userNickName;
        private String userNickImg;
        private String userAcutalName;
        private String userMemberGrade;
        private String userGender;
        private String userBirthday;
        private String userPhone;
        private String createTime;
        private String openId;
        private String alipayName;
        private String alipayAccount;

        public String getAlipayName() {
            return alipayName;
        }

        public void setAlipayName(String alipayName) {
            this.alipayName = alipayName;
        }

        public String getAlipayAccount() {
            return alipayAccount;
        }

        public void setAlipayAccount(String alipayAccount) {
            this.alipayAccount = alipayAccount;
        }

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

        public String getUserAcutalName() {
            return userAcutalName;
        }

        public void setUserAcutalName(String userAcutalName) {
            this.userAcutalName = userAcutalName;
        }

        public String getUserMemberGrade() {
            return userMemberGrade;
        }

        public void setUserMemberGrade(String userMemberGrade) {
            this.userMemberGrade = userMemberGrade;
        }

        public String getUserGender() {
            return userGender;
        }

        public void setUserGender(String userGender) {
            this.userGender = userGender;
        }

        public String getUserBirthday() {
            return userBirthday;
        }

        public void setUserBirthday(String userBirthday) {
            this.userBirthday = userBirthday;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }
    }
}
