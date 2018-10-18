package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/9/29
 * @Describe
 */
public class LoginBean {

    /**
     * msg : success
     * UserDetailPointEntity : {"id":"cfc8d1a358264fa4959d50405bbd68f7","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","points":"0","startTime":"2018-10-09T06:47:35.000+0000","expireTime":"2019-10-09T06:47:35.000+0000","remark":null}
     * code : 200
     * UserAccountEntity : {"id":"cfc8d1a358264fa4959d50405bbd68f7","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","total_account":0,"use_account":0,"unuse_account":0,"create_time":"2018-10-09T06:47:35.000+0000","state":"0"}
     * list : [{"id":"e5b141e7fdc8483b9738fc7047b6cfe5","mobile":"18240011200","code":"224985","create_time":"2018-10-09T06:45:54.000+0000","expire_time":"2018-10-09T06:50:54.000+0000"}]
     * user : {"userId":"7d67892cb02f4766aa72fd5b08b8d8d1","userNickName":"83极品城1539067655909","userNickImg":"","userAcutalName":"","userMemberGrade":"0","userGender":"","userBirthday":"2018-10-08T16:00:00.000+0000","userPhone":"18240011200","createTime":"2018-10-09 14:47:35"}
     */

    private String msg;
    private UserDetailPointEntityBean UserDetailPointEntity;
    private int code;
    private UserAccountEntityBean UserAccountEntity;
    private UserBean user;
    private List<ListBean> list;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UserDetailPointEntityBean getUserDetailPointEntity() {
        return UserDetailPointEntity;
    }

    public void setUserDetailPointEntity(UserDetailPointEntityBean UserDetailPointEntity) {
        this.UserDetailPointEntity = UserDetailPointEntity;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public UserAccountEntityBean getUserAccountEntity() {
        return UserAccountEntity;
    }

    public void setUserAccountEntity(UserAccountEntityBean UserAccountEntity) {
        this.UserAccountEntity = UserAccountEntity;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class UserDetailPointEntityBean {
        /**
         * id : cfc8d1a358264fa4959d50405bbd68f7
         * userId : 7d67892cb02f4766aa72fd5b08b8d8d1
         * points : 0
         * startTime : 2018-10-09T06:47:35.000+0000
         * expireTime : 2019-10-09T06:47:35.000+0000
         * remark : null
         */

        private String id;
        private String userId;
        private String points;
        private String startTime;
        private String expireTime;
        private Object remark;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(String expireTime) {
            this.expireTime = expireTime;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }
    }

    public static class UserAccountEntityBean {
        /**
         * id : cfc8d1a358264fa4959d50405bbd68f7
         * userId : 7d67892cb02f4766aa72fd5b08b8d8d1
         * total_account : 0
         * use_account : 0
         * unuse_account : 0
         * create_time : 2018-10-09T06:47:35.000+0000
         * state : 0
         */

        private String id;
        private String userId;
        private String total_account;
        private String use_account;
        private String unuse_account;
        private String create_time;
        private String state;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getTotal_account() {
            return total_account;
        }

        public void setTotal_account(String total_account) {
            this.total_account = total_account;
        }

        public String getUse_account() {
            return use_account;
        }

        public void setUse_account(String use_account) {
            this.use_account = use_account;
        }

        public String getUnuse_account() {
            return unuse_account;
        }

        public void setUnuse_account(String unuse_account) {
            this.unuse_account = unuse_account;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }

    public static class UserBean {
        /**
         * userId : 7d67892cb02f4766aa72fd5b08b8d8d1
         * userNickName : 83极品城1539067655909
         * userNickImg :
         * userAcutalName :
         * userMemberGrade : 0
         * userGender :
         * userBirthday : 2018-10-08T16:00:00.000+0000
         * userPhone : 18240011200
         * createTime : 2018-10-09 14:47:35
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
    }

    public static class ListBean {
        /**
         * id : e5b141e7fdc8483b9738fc7047b6cfe5
         * mobile : 18240011200
         * code : 224985
         * create_time : 2018-10-09T06:45:54.000+0000
         * expire_time : 2018-10-09T06:50:54.000+0000
         */

        private String id;
        private String mobile;
        private String code;
        private String create_time;
        private String expire_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getExpire_time() {
            return expire_time;
        }

        public void setExpire_time(String expire_time) {
            this.expire_time = expire_time;
        }
    }
}
