package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/4/26
 * @Describe
 */
public class JDBean {

    private String msg;
    private int code;
    private List<EvaluationTabBean.DataBean> data;
    private List<TbCommonBean.AdListBean> adList;

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

    public List<EvaluationTabBean.DataBean> getData() {
        return data;
    }

    public void setData(List<EvaluationTabBean.DataBean> data) {
        this.data = data;
    }

    public List<TbCommonBean.AdListBean> getAdList() {
        return adList;
    }

    public void setAdList(List<TbCommonBean.AdListBean> adList) {
        this.adList = adList;
    }
}
