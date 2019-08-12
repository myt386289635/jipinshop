package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.bean.EvaluationListBean
import com.example.administrator.jipinshop.databinding.ItemEvaInventoryBinding

/**
 * @author 莫小婷
 * @create 2019/8/8
 * @Describe
 */
class EvaInventoryAdapter() : RecyclerView.Adapter<EvaInventoryAdapter.ViewHolder>() {

    private lateinit var mList: MutableList<EvaluationListBean.DataBean>
    private lateinit var context: Context
    private lateinit var mOnClickItem: OnClickItem

    fun setClick(onClickItem: OnClickItem){
        mOnClickItem = onClickItem
    }

    //父类有构造函数，子类的构造函数需要显示调用父类的构造函数
    constructor(mList: MutableList<EvaluationListBean.DataBean>, context: Context) : this(){
        this.mList = mList
        this.context = context
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ViewHolder {
        var itemEvaInventoryBinding = DataBindingUtil.inflate<ItemEvaInventoryBinding>(LayoutInflater.from(context), R.layout.item_eva_inventory,viewGroup,false)
        var holder = ViewHolder(itemEvaInventoryBinding)
        return holder
    }

    override fun getItemCount(): Int {
       return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.run {
            mBinding.data = mList[position]
            itemView.setOnClickListener {
                mOnClickItem.onClickItem(position)
            }
            mBinding.itemUserImg.setOnClickListener {
                mOnClickItem.onClickUserinfo(mList[position].userId)
            }
            mBinding.executePendingBindings()
        }
    }

    class ViewHolder : RecyclerView.ViewHolder {

        var mBinding: ItemEvaInventoryBinding

        constructor(binding: ItemEvaInventoryBinding) : super(binding.root){
            mBinding = binding
        }
    }

    interface OnClickItem{
        fun onClickItem(position: Int)//点击进入文章详情页面
        fun onClickUserinfo(userId : String) //点击进入个人详情页
    }
}