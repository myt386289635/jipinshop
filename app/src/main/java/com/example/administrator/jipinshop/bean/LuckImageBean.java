package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/10/17
 * @Describe 初始化抽奖图片
 */
public class LuckImageBean {

    /**
     * msg : success
     * code : 200
     * prizeList : [{"id":"1","name":"一等奖","imgurl":"http://pi6611u5d.bkt.clouddn.com/33a5ee6fe8114c169cb37c96c189d68e"},{"id":"2","name":"二等奖","imgurl":"http://pi6611u5d.bkt.clouddn.com/33a5ee6fe8114c169cb37c96c189d68e"},{"id":"3","name":"三等奖","imgurl":"http://pi6611u5d.bkt.clouddn.com/33a5ee6fe8114c169cb37c96c189d68e"},{"id":"4","name":"四等奖","imgurl":"http://pi6611u5d.bkt.clouddn.com/33a5ee6fe8114c169cb37c96c189d68e"},{"id":"5","name":"四等奖","imgurl":"http://pi6611u5d.bkt.clouddn.com/33a5ee6fe8114c169cb37c96c189d68e"},{"id":"6","name":"四等奖","imgurl":"http://pi6611u5d.bkt.clouddn.com/33a5ee6fe8114c169cb37c96c189d68e"},{"id":"7","name":"四等奖","imgurl":"http://pi6611u5d.bkt.clouddn.com/33a5ee6fe8114c169cb37c96c189d68e"},{"id":"8","name":"四等奖","imgurl":"http://pi6611u5d.bkt.clouddn.com/33a5ee6fe8114c169cb37c96c189d68e"}]
     */

    private String msg;
    private int code;
    private List<PrizeListBean> prizeList;

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

    public List<PrizeListBean> getPrizeList() {
        return prizeList;
    }

    public void setPrizeList(List<PrizeListBean> prizeList) {
        this.prizeList = prizeList;
    }

    public static class PrizeListBean {
        /**
         * id : 1
         * name : 一等奖
         * imgurl : http://pi6611u5d.bkt.clouddn.com/33a5ee6fe8114c169cb37c96c189d68e
         */

        private String id;
        private String name;
        private String imgurl;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }
    }
}
