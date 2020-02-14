package com.example.administrator.jipinshop.fragment.money

import android.databinding.DataBindingUtil
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.base.DBBaseFragment
import com.example.administrator.jipinshop.databinding.FragmentKtMoneyBinding
import com.example.administrator.jipinshop.util.ToastUtil
import javax.inject.Inject

/**
 * 赚钱页面  （活动页面）
 */
class MoneyFragment : DBBaseFragment(), View.OnClickListener, OnRefreshListener {

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
        if (isVisibleToUser && once) {
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
        mPresenter.setStatusBarHight(mBinding.statusBar,context!!)
        mBinding.swipeToLoad.setOnRefreshListener(this)
        mBinding.swipeToLoad.setBackgroundColor(Color.WHITE)
        mBinding.swipeTarget.visibility = View.INVISIBLE
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.money_rule ->{
                ToastUtil.show("活动规则")
            }
        }
    }

    override fun onRefresh() {
        if(once){
            mBinding.swipeToLoad.setBackgroundColor(resources.getColor(R.color.color_E04625))
            mBinding.swipeTarget.visibility = View.VISIBLE
        }
        stopResher()
        ToastUtil.show("刷新数据")
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