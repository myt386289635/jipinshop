package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.blankj.utilcode.util.SPUtils
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.WebActivity
import com.example.administrator.jipinshop.activity.cheapgoods.CheapBuyActivity
import com.example.administrator.jipinshop.activity.login.LoginActivity
import com.example.administrator.jipinshop.activity.newpeople.NewPeopleActivity
import com.example.administrator.jipinshop.activity.shoppingdetail.tbshoppingdetail.TBShoppingDetailActivity
import com.example.administrator.jipinshop.bean.SuccessBean
import com.example.administrator.jipinshop.bean.TBSreachResultBean
import com.example.administrator.jipinshop.bean.TbkIndexBean
import com.example.administrator.jipinshop.databinding.*
import com.example.administrator.jipinshop.netwrok.RetrofitModule
import com.example.administrator.jipinshop.util.DistanceHelper
import com.example.administrator.jipinshop.util.ShopJumpUtil
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.util.UmApp.AppStatisticalUtil
import com.example.administrator.jipinshop.util.WeakRefHandler
import com.example.administrator.jipinshop.util.sp.CommonDate
import com.example.administrator.jipinshop.view.glide.GlideApp
import com.example.administrator.jipinshop.view.textview.TextViewDel
import com.example.administrator.jipinshop.view.viewpager.TouchViewPager
import com.trello.rxlifecycle2.LifecycleTransformer
import java.math.BigDecimal
import java.util.*


/**
 * @author 莫小婷
 * @create 2019/12/11
 * @Describe
 */
class KTMainAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private val HEAD1 = 1
    private val HEAD2 = 2
    private val HEAD3 = 3
    private val HEAD4 = 4
    private val HEAD5 = 5
    private val CONTENT = 6

    private var mContext: Context
    private lateinit var mOnItem: OnItem
    private lateinit var mPagerAdapter: HomePageAdapter
    private lateinit var appStatisticalUtil: AppStatisticalUtil
    private lateinit var  transformer : LifecycleTransformer<SuccessBean>
    private var layoutType = 1//横线是1，网格是2  默认横线

    private var mList: MutableList<TBSreachResultBean.DataBean>//精选好物列表
    private var mColor: MutableList<String> //颜色列表
    private var mAdListBeans: MutableList<TbkIndexBean.DataBean.Ad1ListBean> //轮播图列表
    private lateinit var mGridList : MutableList<TbkIndexBean.DataBean.BoxListBean> //4宫格图（品质大牌、白菜好物、新品专区、每日签到）
    private lateinit var mUserList: MutableList<TbkIndexBean.DataBean.MessageListBean> //轮播的用户
    private lateinit var mFreeGoodsList : MutableList<TbkIndexBean.DataBean.AllowanceGoodsListBean>//新人免单商品
    private lateinit var mImageDay: TbkIndexBean.DataBean.HotActivityBean//轮播的每日爆款
    private lateinit var mActivityList: MutableList<TbkIndexBean.DataBean.ActivityListBean>//高反专区、大额优惠卷
    private lateinit var mHotShopList: MutableList<TbkIndexBean.DataBean.HotGoodsListBean>//热销榜单
    private var mAd2Bean: TbkIndexBean.DataBean.Ad2Bean? = null //广告位
    private var newUser:Boolean = false
    private var asc = arrayOf("")
    private var orderByType = arrayOf("0")

    fun setOnClick(onItem: OnItem){
        mOnItem = onItem
    }

    constructor(list: MutableList<TBSreachResultBean.DataBean>, color: MutableList<String>,adListBeans: MutableList<TbkIndexBean.DataBean.Ad1ListBean> , context: Context){
        mList = list
        mColor = color
        mAdListBeans = adListBeans
        mContext = context
    }

    fun setNewUser(newUser:Boolean){
        this.newUser = newUser
    }

    fun setLayoutType(layoutType: Int) {
        this.layoutType = layoutType
    }

    fun getLayoutType(): Int {
        return layoutType
    }

    fun setAsc(asc1 : Array<String>){
        this.asc = asc1
    }

    fun setOrderByType(orderByType : Array<String>){
        this.orderByType = orderByType
    }

    fun setAd2Bean(ad2Bean: TbkIndexBean.DataBean.Ad2Bean?){
        mAd2Bean = ad2Bean
    }

    fun setFreeGoodsList(freeGoodsList : MutableList<TbkIndexBean.DataBean.AllowanceGoodsListBean>){
        mFreeGoodsList = freeGoodsList
    }

    fun setHotShopList(hotShopList: MutableList<TbkIndexBean.DataBean.HotGoodsListBean>){
        mHotShopList = hotShopList
    }

    fun setImageDay(imageDay: TbkIndexBean.DataBean.HotActivityBean){
        mImageDay = imageDay
    }

    fun setActivityList(activityList: MutableList<TbkIndexBean.DataBean.ActivityListBean>){
        mActivityList = activityList
    }

    fun setUserList(userList : MutableList<TbkIndexBean.DataBean.MessageListBean>){
        mUserList = userList
    }

    fun setGridList(gridList : MutableList<TbkIndexBean.DataBean.BoxListBean>){
        mGridList = gridList
    }

    fun setPagerAdapter(pagerAdapter: HomePageAdapter) {
        mPagerAdapter = pagerAdapter
    }

    fun setAppStatisticalUtil(appStatisticalUtil : AppStatisticalUtil){
        this.appStatisticalUtil = appStatisticalUtil
    }

    fun setTransformer(transformer : LifecycleTransformer<SuccessBean>){
        this.transformer = transformer
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
                        if (layoutType == 1) {
                            gridLayoutManager.spanCount
                        } else {
                            1
                        }
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
                var itemMainOneBinding = DataBindingUtil.inflate<ItemMainOneBinding>(LayoutInflater.from(mContext), R.layout.item_main_one, viewGroup, false)
                holder = OneViewHolder(itemMainOneBinding)
            }
            HEAD2 -> {
                var itemMainTwoBinding = DataBindingUtil.inflate<ItemMainTwoBinding>(LayoutInflater.from(mContext), R.layout.item_main_two, viewGroup, false)
                holder = TwoViewHolder(itemMainTwoBinding)
            }
            HEAD3 -> {
                var itemMainThreeBinding = DataBindingUtil.inflate<ItemMainThreeBinding>(LayoutInflater.from(mContext), R.layout.item_main_three, viewGroup, false)
                holder = TreeViewHolder(itemMainThreeBinding)
            }
            HEAD4 -> {
                var itemMainForeBinding = DataBindingUtil.inflate<ItemMainForeBinding>(LayoutInflater.from(mContext), R.layout.item_main_fore, viewGroup, false)
                holder = ForeViewHolder(itemMainForeBinding)
            }
            HEAD5 -> {
                var itemMainFiveBinding = DataBindingUtil.inflate<ItemMainFiveBinding>(LayoutInflater.from(mContext), R.layout.item_main_five, viewGroup, false)
                holder = FiveViewHolder(itemMainFiveBinding)
            }
            CONTENT -> {
                var binding1 = DataBindingUtil.inflate<ItemSreachOneBinding>(LayoutInflater.from(mContext), R.layout.item_sreach_one, viewGroup, false)
                holder = ContentViewHolder(binding1)
            }
        }
        return holder!!
    }

    override fun getItemCount(): Int {
       return if (mList.size == 0){
           0
       }else{
           mList.size + 5
       }
    }

    override fun onBindViewHolder(holder : RecyclerView.ViewHolder, position: Int) {
        var type = getItemViewType(position)
        when(type){
            HEAD1 -> {
                var oneViewHolder : OneViewHolder = holder as OneViewHolder
                oneViewHolder.run {
                    mPagerAdapter.setOnClickItem(object : KTPagerAdapter.OnClickItem {
                        override fun onClickItem(postion: Int) {
                            appStatisticalUtil.addEvent("shouye_banner." + (toRealPosition(postion) + 1),transformer)
                            ShopJumpUtil.openBanner(mContext,mAdListBeans[postion].type,
                                    mAdListBeans[postion].objectId,mAdListBeans[postion].name)
//                            ToastUtil.show("点击$postion  --真实位置：" + toRealPosition(postion))
                        }
                    })
                    adListBeans.clear()
                    adListBeans.addAll(mAdListBeans)
                    initBanner()
                }
            }
            HEAD2 -> {
                var twoViewHolder: TwoViewHolder  = holder as TwoViewHolder
                twoViewHolder.run {
                    gridList.clear()
                    gridList.addAll(mGridList)
                    if (gridList.size > 4){
                        binding.gridPoint.visibility = View.VISIBLE
                    }else{
                        binding.gridPoint.visibility = View.GONE
                    }
                    adapter.notifyDataSetChanged()
                }
            }
            HEAD3 -> {
                var threeViewHolder: TreeViewHolder = holder as TreeViewHolder
                threeViewHolder.run {
                    binding.size = mFreeGoodsList.size
                    binding.user = newUser
                    for (i in mFreeGoodsList.indices){
                        when(i){
                            0 -> {
                                binding.one = mFreeGoodsList[0]
                                binding.peopleText1.setTv(true)
                                binding.peopleText1.setColor(R.color.color_9D9D9D)
                            }
                            1 -> {
                                binding.two = mFreeGoodsList[1]
                                binding.peopleText2.setTv(true)
                                binding.peopleText2.setColor(R.color.color_9D9D9D)
                            }
                            2 -> {
                                binding.three = mFreeGoodsList[2]
                                binding.peopleText3.setTv(true)
                                binding.peopleText3.setColor(R.color.color_9D9D9D)
                            }
                            3 -> {
                                binding.fore = mFreeGoodsList[3]
                                binding.peopleText4.setTv(true)
                                binding.peopleText4.setColor(R.color.color_9D9D9D)
                            }
                        }
                    }
                    binding.executePendingBindings()
                    userList.clear()
                    userList.addAll(mUserList)
                    initUser()
                    val animation = AnimationUtils.loadAnimation(mContext, R.anim.free_scale)
                    binding.peopleGo.startAnimation(animation)
                    animation.start()
                    binding.mainNewpeople.setOnClickListener {
                        appStatisticalUtil.addEvent("shouye_xinren",transformer)
                        mContext.startActivity(Intent(mContext, NewPeopleActivity::class.java))
                    }
                    binding.mainOldpeople.setOnClickListener {
                        appStatisticalUtil.addEvent("shouye_tehui",transformer)
                        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                            mContext.startActivity(Intent(mContext, LoginActivity::class.java))
                            return@setOnClickListener
                        }
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
                        )
                    }
                    threeViewHolder.setIsRecyclable(false)//该Item不进行复用，避免动画消失问题
                }
            }
            HEAD4 -> {
                var foreViewHolder: ForeViewHolder = holder as ForeViewHolder
                foreViewHolder.run {
                    binding.title = mImageDay
                    for (i in 0 until 2){
                        when(i) {
                            0 -> {
                                if (i <= mActivityList.size - 1) {
                                    binding.activityOne = mActivityList[0]
                                } else {
                                    binding.activityOne = null
                                }
                            }
                            1 -> {
                                if (i <= mActivityList.size - 1) {
                                    binding.activityTwo = mActivityList[1]
                                } else {
                                    binding.activityTwo = null
                                }
                            }
                        }
                    }
                    if (mAd2Bean != null && !TextUtils.isEmpty(mAd2Bean?.img)){
                        binding.data = mAd2Bean
                    }else{
                        binding.data = null
                    }
                    binding.executePendingBindings()
                    imageOneList.clear()
                    imageOneList.addAll(mImageDay.goodsList1)
                    imageTwoList.clear()
                    imageTwoList.addAll(mImageDay.goodsList2)
                    initImage1()
                    initImage2()
                    binding.itemOneContainer.setOnClickListener {
                        appStatisticalUtil.addEvent("shouye_zhuanti.1",transformer)
                        ShopJumpUtil.openCommen(mContext,mImageDay.type,mImageDay.targetId,mImageDay.title)
                    }
                    binding.itemTwoContainer.setOnClickListener {
                        appStatisticalUtil.addEvent("shouye_zhuanti.2",transformer)
                        ShopJumpUtil.openCommen(mContext,mActivityList[0].type,mActivityList[0].targetId,
                                mActivityList[0].title)
                    }
                    binding.itemThreeContainer.setOnClickListener {
                        appStatisticalUtil.addEvent("shouye_zhuanti.3",transformer)
                        ShopJumpUtil.openCommen(mContext,mActivityList[1].type,mActivityList[1].targetId,
                                mActivityList[1].title)
                    }
                    binding.itemAction.setOnClickListener {
                        mAd2Bean?.let {
                            appStatisticalUtil.addEvent("shouye_banner.huodong",transformer)
                            ShopJumpUtil.openBanner(mContext,it.type,
                                    it.objectId,it.name)
                        }
                    }
                }
            }
            HEAD5 -> {
                var fiveViewHolder: FiveViewHolder = holder as FiveViewHolder
                fiveViewHolder.run {
                    hotShopList.clear()
                    hotShopList.addAll(mHotShopList)
                    adapter.notifyDataSetChanged()
                    initTitle(orderByType[0].toInt())//初始化默认值
                    if(layoutType == 1){
                        binding.sreachChangeImg.setImageResource(R.mipmap.sreach_change)
                    }else{
                        binding.sreachChangeImg.setImageResource(R.mipmap.sreach_change1)
                    }
                    binding.titleZh.setOnClickListener {
                        orderByType[0] = "0"
                        asc[0] = ""
                        initTitle(0)
                        mOnItem.initTitle()
                    }
                    binding.titleJg.setOnClickListener {
                        orderByType[0] = "1"
                        if (TextUtils.isEmpty(asc[0]) || asc[0] == "0") {
                            asc[0] = "1"
                        } else {
                            asc[0] = "0"
                        }
                        initTitle(1)
                        mOnItem.initTitle()
                    }
                    binding.titleBt.setOnClickListener {
                        orderByType[0] = "2"
                        asc[0] = ""
                        initTitle(2)
                        mOnItem.initTitle()
                    }
                    binding.titleXl.setOnClickListener {
                        orderByType[0] = "3"
                        asc[0] = ""
                        initTitle(3)
                        mOnItem.initTitle()
                    }
                    binding.sreachChange.setOnClickListener {
                        appStatisticalUtil.shouye_tj_tab(4,transformer)
                        if(layoutType == 1){
                            binding.sreachChangeImg.setImageResource(R.mipmap.sreach_change1)
                        }else{
                            binding.sreachChangeImg.setImageResource(R.mipmap.sreach_change)
                        }
                        mOnItem.changeList()
                    }
                }
            }
            CONTENT -> {
                var contentViewHolder: ContentViewHolder = holder as ContentViewHolder
                contentViewHolder.run {
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
                    if (layoutType == 1) {
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
                    } else {
                        binding.itemLineContainer.visibility = View.GONE
                        binding.itemGridContainer.visibility = View.VISIBLE
                        binding.itemLine.visibility = View.GONE
                        binding.itemGridImage.post {
                            val layoutParams = binding.itemGridImage.layoutParams
                            layoutParams.height = binding.itemGridImage.width
                            binding.itemGridImage.layoutParams = layoutParams
                        }
                        val layoutParams = binding.itemGridContainer1.layoutParams as RelativeLayout.LayoutParams
                        if (pos % 2 == 0) {
                            layoutParams.leftMargin = mContext.resources.getDimension(R.dimen.x20).toInt()
                            layoutParams.rightMargin = mContext.resources.getDimension(R.dimen.x10).toInt()
                        } else {
                            layoutParams.leftMargin = mContext.resources.getDimension(R.dimen.x10).toInt()
                            layoutParams.rightMargin = mContext.resources.getDimension(R.dimen.x20).toInt()
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
                        binding.itemGridShare.setOnClickListener { v ->
                            mOnItem.onItemShare(pos)
                        }
                    }
                    itemView.setOnClickListener { v ->
                        appStatisticalUtil.addEvent("shouye_tuijian." + (pos+1),transformer)
                        mContext.startActivity(Intent(mContext, TBShoppingDetailActivity::class.java)
                                .putExtra("otherGoodsId", mList[pos].otherGoodsId)
                        )
                    }
                }
            }
        }
    }

    inner class OneViewHolder : RecyclerView.ViewHolder, ViewPager.OnPageChangeListener {

        lateinit var binding : ItemMainOneBinding
        var mPagerAdapter: KTPagerAdapter
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

        constructor(binding: ItemMainOneBinding) : super(binding.root){
            this.binding = binding
            mPagerAdapter = KTPagerAdapter(mContext)
            point = mutableListOf()
            adListBeans = mutableListOf()
            mPagerAdapter.setList(adListBeans)
            mPagerAdapter.setPoint(point)
            mPagerAdapter.setImgCenter(true)
            binding.mainViewpager.adapter = mPagerAdapter
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
            mOnItem.onColor(toRealPosition(position))
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
            mPagerAdapter.notifyDataSetChanged()
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
    }

    inner class TwoViewHolder : RecyclerView.ViewHolder {

        var binding: ItemMainTwoBinding
        var gridList: MutableList<TbkIndexBean.DataBean.BoxListBean>
        var adapter: KTMainGridAdapter

        constructor(binding: ItemMainTwoBinding) : super(binding.root) {
            this.binding = binding

            var linearLayoutManager = LinearLayoutManager(mContext,LinearLayout.HORIZONTAL,false)
            binding.gridViewpager.layoutManager= linearLayoutManager
            gridList = mutableListOf()
            adapter = KTMainGridAdapter(gridList,mContext)
            adapter.setAppStatisticalUtil(appStatisticalUtil)
            adapter.setTransformer(transformer)
            binding.gridViewpager.adapter = adapter
            binding.gridViewpager.isNestedScrollingEnabled = false
            binding.gridViewpager.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    //划出去的宽度
                    var isResult = getResult(linearLayoutManager)
                    //可划出去的总宽度
                    var totleWith = getTotleWith(linearLayoutManager)
                    //线条的总宽度
                    var lineWith = mContext.resources.getDimension(R.dimen.x45)
                    //结果
                    var result  = (lineWith / totleWith) * isResult
                    var layoutParams =  binding.point.layoutParams as RelativeLayout.LayoutParams
                    layoutParams.leftMargin = result.toInt()
                    binding.point.layoutParams = layoutParams
                }
            })
        }

        fun getResult(linearLayoutManager: LinearLayoutManager) : Int{
            //找到即将移出屏幕Item的position,position是移出屏幕item的数量
            var position = linearLayoutManager.findFirstVisibleItemPosition()
            //根据position找到这个Item
            var firstVisiableChildView = linearLayoutManager.findViewByPosition(position)
            //获取Item的宽
            var itemWidth = firstVisiableChildView?.width ?: 0
            //算出该Item还未移出屏幕的高度
            var itemRight = firstVisiableChildView?.right ?: 0
            //position移出屏幕的数量*高度得出移动的距离
            var iposition = position * itemWidth
            //因为横着的RecyclerV第一个取到的Item position为零所以计算时需要加一个宽
            var iResult = iposition - itemRight + itemWidth
            return  iResult
        }

        fun getTotleWith(linearLayoutManager: LinearLayoutManager) : Int{
            //找到即将移出屏幕Item的position,position是移出屏幕item的数量
            var position = linearLayoutManager.findFirstVisibleItemPosition()
            //根据position找到这个Item
            var firstVisiableChildView = linearLayoutManager.findViewByPosition(position)
            //获取Item的宽
            var itemWidth = firstVisiableChildView?.width ?: 0

            return ((itemWidth * gridList.size) - DistanceHelper.getAndroiodScreenwidthPixels(mContext))
        }

    }

    inner class TreeViewHolder : RecyclerView.ViewHolder {

        var binding: ItemMainThreeBinding
        var userList: MutableList<TbkIndexBean.DataBean.MessageListBean>

        constructor(binding: ItemMainThreeBinding) : super(binding.root){
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
            binding.viewFlipper.setFlipInterval(2000)
            binding.viewFlipper.startFlipping()
        }
    }

    inner class ForeViewHolder : RecyclerView.ViewHolder {

        var binding: ItemMainForeBinding
        var imageOneList: MutableList<TbkIndexBean.DataBean.HotActivityBean.GoodsList1Bean>
        var imageTwoList: MutableList<TbkIndexBean.DataBean.HotActivityBean.GoodsList2Bean>

        constructor(binding: ItemMainForeBinding) : super(binding.root){
            this.binding = binding
            imageOneList = mutableListOf()
            imageTwoList = mutableListOf()
        }

        //初始化滚动第一张图
        fun initImage1(){
            binding.viewFlipper1.removeAllViews()
            for (i in imageOneList.indices){
                var view = LayoutInflater.from(mContext).inflate(R.layout.view_one_flipper,null)
                var item_image = view.findViewById<ImageView>(R.id.item_image)
                var item_name = view.findViewById<TextView>(R.id.item_name)
                var item_price = view.findViewById<TextView>(R.id.item_price)
                var item_oldprice = view.findViewById<TextViewDel>(R.id.item_oldprice)
                item_oldprice.setTv(true)
                item_oldprice.setColor(R.color.color_9D9D9D)
                item_name.text = imageOneList[i].otherName
                item_price.text = "¥" + imageOneList[i].buyPrice
                item_oldprice.text = "¥" + imageOneList[i].otherPrice
                GlideApp.loderImage(mContext,imageOneList[i].img,item_image,0,0)
                binding.viewFlipper1.addView(view)
            }
            binding.viewFlipper1.setFlipInterval(5000)
            binding.viewFlipper1.startFlipping()
        }
        //初始化滚动第二张图
        fun initImage2(){
            binding.viewFlipper2.removeAllViews()
            for (i in imageTwoList.indices){
                var view = LayoutInflater.from(mContext).inflate(R.layout.view_one_flipper,null)
                var item_image = view.findViewById<ImageView>(R.id.item_image)
                var item_name = view.findViewById<TextView>(R.id.item_name)
                var item_price = view.findViewById<TextView>(R.id.item_price)
                var item_oldprice = view.findViewById<TextViewDel>(R.id.item_oldprice)
                item_oldprice.setTv(true)
                item_oldprice.setColor(R.color.color_9D9D9D)
                item_name.text = imageTwoList[i].otherName
                item_price.text = "¥" + imageTwoList[i].buyPrice
                item_oldprice.text = "¥" + imageTwoList[i].otherPrice
                GlideApp.loderImage(mContext,imageTwoList[i].img,item_image,0,0)
                binding.viewFlipper2.addView(view)
            }
            binding.viewFlipper2.setFlipInterval(5000)
            binding.viewFlipper2.startFlipping()
        }
    }

    inner class FiveViewHolder : RecyclerView.ViewHolder{

        var binding: ItemMainFiveBinding
        var hotShopList: MutableList<TbkIndexBean.DataBean.HotGoodsListBean>
        var adapter: KTMainHotAdapter
        var mTextViews: MutableList<TextView>

        constructor(binding: ItemMainFiveBinding) : super(binding.root){
            this.binding = binding

            binding.itemRecycler.layoutManager= LinearLayoutManager(mContext,LinearLayout.HORIZONTAL,false)
            hotShopList= mutableListOf()
            adapter = KTMainHotAdapter(hotShopList,mContext)
            adapter.setAppStatisticalUtil(appStatisticalUtil)
            adapter.setTransformer(transformer)
            binding.itemRecycler.adapter = adapter

            mTextViews = ArrayList()
            mTextViews.add(binding.titleZh)
            mTextViews.add(binding.titleJg)
            mTextViews.add(binding.titleBt)
            mTextViews.add(binding.titleXl)
        }

        //初始化标题
        fun initTitle(position: Int) {
            appStatisticalUtil.shouye_tj_tab(position,transformer)
            val drawable = mContext.resources.getDrawable(R.mipmap.sreach_price3)
            drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
            binding.titleJg.setCompoundDrawables(null, null, drawable, null)
            for (i in mTextViews.indices) {
                mTextViews[i].setTextColor(mContext.resources.getColor(R.color.color_9D9D9D))
                mTextViews[i].paint.isFakeBoldText = false
            }
            for (i in mTextViews.indices) {
                if (i == position) {
                    if (position == 1) {
                        val upDrawable: Drawable
                        if (TextUtils.isEmpty(asc[0]) || asc[0] == "0") {
                            upDrawable = mContext.resources.getDrawable(R.mipmap.sreach_price1)
                        } else {
                            upDrawable = mContext.resources.getDrawable(R.mipmap.sreach_price)
                        }
                        upDrawable.setBounds(0, 0, upDrawable.minimumWidth, upDrawable.minimumHeight)
                        binding.titleJg.setCompoundDrawables(null, null, upDrawable, null)
                    }
                    mTextViews[i].setTextColor(mContext.resources.getColor(R.color.color_202020))
                    mTextViews[i].paint.isFakeBoldText = true
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
        fun initTitle()
        fun changeList()
    }
}