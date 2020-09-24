package com.example.administrator.jipinshop.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.blankj.utilcode.util.SPUtils
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.bean.OrderTBBean
import com.example.administrator.jipinshop.databinding.ItemTbOrderBinding
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.util.sp.CommonDate

/**
 * @author 莫小婷
 * @create 2019/10/10
 * @Describe
 */
class KTMyOrderAdapter : RecyclerView.Adapter<KTMyOrderAdapter.ViewHolder>{

    private var mList: MutableList<OrderTBBean.DataBean>
    private var mContext : Context

    constructor(context : Context , list: MutableList<OrderTBBean.DataBean>){
        mContext = context
        mList = list
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ViewHolder {
        var binding = DataBindingUtil.inflate<ItemTbOrderBinding>(LayoutInflater.from(mContext), R.layout.item_tb_order, viewGroup, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return  mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mBinding.let {
            it.date = mList[position]
            it.executePendingBindings()
            it.itemCopy.setOnClickListener {
                val clip = mContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("jipinshop", holder.mBinding.itemOrder.text.toString().replace("订单号：", ""))
                clip.primaryClip = clipData
                ToastUtil.show("复制成功")
                SPUtils.getInstance().put(CommonDate.CLIP, holder.mBinding.itemOrder.text.toString().replace("订单号：", ""))
            }
            if (mList[position].level == 0){
                it.itemPrice.text = "返 ¥" + mList[position].preFee
            }else{
                it.itemPrice.text = "会员返 ¥" + mList[position].preFee
            }
        }
    }


    class ViewHolder : RecyclerView.ViewHolder{

        var mBinding :ItemTbOrderBinding

        constructor(binding :ItemTbOrderBinding) : super(binding.root){
            mBinding = binding
        }
    }
}