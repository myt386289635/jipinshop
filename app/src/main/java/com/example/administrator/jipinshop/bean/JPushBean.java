package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2019/9/5
 * @Describe
 */
public class JPushBean {


    /**
     * targetId : 123456789
     * targetTitle : 测试
     * targetType : 1
     */

    private String targetId;
    private String targetTitle;
    private String targetType;
    private String source;
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getTargetTitle() {
        return targetTitle;
    }

    public void setTargetTitle(String targetTitle) {
        this.targetTitle = targetTitle;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }
}
