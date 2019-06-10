package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/6/6
 * @Describe
 */
public class WithdrawDetailBean {

    /**
     * msg : success
     * code : 0
     * data : [{"id":"1","userId":"2","amount":1,"realname":"1111","account":"111","status":0,"failReason":null,"checkTime":"2019-06-06 16:34:30","createTime":"2019-06-06 16:34:32"}]
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
         * userId : 2
         * amount : 1
         * realname : 1111
         * account : 111
         * status : 0
         * failReason : null
         * checkTime : 2019-06-06 16:34:30
         * createTime : 2019-06-06 16:34:32
         */

        private String id;
        private String userId;
        private String amount;
        private String realname;
        private String account;
        private int status;
        private String failReason;
        private String checkTime;
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

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getFailReason() {
            return failReason;
        }

        public void setFailReason(String failReason) {
            this.failReason = failReason;
        }

        public String getCheckTime() {
            return checkTime;
        }

        public void setCheckTime(String checkTime) {
            this.checkTime = checkTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
