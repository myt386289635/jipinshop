package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/12/17
 * @Describe
 */
public class MemberBuyBean {

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
         * level : 1
         * levelname : 月卡VIP
         * price : 9.9
         * priceBefore : 15.9
         * remark1 : 限时免费升级12个月卡
         * remark2 : 赠送11个月
         * remark3 : 会员月卡限时福利，购买后额外赠送11个月会员期，
         * img :
         */

        private int level;
        private String levelname;
        private String price;
        private String priceBefore;
        private String remark1;
        private String remark2;
        private String remark3;
        private String img;
        private String preLevelEndTime;
        private String preYearLevelEndTime;

        public String getPreYearLevelEndTime() {
            return preYearLevelEndTime;
        }

        public void setPreYearLevelEndTime(String preYearLevelEndTime) {
            this.preYearLevelEndTime = preYearLevelEndTime;
        }

        public String getPreLevelEndTime() {
            return preLevelEndTime;
        }

        public void setPreLevelEndTime(String preLevelEndTime) {
            this.preLevelEndTime = preLevelEndTime;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getLevelname() {
            return levelname;
        }

        public void setLevelname(String levelname) {
            this.levelname = levelname;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPriceBefore() {
            return priceBefore;
        }

        public void setPriceBefore(String priceBefore) {
            this.priceBefore = priceBefore;
        }

        public String getRemark1() {
            return remark1;
        }

        public void setRemark1(String remark1) {
            this.remark1 = remark1;
        }

        public String getRemark2() {
            return remark2;
        }

        public void setRemark2(String remark2) {
            this.remark2 = remark2;
        }

        public String getRemark3() {
            return remark3;
        }

        public void setRemark3(String remark3) {
            this.remark3 = remark3;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
