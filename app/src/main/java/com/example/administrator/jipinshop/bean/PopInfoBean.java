package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2019/9/24
 * @Describe
 */
public class PopInfoBean {

    private String msg;
    private int code;
    private DataBeanX data;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * type : 1
         * data : {"id":"1","title":"2342342342342","type":1,"userGroup":1,"img":"https://jipincheng.cn/cj.png","startTime":"2019-09-26 00:00:00","endTime":"2019-10-28 00:00:00","publishTime":"2019-09-19 14:30:51","targetType":21,"targetId":"a73307f581304c028ceda313c545f622","status":1,"createTime":"2019-09-17 15:30:36"}
         */

        private int type;
        private DataBean data;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
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
             * title : 2342342342342
             * type : 1
             * userGroup : 1
             * img : https://jipincheng.cn/cj.png
             * startTime : 2019-09-26 00:00:00
             * endTime : 2019-10-28 00:00:00
             * publishTime : 2019-09-19 14:30:51
             * targetType : 21
             * targetId : a73307f581304c028ceda313c545f622
             * status : 1
             * createTime : 2019-09-17 15:30:36
             */

            private String id;
            private String title;
            private int type;
            private int userGroup;
            private String img;
            private String startTime;
            private String endTime;
            private String publishTime;
            private String targetType;
            private String targetId;
            private int status;
            private String createTime;
            private String actualPrice;
            private String dendlineTime;
            private String freePrice;
            private String name;


            public String getActualPrice() {
                return actualPrice;
            }

            public void setActualPrice(String actualPrice) {
                this.actualPrice = actualPrice;
            }

            public String getDendlineTime() {
                return dendlineTime;
            }

            public void setDendlineTime(String dendlineTime) {
                this.dendlineTime = dendlineTime;
            }

            public String getFreePrice() {
                return freePrice;
            }

            public void setFreePrice(String freePrice) {
                this.freePrice = freePrice;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

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

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getUserGroup() {
                return userGroup;
            }

            public void setUserGroup(int userGroup) {
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

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }
    }
}
