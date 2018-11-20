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
     * list : [{"id":"0185d0ac968d4d1c8970bf064c9963eb","messageId":"5883d336b1e347c38678762a212f9472","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","title":"商品测试","content":"商品双12活动,商品双12活动商品双12活动商品双12活动商品双12活动商品双12活动商品双12活动","createTime":"2018-11-19 14:47:03","type":1,"status":null,"dataFlag":null,"unreadNum":null},{"id":"c150a4a09ac44b9d9e74b63088f9e447","messageId":"6c46e703d55b4eebaa8827bcb44b9afa","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","title":"马上开始了双十二活动！我们要打起十二分精神起来卖商品了！！！","content":"尊敬的用户您好：马上开始了双十二活动！我们要打起十二分精神起来卖商品了！！！马上开始了双十二活动！我们要打起十二分精神起来卖商品了！！！马上开始了双十二活动！我们要打起十二分精神起来卖商品了！！！马上开始了双十二活动！我们要打起十二分精神起来卖商品了！！！马上开始了双十二活动！我们要打起十二分精神起来卖商品了！！！马上开始了双十二活动！我们要打起十二分精神起来卖商品了！！！","createTime":"2018-11-19 14:39:33","type":1,"status":null,"dataFlag":null,"unreadNum":null},{"id":"5b147b9e526a430489d719a6271b2acd","messageId":"6c46e703d55b4eebaa8827bcb44b9afa","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","title":"标题9","content":"京东返利升级,买一送二","createTime":"2018-11-15 15:36:52","type":1,"status":null,"dataFlag":null,"unreadNum":null},{"id":"9bed970709984a11beeead8df9b0fe45","messageId":"6c46e703d55b4eebaa8827bcb44b9afa","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","title":"标题4","content":"京东返利升级,买一送二","createTime":"2018-11-15 15:35:42","type":1,"status":null,"dataFlag":null,"unreadNum":null},{"id":"f740072589394a7080ede2a5a155fb7c","messageId":"fa8df1577fe443468bae384f4b90ce7f","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","title":"标题3","content":"京东返利升级,买一送二","createTime":"2018-11-15 15:28:44","type":1,"status":null,"dataFlag":null,"unreadNum":null},{"id":"2d944d308b4b4523bc80a2033e0b2e3d","messageId":"53767dbb5e95498ab1ec37664246d299","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","title":"标题2","content":"京东返利升级,买一送二","createTime":"2018-11-15 15:28:30","type":1,"status":null,"dataFlag":null,"unreadNum":null},{"id":"99d79cf269da4620a42e28340fd8ed1e","messageId":"53767dbb5e95498ab1ec37664246d299","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","title":"标题1","content":"京东返利升级,买一送二","createTime":"2018-11-15 15:19:26","type":1,"status":null,"dataFlag":null,"unreadNum":null},{"id":"9734fe6546f04d21b7c85326967e9bc8","messageId":"53767dbb5e95498ab1ec37664246d299","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","title":"标题8","content":"京东返利升级,买一送二","createTime":"2018-11-15 15:19:20","type":1,"status":null,"dataFlag":null,"unreadNum":null},{"id":"8b15e1b8aeb14d94bc6211ea53f8b4d4","messageId":"53767dbb5e95498ab1ec37664246d299","userId":"7d67892cb02f4766aa72fd5b08b8d8d1","title":"标题7","content":"京东返利升级,买一送二","createTime":"2018-11-15 15:19:12","type":1,"status":null,"dataFlag":null,"unreadNum":null}]
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
         * id : 0185d0ac968d4d1c8970bf064c9963eb
         * messageId : 5883d336b1e347c38678762a212f9472
         * userId : 7d67892cb02f4766aa72fd5b08b8d8d1
         * title : 商品测试
         * content : 商品双12活动,商品双12活动商品双12活动商品双12活动商品双12活动商品双12活动商品双12活动
         * createTime : 2018-11-19 14:47:03
         * type : 1
         * status : null
         * dataFlag : null
         * unreadNum : null
         */

        private String id;
        private String messageId;
        private String userId;
        private String title;
        private String content;
        private String createTime;
        private int type;
        private int status;
        private String dataFlag;
        private String unreadNum;

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

        public String getDataFlag() {
            return dataFlag;
        }

        public void setDataFlag(String dataFlag) {
            this.dataFlag = dataFlag;
        }

        public String getUnreadNum() {
            return unreadNum;
        }

        public void setUnreadNum(String unreadNum) {
            this.unreadNum = unreadNum;
        }
    }
}
