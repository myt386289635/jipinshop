package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/10/16
 * @Describe 抽奖的结果
 */
public class LuckselectBean {

    /**
     * msg : success
     * code : 200
     * list : [{"id":"db5faca2a7244f0881071142848f800e","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","code":"4","code_img":"","mark":"1","state":"1"}]
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
         * id : db5faca2a7244f0881071142848f800e
         * userId : 7d67892cb02f4766aa72fd5b08b8d8d1
         * code : 4
         * code_img :
         * mark : 1
         * state : 1
         */

        private String id;
        private String userId;
        private String code;
        private String code_img;
        private String mark;
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

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCode_img() {
            return code_img;
        }

        public void setCode_img(String code_img) {
            this.code_img = code_img;
        }

        public String getMark() {
            return mark;
        }

        public void setMark(String mark) {
            this.mark = mark;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
