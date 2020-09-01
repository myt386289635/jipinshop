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
