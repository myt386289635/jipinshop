package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.blankj.utilcode.util.SPUtils
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.login.LoginActivity
import com.example.administrator.jipinshop.activity.shoppingdetail.tbshoppingdetail.TBShoppingDetailActivity
import com.example.administrator.jipinshop.bean.SimilerGoodsBean
import com.example.administrator.jipinshop.bean.SuccessBean
import com.example.administrator.jipinshop.databinding.ItemUserlikeBinding
import com.example.administrator.jipinshop.util.UmApp.AppStatisticalUtil
import com.example.administrator.jipinshop.util.sp.CommonDate
import com.trello.rxlifecycle2.LifecycleTransformer
import java.math.BigDecimal

/**
 * @author 莫小婷
 * @create 2019/12/13
 * @Describe 热销模块
 */
class KTUserLikeAdapter : RecyclerView.Adapter<KTUserLikeAdapter.ViewHolder>{

    private var mList: MutableList<SimilerGoodsBean.DataBean>
    private var mContext: Context
    private var mOnItem: OnItem? = null
    private var appStatisticalUtil: AppStatisticalUtil? = null
    private lateinit var  transformer : LifecycleTransformer<SuccessBean>

    fun setOnItem(onItem: OnItem) {
        mOnItem = onItem
    }

    fun setAppStatisticalUtil(appStatisticalUtil : AppStatisticalUtil?){
        this.appStatisticalUtil = appStatisticalUtil
    }

    fun setTransformer(transformer : LifecycleTransformer<SuccessBean>){
        this.transformer = transformer
    }

    constructor(list: MutableList<SimilerGoodsBean.DataBean> , context: Context){
        mList = list
        mContext = context
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ViewHolder {
        var binding = DataBindingUtil.inflate<ItemUserlikeBinding>(LayoutInflater.from(mContext), R.layout.item_userlike, viewGroup, false)
        var holder = ViewHolder(binding)
        return holder
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(viewHolder : ViewHolder, position: Int) {
        viewHolder.run {
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
                layoutParams.topMargin = mContext.resources.getDimension(R.dimen.x20).toInt()
            }else{
                layoutParams.topMargin = mContext.resources.getDimension(R.dimen.x3).toInt()
            }
            if (position % 2 != 0) {
                layoutParams.leftMargin = mContext.resources.getDimension(R.dimen.x10).toInt()
                layoutParams.rightMargin = mContext.resources.getDimension(R.dimen.x20).toInt()
            } else {
                layoutParams.leftMargin = mContext.resources.getDimension(R.dimen.x20).toInt()
                layoutParams.rightMargin = mContext.resources.getDimension(R.dimen.x10).toInt()
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
                if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                    mContext.startActivity(Intent(mContext, LoginActivity::class.java))
                    return@setOnClickListener
                }
                mContext.startActivity(Intent(mContext, TBShoppingDetailActivity::class.java)
                        .putExtra("otherGoodsId", mList[position].otherGoodsId)
                        .putExtra("source",mList[position].source)
                )
            }
            binding.itemShare.setOnClickListener {
                mOnItem?.onItemShare(position)
            }
        }
    }

    class ViewHolder : RecyclerView.ViewHolder{
        
        var binding :ItemUserlikeBinding
        
        constructor(binding :ItemUserlikeBinding) : super(binding.root){
            this.binding = binding
        }
    }

    interface OnItem {
        fun onItemShare(position: Int)
    }
}