package com.example.administrator.jipinshop.bean;

public class HealthFragmentGridBean {
    private String name;
    private Boolean tag;
    private String categoryid;

    public HealthFragmentGridBean(String name, Boolean tag, String categoryid) {
        this.name = name;
        this.tag = tag;
        this.categoryid = categoryid;
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
}
