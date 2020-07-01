package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/7/1
 * @Describe
 */
public class PrizeLogBean {
    /**
     * msg : success
     * code : 0
     * data : [{"id":"4f9ddfa62ca54cf184c057e8b873ad8b","prizeId":"10ac86be075b43f999fd5b8d2f969664","userId":"2","prizeName":"戴森吸尘器","createTime":"2020-06-28 14:22:34","nickname":"1111","type":1}]
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
         * id : 4f9ddfa62ca54cf184c057e8b873ad8b
         * prizeId : 10ac86be075b43f999fd5b8d2f969664
         * userId : 2
         * prizeName : 戴森吸尘器
         * createTime : 2020-06-28 14:22:34
         * nickname : 1111
         * type : 1
         */

        private String id;
        private String prizeId;
        private String userId;
        private String prizeName;
        private String createTime;
        private String nickname;
        private String type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPrizeId() {
            return prizeId;
        }

        public void setPrizeId(String prizeId) {
            this.prizeId = prizeId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPrizeName() {
            return prizeName;
        }

        public void setPrizeName(String prizeName) {
            this.prizeName = prizeName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
