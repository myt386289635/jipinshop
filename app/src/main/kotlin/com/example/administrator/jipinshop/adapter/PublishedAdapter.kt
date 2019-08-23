package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.bean.SreachResultArticlesBean
import com.example.administrator.jipinshop.databinding.ItemMyPublishBinding

/**
 * @author 莫小婷
 * @create 2019/8/22
 * @Describe
 */
class PublishedAdapter : RecyclerView.Adapter<PublishedAdapter.ViewHolder>{

    private var mList : MutableList<SreachResultArticlesBean.DataBean>
    private var type = ""
    private var mContext : Context
    private lateinit var mOnClickItem: OnClickItem

    fun setOnClick(onClickItem: OnClickItem){
        mOnClickItem = onClickItem
    }

    fun setType(type : String){
        this.type = type
    }

    constructor(mList: MutableList<SreachResultArticlesBean.DataBean>, mContext: Context) {
        this.mList = mList
        this.mContext = mContext
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ViewHolder {
        var binding = DataBindingUtil.inflate<ItemMyPublishBinding>(LayoutInflater.from(mContext), R.layout.item_my_publish, viewGroup, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return  mList.size
    }

    override fun onBindViewHolder(holder : ViewHolder, position: Int) {
        holder.mBinding.let {
            it.type = type.toInt()
            it.data = mList[position]
            it.executePendingBindings()
            holder.itemView.setOnClickListener {
                if (type == "2"){
                    mOnClickItem.onClickItem(position)
                }else{
                    mOnClickItem.onClickAudit(position)
                }
            }
        }
    }

    class ViewHolder : RecyclerView.ViewHolder{

        var mBinding: ItemMyPublishBinding

        constructor(binding: ItemMyPublishBinding) : super(binding.root){
            mBinding= binding
        }
    }

    interface OnClickItem{
        fun onClickItem(position: Int)//已完成
        fun onClickAudit(position: Int) //审核中
    }

}