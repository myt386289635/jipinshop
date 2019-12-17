package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
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
import com.example.administrator.jipinshop.bean.TBSreachResultBean
import com.example.administrator.jipinshop.bean.TbCommonBean
import com.example.administrator.jipinshop.databinding.ItemHomeOneBinding
import com.example.administrator.jipinshop.databinding.ItemHomeTwoBinding
import com.example.administrator.jipinshop.databinding.ItemSreachOneBinding
import com.example.administrator.jipinshop.util.ShopJumpUtil
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.util.WeakRefHandler
import com.example.administrator.jipinshop.util.sp.CommonDate
import java.math.BigDecimal
import java.util.ArrayList

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
    private var isFlag: Boolean //控制轮播图的
    private var asc = arrayOf("")
    private var orderByType = arrayOf("0")
    private lateinit var mOnItem: OnItem

    fun setOnClick(onItem: OnItem){
        mOnItem = onItem
    }

    constructor(list: MutableList<TBSreachResultBean.DataBean> ,adListBeans: MutableList<TbCommonBean.AdListBean> , context: Context){
        mList = list
        mContext = context
        isFlag = true
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
        return when (position) {
            0 -> HEAD1
            1 -> HEAD2
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
                    if (isFlag) {
                        mPagerAdapter.setOnClickItem(object : KTPagerAdapter2.OnClickItem {
                            override fun onClickItem(postion: Int) {
                                ShopJumpUtil.openBanner(mContext,mAdListBeans[postion].type,
                                        mAdListBeans[postion].objectId,mAdListBeans[postion].name)
                            }
                        })
                        adListBeans.clear()
                        adListBeans.addAll(mAdListBeans)
                        initBanner()
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
                    var pos = position - 2
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

    inner class OneViewHolder : RecyclerView.ViewHolder{

        lateinit var binding : ItemHomeOneBinding
        var mPagerAdapter: KTPagerAdapter2
        var point: MutableList<ImageView>
        lateinit var adListBeans : MutableList<TbCommonBean.AdListBean>

        private var mCallback = Handler.Callback {
            if (it.what == 100) {
                if (adListBeans.size != 0){
                    if(binding.mainViewpager.currentItem == adListBeans.size - 1){
                        binding.mainViewpager.currentItem = 0
                    }else{
                        binding.mainViewpager.currentItem = binding.mainViewpager.currentItem + 1
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
            mPagerAdapter.setViewPager(binding.mainViewpager)
            binding.mainViewpager.adapter = mPagerAdapter
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

    inner class TwoViewHolder : RecyclerView.ViewHolder{

        var binding: ItemHomeTwoBinding
        var mGvAdapter: KTHomeGvAdapter
        var gvListBean: MutableList<TbCommonBean.CategoryListBean>
        var mTextViews: MutableList<TextView>

        constructor(binding: ItemHomeTwoBinding): super(binding.root){
            this.binding = binding
            gvListBean = mutableListOf()
            mGvAdapter = KTHomeGvAdapter(gvListBean,mContext)
            binding.itemGridView.adapter = mGvAdapter

            mTextViews = ArrayList()
            mTextViews.add(binding.titleZh)
            mTextViews.add(binding.titleJg)
            mTextViews.add(binding.titleBt)
            mTextViews.add(binding.titleXl)
        }
        //刷新时，需要重新设置一下gridView高度
        fun setGridViewHeight() {
            val numColumns = 4
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