package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.shoppingdetail.tbshoppingdetail.TBShoppingDetailActivity
import com.example.administrator.jipinshop.bean.SimilerGoodsBean
import com.example.administrator.jipinshop.databinding.ItemTbHeadBinding
import com.example.administrator.jipinshop.databinding.ItemUserlikeBinding
import java.math.BigDecimal

/**
 * @author 莫小婷
 * @create 2020/6/12
 * @Describe
 */
class KTTBDetailAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private val HEAD = 1
    private val CONTENT = 2

    private var mList: MutableList<SimilerGoodsBean.DataBean>
    private var context: Context
    private var mOnItem: OnItem? = null

    fun setOnItem(onItem: OnItem) {
        mOnItem = onItem
    }

    constructor(mList: MutableList<SimilerGoodsBean.DataBean>, context: Context){
        this.mList = mList
        this.context = context
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0){
            return HEAD
        }else{
            return CONTENT
        }
    }

    //为RecyclerView添加头布局
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
        var holder: RecyclerView.ViewHolder? = null
        when(type){
            HEAD -> {
                var binding = DataBindingUtil.inflate<ItemTbHeadBinding>(LayoutInflater.from(context),R.layout.item_tb_head,viewGroup,false)
                holder = HeadViewHolder(binding)
            }
            CONTENT -> {
                var binding = DataBindingUtil.inflate<ItemUserlikeBinding>(LayoutInflater.from(context), R.layout.item_userlike, viewGroup, false)
                holder = ViewHolder(binding)
            }
        }
        return holder!!
    }

    override fun getItemCount(): Int {
        if (mList.size == 0){
            return 0
        }else{
            return mList.size + 1
        }
    }

    override fun onBindViewHolder(viewHolder : RecyclerView.ViewHolder, pos: Int) {
        var type = getItemViewType(pos)
        when(type){
            HEAD -> {
                var headViewHolder = viewHolder as HeadViewHolder
                headViewHolder.run {
                    mBinding.itemSearch.setOnClickListener {
                        mOnItem?.onSearch()
                    }
                }
            }
            CONTENT -> {
                var holder = viewHolder as ViewHolder
                holder.run {
                    var position = pos - 1
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
                    viewHolder.itemView.setOnClickListener {
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

    class ViewHolder : RecyclerView.ViewHolder{

        var binding :ItemUserlikeBinding

        constructor(binding :ItemUserlikeBinding) : super(binding.root){
            this.binding = binding
        }
    }

    class HeadViewHolder : RecyclerView.ViewHolder{

        var mBinding : ItemTbHeadBinding

        constructor(itemView: ItemTbHeadBinding) : super(itemView.root){
            mBinding = itemView
        }
    }

    interface OnItem {
        fun onItemShare(position: Int)
        fun onSearch()
    }
}