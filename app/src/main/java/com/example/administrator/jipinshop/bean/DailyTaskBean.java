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

        private int point;
        private int needPoint;
        private Ad1Bean ad1;
        private Ad1Bean ad2;
        private int isSignin;//0未签到，1已签到
        private List<SigninListBean> signinList;
        private List<ListBean> list1;
        private List<ListBean> list2;
        private List<MyWalletBean.DataBean.AdListBean> boxList;
        private List<MallBean.DataBean> goodsList;
        private String officialWechat;

        public String getOfficialWechat() {
            return officialWechat;
        }

        public void setOfficialWechat(String officialWechat) {
            this.officialWechat = officialWechat;
        }

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }

        public int getNeedPoint() {
            return needPoint;
        }

        public void setNeedPoint(int needPoint) {
            this.needPoint = needPoint;
        }

        public Ad1Bean getAd1() {
            return ad1;
        }

        public void setAd1(Ad1Bean ad1) {
            this.ad1 = ad1;
        }

        public Ad1Bean getAd2() {
            return ad2;
        }

        public void setAd2(Ad1Bean ad2) {
            this.ad2 = ad2;
        }

        public int getIsSignin() {
            return isSignin;
        }

        public void setIsSignin(int isSignin) {
            this.isSignin = isSignin;
        }

        public List<SigninListBean> getSigninList() {
            return signinList;
        }

        public void setSigninList(List<SigninListBean> signinList) {
            this.signinList = signinList;
        }

        public List<ListBean> getList1() {
            return list1;
        }

        public void setList1(List<ListBean> list1) {
            this.list1 = list1;
        }

        public List<ListBean> getList2() {
            return list2;
        }

        public void setList2(List<ListBean> list2) {
            this.list2 = list2;
        }

        public List<MyWalletBean.DataBean.AdListBean> getBoxList() {
            return boxList;
        }

        public void setBoxList(List<MyWalletBean.DataBean.AdListBean> boxList) {
            this.boxList = boxList;
        }

        public List<MallBean.DataBean> getGoodsList() {
            return goodsList;
        }

        public void setGoodsList(List<MallBean.DataBean> goodsList) {
            this.goodsList = goodsList;
        }

        public static class Ad1Bean {
            /**
             * img : http://jipincheng.cn/activity/img/20210317/43982a940cbf4ef9a0cca2885b4a0ed1
             * type : 52
             * name : 百万补贴
             * objectId :
             * color : null
             * source : 0
             * remark :
             */

            private String img;
            private String type;
            private String name;
            private String objectId;
            private String color;
            private String source;
            private String remark;

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

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
        }

        public static class SigninListBean {
            /**
             * point : 2
             * status : 0
             */

            private int point;
            private int status;//-1待补签 0 代签 1已签到

            public int getPoint() {
                return point;
            }

            public void setPoint(int point) {
                this.point = point;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }

        public static class ListBean {
            /**
             * id : 10
             * type : 10
             * point : 2
             * title : 签到(0/1)
             * content : 极币+2
             * location : 9
             * locationTitle : null
             * locationId : null
             * maxPoint : 2
             * iconUrl : http://jipincheng.cn/category/img/20200630/dda3ebc263ff49d1a38322efa55260ad
             * buttonName : 去签到
             * allCount : 1
             * todayCount : 0
             * status : 0
             * isshow : 1
             */

            private String id;
            private int type;
            private int point;
            private String title;
            private String content;
            private int location;
            private String locationTitle;
            private String locationId;
            private int maxPoint;
            private String iconUrl;
            private String buttonName;
            private int allCount;
            private int todayCount;
            private String status;
            private int isshow;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getPoint() {
                return point;
            }

            public void setPoint(int point) {
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

            public int getMaxPoint() {
                return maxPoint;
            }

            public void setMaxPoint(int maxPoint) {
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

            public int getAllCount() {
                return allCount;
            }

            public void setAllCount(int allCount) {
                this.allCount = allCount;
            }

            public int getTodayCount() {
                return todayCount;
            }

            public void setTodayCount(int todayCount) {
                this.todayCount = todayCount;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public int getIsshow() {
                return isshow;
            }

            public void setIsshow(int isshow) {
                this.isshow = isshow;
            }
        }

    }
}
