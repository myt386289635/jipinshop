package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2019/3/14
 * @Describe 默认地址
 */
public class DefaultAddressBean {

    /**
     * msg : success
     * code : 0
     * data : {"id":"87c0c9882c544d649971a59ddf53e306","userId":"5c6fe716a5ad44259f2111b52fe13617","username":"莫玉婷","mobile":"18240011200","area":"浙江省-杭州市-滨江区","address":"海蔚大厦二十三楼","status":1,"createTime":"2019-03-08 10:03:43"}
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
         * id : 87c0c9882c544d649971a59ddf53e306
         * userId : 5c6fe716a5ad44259f2111b52fe13617
         * username : 莫玉婷
         * mobile : 18240011200
         * area : 浙江省-杭州市-滨江区
         * address : 海蔚大厦二十三楼
         * status : 1
         * createTime : 2019-03-08 10:03:43
         */

        private String id;
        private String userId;
        private String username;
        private String mobile;
        private String area;
        private String address;
        private int status;
        private String createTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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
