package com.example.administrator.jipinshop.activity.minekt

import android.app.Dialog
import android.content.Intent
import android.content.res.ColorStateList
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import android.text.Html
import android.text.TextUtils
import android.view.MotionEvent
import android.view.View
import com.blankj.utilcode.util.SPUtils
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.follow.FollowActivity
import com.example.administrator.jipinshop.activity.info.MyInfoActivity
import com.example.administrator.jipinshop.adapter.HomeFragmentAdapter
import com.example.administrator.jipinshop.base.BaseActivity
import com.example.administrator.jipinshop.bean.UserInfoBean
import com.example.administrator.jipinshop.databinding.ActivityUserBinding
import com.example.administrator.jipinshop.fragment.foval.article.FovalArticleFragment
import com.example.administrator.jipinshop.fragment.foval.find.FovalFindFragment
import com.example.administrator.jipinshop.fragment.foval.goods.FovalGoodsFragment
import com.example.administrator.jipinshop.fragment.userkt.article.UserArticleFragment
import com.example.administrator.jipinshop.fragment.userkt.find.UserFindFragment
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.util.sp.CommonDate
import com.example.administrator.jipinshop.view.dialog.DialogUtil
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView
import com.example.administrator.jipinshop.view.glide.GlideApp
import java.math.BigDecimal
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/8/16
 * @Describe 个人主页
 */
class UserActivity : BaseActivity(), View.OnClickListener, UserView {

    @Inject
    lateinit var mPresenter: UserPresenter

    private lateinit var mBinding: ActivityUserBinding
    private var mList: MutableList<Fragment> = mutableListOf()
    private lateinit var mAdapter : HomeFragmentAdapter
    private lateinit var mDialog: Dialog
    private var userid = ""
    private var startX = 0f
    private var startY = 0f
    private var xDistance = 0f
    private var yDistance = 0f
    private var follow = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_user)
        mBinding.listener = this
        mBaseActivityComponent.inject(this)
        mImmersionBar.reset()
                .transparentStatusBar()
                .statusBarDarkFont(true, 0f)
                .init()
        mPresenter.setView(this)
        initView()
    }

    private fun initView() {
        mDialog = ProgressDialogView().createLoadingDialog(this, "")
        mDialog.show()
        userid = intent.getStringExtra("userid")
        mPresenter.setStatusBarHight(mBinding.userTitleContainer,mBinding.statusBar,this)
        mPresenter.setTitleBlack(mBinding.appbar,mBinding.titleBack,mBinding.titleTv,mBinding.statusBar)
        mBinding.tabLayout.tabRippleColor = ColorStateList.valueOf(resources.getColor(R.color.transparent))
        mBinding.tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.color_E25838))

        mAdapter = HomeFragmentAdapter(supportFragmentManager)
        mList.add(UserFindFragment.getInstance(userid,"4"))//评测
        mList.add(UserFindFragment.getInstance(userid,"2"))//清单
        mList.add(UserArticleFragment.getInstance(userid))
        mAdapter.setFragments(mList)
        mBinding.viewPager.adapter = mAdapter
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager)
        mBinding.viewPager.offscreenPageLimit = mList.size - 1

        mPresenter.initTabLayout(mBinding.tabLayout,this,mList)
        mPresenter.getDate(userid,this.bindToLifecycle())
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.title_back -> {
                finish()
            }
            R.id.user_attent -> {
                //关注按钮
                if (follow == 0){
                    //未关注
                    mPresenter.concernInsert(userid,this.bindToLifecycle())
                }else{
                    //已关注
                    mPresenter.concernDelete(userid,this.bindToLifecycle())
                }
            }
            R.id.user_edit -> {
                //跳转个人资料页面
                startActivity(Intent(this, MyInfoActivity::class.java))
            }
            R.id.user_attentNum -> {
                //关注
                if (SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId,"") == userid){
                    startActivity(Intent(this, FollowActivity::class.java)
                            .putExtra("page", 0)
                    )
                }
            }
            R.id.user_fansNum -> {
                //粉丝
                if (SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId,"") == userid){
                    startActivity(Intent(this, FollowActivity::class.java)
                            .putExtra("page", 1)
                    )
                }
            }
            R.id.user_goodNum -> {
                //获赞
                if (SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId,"") == userid){
                    DialogUtil.MyGoods(this, mBinding.userName.text.toString(), mBinding.userGoodText.text.toString())
                }
            }
        }
    }

    override fun onSuccess(bean: UserInfoBean) {
        if (mDialog.isShowing){
            mDialog.dismiss()
        }
        if (TextUtils.isEmpty(bean.data.bgImg)){
            mBinding.userBackground.setImageResource(R.mipmap.mine_imagebg_dafult)
        }else{
            GlideApp.loderImage(this,bean.data.bgImg,mBinding.userBackground,R.mipmap.mine_imagebg_dafult,0)
        }
        GlideApp.loderCircleImage(this,bean.data.avatar,mBinding.userImage,R.mipmap.rlogo,0)
        mBinding.userName.text = bean.data.nickname
        mBinding.userAge.text = bean.data.age.toString()
        when(bean.data.gender){
            "男" -> mBinding.userSex.setImageResource(R.mipmap.user_nan)
            "女" -> mBinding.userSex.setImageResource(R.mipmap.user_nv)
        }
        var html = "<font color='#9D9D9D'><b>个签：</b></font>" + bean.data.detail
        mBinding.userSign.text = Html.fromHtml(html)
        if (SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId,"") == userid){
            mBinding.userEdit.visibility = View.VISIBLE
            mBinding.userAttent.visibility = View.GONE
        }else{
            mBinding.userEdit.visibility = View.GONE
            mBinding.userAttent.visibility = View.VISIBLE
            follow = bean.data.follow
            if(bean.data.follow == 0){
                //未关注
                mBinding.userAttent.text = "+关注"
                mBinding.userAttent.setBackgroundResource(R.drawable.bg_tab_eva)
            }else{
                mBinding.userAttent.text = "已关注"
                mBinding.userAttent.setBackgroundResource(R.drawable.bg_tab_eva4)
            }
            mBinding.userAttent.setPadding(resources.getDimension(R.dimen.x48).toInt(), resources.getDimension(R.dimen.y16).toInt(),
                    resources.getDimension(R.dimen.x48).toInt(), resources.getDimension(R.dimen.y16).toInt())
        }
        mBinding.userAttentText.text = bean.data.followCount
        mBinding.userFansText.text = bean.data.fansCount
        mBinding.userGoodText.text = bean.data.voteCount
    }

    override fun onFile(error: String?) {
        if (mDialog.isShowing){
            mDialog.dismiss()
        }
        ToastUtil.show(error)
    }

    /**
     * 解决AppBarLayout头布局过大与ViewPager手势冲突出现的滑动卡顿问题
     */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                yDistance = 0f
                xDistance = yDistance
                startX = ev.x
                startY = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
            }
        }
        val curX = ev.x
        val curY = ev.y
        xDistance += Math.abs(curX - startX)
        yDistance += Math.abs(curY - startY)
        if (xDistance >= yDistance) {
            //横向滑动
            mBinding.viewPager.setNoScroll(false)
        } else {
            //垂直滑动
            mBinding.viewPager.setNoScroll(true)
        }
        return super.dispatchTouchEvent(ev)
    }

    fun getBar(): AppBarLayout {
        return mBinding.appbar
    }

    override fun onAttent() {
        follow = 1
        mBinding.userAttent.text = "已关注"
        mBinding.userAttent.setBackgroundResource(R.drawable.bg_tab_eva4)
        mBinding.userAttent.setPadding(resources.getDimension(R.dimen.x48).toInt(), resources.getDimension(R.dimen.y16).toInt(),
                resources.getDimension(R.dimen.x48).toInt(), resources.getDimension(R.dimen.y16).toInt())
        var fans = BigDecimal(mBinding.userFansText.text.toString())
        mBinding.userFansText.text = (fans.toInt() + 1).toString()
    }

    override fun onCancleAttent() {
        follow = 0
        mBinding.userAttent.text = "+关注"
        mBinding.userAttent.setBackgroundResource(R.drawable.bg_tab_eva)
        mBinding.userAttent.setPadding(resources.getDimension(R.dimen.x48).toInt(), resources.getDimension(R.dimen.y16).toInt(),
                resources.getDimension(R.dimen.x48).toInt(), resources.getDimension(R.dimen.y16).toInt())
        var fans = BigDecimal(mBinding.userFansText.text.toString())
        mBinding.userFansText.text = (fans.toInt() - 1).toString()
    }

}