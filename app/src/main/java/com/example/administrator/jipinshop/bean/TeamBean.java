package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/6/11
 * @Describe
 */
public class TeamBean {
    /**
     * msg : success
     * code : 0
     * data : [{"nickname":"Test1","avatar":"http://jipincheng.cn/avatar/img/20190513/4e7cb9c95757450db915a3bf8f826dc6","createTime":"2019-01-21 14:06:57","shareCommission":0,"commission":0},{"nickname":"我真的爱奇异","avatar":"http://jipincheng.cn/avatar/img/20190412/b3c178237d624bb192a6e087039a8928","createTime":"2019-05-30 13:55:29","shareCommission":0,"commission":0}]
     */

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
        /**
         * nickname : Test1
         * avatar : http://jipincheng.cn/avatar/img/20190513/4e7cb9c95757450db915a3bf8f826dc6
         * createTime : 2019-01-21 14:06:57
         * shareCommission : 0
         * commission : 0
         */

        private String nickname;
        private String avatar;
        private String createTime;
        private String shareCommission;
        private String commission;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getShareCommission() {
            return shareCommission;
        }

        public void setShareCommission(String shareCommission) {
            this.shareCommission = shareCommission;
        }

        public String getCommission() {
            return commission;
        }

        public void setCommission(String commission) {
            this.commission = commission;
        }
    }
}
