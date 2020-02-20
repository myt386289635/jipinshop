package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.databinding.ItemMoneyRecordBinding

/**
 * Author     ： 莫小婷
 * CreateTime ： 2020/2/20 10:32
 * Description： 红包提现记录页面
 */
class KTMoneyRecordAdapter : RecyclerView.Adapter<KTMoneyRecordAdapter.ViewHolder>{

    private var mList: MutableList<String>
    private var mContext: Context

    constructor(mList: MutableList<String>, mContext: Context){
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
        holder.binding.run {

        }
    }

    class ViewHolder : RecyclerView.ViewHolder{

        var binding:ItemMoneyRecordBinding

        constructor(binding:ItemMoneyRecordBinding) : super(binding.root){
            this.binding = binding
        }
    }
}