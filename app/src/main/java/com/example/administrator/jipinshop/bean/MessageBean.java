package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/11/13
 * @Describe 消息列表
 */
public class MessageBean {

    /**
     * msg : success
     * code : 0
     * data : [{"id":"1","title":"每日好货","content":"每日好货","img":"http://jipincheng.cn/category/img/20210401/7e59640b4048479cafd942590a508724","orderNum":1},{"id":"2","title":"活动公告","content":"活动公告","img":"http://jipincheng.cn/category/img/20210401/7e59640b4048479cafd942590a508724","orderNum":2},{"id":"3","title":"系统消息","content":"系统消息","img":"http://jipincheng.cn/category/img/20210401/7e59640b4048479cafd942590a508724","orderNum":3},{"id":"4","title":"福利消息","content":"福利消息","img":"http://jipincheng.cn/category/img/20210401/7e59640b4048479cafd942590a508724","orderNum":4},{"id":"5","title":"佣金消息","content":"佣金消息","img":"http://jipincheng.cn/category/img/20210401/7e59640b4048479cafd942590a508724","orderNum":5}]
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
         * title : 每日好货
         * content : 每日好货
         * img : http://jipincheng.cn/category/img/20210401/7e59640b4048479cafd942590a508724
         * orderNum : 1
         */

        private String id;
        private String title;
        private String content;
        private String img;
        private String orderNum;
        private int count;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }
    }
}
