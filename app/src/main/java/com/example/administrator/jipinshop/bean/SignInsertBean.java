package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2018/10/11
 * @Describe
 */
public class SignInsertBean {

    /**
     * msg : success
     * code : 0
     * data : {"id":"0ed77c879d4e43babb1eee6a99dbfeb3","userId":"3ed475ccc8f24bd08da35e552c85c85a","totalPoint":30,"usablePoint":20,"todayPoint":0,"status":1,"remark":"新用户开启积分功能","createTime":"2018-12-05 21:26:16","updateTime":"2019-01-18 15:37:27"}
     * addPoint : 6
     */

    private String msg;
    private int code;
    private DataBean data;
    private int addPoint;
    private int daysCount;

    public int getDaysCount() {
        return daysCount;
    }

    public void setDaysCount(int daysCount) {
        this.daysCount = daysCount;
    }

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getAddPoint() {
        return addPoint;
    }

    public void setAddPoint(int addPoint) {
        this.addPoint = addPoint;
    }

    public static class DataBean {
        /**
         * id : 0ed77c879d4e43babb1eee6a99dbfeb3
         * userId : 3ed475ccc8f24bd08da35e552c85c85a
         * totalPoint : 30
         * usablePoint : 20
         * todayPoint : 0
         * status : 1
         * remark : 新用户开启积分功能
         * createTime : 2018-12-05 21:26:16
         * updateTime : 2019-01-18 15:37:27
         */

        private String id;
        private String userId;
        private int totalPoint;
        private int usablePoint;
        private int todayPoint;
        private int status;
        private String remark;
        private String createTime;
        private String updateTime;

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

        public int getTotalPoint() {
            return totalPoint;
        }

        public void setTotalPoint(int totalPoint) {
            this.totalPoint = totalPoint;
        }

        public int getUsablePoint() {
            return usablePoint;
        }

        public void setUsablePoint(int usablePoint) {
            this.usablePoint = usablePoint;
        }

        public int getTodayPoint() {
            return todayPoint;
        }

        public void setTodayPoint(int todayPoint) {
            this.todayPoint = todayPoint;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
