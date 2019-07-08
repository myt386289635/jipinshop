package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2019/7/3
 * @Describe
 */
public class ClassifyTabBean {
    private TopCategoryDetailBean.DataBean.RelatedItemListBean mString;
    private Boolean tag;

    public ClassifyTabBean(TopCategoryDetailBean.DataBean.RelatedItemListBean string, Boolean tag) {
        mString = string;
        this.tag = tag;
    }

    public TopCategoryDetailBean.DataBean.RelatedItemListBean getString() {
        return mString;
    }

    public void setString(TopCategoryDetailBean.DataBean.RelatedItemListBean string) {
        mString = string;
    }

    public Boolean getTag() {
        return tag;
    }

    public void setTag(Boolean tag) {
        this.tag = tag;
    }
}
