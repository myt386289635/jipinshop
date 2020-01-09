package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.WebActivity
import com.example.administrator.jipinshop.bean.WelfareBean
import com.example.administrator.jipinshop.databinding.ItemWelfareBinding

/**
 * @author 莫小婷
 * @create 2020/1/9
 * @Describe
 */
class KTWelfareAdapter : RecyclerView.Adapter<KTWelfareAdapter.ViewHolder>{

    private var list: MutableList<WelfareBean.DataBean>
    private var context: Context

    constructor(list: MutableList<WelfareBean.DataBean>,context: Context){
        this.list = list
        this.context = context
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ViewHolder {
        var itemWelfareBinding = DataBindingUtil.inflate<ItemWelfareBinding>(LayoutInflater.from(context), R.layout.item_welfare, viewGroup, false)
        var holder = ViewHolder(itemWelfareBinding)
        return holder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.run {
            mBinding.date = list[pos]
            mBinding.executePendingBindings()
            mBinding.itemChange.setOnClickListener {
                context.startActivity(Intent(context,WebActivity::class.java)
                        .putExtra(WebActivity.url, list[pos].linkUrl)
                        .putExtra(WebActivity.title,list[pos].title)
                )
            }
        }
    }

    class ViewHolder : RecyclerView.ViewHolder{

        var mBinding: ItemWelfareBinding

        constructor(binding: ItemWelfareBinding) : super(binding.root){
            mBinding = binding
        }
    }
}