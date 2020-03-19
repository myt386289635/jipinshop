package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/3/17
 * @Describe
 */
public class CircleTitleBean {
    /**
     * msg : success
     * code : 0
     * data : [{"id":"eaa82953f5cf4da6bb10e56899d166fe","title":"拉新","children":[{"id":"5cdce68c027e4516b1f60fe3e4a94271","title":"第一天","children":null},{"id":"14298df8622743c2a4f3421f78d89d4f","title":"第一天","children":null},{"id":"4377b182e36e43dd9950ecd44cf76409","title":"第三天","children":null},{"id":"fe572a717de144b596a2a8331c5a4219","title":"第四天","children":null}]},{"id":"854451c4a4e44403acf689467ff3ae89","title":"新人","children":[]},{"id":"447f4e7b0afd435a8e6fcf67e0f4150d","title":"活动","children":[]}]
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
         * id : eaa82953f5cf4da6bb10e56899d166fe
         * title : 拉新
         * children : [{"id":"5cdce68c027e4516b1f60fe3e4a94271","title":"第一天","children":null},{"id":"14298df8622743c2a4f3421f78d89d4f","title":"第一天","children":null},{"id":"4377b182e36e43dd9950ecd44cf76409","title":"第三天","children":null},{"id":"fe572a717de144b596a2a8331c5a4219","title":"第四天","children":null}]
         */

        private String id;
        private String title;
        private List<ChildrenBean> children;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public static class ChildrenBean {
            /**
             * id : 5cdce68c027e4516b1f60fe3e4a94271
             * title : 第一天
             * children : null
             */

            private String id;
            private String title;
            private Object children;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public Object getChildren() {
                return children;
            }

            public void setChildren(Object children) {
                this.children = children;
            }
        }
    }
}
