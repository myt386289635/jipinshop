package com.example.administrator.jipinshop.bean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/10/11
 * @Describe
 */
public class SignBean {

    /**
     * msg : success
     * code : 200
     * weekArray : ["2","2","1","0","0","0","0"]
     * imgList : ["http://pi6611u5d.bkt.clouddn.com/upload/20181121/c6f7fc55df774c4e8119ad17400980a5.png","http://pi6611u5d.bkt.clouddn.com/upload/20181121/6b15d200ed44432281a9e9c53bf9d118.png","http://pi6611u5d.bkt.clouddn.com/upload/20181121/26c7dc87db8543f899f895b6fb509828.png","http://pi6611u5d.bkt.clouddn.com/upload/20181121/300df951b18140858b22665c76ddca85.png","http://pi6611u5d.bkt.clouddn.com/upload/20181121/a595e9d0cd0e4a8d93ad7ad85438e105.png","http://pi6611u5d.bkt.clouddn.com/upload/20181121/9c53d3ca1ba64bcfa1fd96e0d8e183fc.png","http://pi6611u5d.bkt.clouddn.com/upload/20181121/9c45b88daada4c14a2d0ce6645a54723.png"]
     */

    private String msg;
    private int code;
    private List<String> weekArray;
    private List<String> imgList;

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

    public List<String> getWeekArray() {
        return weekArray;
    }

    public void setWeekArray(List<String> weekArray) {
        this.weekArray = weekArray;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }
}
