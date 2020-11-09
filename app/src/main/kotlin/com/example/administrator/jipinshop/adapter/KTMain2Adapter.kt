package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.WebActivity
import com.example.administrator.jipinshop.activity.cheapgoods.CheapBuyActivity
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
import com.example.administrator.jipinshop.util.WeakRefHandler
import com.example.administrator.jipinshop.view.glide.GlideApp
import com.example.administrator.jipinshop.view.viewpager.TouchViewPager
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
    private lateinit var mAdListBeans: MutableList<TbkIndexBean.DataBean.Ad1ListBean> //轮播图列表
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

    fun setAdList(adListBeans: MutableList<TbkIndexBean.DataBean.Ad1ListBean>){
        this.mAdListBeans = adListBeans
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
                        gridLayoutManager.spanCount
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
                    pagerAdapter.setOnClickItem(object : KTPagerAdapter.OnClickItem {
                        override fun onClickItem(postion: Int) {
                            appStatisticalUtil.addEvent("shouye_banner." + (toRealPosition(postion) + 1),transformer)
                            ShopJumpUtil.openBanner(mContext,adListBeans[postion].type,
                                    adListBeans[postion].objectId,adListBeans[postion].name,
                                    adListBeans[postion].source)
                        }
                    })
                    adListBeans.clear()
                    adListBeans.addAll(mAdListBeans)
                    initBanner()
                    //九宫格
                    mBean?.let {
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
                    //老人
                    for (i in mBean!!.data.allowanceGoodsList2.indices) {
                        when (i) {
                            0 -> {
                                binding.old1 = mBean!!.data.allowanceGoodsList2[0]
                            }
                            1 -> {
                                binding.old2 = mBean!!.data.allowanceGoodsList2[1]
                            }
                            2 -> {
                                binding.old3 = mBean!!.data.allowanceGoodsList2[2]
                            }
                            3 -> {
                                binding.old4 = mBean!!.data.allowanceGoodsList2[3]
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
                        appStatisticalUtil.addEvent("shouye_xinren",transformer)
                        mContext.startActivity(Intent(mContext, NewFreeActivity::class.java))
                    }
                    binding.mainOldpeople.setOnClickListener {
                        appStatisticalUtil.addEvent("shouye_tehui",transformer)
                        mContext.startActivity(Intent(mContext, CheapBuyActivity::class.java))
                    }
                    binding.marqueeContainer.setOnClickListener {
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
                                mBean!!.data.ad2.source)
                    }
                }
            }
            HEAD4 ->{
                var foreViewHolder: ForeViewHolder = holder as ForeViewHolder
                foreViewHolder.run {
                    hotShopList.clear()
                    hotShopList.addAll(mBean!!.data.hotGoodsList)
                    adapter.notifyDataSetChanged()
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
                    binding.itemLine.setBackgroundColor(mContext.resources.getColor(R.color.color_DEDEDE))
                    var lineLayout =  binding.itemLine.layoutParams as LinearLayout.LayoutParams
                    lineLayout.height = 1
                    lineLayout.rightMargin = mContext.resources.getDimension(R.dimen.x30).toInt()
                    lineLayout.leftMargin = mContext.resources.getDimension(R.dimen.x30).toInt()
                    binding.itemLine.layoutParams = lineLayout
                    if (pos == 0){
                        binding.itemLine.visibility = View.GONE
                    }else{
                        binding.itemLine.visibility = View.VISIBLE
                    }
                    binding.itemLineContainer.visibility = View.VISIBLE
                    binding.itemGridContainer.visibility = View.GONE
                    binding.detailOtherPrice.setTv(true)
                    binding.detailOtherPrice.setColor(R.color.color_9D9D9D)
                    if (coupon1 == 0.0) {//没有优惠券
                        binding.detailCoupon.visibility = View.GONE
                    } else {
                        binding.detailCoupon.visibility = View.VISIBLE
                    }
                    if (free1 == 0.0) {//没有补贴
                        binding.detailFee.visibility = View.GONE
                    } else {
                        binding.detailFee.visibility = View.VISIBLE
                    }
                    if (coupon1 == 0.0 && free1 == 0.0) {
                        binding.detailOtherPrice.visibility = View.GONE
                    } else {
                        binding.detailOtherPrice.visibility = View.VISIBLE
                    }
                    binding.itemShare.setOnClickListener { v ->
                        mOnItem.onItemShare(pos)
                    }
                    itemView.setOnClickListener { v ->
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

    inner class OneViewHolder : RecyclerView.ViewHolder, ViewPager.OnPageChangeListener {

        lateinit var binding : ItemMain2OneBinding
        //轮播图
        var pagerAdapter: KTPagerAdapter
        var point: MutableList<ImageView>
        var adListBeans : MutableList<TbkIndexBean.DataBean.Ad1ListBean>
        private var currentItem: Int = 0
        private var count : Int = 0
        private var mCallback : Handler.Callback = Handler.Callback {
            if (it.what == 100) {
                if (count > 1){
                    currentItem = currentItem % (count + 1) + 1
                    if (currentItem == 1) {
                        binding.mainViewpager.setCurrentItem(currentItem, false)
                        handler.sendEmptyMessage(100)
                    }else{
                        binding.mainViewpager.currentItem = currentItem
                        handler.sendEmptyMessageDelayed(100,5000)
                    }
                }
            }
            true
        }
        var handler = WeakRefHandler(mCallback, Looper.getMainLooper())
        //九宫格
        var tabs: MutableList<String?>
        var tabAdapter: KTTabAdapter5
        var tabFragments: MutableList<Fragment>
        private var gridPoint : MutableList<ImageView>

        constructor(binding: ItemMain2OneBinding) : super(binding.root){
            this.binding = binding
            pagerAdapter = KTPagerAdapter(mContext)
            point = mutableListOf()
            adListBeans = mutableListOf()
            pagerAdapter.setList(adListBeans)
            pagerAdapter.setPoint(point)
            pagerAdapter.setImgCenter(true)
            binding.mainViewpager.adapter = pagerAdapter
            binding.mainViewpager.addOnPageChangeListener(this)
            binding.mainViewpager.setOnTouchListener(object : TouchViewPager.OnTouchListener {
                override fun startAutoPlay() {
                    handler.removeCallbacksAndMessages(null)
                    handler.sendEmptyMessageDelayed(100,5000)
                }

                override fun stopAutoPlay() {
                    handler.removeCallbacksAndMessages(null)
                }

            })
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

        override fun onPageScrollStateChanged(state: Int) {
            when (state) {
                0//No operation
                -> if (currentItem === 0) {
                    binding.mainViewpager.setCurrentItem(count, false)
                } else if (currentItem === count + 1) {
                    binding.mainViewpager.setCurrentItem(1, false)
                }
                1//start Sliding
                -> if (currentItem === count + 1) {
                    binding.mainViewpager.setCurrentItem(1, false)
                } else if (currentItem === 0) {
                    binding.mainViewpager.setCurrentItem(count, false)
                }
                2//end Sliding
                -> {
                }
            }
        }

        override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}

        override fun onPageSelected(position: Int) {
            for (i in point.indices) {
                if (i == toRealPosition(position)) {
                    point[i].setImageResource(R.drawable.banner_down)
                } else {
                    point[i].setImageResource(R.drawable.banner_up)
                }
            }
            currentItem = position
            mOnItem.onColor(position)
        }

        fun initBanner() {
            count = adListBeans.size - 2
            handler.removeCallbacksAndMessages(null)//刷新时，要删除handler的请求
            handler.sendEmptyMessageDelayed(100,5000)
            binding.mainViewpager.setCurrentItem(1,false)
            point.clear()
            binding.mainPoint.removeAllViews()
            for (i in 0 until count) {
                val imageView = ImageView(mContext)
                point.add(imageView)
                if (i == 0) {
                    imageView.setImageResource(R.drawable.banner_down)
                } else {
                    imageView.setImageResource(R.drawable.banner_up)
                }
                val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                layoutParams.leftMargin = mContext.resources.getDimensionPixelSize(R.dimen.x4)
                layoutParams.rightMargin = mContext.resources.getDimensionPixelSize(R.dimen.x4)
                binding.mainPoint.addView(imageView, layoutParams)
            }
            pagerAdapter.notifyDataSetChanged()
            if (count > 1){
                binding.mainPoint.visibility = View.VISIBLE
            }else{
                binding.mainPoint.visibility = View.INVISIBLE
            }
        }

        /**
         * 返回真实的位置
         * @param position
         * @return 下标从0开始
         */
        fun toRealPosition(position: Int): Int {
            var realPosition: Int = 0
            if (count !== 0) {
                realPosition = (position - 1) % count
            }
            if (realPosition < 0)
                realPosition += count
            return realPosition
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
        var hotShopList: MutableList<TbkIndexBean.DataBean.HotGoodsListBean>
        var adapter: KTMain2HotAdapter

        constructor(binding: ItemMain2ForeBinding) : super(binding.root){
            this.binding = binding
            hotShopList = mutableListOf()
            binding.itemRecycler.layoutManager= LinearLayoutManager(mContext,LinearLayout.HORIZONTAL,false)
            adapter = KTMain2HotAdapter(hotShopList,mContext)
            adapter.setAppStatisticalUtil(appStatisticalUtil)
            adapter.setTransformer(transformer)
            binding.itemRecycler.adapter = adapter
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
        fun onColor(pos: Int)
        fun onItemShare(position: Int)
        fun onSelect(source : String)
    }
}