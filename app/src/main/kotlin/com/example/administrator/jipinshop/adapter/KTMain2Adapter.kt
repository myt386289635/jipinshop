package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.CountDownTimer
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.blankj.utilcode.util.SPUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.WebActivity
import com.example.administrator.jipinshop.activity.cheapgoods.CheapBuyActivity
import com.example.administrator.jipinshop.activity.home.home.HomeNewActivity
import com.example.administrator.jipinshop.activity.home.hot.HomeHotActivity
import com.example.administrator.jipinshop.activity.home.seckill.SeckillActivity
import com.example.administrator.jipinshop.activity.login.LoginActivity
import com.example.administrator.jipinshop.activity.member.buy.MemberBuyActivity
import com.example.administrator.jipinshop.activity.member.zero.ZeroActivity
import com.example.administrator.jipinshop.activity.newpeople.NewFreeActivity
import com.example.administrator.jipinshop.activity.shoppingdetail.tbshoppingdetail.TBShoppingDetailActivity
import com.example.administrator.jipinshop.bean.SuccessBean
import com.example.administrator.jipinshop.bean.TBSreachResultBean
import com.example.administrator.jipinshop.bean.TbkIndexBean
import com.example.administrator.jipinshop.databinding.*
import com.example.administrator.jipinshop.fragment.home.main.tab.CommonTabFragment
import com.example.administrator.jipinshop.netwrok.RetrofitModule
import com.example.administrator.jipinshop.util.DistanceHelper
import com.example.administrator.jipinshop.util.ShopJumpUtil
import com.example.administrator.jipinshop.util.UmApp.AppStatisticalUtil
import com.example.administrator.jipinshop.util.sp.CommonDate
import com.example.administrator.jipinshop.view.dialog.DialogUtil
import com.example.administrator.jipinshop.view.glide.GlideApp
import com.google.gson.Gson
import com.trello.rxlifecycle2.LifecycleTransformer
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import java.math.BigDecimal

/**
 * @author 莫小婷
 * @create 2020/4/20
 * @Describe
 */
class KTMain2Adapter : RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private val HEAD1 = 1
    private val HEAD2 = 2
    private val HEAD3 = 3
    private val HEAD4 = 4
    private val HEAD5 = 5
    private val CONTENT = 6 //今日推荐列表

    private var mContext: Context
    private var mList: MutableList<TBSreachResultBean.DataBean> //今日推荐列表
    private var mBean: TbkIndexBean? = null //全部数据
    private lateinit var mOnItem: OnItem
    private lateinit var appStatisticalUtil: AppStatisticalUtil
    private lateinit var transformer : LifecycleTransformer<SuccessBean>
    private lateinit var mPagerAdapter: HomePageAdapter
    //用于退出 Activity,避免 Countdown，造成资源浪费。
    private var countDownCounters: SparseArray<CountDownTimer>

    constructor(list: MutableList<TBSreachResultBean.DataBean>, context: Context){
        mList = list
        mContext = context
        this.countDownCounters = SparseArray()
    }

    fun setDate(bean: TbkIndexBean?){
        mBean = bean
    }

    fun setOnClick(onItem: OnItem){
        mOnItem = onItem
    }

    fun setAppStatisticalUtil(appStatisticalUtil : AppStatisticalUtil){
        this.appStatisticalUtil = appStatisticalUtil
    }

    fun setTransformer(transformer : LifecycleTransformer<SuccessBean>){
        this.transformer = transformer
    }

    fun setPagerAdapter(pagerAdapter: HomePageAdapter) {
        mPagerAdapter = pagerAdapter
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> HEAD1
            1 -> HEAD2
            2 -> HEAD3
            3 -> HEAD4
            4 -> HEAD5
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
            HEAD1 ->{
                var itemMainOneBinding = DataBindingUtil.inflate<ItemMain2OneBinding>(LayoutInflater.from(mContext), R.layout.item_main2_one, viewGroup, false)
                holder = OneViewHolder(itemMainOneBinding)
            }
            HEAD2 ->{
                var itemMain2TwoBinding = DataBindingUtil.inflate<ItemMain2TwoBinding>(LayoutInflater.from(mContext), R.layout.item_main2_two, viewGroup, false)
                holder = TwoViewHolder(itemMain2TwoBinding)
            }
            HEAD3 ->{
                var itemMain2ThreeBinding = DataBindingUtil.inflate<ItemMain2ThreeBinding>(LayoutInflater.from(mContext), R.layout.item_main2_three, viewGroup, false)
                holder = TreeViewHolder(itemMain2ThreeBinding)
            }
            HEAD4 ->{
                var itemMain2ForeBinding = DataBindingUtil.inflate<ItemMain2ForeBinding>(LayoutInflater.from(mContext), R.layout.item_main2_fore, viewGroup, false)
                holder = ForeViewHolder(itemMain2ForeBinding)
            }
            HEAD5 ->{
                var itemMain2FiveBinding = DataBindingUtil.inflate<ItemMain2FiveBinding>(LayoutInflater.from(mContext), R.layout.item_main2_five, viewGroup, false)
                holder = FiveViewHolder(itemMain2FiveBinding)
            }
            CONTENT ->{
                var binding1 = DataBindingUtil.inflate<ItemSreachOneBinding>(LayoutInflater.from(mContext), R.layout.item_sreach_one, viewGroup, false)
                holder = ContentViewHolder(binding1)
            }
        }
        return holder!!
    }

    override fun getItemCount(): Int {
        return if (mBean == null){
            0
        }else{
            mList.size + 5
        }
    }

    override fun onBindViewHolder(holder : RecyclerView.ViewHolder, position: Int) {
        var type = getItemViewType(position)
        when(type){
            HEAD1 ->{
                var oneViewHolder : OneViewHolder = holder as OneViewHolder
                oneViewHolder.run {
                    mBean?.let {
                        //顶部宫格
                        mTopTabs.clear()
                        mTopTabs.addAll(it.data.boxList)
                        mTopAdapter.notifyDataSetChanged()
                        //九宫格
                        tabs.clear()
                        for (i in it.data.boxCategoryList.indices){
                            tabs.add(it.data.boxCategoryList[i].categoryTitle)
                        }
                        tabAdapter.notifyDataSetChanged()
                        initViewPager(it.data.boxCategoryList)
                    }
                }
            }
            HEAD2 ->{
                var twoViewHolder: TwoViewHolder  = holder as TwoViewHolder
                twoViewHolder.run {
                    binding.user = mBean!!.data.newUser
                    //新人
                    for (i in mBean!!.data.allowanceGoodsList.indices) {
                        when (i) {
                            0 -> {
                                binding.new1 = mBean!!.data.allowanceGoodsList[0]
                            }
                            1 -> {
                                binding.new2 = mBean!!.data.allowanceGoodsList[1]
                            }
                            2 -> {
                                binding.new3 = mBean!!.data.allowanceGoodsList[2]
                            }
                        }
                    }
                    binding.executePendingBindings()
                    //倒计时
                    var timer: Long = 0
                    timer = (mBean!!.data.userEndTime * 1000) - System.currentTimeMillis()
                    var countDownTimer :CountDownTimer? = countDownCounters.get(binding.mainNewpeople.hashCode())
                    countDownTimer?.cancel()
                    if (timer > 0) {
                        countDownTimer = object : CountDownTimer(timer, 1000) {
                            override fun onTick(millisUntilFinished: Long) {
                                val ss = 1000
                                val mi = ss * 60
                                val hh = mi * 60
                                val dd = hh * 24
                                val day = millisUntilFinished / dd
                                val hour = (millisUntilFinished - day * dd) / hh
                                val minute = (millisUntilFinished - hour * hh - day * dd) / mi
                                val second = (millisUntilFinished - hour * hh - minute * mi - day * dd) / ss

                                binding.timeHour.text = "" + ((day * 24) + hour)
                                binding.timeMinute.text = "" + minute
                                binding.timeSecond.text = "" + second
                            }

                            override fun onFinish() {
                                binding.timeHour.text = "0"
                                binding.timeMinute.text = "0"
                                binding.timeSecond.text = "0"
                            }
                        }.start()
                        countDownCounters.put(binding.mainNewpeople.hashCode(), countDownTimer)
                    }else{
                        binding.timeHour.text = "0"
                        binding.timeMinute.text = "0"
                        binding.timeSecond.text = "0"
                    }
                    //广告
                    userList.clear()
                    userList.addAll(mBean!!.data.messageList)
                    initUser()
                    binding.mainNewpeople.setOnClickListener {
                        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                            mContext.startActivity(Intent(mContext, LoginActivity::class.java))
                            return@setOnClickListener
                        }
                        appStatisticalUtil.addEvent("shouye_xinren",transformer)
                        mContext.startActivity(Intent(mContext, NewFreeActivity::class.java))
                    }
                    binding.marqueeContainer.setOnClickListener {
                        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                            mContext.startActivity(Intent(mContext, LoginActivity::class.java))
                            return@setOnClickListener
                        }
                        appStatisticalUtil.addEvent("shouye_pmd",transformer)
                        mContext.startActivity(Intent(mContext, WebActivity::class.java)
                                .putExtra(WebActivity.url, RetrofitModule.H5_URL + "newZn.html")
                                .putExtra(WebActivity.title, "极品城省钱攻略")
                                .putExtra(WebActivity.isShare,true)
                                .putExtra(WebActivity.shareTitle,"如何查找淘宝隐藏优惠券及下单返利？")
                                .putExtra(WebActivity.shareContent,"淘宝天猫90%的商品都能省，同时还有高额返利，淘好物，更省钱！")
                                .putExtra(WebActivity.shareImage,"https://jipincheng.cn/shengqian.png")
                        )
                    }
                }
            }
            HEAD3 ->{
                var threeViewHolder: TreeViewHolder = holder as TreeViewHolder
                threeViewHolder.run {
                    Glide.with(mContext)
                            .load(mBean!!.data.ad2.img)
                            .listener(object : RequestListener<Drawable> {
                                override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                                    return false
                                }

                                override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                                    val width = resource.intrinsicWidth.toDouble()
                                    val height = resource.intrinsicHeight.toDouble()
                                    val screenWidth = DistanceHelper.getAndroiodScreenwidthPixels(mContext).toDouble()
                                    val layoutParams = binding.itemImage.layoutParams as RelativeLayout.LayoutParams
                                    layoutParams.width = screenWidth.toInt() - (mContext.resources.getDimension(R.dimen.x30).toInt() * 2)
                                    layoutParams.height = (height / width * layoutParams.width).toInt()
                                    binding.itemImage.layoutParams = layoutParams
                                    return false
                                }
                            })
                            .into(binding.itemImage)
                    actionList.clear()
                    actionList.addAll(mBean!!.data.activityList)
                    adapter.notifyDataSetChanged()
                    binding.itemRv.setBackgroundColor(Color.parseColor("#" + mBean!!.data.ad2.color))
                    binding.itemImage.setOnClickListener {
                        appStatisticalUtil.addEvent("shouye_banner.huodong",transformer)
                        ShopJumpUtil.openBanner(mContext,mBean!!.data.ad2.type,
                                mBean!!.data.ad2.objectId,mBean!!.data.ad2.name,
                                mBean!!.data.ad2.source,mBean!!.data.ad2.remark)
                    }
                }
            }
            HEAD4 ->{
                var foreViewHolder: ForeViewHolder = holder as ForeViewHolder
                foreViewHolder.run {
                    for (i in mBean!!.data.allowanceGoodsList2.indices) {
                        when (i) {
                            0 -> {
                                binding.fee1 = mBean!!.data.allowanceGoodsList2[0]
                            }
                            1 -> {
                                binding.fee2 = mBean!!.data.allowanceGoodsList2[1]
                            }
                        }
                    }
                    for (i in mBean!!.data.hotGoodsList.indices) {
                        when (i) {
                            0 -> {
                                binding.hot1 = mBean!!.data.hotGoodsList[0]
                            }
                            1 -> {
                                binding.hot2 = mBean!!.data.hotGoodsList[1]
                            }
                        }
                    }
                    for (i in mBean!!.data.allowanceGoodsList3.indices){
                        when (i) {
                            0 -> {
                                binding.zero1 = mBean!!.data.allowanceGoodsList3[0]
                            }
                            1 -> {
                                binding.zero2 = mBean!!.data.allowanceGoodsList3[1]
                            }
                        }
                    }
                    for (i in mBean!!.data.seckillGoodsList.indices){
                        when (i) {
                            0 -> {
                                binding.ms1 = mBean!!.data.seckillGoodsList[0]
                            }
                            1 -> {
                                binding.ms2 = mBean!!.data.seckillGoodsList[1]
                            }
                        }
                    }
                    //倒计时
                    var timer: Long = 0
                    timer = (mBean!!.data.seckillEndTime * 1000) - System.currentTimeMillis()
                    var countDownTimer :CountDownTimer? = countDownCounters.get(binding.msContainer.hashCode())
                    countDownTimer?.cancel()
                    if (timer > 0) {
                        countDownTimer = object : CountDownTimer(timer, 1000) {
                            override fun onTick(millisUntilFinished: Long) {
                                val ss = 1000
                                val mi = ss * 60
                                val hh = mi * 60
                                val dd = hh * 24
                                val day = millisUntilFinished / dd
                                val hour = (millisUntilFinished - day * dd) / hh
                                val minute = (millisUntilFinished - hour * hh - day * dd) / mi
                                val second = (millisUntilFinished - hour * hh - minute * mi - day * dd) / ss

                                var shour = ((day * 24) + hour) * 60
                                binding.mainMsMinutes.text = "" + (minute + shour)
                                binding.mainMsSecond.text = "" + second
                            }

                            override fun onFinish() {
                                binding.mainMsMinutes.text = "0"
                                binding.mainMsSecond.text = "0"
                            }
                        }.start()
                        countDownCounters.put(binding.msContainer.hashCode(), countDownTimer)
                    }else {
                        binding.mainMsMinutes.text = "0"
                        binding.mainMsSecond.text = "0"
                    }
                    //初始化UI
                    binding.hotOneCost.setTv(true)
                    binding.hotOneCost.setColor(R.color.color_9D9D9D)
                    binding.hotTwoCost.setTv(true)
                    binding.hotTwoCost.setColor(R.color.color_9D9D9D)
                    binding.feeOneCost.setTv(true)
                    binding.feeOneCost.setColor(R.color.color_9D9D9D)
                    binding.feeTwoCost.setTv(true)
                    binding.feeTwoCost.setColor(R.color.color_9D9D9D)
                    binding.zeroOneCost.setTv(true)
                    binding.zeroOneCost.setColor(R.color.color_9D9D9D)
                    binding.zeroTwoCost.setTv(true)
                    binding.zeroTwoCost.setColor(R.color.color_9D9D9D)
                    binding.msOneCost.setTv(true)
                    binding.msOneCost.setColor(R.color.color_9D9D9D)
                    binding.msTwoCost.setTv(true)
                    binding.msTwoCost.setColor(R.color.color_9D9D9D)
                    binding.executePendingBindings()
                    binding.feeContainer.setOnClickListener {
                        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                            mContext.startActivity(Intent(mContext, LoginActivity::class.java))
                            return@setOnClickListener
                        }
                        appStatisticalUtil.addEvent("shouye_tehui",transformer)
                        mContext.startActivity(Intent(mContext, CheapBuyActivity::class.java))
                    }
                    binding.hotContainer.setOnClickListener {
                        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                            mContext.startActivity(Intent(mContext, LoginActivity::class.java))
                            return@setOnClickListener
                        }
                        mContext.startActivity(Intent(mContext, HomeHotActivity::class.java))
                    }
                    binding.zeroContainer.setOnClickListener {
                        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                            mContext.startActivity(Intent(mContext, LoginActivity::class.java))
                            return@setOnClickListener
                        }
                        if(mBean!!.data.level == 2){
                            mContext.startActivity(Intent(mContext, ZeroActivity::class.java))
                        }else {
                            DialogUtil.memberGuideDialog(mContext) { v12 ->
                                mContext.startActivity(Intent(mContext, HomeNewActivity::class.java)
                                        .putExtra("type", HomeNewActivity.member)
                                        .putExtra("isyear" , true)
                                )
                            }
                        }
                    }
                    binding.msContainer.setOnClickListener {
                        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                            mContext.startActivity(Intent(mContext, LoginActivity::class.java))
                            return@setOnClickListener
                        }
                        mContext.startActivity(Intent(mContext, SeckillActivity::class.java))
                    }
                }
            }
            HEAD5 ->{
                var fiveViewHolder: FiveViewHolder = holder as FiveViewHolder
                fiveViewHolder.run {
                    binding.itemTb.setOnClickListener {
                        selectTitle(0)
                        mOnItem.onSelect("2")
                    }
                    binding.itemJd.setOnClickListener {
                        selectTitle(1)
                        mOnItem.onSelect("1")
                    }
                    binding.itemPdd.setOnClickListener {
                        selectTitle(2)
                        mOnItem.onSelect("4")
                    }
                }
            }
            CONTENT ->{
                var viewHolder: ContentViewHolder = holder as ContentViewHolder
                viewHolder.run {
                    var pos = position - 5
                    binding.date = mList[pos]
                    binding.executePendingBindings()
                    val coupon1 = BigDecimal(mList[pos].couponPrice).toDouble()
                    val free1 = BigDecimal(mList[pos].fee).toDouble()
                    binding.itemLine.visibility = View.GONE
                    binding.itemLineContainer.visibility = View.GONE
                    binding.itemGridContainer.visibility = View.VISIBLE
                    binding.itemGridImage.post {
                        val layoutParams = binding.itemGridImage.layoutParams
                        layoutParams.height = binding.itemGridImage.width
                        binding.itemGridImage.layoutParams = layoutParams
                    }
                    val layoutParams = binding.itemGridContainer1.layoutParams as RelativeLayout.LayoutParams
                    if (pos % 2 != 0) {
                        layoutParams.leftMargin = mContext.resources.getDimension(R.dimen.x10).toInt()
                        layoutParams.rightMargin = mContext.resources.getDimension(R.dimen.x20).toInt()
                    } else {
                        layoutParams.leftMargin = mContext.resources.getDimension(R.dimen.x20).toInt()
                        layoutParams.rightMargin = mContext.resources.getDimension(R.dimen.x10).toInt()
                    }
                    binding.itemGridContainer1.layoutParams = layoutParams
                    binding.detailGirdOtherPrice.setTv(true)
                    binding.detailGirdOtherPrice.setColor(R.color.color_9D9D9D)
                    if (coupon1 == 0.0) {//没有优惠券
                        binding.detailGirdCoupon.visibility = View.GONE
                    } else {
                        binding.detailGirdCoupon.visibility = View.VISIBLE
                    }
                    if (free1 == 0.0) {//没有补贴
                        binding.detailGirdFee.visibility = View.GONE
                    } else {
                        binding.detailGirdFee.visibility = View.VISIBLE
                    }
                    if (coupon1 == 0.0 && free1 == 0.0) {
                        binding.detailGirdOtherPrice.visibility = View.GONE
                    } else {
                        binding.detailGirdOtherPrice.visibility = View.VISIBLE
                    }
                    binding.itemShare.setOnClickListener { v ->
                        mOnItem.onItemShare(pos)
                    }
                    itemView.setOnClickListener { v ->
                        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                            mContext.startActivity(Intent(mContext, LoginActivity::class.java))
                            return@setOnClickListener
                        }
                        if (mList[pos].source == "1"){
                            appStatisticalUtil.addEvent("shouye_jingdong." + (pos+1),transformer)
                        }else if (mList[pos].source == "4"){
                            appStatisticalUtil.addEvent("shouye_pinduoduo." + (pos+1),transformer)
                        }else{
                            appStatisticalUtil.addEvent("shouye_taobao." + (pos+1),transformer)
                        }
                        mContext.startActivity(Intent(mContext, TBShoppingDetailActivity::class.java)
                                .putExtra("otherGoodsId", mList[pos].otherGoodsId)
                                .putExtra("source",mList[pos].source)
                        )
                    }
                }
            }
        }
    }

    inner class OneViewHolder : RecyclerView.ViewHolder {

        lateinit var binding : ItemMain2OneBinding
        //顶部宫格
        var mTopTabs: MutableList<TbkIndexBean.DataBean.BoxCategoryListBean.ListBean>
        var mTopAdapter: KTMain2TopGridAdapter
        //九宫格
        var tabs: MutableList<String?>
        var tabAdapter: KTTabAdapter5
        var tabFragments: MutableList<Fragment>
        private var gridPoint : MutableList<ImageView>

        constructor(binding: ItemMain2OneBinding) : super(binding.root){
            this.binding = binding
            //顶部宫格
            mTopTabs = mutableListOf()
            binding.mainRv.layoutManager = GridLayoutManager(mContext,4)
            mTopAdapter = KTMain2TopGridAdapter(mContext,mTopTabs)
            binding.mainRv.adapter = mTopAdapter
            //九宫格
            var commonNavigator = CommonNavigator(mContext)
            tabs = mutableListOf()
            tabAdapter = KTTabAdapter5(tabs,binding.mainGrid,binding.mainMenu)
            commonNavigator.adapter = tabAdapter
            binding.mainMenu.navigator = commonNavigator
            gridPoint = mutableListOf()

            tabFragments = mutableListOf()
            mPagerAdapter.setFragments(tabFragments)
            binding.mainGrid.adapter = mPagerAdapter
            binding.mainGrid.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
                override fun onPageScrollStateChanged(state: Int) {}

                override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}

                override fun onPageSelected(position: Int) {
                    binding.mainMenu.onPageSelected(position)
                    binding.mainMenu.onPageScrolled(position,0.0F, 0)
                    for (i in gridPoint.indices) {
                        if (i == position) {
                            gridPoint[i].setImageResource(R.drawable.banner_down4)
                        } else {
                            gridPoint[i].setImageResource(R.drawable.banner_up4)
                        }
                    }
                }
            })
        }

        //初始化宫格
        fun initViewPager(list: MutableList<TbkIndexBean.DataBean.BoxCategoryListBean>) {
            tabFragments.clear()//清空数据
            gridPoint.clear()
            binding.point.removeAllViews()
            var date = Gson().toJson(mBean?.data,TbkIndexBean.DataBean::class.java)
            for (i in list.indices){
                tabFragments.add(CommonTabFragment.getInstance(date,i))
                var imageView = ImageView(mContext)
                gridPoint.add(imageView)
                if (i == binding.mainGrid.currentItem) {
                    imageView.setImageResource(R.drawable.banner_down4)
                } else {
                    imageView.setImageResource(R.drawable.banner_up4)
                }
                binding.point.addView(imageView)
            }
            mPagerAdapter.updateData(tabFragments)
        }
    }

    inner class TwoViewHolder : RecyclerView.ViewHolder {

        var binding: ItemMain2TwoBinding
        var userList: MutableList<TbkIndexBean.DataBean.MessageListBean>

        constructor(binding: ItemMain2TwoBinding) : super(binding.root){
            this.binding = binding
            userList = mutableListOf()
        }
        //初始化滚动用户
        fun initUser(){
            binding.viewFlipper.removeAllViews()
            for (i in userList.indices){
                var view = LayoutInflater.from(mContext).inflate(R.layout.view_flipper,null)
                var item_image = view.findViewById<ImageView>(R.id.item_image)
                var item_name = view.findViewById<TextView>(R.id.item_name)
                item_name.text = userList[i].content
                GlideApp.loderCircleImage(mContext,userList[i].avatar,item_image,0,0)
                binding.viewFlipper.addView(view)
            }
            binding.viewFlipper.setFlipInterval(3000)
            binding.viewFlipper.startFlipping()
        }
    }

    inner class TreeViewHolder : RecyclerView.ViewHolder {

        var binding: ItemMain2ThreeBinding
        var actionList: MutableList<TbkIndexBean.DataBean.ActivityListBean>
        var adapter: KTMain2ActionAdapter

        constructor(binding: ItemMain2ThreeBinding) : super(binding.root){
            this.binding = binding
            actionList = mutableListOf()
            var gridLayoutManager = GridLayoutManager(mContext,2)
            binding.itemRv.layoutManager = gridLayoutManager
            adapter = KTMain2ActionAdapter(mContext,actionList)
            adapter.setAppStatisticalUtil(appStatisticalUtil)
            adapter.setTransformer(transformer)
            binding.itemRv.adapter = adapter
            binding.itemRv.isNestedScrollingEnabled = false
        }

    }

    inner class ForeViewHolder : RecyclerView.ViewHolder{

        var binding: ItemMain2ForeBinding

        constructor(binding: ItemMain2ForeBinding) : super(binding.root){
            this.binding = binding
        }
    }

    inner class FiveViewHolder : RecyclerView.ViewHolder{

        var binding: ItemMain2FiveBinding
        var mTitle: MutableList<TextView>
        var mDec: MutableList<TextView>

        constructor(binding: ItemMain2FiveBinding) : super(binding.root){
            this.binding = binding

            mTitle = mutableListOf()
            mDec = mutableListOf()
            mTitle.add(binding.itemTbTitle)
            mTitle.add(binding.itemJdTitle)
            mTitle.add(binding.itemPddTitle)
            mDec.add(binding.itemTbDec)
            mDec.add(binding.itemJdDec)
            mDec.add(binding.itemPddDec)
        }

        fun selectTitle(position: Int){
            for (i in mTitle.indices){
                if (i  == position){
                    mTitle[i].setTextColor(mContext.resources.getColor(R.color.color_E25838))
                    mDec[i].setTextColor(mContext.resources.getColor(R.color.color_E25838))
                }else{
                    mTitle[i].setTextColor(mContext.resources.getColor(R.color.color_565252))
                    mDec[i].setTextColor(mContext.resources.getColor(R.color.color_9D9D9D))
                }
            }
        }
    }

    inner class ContentViewHolder : RecyclerView.ViewHolder{

        var binding: ItemSreachOneBinding

        constructor(binding: ItemSreachOneBinding) : super(binding.root){
            this.binding = binding
        }
    }

    interface OnItem{
        fun onItemShare(position: Int)
        fun onSelect(source : String)
    }
}