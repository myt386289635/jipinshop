package com.example.administrator.jipinshop.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.blankj.utilcode.util.SPUtils
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.shoppingdetail.tbshoppingdetail.TBShoppingDetailActivity
import com.example.administrator.jipinshop.bean.*
import com.example.administrator.jipinshop.databinding.*
import com.example.administrator.jipinshop.fragment.mine.group.KTMyGroupFragment
import com.example.administrator.jipinshop.util.NotificationUtil
import com.example.administrator.jipinshop.util.ShopJumpUtil
import com.example.administrator.jipinshop.util.WeakRefHandler
import com.example.administrator.jipinshop.util.sp.CommonDate
import com.example.administrator.jipinshop.view.glide.GlideApp
import com.example.administrator.jipinshop.view.viewpager.TouchViewPager
import com.google.gson.Gson
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
    private val HEAD4 = 5
    private val CONTENT = 4

    private var mList: MutableList<SimilerGoodsBean.DataBean>
    private lateinit var mAdListBeans: MutableList<EvaluationTabBean.DataBean.AdListBean> //轮播图列表
    private var context : Context
    private var mBean: UserInfoBean? = null
    private var mOnItem: OnItem? = null
    private var mWalletBean : MyWalletBean? = null
    private lateinit var teamAdapter: HomePageAdapter

    fun setWallet(bean : MyWalletBean?){
        mWalletBean = bean
    }

    fun setBean(bean : UserInfoBean?){
        mBean = bean
    }

    fun setOnItem(onItem: OnItem) {
        mOnItem = onItem
    }

    fun setTeamAdapter(pagerAdapter: HomePageAdapter) {
        teamAdapter = pagerAdapter
    }

    fun setAdList(adListBeans: MutableList<EvaluationTabBean.DataBean.AdListBean>){
        this.mAdListBeans = adListBeans
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
            3 -> HEAD4
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
            HEAD4 -> {
                var itemMineHead4Binding = DataBindingUtil.inflate<ItemMineHead4Binding>(LayoutInflater.from(context), R.layout.item_mine_head4, viewGroup, false)
                holder = Head4ViewHolder(itemMineHead4Binding)
            }
            CONTENT -> {
                var binding = DataBindingUtil.inflate<ItemUserlikeBinding>(LayoutInflater.from(context), R.layout.item_userlike, viewGroup, false)
                holder = ViewHolder(binding)
            }
        }
        return holder!!
    }

    override fun getItemCount(): Int {
        return mList.size + 4
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, pos: Int) {
        var type = getItemViewType(pos)
        when(type){
            HEAD1 -> {
                var head1ViewHolder : HeadViewHolder = holder as HeadViewHolder
                head1ViewHolder.run {
                    mBean?.let {
                        if (it.code == 0){//请求成功
                            binding.mineName.text = SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickName)
                            if (!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickImg))) {
                                GlideApp.loderImage(context, SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickImg), binding.mineImage, R.mipmap.logo, 0)
                            }
                            if (TextUtils.isEmpty(it.data.bgImg)) {
                                binding.mineBackground.setImageResource(R.mipmap.mine_imagebg_dafult)
                            } else {
                                GlideApp.loderImage(context, it.data.bgImg, binding.mineBackground, 0, 0)
                            }
                            if (it.data.level == 2) {//年卡
                                binding.itemGrade.setImageResource(R.mipmap.grade_partner)
                                binding.mineMemberTime.text = "到期：" + it.data.levelEndTime
                                binding.mineMemberTime.visibility = View.VISIBLE
                                binding.mineMemberOpen.text = "续费"
                            } else  if (it.data.level == 1){
                                binding.itemGrade.setImageResource(R.mipmap.grade_vip)
                                binding.mineMemberTime.text = "到期：" + it.data.levelEndTime
                                binding.mineMemberTime.visibility = View.VISIBLE
                                binding.mineMemberOpen.text = "续费"
                            }else {
                                binding.itemGrade.setImageResource(R.mipmap.grade_public)
                                binding.mineMemberTime.visibility = View.GONE
                                binding.mineMemberOpen.text = "开通会员"
                            }
                            mWalletBean?.let { bean ->
                                binding.mineMemberInfoTitle.text = bean.data.title1
                                binding.mineMemberInfoContent.text = bean.data.title2
                                GlideApp.loderImage(context,bean.data.img,binding.mineMemberInfo,0,0)
                            }
                        }else if (it.code == 602){//token失效(未登录)
                            GlideApp.loderImage(context, R.drawable.logo, binding.mineImage, 0, 0)
                            binding.mineBackground.setImageResource(R.mipmap.mine_imagebg_dafult)
                            binding.mineName.text = "登录拿返现"
                            binding.itemGrade.setImageResource(R.mipmap.grade_public)
                            binding.mineMemberTime.visibility = View.GONE
                            binding.mineMemberOpen.text = "开通会员"
                        }else{//其他错误
                            binding.mineName.text = SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickName)
                            if (!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickImg))) {
                                GlideApp.loderImage(context, SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickImg), binding.mineImage, R.mipmap.logo, 0)
                            }
                            if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.bgImg))) {
                                binding.mineBackground.setImageResource(R.mipmap.mine_imagebg_dafult)
                            } else {
                                GlideApp.loderImage(context, SPUtils.getInstance(CommonDate.USER).getString(CommonDate.bgImg), binding.mineBackground, R.mipmap.mine_imagebg_dafult, 0)
                            }
                            binding.itemGrade.setImageResource(R.mipmap.grade_public)
                            binding.mineMemberTime.visibility = View.GONE
                            binding.mineMemberOpen.text = "开通会员"
                            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userPoint, 0)
                        }
                        if (NotificationUtil.isNotificationEnabled(context)){
                            binding.mineNoticeContainer.visibility = View.INVISIBLE
                            binding.statusBar.setBackgroundColor(Color.TRANSPARENT)
                        }else {
                            binding.mineNoticeContainer.visibility = View.VISIBLE
                            binding.statusBar.setBackgroundResource(R.drawable.bg_mine_notice)
                        }
                        binding.mineNotice.setOnClickListener {
                            binding.mineNoticeContainer.visibility = View.INVISIBLE
                            binding.statusBar.setBackgroundColor(Color.TRANSPARENT)
                        }
                        binding.mineNoticeGo.setOnClickListener {
                            NotificationUtil.gotoSet(context as Activity)
                        }
                        binding.mineName.setOnClickListener {
                            mOnItem?.onUserInfo()
                        }
                        binding.mineImage.setOnClickListener {
                            mOnItem?.onUserInfo()
                        }
                        binding.mineMemberOpen.setOnClickListener {
                            mOnItem?.onMember()
                        }
                        binding.mineMemberInfoContainer.setOnClickListener {
                            mOnItem?.onMember()
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
                                            mAdListBeans[postion].source , mAdListBeans[postion].remark)
                                }
                            })
                            adListBeans.clear()
                            adListBeans.addAll(mAdListBeans)
                            initBanner()
                        }else{
                            binding.viewPager.visibility = View.GONE
                        }
                        if (it.code == 0) {//请求成功
                            mWalletBean?.let { bean ->
                                binding.mineWithdrawable.text = "${bean.data.balanceFee}元"
                                binding.mineWithdrawing.text = "${bean.data.preFee}元"
                                binding.mineButie.text = "${bean.data.allowance}元"
                                binding.mineJinbi.text = bean.data.point
                                //拼团信息
                                if (bean.data.groupList.size > 0){
                                    binding.mineTeamContainer.visibility = View.VISIBLE
                                    initViewPager(bean.data.groupList)
                                }else{
                                    binding.mineTeamContainer.visibility = View.GONE
                                }
                            }
                        }else{//未请求成功
                            binding.mineWithdrawable.text = "0元"
                            binding.mineWithdrawing.text = "0元"
                            binding.mineButie.text = "0元"
                            binding.mineJinbi.text = "0"
                            binding.mineTeamContainer.visibility = View.GONE
                        }
                        binding.mineWithdrawContainer.setOnClickListener {
                            mOnItem?.onWallet()
                        }
                        binding.mineWithdrawingContainer.setOnClickListener {
                            mOnItem?.onWallet()
                        }
                        binding.mineButieContainer.setOnClickListener {
                            mOnItem?.onCheapBuy()
                        }
                        binding.mineJinbiContainer.setOnClickListener {
                            mOnItem?.onRule()
                        }
                        binding.mineOrderAll.setOnClickListener {
                            mOnItem?.onOrder(0)
                        }
                        binding.mineOrderwill.setOnClickListener {
                            mOnItem?.onOrder(1)
                        }
                        binding.mineOrderaccount.setOnClickListener {
                            mOnItem?.onOrder(2)
                        }
                        binding.mineOrderinvalid.setOnClickListener {
                            mOnItem?.onOrder(3)
                        }
                    }
                }
            }
            HEAD3 -> {
                var head3ViewHolder : Head3ViewHolder = holder as Head3ViewHolder
                head3ViewHolder.run {
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
                    binding.mineBrowse.setOnClickListener {
                        mOnItem?.onMessage()
                    }
                    binding.mineFover.setOnClickListener {
                        mOnItem?.onFover()
                    }
                    binding.mineRecovery.setOnClickListener {
                        mOnItem?.onOrderRecovery()
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
                    binding.mineGift.setOnClickListener {
                        mOnItem?.onGift()
                    }
                    binding.mineTeam.setOnClickListener {
                        mOnItem?.onTeam()
                    }
                    binding.mineInvitation.setOnClickListener {
                        mOnItem?.onInvitation()
                    }
                    binding.mineWelfare.setOnClickListener {
                        mOnItem?.onWelfare()
                    }
                    binding.mineCourse.setOnClickListener {
                        mOnItem?.onCourse()
                    }
                    binding.mineInvation.setOnClickListener {
                        mOnItem?.onInvationDialog()
                    }
                    binding.mineServer.setOnClickListener {
                        mOnItem?.onServer()
                    }
                    binding.mineApplets.setOnClickListener {
                        mOnItem?.applets()
                    }
                }
            }
            HEAD4 -> {
                var head4ViewHolder : Head4ViewHolder = holder as Head4ViewHolder
                head4ViewHolder.run {
                    mBean?.let {
                        if (it.code == 0) {//请求成功
                            list.clear()
                            adList.clear()
                            mWalletBean?.let { bean ->
                                list.addAll(bean.data.adList)
                                if (bean.data.imgList.size > 0){
                                    mBinding.mineAdRV.visibility = View.VISIBLE
                                    adList.addAll(bean.data.imgList)
                                }else{
                                    mBinding.mineAdRV.visibility = View.GONE
                                }
                            }
                            adapter.notifyDataSetChanged()
                            adAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
            CONTENT -> {
                var contentViewHolder : ViewHolder = holder as ViewHolder
                contentViewHolder.run {
                    var position = pos - 4
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
        //成团数据
        var teamFragments : MutableList<Fragment>
        private var teamPoint : MutableList<ImageView>

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
            //成团数据
            teamFragments = mutableListOf()
            teamPoint = mutableListOf()
            teamAdapter.setFragments(teamFragments)
            binding.mineTeamVP.adapter = teamAdapter
            binding.mineTeamVP.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
                override fun onPageScrollStateChanged(p0: Int) {}

                override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}

                override fun onPageSelected(position: Int) {
                    for (i in teamPoint.indices) {
                        if (i == position) {
                            teamPoint[i].setImageResource(R.drawable.banner_down2)
                        } else {
                            teamPoint[i].setImageResource(R.drawable.banner_up2)
                        }
                    }
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
            binding.viewPager.offscreenPageLimit = mAdListBeans.size - 1//防止图片重叠的情况
            pagerAdapter.notifyDataSetChanged()
        }

        //初始化成团数据
        fun initViewPager(list: MutableList<MyWalletBean.DataBean.GroupListBean>) {
            teamFragments.clear()//清空数据
            teamPoint.clear()
            binding.mineTeamPoint.removeAllViews()
            var date = Gson().toJson(mWalletBean?.data,MyWalletBean.DataBean::class.java)
            for (i in list.indices){
                teamFragments.add(KTMyGroupFragment.getInstance(date,i))
                var imageView = ImageView(context)
                teamPoint.add(imageView)
                if (i == binding.mineTeamVP.currentItem) {
                    imageView.setImageResource(R.drawable.banner_down2)
                } else {
                    imageView.setImageResource(R.drawable.banner_up2)
                }
                var layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                layoutParams.leftMargin = context.resources.getDimension(R.dimen.x4).toInt()
                layoutParams.rightMargin = context.resources.getDimension(R.dimen.x4).toInt()
                binding.mineTeamPoint.addView(imageView,layoutParams)
            }
            teamAdapter.updateData(teamFragments)
        }
    }

    inner class Head3ViewHolder : RecyclerView.ViewHolder{

        var binding: ItemMineHead3Binding

        constructor(binding: ItemMineHead3Binding) : super(binding.root){
            this.binding = binding
        }
    }

    inner class Head4ViewHolder : RecyclerView.ViewHolder{

        var mBinding: ItemMineHead4Binding
        //精选活动
        var list: MutableList<MyWalletBean.DataBean.AdListBean>
        var adapter: KTMineGirdAdapter
        //我的页面广告
        var adList : MutableList<TbkIndexBean.DataBean.Ad1ListBean>
        var adAdapter: KTMineAdAdapter

        constructor(binding: ItemMineHead4Binding) : super(binding.root){
            this.mBinding = binding
            //精选活动
            list = mutableListOf()
            binding.mineActionRv.layoutManager = GridLayoutManager(context,4)
            adapter = KTMineGirdAdapter(list,context)
            binding.mineActionRv.adapter = adapter
            //我的页面广告
            adList = mutableListOf()
            binding.mineAdRV.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            adAdapter = KTMineAdAdapter(context,adList)
            binding.mineAdRV.adapter = adAdapter
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

        fun onUserInfo()//跳转到用户主页
        fun onMember()//进入会员页面

        fun onWallet()//进入我的收益
        fun onCheapBuy() //特惠购
        fun onOrder(status: Int) //我的订单

        fun onMessage()//浏览足迹
        fun onFover()//收藏夹
        fun onGift()//福利兑换
        fun onSetting()//设置
        fun onOpinion()//反馈
        fun onMall()//极币商城
        fun onRule()//极币中心
        fun onInvationDialog()//邀请码dialog
        fun onOrderRecovery() //订单找回
        fun onTeam()//进入团队
        fun onInvitation() //进入邀请页面
        fun onWelfare() //官方福利群
        fun onCourse() //进入省钱教程
        fun onServer() //官方客服
        fun applets() //小程序
    }
}