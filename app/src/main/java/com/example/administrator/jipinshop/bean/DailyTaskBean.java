package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/3/12
 * @Describe
 */
public class DailyTaskBean {

    /**
     * msg : success
     * code : 0
     * data : [{"id":"6","type":6,"point":300,"title":"评论送币","content":"评论送币","createTime":"2019-03-01 13:57:45"},{"id":"5","type":5,"point":100,"title":"阅读文章","content":"阅读文章","createTime":"2019-03-01 13:57:17"},{"id":"4","type":4,"point":60,"title":"分享内容","content":"分享内容","createTime":"2019-03-01 13:56:53"},{"id":"3","type":3,"point":50,"title":"点赞送极币","content":"点赞文章","createTime":"2019-03-01 13:56:35"},{"id":"2","type":2,"point":200,"title":"申请认证","content":"申请认证","createTime":"2019-03-01 13:55:43"},{"id":"1","type":1,"point":100,"title":"邀请好友","content":"每邀请一位好友下载APP并登录成功，奖励600极币，你邀请的好友每次邀请他人成功，你再获得300极币","createTime":"2019-03-01 13:53:45"}]
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
         * id : 6
         * type : 6
         * point : 300
         * title : 评论送币
         * content : 评论送币
         * createTime : 2019-03-01 13:57:45
         */

        private String id;
        private int type;
        private int point;
        private int location;
        private String title;
        private String content;
        private String createTime;

        public int getLocation() {
            return location;
        }

        public void setLocation(int location) {
            this.location = location;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
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

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
