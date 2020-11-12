package com.example.administrator.jipinshop.fragment.home

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.SPUtils
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.home.MainActivity
import com.example.administrator.jipinshop.activity.login.LoginActivity
import com.example.administrator.jipinshop.activity.message.MessageActivity
import com.example.administrator.jipinshop.activity.sign.SignActivity
import com.example.administrator.jipinshop.activity.sreach.TBSreachActivity
import com.example.administrator.jipinshop.adapter.HomeFragmentAdapter
import com.example.administrator.jipinshop.adapter.KTTabAdapter
import com.example.administrator.jipinshop.base.DBBaseFragment
import com.example.administrator.jipinshop.bean.JDBean
import com.example.administrator.jipinshop.bean.SucBeanT
import com.example.administrator.jipinshop.bean.TbkIndexBean
import com.example.administrator.jipinshop.bean.eventbus.EditNameBus
import com.example.administrator.jipinshop.databinding.FragmentKtHomeBinding
import com.example.administrator.jipinshop.fragment.home.commen.KTHomeCommenFragment
import com.example.administrator.jipinshop.fragment.home.main.KTMain2Fragment
import com.example.administrator.jipinshop.fragment.home.userlike.KTUserLikeFragment
import com.example.administrator.jipinshop.fragment.mine.KTMineFragment
import com.example.administrator.jipinshop.util.ShopJumpUtil
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.util.UmApp.AppStatisticalUtil
import com.example.administrator.jipinshop.util.WeakRefHandler
import com.example.administrator.jipinshop.util.sp.CommonDate
import com.example.administrator.jipinshop.view.glide.GlideApp
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import q.rorbin.badgeview.QBadgeView
import java.math.BigDecimal
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
    private lateinit var mTabAdapter: KTTabAdapter
    private var isChange: Boolean = true //是否开启颜色改变
    private var mColor : String = "#E25838"  //轮播图此时滑动的颜色
    private var isAction: Boolean = false //是否开启首页悬浮按钮，默认不开启false
    private var ad : TbkIndexBean.DataBean.Ad1ListBean? = null
    private lateinit var mQBadgeView : QBadgeView

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
        EventBus.getDefault().register(this)
        mBaseFragmentComponent.inject(this)
        mPresenter.setStatusBarHight(mBinding.statusBar,context!!)
        mPresenter.setView(this)
        mBinding.bgHome.setBackgroundColor(Color.parseColor("#E25838"))//默认主题颜色
        mQBadgeView = QBadgeView(context)
        mPresenter.initBadgeView(mQBadgeView, mBinding.homeServer)

        mAdapter = HomeFragmentAdapter(childFragmentManager)
        mList = mutableListOf()
        mTitle = mutableListOf()
        mAdapter.setFragments(mList)
        mBinding.viewPager.adapter = mAdapter
        mBinding.viewPager.addOnPageChangeListener(this)

        var commonNavigator = CommonNavigator(context)
        commonNavigator.leftPadding = context!!.resources.getDimension(R.dimen.x20).toInt()
        commonNavigator.rightPadding = context!!.resources.getDimension(R.dimen.x20).toInt()
        mTabAdapter = KTTabAdapter(mTitle,mBinding.viewPager)
        commonNavigator.adapter = mTabAdapter
        mBinding.tabLayout.navigator = commonNavigator
        ViewPagerHelper.bind(mBinding.tabLayout, mBinding.viewPager)

        context?.let {
            var set = it.resources.getDimension(R.dimen.x74)
            toLeft =   ObjectAnimator.ofFloat(mBinding.homeAction, "translationX",set , 0f)
            toRight =   ObjectAnimator.ofFloat(mBinding.homeAction, "translationX",0f , set)
        }

        mPresenter.getIndexActivityInfo(this.bindToLifecycle())
        mPresenter.getData(this.bindToLifecycle())
        appStatisticalUtil.addEvent("shouye_fenlei.1",this.bindToLifecycle())//统计精选
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.home_sreach -> {
                appStatisticalUtil.addEvent("shouye_sousuo",this.bindToLifecycle())//统计搜索
                startActivity(Intent(context, TBSreachActivity::class.java))
            }
            R.id.home_action -> {
                if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                    startActivity(Intent(context, LoginActivity::class.java))
                    return
                }
                if (!isAction){
                    ToastUtil.show("活动未开始")
                    return
                }
                ShopJumpUtil.openBanner(context,ad?.type,
                        ad?.objectId,ad?.name, ad?.source, ad?.remark)
            }
            R.id.home_sign -> {
                if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                    startActivity(Intent(context, LoginActivity::class.java))
                    return
                }
                startActivity(Intent(context, SignActivity::class.java))
            }
            R.id.home_server -> {
                if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                    startActivity(Intent(context, LoginActivity::class.java))
                    return
                }
                startActivity(Intent(context, MessageActivity::class.java))
            }
            R.id.home_marqueeClose -> {
                mBinding.homeMarqueeContainer.visibility = View.GONE
                mPresenter.closeIndexMessage(this.bindToLifecycle())
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

    fun initMarquee(content : String){
        if (TextUtils.isEmpty(content)){
            mBinding.homeMarqueeContainer.visibility = View.GONE
        }else{
            mBinding.homeMarqueeContainer.visibility = View.VISIBLE
            mBinding.homeMarquee.text = content
        }
    }

    fun initMemberNotice(isShow : Boolean){
        (activity as MainActivity).memberNotice(isShow)
    }

    override fun onPageScrollStateChanged(p0: Int) {}

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}

    override fun onPageSelected(position: Int) {
        appStatisticalUtil.addEvent("shouye_fenlei." + (position + 1),this.bindToLifecycle())//统计首页分类
        if (position != 0){
            if (isAction)
                mBinding.homeAction.visibility = View.GONE
            isChange = false
            context?.let {
                mBinding.bgHome.setBackgroundColor(it.resources.getColor(R.color.color_E25838))
            }
        }else{
            if (isAction)
                mBinding.homeAction.visibility = View.VISIBLE
            isChange = true
            mBinding.bgHome.setBackgroundColor(Color.parseColor(mColor))
        }
    }

    override fun onFile(error: String?) {
        mTitle.add("精选")
        mTitle.add("猜你喜欢")
        mList.add(KTMain2Fragment.getInstance())
        mList.add(KTUserLikeFragment.getInstance())
        mAdapter.notifyDataSetChanged()
        mBinding.viewPager.offscreenPageLimit = mList.size - 1
        mTabAdapter.notifyDataSetChanged()
        ToastUtil.show(error)
    }

    override fun onSuccess(bean: JDBean) {
        for (i in bean.data.indices){
            mTitle.add(bean.data[i].categoryName)
            when (i) {
                0 -> mList.add(KTMain2Fragment.getInstance())
                1 -> mList.add(KTUserLikeFragment.getInstance())
                else -> mList.add(KTHomeCommenFragment.getInstance(bean.data[i].categoryId, "shouye_fenlei." + (i+1)))
            }
        }
        mAdapter.notifyDataSetChanged()
        mBinding.viewPager.offscreenPageLimit = mList.size - 1
        mTabAdapter.notifyDataSetChanged()
    }

    override fun onAction(bean: SucBeanT<TbkIndexBean.DataBean.Ad1ListBean>?) {
        isAction = bean?.data != null
        if (isAction){
            mBinding.homeAction.visibility = View.VISIBLE
            GlideApp.loderImage(context,bean?.data?.img,mBinding.homeAction,0,0)
            ad = bean?.data
        }else{
            mBinding.homeAction.visibility = View.GONE
        }
    }

    override fun onEndAction() {
        isAction = false
        mBinding.homeAction.visibility = View.GONE
    }

    //获取未读消息成功回调
    @Subscribe
    fun unMessageSuc(bus: EditNameBus) {
        if (bus.tag == KTMineFragment.MsgRefersh) {
            var msg = BigDecimal(bus.count).toInt()
            if (msg <= 99) {
                if (msg > 0){
                    mQBadgeView.badgeText = "" + msg
                }else {
                    mQBadgeView.hide(false)
                }
            } else {
                mQBadgeView.badgeText = "99+"
            }
        }
    }

    /*******首页悬浮框动画隐藏效果********/
    private var toRight : ObjectAnimator? = null
    private var toLeft : ObjectAnimator? = null
    private var RightOnce  = true //右边动画只走一次
    private var LeftOnce  = true
    private var isRight = false  //标志只走一次  true:在右边  false ：回原位了
    private var mCallback = Handler.Callback { it ->
        if (it.what == 100) {
            toRight?.let { rightAnimator ->
                rightAnimator.start()
                rightAnimator.addListener(object : Animator.AnimatorListener{
                    override fun onAnimationRepeat(animation: Animator?) {}

                    override fun onAnimationCancel(animation: Animator?) {}

                    override fun onAnimationStart(animation: Animator?) {}

                    override fun onAnimationEnd(animation: Animator?) {}
                })
            }
        } else if (it.what == 110){
            toLeft?.let { leftAnimator ->
                leftAnimator.start()
                leftAnimator.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {}

                    override fun onAnimationCancel(animation: Animator?) {}

                    override fun onAnimationStart(animation: Animator?) {}

                    override fun onAnimationEnd(animation: Animator?) {
                        isRight = false
                    }
                })
            }
        }
        true
    }
    private var mHandler = WeakRefHandler(mCallback, Looper.getMainLooper())

    fun toRight(){
        mHandler.removeCallbacksAndMessages(null)
        if (!isRight){
            if (RightOnce){
                LeftOnce = true
                RightOnce = false
                isRight = true // 此时在右边的
                mHandler.sendEmptyMessage(100)
            }
        }else{
            if (!LeftOnce){
                LeftOnce = true
            }
        }
    }

    fun toLeft(){
        if (isRight){
            if (LeftOnce){
                LeftOnce = false
                RightOnce = true
                mHandler.sendEmptyMessageDelayed(110,2000)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mHandler.removeCallbacksAndMessages(null)
        EventBus.getDefault().unregister(this)
    }
    /**************结束****************/
}