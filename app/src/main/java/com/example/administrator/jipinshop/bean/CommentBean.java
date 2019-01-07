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
     * total : 5
     * code : 0
     * data : [{"commentId":"73cca1738852479c9265cbad744a0dc7","userId":"3ed475ccc8f24bd08da35e552c85c85a","type":null,"targetId":null,"parentId":null,"toUserId":"0","content":"1111111111111111111111","voteCount":0,"childCount":0,"createTime":"2019-01-07 14:12:50","vote":0,"children":[{"commentId":"110394bbac2f427493977a1cd3c3427a","userId":"3ed475ccc8f24bd08da35e552c85c85a","type":null,"targetId":null,"parentId":null,"toUserId":"f0e42ce1f85442cdba03f536a1bc4fe5","content":"1111111111111111111111","voteCount":null,"childCount":null,"createTime":"2019-01-07 14:18:38","vote":0,"children":null,"userNickname":"︶~\u2002夏末丶","userAvatar":"http://jipincheng.cn/c201cd8dd7f04063bfe3f4eed9e79071","toUserNickname":"︶~\u2002夏末丶"},{"commentId":"a6665b4d45ad4e66ab2a4435a83b324a","userId":"f0e42ce1f85442cdba03f536a1bc4fe5","type":null,"targetId":null,"parentId":null,"toUserId":"3ed475ccc8f24bd08da35e552c85c85a","content":"回复","voteCount":null,"childCount":null,"createTime":"2019-01-07 14:26:04","vote":0,"children":null,"userNickname":"111","userAvatar":"http://jipincheng.cn/075a3efe0bb34aae82bdfd5df4f437e2","toUserNickname":"111"},{"commentId":"ec686f5d59f34ba6ba141efdd528f69f","userId":"3ed475ccc8f24bd08da35e552c85c85a","type":null,"targetId":null,"parentId":null,"toUserId":"3ed475ccc8f24bd08da35e552c85c85a","content":"1111111111111111111111","voteCount":null,"childCount":null,"createTime":"2019-01-07 14:14:12","vote":0,"children":null,"userNickname":"︶~\u2002夏末丶","userAvatar":"http://jipincheng.cn/c201cd8dd7f04063bfe3f4eed9e79071","toUserNickname":"︶~\u2002夏末丶"}],"userNickname":"︶~\u2002夏末丶","userAvatar":"http://jipincheng.cn/c201cd8dd7f04063bfe3f4eed9e79071","toUserNickname":null}]
     */

    private String msg;
    private int total;
    private int code;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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
         * commentId : 73cca1738852479c9265cbad744a0dc7
         * userId : 3ed475ccc8f24bd08da35e552c85c85a
         * type : null
         * targetId : null
         * parentId : null
         * toUserId : 0
         * content : 1111111111111111111111
         * voteCount : 0
         * childCount : 0
         * createTime : 2019-01-07 14:12:50
         * vote : 0
         * children : [{"commentId":"110394bbac2f427493977a1cd3c3427a","userId":"3ed475ccc8f24bd08da35e552c85c85a","type":null,"targetId":null,"parentId":null,"toUserId":"f0e42ce1f85442cdba03f536a1bc4fe5","content":"1111111111111111111111","voteCount":null,"childCount":null,"createTime":"2019-01-07 14:18:38","vote":0,"children":null,"userNickname":"︶~\u2002夏末丶","userAvatar":"http://jipincheng.cn/c201cd8dd7f04063bfe3f4eed9e79071","toUserNickname":"︶~\u2002夏末丶"},{"commentId":"a6665b4d45ad4e66ab2a4435a83b324a","userId":"f0e42ce1f85442cdba03f536a1bc4fe5","type":null,"targetId":null,"parentId":null,"toUserId":"3ed475ccc8f24bd08da35e552c85c85a","content":"回复","voteCount":null,"childCount":null,"createTime":"2019-01-07 14:26:04","vote":0,"children":null,"userNickname":"111","userAvatar":"http://jipincheng.cn/075a3efe0bb34aae82bdfd5df4f437e2","toUserNickname":"111"},{"commentId":"ec686f5d59f34ba6ba141efdd528f69f","userId":"3ed475ccc8f24bd08da35e552c85c85a","type":null,"targetId":null,"parentId":null,"toUserId":"3ed475ccc8f24bd08da35e552c85c85a","content":"1111111111111111111111","voteCount":null,"childCount":null,"createTime":"2019-01-07 14:14:12","vote":0,"children":null,"userNickname":"︶~\u2002夏末丶","userAvatar":"http://jipincheng.cn/c201cd8dd7f04063bfe3f4eed9e79071","toUserNickname":"︶~\u2002夏末丶"}]
         * userNickname : ︶~ 夏末丶
         * userAvatar : http://jipincheng.cn/c201cd8dd7f04063bfe3f4eed9e79071
         * toUserNickname : null
         */

        private String commentId;
        private String userId;
        private Object type;
        private Object targetId;
        private Object parentId;
        private String toUserId;
        private String content;
        private int voteCount;
        private int childCount;
        private String createTime;
        private int vote;
        private String userNickname;
        private String userAvatar;
        private Object toUserNickname;
        private List<ChildrenBean> children;
        private String createTimeStr;

        public String getCreateTimeStr() {
            return createTimeStr;
        }

        public void setCreateTimeStr(String createTimeStr) {
            this.createTimeStr = createTimeStr;
        }

        public String getCommentId() {
            return commentId;
        }

        public void setCommentId(String commentId) {
            this.commentId = commentId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public Object getTargetId() {
            return targetId;
        }

        public void setTargetId(Object targetId) {
            this.targetId = targetId;
        }

        public Object getParentId() {
            return parentId;
        }

        public void setParentId(Object parentId) {
            this.parentId = parentId;
        }

        public String getToUserId() {
            return toUserId;
        }

        public void setToUserId(String toUserId) {
            this.toUserId = toUserId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getVoteCount() {
            return voteCount;
        }

        public void setVoteCount(int voteCount) {
            this.voteCount = voteCount;
        }

        public int getChildCount() {
            return childCount;
        }

        public void setChildCount(int childCount) {
            this.childCount = childCount;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getVote() {
            return vote;
        }

        public void setVote(int vote) {
            this.vote = vote;
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

        public Object getToUserNickname() {
            return toUserNickname;
        }

        public void setToUserNickname(Object toUserNickname) {
            this.toUserNickname = toUserNickname;
        }

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public static class ChildrenBean {
            /**
             * commentId : 110394bbac2f427493977a1cd3c3427a
             * userId : 3ed475ccc8f24bd08da35e552c85c85a
             * type : null
             * targetId : null
             * parentId : null
             * toUserId : f0e42ce1f85442cdba03f536a1bc4fe5
             * content : 1111111111111111111111
             * voteCount : null
             * childCount : null
             * createTime : 2019-01-07 14:18:38
             * vote : 0
             * children : null
             * userNickname : ︶~ 夏末丶
             * userAvatar : http://jipincheng.cn/c201cd8dd7f04063bfe3f4eed9e79071
             * toUserNickname : ︶~ 夏末丶
             */

            private String commentId;
            private String userId;
            private Object type;
            private Object targetId;
            private Object parentId;
            private String toUserId;
            private String content;
            private Object voteCount;
            private Object childCount;
            private String createTime;
            private int vote;
            private Object children;
            private String userNickname;
            private String userAvatar;
            private String toUserNickname;

            public String getCommentId() {
                return commentId;
            }

            public void setCommentId(String commentId) {
                this.commentId = commentId;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public Object getType() {
                return type;
            }

            public void setType(Object type) {
                this.type = type;
            }

            public Object getTargetId() {
                return targetId;
            }

            public void setTargetId(Object targetId) {
                this.targetId = targetId;
            }

            public Object getParentId() {
                return parentId;
            }

            public void setParentId(Object parentId) {
                this.parentId = parentId;
            }

            public String getToUserId() {
                return toUserId;
            }

            public void setToUserId(String toUserId) {
                this.toUserId = toUserId;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public Object getVoteCount() {
                return voteCount;
            }

            public void setVoteCount(Object voteCount) {
                this.voteCount = voteCount;
            }

            public Object getChildCount() {
                return childCount;
            }

            public void setChildCount(Object childCount) {
                this.childCount = childCount;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getVote() {
                return vote;
            }

            public void setVote(int vote) {
                this.vote = vote;
            }

            public Object getChildren() {
                return children;
            }

            public void setChildren(Object children) {
                this.children = children;
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

            public String getToUserNickname() {
                return toUserNickname;
            }

            public void setToUserNickname(String toUserNickname) {
                this.toUserNickname = toUserNickname;
            }
        }
    }
}
