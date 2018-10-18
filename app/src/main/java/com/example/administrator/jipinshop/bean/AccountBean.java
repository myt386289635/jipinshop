package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/10/9
 * @Describe 佣金金额
 */
public class AccountBean {

    /**
     * msg : success
     * code : 200
     * list : [{"id":"cfc8d1a358264fa4959d50405bbd68f7","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","total_account":0,"use_account":0,"unuse_account":0,"create_time":"2018-10-09T06:47:35.000+0000","state":"0"}]
     */

    private String msg;
    private int code;
    private List<ListBean> list;

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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : cfc8d1a358264fa4959d50405bbd68f7
         * userId : 7d67892cb02f4766aa72fd5b08b8d8d1
         * total_account : 0
         * use_account : 0
         * unuse_account : 0
         * create_time : 2018-10-09T06:47:35.000+0000
         * state : 0
         */

        private String id;
        private String userId;
        private String total_account;
        private String use_account;
        private String unuse_account;
        private String create_time;
        private String state;

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

        public String getTotal_account() {
            return total_account;
        }

        public void setTotal_account(String total_account) {
            this.total_account = total_account;
        }

        public String getUse_account() {
            return use_account;
        }

        public void setUse_account(String use_account) {
            this.use_account = use_account;
        }

        public String getUnuse_account() {
            return unuse_account;
        }

        public void setUnuse_account(String unuse_account) {
            this.unuse_account = unuse_account;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
