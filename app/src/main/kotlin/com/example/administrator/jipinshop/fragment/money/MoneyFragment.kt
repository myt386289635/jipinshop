package com.example.administrator.jipinshop.fragment.money

import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.money.withdraw.MoneyWithdrawActivity
import com.example.administrator.jipinshop.base.DBBaseFragment
import com.example.administrator.jipinshop.databinding.FragmentKtMoneyBinding
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.view.dialog.DialogUtil
import javax.inject.Inject

/**
 * 赚钱页面  （活动页面）
 */
class MoneyFragment : DBBaseFragment(), View.OnClickListener, OnRefreshListener, MoneyView {

    @Inject
    lateinit var mPresenter: MoneyPresenter

    private lateinit var mBinding: FragmentKtMoneyBinding
    private var once: Boolean = true

    companion object{
        @JvmStatic //java中的静态方法
        fun getInstance() : MoneyFragment {
            return MoneyFragment()
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            mBinding.swipeToLoad.isRefreshing = true
        }
    }

    override fun initLayout(inflater: LayoutInflater?, container: ViewGroup?): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_kt_money, container, false)
        mBinding.listener = this
        return mBinding.root
    }

    override fun initView() {
        mBaseFragmentComponent.inject(this)
        mPresenter.setView(this)
        mPresenter.setStatusBarHight(mBinding.statusBar,context!!)
        mBinding.swipeToLoad.setOnRefreshListener(this)
        mBinding.swipeToLoad.setBackgroundColor(Color.WHITE)
        mBinding.swipeTarget.visibility = View.INVISIBLE
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.money_rule ->{
                ToastUtil.show("规则说明")
            }
            R.id.money_reCopy ->{
                ToastUtil.show("点击复制")
            }
            R.id.money_reInvitation -> {
                ToastUtil.show("立即邀请")
            }
            R.id.money_balanceWithdraw -> {
                startActivity(Intent(context,MoneyWithdrawActivity::class.java))
            }
            R.id.money_background1 -> {
                ToastUtil.show("领取第1个红包")
            }
            R.id.money_background2 -> {
                ToastUtil.show("领取第2个红包")
            }
            R.id.money_background3 -> {
                ToastUtil.show("领取第3个红包")
            }
            R.id.money_background4 -> {
                ToastUtil.show("领取第4个红包")
            }
            R.id.money_background5 -> {
                ToastUtil.show("领取第5个红包")
            }
            R.id.money_background6 -> {
                ToastUtil.show("领取第6个红包")
            }
            R.id.money_background7 -> {
                ToastUtil.show("领取第7个红包")
            }
            R.id.money_background8 -> {
                ToastUtil.show("领取第8个红包")
            }
            R.id.money_background9 -> {
                ToastUtil.show("领取第9个红包")
            }
            R.id.money_friends -> {
                ToastUtil.show("跳转到我的团队")
            }
        }
    }

    override fun onRefresh() {
        if(once){
            mBinding.swipeToLoad.setBackgroundColor(resources.getColor(R.color.color_E04625))
            mBinding.swipeTarget.visibility = View.VISIBLE
        }
        mPresenter.setDate(this.bindToLifecycle())
    }

    override fun onSuccess() {
        stopResher()
        DialogUtil.bedDialog(context,"10") {
            ToastUtil.show("领取成功")
        }
        once = false
    }

    override fun onFile(error: String?) {
        stopResher()
        ToastUtil.show(error)
        once = false
    }


    fun stopResher() {
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isRefreshing) {
            if (!mBinding.swipeToLoad.isRefreshEnabled) {
                mBinding.swipeToLoad.isRefreshEnabled = true
                mBinding.swipeToLoad.isRefreshing = false
                mBinding.swipeToLoad.isRefreshEnabled = false
            } else {
                mBinding.swipeToLoad.isRefreshing = false
            }
        }
    }
}