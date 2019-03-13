package com.example.administrator.jipinshop.activity.sign;

import com.example.administrator.jipinshop.bean.DailyTaskBean;
import com.example.administrator.jipinshop.bean.SignBean;
import com.example.administrator.jipinshop.bean.SignInsertBean;

/**
 * @author 莫小婷
 * @create 2018/10/11
 * @Describe
 */
public interface SignView {

    /*查询签到信息*/
    void getInfoSuc(SignBean signBean);
    void getInfoFaile(String error);

    /**签到成功*/
    void signSuc(SignInsertBean signInsertBean);
    void signFaile(String error);

    /*获取每日任务列表**/
    void getDayList(DailyTaskBean bean);
}
