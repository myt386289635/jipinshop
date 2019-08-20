package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2018/10/10
 * @Describe
 */
public class UserInfoBean {

    /**
     * msg : success
     * code : 0
     * data : {"userId":"3ed475ccc8f24bd08da35e552c85c85a","nickname":"︶~\u2002夏末丶","realname":"","gender":"男","birthday":"2018-12-11","mobile":"182****1200","openid":null,"avatar":"http://jipincheng.cn/c201cd8dd7f04063bfe3f4eed9e79071","role":1,"qrCode":null,"authentication":null,"status":null,"fansCount":0,"voteCount":0,"followCount":0,"createTime":"2018-12-05 21:26:15","updateTime":null,"token":null,"point":10}
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
         * mobile : 182****1200
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
         * token : null
         * point : 10
         */

        private String userId;
        private String nickname;
        private String realname;
        private String gender;
        private String birthday;
        private String mobile;
        private Object openid;
        private String avatar;
        private int role;
        private Object qrCode;
        private int authentication;
        private Object status;
        private String fansCount;
        private String voteCount;
        private String followCount;
        private String createTime;
        private Object updateTime;
        private Object token;
        private int point;
        private String articleCount;
        private int bindMobile;
        private int bindWeixin;
        private  int bindWeibo;
        private String invitationCode;//邀请码
        private String relationId;//是否登陆过淘宝
        private String bgImg;//背景
        private String detail;//个性签名
        private int follow;//是否关注， 0没关注  1关注
        private int age;//年龄

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public int getFollow() {
            return follow;
        }

        public void setFollow(int follow) {
            this.follow = follow;
        }

        public String getBgImg() {
            return bgImg;
        }

        public void setBgImg(String bgImg) {
            this.bgImg = bgImg;
        }

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

        public Object getOpenid() {
            return openid;
        }

        public void setOpenid(Object openid) {
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

        public Object getQrCode() {
            return qrCode;
        }

        public void setQrCode(Object qrCode) {
            this.qrCode = qrCode;
        }

        public int getAuthentication() {
            return authentication;
        }

        public void setAuthentication(int authentication) {
            this.authentication = authentication;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public String getFansCount() {
            return fansCount;
        }

        public void setFansCount(String fansCount) {
            this.fansCount = fansCount;
        }

        public String getVoteCount() {
            return voteCount;
        }

        public void setVoteCount(String voteCount) {
            this.voteCount = voteCount;
        }

        public String getFollowCount() {
            return followCount;
        }

        public void setFollowCount(String followCount) {
            this.followCount = followCount;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public Object getToken() {
            return token;
        }

        public void setToken(Object token) {
            this.token = token;
        }

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }
    }
}
