package com.example.administrator.jipinshop.fragment.member

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.blankj.utilcode.util.SPUtils
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.WebActivity
import com.example.administrator.jipinshop.activity.home.home.HomeNewActivity
import com.example.administrator.jipinshop.activity.info.MyInfoActivity
import com.example.administrator.jipinshop.activity.mall.MallActivity
import com.example.administrator.jipinshop.activity.newpeople.NewFreeActivity
import com.example.administrator.jipinshop.activity.setting.bind.BindWXActivity
import com.example.administrator.jipinshop.activity.sign.invitation.InvitationNewActivity
import com.example.administrator.jipinshop.activity.sreach.TBSreachActivity
import com.example.administrator.jipinshop.adapter.KTSignAdapter
import com.example.administrator.jipinshop.base.DBBaseFragment
import com.example.administrator.jipinshop.bean.*
import com.example.administrator.jipinshop.bean.eventbus.ChangeHomePageBus
import com.example.administrator.jipinshop.databinding.FragmentMemberBinding
import com.example.administrator.jipinshop.netwrok.RetrofitModule
import com.example.administrator.jipinshop.util.ShopJumpUtil
import com.example.administrator.jipinshop.util.TaoBaoUtil
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.util.sp.CommonDate
import com.example.administrator.jipinshop.view.dialog.DialogUtil
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView
import com.example.administrator.jipinshop.view.glide.GlideApp
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2020/4/1
 * @Describe 会员页面
 */
class KTMemberFragment : DBBaseFragment(), View.OnClickListener, OnRefreshListener, KTMemberView, KTSignAdapter.OnClickJump {

    @Inject
    lateinit var mPresenter: KTMemberPresenter

    private lateinit var mBinding: FragmentMemberBinding
    private var once: Boolean = true
    private var level: Int = 0
    private var monthLevelPoint: Int = 0//会员设计临时月度积分
    private var monthLevelInvitedUserCount: Int = 0
    private var levelInvitedUserCount: Int = 0
    private var levelPoint: Int = 0
    private var invitedUserCount: Int = 0 //我邀请的人数
    private var point: Int = 0 //我目前的极币数
    //广告
    private lateinit var mAdList: MutableList<MemberBean.DataBean.MessageListBean>
    private var mDialog: Dialog? = null
    //每日任务
    private lateinit var mDayRule: MutableList<DailyTaskBean.DataBean>
    private lateinit var mDayAdapter : KTSignAdapter
    //完善用户信息
    private lateinit var mUserRule: MutableList<DailyTaskBean.DataBean>
    private lateinit var mUserAdapter: KTSignAdapter

    companion object{
        @JvmStatic
        fun getInstance(type: String): KTMemberFragment {
            var fragment = KTMemberFragment()
            var bundle = Bundle()
            bundle.putString("type",type)//1:fragment 2:activity
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && once) {
            mBinding.swipeToLoad.isRefreshing = true
            once = false
        }
    }

    override fun initLayout(inflater: LayoutInflater?, container: ViewGroup?): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_member,container,false)
        mBinding.listener = this
        return mBinding.root
    }

    override fun initView() {
        mBaseFragmentComponent.inject(this)
        mPresenter.setView(this)
        mPresenter.setStatusBarHight(mBinding.statusBar,context!!)
        mBinding.swipeToLoad.setOnRefreshListener(this)

        //每日任务
        mDayRule = mutableListOf()
        mBinding.memberDayRules.layoutManager = LinearLayoutManager(context!!)
        mDayAdapter = KTSignAdapter(mDayRule,context!!)
        mDayAdapter.setOnClickJump(this)
        mDayAdapter.setType(1)
        mBinding.memberDayRules.adapter = mDayAdapter
        mBinding.memberDayRules.isNestedScrollingEnabled = false

        //完成用户信息任务
        mUserRule = mutableListOf()
        mBinding.memberUserRules.layoutManager = LinearLayoutManager(context!!)
        mUserAdapter = KTSignAdapter(mUserRule,context!!)
        mUserAdapter.setOnClickJump(this)
        mUserAdapter.setType(2)
        mBinding.memberUserRules.adapter = mUserAdapter
        mBinding.memberUserRules.isNestedScrollingEnabled = false

        //广告
        mAdList = mutableListOf()
        arguments?.let {
            if (it.getString("type","2") == "2"){
                mBinding.swipeToLoad.post {
                    mBinding.swipeToLoad.isRefreshing = true
                    once = false
                }
                mBinding.memberBlack.visibility = View.VISIBLE
            }
        }
    }

    override fun onRefresh() {
        mPresenter.memberIndex(this.bindToLifecycle())
        mPresenter.DailytaskList(this.bindToLifecycle())
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.member_share -> {
                startActivity(Intent(context, WebActivity::class.java)
                        .putExtra(WebActivity.url, RetrofitModule.H5_URL + "new-free/memberrule")
                        .putExtra(WebActivity.title, "规则说明")
                )
            }
            R.id.member_adContainer,
            R.id.member_infoUserInvation,R.id.member_infoUserInvationCopy -> {
                //进入邀请好友
                startActivity(Intent(context, InvitationNewActivity::class.java))
            }
            R.id.member_newBuy -> {
                startActivity(Intent(context, NewFreeActivity::class.java))
            }
            R.id.member_newH5 -> {
                startActivity(Intent(context, WebActivity::class.java)
                        .putExtra(WebActivity.url, RetrofitModule.H5_URL + "newZn.html")
                        .putExtra(WebActivity.title, "极品城省钱攻略")
                        .putExtra(WebActivity.isShare,true)
                        .putExtra(WebActivity.shareTitle,"如何查找淘宝隐藏优惠券及下单返利？")
                        .putExtra(WebActivity.shareContent,"淘宝天猫90%的商品都能省，同时还有高额返利，淘好物，更省钱！")
                        .putExtra(WebActivity.shareImage,"https://jipincheng.cn/shengqian.png")
                )
            }
            R.id.member_infoCoinInvation, R.id.member_infoCoinInvationCopy -> {
                //去赚极币
                mBinding.swipeTarget.scrollTo(0,  mBinding.memberTitle2.top)
            }
            R.id.member_coinExchange -> {
                //极币商城
                startActivity(Intent(context, MallActivity::class.java))
            }
            R.id.member_copy -> {
                var clip = context!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                var clipData = ClipData.newPlainText("jipinshop", mBinding.memberWxCode.text.toString().replace(resources.getString(R.string.member_wx),""))
                clip.primaryClip = clipData
                SPUtils.getInstance().put(CommonDate.CLIP,  mBinding.memberWxCode.text.toString().replace(resources.getString(R.string.member_wx),""))
                ToastUtil.show("复制成功")
            }
            R.id.member_black -> {
                activity?.let {
                    it.finish()
                }
            }
            R.id.member_up30 -> {
                if (invitedUserCount < monthLevelInvitedUserCount){
                    ToastUtil.show("不符合升级条件，请继续努力")
                    return
                }
                if (point < monthLevelPoint){
                    ToastUtil.show("不符合升级条件，请继续努力")
                    return
                }
                var content = "需消耗"+ monthLevelInvitedUserCount +"名直邀用户，"+ monthLevelPoint +"极币\n升级消耗的邀请用户数量不影\n响您的分佣"
                var title = "申请升级30天VIP"
                DialogUtil.listingDetele(context,title,content,"立即升级","取消",
                        R.color.color_202020,R.color.color_4A90E2,R.color.color_9D9D9D,R.color.color_565252,true,false){
                    mDialog = ProgressDialogView().createLoadingDialog(context, "")
                    mDialog?.show()
                    mPresenter.memberUpdate("1",this.bindToLifecycle())
                }
            }
            R.id.member_apply -> {
                if (invitedUserCount < levelInvitedUserCount){
                    ToastUtil.show("不符合升级条件，请继续努力")
                    return
                }
                if (point < levelPoint){
                    ToastUtil.show("不符合升级条件，请继续努力")
                    return
                }
                var type = "1"
                var title = ""
                when (level){
                    0 -> { //普通人员
                        type = "2"
                        title = "申请升级永久VIP"
                    }
                    1 -> {//vip
                        type = "3"
                        title = "申请升级永久合伙人"
                    }
                }
                var content = "需消耗"+ levelInvitedUserCount +"名直邀用户，"+ levelPoint +"极币\n升级消耗的邀请用户数量不影\n响您的分佣"
                DialogUtil.listingDetele(context,title,content,"立即升级","取消",
                        R.color.color_202020,R.color.color_4A90E2,R.color.color_9D9D9D,R.color.color_565252,true,false){
                    mDialog = ProgressDialogView().createLoadingDialog(context, "")
                    mDialog?.show()
                    mPresenter.memberUpdate(type,this.bindToLifecycle())
                }
            }
        }
    }

    override fun onSuccess(bean: MemberBean) {
        monthLevelPoint = bean.data.monthLevelPoint
        monthLevelInvitedUserCount = bean.data.monthLevelInvitedUserCount
        levelInvitedUserCount = bean.data.levelInvitedUserCount
        levelPoint = bean.data.levelPoint
        invitedUserCount = bean.data.invitedUserCount
        point = bean.data.point
        mBinding.swipeToLoad.isRefreshing = false
        mBinding.date = bean.data
        mBinding.executePendingBindings()
        level = bean.data.level
        when (bean.data.level){
            0 -> {
                //普通人员
                mBinding.memberUserLabel.setImageResource(R.mipmap.member_label1)
                mBinding.memberImage.setImageResource(R.mipmap.member_public)
                mBinding.memberApply.text = "升级永久VIP"
                mBinding.memberInfoContainer.visibility = View.VISIBLE
                mBinding.memberUserContainerCopy.visibility = View.VISIBLE
                mBinding.memberTime.visibility = View.GONE
                mBinding.memberUserImage.borderColor = resources.getColor(R.color.color_7B849C)
                mBinding.memberUserName.setTextColor(resources.getColor(R.color.color_FCFCFC))
            }
            1 -> {
                //vip
                mBinding.memberUserLabel.setImageResource(R.mipmap.member_label2)
                mBinding.memberImage.setImageResource(R.mipmap.member_vip)
                mBinding.memberApply.text = "升级永久合伙人"
                mBinding.memberInfoContainer.visibility = View.VISIBLE
                mBinding.memberUserContainerCopy.visibility = View.GONE
                mBinding.memberTime.visibility = View.VISIBLE
                mBinding.memberTime.setTextColor(resources.getColor(R.color.color_855D26))
                if (TextUtils.isEmpty(bean.data.levelEndTime)){
                    mBinding.memberTime.text = "永久会员"
                }else{
                    mBinding.memberTime.text = bean.data.levelEndTime + "到期"
                }
                mBinding.memberUserName.setTextColor(resources.getColor(R.color.color_855D26))
                mBinding.memberUserImage.borderColor = resources.getColor(R.color.color_F0C57A)
            }
            else -> {
                //合伙人
                mBinding.memberUserLabel.setImageResource(R.mipmap.member_label3)
                mBinding.memberImage.setImageResource(R.mipmap.member_partner)
                mBinding.memberInfoContainer.visibility = View.GONE
                mBinding.memberUserContainerCopy.visibility = View.GONE
                mBinding.memberTime.visibility = View.VISIBLE
                mBinding.memberTime.text = "永久会员"
                mBinding.memberTime.setTextColor(resources.getColor(R.color.color_754235))
                mBinding.memberUserName.setTextColor(resources.getColor(R.color.color_754235))
                mBinding.memberUserImage.borderColor = resources.getColor(R.color.color_683428)
            }
        }
        //广告
        mAdList.clear()
        mAdList.addAll(bean.data.messageList)
        mBinding.viewFlipper.removeAllViews()
        for (i in mAdList.indices){
            var view = LayoutInflater.from(context).inflate(R.layout.view_flipper,null)
            var item_image = view.findViewById<ImageView>(R.id.item_image)
            var item_name = view.findViewById<TextView>(R.id.item_name)
            item_name.setTextColor(context?.resources?.getColor(R.color.color_222222)!!)
            item_name.text = mAdList[i].content
            GlideApp.loderCircleImage(context,mAdList[i].avatar,item_image,0,0)
            mBinding.viewFlipper.addView(view)
        }
        mBinding.viewFlipper.setFlipInterval(3000)
        mBinding.viewFlipper.startFlipping()
        //进度条设置
        mBinding.memberInfoUserProgressCopy.setTotalAndCurrentCount(bean.data.monthLevelInvitedUserCount,bean.data.invitedUserCount)
        mBinding.memberInfoUserProgress.setTotalAndCurrentCount(bean.data.levelInvitedUserCount,bean.data.invitedUserCount)
        mBinding.memberInfoCoinProgressCopy.setTotalAndCurrentCount(bean.data.monthLevelPoint,bean.data.point)
        mBinding.memberInfoCoinProgress.setTotalAndCurrentCount(bean.data.levelPoint,bean.data.point)
        //我的极币
        mBinding.memberCoin.text = "" + bean.data.point
    }

    override fun getDayList(bean: DailyTaskBean) {
        //每日任务
        mDayRule.clear()
        mDayRule.addAll(bean.data)
        mDayAdapter.notifyDataSetChanged()
        //完善用户信息任务
        mUserRule.clear()
        mUserRule.addAll(bean.list2)
        mUserAdapter.notifyDataSetChanged()
    }

    override fun onFile(error: String?) {
        mBinding.swipeToLoad.isRefreshing = false
        mDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
        if (!error.equals("登录凭证失效，请重新登录")){
            ToastUtil.show(error)
        }
    }

    override fun onApply() {
        mDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
        mPresenter.memberIndex(this.bindToLifecycle())//刷新页面
        DialogUtil.LoginDialog(context,"升级申请提交成功\n请添加您的导师微信：" + mBinding.memberWxCode.text.toString().replace(resources.getString(R.string.member_wx),"")
        ,"复制","取消"){
            var clip = context!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            var clipData = ClipData.newPlainText("jipinshop", mBinding.memberWxCode.text.toString().replace(resources.getString(R.string.member_wx),""))
            clip.primaryClip = clipData
            SPUtils.getInstance().put(CommonDate.CLIP,  mBinding.memberWxCode.text.toString().replace(resources.getString(R.string.member_wx),""))
            ToastUtil.show("复制成功")
        }
    }

    override fun onDayJump(pos: Int) {
        dayJump(mDayRule[pos].location, mDayRule[pos].locationId)
    }

    override fun onJump(pos: Int) {
        dayJump(mUserRule[pos].location , mUserRule[pos].locationId)
    }

    //每日任务的跳转逻辑
    fun dayJump(location: Int , url : String?) {
        when (location) {
            1 -> {//跳转到首页
                EventBus.getDefault().post(ChangeHomePageBus(0))
            }
            3 -> {//跳转到评测
                startActivity(Intent(context, HomeNewActivity::class.java)
                        .putExtra("type", HomeNewActivity.evaluation)
                )
            }
            4 -> {//跳转到邀请页面
                startActivity(Intent(context, InvitationNewActivity::class.java))
            }
            7 -> {//编辑个人资料
                startActivity(Intent(context, MyInfoActivity::class.java)
                        .putExtra("bgImg", SPUtils.getInstance(CommonDate.USER).getString(CommonDate.bgImg))
                        .putExtra("sign", SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userSign))
                )
            }
            8 -> {//填写邀请码
                DialogUtil.invitationDialog(context){ invitationCode, dialog, inputManager ->
                    if (TextUtils.isEmpty(invitationCode)) {
                        ToastUtil.show("请输入邀请码")
                        return@invitationDialog
                    }
                    mDialog = ProgressDialogView().createLoadingDialog(context, "")
                    mDialog?.show()
                    mPresenter.addInvitationCode(invitationCode, dialog, inputManager, this.bindToLifecycle<SuccessBean>())
                }
            }
            9 -> { //调用签到接口
                mDialog = ProgressDialogView().createLoadingDialog(context, "")
                mDialog?.show()
                mPresenter.sign(this.bindToLifecycle<SignInsertBean>())
            }
            10 -> {//搜索
                startActivity(Intent(context, TBSreachActivity::class.java))
            }
            11 -> {//分享发圈
                EventBus.getDefault().post(ChangeHomePageBus(2))
            }
            12 -> {//授权淘宝
                TaoBaoUtil.openTB(context) { ToastUtil.show("已完成授权") }
            }
            13 -> {//填写微信号
                startActivity(Intent(context, BindWXActivity::class.java))
            }
            14 -> {//添加导师微信
                mDialog = ProgressDialogView().createLoadingDialog(context, "")
                mDialog?.show()
                mPresenter.getParentInfo(this.bindToLifecycle<TeacherBean>())
            }
            15 -> {//填写调查问卷
                startActivity(Intent(context, WebActivity::class.java)
                        .putExtra(WebActivity.url, url)
                        .putExtra(WebActivity.title, "调查问卷")
                )
            }
            16 -> {//应用市场好评
                ShopJumpUtil.jumpMarkets(context)
            }
            17 -> {//关注公众号
                DialogUtil.wxDialog(context, "关注公众号", "微信服务号名称：", "微信关注极品城公众号，并绑定账号")
            }
            18 -> {//绑定小程序
                DialogUtil.wxDialog(context, "绑定小程序", "微信小程序：", "微信搜索极品城小程序，并绑定账号")
            }
        }
    }

    override fun onCodeSuc(dialog: Dialog, inputManager: InputMethodManager, bean: SuccessBean) {
        mDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
        ToastUtil.show(bean.msg)
        if (dialog.currentFocus != null)
            inputManager.hideSoftInputFromWindow(dialog.currentFocus!!.windowToken, 0)
        dialog.dismiss()
    }

    override fun signSuc(signInsertBean: SignInsertBean) {
        mDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.userPoint, signInsertBean.data.usablePoint)
        mBinding.memberCoin.text = SPUtils.getInstance(CommonDate.USER).getInt(CommonDate.userPoint).toString()
        ToastUtil.show("签到成功")
    }

    override fun onTeacher(bean: TeacherBean) {
        mDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
        DialogUtil.teacherDialog(context, bean.data.wechat, bean.data.avatar)
    }

    override fun onResume() {
        super.onResume()
        if (!once){
            //刷新任务
            mPresenter.DailytaskList(this.bindToLifecycle())
        }
    }
}