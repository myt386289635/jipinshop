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
     * code : 200
     * list : [{"subtilte":[{"categoryid":"31c71a9ae22047e5ab966ca478325661","categoryname":"剃毛器","categoryCode":"G","parentid":"c38c97e81c3142779f9e085902c9423d","parentName":""},{"categoryid":"513f96f9ab4d44bbad7b5b13dc4dce3c","categoryname":"计步器","categoryCode":"M","parentid":"c38c97e81c3142779f9e085902c9423d","parentName":""},{"categoryid":"54a82377c556471dbdeddd1dd7e60a00","categoryname":"耳温枪","categoryCode":"H","parentid":"c38c97e81c3142779f9e085902c9423d","parentName":""},{"categoryid":"6b57e095a15d45079c1290510d3c125d","categoryname":"体重秤","categoryCode":"L","parentid":"c38c97e81c3142779f9e085902c9423d","parentName":""},{"categoryid":"6de718eba1d74b80bb03a23ed6773290","categoryname":"眼部按摩仪","categoryCode":"I","parentid":"c38c97e81c3142779f9e085902c9423d","parentName":""},{"categoryid":"87ed34710ef24704b393f753da7f270b","categoryname":"足浴盆","categoryCode":"K","parentid":"c38c97e81c3142779f9e085902c9423d","parentName":""},{"categoryid":"95f3aba5776a425aabee024f60dafff2","categoryname":"按摩椅","categoryCode":"J","parentid":"c38c97e81c3142779f9e085902c9423d","parentName":""},{"categoryid":"99cc6b195a314e75b2523634e679215c","categoryname":"卷发器","categoryCode":"E","parentid":"c38c97e81c3142779f9e085902c9423d","parentName":""},{"categoryid":"a9a2a7a97b2441c5a2234619c5ed59e1","categoryname":"美容仪","categoryCode":"D","parentid":"c38c97e81c3142779f9e085902c9423d","parentName":""},{"categoryid":"b4cb5713c5d548f5a9196ccc8bb0f245","categoryname":"电吹风","categoryCode":"C","parentid":"c38c97e81c3142779f9e085902c9423d","parentName":""},{"categoryid":"bb269c5d3fe94b1cb1d5df68afcda140","categoryname":"剃须刀","categoryCode":"A","parentid":"c38c97e81c3142779f9e085902c9423d","parentName":""},{"categoryid":"e2750799418043ceb3288739ef372e10","categoryname":"电动牙刷","categoryCode":"B","parentid":"c38c97e81c3142779f9e085902c9423d","parentName":""},{"categoryid":"ea8455cc01b643cb86c43437c4a566ff","categoryname":"理发器","categoryCode":"F","parentid":"c38c97e81c3142779f9e085902c9423d","parentName":""}],"tilte":{"categoryid":"c38c97e81c3142779f9e085902c9423d","categoryname":"个护健康","categoryCode":"1","parentid":"0","parentName":""}},{"subtilte":[{"categoryid":"0225166f6c664957bc41e1db05abd8bf","categoryname":"微波炉","categoryCode":"C","parentid":"307a1aff33434386bedfd2eda913bd97","parentName":""},{"categoryid":"3ddc31b6ddf44bea8ceb61e7f1749d3a","categoryname":"电磁炉/电陶炉","categoryCode":"B","parentid":"307a1aff33434386bedfd2eda913bd97","parentName":""},{"categoryid":"6bfac3f655dc4bfa8600b33846033179","categoryname":"料理机","categoryCode":"D","parentid":"307a1aff33434386bedfd2eda913bd97","parentName":""},{"categoryid":"a3cc3b108bcf4c1b91479f2b8e8cc3b1","categoryname":"搅拌/榨汁机","categoryCode":"E","parentid":"307a1aff33434386bedfd2eda913bd97","parentName":""},{"categoryid":"a63b74c8706747e2bfaa205c78e5dc15","categoryname":"电饭煲","categoryCode":"A","parentid":"307a1aff33434386bedfd2eda913bd97","parentName":""}],"tilte":{"categoryid":"307a1aff33434386bedfd2eda913bd97","categoryname":"厨卫电器","categoryCode":"2","parentid":"0","parentName":""}},{"subtilte":[{"categoryid":"507e3f52328a47a59e563bc918efcc3f","categoryname":"空调扇","categoryCode":"B","parentid":"971cf3d41cb0441f88af248578cbb7e6","parentName":""},{"categoryid":"6fa6276ba4914ba5b6764fd9c6feddeb","categoryname":"吸尘器","categoryCode":"C","parentid":"971cf3d41cb0441f88af248578cbb7e6","parentName":""},{"categoryid":"afbe5c62ba2d4e7d92c92b4a4c30db58","categoryname":"取暖器","categoryCode":"A","parentid":"971cf3d41cb0441f88af248578cbb7e6","parentName":""}],"tilte":{"categoryid":"971cf3d41cb0441f88af248578cbb7e6","categoryname":"生活家电","categoryCode":"3","parentid":"0","parentName":""}},{"subtilte":[{"categoryid":"a3fc98da2f404c2190353ca571aabd5c","categoryname":"生活家电","categoryCode":"0","parentid":"cb1ccdb60b0448eda2e709dc10cd8244","parentName":""}],"tilte":{"categoryid":"cb1ccdb60b0448eda2e709dc10cd8244","categoryname":"家用大电","categoryCode":"4","parentid":"0","parentName":""}}]
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
         * subtilte : [{"categoryid":"31c71a9ae22047e5ab966ca478325661","categoryname":"剃毛器","categoryCode":"G","parentid":"c38c97e81c3142779f9e085902c9423d","parentName":""},{"categoryid":"513f96f9ab4d44bbad7b5b13dc4dce3c","categoryname":"计步器","categoryCode":"M","parentid":"c38c97e81c3142779f9e085902c9423d","parentName":""},{"categoryid":"54a82377c556471dbdeddd1dd7e60a00","categoryname":"耳温枪","categoryCode":"H","parentid":"c38c97e81c3142779f9e085902c9423d","parentName":""},{"categoryid":"6b57e095a15d45079c1290510d3c125d","categoryname":"体重秤","categoryCode":"L","parentid":"c38c97e81c3142779f9e085902c9423d","parentName":""},{"categoryid":"6de718eba1d74b80bb03a23ed6773290","categoryname":"眼部按摩仪","categoryCode":"I","parentid":"c38c97e81c3142779f9e085902c9423d","parentName":""},{"categoryid":"87ed34710ef24704b393f753da7f270b","categoryname":"足浴盆","categoryCode":"K","parentid":"c38c97e81c3142779f9e085902c9423d","parentName":""},{"categoryid":"95f3aba5776a425aabee024f60dafff2","categoryname":"按摩椅","categoryCode":"J","parentid":"c38c97e81c3142779f9e085902c9423d","parentName":""},{"categoryid":"99cc6b195a314e75b2523634e679215c","categoryname":"卷发器","categoryCode":"E","parentid":"c38c97e81c3142779f9e085902c9423d","parentName":""},{"categoryid":"a9a2a7a97b2441c5a2234619c5ed59e1","categoryname":"美容仪","categoryCode":"D","parentid":"c38c97e81c3142779f9e085902c9423d","parentName":""},{"categoryid":"b4cb5713c5d548f5a9196ccc8bb0f245","categoryname":"电吹风","categoryCode":"C","parentid":"c38c97e81c3142779f9e085902c9423d","parentName":""},{"categoryid":"bb269c5d3fe94b1cb1d5df68afcda140","categoryname":"剃须刀","categoryCode":"A","parentid":"c38c97e81c3142779f9e085902c9423d","parentName":""},{"categoryid":"e2750799418043ceb3288739ef372e10","categoryname":"电动牙刷","categoryCode":"B","parentid":"c38c97e81c3142779f9e085902c9423d","parentName":""},{"categoryid":"ea8455cc01b643cb86c43437c4a566ff","categoryname":"理发器","categoryCode":"F","parentid":"c38c97e81c3142779f9e085902c9423d","parentName":""}]
         * tilte : {"categoryid":"c38c97e81c3142779f9e085902c9423d","categoryname":"个护健康","categoryCode":"1","parentid":"0","parentName":""}
         */

        private TilteBean tilte;
        private List<SubtilteBean> subtilte;

        public TilteBean getTilte() {
            return tilte;
        }

        public void setTilte(TilteBean tilte) {
            this.tilte = tilte;
        }

        public List<SubtilteBean> getSubtilte() {
            return subtilte;
        }

        public void setSubtilte(List<SubtilteBean> subtilte) {
            this.subtilte = subtilte;
        }

        public static class TilteBean {
            /**
             * categoryid : c38c97e81c3142779f9e085902c9423d
             * categoryname : 个护健康
             * categoryCode : 1
             * parentid : 0
             * parentName :
             */

            private String categoryid;
            private String categoryname;
            private String categoryCode;
            private String parentid;
            private String parentName;

            public String getCategoryid() {
                return categoryid;
            }

            public void setCategoryid(String categoryid) {
                this.categoryid = categoryid;
            }

            public String getCategoryname() {
                return categoryname;
            }

            public void setCategoryname(String categoryname) {
                this.categoryname = categoryname;
            }

            public String getCategoryCode() {
                return categoryCode;
            }

            public void setCategoryCode(String categoryCode) {
                this.categoryCode = categoryCode;
            }

            public String getParentid() {
                return parentid;
            }

            public void setParentid(String parentid) {
                this.parentid = parentid;
            }

            public String getParentName() {
                return parentName;
            }

            public void setParentName(String parentName) {
                this.parentName = parentName;
            }
        }

        public static class SubtilteBean {
            /**
             * categoryid : 31c71a9ae22047e5ab966ca478325661
             * categoryname : 剃毛器
             * categoryCode : G
             * parentid : c38c97e81c3142779f9e085902c9423d
             * parentName :
             */

            private String categoryid;
            private String categoryname;
            private String categoryCode;
            private String parentid;
            private String parentName;

            public String getCategoryid() {
                return categoryid;
            }

            public void setCategoryid(String categoryid) {
                this.categoryid = categoryid;
            }

            public String getCategoryname() {
                return categoryname;
            }

            public void setCategoryname(String categoryname) {
                this.categoryname = categoryname;
            }

            public String getCategoryCode() {
                return categoryCode;
            }

            public void setCategoryCode(String categoryCode) {
                this.categoryCode = categoryCode;
            }

            public String getParentid() {
                return parentid;
            }

            public void setParentid(String parentid) {
                this.parentid = parentid;
            }

            public String getParentName() {
                return parentName;
            }

            public void setParentName(String parentName) {
                this.parentName = parentName;
            }
        }
    }
}
