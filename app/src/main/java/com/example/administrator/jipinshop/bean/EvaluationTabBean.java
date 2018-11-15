package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/11/15
 * @Describe 评测tab
 */
public class EvaluationTabBean {

    /**
     * msg : success
     * code : 200
     * list : [{"categoryId":"b6f89e5c92704579993b9e4f579aa9aa","categoryName":"精选榜","categoryCode":0,"status":1},{"categoryId":"7ee077bc59e84a2eb2ba85c4cc068d58","categoryName":"个护健康","categoryCode":1,"status":1},{"categoryId":"17badcca7e3f4147be206eb7fd828046","categoryName":"厨卫电器","categoryCode":2,"status":1},{"categoryId":"e3e088bd3d04408f8fc61e210f7fcc11","categoryName":"生活家电","categoryCode":3,"status":1},{"categoryId":"744d4b11ca3f47f98525b729682b8672","categoryName":"家用大电","categoryCode":4,"status":1}]
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
         * categoryId : b6f89e5c92704579993b9e4f579aa9aa
         * categoryName : 精选榜
         * categoryCode : 0
         * status : 1
         */

        private String categoryId;
        private String categoryName;
        private int categoryCode;
        private int status;

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public int getCategoryCode() {
            return categoryCode;
        }

        public void setCategoryCode(int categoryCode) {
            this.categoryCode = categoryCode;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
