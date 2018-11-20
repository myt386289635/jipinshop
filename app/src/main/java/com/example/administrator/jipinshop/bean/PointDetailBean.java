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
     * code : 200
     * pointDetailList : [{"id":"234eddb69d4946798b94bd49352eae40","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","point":2,"currentPoint":976,"remark":"签到积分","createTime":"2018-11-20 19:32:34"},{"id":"28b4463125b04114bc76bfe0e4d9f85d","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","point":200,"currentPoint":1478,"remark":"二等奖","createTime":"2018-11-20 20:00:59"},{"id":"5762d2f1d4104d11bc1d277ce731b840","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","point":200,"currentPoint":1078,"remark":"四等奖","createTime":"2018-11-20 19:57:55"},{"id":"68f38f0be3d24e8e90619fe6bb81b303","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","point":-10,"currentPoint":986,"remark":"补签","createTime":"2018-11-20 16:31:31"},{"id":"6e4f6caf7dda4fcb870d0fdf4c09d1df","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","point":2,"currentPoint":984,"remark":"签到积分","createTime":"2018-11-20 16:29:52"},{"id":"a6baa87488744839992ba8c7fe846574","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","point":200,"currentPoint":1278,"remark":"二等奖","createTime":"2018-11-20 20:00:19"},{"id":"bc4586478ef6441cb43bf7d662dd1a20","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","point":100,"currentPoint":978,"remark":"100积分","createTime":"2018-11-20 19:44:38"}]
     */

    private String msg;
    private int code;
    private List<PointDetailListBean> pointDetailList;

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

    public List<PointDetailListBean> getPointDetailList() {
        return pointDetailList;
    }

    public void setPointDetailList(List<PointDetailListBean> pointDetailList) {
        this.pointDetailList = pointDetailList;
    }

    public static class PointDetailListBean {
        /**
         * id : 234eddb69d4946798b94bd49352eae40
         * userId : 7d67892cb02f4766aa72fd5b08b8d8d1
         * point : 2
         * currentPoint : 976
         * remark : 签到积分
         * createTime : 2018-11-20 19:32:34
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
