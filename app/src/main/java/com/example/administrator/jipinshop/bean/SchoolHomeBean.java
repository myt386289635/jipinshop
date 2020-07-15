package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/7/14
 * @Describe
 */
public class SchoolHomeBean {

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
        private List<TbkIndexBean.DataBean.Ad1ListBean> adList;
        private List<BoxListBean> boxList;
        private List<CategoryListBean> categoryList;

        public List<TbkIndexBean.DataBean.Ad1ListBean> getAdList() {
            return adList;
        }

        public void setAdList(List<TbkIndexBean.DataBean.Ad1ListBean> adList) {
            this.adList = adList;
        }

        public List<BoxListBean> getBoxList() {
            return boxList;
        }

        public void setBoxList(List<BoxListBean> boxList) {
            this.boxList = boxList;
        }

        public List<CategoryListBean> getCategoryList() {
            return categoryList;
        }

        public void setCategoryList(List<CategoryListBean> categoryList) {
            this.categoryList = categoryList;
        }

        public static class BoxListBean {
            /**
             * id : 7
             * iconUrl : http://jipincheng.cn/category/img/20200624/fcc6ce1b3e3d475981ae37fb135c7efc
             * title : 赚钱教程
             * orderNum : 9
             * status : 1
             * type : 0
             * targetId : null
             * source : 0
             */

            private String id;
            private String iconUrl;
            private String title;
            private String orderNum;
            private String status;
            private String type;
            private String targetId;
            private String source;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getIconUrl() {
                return iconUrl;
            }

            public void setIconUrl(String iconUrl) {
                this.iconUrl = iconUrl;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getOrderNum() {
                return orderNum;
            }

            public void setOrderNum(String orderNum) {
                this.orderNum = orderNum;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getTargetId() {
                return targetId;
            }

            public void setTargetId(String targetId) {
                this.targetId = targetId;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }
        }

        public static class CategoryListBean {
            /**
             * id : 1
             * name : 省钱教程
             * orderNum : 1
             * courseList : [{"id":"1","categoryId":"1","title":"√1.如何邀请好友成为你的粉丝","thumb":"https://jipincheng.cn/defaultImg.png","video":"https://jipincheng.cn/test1.mp4","status":1,"orderNum":0,"creator":null,"reading":31260,"share":2649,"liked":5963,"createTime":"2020-07-14 10:48:23","updateTme":"2020-07-14 10:48:25"}]
             */

            private String id;
            private String name;
            private String orderNum;
            private List<CourseListBean> courseList;

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

            public String getOrderNum() {
                return orderNum;
            }

            public void setOrderNum(String orderNum) {
                this.orderNum = orderNum;
            }

            public List<CourseListBean> getCourseList() {
                return courseList;
            }

            public void setCourseList(List<CourseListBean> courseList) {
                this.courseList = courseList;
            }

            public static class CourseListBean {
                /**
                 * id : 1
                 * categoryId : 1
                 * title : √1.如何邀请好友成为你的粉丝
                 * thumb : https://jipincheng.cn/defaultImg.png
                 * video : https://jipincheng.cn/test1.mp4
                 * status : 1
                 * orderNum : 0
                 * creator : null
                 * reading : 31260
                 * share : 2649
                 * liked : 5963
                 * createTime : 2020-07-14 10:48:23
                 * updateTme : 2020-07-14 10:48:25
                 */

                private String id;
                private String categoryId;
                private String title;
                private String thumb;
                private String video;
                private String status;
                private String orderNum;
                private String creator;
                private String reading;
                private String share;
                private String liked;
                private String createTime;
                private String updateTme;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getCategoryId() {
                    return categoryId;
                }

                public void setCategoryId(String categoryId) {
                    this.categoryId = categoryId;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getThumb() {
                    return thumb;
                }

                public void setThumb(String thumb) {
                    this.thumb = thumb;
                }

                public String getVideo() {
                    return video;
                }

                public void setVideo(String video) {
                    this.video = video;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public String getOrderNum() {
                    return orderNum;
                }

                public void setOrderNum(String orderNum) {
                    this.orderNum = orderNum;
                }

                public String getCreator() {
                    return creator;
                }

                public void setCreator(String creator) {
                    this.creator = creator;
                }

                public String getReading() {
                    return reading;
                }

                public void setReading(String reading) {
                    this.reading = reading;
                }

                public String getShare() {
                    return share;
                }

                public void setShare(String share) {
                    this.share = share;
                }

                public String getLiked() {
                    return liked;
                }

                public void setLiked(String liked) {
                    this.liked = liked;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }

                public String getUpdateTme() {
                    return updateTme;
                }

                public void setUpdateTme(String updateTme) {
                    this.updateTme = updateTme;
                }
            }
        }
    }
}
