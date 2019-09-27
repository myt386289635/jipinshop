package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.bean.MyFreeBean
import com.example.administrator.jipinshop.databinding.ItemMyFreeendBinding

/**
 * @author 莫小婷
 * @create 2019/9/26
 * @Describe
 */
class PayPendedAdapter : RecyclerView.Adapter<PayPendedAdapter.ViewHolder> {

    private var mContext: Context
    private var mList: MutableList<MyFreeBean.DataBean>
    private var type = ""

    constructor(mList: MutableList<MyFreeBean.DataBean>, mContext: Context){
        this.mContext = mContext
        this.mList = mList
    }

    fun setType(type : String){
        this.type = type
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ViewHolder {
        var binding = DataBindingUtil.inflate<ItemMyFreeendBinding>(LayoutInflater.from(mContext), R.layout.item_my_freeend, viewGroup, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return  mList.size
    }

    override fun onBindViewHolder(holder : ViewHolder, position: Int) {
        holder.mBinding.let {
            it.type = type.toInt()
            it.date = mList[position]
            it.executePendingBindings()
            it.itemPriceOld.setColor(R.color.color_9D9D9D)
            it.itemPriceOld.setTv(true)
        }
    }

    class ViewHolder : RecyclerView.ViewHolder{

        var mBinding: ItemMyFreeendBinding

        constructor(binding: ItemMyFreeendBinding) : super(binding.root){
            mBinding = binding
        }
    }

}