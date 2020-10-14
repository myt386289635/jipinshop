package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/3/12
 * @Describe
 */
public class DailyTaskBean {
    
    private String msg;
    private int code;
    private AdBean ad2;
    private int point;
    private AdBean ad1;
    private List<DataBean> data;
    private List<DataBean> list2;

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

    public AdBean getAd2() {
        return ad2;
    }

    public void setAd2(AdBean ad2) {
        this.ad2 = ad2;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public AdBean getAd1() {
        return ad1;
    }

    public void setAd1(AdBean ad1) {
        this.ad1 = ad1;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public List<DataBean> getList2() {
        return list2;
    }

    public void setList2(List<DataBean> list2) {
        this.list2 = list2;
    }

    public static class AdBean {
        /**
         * img : http://jipincheng.cn/activity/img/20200630/f1be1e35950649eb83f017f933756bd5
         * type : 13
         * name : 100%中奖，抽奖赢戴森
         * objectId : http://40.0.0.60:8082/new-free/rafflePage
         * color : null
         * source : 0
         */

        private String img;
        private String type;
        private String name;
        private String objectId;
        private String color;
        private String source;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }
    }

    public static class DataBean {

        private String id;
        private String type;
        private String point;
        private String title;
        private String content;
        private int location;
        private String maxPoint;
        private String iconUrl;
        private String buttonName;
        private String allCount;
        private String todayCount;
        private String status;
        private String isshow;
        private String locationId;//调查问卷地址
        private String locationTitle;

        public String getLocationTitle() {
            return locationTitle;
        }

        public void setLocationTitle(String locationTitle) {
            this.locationTitle = locationTitle;
        }

        public String getLocationId() {
            return locationId;
        }

        public void setLocationId(String locationId) {
            this.locationId = locationId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPoint() {
            return point;
        }

        public void setPoint(String point) {
            this.point = point;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getLocation() {
            return location;
        }

        public void setLocation(int location) {
            this.location = location;
        }

        public String getMaxPoint() {
            return maxPoint;
        }

        public void setMaxPoint(String maxPoint) {
            this.maxPoint = maxPoint;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public String getButtonName() {
            return buttonName;
        }

        public void setButtonName(String buttonName) {
            this.buttonName = buttonName;
        }

        public String getAllCount() {
            return allCount;
        }

        public void setAllCount(String allCount) {
            this.allCount = allCount;
        }

        public String getTodayCount() {
            return todayCount;
        }

        public void setTodayCount(String todayCount) {
            this.todayCount = todayCount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getIsshow() {
            return isshow;
        }

        public void setIsshow(String isshow) {
            this.isshow = isshow;
        }
    }
}
