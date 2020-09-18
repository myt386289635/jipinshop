package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2018/10/17
 * @Describe
 */
public class ImageBean {

    /**
     * msg : success
     * code : 0
     * data : http://jipincheng.cn/910ffa7702984517b88e4cc3d48cf66f
     */

    private String msg;
    private int code;
    private String data;
    private String otherGoodsId;
    private String endTime;//支付后使用

    //分享需要
    private String desc;
    private String img;
    private String url;
    private String title;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getOtherGoodsId() {
        return otherGoodsId;
    }

    public void setOtherGoodsId(String otherGoodsId) {
        this.otherGoodsId = otherGoodsId;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
