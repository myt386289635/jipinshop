package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.bean.EvaEvaBean
import com.example.administrator.jipinshop.databinding.ItemEvaEva2Binding
import com.example.administrator.jipinshop.util.ClickUtil
import java.math.BigDecimal

/**
 * @author 莫小婷
 * @create 2019/8/13
 * @Describe
 */
class UnboxAdapter() : RecyclerView.Adapter<UnboxAdapter.ViewHolder>(){

    private lateinit var context : Context
    private lateinit var mList : MutableList<EvaEvaBean.DataBean>
    private lateinit var mOnClickItem: OnClickItem

    fun setClick(onClickItem: OnClickItem){
        mOnClickItem = onClickItem
    }

    constructor(context: Context, mList: MutableList<EvaEvaBean.DataBean>) : this() {
        this.context = context
        this.mList = mList
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ViewHolder {
        var itemEvaEva2Binding = DataBindingUtil.inflate<ItemEvaEva2Binding>(LayoutInflater.from(context), R.layout.item_eva_eva2, viewGroup, false)
        var holder = ViewHolder(itemEvaEva2Binding)
        return holder
    }

    override fun getItemCount(): Int {
       return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.run {
            mBinding.data = mList[pos]
            if (pos == mList.size - 1 || pos == 2){
                mBinding.itemLine.visibility = View.GONE
            }else{
                mBinding.itemLine.visibility = View.VISIBLE
            }
            itemView.setOnClickListener {
                mList[pos].let {
                    if (ClickUtil.isFastDoubleClick(800)) {
                        return@setOnClickListener
                    } else {
                        val bigDecimal = BigDecimal(it.pv)
                        it.pv = bigDecimal.toInt() + 1
                        mOnClickItem.onClickItem(it.articleId, "" + it.type,it.contentType)
                    }
                }
            }
            mBinding.itemUserImg.setOnClickListener {
                mOnClickItem.onClickUserinfo(mList[pos].userId)
            }
            mBinding.executePendingBindings()
        }
    }

    class ViewHolder : RecyclerView.ViewHolder{

        var mBinding: ItemEvaEva2Binding

        constructor(binding: ItemEvaEva2Binding) : super(binding.root){
            mBinding = binding
        }
    }

    interface OnClickItem{
        fun onClickItem(articleId :String , type :String , contentType : Int)//点击进入文章详情页面
        fun onClickUserinfo(userId : String) //点击进入个人详情页
    }
}