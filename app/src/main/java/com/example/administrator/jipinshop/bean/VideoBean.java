package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2020/7/23
 * @Describe
 */
public class VideoBean {

    private String msg;
    private ShareInfoBean shareInfo;
    private int code;
    private SchoolHomeBean.DataBean.CategoryListBean.CourseListBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ShareInfoBean getShareInfo() {
        return shareInfo;
    }

    public void setShareInfo(ShareInfoBean shareInfo) {
        this.shareInfo = shareInfo;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public SchoolHomeBean.DataBean.CategoryListBean.CourseListBean getData() {
        return data;
    }

    public void setData(SchoolHomeBean.DataBean.CategoryListBean.CourseListBean data) {
        this.data = data;
    }

    public static class ShareInfoBean {
        /**
         * title : 淘宝省钱教程
         * desc : 淘宝省钱教程
         * imgUrl : http://jipincheng.cn/goods/img/20200722/08c117534f33472faafd2843e7affce7
         * link : http://40.0.0.60:8082/new-free/shareVideo?id=ddbfee54082b4bcb92a1750f885f4b4d
         */

        private String title;
        private String desc;
        private String imgUrl;
        private String link;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }

}
