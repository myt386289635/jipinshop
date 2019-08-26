package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.bean.QuestionsBean
import com.example.administrator.jipinshop.databinding.ItemQuesUnpassBinding

/**
 * @author 莫小婷
 * @create 2019/8/26
 * @Describe
 */
class UnPassQuesAdapter :  RecyclerView.Adapter<UnPassQuesAdapter.ViewHolder>{

    private var mList: MutableList<QuestionsBean.DataBean>
    private var mContext : Context
    private var type = ""
    private lateinit var mOnClickItem: OnClickItem

    fun setClick(onClickItem: OnClickItem){
        mOnClickItem = onClickItem
    }

    constructor(mList: MutableList<QuestionsBean.DataBean>, mContext: Context, type: String) : super() {
        this.mList = mList
        this.mContext = mContext
        this.type = type
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ViewHolder {
        var binding = DataBindingUtil.inflate<ItemQuesUnpassBinding>(LayoutInflater.from(mContext), R.layout.item_ques_unpass, viewGroup, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder : ViewHolder, position: Int) {
        holder.mBinding.let {
            it.type = type.toInt()
            it.date = mList[position]
            it.executePendingBindings()
            it.itemDelete.setOnClickListener {
                mOnClickItem.onClickDelete(position)
            }
        }
    }

    class ViewHolder : RecyclerView.ViewHolder{

        var mBinding: ItemQuesUnpassBinding

        constructor(binding: ItemQuesUnpassBinding) : super(binding.root){
            mBinding = binding
        }
    }

    interface OnClickItem{
        fun onClickDelete(position: Int)//删除
    }
}