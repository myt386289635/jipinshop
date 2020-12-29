package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.shoppingdetail.tbshoppingdetail.TBShoppingDetailActivity
import com.example.administrator.jipinshop.bean.SuccessBean
import com.example.administrator.jipinshop.bean.TBSreachResultBean
import com.example.administrator.jipinshop.databinding.ItemMainHotBinding
import com.example.administrator.jipinshop.util.UmApp.AppStatisticalUtil
import com.trello.rxlifecycle2.LifecycleTransformer
import java.math.BigDecimal

/**
 * @author 莫小婷
 * @create 2020/4/21
 * @Describe 热销榜单
 */
class KTMain2HotAdapter : RecyclerView.Adapter<KTMain2HotAdapter.ViewHolder>{

    private var mList: MutableList<TBSreachResultBean.DataBean>
    private var mContext: Context
    private lateinit var appStatisticalUtil: AppStatisticalUtil
    private lateinit var  transformer : LifecycleTransformer<SuccessBean>
    private lateinit var mOnItem: OnItem

    constructor(list: MutableList<TBSreachResultBean.DataBean>, context: Context){
        mList = list
        mContext = context
    }

    fun setAppStatisticalUtil(appStatisticalUtil : AppStatisticalUtil){
        this.appStatisticalUtil = appStatisticalUtil
    }

    fun setTransformer(transformer : LifecycleTransformer<SuccessBean>){
        this.transformer = transformer
    }

    fun setOnClick(onItem: OnItem){
        mOnItem = onItem
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ViewHolder {
        var itemMainHotBinding = DataBindingUtil.inflate<ItemMainHotBinding>(LayoutInflater.from(mContext), R.layout.item_main_hot, viewGroup, false)
        var holder = ViewHolder(itemMainHotBinding)
        return holder
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.run {
            binding.data = mList[position]
            binding.executePendingBindings()
            when (position) {
                0 -> {
                    binding.itemSort.visibility = View.VISIBLE
                    binding.itemSort.setImageResource(R.mipmap.home_sort1)
                }
                1 -> {
                    binding.itemSort.visibility = View.VISIBLE
                    binding.itemSort.setImageResource(R.mipmap.home_sort2)
                }
                2 -> {
                    binding.itemSort.visibility = View.VISIBLE
                    binding.itemSort.setImageResource(R.mipmap.home_sort3)
                }
                else -> binding.itemSort.visibility = View.GONE
            }
            binding.itemPriceOld.setTv(true)
            binding.itemPriceOld.setColor(R.color.color_9D9D9D)
            val coupon1 = BigDecimal(mList[position].couponPrice).toDouble()
            val free1 = BigDecimal(mList[position].fee).toDouble()
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
            itemView.setOnClickListener {
                appStatisticalUtil.addEvent("shouye_bangdan." + (position + 1) , transformer)
                mContext.startActivity(Intent(mContext, TBShoppingDetailActivity::class.java)
                        .putExtra("otherGoodsId", mList[position].otherGoodsId)
                        .putExtra("source",mList[position].source)
                )
            }
            binding.itemGridShare.setOnClickListener {
                mOnItem.onItemShare(position)
            }
        }
    }

    class ViewHolder : RecyclerView.ViewHolder{

        var binding: ItemMainHotBinding

        constructor(binding: ItemMainHotBinding) : super(binding.root) {
            this.binding = binding
        }
    }

    interface OnItem{
        fun onItemShare(position: Int)
    }
}