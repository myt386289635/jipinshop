package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/12/30
 * @Describe
 */
public class SeckillTabBean {


    /**
     * msg : success
     * code : 0
     * data : [{"id":"e6b56d33443f4a8bacd57471268a1c25","title":"第一场","startTime":"1970-01-01 00:00:00","endTime":"1970-01-01 00:59:00","createTime":"2020-12-28 15:34:58","status":1},{"id":"bf5ac75ff705474083e82441383b27a8","title":"第二场","startTime":"1970-01-01 10:00:00","endTime":"1970-01-01 10:59:00","createTime":"2020-12-28 15:34:58","status":1},{"id":"4e62b465acce455284651836cf6790e0","title":"第三场","startTime":"1970-01-01 12:00:00","endTime":"1970-01-01 12:59:00","createTime":"2020-12-29 14:21:27","status":1},{"id":"7042a6437474463bafc85bac4a014a07","title":"第四场","startTime":"1970-01-01 15:00:00","endTime":"1970-01-01 15:59:00","createTime":"2020-12-29 14:32:00","status":1},{"id":"3da6fdd5c31542d0bc5e1648b5d844b3","title":"第五场","startTime":"1970-01-01 19:00:00","endTime":"1970-01-01 19:59:00","createTime":"2020-12-29 16:01:34","status":1}]
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
         * id : e6b56d33443f4a8bacd57471268a1c25
         * title : 第一场
         * startTime : 1970-01-01 00:00:00
         * endTime : 1970-01-01 00:59:00
         * createTime : 2020-12-28 15:34:58
         * status : 1
         */

        private String id;
        private String title;
        private String startTime;
        private String endTime;
        private String createTime;
        private int status;

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

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
