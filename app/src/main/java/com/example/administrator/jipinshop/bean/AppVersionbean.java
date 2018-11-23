package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2018/11/22
 * @Describe 版本更新
 */
public class AppVersionbean {

    /**
     * msg : success
     * appVersion : {"id":"1","versionCode":"2","versionName":"V1.0.1","needUpdate":0,"downloadUrl":"http://www.jipincheng.cn/app-release.apk","content":"1.更新界面","createTime":"2018-11-22 19:54:09"}
     * code : 200
     */

    private String msg;
    private AppVersionBean appVersion;
    private int code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public AppVersionBean getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(AppVersionBean appVersion) {
        this.appVersion = appVersion;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class AppVersionBean {
        /**
         * id : 1
         * versionCode : 2
         * versionName : V1.0.1
         * needUpdate : 0
         * downloadUrl : http://www.jipincheng.cn/app-release.apk
         * content : 1.更新界面
         * createTime : 2018-11-22 19:54:09
         */

        private String id;
        private int versionCode;
        private String versionName;
        private int needUpdate;
        private String downloadUrl;
        private String content;
        private String createTime;

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
    }
}
