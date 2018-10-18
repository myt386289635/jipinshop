package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/10/17
 * @Describe 初始化抽奖图片
 */
public class LuckImageBean {

    /**
     * msg : success
     * code : 200
     * list : [{"id":"1","numbers":1,"code_img":"http://192.168.5.182:8083/img/1539767586215prize-100integral.png","mark":"100元积分"},{"id":"2","numbers":2,"code_img":"http://192.168.5.182:8083/img/1539767616882prize-200integral.png","mark":"200元积分"},{"id":"3","numbers":3,"code_img":"http://192.168.5.182:8083/img/1539767645573prize-hairdrier.png","mark":"吹风机"},{"id":"4","numbers":4,"code_img":"http://192.168.5.182:8083/img/1539767672322prize-ricecooker.png","mark":"煲饭锅"},{"id":"5","numbers":5,"code_img":"http://192.168.5.182:8083/img/1539767703851prize-voucher.png","mark":"免费体验劵"},{"id":"6","numbers":6,"code_img":"http://192.168.5.182:8083/img/1539767777199prize-10coupon.png","mark":"10元优惠券"},{"id":"7","numbers":7,"code_img":"http://192.168.5.182:8083/img/1539767925851prize-kettle.png","mark":""},{"id":"8","numbers":0,"code_img":"http://192.168.5.182:8083/img/1539767434129prize-5coupon.png","mark":"5元优惠券"}]
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
         * id : 1
         * numbers : 1
         * code_img : http://192.168.5.182:8083/img/1539767586215prize-100integral.png
         * mark : 100元积分
         */

        private String id;
        private int numbers;
        private String code_img;
        private String mark;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getNumbers() {
            return numbers;
        }

        public void setNumbers(int numbers) {
            this.numbers = numbers;
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
    }
}
