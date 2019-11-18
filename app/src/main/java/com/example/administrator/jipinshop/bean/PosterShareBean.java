package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2019/11/18
 * @Describe
 */
public class PosterShareBean {

    /**
     * msg : success
     * code : 0
     * data : {"shareTitle":"送你一份豪礼，新人全场0元到手，手慢无！","shareContent":"送你一份豪礼，新人全场0元到手，手慢无！","shareImg":"http://jipincheng.cn/upload/20191118/c1dc87a52040459c84240308450e3e26.png","shareUrl":"pages/main/main-v2-list/index?fromUserId=fc603515e5f04f3aa1fd4f0466f9c4dd","posterImg":"https://jipincheng.cn/qrcode/img/20191118/b5a75873c17146d685b085b1be505304"}
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
         * shareTitle : 送你一份豪礼，新人全场0元到手，手慢无！
         * shareContent : 送你一份豪礼，新人全场0元到手，手慢无！
         * shareImg : http://jipincheng.cn/upload/20191118/c1dc87a52040459c84240308450e3e26.png
         * shareUrl : pages/main/main-v2-list/index?fromUserId=fc603515e5f04f3aa1fd4f0466f9c4dd
         * posterImg : https://jipincheng.cn/qrcode/img/20191118/b5a75873c17146d685b085b1be505304
         */

        private String shareTitle;
        private String shareContent;
        private String shareImg;
        private String shareUrl;
        private String posterImg;

        public String getShareTitle() {
            return shareTitle;
        }

        public void setShareTitle(String shareTitle) {
            this.shareTitle = shareTitle;
        }

        public String getShareContent() {
            return shareContent;
        }

        public void setShareContent(String shareContent) {
            this.shareContent = shareContent;
        }

        public String getShareImg() {
            return shareImg;
        }

        public void setShareImg(String shareImg) {
            this.shareImg = shareImg;
        }

        public String getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(String shareUrl) {
            this.shareUrl = shareUrl;
        }

        public String getPosterImg() {
            return posterImg;
        }

        public void setPosterImg(String posterImg) {
            this.posterImg = posterImg;
        }
    }
}
