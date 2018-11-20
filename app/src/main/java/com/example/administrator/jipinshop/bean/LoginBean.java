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
     * code : 200
     * UserAccountEntity : {"id":"cfc8d1a358264fa4959d50405bbd68f7","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","total_account":90.96,"use_account":29.06,"unuse_account":9.04,"create_time":"2018-10-09 14:47:35","state":"0"}
     * list : [{"id":"fa4f36cca43d4d40819035bca36359f7","mobile":"18240011200","code":"947427","create_time":"2018-11-20 16:06:50","expire_time":"2018-11-20 16:11:50"}]
     * user : {"userId":"7d67892cb02f4766aa72fd5b08b8d8d1","userNickName":"御用编辑1","userNickImg":"http://pi6611u5d.bkt.clouddn.com/d61bf54391b64b289e72208654168bd1","userAcutalName":"","userMemberGrade":"0","userGender":"男","userBirthday":"1994-06-30","userPhone":"18240011200","createTime":"2018-11-15 11:24:43","openId":" ","alipayName":"李耕旬","alipayAccount":"13841284944","fansCount":101}
     * points : 0
     */

    private String msg;
    private int code;
    private UserAccountEntityBean UserAccountEntity;
    private UserBean user;
    private int points;
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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class UserAccountEntityBean {
        /**
         * id : cfc8d1a358264fa4959d50405bbd68f7
         * userId : 7d67892cb02f4766aa72fd5b08b8d8d1
         * total_account : 90.96
         * use_account : 29.06
         * unuse_account : 9.04
         * create_time : 2018-10-09 14:47:35
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
         * userNickName : 御用编辑1
         * userNickImg : http://pi6611u5d.bkt.clouddn.com/d61bf54391b64b289e72208654168bd1
         * userAcutalName :
         * userMemberGrade : 0
         * userGender : 男
         * userBirthday : 1994-06-30
         * userPhone : 18240011200
         * createTime : 2018-11-15 11:24:43
         * openId :
         * alipayName : 李耕旬
         * alipayAccount : 13841284944
         * fansCount : 101
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

        public int getFansCount() {
            return fansCount;
        }

        public void setFansCount(int fansCount) {
            this.fansCount = fansCount;
        }
    }

    public static class ListBean {
        /**
         * id : fa4f36cca43d4d40819035bca36359f7
         * mobile : 18240011200
         * code : 947427
         * create_time : 2018-11-20 16:06:50
         * expire_time : 2018-11-20 16:11:50
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
