package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2020/4/27
 * @Describe
 */
public class ClickUrlBean {

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
         * mobileUrl : https://mobile.yangkeduo.com/app.html?use_reload=1&launch_url=duo_coupon_landing.html%3Fgoods_id%3D8832253078%26pid%3D10228466_138382979%26customParameters%3D909090%26cpsSign%3DCC_200427_10228466_138382979_803b2acccb2c5886bb18946ea59717d6%26duoduo_type%3D2&campaign=ddjb&cid=launch_dl_force_
         * schemaUrl : pinduoduo://com.xunmeng.pinduoduo/duo_coupon_landing.html?goods_id=8832253078&pid=10228466_138382979&customParameters=909090&cpsSign=CC_200427_10228466_138382979_d0cc9767386357aaa01fe07c920d8449&duoduo_type=2
         * weAppId : wx32540bd863b27570
         * pagePath : {package_a/welfare_coupon/welfare_coupon?goods_id=8832253078&pid=10228466_138382979&customParameters=909090&cpsSign=CC_200427_10228466_138382979_803b2acccb2c5886bb18946ea59717d6&duoduo_type=2
         */

        private String mobileUrl;
        private String schemaUrl;
        private String weAppId;
        private String pagePath;
        private String otherGoodsId;

        public String getOtherGoodsId() {
            return otherGoodsId;
        }

        public void setOtherGoodsId(String otherGoodsId) {
            this.otherGoodsId = otherGoodsId;
        }

        public String getMobileUrl() {
            return mobileUrl;
        }

        public void setMobileUrl(String mobileUrl) {
            this.mobileUrl = mobileUrl;
        }

        public String getSchemaUrl() {
            return schemaUrl;
        }

        public void setSchemaUrl(String schemaUrl) {
            this.schemaUrl = schemaUrl;
        }

        public String getWeAppId() {
            return weAppId;
        }

        public void setWeAppId(String weAppId) {
            this.weAppId = weAppId;
        }

        public String getPagePath() {
            return pagePath;
        }

        public void setPagePath(String pagePath) {
            this.pagePath = pagePath;
        }
    }
}
