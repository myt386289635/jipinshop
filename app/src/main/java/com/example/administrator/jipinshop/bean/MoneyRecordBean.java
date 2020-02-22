package com.example.administrator.jipinshop.bean;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/2/22
 * @Describe 提现记录实体类
 */
public class MoneyRecordBean {

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
         * id : 55e08a48f399466aa649d041e187e280
         * userId : 2
         * money : 50.0
         * serviceCharge : 15.0
         * status : 0
         * createTime : 2020-02-21 17:11:28
         * updateTime : 2020-02-21 17:11:28
         * remark : null
         */

        private String id;
        private String userId;
        private String money;
        private String serviceCharge;
        private int status;
        private String createTime;
        private String updateTime;
        private String remark;
        private String alipayNickname;

        public String getAlipayNickname() {
            return alipayNickname;
        }

        public void setAlipayNickname(String alipayNickname) {
            this.alipayNickname = alipayNickname;
        }

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

        public String getMoney() {
            return new BigDecimal(money).stripTrailingZeros().toPlainString();
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getServiceCharge() {
            return serviceCharge;
        }

        public void setServiceCharge(String serviceCharge) {
            this.serviceCharge = serviceCharge;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
