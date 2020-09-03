package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Handler
import android.os.Looper
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.blankj.utilcode.util.SPUtils
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.shoppingdetail.tbshoppingdetail.TBShoppingDetailActivity
import com.example.administrator.jipinshop.bean.EvaluationTabBean
import com.example.administrator.jipinshop.bean.MyWalletBean
import com.example.administrator.jipinshop.bean.SimilerGoodsBean
import com.example.administrator.jipinshop.bean.UserInfoBean
import com.example.administrator.jipinshop.databinding.ItemMineHead1Binding
import com.example.administrator.jipinshop.databinding.ItemMineHead2Binding
import com.example.administrator.jipinshop.databinding.ItemMineHead3Binding
import com.example.administrator.jipinshop.databinding.ItemUserlikeBinding
import com.example.administrator.jipinshop.util.ShopJumpUtil
import com.example.administrator.jipinshop.util.WeakRefHandler
import com.example.administrator.jipinshop.util.sp.CommonDate
import com.example.administrator.jipinshop.view.glide.GlideApp
import com.example.administrator.jipinshop.view.viewpager.TouchViewPager
import q.rorbin.badgeview.QBadgeView
import java.math.BigDecimal

/**
 * @author 莫小婷
 * @create 2020/6/5
 * @Describe
 */
class KTMineAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private val HEAD1 = 1
    private val HEAD2 = 2
    private val HEAD3 = 3
    private val CONTENT = 4

    private var mList: MutableList<SimilerGoodsBean.DataBean>
    private lateinit var mAdListBeans: MutableList<EvaluationTabBean.DataBean.AdListBean> //轮播图列表
    private var context : Context
    private var mBean: UserInfoBean? = null
    private var mOnItem: OnItem? = null
    private var unMessage: Int = 0//未读数
    private var mWalletBean : MyWalletBean? = null

    fun setWallet(bean : MyWalletBean?){
        mWalletBean = bean
    }

    fun setBean(bean : UserInfoBean?){
        mBean = bean
    }

    fun setOnItem(onItem: OnItem) {
        mOnItem = onItem
    }

    fun setAdList(adListBeans: MutableList<EvaluationTabBean.DataBean.AdListBean>){
        this.mAdListBeans = adListBeans
    }

    fun setUnMessage(unMessage: Int){
        this.unMessage = unMessage
    }

    constructor(list: MutableList<SimilerGoodsBean.DataBean>, context: Context) {
        this.mList = list
        this.context = context
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> HEAD1
            1 -> HEAD2
            2 -> HEAD3
            else -> CONTENT
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is GridLayoutManager) {
            val gridLayoutManager = layoutManager as GridLayoutManager?
            gridLayoutManager!!.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (getItemViewType(position) == CONTENT) {
                        1
                    } else {
                        gridLayoutManager.spanCount
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): RecyclerView.ViewHolder {
        var holder : RecyclerView.ViewHolder? = null
        when(type){
            HEAD1 -> {
                var itemMineHead1Binding = DataBindingUtil.inflate<ItemMineHead1Binding>(LayoutInflater.from(context), R.layout.item_mine_head1, viewGroup, false)
                holder = HeadViewHolder(itemMineHead1Binding)
            }
            HEAD2 -> {
                var itemMineHead2Binding = DataBindingUtil.inflate<ItemMineHead2Binding>(LayoutInflater.from(context), R.layout.item_mine_head2, viewGroup, false)
                holder = Head2ViewHolder(itemMineHead2Binding)
            }
            HEAD3 -> {
                var itemMineHead3Binding = DataBindingUtil.inflate<ItemMineHead3Binding>(LayoutInflater.from(context), R.layout.item_mine_head3, viewGroup, false)
                holder = Head3ViewHolder(itemMineHead3Binding)
            }
            CONTENT -> {
                var binding = DataBindingUtil.inflate<ItemUserlikeBinding>(LayoutInflater.from(context), R.layout.item_userlike, viewGroup, false)
                holder = ViewHolder(binding)
            }
        }
        return holder!!
    }

    override fun getItemCount(): Int {
        return mList.size + 3
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, pos: Int) {
        var type = getItemViewType(pos)
        when(type){
            HEAD1 -> {
                var head1ViewHolder : HeadViewHolder = holder as HeadViewHolder
                head1ViewHolder.run {
                    mBean?.let {
                        if (it.code == 0){//请求成功
                            binding.mineName.visibility = View.VISIBLE
                            binding.mineLogin.visibility = View.GONE
                            binding.mineInfo.visibility = View.VISIBLE
                            binding.mineCopyContainer.visibility = View.VISIBLE//复制邀请码
                            binding.mineIntegral.text = "邀请码：" + SPUtils.getInstance(CommonDate.USER).getString(CommonDate.qrCode, "000000")
                            binding.mineName.text = SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickName)
                            if (!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickImg))) {
                                GlideApp.loderImage(context, SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickImg), binding.mineImage, R.mipmap.logo, 0)
                            }
                            if (TextUtils.isEmpty(it.data.bgImg)) {
                                binding.mineBackground.setImageResource(R.mipmap.mine_imagebg_dafult)
                            } else {
                                GlideApp.loderImage(context, it.data.bgImg, binding.mineBackground, 0, 0)
                            }
                            if (it.data.level == 2) {
                                //2合伙人
                                binding.itemGrade.setImageResource(R.mipmap.grade_partner)
                            } else {
                                if (it.data.level == 1) {
                                    binding.itemGrade.setImageResource(R.mipmap.grade_vip)
                                } else {
                                    binding.itemGrade.setImageResource(R.mipmap.grade_public)
                                }
                            }
                            mWalletBean?.let { it ->
                                binding.mineWithdrawal.text = it.data.preTodayFee//今日预估
                                binding.mineImminent.text = it.data.currentMonthFee //本月预估
                                binding.mineTotal.text = it.data.preMonthFee //上月预估
                                binding.mineWalletMoney.text = it.data.balanceFee + "元" //可提现
                            }
                        }else if (it.code == 602){//token失效(未登录)
                            binding.mineName.visibility = View.GONE
                            binding.mineLogin.visibility = View.VISIBLE
                            binding.mineInfo.visibility = View.GONE
                            GlideApp.loderImage(context, R.drawable.logo, binding.mineImage, 0, 0)
                            binding.mineBackground.setImageResource(R.mipmap.mine_imagebg_dafult)
                            binding.mineWithdrawal.text = "0"//今日预估
                            binding.mineImminent.text = "0" //本月预估
                            binding.mineTotal.text = "0" //上月预估
                            binding.mineWalletMoney.text = "0元" //可提现
                            binding.mineCopyContainer.visibility = View.GONE//复制邀请码
                            binding.itemGrade.setImageResource(R.mipmap.grade_public)
                        }else{//其他错误
                            binding.mineName.visibility = View.VISIBLE
                            binding.mineLogin.visibility = View.GONE
                            binding.mineInfo.visibility = View.VISIBLE
                            binding.mineName.text = SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickName)
                            if (!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickImg))) {
                                GlideApp.loderImage(context, SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickImg), binding.mineImage, R.mipmap.logo, 0)
                            }
                            binding.mineCopyContainer.visibility = View.VISIBLE//复制邀请码
                            binding.mineIntegral.text = "邀请码：" + SPUtils.getInstance(CommonDate.USER).getString(CommonDate.qrCode, "000000")
                            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userPoint, 0)
                        }
                        binding.mineLogin.setOnClickListener {
                            //跳转到登陆页面
                            mOnItem?.onLogin()
                        }
                        binding.mineName.setOnClickListener {
                            mOnItem?.onUserInfo()
                        }
                        binding.mineImage.setOnClickListener {
                            mOnItem?.onUserInfo()
                        }
                        binding.mineInfo.setOnClickListener {
                            mOnItem?.onUserInfo()
                        }
                        binding.mineCopy.setOnClickListener {
                            mOnItem?.onCopy(binding.mineIntegral.text.toString().replace("邀请码：", ""))
                        }
                        binding.mineWallet.setOnClickListener {
                            mOnItem?.onWallet()
                        }
                        binding.mineWalletBottomContainer.setOnClickListener {
                            mOnItem?.onWallet()
                        }
                    }

                }
            }
            HEAD2 -> {
                var head2ViewHolder : Head2ViewHolder = holder as Head2ViewHolder
                head2ViewHolder.run {
                    mBean?.let {
                        if (mAdListBeans.size > 0){
                            binding.viewPager.visibility = View.VISIBLE
                            pagerAdapter.setOnClickItem(object : KTPagerAdapter3.OnClickItem {
                                override fun onClickItem(postion: Int) {
                                    ShopJumpUtil.openBanner(context, mAdListBeans[postion].type.toString() + "",
                                            mAdListBeans[postion].objectId, mAdListBeans[postion].name,
                                            mAdListBeans[postion].source)
                                }
                            })
                            adListBeans.clear()
                            adListBeans.addAll(mAdListBeans)
                            initBanner()
                        }else{
                            binding.viewPager.visibility = View.GONE
                        }
                        if (it.code == 0) {//请求成功
                            binding.mineTeamCount.text = it.data.teamCount
                            if (it.data.level == 0){
                                binding.mineMemberContainer.visibility = View.VISIBLE
                            }else{
                                binding.mineMemberContainer.visibility = View.GONE
                            }
                        }else if (it.code == 602) {//token失效(未登录)
                            binding.mineTeamCount.text = "0"
                            binding.mineMemberContainer.visibility = View.VISIBLE
                        }else{
                            binding.mineTeamCount.text = "0"
                            binding.mineMemberContainer.visibility = View.VISIBLE
                        }
                        binding.mineMember.setOnClickListener {
                            mOnItem?.onMember()
                        }
                        binding.mineWithdraw.setOnClickListener {
                            mOnItem?.onWithdraw()
                        }
                        binding.mineTeam.setOnClickListener {
                            mOnItem?.onTeam()
                        }
                        binding.mineOrder.setOnClickListener {
                            mOnItem?.onOrder()
                        }
                        binding.mineInvitation.setOnClickListener {
                            mOnItem?.onInvitation()
                        }
                    }
                }
            }
            HEAD3 -> {
                var head3ViewHolder : Head3ViewHolder = holder as Head3ViewHolder
                head3ViewHolder.run {
                    if (unMessage != 0) {
                        if (unMessage <= 99) {
                            mQBadgeView.setBadgeText("" + unMessage)
                        } else {
                            mQBadgeView.setBadgeText("99+")
                        }
                    } else {
                        mQBadgeView.hide(false)
                    }
                    mBean?.let {
                        if (it.code == 0) {//请求成功
                            if (it.data.pid == "0") {
                                binding.mineInvation.visibility = View.VISIBLE
                            } else {
                                binding.mineInvation.visibility = View.GONE
                            }
                        }else{
                            binding.mineInvation.visibility = View.GONE
                        }
                    }
                    binding.mineMessage.setOnClickListener {
                        mOnItem?.onMessage()
                    }
                    binding.mineFover.setOnClickListener {
                        mOnItem?.onFover()
                    }
                    binding.mineGift.setOnClickListener {
                        mOnItem?.onGift()
                    }
                    binding.mineSetting.setOnClickListener {
                        mOnItem?.onSetting()
                    }
                    binding.mineOpinion.setOnClickListener {
                        mOnItem?.onOpinion()
                    }
                    binding.mineMall.setOnClickListener {
                        mOnItem?.onMall()
                    }
                    binding.mineRule.setOnClickListener {
                        mOnItem?.onRule()
                    }
                    binding.mineInvation.setOnClickListener {
                        mOnItem?.onInvationDialog()
                    }
                    binding.mineRecovery.setOnClickListener {
                        mOnItem?.onOrderRecovery()
                    }
                }
            }
            CONTENT -> {
                var contentViewHolder : ViewHolder = holder as ViewHolder
                contentViewHolder.run {
                    var position = pos - 3
                    binding.date = mList[position]
                    binding.executePendingBindings()
                    binding.detailOtherPrice.setTv(true)
                    binding.detailOtherPrice.setColor(R.color.color_9D9D9D)
                    binding.itemImage.post {
                        val layoutParams = binding.itemImage.layoutParams
                        layoutParams.height = binding.itemImage.width
                        binding.itemImage.layoutParams = layoutParams
                    }
                    val layoutParams = binding.itemContainer.layoutParams as RelativeLayout.LayoutParams
                    if (position == 0 || position == 1){
                        layoutParams.topMargin = context.resources.getDimension(R.dimen.x20).toInt()
                    }else{
                        layoutParams.topMargin = context.resources.getDimension(R.dimen.x3).toInt()
                    }
                    if (position % 2 != 0) {
                        layoutParams.leftMargin = context.resources.getDimension(R.dimen.x10).toInt()
                        layoutParams.rightMargin = context.resources.getDimension(R.dimen.x20).toInt()
                    } else {
                        layoutParams.leftMargin = context.resources.getDimension(R.dimen.x20).toInt()
                        layoutParams.rightMargin = context.resources.getDimension(R.dimen.x10).toInt()
                    }
                    binding.itemContainer.layoutParams = layoutParams
                    val coupon = BigDecimal(mList[position].couponPrice).toDouble()
                    if (coupon == 0.0) {//没有优惠券
                        binding.detailCoupon.visibility = View.GONE
                    } else {
                        binding.detailCoupon.visibility = View.VISIBLE
                    }
                    val free = BigDecimal(mList[position].fee).toDouble()
                    if (free == 0.0) {//没有补贴
                        binding.detailFee.visibility = View.GONE
                    } else {
                        binding.detailFee.visibility = View.VISIBLE
                    }
                    if (coupon == 0.0 && free == 0.0) {
                        binding.detailOtherPrice.visibility = View.GONE
                    } else {
                        binding.detailOtherPrice.visibility = View.VISIBLE
                    }
                    itemView.setOnClickListener {
                        context.startActivity(Intent(context, TBShoppingDetailActivity::class.java)
                                .putExtra("otherGoodsId", mList[position].otherGoodsId)
                                .putExtra("source",mList[position].source)
                        )
                    }
                    binding.itemShare.setOnClickListener {
                        mOnItem?.onItemShare(position)
                    }
                }
            }
        }
    }

    inner class HeadViewHolder :RecyclerView.ViewHolder{

        var binding: ItemMineHead1Binding

        constructor(binding: ItemMineHead1Binding) : super(binding.root){
            this.binding = binding
            setStatusBarHight()
        }

        fun setStatusBarHight() {
            val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                val statusBarHeight = context.resources.getDimensionPixelSize(resourceId)
                val layoutParams = binding.statusBar.layoutParams
                layoutParams.height = statusBarHeight
            }
        }
    }

    inner class Head2ViewHolder : RecyclerView.ViewHolder, ViewPager.OnPageChangeListener {

        lateinit var binding: ItemMineHead2Binding
        //轮播图
        var pagerAdapter: KTPagerAdapter3
        var adListBeans : MutableList<EvaluationTabBean.DataBean.AdListBean>
        private var currentItem: Int = 0
        private var count : Int = 0
        private var mCallback : Handler.Callback = Handler.Callback {
            if (it.what == 100) {
                if (count > 1){
                    currentItem = currentItem % (count + 1) + 1
                    if (currentItem == 1) {
                        binding.viewPager.setCurrentItem(currentItem, false)
                        handler.sendEmptyMessage(100)
                    }else{
                        binding.viewPager.currentItem = currentItem
                        handler.sendEmptyMessageDelayed(100,5000)
                    }
                }
            }
            true
        }
        var handler = WeakRefHandler(mCallback, Looper.getMainLooper())

        constructor(binding: ItemMineHead2Binding) : super(binding.root){
            this.binding = binding
            pagerAdapter = KTPagerAdapter3(context)
            adListBeans = mutableListOf()
            pagerAdapter.setList(adListBeans)
            pagerAdapter.setImgCenter(false)
            binding.viewPager.adapter = pagerAdapter
            binding.viewPager.addOnPageChangeListener(this)
            binding.viewPager.setOnTouchListener(object : TouchViewPager.OnTouchListener {
                override fun startAutoPlay() {
                    handler.removeCallbacksAndMessages(null)
                    handler.sendEmptyMessageDelayed(100,5000)
                }

                override fun stopAutoPlay() {
                    handler.removeCallbacksAndMessages(null)
                }

            })
        }

        override fun onPageScrollStateChanged(state: Int) {
            when (state) {
                0//No operation
                -> if (currentItem === 0) {
                    binding.viewPager.setCurrentItem(count, false)
                } else if (currentItem === count + 1) {
                    binding.viewPager.setCurrentItem(1, false)
                }
                1//start Sliding
                -> if (currentItem === count + 1) {
                    binding.viewPager.setCurrentItem(1, false)
                } else if (currentItem === 0) {
                    binding.viewPager.setCurrentItem(count, false)
                }
                2//end Sliding
                -> {
                }
            }
        }

        override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}

        override fun onPageSelected(position: Int) {
            currentItem = position
        }

        fun initBanner() {
            if (mAdListBeans.size > 1) {
                count = mAdListBeans.size - 2
            } else {
                count = mAdListBeans.size
            }
            handler.removeCallbacksAndMessages(null)//刷新时，要删除handler的请求
            handler.sendEmptyMessageDelayed(100,5000)
            binding.viewPager.setCurrentItem(1,false)
            pagerAdapter.notifyDataSetChanged()
        }
    }

    inner class Head3ViewHolder : RecyclerView.ViewHolder{

        var binding: ItemMineHead3Binding
        var mQBadgeView: QBadgeView

        constructor(binding: ItemMineHead3Binding) : super(binding.root){
            this.binding = binding
            mQBadgeView = QBadgeView(context)
            mQBadgeView.bindTarget(binding.mineMessageImg)
                    .setBadgeTextSize(8f, true)
                    .setBadgeGravity(Gravity.END or Gravity.TOP)
                    .setBadgePadding(2f, true)
                    .setGravityOffset(0f, 0f, true)
                    .setBadgeBackgroundColor(-0x10000)
        }
    }

    class ViewHolder : RecyclerView.ViewHolder{

        var binding :ItemUserlikeBinding

        constructor(binding :ItemUserlikeBinding) : super(binding.root){
            this.binding = binding
        }
    }

    interface OnItem {
        fun onItemShare(position: Int)
        fun onLogin()//跳转到登陆页面
        fun onUserInfo()//跳转到用户主页
        fun onCopy(code: String)//复制邀请码
        fun onWallet()//进入钱包

        fun onMember()//进入会员
        fun onWithdraw()//进入提现
        fun onTeam()//进入团队
        fun onOrder() //进入我的订单
        fun onInvitation() //进入邀请页面

        fun onMessage()//消息通知
        fun onFover()//收藏夹
        fun onGift()//福利兑换
        fun onSetting()//设置
        fun onOpinion()//反馈
        fun onMall()//极币商城
        fun onRule()//任务中心
        fun onInvationDialog()//邀请码dialog
        fun onOrderRecovery() //订单找回
    }
}