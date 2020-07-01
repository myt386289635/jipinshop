package com.example.administrator.jipinshop.fragment.member

import android.app.Dialog
import android.view.inputmethod.InputMethodManager
import com.example.administrator.jipinshop.bean.DailyTaskBean
import com.example.administrator.jipinshop.bean.MemberBean
import com.example.administrator.jipinshop.bean.SignInsertBean
import com.example.administrator.jipinshop.bean.SuccessBean

/**
 * @author 莫小婷
 * @create 2020/4/2
 * @Describe
 */
interface KTMemberView {

    fun onSuccess(bean : MemberBean)
    fun onFile(error:String?)

    fun onApply()

    fun getDayList(bean: DailyTaskBean)

    fun onCodeSuc(dialog: Dialog, inputManager: InputMethodManager, bean: SuccessBean)

    fun signSuc(signInsertBean: SignInsertBean)
}