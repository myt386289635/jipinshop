package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/11/15
 * @Describe 评测tab
 */
public class EvaluationTabBean {

    /**
     * msg : success
     * code : 0
     * data : [{"categoryId":"1","type":1,"categoryName":"精选","orderNum":0,"adList":[{"img":"http://jipincheng.cn/24ddace8f8c34153afff6e29d19cc402","type":0,"objectId":"0"}]},{"categoryId":"7ee077bc59e84a2eb2ba85c4cc068d58","type":1,"categoryName":"视频","orderNum":1,"adList":[]},{"categoryId":"17badcca7e3f4147be206eb7fd828046","type":1,"categoryName":"运动","orderNum":2,"adList":[]},{"categoryId":"e3e088bd3d04408f8fc61e210f7fcc11","type":1,"categoryName":"创意","orderNum":3,"adList":[]},{"categoryId":"93bb3583dee4493e9709e4b119bd6bc7","type":1,"categoryName":"居家","orderNum":4,"adList":[]},{"categoryId":"da5d690dd8ff436f9536a5a6b806522d","type":1,"categoryName":"科技","orderNum":5,"adList":[]},{"categoryId":"970bba901dbb44a99d15d06080449a45","type":1,"categoryName":"旅行","orderNum":6,"adList":[]}]
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
         * categoryId : 1
         * type : 1
         * categoryName : 精选
         * orderNum : 0
         * adList : [{"img":"http://jipincheng.cn/24ddace8f8c34153afff6e29d19cc402","type":0,"objectId":"0"}]
         */

        private String categoryId;
        private int type;
        private String categoryName;
        private int orderNum;
        private List<AdListBean> adList;

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public int getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(int orderNum) {
            this.orderNum = orderNum;
        }

        public List<AdListBean> getAdList() {
            return adList;
        }

        public void setAdList(List<AdListBean> adList) {
            this.adList = adList;
        }

        public static class AdListBean {
            /**
             * img : http://jipincheng.cn/24ddace8f8c34153afff6e29d19cc402
             * type : 0
             * objectId : 0
             */

            private String img;
            private int type;
            private String objectId;
            private String name;
            private String source;
            private String remark;

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getObjectId() {
                return objectId;
            }

            public void setObjectId(String objectId) {
                this.objectId = objectId;
            }
        }
    }
}
