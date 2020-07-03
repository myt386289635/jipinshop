package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.blankj.utilcode.util.SPUtils
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.login.LoginActivity
import com.example.administrator.jipinshop.activity.shoppingdetail.tbshoppingdetail.TBShoppingDetailActivity
import com.example.administrator.jipinshop.bean.SuccessBean
import com.example.administrator.jipinshop.bean.TBSreachResultBean
import com.example.administrator.jipinshop.bean.TbCommonBean
import com.example.administrator.jipinshop.databinding.ItemHomeOneBinding
import com.example.administrator.jipinshop.databinding.ItemHomeTwoBinding
import com.example.administrator.jipinshop.databinding.ItemSreachOneBinding
import com.example.administrator.jipinshop.util.ShopJumpUtil
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.util.UmApp.AppStatisticalUtil
import com.example.administrator.jipinshop.util.WeakRefHandler
import com.example.administrator.jipinshop.util.sp.CommonDate
import com.example.administrator.jipinshop.view.viewpager.TouchViewPager
import com.trello.rxlifecycle2.LifecycleTransformer
import java.math.BigDecimal
import java.util.*

/**
 * @author 莫小婷
 * @create 2019/12/13
 * @Describe 首页 通用页
 */
class KTHomeCommenAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private val HEAD1 = 1
    private val HEAD2 = 2
    private val CONTENT = 3

    private var mList: MutableList<TBSreachResultBean.DataBean>
    private var mAdListBeans: MutableList<TbCommonBean.AdListBean> //轮播图列表
    private lateinit var mGvListBeans: MutableList<TbCommonBean.CategoryListBean>
    private var mContext: Context
    private var layoutType = 1//横线是1，网格是2  默认横线
    private var asc = arrayOf("")
    private var orderByType = arrayOf("0")
    private lateinit var mOnItem: OnItem
    private lateinit var appStatisticalUtil: AppStatisticalUtil
    private lateinit var  transformer : LifecycleTransformer<SuccessBean>
    private var commenStatistical = ""

    fun setOnClick(onItem: OnItem){
        mOnItem = onItem
    }

    fun setAppStatisticalUtil(appStatisticalUtil : AppStatisticalUtil){
        this.appStatisticalUtil = appStatisticalUtil
    }

    fun setTransformer(transformer : LifecycleTransformer<SuccessBean>){
        this.transformer = transformer
    }

    fun setCommenStatistical(commenStatistical: String){
        this.commenStatistical = commenStatistical
    }

    constructor(list: MutableList<TBSreachResultBean.DataBean> ,adListBeans: MutableList<TbCommonBean.AdListBean> , context: Context){
        mList = list
        mContext = context
        mAdListBeans = adListBeans
    }

    fun setGv(mGvListBeans: MutableList<TbCommonBean.CategoryListBean>){
        this.mGvListBeans = mGvListBeans
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

    override fun getItemViewType(position: Int): Int {
        return if(mGvListBeans.size != 0 && mAdListBeans.size == 0){
            if (position == 0){
                HEAD2
            }else {
                CONTENT
            }
        }else{
            when (position) {
                0 -> HEAD1
                1 -> HEAD2
                else -> CONTENT
            }
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
        when(type) {
            HEAD1 -> {
                var itemHomeOneBinding = DataBindingUtil.inflate<ItemHomeOneBinding>(LayoutInflater.from(mContext), R.layout.item_home_one, viewGroup, false)
                holder = OneViewHolder(itemHomeOneBinding)
            }
            HEAD2 -> {
                var itemHomeTwoBinding = DataBindingUtil.inflate<ItemHomeTwoBinding>(LayoutInflater.from(mContext), R.layout.item_home_two, viewGroup, false)
                holder = TwoViewHolder(itemHomeTwoBinding)
            }
            CONTENT -> {
                var binding1 = DataBindingUtil.inflate<ItemSreachOneBinding>(LayoutInflater.from(mContext), R.layout.item_sreach_one, viewGroup, false)
                holder = ContentViewHolder(binding1)
            }
        }
        return holder!!
    }

    override fun getItemCount(): Int {
        return if (mList.size == 0 && mGvListBeans.size == 0 && mAdListBeans.size == 0){
            0
        }else if(mGvListBeans.size != 0 &&mAdListBeans.size == 0){
            mList.size + 1
        }else{
            mList.size + 2
        }
    }

    override fun onBindViewHolder(holder : RecyclerView.ViewHolder, position: Int) {
        var type = getItemViewType(position)
        when(type){
            HEAD1 -> {
                var oneViewHolder : OneViewHolder = holder as OneViewHolder
                oneViewHolder.run {
                    mPagerAdapter.setOnClickItem(object : KTPagerAdapter2.OnClickItem {
                        override fun onClickItem(postion: Int) {
                            appStatisticalUtil.addEvent(commenStatistical + "_banner." + (toRealPosition(postion) + 1), transformer)
                            ShopJumpUtil.openBanner(mContext, mAdListBeans[postion].type,
                                    mAdListBeans[postion].objectId, mAdListBeans[postion].name,
                                    mAdListBeans[postion].source)
//                            ToastUtil.show("点击位置：" + toRealPosition(postion))
                        }
                    })
                    adListBeans.clear()
                    adListBeans.addAll(mAdListBeans)
                    initBanner()
                }
            }
            HEAD2 -> {
                var twoViewHolder : TwoViewHolder = holder as TwoViewHolder
                twoViewHolder.run {
                    gvListBean.clear()
                    gvListBean.addAll(mGvListBeans)
                    setGridViewHeight()
                    mGvAdapter.notifyDataSetChanged()
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
                        appStatisticalUtil.shouye_fl_tab(4,commenStatistical,transformer)
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
                    var pos = if(mGvListBeans.size != 0 &&mAdListBeans.size == 0){
                        position - 1
                    }else{
                        position - 2
                    }
                    binding.date = mList[pos]
                    binding.executePendingBindings()
                    val coupon1 = BigDecimal(mList[pos].couponPrice).toDouble()
                    val free1 = BigDecimal(mList[pos].fee).toDouble()
                    binding.itemLine.setBackgroundColor(mContext.resources.getColor(R.color.color_DEDEDE))
                    var lineLayout = binding.itemLine.layoutParams as LinearLayout.LayoutParams
                    lineLayout.height = 1
                    lineLayout.rightMargin = mContext.resources.getDimension(R.dimen.x30).toInt()
                    lineLayout.leftMargin = mContext.resources.getDimension(R.dimen.x30).toInt()
                    binding.itemLine.layoutParams = lineLayout
                    if (layoutType == 1) {
                        if (pos == 0) {
                            binding.itemLine.visibility = View.GONE
                        } else {
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
                            if (mOnItem != null) {
                                mOnItem.onItemShare(pos)
                            }
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
                            if (mOnItem != null) {
                                mOnItem.onItemShare(pos)
                            }
                        }
                    }
                    itemView.setOnClickListener { v ->
                        appStatisticalUtil.addEvent(commenStatistical + "_liebiao." + (pos+1),transformer)
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

        lateinit var binding : ItemHomeOneBinding
        var mPagerAdapter: KTPagerAdapter2
        var point: MutableList<ImageView>
        var adListBeans : MutableList<TbCommonBean.AdListBean>
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

        constructor(binding: ItemHomeOneBinding) : super(binding.root){
            this.binding = binding
            mPagerAdapter = KTPagerAdapter2(mContext)
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
        }

        fun initBanner() {
            if (adListBeans.size > 1){
                count = adListBeans.size - 2
            }else{
                count = adListBeans.size
            }
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

    inner class TwoViewHolder : RecyclerView.ViewHolder{

        var binding: ItemHomeTwoBinding
        var mGvAdapter: KTHomeGvAdapter
        var gvListBean: MutableList<TbCommonBean.CategoryListBean>
        var mTextViews: MutableList<TextView>

        constructor(binding: ItemHomeTwoBinding): super(binding.root){
            this.binding = binding
            gvListBean = mutableListOf()
            mGvAdapter = KTHomeGvAdapter(gvListBean,mContext)
            mGvAdapter.setAppStatisticalUtil(appStatisticalUtil)
            mGvAdapter.setTransformer(transformer)
            mGvAdapter.setCommenStatistical(commenStatistical)
            binding.itemGridView.adapter = mGvAdapter

            mTextViews = ArrayList()
            mTextViews.add(binding.titleZh)
            mTextViews.add(binding.titleJg)
            mTextViews.add(binding.titleBt)
            mTextViews.add(binding.titleXl)
        }
        //刷新时，需要重新设置一下gridView高度
        fun setGridViewHeight() {
            val numColumns = 5
            var totalHeight = 0
            var i = 0
            while (i < mGvAdapter.count) {
                val listItem = mGvAdapter.getView(i, null, binding.itemGridView)
                listItem.measure(0, 0)
                totalHeight += listItem.measuredHeight
                i += numColumns
            }
            val params = binding.itemGridView.layoutParams
            params.height = totalHeight
            binding.itemGridView.layoutParams = params
        }

        //初始化标题
        fun initTitle(position: Int) {
            appStatisticalUtil.shouye_fl_tab(position,commenStatistical,transformer)
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
        fun onItemShare(position: Int)
        fun initTitle()
        fun changeList()
    }
}