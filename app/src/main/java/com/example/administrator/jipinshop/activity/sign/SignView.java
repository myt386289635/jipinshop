package com.example.administrator.jipinshop.activity.sign;

import com.example.administrator.jipinshop.bean.LuckImageBean;
import com.example.administrator.jipinshop.bean.LuckselectBean;
import com.example.administrator.jipinshop.bean.SignBean;
import com.example.administrator.jipinshop.bean.SignInsertBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.SupplementBean;

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

    /**补签成功**/
    void SuppleSuc(SupplementBean supplementBean);
    void SuppleFaile(String error);

    /**抽奖成功**/
    void LuckSuc(LuckselectBean luckselectBean);
    void LuckFaile(String error);

    /**获取抽奖图片***/
    void LuckImageSuc(LuckImageBean luckImageBean);
    void LuckImageFaile(String error);
}
