package com.example.administrator.jipinshop.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/3/6
 * @Describe
 */
public class AddressBean implements Serializable{

    /**
     * msg : success
     * code : 0
     * data : [{"id":"0c1e4a609f9343a2ae322a42d1ed7d98","userId":"5c6fe716a5ad44259f2111b52fe13617","username":"莫玉婷","mobile":"18240011200","address":"浙江省杭州市滨江区西陵路888号海威大厦712号畅卓电商","status":null,"createTime":"2019-03-06 10:45:11"}]
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

    public static class DataBean implements Serializable{
        /**
         * id : 0c1e4a609f9343a2ae322a42d1ed7d98
         * userId : 5c6fe716a5ad44259f2111b52fe13617
         * username : 莫玉婷
         * mobile : 18240011200
         * address : 浙江省杭州市滨江区西陵路888号海威大厦712号畅卓电商
         * status : null
         * createTime : 2019-03-06 10:45:11
         */

        private String id;
        private String userId;
        private String username;
        private String mobile;
        private String address;
        private String status;
        private String createTime;
        private String area;

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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
    }
}
