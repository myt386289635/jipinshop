package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/1/7
 * @Describe
 */
public class AllowanceRecordBean {

    /**
     * msg : success
     * code : 0
     * data : [{"id":"6a73ec1a60ba419a911b99c695943b6f","allowanceGoodsId":"4","userId":"2","goodsName":"4新洁丽简易包装软抽纸巾 100抽高品质面巾纸2层原木浆一箱包邮","img":"https://img.alicdn.com/bao/uploaded/i3/13416030965327878/T1B22GFhNXXXXXXXXX_!!0-item_pic.jpg","otherPrice":null,"buyPrice":null,"useAllowancePrice":1,"addAllowancePrice":0,"allowanceUrl":"https://uland.taobao.com/taolijin/edetail?eh=b5DC4NScqvGZuQF0XRz0iOf9abvGldPIHGcP2339OF9xgyaHtVzu9VO2QszMah0%2BKlgvu1MOvkScof7EVFM3osXxZWUgnrwAo6ozDjtPT0hNL6nOsG9YQTqQ21O%2Bxk%2BRbd76m3V5xpZv4WZfXVtZjQj%2F7ReTQLUMF0%2FybEZMQN5OM2pmNcFoV42nmxfVvdVfwdaM7ZHV%2BhOutwqBQC%2Fe43SK8En2Ew4Md7WmOgQ30GNERgk3G1wdzyUzVkkdwsIm&union_lens=lensId%3A0b1538d6_0c3d_16f7ac5a8b7_af09%3Btraffic_flag%3Dlm","createTime":"2020-01-06 20:12:51"},{"id":"cfe97937df9442bf98e74ca3ae887575","allowanceGoodsId":"3","userId":"2","goodsName":"3新洁丽简易包装软抽纸巾 100抽高品质面巾纸2层原木浆一箱包邮","img":"https://img.alicdn.com/bao/uploaded/i3/13416030965327878/T1B22GFhNXXXXXXXXX_!!0-item_pic.jpg","otherPrice":null,"buyPrice":null,"useAllowancePrice":1,"addAllowancePrice":0,"allowanceUrl":"https://uland.taobao.com/taolijin/edetail?eh=t%2BjI6fR5pmaZuQF0XRz0iOf9abvGldPIHGcP2339OF9xgyaHtVzu9VO2QszMah0%2BFKuB504E90beUJidhxA%2BZcXxZWUgnrwAo6ozDjtPT0hNL6nOsG9YQTqQ21O%2Bxk%2BRbd76m3V5xpZv4WZfXVtZjQj%2F7ReTQLUMF0%2FybEZMQN5OM2pmNcFoV42nmxfVvdVfwdaM7ZHV%2BhOutwqBQC%2Fe43SK8En2Ew4Md7WmOgQ30GNERgk3G1wdzyUzVkkdwsIm&union_lens=lensId%3A0baf51f5_0bdb_16f7ab5d9b8_a689%3Btraffic_flag%3Dlm","createTime":"2020-01-06 19:55:35"}]
     */

    private String msg;
    private int code;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 6a73ec1a60ba419a911b99c695943b6f
         * allowanceGoodsId : 4
         * userId : 2
         * goodsName : 4新洁丽简易包装软抽纸巾 100抽高品质面巾纸2层原木浆一箱包邮
         * img : https://img.alicdn.com/bao/uploaded/i3/13416030965327878/T1B22GFhNXXXXXXXXX_!!0-item_pic.jpg
         * otherPrice : null
         * buyPrice : null
         * useAllowancePrice : 1.0
         * addAllowancePrice : 0.0
         * allowanceUrl : https://uland.taobao.com/taolijin/edetail?eh=b5DC4NScqvGZuQF0XRz0iOf9abvGldPIHGcP2339OF9xgyaHtVzu9VO2QszMah0%2BKlgvu1MOvkScof7EVFM3osXxZWUgnrwAo6ozDjtPT0hNL6nOsG9YQTqQ21O%2Bxk%2BRbd76m3V5xpZv4WZfXVtZjQj%2F7ReTQLUMF0%2FybEZMQN5OM2pmNcFoV42nmxfVvdVfwdaM7ZHV%2BhOutwqBQC%2Fe43SK8En2Ew4Md7WmOgQ30GNERgk3G1wdzyUzVkkdwsIm&union_lens=lensId%3A0b1538d6_0c3d_16f7ac5a8b7_af09%3Btraffic_flag%3Dlm
         * createTime : 2020-01-06 20:12:51
         */

        private String id;
        private String allowanceGoodsId;
        private String userId;
        private String goodsName;
        private String img;
        private String otherPrice;
        private String buyPrice;
        private String useAllowancePrice;
        private String addAllowancePrice;
        private String allowanceUrl;
        private String createTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAllowanceGoodsId() {
            return allowanceGoodsId;
        }

        public void setAllowanceGoodsId(String allowanceGoodsId) {
            this.allowanceGoodsId = allowanceGoodsId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getOtherPrice() {
            return otherPrice;
        }

        public void setOtherPrice(String otherPrice) {
            this.otherPrice = otherPrice;
        }

        public String getBuyPrice() {
            return buyPrice;
        }

        public void setBuyPrice(String buyPrice) {
            this.buyPrice = buyPrice;
        }

        public String getUseAllowancePrice() {
            return useAllowancePrice;
        }

        public void setUseAllowancePrice(String useAllowancePrice) {
            this.useAllowancePrice = useAllowancePrice;
        }

        public String getAddAllowancePrice() {
            return addAllowancePrice;
        }

        public void setAddAllowancePrice(String addAllowancePrice) {
            this.addAllowancePrice = addAllowancePrice;
        }

        public String getAllowanceUrl() {
            return allowanceUrl;
        }

        public void setAllowanceUrl(String allowanceUrl) {
            this.allowanceUrl = allowanceUrl;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
