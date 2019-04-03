package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/3/13
 * @Describe 极币商城
 */
public class MallBean {

    /**
     * msg : success
     * code : 0
     * data : [{"id":"ef141a1a6eb24a7a9f59b324140825b3","goodsName":"第三方价四大行发坚实的解释道","img":"http://jipincheng.cn/goods/img/20190313/b89689b1fc0e4917abc5585ead784f26","imgs":"http://jipincheng.cn/goods/img/20190313/a0c7bd7ea15444b9a80b41b6e00ddfbd","exchangePoint":0,"total":0,"content":"<p>黄金国际好好计划<\/p>\n","startTime":"2019-03-14 00:00:00","endTime":"2019-03-15 00:00:00","orderNum":0,"up":1,"createTime":"2019-03-12 18:01:37"}]
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
         * id : ef141a1a6eb24a7a9f59b324140825b3
         * goodsName : 第三方价四大行发坚实的解释道
         * img : http://jipincheng.cn/goods/img/20190313/b89689b1fc0e4917abc5585ead784f26
         * imgs : http://jipincheng.cn/goods/img/20190313/a0c7bd7ea15444b9a80b41b6e00ddfbd
         * exchangePoint : 0
         * total : 0
         * content : <p>黄金国际好好计划</p>

         * startTime : 2019-03-14 00:00:00
         * endTime : 2019-03-15 00:00:00
         * orderNum : 0
         * up : 1
         * createTime : 2019-03-12 18:01:37
         */

        private String id;
        private String goodsName;
        private String img;
        private String imgs;
        private int exchangePoint;
        private int total;
        private String content;
        private String startTime;
        private String endTime;
        private int orderNum;
        private int up;
        private String createTime;
        private int type;//类型：0普通商品，1活动商品

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getImgs() {
            return imgs;
        }

        public void setImgs(String imgs) {
            this.imgs = imgs;
        }

        public int getExchangePoint() {
            return exchangePoint;
        }

        public void setExchangePoint(int exchangePoint) {
            this.exchangePoint = exchangePoint;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(int orderNum) {
            this.orderNum = orderNum;
        }

        public int getUp() {
            return up;
        }

        public void setUp(int up) {
            this.up = up;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
