package com.example.administrator.jipinshop.bean;

import java.math.BigDecimal;
import java.util.List;

/**
 * Author     ： 莫小婷
 * CreateTime ： 2020/2/20 12:48
 * Description： 红包页面实体类
 */
public class MoneyBean {

    private String msg;
    private int code;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * invitationCode : NARK20
         * currentMoney : 0
         * messageList : [{"id":"1","mobile":"131****1491","money":"100","time":"30"}]
         * avatarList : ["https://jipincheng.cn/defaultImg.png"]
         * hongbaoList : [{"id":null,"userId":null,"orderNum":1,"addHongbao":3,"createTime":null,"status":null,"invitationUserId":null,"avatar":null},{"id":null,"userId":null,"orderNum":2,"addHongbao":4,"createTime":null,"status":null,"invitationUserId":null,"avatar":null},{"id":null,"userId":null,"orderNum":3,"addHongbao":5,"createTime":null,"status":null,"invitationUserId":null,"avatar":null},{"id":null,"userId":null,"orderNum":4,"addHongbao":6,"createTime":null,"status":null,"invitationUserId":null,"avatar":null},{"id":null,"userId":null,"orderNum":5,"addHongbao":7,"createTime":null,"status":null,"invitationUserId":null,"avatar":null},{"id":null,"userId":null,"orderNum":6,"addHongbao":8,"createTime":null,"status":null,"invitationUserId":null,"avatar":null},{"id":null,"userId":null,"orderNum":7,"addHongbao":9,"createTime":null,"status":null,"invitationUserId":null,"avatar":null},{"id":null,"userId":null,"orderNum":8,"addHongbao":10,"createTime":null,"status":null,"invitationUserId":null,"avatar":null},{"id":null,"userId":null,"orderNum":9,"addHongbao":10,"createTime":null,"status":null,"invitationUserId":null,"avatar":null}]
         */

        private String invitationCode;
        private String currentMoney;
        private String totalMoney;//累计红包
        private String alipayNickname;//支付宝昵称
        private String realname;//真实姓名
        private List<MessageListBean> messageList;//跑马灯
        private List<String> avatarList;//头像
        private List<HongbaoListBean> hongbaoList;

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getAlipayNickname() {
            return alipayNickname;
        }

        public void setAlipayNickname(String alipayNickname) {
            this.alipayNickname = alipayNickname;
        }

        public String getTotalMoney() {
            return totalMoney;
        }

        public void setTotalMoney(String totalMoney) {
            this.totalMoney = totalMoney;
        }

        public String getInvitationCode() {
            return invitationCode;
        }

        public void setInvitationCode(String invitationCode) {
            this.invitationCode = invitationCode;
        }

        public String getCurrentMoney() {
            return new BigDecimal(currentMoney.trim()).stripTrailingZeros().toPlainString();
        }

        public void setCurrentMoney(String currentMoney) {
            this.currentMoney = currentMoney;
        }

        public List<MessageListBean> getMessageList() {
            return messageList;
        }

        public void setMessageList(List<MessageListBean> messageList) {
            this.messageList = messageList;
        }

        public List<String> getAvatarList() {
            return avatarList;
        }

        public void setAvatarList(List<String> avatarList) {
            this.avatarList = avatarList;
        }

        public List<HongbaoListBean> getHongbaoList() {
            return hongbaoList;
        }

        public void setHongbaoList(List<HongbaoListBean> hongbaoList) {
            this.hongbaoList = hongbaoList;
        }

        public static class MessageListBean {
            /**
             * id : 1
             * mobile : 131****1491
             * money : 100
             * time : 30
             */

            private String id;
            private String mobile;
            private String money;
            private String time;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public static class HongbaoListBean {
            /**
             * id : null
             * userId : null
             * orderNum : 1
             * addHongbao : 3
             * createTime : null
             * status : null
             * invitationUserId : null
             * avatar : null
             */

            private String id;
            private String userId;
            private String orderNum;
            private String addHongbao;
            private String createTime;
            private String status;
            private String invitationUserId;
            private String avatar;

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

            public String getOrderNum() {
                return orderNum;
            }

            public void setOrderNum(String orderNum) {
                this.orderNum = orderNum;
            }

            public String getAddHongbao() {
                return addHongbao;
            }

            public void setAddHongbao(String addHongbao) {
                this.addHongbao = addHongbao;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getInvitationUserId() {
                return invitationUserId;
            }

            public void setInvitationUserId(String invitationUserId) {
                this.invitationUserId = invitationUserId;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }
        }
    }
}
