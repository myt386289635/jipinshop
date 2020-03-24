package com.example.administrator.jipinshop.bean;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/3/20
 * @Describe
 */
public class ShareBean {

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
         * content : 分享出去获得佣金
         * shareImg : http://jipincheng.cn/upload/20200320/e512d8c2b9cf4ac9a46d44dd3ed89bb4.png
         * downloadUrl : https://a.app.qq.com/o/simple.jsp?pkgname=com.example.administrator.jipinshop
         * tkl : ￥xA1f172MFcZ￥
         * imgs : ["https://img.alicdn.com/i2/441622457/O1CN01VLaYyA1U1LihxBZZM_!!441622457.jpg","https://img.alicdn.com/i3/441622457/O1CN012u7ErP1U1LdTAcoeX_!!441622457.jpg","https://img.alicdn.com/i2/441622457/O1CN01kPpySg1U1LdTtLXRN_!!441622457.jpg","https://img.alicdn.com/i2/441622457/O1CN011eSJWq1U1LdTAJKVr_!!441622457.jpg"]
         * invitationCode : NARK20
         * fee : 311.76
         */

        private String content;
        private String shareImg;
        private String downloadUrl;
        private String tkl;
        private String invitationCode;
        private String fee;
        private List<String> imgs;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getShareImg() {
            return shareImg;
        }

        public void setShareImg(String shareImg) {
            this.shareImg = shareImg;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public String getTkl() {
            return tkl;
        }

        public void setTkl(String tkl) {
            this.tkl = tkl;
        }

        public String getInvitationCode() {
            return invitationCode;
        }

        public void setInvitationCode(String invitationCode) {
            this.invitationCode = invitationCode;
        }

        public String getFee() {
            return new BigDecimal(fee).stripTrailingZeros().toPlainString();
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public List<String> getImgs() {
            return imgs;
        }

        public void setImgs(List<String> imgs) {
            this.imgs = imgs;
        }
    }
}
