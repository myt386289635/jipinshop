package com.example.administrator.jipinshop.bean.eventbus;

/**
 * @author 莫小婷
 * @create 2019/4/1
 * @Describe 新用户获取极币bus
 */
public class HomeNewPeopleBus {

    private int addPoint;

    public HomeNewPeopleBus(int addPoint) {
        this.addPoint = addPoint;
    }

    public int getAddPoint() {
        return addPoint;
    }

    public void setAddPoint(int addPoint) {
        this.addPoint = addPoint;
    }
}
