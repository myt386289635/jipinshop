package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.bean.MoneyRecordBean
import com.example.administrator.jipinshop.databinding.ItemMoneyRecordBinding

/**
 * Author     ： 莫小婷
 * CreateTime ： 2020/2/20 10:32
 * Description： 红包提现记录页面
 */
class KTMoneyRecordAdapter : RecyclerView.Adapter<KTMoneyRecordAdapter.ViewHolder>{

    private var mList: MutableList<MoneyRecordBean.DataBean>
    private var mContext: Context

    constructor(mList: MutableList<MoneyRecordBean.DataBean>, mContext: Context){
        this.mList = mList
        this.mContext = mContext
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ViewHolder {
        var binding = DataBindingUtil.inflate<ItemMoneyRecordBinding>(LayoutInflater.from(mContext), R.layout.item_money_record, viewGroup, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.let {
            it.data = mList[position]
            when(mList[position].status){
                -2,-1 -> {
                    it.itemState.text = "提现超时"
                    it.itemState.setTextColor(mContext.resources.getColor(R.color.color_FFB829))
                }
                0,1,2,3 -> {
                    it.itemState.text = "审核中"
                    it.itemState.setTextColor(mContext.resources.getColor(R.color.color_9D9D9D))
                }
                4 -> {
                    it.itemState.text = "已转账"
                    it.itemState.setTextColor(mContext.resources.getColor(R.color.color_9D9D9D))
                }
            }
            it.executePendingBindings()
        }
    }

    class ViewHolder : RecyclerView.ViewHolder{

        var binding:ItemMoneyRecordBinding

        constructor(binding:ItemMoneyRecordBinding) : super(binding.root){
            this.binding = binding
        }
    }
}