package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.bean.DailyTaskBean
import com.example.administrator.jipinshop.databinding.ItemRuleBinding

/**
 * @author 莫小婷
 * @create 2020/6/28
 * @Describe 赚钱——任务列表adapter
 */
class KTSignAdapter : RecyclerView.Adapter<KTSignAdapter.ViewHolder>{

    private var mList: MutableList<DailyTaskBean.DataBean.ListBean>
    private var mContext: Context
    private var type = 1 //1 日常任务  0新人任务
    private lateinit var mOnClick: OnClickJump

    constructor(mList: MutableList<DailyTaskBean.DataBean.ListBean>, mContext: Context){
        this.mList = mList
        this.mContext = mContext
    }

    fun setType(type : Int){
        this.type = type
    }

    fun setOnClickJump(onClickJump : OnClickJump){
        this.mOnClick = onClickJump
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ViewHolder {
        var itemRuleBinding = DataBindingUtil.inflate<ItemRuleBinding>(LayoutInflater.from(mContext), R.layout.item_rule, viewGroup, false)
        var holder = ViewHolder(itemRuleBinding)
        return holder
    }

    override fun getItemCount(): Int {
       return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.run {
            if (type == 1){
                binding.itemOk.text = "明天再来"
            }else{
                binding.itemOk.text = "已完成"
            }
            binding.date = mList[pos]
            binding.executePendingBindings()
            if (mList[pos].status == "0"){
                binding.itemOk.visibility = View.GONE
                binding.itemGoto.visibility = View.VISIBLE
            }else{
                binding.itemOk.visibility = View.VISIBLE
                binding.itemGoto.visibility = View.GONE
            }
            binding.itemGoto.setOnClickListener {
                mOnClick.onJump(pos)
            }
        }
    }

    inner class ViewHolder : RecyclerView.ViewHolder{

        var binding: ItemRuleBinding

        constructor(itemView: ItemRuleBinding) : super(itemView.root){
            binding = itemView
        }
    }

    interface OnClickJump{
        fun onJump(pos: Int)
    }
}