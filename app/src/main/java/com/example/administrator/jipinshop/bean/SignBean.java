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
     * signinInfo : {"id":"738ee2bdf2b646d5b69ce20bdba021c1","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","week":"0,1,0,0,0,0,0","weekArray":["2","1","0","0","0","0","0"],"lastTime":"2018-11-20 13:18:27","daysCount":1,"remark":"签到"}
     */

    private String msg;
    private int code;
    private SigninInfoBean signinInfo;

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

    public SigninInfoBean getSigninInfo() {
        return signinInfo;
    }

    public void setSigninInfo(SigninInfoBean signinInfo) {
        this.signinInfo = signinInfo;
    }

    public static class SigninInfoBean {
        /**
         * id : 738ee2bdf2b646d5b69ce20bdba021c1
         * userId : 7d67892cb02f4766aa72fd5b08b8d8d1
         * week : 0,1,0,0,0,0,0
         * weekArray : ["2","1","0","0","0","0","0"]
         * lastTime : 2018-11-20 13:18:27
         * daysCount : 1
         * remark : 签到
         */

        private String id;
        private String userId;
        private String week;
        private String lastTime;
        private int daysCount;
        private String remark;
        private List<String> weekArray;

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

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
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

        public List<String> getWeekArray() {
            return weekArray;
        }

        public void setWeekArray(List<String> weekArray) {
            this.weekArray = weekArray;
        }
    }
}
