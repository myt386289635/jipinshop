package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/4/2
 * @Describe
 */
public class TrialCommonBean {

    /**
     * msg : success
     * code : 0
     * data : [{"id":"3","name":"啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊","img":"http://jipincheng.cn/9f4e435d64ff435e9ba32d96b6f45e83","voteCount":0,"remark":"申请成功","status":1,"reportStatus":null,"checkTime":null,"reportEndTime":"2019-04-26 14:00:45","voteShareTitle":"我在拉赞免费拿啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊,帮我点下赞好不好。","voteShareContent":"我正在参加极品城官方新品试用，帮我点赞，一起免费用~","voteShareUrl":"https://www.jipincheng.cn/share/trial_vote.html?trialId=3&uid=2","voteShareImg":"https://jipincheng.cn/9f4e435d64ff435e9ba32d96b6f45e83"},{"id":"3","name":"啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊","img":"http://jipincheng.cn/9f4e435d64ff435e9ba32d96b6f45e83","voteCount":0,"remark":"申请成功","status":1,"reportStatus":null,"checkTime":null,"reportEndTime":"2019-04-26 14:00:45","voteShareTitle":"我在拉赞免费拿啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊,帮我点下赞好不好。","voteShareContent":"我正在参加极品城官方新品试用，帮我点赞，一起免费用~","voteShareUrl":"https://www.jipincheng.cn/share/trial_vote.html?trialId=3&uid=2","voteShareImg":"https://jipincheng.cn/9f4e435d64ff435e9ba32d96b6f45e83"},{"id":"9e187764d29d419296c5f77028732d4b","name":"11","img":"http://jipincheng.cn/9f4e435d64ff435e9ba32d96b6f45e83","voteCount":51451,"remark":null,"status":2,"reportStatus":null,"checkTime":null,"reportEndTime":"2019-03-23 11:38:38","voteShareTitle":"我在拉赞免费拿11,帮我点下赞好不好。","voteShareContent":"我正在参加极品城官方新品试用，帮我点赞，一起免费用~","voteShareUrl":"https://www.jipincheng.cn/share/trial_vote.html?trialId=9e187764d29d419296c5f77028732d4b&uid=2","voteShareImg":"https://jipincheng.cn/9f4e435d64ff435e9ba32d96b6f45e83"}]
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
         * id : 3
         * name : 啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊
         * img : http://jipincheng.cn/9f4e435d64ff435e9ba32d96b6f45e83
         * voteCount : 0
         * remark : 申请成功
         * status : 1
         * reportStatus : null
         * checkTime : null
         * reportEndTime : 2019-04-26 14:00:45
         * voteShareTitle : 我在拉赞免费拿啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊,帮我点下赞好不好。
         * voteShareContent : 我正在参加极品城官方新品试用，帮我点赞，一起免费用~
         * voteShareUrl : https://www.jipincheng.cn/share/trial_vote.html?trialId=3&uid=2
         * voteShareImg : https://jipincheng.cn/9f4e435d64ff435e9ba32d96b6f45e83
         */

        private String id;
        private String name;
        private String img;
        private int voteCount;
        private String remark;
        private int status;
        private int reportStatus;
        private String checkTime;
        private String reportEndTime;
        private String voteShareTitle;
        private String voteShareContent;
        private String voteShareUrl;
        private String voteShareImg;
        private String activitiesEndTime;

        public String getActivitiesEndTime() {
            return activitiesEndTime;
        }

        public void setActivitiesEndTime(String activitiesEndTime) {
            this.activitiesEndTime = activitiesEndTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getVoteCount() {
            return voteCount;
        }

        public void setVoteCount(int voteCount) {
            this.voteCount = voteCount;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getReportStatus() {
            return reportStatus;
        }

        public void setReportStatus(int reportStatus) {
            this.reportStatus = reportStatus;
        }

        public String getCheckTime() {
            return checkTime;
        }

        public void setCheckTime(String checkTime) {
            this.checkTime = checkTime;
        }

        public String getReportEndTime() {
            return reportEndTime;
        }

        public void setReportEndTime(String reportEndTime) {
            this.reportEndTime = reportEndTime;
        }

        public String getVoteShareTitle() {
            return voteShareTitle;
        }

        public void setVoteShareTitle(String voteShareTitle) {
            this.voteShareTitle = voteShareTitle;
        }

        public String getVoteShareContent() {
            return voteShareContent;
        }

        public void setVoteShareContent(String voteShareContent) {
            this.voteShareContent = voteShareContent;
        }

        public String getVoteShareUrl() {
            return voteShareUrl;
        }

        public void setVoteShareUrl(String voteShareUrl) {
            this.voteShareUrl = voteShareUrl;
        }

        public String getVoteShareImg() {
            return voteShareImg;
        }

        public void setVoteShareImg(String voteShareImg) {
            this.voteShareImg = voteShareImg;
        }
    }
}
