package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/6/24
 * @Describe 免购——参与名单
 */
public class FreeUserListBean {

    /**
     * msg : success
     * code : 0
     * data : [{"id":"1","freeId":"0993bbdc22e548a9a5b0da541b8648b1","userId":"1","otherGoodsId":null,"nickname":"1","avatar":"1","freeRate":1,"ismyself":0,"createTime":"2019-06-20 17:30:31"},{"id":"1164b5df4162483d863560672cd54812","freeId":"0993bbdc22e548a9a5b0da541b8648b1","userId":"2","otherGoodsId":"522221080171","nickname":"小胖子","avatar":"http://jipincheng.cn/avatar/img/20190603/0b97ccc1392745a3bde6deef9c2391af","freeRate":1,"ismyself":1,"createTime":"2019-06-24 16:04:31"}]
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
         * id : 1
         * freeId : 0993bbdc22e548a9a5b0da541b8648b1
         * userId : 1
         * otherGoodsId : null
         * nickname : 1
         * avatar : 1
         * freeRate : 1
         * ismyself : 0
         * createTime : 2019-06-20 17:30:31
         */

        private String id;
        private String freeId;
        private String userId;
        private String otherGoodsId;
        private String nickname;
        private String avatar;
        private double freeRate;
        private int ismyself;
        private String createTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFreeId() {
            return freeId;
        }

        public void setFreeId(String freeId) {
            this.freeId = freeId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getOtherGoodsId() {
            return otherGoodsId;
        }

        public void setOtherGoodsId(String otherGoodsId) {
            this.otherGoodsId = otherGoodsId;
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

        public double getFreeRate() {
            return freeRate;
        }

        public void setFreeRate(double freeRate) {
            this.freeRate = freeRate;
        }

        public int getIsmyself() {
            return ismyself;
        }

        public void setIsmyself(int ismyself) {
            this.ismyself = ismyself;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
