package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.WebActivity
import com.example.administrator.jipinshop.bean.NewPeopleBean
import com.example.administrator.jipinshop.databinding.ItemCheapbuyHeadBinding
import com.example.administrator.jipinshop.databinding.ItemNewForeBinding
import com.example.administrator.jipinshop.netwrok.RetrofitModule
import java.math.BigDecimal

/**
 * @author 莫小婷
 * @create 2020/1/3
 * @Describe
 */
class KTCheapBuyAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private val HEAD = 1
    private val CONTENT = 2

    private var mList: MutableList<NewPeopleBean.DataBean.AllowanceGoodsListBean>
    private var mContent: Context
    private var mOnClickItem: OnClickItem? = null
    private var totalUsedAllowance: String = "0"
    private var allowance: String = "0"

    fun setAllowance(allowance: String){
        this.allowance = allowance
    }

    fun setTotalUsedAllowance(totalUsedAllowance: String){
        this.totalUsedAllowance = totalUsedAllowance
    }

    fun setOnClickItem(onClickItem: OnClickItem?) {
        mOnClickItem = onClickItem
    }


    constructor(context: Context, mList: MutableList<NewPeopleBean.DataBean.AllowanceGoodsListBean>){
        mContent = context
        this.mList = mList
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0){
            HEAD
        }else {
            CONTENT
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): RecyclerView.ViewHolder {
        var holder : RecyclerView.ViewHolder? = null
        when(type){
            HEAD -> {
                var itemCheapbuyHeadBinding = DataBindingUtil.inflate<ItemCheapbuyHeadBinding>(LayoutInflater.from(mContent), R.layout.item_cheapbuy_head, viewGroup, false)
                holder = HeadViewHolder(itemCheapbuyHeadBinding)
            }
            CONTENT -> {
                var itemNewForeBinding = DataBindingUtil.inflate<ItemNewForeBinding>(LayoutInflater.from(mContent) , R.layout.item_new_fore, viewGroup, false)
                holder = ContentViewHolder(itemNewForeBinding)
            }
        }
        return holder!!
    }

    override fun getItemCount(): Int {
        return if (mList.size == 0){
            0
        }else{
            mList.size + 1
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var type = getItemViewType(position)
        when(type){
            HEAD -> {
                var headViewHolder : HeadViewHolder = holder as HeadViewHolder
                headViewHolder.run {
                    binding.itemCheapPrice.text =  BigDecimal(allowance).stripTrailingZeros().toPlainString() + "元"
                    binding.itemRule.setOnClickListener {
                        mContent.startActivity(Intent(mContent, WebActivity::class.java)
                                .putExtra(WebActivity.url, RetrofitModule.H5_URL + "th-rule.html")
                                .putExtra(WebActivity.title, "规则说明")
                        )
                    }
                }
            }
            CONTENT -> {
                var contentViewHolder : ContentViewHolder = holder as ContentViewHolder
                var pos = position - 1
                contentViewHolder.run {
                    binding.itemBuy.setOnClickListener {
                        mOnClickItem?.onBuy(pos)
                    }
                    var coupon = BigDecimal(mList[pos].couponPrice).toDouble()
                    if (coupon == 0.0) {
                        binding.itemCoupon.visibility = View.GONE
                    } else {
                        binding.itemCoupon.visibility = View.VISIBLE
                    }
                    var allowance = BigDecimal(mList[pos].useAllowancePrice).toDouble()
                    if (allowance == 0.0) {
                        binding.itemAllowance.visibility = View.GONE
                    } else {
                        binding.itemAllowance.visibility = View.VISIBLE
                    }
                    binding.itemPriceOld.setTv(true)
                    binding.itemPriceOld.setColor(R.color.color_9D9D9D)
                    binding.data = mList[pos]
                    binding.executePendingBindings()
                }
            }
        }
    }

    inner class ContentViewHolder: RecyclerView.ViewHolder{

        var binding: ItemNewForeBinding

        constructor(binding: ItemNewForeBinding) : super(binding.root){
            this.binding = binding
        }
    }


    inner class HeadViewHolder: RecyclerView.ViewHolder{

        var binding: ItemCheapbuyHeadBinding

        constructor(binding: ItemCheapbuyHeadBinding) : super(binding.root){
            this.binding = binding
        }
    }

    interface OnClickItem {
        fun onBuy(position: Int)
    }
}