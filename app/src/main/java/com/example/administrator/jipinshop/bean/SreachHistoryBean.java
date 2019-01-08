package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/1/8
 * @Describe 搜索记录
 */
public class SreachHistoryBean {
    /**
     * msg : success
     * code : 0
     * data : {"hotWordList":[{"word":"电动牙刷"}],"logList":[{"id":"1fe4433d29c14774853fc414334cb4d2","word":"吹风机","userId":"3ed475ccc8f24bd08da35e552c85c85a","createTime":"2019-01-08 13:48:16"},{"id":"34024f724298426bbfdf83ba71708e2b","word":"吹风机","userId":"3ed475ccc8f24bd08da35e552c85c85a","createTime":"2019-01-08 13:48:16"}]}
     */

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
        private List<HotWordListBean> hotWordList;
        private List<LogListBean> logList;

        public List<HotWordListBean> getHotWordList() {
            return hotWordList;
        }

        public void setHotWordList(List<HotWordListBean> hotWordList) {
            this.hotWordList = hotWordList;
        }

        public List<LogListBean> getLogList() {
            return logList;
        }

        public void setLogList(List<LogListBean> logList) {
            this.logList = logList;
        }

        public static class HotWordListBean {
            /**
             * word : 电动牙刷
             */

            private String word;

            public String getWord() {
                return word;
            }

            public void setWord(String word) {
                this.word = word;
            }
        }

        public static class LogListBean {
            /**
             * id : 1fe4433d29c14774853fc414334cb4d2
             * word : 吹风机
             * userId : 3ed475ccc8f24bd08da35e552c85c85a
             * createTime : 2019-01-08 13:48:16
             */

            private String id;
            private String word;
            private String userId;
            private String createTime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getWord() {
                return word;
            }

            public void setWord(String word) {
                this.word = word;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }
    }
}
