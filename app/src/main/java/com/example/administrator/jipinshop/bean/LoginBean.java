package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2018/9/29
 * @Describe
 */
public class LoginBean {

    /**
     * msg : success
     * code : 0
     * data : {"userId":"3ed475ccc8f24bd08da35e552c85c85a","nickname":"︶~\u2002夏末丶","realname":"","gender":"男","birthday":"2018-12-11","mobile":"18240011200","openid":null,"avatar":"http://jipincheng.cn/c201cd8dd7f04063bfe3f4eed9e79071","role":1,"qrCode":null,"authentication":null,"status":null,"fansCount":0,"voteCount":0,"followCount":0,"createTime":"2018-12-05 21:26:15","updateTime":null,"token":"b254af165bcb4e9ea4a7585b99f2ed9d"}
     */

    private String msg;
    private int code;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * userId : 3ed475ccc8f24bd08da35e552c85c85a
         * nickname : ︶~ 夏末丶
         * realname :
         * gender : 男
         * birthday : 2018-12-11
         * mobile : 18240011200
         * openid : null
         * avatar : http://jipincheng.cn/c201cd8dd7f04063bfe3f4eed9e79071
         * role : 1
         * qrCode : null
         * authentication : null
         * status : null
         * fansCount : 0
         * voteCount : 0
         * followCount : 0
         * createTime : 2018-12-05 21:26:15
         * updateTime : null
         * token : b254af165bcb4e9ea4a7585b99f2ed9d
         */

        private String userId;
        private String nickname;
        private String realname;
        private String gender;
        private String birthday;
        private String mobile;
        private String openid;
        private String avatar;
        private int role;
        private String qrCode;
        private String authentication;
        private String status;
        private int fansCount;
        private int voteCount;
        private int followCount;
        private String createTime;
        private String updateTime;
        private String token;
        private int point;
        private String articleCount;
        private int bindMobile;
        private int bindWeixin;
        private  int bindWeibo;
        private int addPoint;//新用户获得极币
        private String invitationCode;//邀请码
        private String relationId;//是否登陆过淘宝

        public String getRelationId() {
            return relationId;
        }

        public void setRelationId(String relationId) {
            this.relationId = relationId;
        }

        public String getInvitationCode() {
            return invitationCode;
        }

        public void setInvitationCode(String invitationCode) {
            this.invitationCode = invitationCode;
        }

        public int getAddPoint() {
            return addPoint;
        }

        public void setAddPoint(int addPoint) {
            this.addPoint = addPoint;
        }

        public String getArticleCount() {
            return articleCount;
        }

        public void setArticleCount(String articleCount) {
            this.articleCount = articleCount;
        }

        public int getBindMobile() {
            return bindMobile;
        }

        public void setBindMobile(int bindMobile) {
            this.bindMobile = bindMobile;
        }

        public int getBindWeixin() {
            return bindWeixin;
        }

        public void setBindWeixin(int bindWeixin) {
            this.bindWeixin = bindWeixin;
        }

        public int getBindWeibo() {
            return bindWeibo;
        }

        public void setBindWeibo(int bindWeibo) {
            this.bindWeibo = bindWeibo;
        }

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }

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

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }

        public String getQrCode() {
            return qrCode;
        }

        public void setQrCode(String qrCode) {
            this.qrCode = qrCode;
        }

        public String getAuthentication() {
            return authentication;
        }

        public void setAuthentication(String authentication) {
            this.authentication = authentication;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getFansCount() {
            return fansCount;
        }

        public void setFansCount(int fansCount) {
            this.fansCount = fansCount;
        }

        public int getVoteCount() {
            return voteCount;
        }

        public void setVoteCount(int voteCount) {
            this.voteCount = voteCount;
        }

        public int getFollowCount() {
            return followCount;
        }

        public void setFollowCount(int followCount) {
            this.followCount = followCount;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
