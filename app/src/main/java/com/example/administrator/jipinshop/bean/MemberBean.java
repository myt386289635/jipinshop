package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/4/2
 * @Describe
 */
public class MemberBean {


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
         * avatar : http://jipincheng.cn/avatar/img/20200228/0e91f55a7254431a92678fb75e9210d5
         * nickname : 小婷
         * level : 0
         * invitedUserCount : 0
         * levelInvitedUserCount : 0
         * commission : 0
         * levelCommission : 0.0
         * pAvatar : https://jipincheng.cn/defaultImg.png
         * pWechat : jpkele
         * imgList : ["http://jipincheng.cn/activity/img/20200402/f67ef79347624812a0cf849b1e49a261","http://jipincheng.cn/activity/img/20200402/56c0d324bef244919a9f93c9bb142a02","http://jipincheng.cn/activity/img/20200402/848e770c07664e99bed28048241fc233"]
         * messageList : [{"id":"11","avatar":"http://jipincheng.cn/avatar/img/20191223/61b3cbc30fa140f99f45b0b97aba6565","content":"嘿马小丸子刚刚成为极品城vip","type":2},{"id":"22","avatar":"http://jipincheng.cn/avatar/img/20191223/0972ae743b1949558c64ed359abfbac2","content":"郭传霞刚刚成为极品城vip","type":2},{"id":"222","avatar":"http://jipincheng.cn/avatar/img/20191223/ba4eecf20e6f43b3b85acc30cf71be7c","content":"果果子刚刚成为极品城vip","type":2}]
         */

        private String avatar;
        private String nickname;
        private int level; //会员等级
        private int invitedUserCount; //直属粉丝
        private int levelInvitedUserCount; //需要邀请的直属粉丝
        private int levelStatus; //会员状态 （是否在申请中）  已没用
        private String pAvatar; //导师头像
        private String pWechat; //导师微信
        private List<String> imgList;
        private List<MessageListBean> messageList;
        private int point; //极币数
        private String img;//尊享特权图片
        private String levelEndTime;//临时会员到期时间
        private int levelPoint;//永久会员升级积分
        private int monthLevelPoint;//会员设计临时月度积分
        private int monthLevelInvitedUserCount;

        public int getMonthLevelInvitedUserCount() {
            return monthLevelInvitedUserCount;
        }

        public void setMonthLevelInvitedUserCount(int monthLevelInvitedUserCount) {
            this.monthLevelInvitedUserCount = monthLevelInvitedUserCount;
        }

        public int getLevelPoint() {
            return levelPoint;
        }

        public void setLevelPoint(int levelPoint) {
            this.levelPoint = levelPoint;
        }

        public int getMonthLevelPoint() {
            return monthLevelPoint;
        }

        public void setMonthLevelPoint(int monthLevelPoint) {
            this.monthLevelPoint = monthLevelPoint;
        }

        public String getLevelEndTime() {
            return levelEndTime;
        }

        public void setLevelEndTime(String levelEndTime) {
            this.levelEndTime = levelEndTime;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }

        public int getLevelStatus() {
            return levelStatus;
        }

        public void setLevelStatus(int levelStatus) {
            this.levelStatus = levelStatus;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getInvitedUserCount() {
            return invitedUserCount;
        }

        public void setInvitedUserCount(int invitedUserCount) {
            this.invitedUserCount = invitedUserCount;
        }

        public int getLevelInvitedUserCount() {
            return levelInvitedUserCount;
        }

        public void setLevelInvitedUserCount(int levelInvitedUserCount) {
            this.levelInvitedUserCount = levelInvitedUserCount;
        }

        public String getPAvatar() {
            return pAvatar;
        }

        public void setPAvatar(String pAvatar) {
            this.pAvatar = pAvatar;
        }

        public String getPWechat() {
            return pWechat;
        }

        public void setPWechat(String pWechat) {
            this.pWechat = pWechat;
        }

        public List<String> getImgList() {
            return imgList;
        }

        public void setImgList(List<String> imgList) {
            this.imgList = imgList;
        }

        public List<MessageListBean> getMessageList() {
            return messageList;
        }

        public void setMessageList(List<MessageListBean> messageList) {
            this.messageList = messageList;
        }

        public static class MessageListBean {
            /**
             * id : 11
             * avatar : http://jipincheng.cn/avatar/img/20191223/61b3cbc30fa140f99f45b0b97aba6565
             * content : 嘿马小丸子刚刚成为极品城vip
             * type : 2
             */

            private String id;
            private String avatar;
            private String content;
            private int type;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
