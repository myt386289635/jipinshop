package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/11/10
 * @Describe
 */
public class PlayBean {

    private String msg;
    private int code;
    private List<DataBean> data;
    private List<EvaluationTabBean.DataBean.AdListBean> ad;

    public List<EvaluationTabBean.DataBean.AdListBean> getAd() {
        return ad;
    }

    public void setAd(List<EvaluationTabBean.DataBean.AdListBean> ad) {
        this.ad = ad;
    }

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

        private String categoryTitle;
        private String id;
        private List<MemberNewBean.DataBean.LevelDetail7Bean.ListBeanXXX> list;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCategoryTitle() {
            return categoryTitle;
        }

        public void setCategoryTitle(String categoryTitle) {
            this.categoryTitle = categoryTitle;
        }

        public List<MemberNewBean.DataBean.LevelDetail7Bean.ListBeanXXX> getList() {
            return list;
        }

        public void setList(List<MemberNewBean.DataBean.LevelDetail7Bean.ListBeanXXX> list) {
            this.list = list;
        }
    }

}
