package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2018/10/16
 * @Describe 抽奖的结果
 */
public class LuckselectBean {

    /**
     * msg : success
     * code : 200
     * myPrize : {"id":"2","name":"二等奖","imgurl":"http://pi6611u5d.bkt.clouddn.com/33a5ee6fe8114c169cb37c96c189d68e"}
     * points : 204
     */

    private String msg;
    private int code;
    private MyPrizeBean myPrize;
    private int points;

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

    public MyPrizeBean getMyPrize() {
        return myPrize;
    }

    public void setMyPrize(MyPrizeBean myPrize) {
        this.myPrize = myPrize;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public static class MyPrizeBean {
        /**
         * id : 2
         * name : 二等奖
         * imgurl : http://pi6611u5d.bkt.clouddn.com/33a5ee6fe8114c169cb37c96c189d68e
         */

        private int id;
        private String name;
        private String imgurl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
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
