package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2021/4/28
 * @Describe
 */
public class NewPopInfoBean {


    /**
     * msg : success
     * code : 0
     * data : {"id":"1","title":"新人0元购","type":1,"userGroup":1,"img":"http://jipincheng.cn/activity/img/20210427/e13d4f9f5d6247d3909ab8a640e51db1","startTime":null,"endTime":null,"publishTime":null,"targetType":54,"targetId":"","status":1,"createTime":"2021-04-26 16:31:49","fromId":null,"source":"0","remark":""}
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
         * id : 1
         * title : 新人0元购
         * type : 1
         * userGroup : 1
         * img : http://jipincheng.cn/activity/img/20210427/e13d4f9f5d6247d3909ab8a640e51db1
         * startTime : null
         * endTime : null
         * publishTime : null
         * targetType : 54
         * targetId :
         * status : 1
         * createTime : 2021-04-26 16:31:49
         * fromId : null
         * source : 0
         * remark :
         */

        private String id;
        private String title;
        private String type;
        private String userGroup;
        private String img;
        private String startTime;
        private String endTime;
        private String publishTime;
        private String targetType;
        private String targetId;
        private String status;
        private String createTime;
        private String fromId;
        private String source;
        private String remark;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUserGroup() {
            return userGroup;
        }

        public void setUserGroup(String userGroup) {
            this.userGroup = userGroup;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
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

        public String getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(String publishTime) {
            this.publishTime = publishTime;
        }

        public String getTargetType() {
            return targetType;
        }

        public void setTargetType(String targetType) {
            this.targetType = targetType;
        }

        public String getTargetId() {
            return targetId;
        }

        public void setTargetId(String targetId) {
            this.targetId = targetId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getFromId() {
            return fromId;
        }

        public void setFromId(String fromId) {
            this.fromId = fromId;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
