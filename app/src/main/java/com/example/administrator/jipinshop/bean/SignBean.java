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
     * code : 0
     * data : {"id":"a099d37196b249608ef0feaa37966732","userId":"3ed475ccc8f24bd08da35e552c85c85a","lastTime":"2019-01-18 11:48:45","daysCount":5,"remark":"签到","pointAccount":{"id":"0ed77c879d4e43babb1eee6a99dbfeb3","userId":"3ed475ccc8f24bd08da35e552c85c85a","totalPoint":24,"usablePoint":14,"todayPoint":29,"status":1,"remark":"新用户开启积分功能","createTime":"2018-12-05 21:26:16","updateTime":"2019-01-18 11:48:45"},"signin":1,"pointArr":[1,2,3,4,5,6,7]}
     */

    private String msg;
    private int code;
    private DataBean data;

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

    public static class DataBean {
        /**
         * id : a099d37196b249608ef0feaa37966732
         * userId : 3ed475ccc8f24bd08da35e552c85c85a
         * lastTime : 2019-01-18 11:48:45
         * daysCount : 5
         * remark : 签到
         * pointAccount : {"id":"0ed77c879d4e43babb1eee6a99dbfeb3","userId":"3ed475ccc8f24bd08da35e552c85c85a","totalPoint":24,"usablePoint":14,"todayPoint":29,"status":1,"remark":"新用户开启积分功能","createTime":"2018-12-05 21:26:16","updateTime":"2019-01-18 11:48:45"}
         * signin : 1
         * pointArr : [1,2,3,4,5,6,7]
         */

        private String id;
        private String userId;
        private String lastTime;
        private int daysCount;
        private String remark;
        private PointAccountBean pointAccount;
        private int signin;
        private List<Integer> pointArr;

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

        public String getLastTime() {
            return lastTime;
        }

        public void setLastTime(String lastTime) {
            this.lastTime = lastTime;
        }

        public int getDaysCount() {
            return daysCount;
        }

        public void setDaysCount(int daysCount) {
            this.daysCount = daysCount;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public PointAccountBean getPointAccount() {
            return pointAccount;
        }

        public void setPointAccount(PointAccountBean pointAccount) {
            this.pointAccount = pointAccount;
        }

        public int getSignin() {
            return signin;
        }

        public void setSignin(int signin) {
            this.signin = signin;
        }

        public List<Integer> getPointArr() {
            return pointArr;
        }

        public void setPointArr(List<Integer> pointArr) {
            this.pointArr = pointArr;
        }

        public static class PointAccountBean {
            /**
             * id : 0ed77c879d4e43babb1eee6a99dbfeb3
             * userId : 3ed475ccc8f24bd08da35e552c85c85a
             * totalPoint : 24
             * usablePoint : 14
             * todayPoint : 29
             * status : 1
             * remark : 新用户开启积分功能
             * createTime : 2018-12-05 21:26:16
             * updateTime : 2019-01-18 11:48:45
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
}
