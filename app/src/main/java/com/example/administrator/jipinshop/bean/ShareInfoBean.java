package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2020/2/21
 * @Describe
 */
public class ShareInfoBean {

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

        private String content;
        private String posterImg;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPosterImg() {
            return posterImg;
        }

        public void setPosterImg(String posterImg) {
            this.posterImg = posterImg;
        }
    }
}
