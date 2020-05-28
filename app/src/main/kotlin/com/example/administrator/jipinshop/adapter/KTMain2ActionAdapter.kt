package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.bean.TbkIndexBean
import com.example.administrator.jipinshop.databinding.ItemMain2ActionBinding
import com.example.administrator.jipinshop.util.ShopJumpUtil
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.view.glide.GlideApp


/**
 * @author 莫小婷
 * @create 2020/4/21
 * @Describe
 */
class KTMain2ActionAdapter : RecyclerView.Adapter<KTMain2ActionAdapter.ViewHolder>{

    private var mContext: Context
    private var mList:MutableList<TbkIndexBean.DataBean.ActivityListBean>

    constructor(context: Context , list: MutableList<TbkIndexBean.DataBean.ActivityListBean>){
        mContext = context
        mList = list
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ViewHolder {
        var itemMain2ActionBinding = DataBindingUtil.inflate<ItemMain2ActionBinding>(LayoutInflater.from(mContext), R.layout.item_main2_action, viewGroup, false)
        return ViewHolder(itemMain2ActionBinding)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.run {
            var layoutParams =  binding.itemContainer.layoutParams as GridLayoutManager.LayoutParams
            if (position % 2 == 0){
                layoutParams.rightMargin = mContext.resources.getDimension(R.dimen.x10).toInt()
                layoutParams.leftMargin = 0
            }else{
                layoutParams.rightMargin = 0
                layoutParams.leftMargin = mContext.resources.getDimension(R.dimen.x10).toInt()
            }
            binding.itemContainer.layoutParams = layoutParams
            GlideApp.loderImage(mContext,mList[position].img,binding.itemImage,0,0)
            binding.itemImage.setOnClickListener {
                ShopJumpUtil.openBanner(mContext,mList[position].type,
                        mList[position].objectId,mList[position].name)
            }
        }
    }

    inner class ViewHolder : RecyclerView.ViewHolder{

        var binding: ItemMain2ActionBinding

        constructor(binding: ItemMain2ActionBinding) : super(binding.root){
            this.binding = binding
        }
    }
}