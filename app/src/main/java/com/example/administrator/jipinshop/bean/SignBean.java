package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/10/11
 * @Describe
 */
public class SignBean {

    /**
     * msg : success
     * code : 200
     * list : [{"sign_id":"111","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","sign_poins":"3","sign_state":"1","sign_count":0,"sign_time":"2018-10-11T04:47:23.000+0000","arrays_time":"星期四","week":["星期四"]}]
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
         * sign_id : 111
         * userId : 7d67892cb02f4766aa72fd5b08b8d8d1
         * sign_poins : 3
         * sign_state : 1
         * sign_count : 0
         * sign_time : 2018-10-11T04:47:23.000+0000
         * arrays_time : 星期四
         * week : ["星期四"]
         */

        private String sign_id;
        private String userId;
        private String sign_poins;
        private String sign_state;
        private int sign_count;
        private String sign_time;
        private String arrays_time;
        private List<String> week;

        public String getSign_id() {
            return sign_id;
        }

        public void setSign_id(String sign_id) {
            this.sign_id = sign_id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getSign_poins() {
            return sign_poins;
        }

        public void setSign_poins(String sign_poins) {
            this.sign_poins = sign_poins;
        }

        public String getSign_state() {
            return sign_state;
        }

        public void setSign_state(String sign_state) {
            this.sign_state = sign_state;
        }

        public int getSign_count() {
            return sign_count;
        }

        public void setSign_count(int sign_count) {
            this.sign_count = sign_count;
        }

        public String getSign_time() {
            return sign_time;
        }

        public void setSign_time(String sign_time) {
            this.sign_time = sign_time;
        }

        public String getArrays_time() {
            return arrays_time;
        }

        public void setArrays_time(String arrays_time) {
            this.arrays_time = arrays_time;
        }

        public List<String> getWeek() {
            return week;
        }

        public void setWeek(List<String> week) {
            this.week = week;
        }
    }
}
