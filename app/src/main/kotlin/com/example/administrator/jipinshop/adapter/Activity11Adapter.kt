package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.activity11.Action11Activity
import com.example.administrator.jipinshop.bean.Activity11Bean
import com.example.administrator.jipinshop.databinding.ItemActionOne1Binding
import com.example.administrator.jipinshop.databinding.ItemActionTwo1Binding
import com.example.administrator.jipinshop.util.ShopJumpUtil
import com.umeng.commonsdk.stateless.UMSLEnvelopeBuild.mContext
import java.math.BigDecimal

/**
 * @author 莫小婷
 * @create 2019/10/30
 * @Describe
 */
class Activity11Adapter : RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private val one = 1
    private val two = 2

    private var mList: MutableList<Activity11Bean.DataBean>
    private lateinit var mAd : Activity11Bean.AdBean
    private var mContext: Context
    private lateinit var mOnClickItem: OnClickItem

    constructor(mList: MutableList<Activity11Bean.DataBean>, context: Context){
        this.mList = mList
        this.mContext = context
    }

    fun setAd(ad : Activity11Bean.AdBean){
        mAd = ad
    }

    fun setOnClick(mOnClickItem: OnClickItem){
        this.mOnClickItem = mOnClickItem
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> one
            else -> two
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): RecyclerView.ViewHolder {
        var holder : RecyclerView.ViewHolder? = null
        when(type){
            one -> {
                var itemBinding = DataBindingUtil.inflate<ItemActionOne1Binding>(LayoutInflater.from(mContext), R.layout.item_action_one1, viewGroup, false)
                holder = OneViewHolder(itemBinding)
            }
            two -> {
                var itemBinding = DataBindingUtil.inflate<ItemActionTwo1Binding>(LayoutInflater.from(mContext), R.layout.item_action_two1, viewGroup, false)
                holder = TwoViewHolder(itemBinding)
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

    override fun onBindViewHolder(holder : RecyclerView.ViewHolder, position: Int) {
        var type = getItemViewType(position)
        when(type){
            one -> {
                var oneViewHolder : OneViewHolder = holder as OneViewHolder
                oneViewHolder.run {
                    binding.date = mAd
                    binding.itemImage.setOnClickListener {
                        if (mAd.type == 2){//跳转到文章web
                            ShopJumpUtil.jumpArticle(mContext,mAd.objectId,"" + mAd.type,1)//固定
                        }else if (mAd.type == 10){
                            //专题页
                            mContext.startActivity(Intent(mContext, Action11Activity::class.java)
                                    .putExtra("title",mAd.name)
                                    .putExtra("categoryId",mAd.objectId)
                            )
                        }
                    }
                    binding.executePendingBindings()
                }
            }
            two -> {
                var twoViewHolder : TwoViewHolder = holder as TwoViewHolder
                var pos = position - 1
                twoViewHolder.run {
                    binding.date = mList[pos]
                    binding.itemBuy.setOnClickListener {
                        mOnClickItem.onClickBuy(pos)
                    }
                    itemView.setOnClickListener {
                        ShopJumpUtil.jumpArticle(mContext, mList[pos].articleId,
                                "2", 1)
                    }
                    var tags = ""
                    for (i in mList[pos].goodsTagsList.indices){
                        tags += if (i == mList[pos].goodsTagsList.size - 1){
                            mList[pos].goodsTagsList[i].name
                        }else{
                            mList[pos].goodsTagsList[i].name + "、"
                        }
                    }
                    if (BigDecimal(mList[pos].couponPrice).toDouble() == 0.0) {
                        binding.detailCoupon.visibility = View.GONE
                    } else {
                        binding.detailCoupon.visibility = View.VISIBLE
                    }
                    if (BigDecimal(mList[pos].fee).toDouble() == 0.0) {
                        binding.detailFee.visibility = View.GONE
                    } else {
                        binding.detailFee.visibility = View.VISIBLE
                    }
                    if (BigDecimal(mList[pos].couponPrice).toDouble() == 0.0 && BigDecimal(mList[pos].fee).toDouble() == 0.0) {
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
        }
    }

    class OneViewHolder : RecyclerView.ViewHolder{

        var binding : ItemActionOne1Binding

        constructor(binding: ItemActionOne1Binding) : super(binding.root){
            this.binding = binding
        }
    }

    class TwoViewHolder : RecyclerView.ViewHolder{

        var binding : ItemActionTwo1Binding

        constructor(binding: ItemActionTwo1Binding) : super(binding.root){
            this.binding = binding
        }
    }

    interface OnClickItem{
        fun onClickBuy(pos : Int)
    }
}