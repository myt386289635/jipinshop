package com.example.administrator.jipinshop.fragment.mine

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.widget.GridLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.aspsine.swipetoloadlayout.OnLoadMoreListener
import com.blankj.utilcode.util.SPUtils
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.balance.MyWalletActivity
import com.example.administrator.jipinshop.activity.balance.team.TeamActivity
import com.example.administrator.jipinshop.activity.balance.withdraw.WithdrawActivity
import com.example.administrator.jipinshop.activity.cheapgoods.CheapBuyActivity
import com.example.administrator.jipinshop.activity.foval.FovalActivity
import com.example.administrator.jipinshop.activity.home.home.HomeNewActivity
import com.example.administrator.jipinshop.activity.login.LoginActivity
import com.example.administrator.jipinshop.activity.member.buy.MemberBuyActivity
import com.example.administrator.jipinshop.activity.mine.browse.BrowseActivity
import com.example.administrator.jipinshop.activity.mine.welfare.OfficialWelfareActivity
import com.example.administrator.jipinshop.activity.minekt.orderkt.KTMyOrderActivity
import com.example.administrator.jipinshop.activity.minekt.recovery.OrderRecoveryActivity
import com.example.administrator.jipinshop.activity.minekt.userkt.UserActivity
import com.example.administrator.jipinshop.activity.setting.SettingActivity
import com.example.administrator.jipinshop.activity.setting.opinion.OpinionActivity
import com.example.administrator.jipinshop.activity.share.ShareActivity
import com.example.administrator.jipinshop.activity.sign.SignActivity
import com.example.administrator.jipinshop.activity.sign.invitation.InvitationNewActivity
import com.example.administrator.jipinshop.activity.web.server.ServerWebActivity
import com.example.administrator.jipinshop.adapter.HomePageAdapter
import com.example.administrator.jipinshop.adapter.KTMineAdapter
import com.example.administrator.jipinshop.base.DBBaseFragment
import com.example.administrator.jipinshop.bean.*
import com.example.administrator.jipinshop.bean.eventbus.ChangeHomePageBus
import com.example.administrator.jipinshop.bean.eventbus.EditNameBus
import com.example.administrator.jipinshop.bean.eventbus.HomeRefresh
import com.example.administrator.jipinshop.bean.eventbus.MemberRefreshBus
import com.example.administrator.jipinshop.databinding.FragmentKtMineBinding
import com.example.administrator.jipinshop.jpush.JPushReceiver
import com.example.administrator.jipinshop.netwrok.RetrofitModule
import com.example.administrator.jipinshop.util.ShareUtils
import com.example.administrator.jipinshop.util.TaoBaoUtil
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.util.UmApp.UAppUtil
import com.example.administrator.jipinshop.util.sp.CommonDate
import com.example.administrator.jipinshop.view.dialog.DialogUtil
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.trello.rxlifecycle2.android.FragmentEvent
import com.umeng.socialize.bean.SHARE_MEDIA
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2020/6/5
 * @Describe
 */
class KTMineFragment : DBBaseFragment(), KTMineAdapter.OnItem, KTMineView, OnLoadMoreListener, View.OnClickListener {

    @Inject
    lateinit var mPresenter: KTMinePresenter

    private lateinit var mBinding: FragmentKtMineBinding
    private lateinit var mList: MutableList<SimilerGoodsBean.DataBean>
    private lateinit var mAdListBeans: MutableList<EvaluationTabBean.DataBean.AdListBean>
    private lateinit var mAdapter : KTMineAdapter
    private var once : Boolean = true //第一次进入
    private var page = 1
    private var refersh: Boolean = true
    private var officialWeChat = ""//客服电话
    private var officialWeChatQR = "" //客服二维码
    private var mDialog: Dialog? = null
    private var mBean : UserInfoBean? = null
    private var level : Int = 0 //默认普通会员
    private var withdraw : String = "0" //用户可提现金额

    companion object{
        @JvmStatic //java中的静态方法
        fun getInstance() : KTMineFragment {
            return KTMineFragment()
        }

        @JvmField
        val MsgRefersh = "MsgRefersh"

        @JvmField
        val eventbusTag = "SignActivity"
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && once){
            page = 1
            refersh = true
            mPresenter.listSimilerGoods(context!!,page,this.bindToLifecycle())
        }
    }

    override fun initLayout(inflater: LayoutInflater?, container: ViewGroup?): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_kt_mine, container, false)
        mBinding.listener = this
        EventBus.getDefault().register(this)
        return mBinding.root
    }

    override fun initView() {
        mBaseFragmentComponent.inject(this)
        mPresenter.setView(this)

        mAdListBeans = mutableListOf()
        mList = mutableListOf()
        mBinding.swipeTarget.layoutManager = GridLayoutManager(context!!,2)
        mAdapter = KTMineAdapter(mList,context!!)
        mAdapter.setOnItem(this)
        mAdapter.setAdList(mAdListBeans)
        mAdapter.setTeamAdapter(HomePageAdapter(childFragmentManager))
        mBinding.swipeTarget.adapter = mAdapter

        mBinding.swipeToLoad.setOnLoadMoreListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.mine_server -> {
                //联系客服
                onHelp()
            }
        }
    }

    override fun onLoadMore() {
        page++
        refersh = false
        mPresenter.listSimilerGoods(context!!,page,this.bindToLifecycle())
    }

    override fun onResume() {
        super.onResume()
        if (!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token,"").trim())) {
            //这里防止点击广告后直接跳转广告未请求到用户姓名和登陆信息情况
            mPresenter.modelUser(this.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
            mPresenter.myCommssionSummary(this.bindToLifecycle())//获取本人佣金
        }
        mPresenter.unMessage(this.bindToLifecycle())
        mPresenter.adList(this.bindToLifecycle())//请求轮播图数据
    }

    //获取到用户信息
    override fun successUserInfo(userInfoBean: UserInfoBean) {
        //存储用户信息数据
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.userBirthday, userInfoBean.data.birthday)
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.userGender, userInfoBean.data.gender)
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.userNickImg, userInfoBean.data.avatar)
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.userNickName, userInfoBean.data.nickname)
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.userPhone, userInfoBean.data.mobile)
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.userMemberGrade, userInfoBean.data.memberLevel)
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.userPoint, userInfoBean.data.point)
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.bindMobile, userInfoBean.data.bindMobile.toString() + "")
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.bindWeibo, userInfoBean.data.bindWeibo.toString() + "")
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.bindWeixin, userInfoBean.data.bindWeixin.toString() + "")
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.qrCode, userInfoBean.data.invitationCode)
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.relationId, userInfoBean.data.relationId)
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.userId, userInfoBean.data.userId)
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.userSign, userInfoBean.data.detail)
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.bgImg, userInfoBean.data.bgImg)
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.wechat, userInfoBean.data.wechat)
        SPUtils.getInstance(CommonDate.USER).put(CommonDate.isNewUser, userInfoBean.data.isNewUser)

        mBean = userInfoBean
        officialWeChat = userInfoBean.data.officialWeChat
        officialWeChatQR = userInfoBean.data.officialWeChatQR
        level = userInfoBean.data.level
        mAdapter.setBean(userInfoBean)
        mAdapter.notifyDataSetChanged()
    }

    //获取用户信息失败
    override fun FaileUserInfo(error: Int, message : String?) {
        var userInfoBean = UserInfoBean()
        userInfoBean.code = error
        mAdapter.setBean(userInfoBean)
        mAdapter.notifyDataSetChanged()
        ToastUtil.show(message)
        if (error == 602){
            SPUtils.getInstance(CommonDate.USER).clear()
        }
    }

    //佣金
    override fun onCommssionSummary(bean: MyWalletBean) {
        withdraw = bean.data.balanceFee
        mAdapter.setWallet(bean)
        mAdapter.notifyDataSetChanged()
    }

    override fun onFileCommen(error: String?) {
        mDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
        ToastUtil.show(error)
    }

    //轮播图
    override fun onAdList(bean: SucBean<EvaluationTabBean.DataBean.AdListBean>) {
        if (bean.data != null && bean.data.size != 0) {
            mAdListBeans.clear()
            if (bean.data.size > 1) {
                for (i in 0 until bean.data.size + 2) {
                    if (i == 0) {
                        //加入最后一个
                        mAdListBeans.add(bean.data[bean.data.size - 1])
                    } else if (i == bean.data.size + 1) {
                        //加入第一个
                        mAdListBeans.add(bean.data[0])
                    } else {
                        mAdListBeans.add(bean.data[i - 1])
                    }
                }
            } else {
                mAdListBeans.addAll(bean.data)
            }
        }else{
            mAdListBeans.clear()
        }
        mAdapter.notifyDataSetChanged()
    }

    //消息未读数
    override fun unMessageSuc(unMessageBean: UnMessageBean) {
        EventBus.getDefault().post(EditNameBus(MsgRefersh, "" + unMessageBean.data))
    }

    //猜你喜欢
    override fun onSuccess(bean: SimilerGoodsBean) {
        if (refersh) {
            if (bean.data != null && bean.data.size != 0) {
                mList.clear()
                mList.addAll(bean.data)
                mAdapter.notifyDataSetChanged()
            }
        } else {
            dissLoading()
            if (bean.data != null && bean.data.size != 0) {
                mList.addAll(bean.data)
                mAdapter.notifyDataSetChanged()
            } else {
                page--
                ToastUtil.show("已经是最后一页了")
            }
        }
        if (once)
            once = false
    }

    override fun onFile(error: String?) {
        if (refersh) {
        } else {
            dissLoading()
            page--
        }
        ToastUtil.show(error)
    }

    fun dissLoading() {
        if (mBinding.swipeToLoad.isLoadingMore) {
            if (!mBinding.swipeToLoad.isLoadMoreEnabled) {
                mBinding.swipeToLoad.isLoadMoreEnabled = true
                mBinding.swipeToLoad.isLoadingMore = false
                mBinding.swipeToLoad.isLoadMoreEnabled = false
            } else {
                mBinding.swipeToLoad.isLoadingMore = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this)
    }

    override fun onItemShare(position: Int) {
        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
            startActivity(Intent(context, LoginActivity::class.java))
            return
        }
        if (TextUtils.isEmpty(mList[position].source) || mList[position].source == "2"){
            TaoBaoUtil.openTB(context){
                startActivity(Intent(context, ShareActivity::class.java)
                        .putExtra("otherGoodsId", mList[position].otherGoodsId)
                        .putExtra("source",mList[position].source)
                )
            }
        }else{
            startActivity(Intent(context, ShareActivity::class.java)
                    .putExtra("otherGoodsId", mList[position].otherGoodsId)
                    .putExtra("source",mList[position].source)
            )
        }
    }

    //跳转到个人主页
    override fun onUserInfo() {
        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
            startActivity(Intent(context, LoginActivity::class.java))
            return
        }
        startActivity(Intent(context, UserActivity::class.java)
                .putExtra("userid", SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId))
        )
    }

    //进入会员页面
    override fun onMember() {
        EventBus.getDefault().post(ChangeHomePageBus(2))
    }

    //我的收益
    override fun onWallet() {
        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
            startActivity(Intent(context, LoginActivity::class.java))
            return
        }
        startActivity(Intent(context, MyWalletActivity::class.java))
        UAppUtil.mine(context, 7)
    }

    //我的好友
    override fun onTeam() {
        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
            startActivity(Intent(context, LoginActivity::class.java))
            return
        }
        startActivity(Intent(context, TeamActivity::class.java))
    }

    //我的订单
    override fun onOrder(status: Int) {
        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
            startActivity(Intent(context, LoginActivity::class.java))
            return
        }
        startActivity(Intent(context, KTMyOrderActivity::class.java)
                .putExtra("status",status)
        )
    }

    //我的邀请
    override fun onInvitation() {
        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
            startActivity(Intent(context, LoginActivity::class.java))
            return
        }
        startActivity(Intent(context, InvitationNewActivity::class.java))
    }

    //浏览足迹
    override fun onMessage() {
        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
            startActivity(Intent(context, LoginActivity::class.java))
            return
        }
        startActivity(Intent(context, BrowseActivity::class.java))
    }

    //收藏夹
    override fun onFover() {
        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
            startActivity(Intent(context, LoginActivity::class.java))
            return
        }
        startActivity(Intent(context, FovalActivity::class.java))
        UAppUtil.mine(context, 10)
    }

    //设置
    override fun onSetting() {
        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
            startActivity(Intent(context, LoginActivity::class.java))
            return
        }
        startActivityForResult(Intent(context, SettingActivity::class.java)
                .putExtra("officialWeChat", officialWeChat), 100)
        UAppUtil.mine(context, 12)
    }

    //任务中心
    override fun onRule() {
        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
            startActivity(Intent(context, LoginActivity::class.java))
            return
        }
        startActivity(Intent(context, SignActivity::class.java))
        UAppUtil.mine(context, 9)
    }

    //打开特惠购
    override fun onCheapBuy() {
        startActivity(Intent(context, CheapBuyActivity::class.java))
    }

    //打开官方福利群
    override fun onWelfare() {
        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
            startActivity(Intent(context, LoginActivity::class.java))
            return
        }
        startActivity(Intent(context, OfficialWelfareActivity::class.java)
                .putExtra("officialWeChat",officialWeChat)
                .putExtra("officialWeChatQR",officialWeChatQR)
        )
    }

    //官方客服
    override fun onServer() {
        DialogUtil.LoginDialog(context, "官方客服微信：$officialWeChat", "复制", "取消") { v ->
            var clip = context!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            var clipData = ClipData.newPlainText("jipinshop", officialWeChat)
            clip.primaryClip = clipData
            ToastUtil.show("微信号复制成功")
            SPUtils.getInstance().put(CommonDate.CLIP, officialWeChat)
        }
    }

    //客服与帮助
    override fun onHelp() {
        startActivity(Intent(context, ServerWebActivity::class.java)
                .putExtra(ServerWebActivity.url, RetrofitModule.JP_H5_URL + "new-free/helpServices?userId="
                        + SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId))
        )
    }

    //进入省钱流程
    override fun onCourse() {
        startActivity(Intent(context, HomeNewActivity::class.java)
                .putExtra("type", HomeNewActivity.school)
        )
    }

    //会员兑换
    override fun onMemberDialog() {
        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
            startActivity(Intent(context, LoginActivity::class.java))
            return
        }
        DialogUtil.memberExchange(context) { invitationCode, dialog, inputManager ->
            if (TextUtils.isEmpty(invitationCode)) {
                ToastUtil.show("请输入兑换码")
                return@memberExchange
            }
            mDialog = ProgressDialogView().createLoadingDialog(context, "")
            mDialog?.show()
            mPresenter.exchangeCode(invitationCode,dialog,inputManager,this.bindToLifecycle())
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
        EventBus.getDefault().post(HomeRefresh(HomeRefresh.tag))//用来刷新首页的
        EventBus.getDefault().post(MemberRefreshBus())//刷新会员页面
        mPresenter.modelUser(this.bindUntilEvent(FragmentEvent.DESTROY_VIEW))//刷新我的页面
    }

    //订单找回
    override fun onOrderRecovery() {
        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
            startActivity(Intent(context, LoginActivity::class.java))
            return
        }
        startActivity(Intent(context, OrderRecoveryActivity::class.java))
    }

    //进入小程序
    override fun applets() {
        var api = WXAPIFactory.createWXAPI(context, "wxfd2e92db2568030a")
        var req = WXLaunchMiniProgram.Req()
        req.userName = "gh_b0a86c45468d"
        req.path = "pages/tabMain/main/main"
        req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE // 可选打开 开发版1体验版2和正式版0
        api.sendReq(req)
    }

    //进入会员购买页
    override fun onMemberBuy() {
        if (level == 0){
            //购买
            startActivityForResult(Intent(context, MemberBuyActivity::class.java)
                    .putExtra("isBuy", "1"), 300)
        }else{
            //续费
            startActivityForResult(Intent(context, MemberBuyActivity::class.java)
                    .putExtra("isBuy", "2"), 300)
        }
    }

    //进入提现页面
    override fun onWithdraw() {
        startActivity(Intent(context, WithdrawActivity::class.java)
                .putExtra("price", "¥$withdraw")
        )
    }

    @Subscribe
    fun eidtInfo(bus: EditNameBus?) {
       if (bus != null && bus.tag == eventbusTag) {
            //首页绑定上下级时需要刷新我的页面
           mBean?.let {
               it.data.pid = "1"
               mAdapter.notifyDataSetChanged()
           }
        }
    }

    @Subscribe
    fun unMessage(s: String) {
        if (!TextUtils.isEmpty(s) && s == JPushReceiver.TAG) {
            mPresenter.unMessage(this.bindToLifecycle())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 300 && resultCode == 200){
            //购买会员成功后返回
            var level = data?.getStringExtra("level")
            DialogUtil.paySucDialog(context, level){
                mPresenter.initShare(this.bindToLifecycle())
                mPresenter.taskFinish(this.bindUntilEvent<TaskFinishBean>(FragmentEvent.DESTROY_VIEW))
            }
        }else{
            when (resultCode) {
                201 -> { //退出登陆成功
                    var userInfoBean = UserInfoBean()
                    userInfoBean.code = 602
                    mAdapter.setBean(userInfoBean)
                    mAdapter.notifyDataSetChanged()
                    SPUtils.getInstance(CommonDate.USER).clear()
                    EventBus.getDefault().post(HomeRefresh(HomeRefresh.tag))//用来刷新首页的
                    EventBus.getDefault().post(ChangeHomePageBus(0))//滑动到第一页
                }
            }
        }
    }

    //会员购买分享
    override fun initShare(bean: ShareInfoBean) {
        ShareUtils(activity, SHARE_MEDIA.WEIXIN)
                .shareImage(activity, bean.data.imgUrl)
    }
}