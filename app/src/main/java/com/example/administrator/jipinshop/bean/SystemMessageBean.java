package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/11/13
 * @Describe 系统消息
 */
public class SystemMessageBean {

    /**
     * msg : success
     * code : 200
     * list : [{"messageId":"14358eb8e526428f808525c1bafe8417","messageType":"1","messageTitle":"33333333333","messageFrom":null,"messageImg":"1","messageContentDetail":null,"messageCreatetime":"2018-11-12 16:04:05","messageBaticContent":null,"messagePushTime":"2018-10-19 16:16:24","pushStatus":null,"pushMessageUser":null,"readNum":0},{"messageId":"bc14b2fed1854a079da8b2ec1843bbf9","messageType":"1","messageTitle":"33333333333","messageFrom":null,"messageImg":"1","messageContentDetail":null,"messageCreatetime":"2018-11-13 19:33:21","messageBaticContent":"系统消息   啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊","messagePushTime":"2018-11-13 19:33:32","pushStatus":null,"pushMessageUser":null,"readNum":0},{"messageId":"dcd2faea668648cc9cf3a32578a6a54b","messageType":"1","messageTitle":"222222222222","messageFrom":"21312","messageImg":"3213","messageContentDetail":"31231","messageCreatetime":"2018-10-20 17:23:35","messageBaticContent":"21312","messagePushTime":"2018-11-12 17:57:31","pushStatus":null,"pushMessageUser":null,"readNum":0},{"messageId":"dffa13905bb744289f7be9acb5faa701","messageType":"1","messageTitle":"111111111111","messageFrom":"","messageImg":"","messageContentDetail":"","messageCreatetime":"2018-10-20 18:49:17","messageBaticContent":"","messagePushTime":"2018-11-12 17:57:35","pushStatus":null,"pushMessageUser":null,"readNum":0}]
     */

    private String msg;
    private int code;
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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * messageId : 14358eb8e526428f808525c1bafe8417
         * messageType : 1
         * messageTitle : 33333333333
         * messageFrom : null
         * messageImg : 1
         * messageContentDetail : null
         * messageCreatetime : 2018-11-12 16:04:05
         * messageBaticContent : null
         * messagePushTime : 2018-10-19 16:16:24
         * pushStatus : null
         * pushMessageUser : null
         * readNum : 0
         */

        private String messageId;
        private String messageType;
        private String messageTitle;
        private String messageFrom;
        private String messageImg;
        private String messageContentDetail;
        private String messageCreatetime;
        private String messageBaticContent;
        private String messagePushTime;
        private String pushStatus;
        private String pushMessageUser;
        private int readNum;

        public String getMessageId() {
            return messageId;
        }

        public void setMessageId(String messageId) {
            this.messageId = messageId;
        }

        public String getMessageType() {
            return messageType;
        }

        public void setMessageType(String messageType) {
            this.messageType = messageType;
        }

        public String getMessageTitle() {
            return messageTitle;
        }

        public void setMessageTitle(String messageTitle) {
            this.messageTitle = messageTitle;
        }

        public Object getMessageFrom() {
            return messageFrom;
        }

        public void setMessageFrom(String messageFrom) {
            this.messageFrom = messageFrom;
        }

        public String getMessageImg() {
            return messageImg;
        }

        public void setMessageImg(String messageImg) {
            this.messageImg = messageImg;
        }

        public String getMessageContentDetail() {
            return messageContentDetail;
        }

        public void setMessageContentDetail(String messageContentDetail) {
            this.messageContentDetail = messageContentDetail;
        }

        public String getMessageCreatetime() {
            return messageCreatetime;
        }

        public void setMessageCreatetime(String messageCreatetime) {
            this.messageCreatetime = messageCreatetime;
        }

        public String getMessageBaticContent() {
            return messageBaticContent;
        }

        public void setMessageBaticContent(String messageBaticContent) {
            this.messageBaticContent = messageBaticContent;
        }

        public String getMessagePushTime() {
            return messagePushTime;
        }

        public void setMessagePushTime(String messagePushTime) {
            this.messagePushTime = messagePushTime;
        }

        public String getPushStatus() {
            return pushStatus;
        }

        public void setPushStatus(String pushStatus) {
            this.pushStatus = pushStatus;
        }

        public String getPushMessageUser() {
            return pushMessageUser;
        }

        public void setPushMessageUser(String pushMessageUser) {
            this.pushMessageUser = pushMessageUser;
        }

        public int getReadNum() {
            return readNum;
        }

        public void setReadNum(int readNum) {
            this.readNum = readNum;
        }
    }
}
