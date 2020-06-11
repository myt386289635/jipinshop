package com.example.administrator.jipinshop.activity.balance.team;

import com.example.administrator.jipinshop.bean.TeacherBean;
import com.example.administrator.jipinshop.bean.TeamBean;

/**
 * @author 莫小婷
 * @create 2019/6/11
 * @Describe
 */
public interface TeamView {
    void onSuccess(TeamBean bean);
    void onFile(String error);

    void onTeacher(TeacherBean bean);
}
