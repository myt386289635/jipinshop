package com.example.administrator.jipinshop.fragment.money

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.blankj.utilcode.util.SPUtils
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.balance.team.TeamActivity
import com.example.administrator.jipinshop.activity.login.LoginActivity
import com.example.administrator.jipinshop.activity.money.withdraw.MoneyWithdrawActivity
import com.example.administrator.jipinshop.base.DBBaseFragment
import com.example.administrator.jipinshop.bean.MoneyBean
import com.example.administrator.jipinshop.databinding.FragmentKtMoneyBinding
import com.example.administrator.jipinshop.util.FileManager
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.util.sp.CommonDate
import com.example.administrator.jipinshop.view.glide.GlideApp
import java.math.BigDecimal
import javax.inject.Inject

/**
 * 赚钱页面  （活动页面）
 */
class MoneyFragment : DBBaseFragment(), View.OnClickListener, OnRefreshListener, MoneyView {

    @Inject
    lateinit var mPresenter: MoneyPresenter

    private lateinit var mBinding: FragmentKtMoneyBinding
    private var once: Boolean = true
    private lateinit var mBedBg : MutableList<ImageView>
    private lateinit var mBedTag :  MutableList<TextView>
    private lateinit var mBedContainer : MutableList<LinearLayout>
    private lateinit var mGetBedContainer: MutableList<RelativeLayout>
    private lateinit var mHongbaoList : MutableList<MoneyBean.DataBean.HongbaoListBean>

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
        mHongbaoList = mutableListOf()

        //红包背景
        mBedBg = mutableListOf()
        mBedBg.add(mBinding.moneyBackground1)
        mBedBg.add(mBinding.moneyBackground2)
        mBedBg.add(mBinding.moneyBackground3)
        mBedBg.add(mBinding.moneyBackground4)
        mBedBg.add(mBinding.moneyBackground5)
        mBedBg.add(mBinding.moneyBackground6)
        mBedBg.add(mBinding.moneyBackground7)
        mBedBg.add(mBinding.moneyBackground8)
        mBedBg.add(mBinding.moneyBackground9)
        //元/位  元
        mBedTag = mutableListOf()
        mBedTag.add(mBinding.moneyBedTag1)
        mBedTag.add(mBinding.moneyBedTag2)
        mBedTag.add(mBinding.moneyBedTag3)
        mBedTag.add(mBinding.moneyBedTag4)
        mBedTag.add(mBinding.moneyBedTag5)
        mBedTag.add(mBinding.moneyBedTag6)
        mBedTag.add(mBinding.moneyBedTag7)
        mBedTag.add(mBinding.moneyBedTag8)
        mBedTag.add(mBinding.moneyBedTag9)
        //钱背景1
        mBedContainer = mutableListOf()
        mBedContainer.add(mBinding.moneyBedContainer1)
        mBedContainer.add(mBinding.moneyBedContainer2)
        mBedContainer.add(mBinding.moneyBedContainer3)
        mBedContainer.add(mBinding.moneyBedContainer4)
        mBedContainer.add(mBinding.moneyBedContainer5)
        mBedContainer.add(mBinding.moneyBedContainer6)
        mBedContainer.add(mBinding.moneyBedContainer7)
        mBedContainer.add(mBinding.moneyBedContainer8)
        mBedContainer.add(mBinding.moneyBedContainer9)
        //钱背景2
        mGetBedContainer = mutableListOf()
        mGetBedContainer.add(mBinding.moneyGetBedContainer1)
        mGetBedContainer.add(mBinding.moneyGetBedContainer2)
        mGetBedContainer.add(mBinding.moneyGetBedContainer3)
        mGetBedContainer.add(mBinding.moneyGetBedContainer4)
        mGetBedContainer.add(mBinding.moneyGetBedContainer5)
        mGetBedContainer.add(mBinding.moneyGetBedContainer6)
        mGetBedContainer.add(mBinding.moneyGetBedContainer7)
        mGetBedContainer.add(mBinding.moneyGetBedContainer8)
        mGetBedContainer.add(mBinding.moneyGetBedContainer9)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.money_rule ->{
                ToastUtil.show("规则说明")
            }
            R.id.money_reCopy ->{
                var clip = context!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                var clipData = ClipData.newPlainText("jipinshop", mBinding.moneyReCode.text.toString())
                clip.primaryClip = clipData
                ToastUtil.show("复制成功")
                SPUtils.getInstance().put(CommonDate.CLIP, mBinding.moneyReCode.text.toString())
            }
            R.id.money_reInvitation -> {
                ToastUtil.show("立即邀请")
            }
            R.id.money_balanceWithdraw -> {
                if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                    startActivity(Intent(context,LoginActivity::class.java))
                }else{
                    startActivity(Intent(context,MoneyWithdrawActivity::class.java))
                }
            }
            R.id.money_background1 -> {
                BedClick(0,3)
            }
            R.id.money_background2 -> {
                BedClick(1,4)
            }
            R.id.money_background3 -> {
                BedClick(2,5)
            }
            R.id.money_background4 -> {
                BedClick(3,6)
            }
            R.id.money_background5 -> {
                BedClick(4,7)
            }
            R.id.money_background6 -> {
                BedClick(5,8)
            }
            R.id.money_background7 -> {
                BedClick(6,9)
            }
            R.id.money_background8 -> {
                BedClick(7,10)
            }
            R.id.money_background9 -> {
                BedClick(8,10)
            }
            R.id.money_friends -> {
                startActivity(Intent(context, TeamActivity::class.java))
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

    override fun onSuccess(bean : MoneyBean) {
        stopResher()
        mHongbaoList.clear()
        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
            //未登录
            mBinding.moneyReTitle1.visibility = View.GONE
            mBinding.moneyReTitle2.visibility = View.VISIBLE
            //当前红包余额
            mBinding.moneyBalance.text = "152"
            mBinding.moneyBalanceNode.text = "登陆后领取现金红包"
            mBinding.moneyBalanceWithdraw.text = "立即登录"
            //红包列表
            initBed()
            //我的好友
            mBinding.moneyFriendsLayout.visibility = View.GONE
            mBinding.moneyNofriendsLayout.visibility = View.VISIBLE
        }else{
            //已登录
            mBinding.moneyReTitle1.visibility = View.VISIBLE
            mBinding.moneyReCode.text = bean.data.invitationCode
            mBinding.moneyReTitle2.visibility = View.GONE
            //当前红包余额
            mBinding.moneyBalance.text = bean.data.currentMoney
            mBinding.moneyBalanceNode.text = "邀请好友奖励可以立即提现"
            mBinding.moneyBalanceWithdraw.text = "立即提现"
            //红包列表
            initBed(bean.data.hongbaoList)
            mHongbaoList.addAll(bean.data.hongbaoList)
            //我的好友
            if (bean.data.avatarList.size != 0){
                mBinding.moneyFriendsLayout.visibility = View.VISIBLE
                mBinding.moneyNofriendsLayout.visibility = View.GONE
                mBinding.moneyFriendNum.text = "等"+ bean.data.avatarList.size +"人加入我的团队"
                mBinding.moneyFriendMoney.text = "累计获得现金红包8.00元"
                for (i in bean.data.avatarList.indices){
                    when(i){
                        0 -> {
                            mBinding.moneyFriend1.visibility = View.VISIBLE
                            GlideApp.loderImage(context,bean.data.avatarList[i],mBinding.moneyFriend1,0,0)
                        }
                        1 -> {
                            mBinding.moneyFriend2.visibility = View.VISIBLE
                            GlideApp.loderImage(context,bean.data.avatarList[i],mBinding.moneyFriend2,0,0)
                        }
                        2 -> {
                            mBinding.moneyFriend3.visibility = View.VISIBLE
                            GlideApp.loderImage(context,bean.data.avatarList[i],mBinding.moneyFriend3,0,0)
                        }
                    }
                }
            }else{
                mBinding.moneyFriendsLayout.visibility = View.GONE
                mBinding.moneyNofriendsLayout.visibility = View.VISIBLE
            }
        }
        initMessage(bean.data.messageList)//跑马灯
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

    //跑马灯
    private fun initMessage(messageList: List<MoneyBean.DataBean.MessageListBean>) {
        mBinding.viewFlipper.removeAllViews()
        for (i in messageList.indices){
            var view = LayoutInflater.from(context).inflate(R.layout.item_money_flipper,null)
            var item_money = view.findViewById<TextView>(R.id.item_money)
            var item_name = view.findViewById<TextView>(R.id.item_name)
            var item_time = view.findViewById<TextView>(R.id.item_time)
            item_name.text = FileManager.editPhone(messageList[i].mobile) + "支付宝"
            item_money.text = "提现"+ messageList[i].money +"元"
            item_time.text = messageList[i].time + "分钟前"
            mBinding.viewFlipper.addView(view)
        }
        mBinding.viewFlipper.setFlipInterval(2000)
        mBinding.viewFlipper.startFlipping()
    }

    //初始化红包（未登录）
    private fun initBed(){
        for (i in 0 until 9){
            mBedBg[i].setImageResource(R.mipmap.bg_money_unget)
            mBedContainer[i].visibility = View.VISIBLE
            mGetBedContainer[i].visibility = View.GONE
            mBedTag[i].text = "元/位"
        }
    }

    //初始化红包列表（已登录）
    private fun initBed(hongbaoList : List<MoneyBean.DataBean.HongbaoListBean>){
        for (i in 0 until 9){
            when(hongbaoList[i].status){
                "0" -> {
                    //待邀请
                    mBedBg[i].setImageResource(R.mipmap.bg_money_unget)
                    mBedContainer[i].visibility = View.VISIBLE
                    mGetBedContainer[i].visibility = View.GONE
                    mBedTag[i].text = "元/位"
                }
                "1" -> {
                    //待领取
                    mBedBg[i].setImageResource(R.mipmap.bg_money_get)
                    mBedContainer[i].visibility = View.VISIBLE
                    mGetBedContainer[i].visibility = View.GONE
                    mBedTag[i].text = "元"
                }
                "2" -> {
                    //已领取
                    mBedBg[i].setImageResource(R.mipmap.bg_money_geted)
                    mBedContainer[i].visibility = View.GONE
                    mGetBedContainer[i].visibility = View.VISIBLE
                }
            }
        }
    }

    //红包点击事件
    private fun BedClick(set: Int , money : Int){
        if (mHongbaoList.size != 0 && mHongbaoList[set].status == "1") {
            mBedBg[set].setImageResource(R.mipmap.bg_money_geted)
            mBedContainer[set].visibility = View.GONE
            mGetBedContainer[set].visibility = View.VISIBLE
            var balance = BigDecimal(mBinding.moneyBalance.text.toString()).toDouble() + money
            mBinding.moneyBalance.text = BigDecimal(balance).setScale(2, BigDecimal.ROUND_DOWN).stripTrailingZeros().toPlainString()
        }
    }

}