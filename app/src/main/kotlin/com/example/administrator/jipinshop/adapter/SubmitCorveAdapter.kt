package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.bean.SreachResultGoodsBean
import com.example.administrator.jipinshop.databinding.ItemSubmitCorverBinding
import com.google.android.flexbox.FlexboxLayout

/**
 * @author 莫小婷
 * @create 2019/8/14
 * @Describe
 */
class SubmitCorveAdapter : RecyclerView.Adapter<SubmitCorveAdapter.ViewHolder>{

    private var mList : MutableList<SreachResultGoodsBean.DataBean>
    private var mContext : Context
    private lateinit var mOnClickItem : OnClickItem

    fun setOnClick(onClickItem : OnClickItem){
        mOnClickItem = onClickItem
    }

    constructor(mList: MutableList<SreachResultGoodsBean.DataBean>, mContext: Context) {
        this.mList = mList
        this.mContext = mContext
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ViewHolder {
        var itemSubmitCorverBinding = DataBindingUtil.inflate<ItemSubmitCorverBinding>(LayoutInflater.from(mContext), R.layout.item_submit_corver, viewGroup, false)
        return ViewHolder(itemSubmitCorverBinding)
    }

    override fun getItemCount(): Int {
        return  mList.size
    }

    override fun onBindViewHolder(holder : ViewHolder, position: Int) {
        holder.run {
            if (mList[position].related == 0){
                mBinding.itemAdd.setBackgroundResource(R.drawable.bg_tab_eva3)
                mBinding.itemAdd.text = "一键添加"
                mBinding.itemAdd.setOnClickListener {
                    mOnClickItem.onClickAdd(position)
                }
            }else{
                mBinding.itemAdd.setBackgroundResource(R.drawable.bg_tab_eva)
                mBinding.itemAdd.text = "取消添加"
                mBinding.itemAdd.setOnClickListener {
                    mOnClickItem.onClickCancle(position)
                }
            }
            mBinding.data = mList[position]
            initTags(mBinding.itemFlexLayout,position)
            mBinding.executePendingBindings()
        }
    }

    inner class ViewHolder : RecyclerView.ViewHolder{

        var mBinding : ItemSubmitCorverBinding

        constructor(binding : ItemSubmitCorverBinding) : super(binding.root){
            mBinding = binding
        }

        fun initTags(flexboxLayout: FlexboxLayout, pos: Int) {
            flexboxLayout.removeAllViews()
            for (i in 0 until mList[pos].goodsTagsList.size) {
                val itemTypeView = LayoutInflater.from(mContext).inflate(R.layout.item_goodstag, null)
                val textView = itemTypeView.findViewById<TextView>(R.id.item_tag)
                textView.text = mList[pos].goodsTagsList[i].name
                flexboxLayout.addView(itemTypeView)
            }
        }
    }

    interface OnClickItem {
        fun onClickAdd(position: Int)//添加关联
        fun onClickCancle(position: Int)//取消关联
    }
}