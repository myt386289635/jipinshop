package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2019/3/16
 * @Describe 邀请好友
 */
public class InvitationBean {

    /**
     * msg : success
     * code : 0
     * data : {"qrcodeImg":"http://jipincheng.cn/upload/20190316/16b4e0c2f9fa4db3a2ea6c1efffdcbcd.jpg","posterImg":"http://jipincheng.cn/upload/20190316/6a40c770fc134424b7303ea37db90eb2.png"}
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
         * qrcodeImg : http://jipincheng.cn/upload/20190316/16b4e0c2f9fa4db3a2ea6c1efffdcbcd.jpg
         * posterImg : http://jipincheng.cn/upload/20190316/6a40c770fc134424b7303ea37db90eb2.png
         */

        private String qrcodeImg;
        private String posterImg;

        public String getQrcodeImg() {
            return qrcodeImg;
        }

        public void setQrcodeImg(String qrcodeImg) {
            this.qrcodeImg = qrcodeImg;
        }

        public String getPosterImg() {
            return posterImg;
        }

        public void setPosterImg(String posterImg) {
            this.posterImg = posterImg;
        }
    }
}
