package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/10/10
 * @Describe
 */
public class FovalBean {

    /**
     * msg : success
     * code : 200
     * list : [{"id":"1","user_id":"11","project_id":"545363931984","eval_id":null,"findGoods_id":null,"create_time":"2018-11-06 15:55:18","state":"1","collect_name":null,"collect_price":null,"collect_nubmer":null,"product_img":null,"product_tab":null,"product_price":null,"product_resouce":null,"goodsRanklist":{"goodsId":"545363931984","goodsName":"族绣花中长款衬衫宽松棉打底衬衣","rankGoodImg":"http://img.alicdn.com/bao/uploaded/i1/2455486615/TB1XMhyagsSMeJjSspdXXXZ4pXa_!!0-item_pic.jpg","goodsTypeid":"[{\"name\":\"成人\"},{\"name\":\"小资\"}]","actualPrice":179,"cutPrice":20,"otherPlatform":"布景秋装 翻领民族绣花中长款衬衫宽松棉打底衬衣","otherPrice":199,"visitCount":"9222","recommendReason":null,"goodsGrade":6.5,"goodsStandard":"[{\"name\":\"外观\",\"score\":\"6\"},{\"name\":\"族绣花中\",\"score\":\"1\"},{\"name\":\"族绣花中\",\"score\":\"10\"},{\"name\":\"族绣花中\",\"score\":\"9\"}]","category1_id":"c38c97e81c3142779f9e085902c9423d","category2_id":"bb269c5d3fe94b1cb1d5df68afcda140","createTime":"2018-11-05 20:20:38","rankListStatus":0,"findGoodsStatus":0,"evalWayStatus":0,"pointsStatus":0,"sourceStatus":2,"status":1,"goodstypeList":[{"name":"成人"},{"name":"小资"}],"goodsScopeList":null},"goodsEvalway":null},{"id":"2","user_id":"11","project_id":"544092135549","eval_id":null,"findGoods_id":null,"create_time":"2018-11-06 15:55:16","state":"1","collect_name":null,"collect_price":null,"collect_nubmer":null,"product_img":null,"product_tab":null,"product_price":null,"product_resouce":null,"goodsRanklist":{"goodsId":"544092135549","goodsName":"","rankGoodImg":"http://img.alicdn.com/bao/uploaded/i3/3029542506/TB2OSPichjaK1RjSZFAXXbdLFXa_!!3029542506-0-item_pic.jpg","goodsTypeid":"[]","actualPrice":69.9,"cutPrice":20,"otherPlatform":"新款牛仔外套女装宽松春秋破洞韩版潮学生原宿BF上衣大码长袖夹克","otherPrice":89.9,"visitCount":"0","recommendReason":null,"goodsGrade":0,"goodsStandard":"","category1_id":"c38c97e81c3142779f9e085902c9423d","category2_id":"bb269c5d3fe94b1cb1d5df68afcda140","createTime":"2018-10-30 16:16:39","rankListStatus":0,"findGoodsStatus":0,"evalWayStatus":0,"pointsStatus":0,"sourceStatus":1,"status":0,"goodstypeList":[],"goodsScopeList":null},"goodsEvalway":null},{"id":"3","user_id":"11","project_id":"554830515414","eval_id":null,"findGoods_id":null,"create_time":"2018-11-06 15:55:15","state":"1","collect_name":null,"collect_price":null,"collect_nubmer":null,"product_img":null,"product_tab":null,"product_price":null,"product_resouce":null,"goodsRanklist":{"goodsId":"554830515414","goodsName":"","rankGoodImg":"http://img.alicdn.com/bao/uploaded/i4/2081483336/TB1ms4VdqagSKJjy0FcXXcZeVXa_!!0-item_pic.jpg","goodsTypeid":"[{\"name\":\"成人\"},{\"name\":\"小资\"}]","actualPrice":299,"cutPrice":50,"otherPlatform":"初语女装秋季新款 翻领磨破毛须乞丐风上衣纯色短款长袖牛仔外套","otherPrice":349,"visitCount":"0","recommendReason":null,"goodsGrade":0,"goodsStandard":"","category1_id":"307a1aff33434386bedfd2eda913bd97","category2_id":"a63b74c8706747e2bfaa205c78e5dc15","createTime":"2018-10-30 16:16:54","rankListStatus":0,"findGoodsStatus":0,"evalWayStatus":0,"pointsStatus":0,"sourceStatus":2,"status":0,"goodstypeList":[{"name":"成人"},{"name":"小资"}],"goodsScopeList":null},"goodsEvalway":null},{"id":"4","user_id":"11","project_id":null,"eval_id":"cc898e31a8ca45239d42f261d222046e","findGoods_id":null,"create_time":"2018-11-06 15:55:14","state":"2","collect_name":null,"collect_price":null,"collect_nubmer":null,"product_img":null,"product_tab":null,"product_price":null,"product_resouce":null,"goodsRanklist":null,"goodsEvalway":{"evalWayId":"cc898e31a8ca45239d42f261d222046e","userId":"","imgId":"","visitCount":null,"evalWayName":"看啊看看啊看看","content":null,"commentId":null,"publishTime":null,"createTime":"2018-11-05 20:17:32","status":0}},{"id":"5","user_id":"11","project_id":null,"eval_id":"2a343b92c9ae4bc5b01a16ca48b0df2b","findGoods_id":null,"create_time":"2018-11-06 15:55:13","state":"2","collect_name":null,"collect_price":null,"collect_nubmer":null,"product_img":null,"product_tab":null,"product_price":null,"product_resouce":null,"goodsRanklist":null,"goodsEvalway":{"evalWayId":"2a343b92c9ae4bc5b01a16ca48b0df2b","userId":null,"imgId":"","visitCount":"23222","evalWayName":null,"content":null,"commentId":"","publishTime":null,"createTime":"2018-11-05 20:20:38","status":2}}]
     */

    private String msg;
    private int code;
    private List<ListBean> list;

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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 1
         * user_id : 11
         * project_id : 545363931984
         * eval_id : null
         * findGoods_id : null
         * create_time : 2018-11-06 15:55:18
         * state : 1
         * collect_name : null
         * collect_price : null
         * collect_nubmer : null
         * product_img : null
         * product_tab : null
         * product_price : null
         * product_resouce : null
         * goodsRanklist : {"goodsId":"545363931984","goodsName":"族绣花中长款衬衫宽松棉打底衬衣","rankGoodImg":"http://img.alicdn.com/bao/uploaded/i1/2455486615/TB1XMhyagsSMeJjSspdXXXZ4pXa_!!0-item_pic.jpg","goodsTypeid":"[{\"name\":\"成人\"},{\"name\":\"小资\"}]","actualPrice":179,"cutPrice":20,"otherPlatform":"布景秋装 翻领民族绣花中长款衬衫宽松棉打底衬衣","otherPrice":199,"visitCount":"9222","recommendReason":null,"goodsGrade":6.5,"goodsStandard":"[{\"name\":\"外观\",\"score\":\"6\"},{\"name\":\"族绣花中\",\"score\":\"1\"},{\"name\":\"族绣花中\",\"score\":\"10\"},{\"name\":\"族绣花中\",\"score\":\"9\"}]","category1_id":"c38c97e81c3142779f9e085902c9423d","category2_id":"bb269c5d3fe94b1cb1d5df68afcda140","createTime":"2018-11-05 20:20:38","rankListStatus":0,"findGoodsStatus":0,"evalWayStatus":0,"pointsStatus":0,"sourceStatus":2,"status":1,"goodstypeList":[{"name":"成人"},{"name":"小资"}],"goodsScopeList":null}
         * goodsEvalway : null
         */

        private String id;
        private String user_id;
        private String project_id;
        private Object eval_id;
        private Object findGoods_id;
        private String create_time;
        private String state;
        private Object collect_name;
        private Object collect_price;
        private Object collect_nubmer;
        private Object product_img;
        private Object product_tab;
        private Object product_price;
        private Object product_resouce;
        private GoodsRanklistBean goodsRanklist;
        private GoodsEvalwayBean goodsEvalway;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getProject_id() {
            return project_id;
        }

        public void setProject_id(String project_id) {
            this.project_id = project_id;
        }

        public Object getEval_id() {
            return eval_id;
        }

        public void setEval_id(Object eval_id) {
            this.eval_id = eval_id;
        }

        public Object getFindGoods_id() {
            return findGoods_id;
        }

        public void setFindGoods_id(Object findGoods_id) {
            this.findGoods_id = findGoods_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public Object getCollect_name() {
            return collect_name;
        }

        public void setCollect_name(Object collect_name) {
            this.collect_name = collect_name;
        }

        public Object getCollect_price() {
            return collect_price;
        }

        public void setCollect_price(Object collect_price) {
            this.collect_price = collect_price;
        }

        public Object getCollect_nubmer() {
            return collect_nubmer;
        }

        public void setCollect_nubmer(Object collect_nubmer) {
            this.collect_nubmer = collect_nubmer;
        }

        public Object getProduct_img() {
            return product_img;
        }

        public void setProduct_img(Object product_img) {
            this.product_img = product_img;
        }

        public Object getProduct_tab() {
            return product_tab;
        }

        public void setProduct_tab(Object product_tab) {
            this.product_tab = product_tab;
        }

        public Object getProduct_price() {
            return product_price;
        }

        public void setProduct_price(Object product_price) {
            this.product_price = product_price;
        }

        public Object getProduct_resouce() {
            return product_resouce;
        }

        public void setProduct_resouce(Object product_resouce) {
            this.product_resouce = product_resouce;
        }

        public GoodsRanklistBean getGoodsRanklist() {
            return goodsRanklist;
        }

        public void setGoodsRanklist(GoodsRanklistBean goodsRanklist) {
            this.goodsRanklist = goodsRanklist;
        }

        public GoodsEvalwayBean getGoodsEvalway() {
            return goodsEvalway;
        }

        public void setGoodsEvalway(GoodsEvalwayBean goodsEvalway) {
            this.goodsEvalway = goodsEvalway;
        }

        public static class GoodsRanklistBean {
            /**
             * goodsId : 545363931984
             * goodsName : 族绣花中长款衬衫宽松棉打底衬衣
             * rankGoodImg : http://img.alicdn.com/bao/uploaded/i1/2455486615/TB1XMhyagsSMeJjSspdXXXZ4pXa_!!0-item_pic.jpg
             * goodsTypeid : [{"name":"成人"},{"name":"小资"}]
             * actualPrice : 179
             * cutPrice : 20
             * otherPlatform : 布景秋装 翻领民族绣花中长款衬衫宽松棉打底衬衣
             * otherPrice : 199
             * visitCount : 9222
             * recommendReason : null
             * goodsGrade : 6.5
             * goodsStandard : [{"name":"外观","score":"6"},{"name":"族绣花中","score":"1"},{"name":"族绣花中","score":"10"},{"name":"族绣花中","score":"9"}]
             * category1_id : c38c97e81c3142779f9e085902c9423d
             * category2_id : bb269c5d3fe94b1cb1d5df68afcda140
             * createTime : 2018-11-05 20:20:38
             * rankListStatus : 0
             * findGoodsStatus : 0
             * evalWayStatus : 0
             * pointsStatus : 0
             * sourceStatus : 2
             * status : 1
             * goodstypeList : [{"name":"成人"},{"name":"小资"}]
             * goodsScopeList : null
             */

            private String goodsId;
            private String goodsName;
            private String rankGoodImg;
            private String goodsTypeid;
            private String actualPrice;
            private String cutPrice;
            private String otherPlatform;
            private String otherPrice;
            private String visitCount;
            private Object recommendReason;
            private String goodsGrade;
            private String goodsStandard;
            private String category1_id;
            private String category2_id;
            private String createTime;
            private String rankListStatus;
            private String findGoodsStatus;
            private String evalWayStatus;
            private String pointsStatus;
            private int sourceStatus;
            private String status;
            private Object goodsScopeList;
            private List<GoodstypeListBean> goodstypeList;

            public String getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(String goodsId) {
                this.goodsId = goodsId;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getRankGoodImg() {
                return rankGoodImg;
            }

            public void setRankGoodImg(String rankGoodImg) {
                this.rankGoodImg = rankGoodImg;
            }

            public String getGoodsTypeid() {
                return goodsTypeid;
            }

            public void setGoodsTypeid(String goodsTypeid) {
                this.goodsTypeid = goodsTypeid;
            }

            public String getActualPrice() {
                return actualPrice;
            }

            public void setActualPrice(String actualPrice) {
                this.actualPrice = actualPrice;
            }

            public String getCutPrice() {
                return cutPrice;
            }

            public void setCutPrice(String cutPrice) {
                this.cutPrice = cutPrice;
            }

            public String getOtherPlatform() {
                return otherPlatform;
            }

            public void setOtherPlatform(String otherPlatform) {
                this.otherPlatform = otherPlatform;
            }

            public String getOtherPrice() {
                return otherPrice;
            }

            public void setOtherPrice(String otherPrice) {
                this.otherPrice = otherPrice;
            }

            public String getVisitCount() {
                return visitCount;
            }

            public void setVisitCount(String visitCount) {
                this.visitCount = visitCount;
            }

            public Object getRecommendReason() {
                return recommendReason;
            }

            public void setRecommendReason(Object recommendReason) {
                this.recommendReason = recommendReason;
            }

            public String getGoodsGrade() {
                return goodsGrade;
            }

            public void setGoodsGrade(String goodsGrade) {
                this.goodsGrade = goodsGrade;
            }

            public String getGoodsStandard() {
                return goodsStandard;
            }

            public void setGoodsStandard(String goodsStandard) {
                this.goodsStandard = goodsStandard;
            }

            public String getCategory1_id() {
                return category1_id;
            }

            public void setCategory1_id(String category1_id) {
                this.category1_id = category1_id;
            }

            public String getCategory2_id() {
                return category2_id;
            }

            public void setCategory2_id(String category2_id) {
                this.category2_id = category2_id;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getRankListStatus() {
                return rankListStatus;
            }

            public void setRankListStatus(String rankListStatus) {
                this.rankListStatus = rankListStatus;
            }

            public String getFindGoodsStatus() {
                return findGoodsStatus;
            }

            public void setFindGoodsStatus(String findGoodsStatus) {
                this.findGoodsStatus = findGoodsStatus;
            }

            public String getEvalWayStatus() {
                return evalWayStatus;
            }

            public void setEvalWayStatus(String evalWayStatus) {
                this.evalWayStatus = evalWayStatus;
            }

            public String getPointsStatus() {
                return pointsStatus;
            }

            public void setPointsStatus(String pointsStatus) {
                this.pointsStatus = pointsStatus;
            }

            public int getSourceStatus() {
                return sourceStatus;
            }

            public void setSourceStatus(int sourceStatus) {
                this.sourceStatus = sourceStatus;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public Object getGoodsScopeList() {
                return goodsScopeList;
            }

            public void setGoodsScopeList(Object goodsScopeList) {
                this.goodsScopeList = goodsScopeList;
            }

            public List<GoodstypeListBean> getGoodstypeList() {
                return goodstypeList;
            }

            public void setGoodstypeList(List<GoodstypeListBean> goodstypeList) {
                this.goodstypeList = goodstypeList;
            }

            public static class GoodstypeListBean {
                /**
                 * name : 成人
                 */

                private String name;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }

        public static class GoodsEvalwayBean {

            private String evalWayId;
            private String userId;
            private String imgId;
            private String visitCount;
            private String evalWayName;
            private String content;
            private String commentId;
            private String publishTime;
            private String createTime;
            private String status;

            public String getEvalWayId() {
                return evalWayId;
            }

            public void setEvalWayId(String evalWayId) {
                this.evalWayId = evalWayId;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getImgId() {
                return imgId;
            }

            public void setImgId(String imgId) {
                this.imgId = imgId;
            }

            public String getVisitCount() {
                return visitCount;
            }

            public void setVisitCount(String visitCount) {
                this.visitCount = visitCount;
            }

            public String getEvalWayName() {
                return evalWayName;
            }

            public void setEvalWayName(String evalWayName) {
                this.evalWayName = evalWayName;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getCommentId() {
                return commentId;
            }

            public void setCommentId(String commentId) {
                this.commentId = commentId;
            }

            public String getPublishTime() {
                return publishTime;
            }

            public void setPublishTime(String publishTime) {
                this.publishTime = publishTime;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}
