package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.graphics.drawable.Drawable
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
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.ali.auth.third.core.context.KernelContext.resources
import com.blankj.utilcode.util.SPUtils
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.WebActivity
import com.example.administrator.jipinshop.activity.login.LoginActivity
import com.example.administrator.jipinshop.activity.newpeople.NewPeopleActivity
import com.example.administrator.jipinshop.activity.shoppingdetail.tbshoppingdetail.TBShoppingDetailActivity
import com.example.administrator.jipinshop.bean.TBSreachResultBean
import com.example.administrator.jipinshop.bean.TbkIndexBean
import com.example.administrator.jipinshop.databinding.*
import com.example.administrator.jipinshop.fragment.home.main.tab.KTTabFragment
import com.example.administrator.jipinshop.netwrok.RetrofitModule
import com.example.administrator.jipinshop.util.ShopJumpUtil
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.util.WeakRefHandler
import com.example.administrator.jipinshop.util.sp.CommonDate
import com.example.administrator.jipinshop.view.glide.GlideApp
import com.example.administrator.jipinshop.view.textview.TextViewDel
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import java.math.BigDecimal
import java.util.ArrayList


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
    private var isFlag: Boolean //控制轮播图的
    private lateinit var mOnItem: OnItem
    private lateinit var mPagerAdapter: HomePageAdapter
    private var layoutType = 1//横线是1，网格是2  默认横线

    private var mList: MutableList<TBSreachResultBean.DataBean>//精选好物列表
    private var mColor: MutableList<String> //颜色列表
    private var mAdListBeans: MutableList<TbkIndexBean.DataBean.Ad1ListBean> //轮播图列表
    private lateinit var mGridList : MutableList<TbkIndexBean.DataBean.BoxListBean> //4宫格图（品质大牌、白菜好物、新品专区、每日签到）
    private lateinit var mUserList: MutableList<TbkIndexBean.DataBean.MessageListBean> //轮播的用户
    private lateinit var mFreeGoodsList : MutableList<TbkIndexBean.DataBean.FreeGoodsListBean>//新人免单商品
    private lateinit var mImageDay: TbkIndexBean.DataBean.HotActivityBean//轮播的每日爆款
    private lateinit var mActivityList: MutableList<TbkIndexBean.DataBean.ActivityListBean>//高反专区、大额优惠卷
    private lateinit var mHotShopList: MutableList<TbkIndexBean.DataBean.HotGoodsListBean>//热销榜单
    private var mAd2Bean: TbkIndexBean.DataBean.Ad2Bean? = null //广告位
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
        this.isFlag = true
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

    fun setAd2Bean(ad2Bean: TbkIndexBean.DataBean.Ad2Bean){
        mAd2Bean = ad2Bean
    }

    fun setFreeGoodsList(freeGoodsList : MutableList<TbkIndexBean.DataBean.FreeGoodsListBean>){
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
                            ShopJumpUtil.openBanner(mContext,mAdListBeans[postion].type,
                                    mAdListBeans[postion].objectId,mAdListBeans[postion].name)
                        }
                    })
                    adListBeans.clear()
                    mOnItem.onColor(0)
                    binding.mainBackground.setColorFilter(Color.parseColor("#" +mColor[0]))
                    adListBeans.addAll(mAdListBeans)
                    initBanner()
                    if (isFlag) {
                        Thread {
                            while (true) {
                                try {
                                    Thread.sleep(5000)
                                } catch (e: InterruptedException) {
                                    e.printStackTrace()
                                }
                                handler.sendEmptyMessage(100)
                            }
                        }.start()
                        isFlag = false
                    }
                }
            }
            HEAD2 -> {
                var twoViewHolder: TwoViewHolder  = holder as TwoViewHolder
                twoViewHolder.run {
                    initGrid()
                }
            }
            HEAD3 -> {
                var threeViewHolder: TreeViewHolder = holder as TreeViewHolder
                threeViewHolder.run {
                    binding.size = mFreeGoodsList.size
                    for (i in 0 until 4){
                        when(i){
                            0 -> {
                                if (i <= mFreeGoodsList.size - 1){
                                    binding.one = mFreeGoodsList[0]
                                    binding.peopleText1.setTv(true)
                                    binding.peopleText1.setColor(R.color.color_9D9D9D)
                                }else{
                                    binding.one = null
                                }
                            }
                            1 -> {
                                if (i <= mFreeGoodsList.size - 1){
                                    binding.two = mFreeGoodsList[1]
                                    binding.peopleText2.setTv(true)
                                    binding.peopleText2.setColor(R.color.color_9D9D9D)
                                }else{
                                    binding.two = null
                                }
                            }
                            2 -> {
                                if (i <= mFreeGoodsList.size - 1){
                                    binding.three = mFreeGoodsList[2]
                                    binding.peopleText3.setTv(true)
                                    binding.peopleText3.setColor(R.color.color_9D9D9D)
                                }else{
                                    binding.three = null
                                }
                            }
                            3 -> {
                                if (i <= mFreeGoodsList.size - 1){
                                    binding.fore = mFreeGoodsList[3]
                                    binding.peopleText4.setTv(true)
                                    binding.peopleText4.setColor(R.color.color_9D9D9D)
                                }else{
                                    binding.fore = null
                                }
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
                        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                            mContext.startActivity(Intent(mContext, LoginActivity::class.java))
                            return@setOnClickListener
                        }
                        mContext.startActivity(Intent(mContext, NewPeopleActivity::class.java))
                    }
                    binding.marqueeContainer.setOnClickListener {
                        mContext.startActivity(Intent(mContext, WebActivity::class.java)
                                .putExtra(WebActivity.url, RetrofitModule.H5_URL + "fee-rule.html")
                                .putExtra(WebActivity.title, "极品城购物补贴说明")
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
                        ShopJumpUtil.openCommen(mContext,mImageDay.type,mImageDay.targetId,mImageDay.title)
                    }
                    binding.itemTwoContainer.setOnClickListener {
                        ShopJumpUtil.openCommen(mContext,mActivityList[0].type,mActivityList[0].targetId,
                                mActivityList[0].title)
                    }
                    binding.itemThreeContainer.setOnClickListener {
                        ShopJumpUtil.openCommen(mContext,mActivityList[1].type,mActivityList[1].targetId,
                                mActivityList[1].title)
                    }
                    binding.itemAction.setOnClickListener {
                        mAd2Bean?.let {
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
                        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                            mContext.startActivity(Intent(mContext, LoginActivity::class.java))
                            return@setOnClickListener
                        }
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
        lateinit var adListBeans : MutableList<TbkIndexBean.DataBean.Ad1ListBean>

        private var mCallback = Handler.Callback {
            if (it.what == 100) {
                if (adListBeans.size != 0){
                    if(binding.mainViewpager.currentItem == adListBeans.size - 1){
                        binding.mainViewpager.currentItem = 0
                    }else{
                        binding.mainViewpager.currentItem = binding.mainViewpager.currentItem + 1
                    }
                    mOnItem.onColor(binding.mainViewpager.currentItem)
                    binding.mainBackground.setColorFilter(Color.parseColor("#" +mColor[binding.mainViewpager.currentItem]))
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
            mPagerAdapter.setViewPager(binding.mainViewpager)
            binding.mainViewpager.adapter = mPagerAdapter
            binding.mainViewpager.addOnPageChangeListener(this)
        }

        override fun onPageScrollStateChanged(p0: Int) {}

        override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}

        override fun onPageSelected(position: Int) {
            mOnItem.onColor(position % adListBeans.size)
            binding.mainBackground.setColorFilter(Color.parseColor("#" +mColor[position % adListBeans.size]))
        }

        fun initBanner() {
            binding.mainViewpager.setCurrentItem(0,false)
            point.clear()
            binding.mainPoint.removeAllViews()
            for (i in adListBeans.indices) {
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
        }
    }

    inner class TwoViewHolder : RecyclerView.ViewHolder {

        var binding: ItemMainTwoBinding
        var fragments: MutableList<Fragment>
        var tabAdapter: KTTabNoTitleAdapter

        constructor(binding: ItemMainTwoBinding) : super(binding.root) {
            this.binding = binding

            fragments = mutableListOf()
            mPagerAdapter.setFragments(fragments)
            binding.gridViewpager.adapter = mPagerAdapter
            var commonNavigator = CommonNavigator(mContext)
            tabAdapter = KTTabNoTitleAdapter(fragments)
            commonNavigator.isAdjustMode = true
            commonNavigator.adapter = tabAdapter
            binding.gridPoint.navigator = commonNavigator
            ViewPagerHelper.bind(binding.gridPoint, binding.gridViewpager)
        }

        fun initGrid(){
            fragments.clear()//清空数据
            if (mGridList != null && mGridList.size != 0){
                if (mGridList.size <= 4){
                    binding.gridPoint.visibility  = View.GONE
                    fragments.add(KTTabFragment.getInstance(0,mGridList))
                }else{
                    binding.gridPoint.visibility  = View.VISIBLE
                    fragments.add(KTTabFragment.getInstance(0,mGridList))
                    fragments.add(KTTabFragment.getInstance(1,mGridList))
                }
            }
            mPagerAdapter.updateData(fragments)//更新adapter
            tabAdapter.notifyDataSetChanged()
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
            binding.itemRecycler.adapter = adapter

            mTextViews = ArrayList()
            mTextViews.add(binding.titleZh)
            mTextViews.add(binding.titleJg)
            mTextViews.add(binding.titleBt)
            mTextViews.add(binding.titleXl)
        }

        //初始化标题
        fun initTitle(position: Int) {
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