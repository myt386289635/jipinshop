package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/11/26
 * @Describe
 */
public class GroupInfoBean {

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
         * id : d09852ccf15b45b39e7b233e08790847
         * createUserId : 2
         * joinUserId : 2
         * source : 2
         * otherGoodsId : 622381661360
         * startTime : 2020-11-26 10:08:21
         * endTime : 2020-11-26 15:08:21
         * timeToEndTime : 1606374501
         * createTime : 2020-11-26 10:08:21
         * status : 0
         * count : 2
         * leftCount : 1
         * tradeId :
         * avatar : http://jipincheng.cn/avatar/img/20200731/3fb6ec503d614353abdbaa67f86c652a
         * avatarList : ["http://jipincheng.cn/avatar/img/20200731/3fb6ec503d614353abdbaa67f86c652a"]
         * img : https://img.alicdn.com/bao/uploaded/i4/2207905066191/O1CN01Kfx8C31vbWfgVTAF3_!!0-item_pic.jpg
         * goodsName : 正品田七中草药牙膏去黄口臭牙垢亮白口气清新清热祛火家庭实惠装
         * fee : 11.97
         * upFee : 7.48
         */

        private String id;
        private String createUserId;
        private String joinUserId;
        private int source;
        private String otherGoodsId;
        private String startTime;
        private String endTime;
        private long timeToEndTime;
        private String createTime;
        private int status;
        private int count;
        private int leftCount;
        private String tradeId;
        private String avatar;
        private String img;
        private String goodsName;
        private String fee;
        private String upFee;
        private List<String> avatarList;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(String createUserId) {
            this.createUserId = createUserId;
        }

        public String getJoinUserId() {
            return joinUserId;
        }

        public void setJoinUserId(String joinUserId) {
            this.joinUserId = joinUserId;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public String getOtherGoodsId() {
            return otherGoodsId;
        }

        public void setOtherGoodsId(String otherGoodsId) {
            this.otherGoodsId = otherGoodsId;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public long getTimeToEndTime() {
            return timeToEndTime;
        }

        public void setTimeToEndTime(long timeToEndTime) {
            this.timeToEndTime = timeToEndTime;
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

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getLeftCount() {
            return leftCount;
        }

        public void setLeftCount(int leftCount) {
            this.leftCount = leftCount;
        }

        public String getTradeId() {
            return tradeId;
        }

        public void setTradeId(String tradeId) {
            this.tradeId = tradeId;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getUpFee() {
            return upFee;
        }

        public void setUpFee(String upFee) {
            this.upFee = upFee;
        }

        public List<String> getAvatarList() {
            return avatarList;
        }

        public void setAvatarList(List<String> avatarList) {
            this.avatarList = avatarList;
        }
    }
}
