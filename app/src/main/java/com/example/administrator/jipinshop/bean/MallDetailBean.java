package com.example.administrator.jipinshop.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/3/13
 * @Describe 极币商城里商品详情页
 */
public class MallDetailBean implements Serializable{

    /**
     * msg : success
     * code : 0
     * data : {"id":"64f9ebf436fc4ca1b4a565cc8c347427","goodsName":"21312312312321","img":"https://jipincheng.cn/goods/img/20190227/b5cd0eeb7f654f89ae91a06413eb1236","imgs":"https://jipincheng.cn/goods/img/20190227/b5cd0eeb7f654f89ae91a06413eb1236,https://jipincheng.cn/goods/img/20190227/b5cd0eeb7f654f89ae91a06413eb1236","exchangePoint":10000,"total":200,"content":"<!DOCTYPE html><html><head><meta charset='utf-8' /><style type=\"text/css\"> img {max-width:100%;height: auto !important;display:block;margin-left:auto;margin-right:auto;} <\/style><\/head><body><p>孙飞水电费水电费水电费水电费水电费和价格几号给几号给就蛊惑江湖规划局规划局规划局规划局火锅<\/p>\n<\/body><\/html>","startTime":"2019-03-13 00:00:00","endTime":"2019-03-22 00:00:00","orderNum":2,"up":1,"createTime":"2019-03-12 17:28:04","imgList":["https://jipincheng.cn/goods/img/20190227/b5cd0eeb7f654f89ae91a06413eb1236","https://jipincheng.cn/goods/img/20190227/b5cd0eeb7f654f89ae91a06413eb1236"]}
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

    public static class DataBean implements Serializable{
        /**
         * id : 64f9ebf436fc4ca1b4a565cc8c347427
         * goodsName : 21312312312321
         * img : https://jipincheng.cn/goods/img/20190227/b5cd0eeb7f654f89ae91a06413eb1236
         * imgs : https://jipincheng.cn/goods/img/20190227/b5cd0eeb7f654f89ae91a06413eb1236,https://jipincheng.cn/goods/img/20190227/b5cd0eeb7f654f89ae91a06413eb1236
         * exchangePoint : 10000
         * total : 200
         * content : <!DOCTYPE html><html><head><meta charset='utf-8' /><style type="text/css"> img {max-width:100%;height: auto !important;display:block;margin-left:auto;margin-right:auto;} </style></head><body><p>孙飞水电费水电费水电费水电费水电费和价格几号给几号给就蛊惑江湖规划局规划局规划局规划局火锅</p>
         </body></html>
         * startTime : 2019-03-13 00:00:00
         * endTime : 2019-03-22 00:00:00
         * orderNum : 2
         * up : 1
         * createTime : 2019-03-12 17:28:04
         * imgList : ["https://jipincheng.cn/goods/img/20190227/b5cd0eeb7f654f89ae91a06413eb1236","https://jipincheng.cn/goods/img/20190227/b5cd0eeb7f654f89ae91a06413eb1236"]
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
        private List<String> imgList;

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

        public List<String> getImgList() {
            return imgList;
        }

        public void setImgList(List<String> imgList) {
            this.imgList = imgList;
        }
    }
}
