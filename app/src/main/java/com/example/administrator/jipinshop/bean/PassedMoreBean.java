package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/3/26
 * @Describe
 */
public class PassedMoreBean {

    /**
     * msg : success
     * code : 0
     * data : [{"id":"0dd3c4aa1ccf488c8c332e5bc75a31ec","trialId":"9e187764d29d419296c5f77028732d4b","userId":null,"voteCount":100,"userNickname":"测试22号","userAvatar":"http://jipincheng.cn/dafca5b3d66a43dc9cc00e07acd7bdb0","status":1,"remark":null},{"id":"2301b19320924ef9aa819b9d0f94069c","trialId":"9e187764d29d419296c5f77028732d4b","userId":null,"voteCount":5949,"userNickname":"测试21号","userAvatar":"http://jipincheng.cn/dafca5b3d66a43dc9cc00e07acd7bdb0","status":1,"remark":null},{"id":"5185ccbc4d0647f4843cda3f8bdf1e7a","trialId":"9e187764d29d419296c5f77028732d4b","userId":null,"voteCount":0,"userNickname":"测试18号","userAvatar":"http://jipincheng.cn/dafca5b3d66a43dc9cc00e07acd7bdb0","status":3,"remark":null},{"id":"56b642f807404b9a879f9185534a1cf2","trialId":"9e187764d29d419296c5f77028732d4b","userId":null,"voteCount":0,"userNickname":"测试17号","userAvatar":"http://jipincheng.cn/dafca5b3d66a43dc9cc00e07acd7bdb0","status":1,"remark":null},{"id":"62f544600fbb4466a1c5a9f1edd7bab0","trialId":"9e187764d29d419296c5f77028732d4b","userId":null,"voteCount":122,"userNickname":"测试16号","userAvatar":"http://jipincheng.cn/dafca5b3d66a43dc9cc00e07acd7bdb0","status":2,"remark":null},{"id":"7a603d8ac09c4b0bb9cc4f0a7d162c02","trialId":"9e187764d29d419296c5f77028732d4b","userId":null,"voteCount":146,"userNickname":"测试14号","userAvatar":"http://jipincheng.cn/dafca5b3d66a43dc9cc00e07acd7bdb0","status":1,"remark":null},{"id":"bb55f6e1f9284e4db0a6a85196d25cd6","trialId":"9e187764d29d419296c5f77028732d4b","userId":null,"voteCount":0,"userNickname":"测试10号","userAvatar":"http://jipincheng.cn/dafca5b3d66a43dc9cc00e07acd7bdb0","status":2,"remark":null},{"id":"c8b5a286d4dc426ebca5dbe0047a3aa1","trialId":"9e187764d29d419296c5f77028732d4b","userId":null,"voteCount":5415,"userNickname":"测试9号","userAvatar":"http://jipincheng.cn/dafca5b3d66a43dc9cc00e07acd7bdb0","status":1,"remark":null},{"id":"ea17502ff2df44049a305270ff9338c8","trialId":"9e187764d29d419296c5f77028732d4b","userId":null,"voteCount":415,"userNickname":"测试5号","userAvatar":"http://jipincheng.cn/dafca5b3d66a43dc9cc00e07acd7bdb0","status":1,"remark":null},{"id":"eed32b0aae6f449f81e99cef113366d5","trialId":"9e187764d29d419296c5f77028732d4b","userId":"2","voteCount":51451,"userNickname":"编辑","userAvatar":"http://jipincheng.cn/dafca5b3d66a43dc9cc00e07acd7bdb0","status":2,"remark":null}]
     * activitiesEndTime : 2019-03-20 11:38:36
     * status : 3
     */

    private String msg;
    private int code;
    private String reportEndTime;
    private int status;
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

    public String getReportEndTime() {
        return reportEndTime;
    }

    public void setReportEndTime(String reportEndTime) {
        this.reportEndTime = reportEndTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 0dd3c4aa1ccf488c8c332e5bc75a31ec
         * trialId : 9e187764d29d419296c5f77028732d4b
         * userId : null
         * voteCount : 100
         * userNickname : 测试22号
         * userAvatar : http://jipincheng.cn/dafca5b3d66a43dc9cc00e07acd7bdb0
         * status : 1
         * remark : null
         */

        private String id;
        private String trialId;
        private Object userId;
        private int voteCount;
        private String userNickname;
        private String userAvatar;
        private int status;
        private Object remark;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTrialId() {
            return trialId;
        }

        public void setTrialId(String trialId) {
            this.trialId = trialId;
        }

        public Object getUserId() {
            return userId;
        }

        public void setUserId(Object userId) {
            this.userId = userId;
        }

        public int getVoteCount() {
            return voteCount;
        }

        public void setVoteCount(int voteCount) {
            this.voteCount = voteCount;
        }

        public String getUserNickname() {
            return userNickname;
        }

        public void setUserNickname(String userNickname) {
            this.userNickname = userNickname;
        }

        public String getUserAvatar() {
            return userAvatar;
        }

        public void setUserAvatar(String userAvatar) {
            this.userAvatar = userAvatar;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }
    }
}
