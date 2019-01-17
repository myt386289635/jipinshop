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
     * code : 0
     * data : [{"userId":"7d67892cb02f4766aa72fd5b08b8d8d1","nickname":"皮Sir","avatar":"http://jipincheng.cn/dafca5b3d66a43dc9cc00e07acd7bdb0","status":0}]
     */

    private String msg;
    private int code;
    private int total;
    private List<DataBean> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

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
         * userId : 7d67892cb02f4766aa72fd5b08b8d8d1
         * nickname : 皮Sir
         * avatar : http://jipincheng.cn/dafca5b3d66a43dc9cc00e07acd7bdb0
         * status : 0
         */

        private String userId;
        private String nickname;
        private String avatar;
        private int status;
        private int follow;

        public int getFollow() {
            return follow;
        }

        public void setFollow(int follow) {
            this.follow = follow;
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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
