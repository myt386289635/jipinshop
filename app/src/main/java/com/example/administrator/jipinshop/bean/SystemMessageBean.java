package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/11/13
 * @Describe 消息列表
 */
public class SystemMessageBean {

    /**
     * msg : success
     * code : 0
     * data : [{"id":"9","messageId":"a11993ae1e534e868ae2b10377e805a1","userId":"3ed475ccc8f24bd08da35e552c85c85a","title":"APP遇到闪退等问题处理方式","content":"APP遇到闪退，请先清理缓存，如果还未解决，请联系技术部门","createTime":"2018-12-10 11:50:19","type":1,"status":0,"dataFlag":1},{"id":"8576eebf82794fd982a5f96a13debc66","messageId":"a11993ae1e534e868ae2b10377e805a1","userId":"3ed475ccc8f24bd08da35e552c85c85a","title":"APP遇到闪退等问题处理方式","content":"APP遇到闪退，请先清理缓存，如果还未解决，请联系技术部门","createTime":"2018-12-10 11:50:19","type":1,"status":1,"dataFlag":1},{"id":"8","messageId":"a11993ae1e534e868ae2b10377e805a1","userId":"3ed475ccc8f24bd08da35e552c85c85a","title":"APP遇到闪退等问题处理方式","content":"APP遇到闪退，请先清理缓存，如果还未解决，请联系技术部门","createTime":"2018-12-10 11:50:19","type":1,"status":0,"dataFlag":1},{"id":"7","messageId":"a11993ae1e534e868ae2b10377e805a1","userId":"3ed475ccc8f24bd08da35e552c85c85a","title":"APP遇到闪退等问题处理方式","content":"APP遇到闪退，请先清理缓存，如果还未解决，请联系技术部门","createTime":"2018-12-10 11:50:19","type":1,"status":0,"dataFlag":1},{"id":"6","messageId":"a11993ae1e534e868ae2b10377e805a1","userId":"3ed475ccc8f24bd08da35e552c85c85a","title":"APP遇到闪退等问题处理方式","content":"APP遇到闪退，请先清理缓存，如果还未解决，请联系技术部门","createTime":"2018-12-10 11:50:19","type":1,"status":0,"dataFlag":1},{"id":"5","messageId":"a11993ae1e534e868ae2b10377e805a1","userId":"3ed475ccc8f24bd08da35e552c85c85a","title":"APP遇到闪退等问题处理方式","content":"APP遇到闪退，请先清理缓存，如果还未解决，请联系技术部门","createTime":"2018-12-10 11:50:19","type":1,"status":0,"dataFlag":1},{"id":"4","messageId":"a11993ae1e534e868ae2b10377e805a1","userId":"3ed475ccc8f24bd08da35e552c85c85a","title":"APP遇到闪退等问题处理方式","content":"APP遇到闪退，请先清理缓存，如果还未解决，请联系技术部门","createTime":"2018-12-10 11:50:19","type":1,"status":0,"dataFlag":1},{"id":"3","messageId":"a11993ae1e534e868ae2b10377e805a1","userId":"3ed475ccc8f24bd08da35e552c85c85a","title":"APP遇到闪退等问题处理方式","content":"APP遇到闪退，请先清理缓存，如果还未解决，请联系技术部门","createTime":"2018-12-10 11:50:19","type":1,"status":0,"dataFlag":1},{"id":"2","messageId":"a11993ae1e534e868ae2b10377e805a1","userId":"3ed475ccc8f24bd08da35e552c85c85a","title":"APP遇到闪退等问题处理方式","content":"APP遇到闪退，请先清理缓存，如果还未解决，请联系技术部门","createTime":"2018-12-10 11:50:19","type":1,"status":0,"dataFlag":1},{"id":"13","messageId":"a11993ae1e534e868ae2b10377e805a1","userId":"3ed475ccc8f24bd08da35e552c85c85a","title":"APP遇到闪退等问题处理方式","content":"APP遇到闪退，请先清理缓存，如果还未解决，请联系技术部门","createTime":"2018-12-10 11:50:19","type":1,"status":0,"dataFlag":1}]
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
         * id : 9
         * messageId : a11993ae1e534e868ae2b10377e805a1
         * userId : 3ed475ccc8f24bd08da35e552c85c85a
         * title : APP遇到闪退等问题处理方式
         * content : APP遇到闪退，请先清理缓存，如果还未解决，请联系技术部门
         * createTime : 2018-12-10 11:50:19
         * type : 1
         * status : 0
         * dataFlag : 1
         */

        private String id;
        private String messageId;
        private String userId;
        private String title;
        private String content;
        private String createTime;
        private int type;
        private int status;
        private int dataFlag;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMessageId() {
            return messageId;
        }

        public void setMessageId(String messageId) {
            this.messageId = messageId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getDataFlag() {
            return dataFlag;
        }

        public void setDataFlag(int dataFlag) {
            this.dataFlag = dataFlag;
        }
    }
}
