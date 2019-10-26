package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.bean.EvaEvaBean
import com.example.administrator.jipinshop.databinding.ItemEvaEva3Binding
import com.example.administrator.jipinshop.util.ClickUtil
import java.math.BigDecimal

/**
 * @author 莫小婷
 * @create 2019/10/17
 * @Describe
 */
class EvaCompareAdapter :RecyclerView.Adapter<EvaCompareAdapter.ViewHolder>{

    private var mList: MutableList<EvaEvaBean.List2Bean>
    private var mContext : Context
    private lateinit var mOnClickItem: OnClickItem

    fun setClick(onClickItem: OnClickItem){
        mOnClickItem = onClickItem
    }

    constructor(mList: MutableList<EvaEvaBean.List2Bean>, mContext: Context) : super() {
        this.mList = mList
        this.mContext = mContext
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ViewHolder {
        var itemEvaEva3Binding = DataBindingUtil.inflate<ItemEvaEva3Binding>(LayoutInflater.from(mContext), R.layout.item_eva_eva3, viewGroup, false)
        return ViewHolder(itemEvaEva3Binding)
    }

    override fun getItemCount(): Int {
       return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.run {
            mBinding.data = mList[pos]
            itemView.setOnClickListener {
                mList[pos].let {
                    if (ClickUtil.isFastDoubleClick(800)) {
                        return@setOnClickListener
                    } else {
                        val bigDecimal = BigDecimal(it.pv)
                        it.pv = bigDecimal.toInt() + 1
                        it.articleReadData.clickCount = 1
                        mOnClickItem.onClickItem(it.articleId, "" + it.type,it.contentType)
                    }
                }
            }
            mBinding.itemUserImg.setOnClickListener {
                mOnClickItem.onClickUserinfo(mList[pos].user.userId)
            }
            mBinding.executePendingBindings()
        }
    }

    class ViewHolder : RecyclerView.ViewHolder{
        var mBinding: ItemEvaEva3Binding

        constructor(binding: ItemEvaEva3Binding) : super(binding.root){
            mBinding = binding
        }
    }

    interface OnClickItem {
        fun onClickItem(articleId: String, type: String, contentType: Int)//点击进入文章详情页面
        fun onClickUserinfo(userId: String) //点击进入个人详情页
    }
}