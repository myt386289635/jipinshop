package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/8/8
 * @Describe
 */
public class EvaHotBean {


    /**
     * msg : success
     * ads : [{"img":"http://jipincheng.cn/85abcd70dc4a4171b3c7e10ebbc79fce-findgoods_list_img","type":3,"objectId":"a6f978a041d946df9a87e85bde88d709"},{"img":"http://jipincheng.cn/c7b8617c2c50493ebddf7414f4354462-findgoods_list_img","type":3,"objectId":"a41a22f1a1fd4b6b94d90197741310a5"}]
     * code : 0
     */

    private String msg;
    private int code;
    private List<AdsBean> ads;
    private List<EvaAttentBean.DataBean.ArticleBean> data;

    public List<EvaAttentBean.DataBean.ArticleBean> getData() {
        return data;
    }

    public void setData(List<EvaAttentBean.DataBean.ArticleBean> data) {
        this.data = data;
    }

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

    public List<AdsBean> getAds() {
        return ads;
    }

    public void setAds(List<AdsBean> ads) {
        this.ads = ads;
    }

    public static class AdsBean {
        /**
         * img : http://jipincheng.cn/85abcd70dc4a4171b3c7e10ebbc79fce-findgoods_list_img
         * type : 3
         * objectId : a6f978a041d946df9a87e85bde88d709
         */

        private String img;
        private int type;
        private String objectId;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }
    }
}
