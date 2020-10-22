package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/9/24
 * @Describe
 */
public class PopInfoBean {

    private String msg;
    private int code;
    private List<DataBeanX> data;

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

    public List<DataBeanX> getData() {
        return data;
    }

    public void setData(List<DataBeanX> data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * type : 0
         * popId : 8cde031535aa49ad8a7b1a57062bfb3b
         * data : {"id":"0","title":"00000000000","type":1,"userGroup":1,"img":"http://jipincheng.cn/activity/img/20200108/b1faeb859c7c40eaa05242d75f7ff72c","startTime":"2019-11-01 00:00:00","endTime":"2019-12-31 00:00:00","publishTime":"2019-11-25 17:32:08","targetType":12,"targetId":"542736ea48f847dc9354732cc10b9981","status":-1,"createTime":"2019-09-18 14:23:22","fromId":"8cde031535aa49ad8a7b1a57062bfb3b"}
         */

        private int type;
        private String popId;
        private DataBean data;
        private String addAllowancePrice;
        private List<PopBean.DataBean.AllowanceGoodsListBean> allowanceGoodsList;

        public String getAddAllowancePrice() {
            return addAllowancePrice;
        }

        public void setAddAllowancePrice(String addAllowancePrice) {
            this.addAllowancePrice = addAllowancePrice;
        }

        public List<PopBean.DataBean.AllowanceGoodsListBean> getAllowanceGoodsList() {
            return allowanceGoodsList;
        }

        public void setAllowanceGoodsList(List<PopBean.DataBean.AllowanceGoodsListBean> allowanceGoodsList) {
            this.allowanceGoodsList = allowanceGoodsList;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getPopId() {
            return popId;
        }

        public void setPopId(String popId) {
            this.popId = popId;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 0
             * title : 00000000000
             * type : 1
             * userGroup : 1
             * img : http://jipincheng.cn/activity/img/20200108/b1faeb859c7c40eaa05242d75f7ff72c
             * startTime : 2019-11-01 00:00:00
             * endTime : 2019-12-31 00:00:00
             * publishTime : 2019-11-25 17:32:08
             * targetType : 12
             * targetId : 542736ea48f847dc9354732cc10b9981
             * status : -1
             * createTime : 2019-09-18 14:23:22
             * fromId : 8cde031535aa49ad8a7b1a57062bfb3b
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
            private String fromId;
            private String source;

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
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

            public String getFromId() {
                return fromId;
            }

            public void setFromId(String fromId) {
                this.fromId = fromId;
            }
        }
    }
}
