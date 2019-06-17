package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2018/11/22
 * @Describe 版本更新
 */
public class AppVersionbean {

    /**
     * msg : success
     * code : 0
     * data : {"id":"46ab9191967044c99827efb7d778a3ac","versionCode":"3","versionName":"V1.1.1","requiredVersionCode":null,"needUpdate":0,"downloadUrl":"http://www.jipincheng.cn/cph.apk","open":0,"content":"1.版本更新1.11\n2.界面优化\n3.提升人机交互体验","createTime":null}
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
        /**
         * id : 46ab9191967044c99827efb7d778a3ac
         * versionCode : 3
         * versionName : V1.1.1
         * requiredVersionCode : null
         * needUpdate : 0
         * downloadUrl : http://www.jipincheng.cn/cph.apk
         * open : 0
         * content : 1.版本更新1.11
         2.界面优化
         3.提升人机交互体验
         * createTime : null
         */

        private String id;
        private int versionCode;
        private String versionName;
        private Object requiredVersionCode;
        private int needUpdate;
        private String downloadUrl;
        private int open;
        private String content;
        private Object createTime;
        private String needVerify;// 是否需要安全验证（0不需要，1需要）

        public String getNeedVerify() {
            return needVerify;
        }

        public void setNeedVerify(String needVerify) {
            this.needVerify = needVerify;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public Object getRequiredVersionCode() {
            return requiredVersionCode;
        }

        public void setRequiredVersionCode(Object requiredVersionCode) {
            this.requiredVersionCode = requiredVersionCode;
        }

        public int getNeedUpdate() {
            return needUpdate;
        }

        public void setNeedUpdate(int needUpdate) {
            this.needUpdate = needUpdate;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public int getOpen() {
            return open;
        }

        public void setOpen(int open) {
            this.open = open;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }
    }
}
