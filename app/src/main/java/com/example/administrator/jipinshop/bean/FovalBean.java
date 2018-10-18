package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/10/10
 * @Describe
 */
public class FovalBean {


    /**
     * msg : success
     * code : 200
     * list : [{"id":"11","user_id":"7d67892cb02f4766aa72fd5b08b8d8d1","project_id":"6","user_name":"33","collect_message":"45","state":"66","create_time":"2018-10-12T09:06:27.000+0000","collect_name":"22","collect_price":"33","collect_nubmer":"","product_img":" ","product_tab":"格力,环保,榨汁,声波","product_price":"0","product_resouce":"","product_tabs":["格力","环保","榨汁","声波"]},{"id":"10","user_id":"7d67892cb02f4766aa72fd5b08b8d8d1","project_id":"77","user_name":"77","collect_message":"77","state":"77","create_time":"2018-10-08T09:06:09.000+0000","collect_name":"7","collect_price":"7","collect_nubmer":"7","product_img":" ","product_tab":"格力,环保,榨汁,声波","product_price":"0","product_resouce":"","product_tabs":["格力","环保","榨汁","声波"]},{"id":"1","user_id":"7d67892cb02f4766aa72fd5b08b8d8d1","project_id":"3","user_name":"4","collect_message":"5","state":"6","create_time":"2018-10-08T09:02:30.000+0000","collect_name":"8","collect_price":"9","collect_nubmer":"10","product_img":" ","product_tab":"格力,环保,榨汁,声波","product_price":"0","product_resouce":"","product_tabs":["格力","环保","榨汁","声波"]},{"id":"9","user_id":"7d67892cb02f4766aa72fd5b08b8d8d1","project_id":"66","user_name":"66","collect_message":"66","state":"66","create_time":"2018-10-07T09:05:54.000+0000","collect_name":"66","collect_price":"66","collect_nubmer":"","product_img":null,"product_tab":"格力,环保,榨汁,声波","product_price":"0","product_resouce":"","product_tabs":["格力","环保","榨汁","声波"]},{"id":"2","user_id":"7d67892cb02f4766aa72fd5b08b8d8d1","project_id":"3","user_name":"3","collect_message":"3","state":"4","create_time":"2018-10-07T09:03:18.000+0000","collect_name":"4","collect_price":"4","collect_nubmer":"5","product_img":" ","product_tab":"格力,环保,榨汁,声波","product_price":"0","product_resouce":"","product_tabs":["格力","环保","榨汁","声波"]},{"id":"3","user_id":"7d67892cb02f4766aa72fd5b08b8d8d1","project_id":"4","user_name":"5","collect_message":"6","state":"4","create_time":"2018-10-06T09:03:40.000+0000","collect_name":"5","collect_price":"","collect_nubmer":"","product_img":" ","product_tab":"格力,环保,榨汁,声波","product_price":"0","product_resouce":"","product_tabs":["格力","环保","榨汁","声波"]},{"id":"4","user_id":"7d67892cb02f4766aa72fd5b08b8d8d1","project_id":"5","user_name":"6","collect_message":"7","state":"7","create_time":"2018-10-05T09:03:54.000+0000","collect_name":"4","collect_price":"4","collect_nubmer":"","product_img":" ","product_tab":"格力,环保,榨汁,声波","product_price":"0","product_resouce":"","product_tabs":["格力","环保","榨汁","声波"]},{"id":"6","user_id":"7d67892cb02f4766aa72fd5b08b8d8d1","project_id":"8","user_name":"9","collect_message":"9","state":"9","create_time":"2018-10-04T09:04:24.000+0000","collect_name":"4","collect_price":"","collect_nubmer":"","product_img":" ","product_tab":"格力,环保,榨汁,声波","product_price":"0","product_resouce":"","product_tabs":["格力","环保","榨汁","声波"]},{"id":"8","user_id":"7d67892cb02f4766aa72fd5b08b8d8d1","project_id":"9","user_name":"11","collect_message":"11","state":"11","create_time":"2018-10-03T09:04:56.000+0000","collect_name":"122","collect_price":"","collect_nubmer":"","product_img":" ","product_tab":"格力,环保,榨汁,声波","product_price":"0","product_resouce":"","product_tabs":["格力","环保","榨汁","声波"]},{"id":"7","user_id":"7d67892cb02f4766aa72fd5b08b8d8d1","project_id":"33","user_name":"33","collect_message":"3","state":"3","create_time":"2018-10-02T09:04:36.000+0000","collect_name":"2","collect_price":"","collect_nubmer":"","product_img":" ","product_tab":"格力,环保,榨汁,声波","product_price":"0","product_resouce":"","product_tabs":["格力","环保","榨汁","声波"]}]
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
         * id : 11
         * user_id : 7d67892cb02f4766aa72fd5b08b8d8d1
         * project_id : 6
         * user_name : 33
         * collect_message : 45
         * state : 66
         * create_time : 2018-10-12T09:06:27.000+0000
         * collect_name : 22
         * collect_price : 33
         * collect_nubmer :
         * product_img :
         * product_tab : 格力,环保,榨汁,声波
         * product_price : 0
         * product_resouce :
         * product_tabs : ["格力","环保","榨汁","声波"]
         */

        private String id;
        private String user_id;
        private String project_id;
        private String user_name;
        private String collect_message;
        private String state;
        private String create_time;
        private String collect_name;
        private String collect_price;
        private String collect_nubmer;
        private String product_img;
        private String product_tab;
        private String product_price;
        private String product_resouce;
        private List<String> product_tabs;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getProject_id() {
            return project_id;
        }

        public void setProject_id(String project_id) {
            this.project_id = project_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getCollect_message() {
            return collect_message;
        }

        public void setCollect_message(String collect_message) {
            this.collect_message = collect_message;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getCollect_name() {
            return collect_name;
        }

        public void setCollect_name(String collect_name) {
            this.collect_name = collect_name;
        }

        public String getCollect_price() {
            return collect_price;
        }

        public void setCollect_price(String collect_price) {
            this.collect_price = collect_price;
        }

        public String getCollect_nubmer() {
            return collect_nubmer;
        }

        public void setCollect_nubmer(String collect_nubmer) {
            this.collect_nubmer = collect_nubmer;
        }

        public String getProduct_img() {
            return product_img;
        }

        public void setProduct_img(String product_img) {
            this.product_img = product_img;
        }

        public String getProduct_tab() {
            return product_tab;
        }

        public void setProduct_tab(String product_tab) {
            this.product_tab = product_tab;
        }

        public String getProduct_price() {
            return product_price;
        }

        public void setProduct_price(String product_price) {
            this.product_price = product_price;
        }

        public String getProduct_resouce() {
            return product_resouce;
        }

        public void setProduct_resouce(String product_resouce) {
            this.product_resouce = product_resouce;
        }

        public List<String> getProduct_tabs() {
            return product_tabs;
        }

        public void setProduct_tabs(List<String> product_tabs) {
            this.product_tabs = product_tabs;
        }
    }
}
