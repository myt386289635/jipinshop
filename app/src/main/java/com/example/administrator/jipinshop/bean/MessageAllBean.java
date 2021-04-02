package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2021/4/1
 * @Describe
 */
public class MessageAllBean {

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
         * messageUserId : 252b959b3d554b73bd6d23aea1bc7f07
         * messageId : 141fd9c215fb4b03abed406e8fef9dc2
         * userId : 2
         * title : 21312
         * content : 324234234
         * messageImg : null
         * messageType : 1
         * status : 0
         * messageContent : 324234234
         * type : 11
         * targetId : 1
         * source : 0
         * createTime : 2020-03-11 11:26:00
         * dataFlag : 1
         */

        private String messageUserId;
        private String messageId;
        private String userId;
        private String title;
        private String content;
        private String messageImg;
        private String messageType;
        private String status;
        private String messageContent;
        private int type;
        private String targetId;
        private String source;
        private String createTime;
        private String dataFlag;
        private String remark;
        private String messagePushTime;

        public String getMessagePushTime() {
            return messagePushTime;
        }

        public void setMessagePushTime(String messagePushTime) {
            this.messagePushTime = messagePushTime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getMessageUserId() {
            return messageUserId;
        }

        public void setMessageUserId(String messageUserId) {
            this.messageUserId = messageUserId;
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

        public String getMessageImg() {
            return messageImg;
        }

        public void setMessageImg(String messageImg) {
            this.messageImg = messageImg;
        }

        public String getMessageType() {
            return messageType;
        }

        public void setMessageType(String messageType) {
            this.messageType = messageType;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessageContent() {
            return messageContent;
        }

        public void setMessageContent(String messageContent) {
            this.messageContent = messageContent;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTargetId() {
            return targetId;
        }

        public void setTargetId(String targetId) {
            this.targetId = targetId;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDataFlag() {
            return dataFlag;
        }

        public void setDataFlag(String dataFlag) {
            this.dataFlag = dataFlag;
        }
    }
}
