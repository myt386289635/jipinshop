package com.example.administrator.jipinshop.bean;

/**
 * @author 莫小婷
 * @create 2019/6/10
 * @Describe
 */
public class WithdrawBean {

    /**
     * msg : success
     * code : 0
     * withdrawNote : *提现审核时间每月25号
     *提现时请务必正确填写支付宝账号与真实姓名
     *每月提现上月30号之前确认收货的订单佣金
     * minWithdraw : 50
     */

    private String msg;
    private int code;
    private String withdrawNote;
    private String minWithdraw;

    //提现成功时需要的数据
    private String data;
    private String point;
    private String shareContent;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
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

    public String getWithdrawNote() {
        return withdrawNote;
    }

    public void setWithdrawNote(String withdrawNote) {
        this.withdrawNote = withdrawNote;
    }

    public String getMinWithdraw() {
        return minWithdraw;
    }

    public void setMinWithdraw(String minWithdraw) {
        this.minWithdraw = minWithdraw;
    }
}
