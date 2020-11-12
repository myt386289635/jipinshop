package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Handler
import android.os.Looper
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.shoppingdetail.tbshoppingdetail.TBShoppingDetailActivity
import com.example.administrator.jipinshop.bean.EvaluationTabBean
import com.example.administrator.jipinshop.bean.TBSreachResultBean
import com.example.administrator.jipinshop.bean.TbCommonBean
import com.example.administrator.jipinshop.databinding.ItemHomeOneBinding
import com.example.administrator.jipinshop.databinding.ItemJdBinding
import com.example.administrator.jipinshop.databinding.ItemSreachOneBinding
import com.example.administrator.jipinshop.util.ShopJumpUtil
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.util.WeakRefHandler
import com.example.administrator.jipinshop.view.viewpager.TouchViewPager
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import java.math.BigDecimal

/**
 * @author 莫小婷
 * @create 2020/4/22
 * @Describe
 */
class KTJDDetailAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private val HEAD1 = 1
    private val HEAD2 = 2
    private val CONTENT = 3

    private var mContext: Context
    private lateinit var mAdListBeans: MutableList<TbCommonBean.AdListBean> //轮播图列表
    private var mList: MutableList<TBSreachResultBean.DataBean>
    private lateinit var mTitles : MutableList<EvaluationTabBean.DataBean>
    private lateinit var mOnItem: OnItem
    private var location = 0 //位置默认是0

    constructor(context: Context,list: MutableList<TBSreachResultBean.DataBean>){
        mContext = context
        mList = list
    }

    fun setOnClick(onItem: OnItem){
        mOnItem = onItem
    }

    fun setAd(adList: MutableList<TbCommonBean.AdListBean>){
        mAdListBeans = adList
    }

    fun setTitle(titles : MutableList<EvaluationTabBean.DataBean>){
        mTitles = titles
    }

    fun setLocation(location : Int){
        this.location = location
    }

    override fun getItemViewType(position: Int): Int {
       return when (position) {
           0 -> HEAD1
           1 -> HEAD2
           else -> CONTENT
       }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): RecyclerView.ViewHolder {
        var holder : RecyclerView.ViewHolder? = null
        when(type){
            HEAD1 -> {
                var itemHomeOneBinding = DataBindingUtil.inflate<ItemHomeOneBinding>(LayoutInflater.from(mContext), R.layout.item_home_one, viewGroup, false)
                holder = OneViewHolder(itemHomeOneBinding)
            }
            HEAD2 -> {
                var itemJdBinding = DataBindingUtil.inflate<ItemJdBinding>(LayoutInflater.from(mContext), R.layout.item_jd, viewGroup, false)
                holder = TwoViewHolder(itemJdBinding)
            }
            CONTENT -> {
                var binding1 = DataBindingUtil.inflate<ItemSreachOneBinding>(LayoutInflater.from(mContext), R.layout.item_sreach_one, viewGroup, false)
                holder = ContentViewHolder(binding1)
            }
        }
        return holder!!
    }

    override fun getItemCount(): Int {
        return if (mTitles.size == 0){
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
                    mPagerAdapter.setOnClickItem(object : KTPagerAdapter2.OnClickItem {
                        override fun onClickItem(postion: Int) {
                            ShopJumpUtil.openBanner(mContext, mAdListBeans[postion].type,
                                    mAdListBeans[postion].objectId, mAdListBeans[postion].name,
                                    mAdListBeans[postion].source,mAdListBeans[postion].remark)
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
                    titles.clear()
                    for ( i in mTitles.indices){
                        titles.add(mTitles[i].categoryName)
                    }
                    binding.dailyMainMenu.onPageSelected(location)
                    binding.dailyMainMenu.onPageScrolled(location, 0.0F, 0)
                    adapter.setOnClick(object : KTTabAdapter4.OnItem{
                        override fun onClick(position: Int) {
                            binding.dailyMainMenu.onPageSelected(position)
                            binding.dailyMainMenu.onPageScrolled(position, 0.0F, 0)
                            mOnItem.selcetTitle(position)
                        }
                    })
                    adapter.notifyDataSetChanged()
                    binding.netClude?.run {
                        if (mList.size == 0) {
                            qsNet.visibility = View.VISIBLE
                            errorImage.setBackgroundResource(R.mipmap.qs_nodata)
                            errorTitle.text = "暂无数据"
                            errorContent.text = "暂时没有任何数据！~"
                        } else {
                            qsNet.visibility = View.GONE
                        }
                    }
                }
            }
            CONTENT -> {
                var viewHolder: ContentViewHolder = holder as ContentViewHolder
                viewHolder.run {
                    var pos = position - 2
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

        var binding: ItemJdBinding
        var titles: MutableList<String>
        var adapter : KTTabAdapter4

        constructor(binding: ItemJdBinding) :super(binding.root){
            this.binding = binding
            titles = mutableListOf()
            var commonNavigator = CommonNavigator(mContext)
            adapter = KTTabAdapter4(titles)
            adapter.setPadding(mContext.resources.getDimension(R.dimen.x30).toInt(),
                    mContext.resources.getDimension(R.dimen.x30).toInt())
            adapter.setColor(mContext.resources.getColor(R.color.pickerview_topbar_title),
                    mContext.resources.getColor(R.color.color_E25838))
            commonNavigator.adapter = adapter
            binding.dailyMainMenu.navigator = commonNavigator
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
        fun selcetTitle(position: Int)
    }
}