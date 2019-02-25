package com.example.administrator.jipinshop.bean;

public class ChildrenTabBean {
    private String name;
    private Boolean tag;
    private String categoryid;
    private String img;

    public ChildrenTabBean(String name, Boolean tag, String categoryid,String img) {
        this.name = name;
        this.tag = tag;
        this.categoryid = categoryid;
        this.img = img;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getTag() {
        return tag;
    }

    public void setTag(Boolean tag) {
        this.tag = tag;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
