package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/11/9
 * @Describe 评论列表 bean
 */
public class CommentBean {

    /**
     * msg : success
     * code : 200
     * count : 8
     * list : [{"commentId":"1","articId":"11","userId":"11","content":"一楼","createTime":"2018-11-09 10:40:55","showTime":"5小时前","fromImg":null,"fromNickname":null,"parentId":"0","status":0,"snapNum":0,"userSnap":false,"secondNum":2,"userCommentList":[{"commentId":"10","articId":"11","userId":"111","content":"看什么看啊","createTime":null,"showTime":null,"fromImg":null,"fromNickname":null,"parentId":"11","status":null,"snapNum":null,"userSnap":null,"secondNum":null,"userCommentList":null},{"commentId":"12","articId":"11","userId":"112","content":"发的发送的是的发送到发送发的","createTime":null,"showTime":null,"fromImg":null,"fromNickname":null,"parentId":"11","status":null,"snapNum":null,"userSnap":null,"secondNum":null,"userCommentList":null}]},{"commentId":"2","articId":"11","userId":"22","content":"二楼","createTime":"2018-11-08 13:48:54","showTime":"","fromImg":null,"fromNickname":null,"parentId":"0","status":null,"snapNum":0,"userSnap":false,"secondNum":0,"userCommentList":[]},{"commentId":"3","articId":"11","userId":"33","content":"三楼","createTime":"2018-11-08 13:48:49","showTime":"","fromImg":null,"fromNickname":null,"parentId":"0","status":null,"snapNum":0,"userSnap":false,"secondNum":0,"userCommentList":[]},{"commentId":"4","articId":"11","userId":"44","content":"四楼","createTime":"2018-11-08 13:48:24","showTime":"","fromImg":null,"fromNickname":null,"parentId":"0","status":null,"snapNum":0,"userSnap":false,"secondNum":0,"userCommentList":[]},{"commentId":"5","articId":"11","userId":"55","content":"五楼","createTime":"2018-11-08 13:48:22","showTime":"","fromImg":null,"fromNickname":null,"parentId":"0","status":null,"snapNum":0,"userSnap":false,"secondNum":0,"userCommentList":[]},{"commentId":"89e7c7a04be249f583e1ba2c9c2c7573","articId":"11","userId":"1","content":"反倒是士大夫撒旦法阿萨德","createTime":"2018-11-06 16:02:32","showTime":"","fromImg":null,"fromNickname":null,"parentId":"0","status":null,"snapNum":0,"userSnap":false,"secondNum":0,"userCommentList":[]},{"commentId":"6","articId":"11","userId":"66","content":"六楼","createTime":"2018-11-06 13:48:20","showTime":"","fromImg":null,"fromNickname":null,"parentId":"0","status":null,"snapNum":0,"userSnap":false,"secondNum":0,"userCommentList":[]},{"commentId":"11","articId":"11","userId":null,"content":null,"createTime":null,"showTime":null,"fromImg":null,"fromNickname":null,"parentId":"0","status":null,"snapNum":1,"userSnap":true,"secondNum":0,"userCommentList":[]}]
     */

    private String msg;
    private int code;
    private int count;
    private List<ListBean> list;

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * commentId : 1
         * articId : 11
         * userId : 11
         * content : 一楼
         * createTime : 2018-11-09 10:40:55
         * showTime : 5小时前
         * fromImg : null
         * fromNickname : null
         * parentId : 0
         * status : 0
         * snapNum : 0
         * userSnap : false
         * secondNum : 2
         * userCommentList : [{"commentId":"10","articId":"11","userId":"111","content":"看什么看啊","createTime":null,"showTime":null,"fromImg":null,"fromNickname":null,"parentId":"11","status":null,"snapNum":null,"userSnap":null,"secondNum":null,"userCommentList":null},{"commentId":"12","articId":"11","userId":"112","content":"发的发送的是的发送到发送发的","createTime":null,"showTime":null,"fromImg":null,"fromNickname":null,"parentId":"11","status":null,"snapNum":null,"userSnap":null,"secondNum":null,"userCommentList":null}]
         */

        private String commentId;
        private String articId;
        private String userId;
        private String content;
        private String createTime;
        private String showTime;
        private String fromImg;
        private String fromNickname;
        private String parentId;
        private int status;
        private String snapNum;
        private boolean userSnap;
        private int secondNum;
        private List<UserCommentListBean> userCommentList;

        public String getCommentId() {
            return commentId;
        }

        public void setCommentId(String commentId) {
            this.commentId = commentId;
        }

        public String getArticId() {
            return articId;
        }

        public void setArticId(String articId) {
            this.articId = articId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
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

        public String getShowTime() {
            return showTime;
        }

        public void setShowTime(String showTime) {
            this.showTime = showTime;
        }

        public String getFromImg() {
            return fromImg;
        }

        public void setFromImg(String fromImg) {
            this.fromImg = fromImg;
        }

        public String getFromNickname() {
            return fromNickname;
        }

        public void setFromNickname(String fromNickname) {
            this.fromNickname = fromNickname;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getSnapNum() {
            return snapNum;
        }

        public void setSnapNum(String snapNum) {
            this.snapNum = snapNum;
        }

        public boolean isUserSnap() {
            return userSnap;
        }

        public void setUserSnap(boolean userSnap) {
            this.userSnap = userSnap;
        }

        public int getSecondNum() {
            return secondNum;
        }

        public void setSecondNum(int secondNum) {
            this.secondNum = secondNum;
        }

        public List<UserCommentListBean> getUserCommentList() {
            return userCommentList;
        }

        public void setUserCommentList(List<UserCommentListBean> userCommentList) {
            this.userCommentList = userCommentList;
        }

        public static class UserCommentListBean {
            /**
             * commentId : 10
             * articId : 11
             * userId : 111
             * content : 看什么看啊
             * createTime : null
             * showTime : null
             * fromImg : null
             * fromNickname : null
             * parentId : 11
             * status : null
             * snapNum : null
             * userSnap : null
             * secondNum : null
             * userCommentList : null
             */

            private String commentId;
            private String articId;
            private String userId;
            private String content;
            private String createTime;
            private String showTime;
            private String fromImg;
            private String fromNickname;
            private String parentId;
            private String status;
            private String snapNum;
            private boolean userSnap;
            private Object secondNum;
            private Object userCommentList;

            public String getCommentId() {
                return commentId;
            }

            public void setCommentId(String commentId) {
                this.commentId = commentId;
            }

            public String getArticId() {
                return articId;
            }

            public void setArticId(String articId) {
                this.articId = articId;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
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

            public String getShowTime() {
                return showTime;
            }

            public void setShowTime(String showTime) {
                this.showTime = showTime;
            }

            public String getFromImg() {
                return fromImg;
            }

            public void setFromImg(String fromImg) {
                this.fromImg = fromImg;
            }

            public String getFromNickname() {
                return fromNickname;
            }

            public void setFromNickname(String fromNickname) {
                this.fromNickname = fromNickname;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getSnapNum() {
                return snapNum;
            }

            public void setSnapNum(String snapNum) {
                this.snapNum = snapNum;
            }

            public boolean getUserSnap() {
                return userSnap;
            }

            public void setUserSnap(boolean userSnap) {
                this.userSnap = userSnap;
            }

            public Object getSecondNum() {
                return secondNum;
            }

            public void setSecondNum(Object secondNum) {
                this.secondNum = secondNum;
            }

            public Object getUserCommentList() {
                return userCommentList;
            }

            public void setUserCommentList(Object userCommentList) {
                this.userCommentList = userCommentList;
            }
        }
    }
}
