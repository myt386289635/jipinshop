package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.bean.SreachResultArticlesBean
import com.example.administrator.jipinshop.databinding.ItemMyUnpassBinding

/**
 * @author 莫小婷
 * @create 2019/8/23
 * @Describe
 */
class UnPassAdapter : RecyclerView.Adapter<UnPassAdapter.ViewHolder>{

    private var mList : MutableList<SreachResultArticlesBean.DataBean>
    private var type = ""
    private var mContext : Context
    private lateinit var mOnClickItem: OnClickItem

    fun setClick(onClickItem: OnClickItem){
        mOnClickItem = onClickItem
    }

    constructor(mList: MutableList<SreachResultArticlesBean.DataBean>, type: String, mContext: Context) {
        this.mList = mList
        this.type = type
        this.mContext = mContext
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ViewHolder {
        var binding = DataBindingUtil.inflate<ItemMyUnpassBinding>(LayoutInflater.from(mContext), R.layout.item_my_unpass, viewGroup, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder : ViewHolder, position: Int) {
        holder.mBinding.let {
            it.type = type.toInt()
            it.data = mList[position]
            it.executePendingBindings()
            it.itemDelete.setOnClickListener {
                //删除
                mOnClickItem.onClickDelete(position)
            }
            it.itemEdit.setOnClickListener {
                //编辑
                mOnClickItem.onClickEdit(position)
            }
            it.itemSend.setOnClickListener {
                //发布
                mOnClickItem.onClickSend(position)
            }
        }
    }

    class ViewHolder : RecyclerView.ViewHolder{

        var mBinding: ItemMyUnpassBinding

        constructor(binding: ItemMyUnpassBinding) : super(binding.root){
            mBinding = binding
        }
    }

    interface OnClickItem{
        fun onClickDelete(position: Int)//删除
        fun onClickEdit(position: Int) //编辑
        fun onClickSend(position: Int) //发布
    }
}