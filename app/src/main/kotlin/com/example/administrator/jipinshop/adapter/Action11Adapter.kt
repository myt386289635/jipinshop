package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Handler
import android.os.Looper
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.activity11.Action11Activity
import com.example.administrator.jipinshop.bean.Action11Bean
import com.example.administrator.jipinshop.bean.EvaluationTabBean
import com.example.administrator.jipinshop.databinding.ItemActionForeBinding
import com.example.administrator.jipinshop.databinding.ItemActionOneBinding
import com.example.administrator.jipinshop.databinding.ItemActionThreeBinding
import com.example.administrator.jipinshop.databinding.ItemActionTwoBinding
import com.example.administrator.jipinshop.util.ShopJumpUtil
import com.example.administrator.jipinshop.util.WeakRefHandler
import java.math.BigDecimal


/**
 * @author 莫小婷
 * @create 2019/10/29
 * @Describe
 */
class Action11Adapter : RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private val one = 1
    private val two = 2
    private val free = 3
    private val fore = 4

    private var mList: MutableList<Action11Bean.DataBean.GoodsDataListBean>
    private lateinit var mAdListBeans: MutableList<EvaluationTabBean.DataBean.AdListBean>
    private lateinit var mGvListBeans: MutableList<Action11Bean.DataBean.CategoryListBean>
    private var mContext: Context
    private var isFlag: Boolean
    private lateinit var mOnClickItem: OnClickItem

    constructor(mList: MutableList<Action11Bean.DataBean.GoodsDataListBean>, context: Context,isFlag: Boolean){
        this.mList = mList
        this.mContext = context
        this.isFlag = isFlag
    }

    fun setAd(mAdListBeans: MutableList<EvaluationTabBean.DataBean.AdListBean>){
        this.mAdListBeans = mAdListBeans
    }

    fun setGv(mGvListBeans: MutableList<Action11Bean.DataBean.CategoryListBean>){
        this.mGvListBeans = mGvListBeans
    }

    fun setOnClick(mOnClickItem: OnClickItem){
        this.mOnClickItem = mOnClickItem
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> one
            position == 1 -> two
            mList[position - 2].type == 1 -> free
            else -> fore
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): RecyclerView.ViewHolder {
        var holder : RecyclerView.ViewHolder? = null
        when(type){
            one -> {
                var itemActionOneBinding = DataBindingUtil.inflate<ItemActionOneBinding>(LayoutInflater.from(mContext), R.layout.item_action_one, viewGroup, false)
                holder = OneViewHolder(itemActionOneBinding)
            }
            two -> {
                var itemActionTwoBinding = DataBindingUtil.inflate<ItemActionTwoBinding>(LayoutInflater.from(mContext), R.layout.item_action_two, viewGroup, false)
                holder = TwoViewHolder(itemActionTwoBinding)
            }
            free -> {
                var itemActionThreeBinding = DataBindingUtil.inflate<ItemActionThreeBinding>(LayoutInflater.from(mContext), R.layout.item_action_three, viewGroup, false)
                holder = ThreeViewHolder(itemActionThreeBinding)
            }
            fore -> {
                var itemActionForeBinding = DataBindingUtil.inflate<ItemActionForeBinding>(LayoutInflater.from(mContext), R.layout.item_action_fore, viewGroup, false)
                holder = ForeViewHolder(itemActionForeBinding)
            }
        }
        return holder!!
    }

    override fun getItemCount(): Int {
        return if (mList.size == 0){
            0
        }else{
            mList.size + 2
        }
    }

    override fun onBindViewHolder(holder : RecyclerView.ViewHolder, position: Int) {
        var type = getItemViewType(position)
        when(type){
            one ->{
                var oneViewHolder : OneViewHolder = holder as OneViewHolder
                oneViewHolder.run {
                    if (isFlag) {
                        mPagerAdapter.setOnClickItem {
                            if (mAdListBeans[it].type == 2){//跳转到文章web
                                ShopJumpUtil.jumpArticle(mContext,mAdListBeans[it].objectId,"" + mAdListBeans[it].type,1)//固定
                            }else if (mAdListBeans[it].type == 10){ //专题页
                                mContext.startActivity(Intent(mContext, Action11Activity::class.java)
                                        .putExtra("title",mAdListBeans[it].name)
                                        .putExtra("categoryId",mAdListBeans[it].objectId)
                                )
                            }
                        }
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
            two ->{
                var twoViewHolder : TwoViewHolder = holder as TwoViewHolder
                twoViewHolder.run {
                    gvListBean.clear()
                    gvListBean.addAll(mGvListBeans)
                    setGridViewHeight()
                    mGvAdapter.notifyDataSetChanged()
                }
            }
            free ->{
                var threeViewHolder : ThreeViewHolder = holder as ThreeViewHolder
                var pos = position - 2
                threeViewHolder.run {
                    binding.date = mList[pos].goods
                    binding.itemBuy.setOnClickListener {
                        mOnClickItem.onClickBuy(pos)
                    }
                    itemView.setOnClickListener {
                        ShopJumpUtil.jumpArticle(mContext, mList[pos].goods.articleId,
                                "2", 1)
                    }
                    var tags = ""
                    for (i in mList[pos].goods.goodsTagsList.indices){
                        tags += if (i == mList[pos].goods.goodsTagsList.size - 1){
                            mList[pos].goods.goodsTagsList[i].name
                        }else{
                            mList[pos].goods.goodsTagsList[i].name + "、"
                        }
                    }
                    if (BigDecimal(mList[pos].goods.couponPrice).toDouble() == 0.0) {
                        binding.detailCoupon.visibility = View.GONE
                    } else {
                        binding.detailCoupon.visibility = View.VISIBLE
                    }
                    if (BigDecimal(mList[pos].goods.fee).toDouble() == 0.0) {
                        binding.detailFee.visibility = View.GONE
                    } else {
                        binding.detailFee.visibility = View.VISIBLE
                    }
                    if (BigDecimal(mList[pos].goods.couponPrice).toDouble() == 0.0 && BigDecimal(mList[pos].goods.fee).toDouble() == 0.0) {
                        binding.detailOtherPrice.visibility = View.GONE
                    } else {
                        binding.detailOtherPrice.visibility = View.VISIBLE
                    }
                    binding.detailOtherPrice.setTv(true)
                    binding.detailOtherPrice.setColor(R.color.color_9D9D9D)
                    binding.itemDescribe.text = tags
                    binding.executePendingBindings()
                }
            }
            fore ->{
                var foreViewHolder : ForeViewHolder = holder as ForeViewHolder
                var pos = position - 2
                foreViewHolder.run {
                    binding.date = mList[pos]
                    binding.itemImage.setOnClickListener {
                        mOnClickItem.onClickDetail(pos)
                    }
                    binding.executePendingBindings()
                }
            }
        }
    }

    inner class OneViewHolder : RecyclerView.ViewHolder{

        lateinit var binding : ItemActionOneBinding
        var mPagerAdapter: HomePagerAdapter
        var point: MutableList<ImageView>

        private var mCallback = Handler.Callback {
            if (it.what == 100) {
                if (binding.viewPager != null) {
                    binding.viewPager.currentItem = binding.viewPager.currentItem + 1
                }
            }
            true
        }
        var handler = WeakRefHandler(mCallback, Looper.getMainLooper())

        constructor(binding: ItemActionOneBinding) : super(binding.root){
            this.binding = binding
            mPagerAdapter = HomePagerAdapter(mContext)
            point = mutableListOf()
            mPagerAdapter.setList(mAdListBeans)
            mPagerAdapter.setPoint(point)
            mPagerAdapter.setImgCenter(false)
            mPagerAdapter.setViewPager(binding.viewPager)
            binding.viewPager.adapter = mPagerAdapter
        }

        fun initBanner() {
            binding.viewPager.currentItem = 0
            point.clear()
            binding.detailPoint.removeAllViews()
            for (i in mAdListBeans.indices) {
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
                binding.detailPoint.addView(imageView, layoutParams)
            }
            mPagerAdapter.notifyDataSetChanged()
        }
    }

    inner class TwoViewHolder : RecyclerView.ViewHolder{

        var binding : ItemActionTwoBinding
        var mGvAdapter: Action11GvAdapter
        var gvListBean: MutableList<Action11Bean.DataBean.CategoryListBean>

        constructor(binding: ItemActionTwoBinding) : super(binding.root){
            this.binding = binding
            gvListBean = mutableListOf()
            mGvAdapter = Action11GvAdapter(gvListBean,mContext)
            binding.itemGridView.adapter = mGvAdapter
        }

        //刷新时，需要重新设置一下gridView高度
        fun setGridViewHeight() {
            // 固定列宽，有多少列
            val numColumns = 4
            var totalHeight = 0
            // 计算每一列的高度之和
            var i = 0
            while (i < mGvAdapter.count) {
                // 获取gridview的每一个item
                val listItem = mGvAdapter.getView(i, null, binding.itemGridView)
                listItem.measure(0, 0)
                // 获取item的高度和
                totalHeight += listItem.measuredHeight
                i += numColumns
            }
            // 获取gridview的布局参数
            val params = binding.itemGridView.layoutParams
            params.height = totalHeight
            binding.itemGridView.layoutParams = params
        }
    }

    class ThreeViewHolder : RecyclerView.ViewHolder{

        var binding : ItemActionThreeBinding

        constructor(binding: ItemActionThreeBinding) : super(binding.root){
            this.binding = binding
        }
    }

    class ForeViewHolder : RecyclerView.ViewHolder{

        var binding : ItemActionForeBinding

        constructor(binding: ItemActionForeBinding) : super(binding.root){
            this.binding = binding
        }
    }

    interface OnClickItem{
        fun onClickBuy(pos : Int)
        fun onClickDetail(pos : Int)
    }
}