package com.example.administrator.jipinshop.fragment.home

import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.sreach.TBSreachActivity
import com.example.administrator.jipinshop.adapter.HomeFragmentAdapter
import com.example.administrator.jipinshop.adapter.KTTabAdaper
import com.example.administrator.jipinshop.base.DBBaseFragment
import com.example.administrator.jipinshop.bean.EvaluationTabBean
import com.example.administrator.jipinshop.databinding.FragmentKtHomeBinding
import com.example.administrator.jipinshop.fragment.home.commen.KTHomeCommenFragment
import com.example.administrator.jipinshop.fragment.home.main.KTMainFragment
import com.example.administrator.jipinshop.fragment.home.userlike.KTUserLikeFragment
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.util.UmApp.AppStatisticalUtil
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/12/11
 * @Describe
 */
class KTHomeFragnent : DBBaseFragment(), View.OnClickListener, ViewPager.OnPageChangeListener, KTHomeView {

    @Inject
    lateinit var mPresenter: KTHomePresenter
    @Inject
    lateinit var appStatisticalUtil: AppStatisticalUtil

    private lateinit var mBinding : FragmentKtHomeBinding
    private lateinit var mAdapter : HomeFragmentAdapter
    private lateinit var mList: MutableList<Fragment>
    private lateinit var mTitle: MutableList<String>
    private lateinit var mTabAdapter: KTTabAdaper
    private var isChange: Boolean = true //是否开启颜色改变
    private var mColor : String = "#E25838"  //轮播图此时滑动的颜色

    companion object{
        @JvmStatic //java中的静态方法
        fun getInstance() : KTHomeFragnent {
            return KTHomeFragnent()
        }
    }

    override fun initLayout(inflater: LayoutInflater?, container: ViewGroup?): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_kt_home, container, false)
        mBinding.listener = this
        return mBinding.root
    }

    override fun initView() {
        mBaseFragmentComponent.inject(this)
        mPresenter.setStatusBarHight(mBinding.statusBar,context!!)
        mPresenter.setView(this)
        mBinding.bgHome.setBackgroundColor(Color.parseColor("#E25838"))//默认主题颜色

        mAdapter = HomeFragmentAdapter(childFragmentManager)
        mList = mutableListOf()
        mTitle = mutableListOf()
        mAdapter.setFragments(mList)
        mBinding.viewPager.adapter = mAdapter
        mBinding.viewPager.addOnPageChangeListener(this)

        var commonNavigator = CommonNavigator(context)
        commonNavigator.leftPadding = context!!.resources.getDimension(R.dimen.x20).toInt()
        commonNavigator.rightPadding = context!!.resources.getDimension(R.dimen.x20).toInt()
        mTabAdapter = KTTabAdaper(mTitle,mBinding.viewPager)
        commonNavigator.adapter = mTabAdapter
        mBinding.tabLayout.navigator = commonNavigator
        ViewPagerHelper.bind(mBinding.tabLayout, mBinding.viewPager)

        mPresenter.getData(this.bindToLifecycle())
        appStatisticalUtil.addEvent("shouye_fenlei.1",this.bindToLifecycle())//统计精选
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.home_sreach -> {
                appStatisticalUtil.addEvent("shouye_sousuo",this.bindToLifecycle())//统计搜索
                startActivity(Intent(context, TBSreachActivity::class.java))
            }
        }
    }

    fun initColor(color : String){
        if (isChange){
            mBinding.bgHome.setBackgroundColor(Color.parseColor(color))
        }
        mColor = color
    }

    fun getAppBar() : AppBarLayout{
        return mBinding.appbar
    }

    override fun onPageScrollStateChanged(p0: Int) {}

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}

    override fun onPageSelected(position: Int) {
        appStatisticalUtil.addEvent("shouye_fenlei." + (position + 1),this.bindToLifecycle())//统计首页分类
        if (position != 0){
            isChange = false
            context?.let {
                mBinding.bgHome.setBackgroundColor(it.resources.getColor(R.color.color_E25838))
            }
        }else{
            isChange = true
            mBinding.bgHome.setBackgroundColor(Color.parseColor(mColor))
        }
    }

    override fun onFile(error: String?) {
        mTitle.add("精选")
        mTitle.add("猜你喜欢")
        mList.add(KTMainFragment.getInstance())
        mList.add(KTUserLikeFragment.getInstance())
        mAdapter.notifyDataSetChanged()
        mBinding.viewPager.offscreenPageLimit = mList.size - 1
        mTabAdapter.notifyDataSetChanged()
        ToastUtil.show(error)
    }

    override fun onSuccess(bean: EvaluationTabBean) {
        for (i in bean.data.indices){
            mTitle.add(bean.data[i].categoryName)
            when (i) {
                0 -> mList.add(KTMainFragment.getInstance())
                1 -> mList.add(KTUserLikeFragment.getInstance())
                else -> mList.add(KTHomeCommenFragment.getInstance(bean.data[i].categoryId, "shouye_fenlei." + (i+1)))
            }
        }
        mAdapter.notifyDataSetChanged()
        mBinding.viewPager.offscreenPageLimit = mList.size - 1
        mTabAdapter.notifyDataSetChanged()
    }
}