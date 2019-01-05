package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/10/17
 * @Describe
 */
public class TabBean {

    /**
     * msg : success
     * code : 0
     * data : [{"categoryId":"0ae94ab3649c4806a099c28353c66d1a","parentId":"0","categoryName":"综合榜","orderNum":0,"children":[]},{"categoryId":"c38c97e81c3142779f9e085902c9423d","parentId":"0","categoryName":"个护健康榜","orderNum":1,"children":[{"categoryId":"3a9da7e316914052ab207c6162a59ae6","parentId":"c38c97e81c3142779f9e085902c9423d","categoryName":"电动牙刷","orderNum":1,"children":[]},{"categoryId":"bb269c5d3fe94b1cb1d5df68afcda140","parentId":"c38c97e81c3142779f9e085902c9423d","categoryName":"剃须刀","orderNum":2,"children":[]},{"categoryId":"b4cb5713c5d548f5a9196ccc8bb0f245","parentId":"c38c97e81c3142779f9e085902c9423d","categoryName":"电吹风","orderNum":3,"children":[]},{"categoryId":"ae89a86ec0f647729607db165f3b9d8a","parentId":"c38c97e81c3142779f9e085902c9423d","categoryName":"卷/直发器","orderNum":4,"children":[]},{"categoryId":"c5701c52584f4a0d8ea41dd91e4e8cd4","parentId":"c38c97e81c3142779f9e085902c9423d","categoryName":"美容仪","orderNum":5,"children":[]},{"categoryId":"ea8455cc01b643cb86c43437c4a566ff","parentId":"c38c97e81c3142779f9e085902c9423d","categoryName":"理发器","orderNum":6,"children":[]},{"categoryId":"d645d23f5a10440ea600008d11a8d73f","parentId":"c38c97e81c3142779f9e085902c9423d","categoryName":"体重秤","orderNum":7,"children":[]},{"categoryId":"d7ce478106d847d4b1a3442783910c54","parentId":"c38c97e81c3142779f9e085902c9423d","categoryName":"按摩椅","orderNum":8,"children":[]},{"categoryId":"fa16deee893a4e3bb7b4c9ab384c35a7","parentId":"c38c97e81c3142779f9e085902c9423d","categoryName":"足浴盆","orderNum":9,"children":[]}]},{"categoryId":"307a1aff33434386bedfd2eda913bd97","parentId":"0","categoryName":"厨房电器榜","orderNum":2,"children":[{"categoryId":"3ddc31b6ddf44bea8ceb61e7f1749d3a","parentId":"307a1aff33434386bedfd2eda913bd97","categoryName":"电火锅","orderNum":0,"children":[]},{"categoryId":"8452e9083c914af0badfebc21bdd55ee","parentId":"307a1aff33434386bedfd2eda913bd97","categoryName":"11111","orderNum":0,"children":[]},{"categoryId":"a3cc3b108bcf4c1b91479f2b8e8cc3b1","parentId":"307a1aff33434386bedfd2eda913bd97","categoryName":"豆浆机","orderNum":0,"children":[]},{"categoryId":"a63b74c8706747e2bfaa205c78e5dc15","parentId":"307a1aff33434386bedfd2eda913bd97","categoryName":"蒸蛋器","orderNum":0,"children":[]}]},{"categoryId":"971cf3d41cb0441f88af248578cbb7e6","parentId":"0","categoryName":"生活电器榜","orderNum":3,"children":[{"categoryId":"758906e13b614e688224572d2f99f957","parentId":"971cf3d41cb0441f88af248578cbb7e6","categoryName":"扫地机器人","orderNum":0,"children":[]},{"categoryId":"81bcd5911a794af8b968ff010d2546df","parentId":"971cf3d41cb0441f88af248578cbb7e6","categoryName":"电风扇","orderNum":0,"children":[]},{"categoryId":"db61fa35e64c441ebd27ad81effce436","parentId":"971cf3d41cb0441f88af248578cbb7e6","categoryName":"空气净化器","orderNum":0,"children":[]}]}]
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
         * categoryId : 0ae94ab3649c4806a099c28353c66d1a
         * parentId : 0
         * categoryName : 综合榜
         * orderNum : 0
         * children : []
         */

        private String categoryId;
        private String parentId;
        private String categoryName;
        private int orderNum;
        private List<ChildrenBean> children;
        private List<AdListBean> adList;

        public List<AdListBean> getAdList() {
            return adList;
        }

        public void setAdList(List<AdListBean> adList) {
            this.adList = adList;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public int getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(int orderNum) {
            this.orderNum = orderNum;
        }

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public static class ChildrenBean {
            private String categoryId;
            private String parentId;
            private String categoryName;
            private int orderNum;


            public String getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(String categoryId) {
                this.categoryId = categoryId;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public String getCategoryName() {
                return categoryName;
            }

            public void setCategoryName(String categoryName) {
                this.categoryName = categoryName;
            }

            public int getOrderNum() {
                return orderNum;
            }

            public void setOrderNum(int orderNum) {
                this.orderNum = orderNum;
            }
        }

        public static class AdListBean{
            private String img;
            private String objectId;
            private String type;

            public String getObjectId() {
                return objectId;
            }

            public void setObjectId(String objectId) {
                this.objectId = objectId;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }
    }
}
