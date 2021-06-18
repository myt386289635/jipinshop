package com.example.administrator.jipinshop.fragment.mine

import android.app.Dialog
import android.view.inputmethod.InputMethodManager
import com.example.administrator.jipinshop.bean.*

/**
 * @author 莫小婷
 * @create 2020/6/5
 * @Describe
 */
interface KTMineView {

    fun successUserInfo(userInfoBean: UserInfoBean)
    fun FaileUserInfo(error: Int , message : String?)

    fun onSuccess(bean: SimilerGoodsBean)
    fun onFile(error: String?)

    fun onAdList(adListBeanSucBean: SucBean<EvaluationTabBean.DataBean.AdListBean>)
    fun onFileCommen(error: String?)

    fun unMessageSuc(unMessageBean: UnMessageBean)

    fun onCodeSuc(dialog: Dialog, inputManager: InputMethodManager, bean: SuccessBean)
    fun onCodeFile(error: String?)

    fun onCommssionSummary(bean: MyWalletBean)

    fun initShare(bean: ShareInfoBean)
}