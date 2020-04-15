package com.example.administrator.jipinshop.fragment.member

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.blankj.utilcode.util.SPUtils
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.WebActivity
import com.example.administrator.jipinshop.activity.newpeople.NewPeopleActivity
import com.example.administrator.jipinshop.activity.sign.invitation.InvitationNewActivity
import com.example.administrator.jipinshop.adapter.NoPageBannerAdapter
import com.example.administrator.jipinshop.base.DBBaseFragment
import com.example.administrator.jipinshop.bean.MemberBean
import com.example.administrator.jipinshop.databinding.FragmentMemberBinding
import com.example.administrator.jipinshop.netwrok.RetrofitModule
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.util.sp.CommonDate
import com.example.administrator.jipinshop.view.dialog.DialogUtil
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView
import com.example.administrator.jipinshop.view.glide.GlideApp
import java.math.BigDecimal
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2020/4/1
 * @Describe 会员页面
 */
class KTMemberFragment : DBBaseFragment(), View.OnClickListener, OnRefreshListener, KTMemberView {

    @Inject
    lateinit var mPresenter: KTMemberPresenter

    private lateinit var mBinding: FragmentMemberBinding
    private var once: Boolean = true
    //banner
    private lateinit var mBannerAdapter: NoPageBannerAdapter
    private lateinit var mBannerList: MutableList<String>
    private lateinit var point: MutableList<ImageView>
    //广告
    private lateinit var mAdList: MutableList<MemberBean.DataBean.MessageListBean>

    private var levelCommission = 0
    private var commission = 0
    private var levelInvitedUserCount = 0
    private var invitedUserCount = 0
    private var mDialog: Dialog? = null

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

        //banner
        mBannerAdapter = NoPageBannerAdapter(context)
        mBannerList = mutableListOf()
        point = mutableListOf()
        mBannerAdapter.setPoint(point)
        mBannerAdapter.setList(mBannerList)
        mBannerAdapter.setViewPager(mBinding.memberViewPager)
        mBannerAdapter.setType(1)//设置点样式
        mBannerAdapter.setImgFixCenter(true)
        mBannerAdapter.setRefresh(1)
        mBinding.memberViewPager.adapter = mBannerAdapter
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
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.member_share, R.id.member_adContainer -> {
                //进入邀请好友getResources().getString(R.string.name)
                startActivity(Intent(context, InvitationNewActivity::class.java))
            }
            R.id.member_newBuy -> {
                startActivity(Intent(context, NewPeopleActivity::class.java))
            }
            R.id.member_newH5 -> {
                startActivity(Intent(context, WebActivity::class.java)
                        .putExtra(WebActivity.url, RetrofitModule.H5_URL + "newZn.html")
                        .putExtra(WebActivity.title, "极品城省钱攻略")
                        .putExtra(WebActivity.isShare,true)
                        .putExtra(WebActivity.shareTitle,"如何查找淘宝隐藏优惠券及下单返利？")
                        .putExtra(WebActivity.shareContent,"淘宝天猫90%的商品都能省，同时还有高额返利，淘好物，更省钱！")
                )
            }
            R.id.member_apply -> {
                if (mBinding.memberApply.text.toString() == "已为最高等级"){
                    return
                }
                if (mBinding.memberApply.text.toString() == "申请中"){
                    ToastUtil.show("您的申请正在审核中，请耐心等待")
                    return
                }
                if (levelInvitedUserCount == 0 || invitedUserCount < levelInvitedUserCount ){
                    ToastUtil.show("您还未完成任务，请继续努力")
                    return
                }
                if (levelCommission == 0 || commission < levelCommission){
                    ToastUtil.show("您还未完成任务，请继续努力")
                    return
                }
                mDialog = ProgressDialogView().createLoadingDialog(context, "")
                mDialog?.show()
                mPresenter.memberUpdate(this.bindToLifecycle())
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
        }
    }

    override fun onSuccess(bean: MemberBean) {
        mBinding.swipeToLoad.isRefreshing = false
        mBinding.date = bean.data
        mBinding.executePendingBindings()
        when (bean.data.level){
            0 -> {
                //普通人员
                mBinding.memberUserLabel.setImageResource(R.mipmap.member_label1)
                mBinding.memberImage.setImageResource(R.mipmap.member_public)
                mBinding.memberTitle2.setImageResource(R.mipmap.member_title3)
                mBinding.memberApply.text = "申请升级VIP"
                mBinding.memberUserImage.borderColor = resources.getColor(R.color.color_7B849C)
            }
            1 -> {
                //vip
                mBinding.memberUserLabel.setImageResource(R.mipmap.member_label2)
                mBinding.memberImage.setImageResource(R.mipmap.member_vip)
                mBinding.memberTitle2.setImageResource(R.mipmap.member_title2)
                mBinding.memberApply.text = "申请升级合伙人"
                mBinding.memberUserImage.borderColor = resources.getColor(R.color.color_F0C57A)
            }
            else -> {
                //合伙人
                mBinding.memberUserLabel.setImageResource(R.mipmap.member_label3)
                mBinding.memberImage.setImageResource(R.mipmap.member_partner)
                mBinding.memberTitle2.setImageResource(R.mipmap.member_title2)
                mBinding.memberApply.text = "已为最高等级"
                mBinding.memberUserImage.borderColor = resources.getColor(R.color.color_683428)
            }
        }
        //轮播图
        mBannerList.clear()
        point.clear()
        mBannerList.addAll(bean.data.imgList)
        mPresenter.initBanner(mBannerList, context!!, point, mBinding.memberPoint, mBannerAdapter)
        mBinding.memberViewPager.currentItem = 0
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
        levelInvitedUserCount = bean.data.levelInvitedUserCount
        invitedUserCount = bean.data.invitedUserCount
        mBinding.memberFansProgress.setTotalAndCurrentCount(levelInvitedUserCount,invitedUserCount)
        levelCommission = BigDecimal(bean.data.levelCommission).setScale(0,BigDecimal.ROUND_DOWN).toInt()
        commission = BigDecimal(bean.data.commission).setScale(0,BigDecimal.ROUND_DOWN).toInt()
        mBinding.memberCommissionProgress.setTotalAndCurrentCount(levelCommission,commission)
        if (bean.data.levelStatus == 0){
            mBinding.memberApply.text = "申请中"
        }
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
        mBinding.memberApply.text = "申请中"
        DialogUtil.LoginDialog(context,"升级申请提交成功\n请添加您的导师微信：" + mBinding.memberWxCode.text.toString().replace(resources.getString(R.string.member_wx),"")
        ,"复制","取消"){
            var clip = context!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            var clipData = ClipData.newPlainText("jipinshop", mBinding.memberWxCode.text.toString().replace(resources.getString(R.string.member_wx),""))
            clip.primaryClip = clipData
            SPUtils.getInstance().put(CommonDate.CLIP,  mBinding.memberWxCode.text.toString().replace(resources.getString(R.string.member_wx),""))
            ToastUtil.show("复制成功")
        }
    }

    override fun onResume() {
        super.onResume()
        if (!once){
            mPresenter.memberIndex(this.bindToLifecycle())
        }
    }
}