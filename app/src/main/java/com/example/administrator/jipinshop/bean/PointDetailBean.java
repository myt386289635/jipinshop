package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/11/20
 * @Describe 积分明细
 */
public class PointDetailBean {

    /**
     * msg : success
     * code : 0
     * data : [{"id":"9b44280c37514c83bd770ce56e31a015","userId":"3ed475ccc8f24bd08da35e552c85c85a","point":3,"currentPoint":7,"remark":"签到积分","createTime":"2018-12-12 15:06:53"},{"id":"38faf5b0da5c4b01b423e4aae2052b1f","userId":"3ed475ccc8f24bd08da35e552c85c85a","point":2,"currentPoint":5,"remark":"签到积分","createTime":"2018-12-11 09:33:33"},{"id":"dea1ddbda4234244825bf6f53d3d8a7a","userId":"3ed475ccc8f24bd08da35e552c85c85a","point":1,"currentPoint":4,"remark":"签到积分","createTime":"2018-12-10 16:20:45"},{"id":"b7be364860d64798aa19ec4945bbc63e","userId":"3ed475ccc8f24bd08da35e552c85c85a","point":4,"currentPoint":0,"remark":"签到积分","createTime":"2018-12-06 17:44:12"}]
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
         * id : 9b44280c37514c83bd770ce56e31a015
         * userId : 3ed475ccc8f24bd08da35e552c85c85a
         * point : 3
         * currentPoint : 7
         * remark : 签到积分
         * createTime : 2018-12-12 15:06:53
         */

        private String id;
        private String userId;
        private int point;
        private int currentPoint;
        private String remark;
        private String createTime;

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

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }

        public int getCurrentPoint() {
            return currentPoint;
        }

        public void setCurrentPoint(int currentPoint) {
            this.currentPoint = currentPoint;
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
    }
}
