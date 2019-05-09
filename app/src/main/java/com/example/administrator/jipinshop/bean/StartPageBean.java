package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/5/9
 * @Describe
 */
public class StartPageBean {

    /**
     * msg : success
     * code : 0
     * adList : [{"img":"http://jipincheng.cn/24ddace8f8c34153afff6e29d19cc402","type":2,"objectId":"0a722b462e0a4b2a99aa64689b87893e"}]
     */

    private String msg;
    private int code;
    private List<AdListBean> adList;

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

    public List<AdListBean> getAdList() {
        return adList;
    }

    public void setAdList(List<AdListBean> adList) {
        this.adList = adList;
    }

    public static class AdListBean {
        /**
         * img : http://jipincheng.cn/24ddace8f8c34153afff6e29d19cc402
         * type : 2
         * objectId : 0a722b462e0a4b2a99aa64689b87893e
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
