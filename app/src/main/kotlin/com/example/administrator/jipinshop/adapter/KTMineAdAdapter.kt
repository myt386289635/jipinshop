package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.bean.TbkIndexBean
import com.example.administrator.jipinshop.databinding.ItemMineAdBinding
import com.example.administrator.jipinshop.util.ShopJumpUtil
import com.example.administrator.jipinshop.view.glide.GlideApp

/**
 * @author 莫小婷
 * @create 2020/11/18
 * @Describe
 */
class KTMineAdAdapter : RecyclerView.Adapter<KTMineAdAdapter.ViewHolder>{

    private var mContent : Context
    private var mList: MutableList<TbkIndexBean.DataBean.Ad1ListBean>

    constructor(mContent: Context, mList: MutableList<TbkIndexBean.DataBean.Ad1ListBean>) : super() {
        this.mContent = mContent
        this.mList = mList
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ViewHolder {
        var itemMineAdBinding = DataBindingUtil.inflate<ItemMineAdBinding>(LayoutInflater.from(mContent), R.layout.item_mine_ad, viewGroup, false)
        return ViewHolder(itemMineAdBinding)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.run {
            GlideApp.loderImage(mContent,mList[pos].img,binding.itemImage,0,0)
            var layoutParams = binding.itemImage.layoutParams as RelativeLayout.LayoutParams
            if (pos == 0){
                layoutParams.leftMargin = mContent.resources.getDimension(R.dimen.x30).toInt()
                layoutParams.rightMargin = mContent.resources.getDimension(R.dimen.x6).toInt()
            }else if (pos == mList.size - 1){
                layoutParams.leftMargin = mContent.resources.getDimension(R.dimen.x6).toInt()
                layoutParams.rightMargin = mContent.resources.getDimension(R.dimen.x30).toInt()
            }else{
                layoutParams.leftMargin = mContent.resources.getDimension(R.dimen.x6).toInt()
                layoutParams.rightMargin = mContent.resources.getDimension(R.dimen.x6).toInt()
            }
            binding.itemImage.layoutParams = layoutParams
            itemView.setOnClickListener {
                ShopJumpUtil.openBanner(mContent, mList[pos].type,
                        mList[pos].objectId, mList[pos].name,
                        mList[pos].source , mList[pos].remark)
            }
        }
    }

    class ViewHolder : RecyclerView.ViewHolder{

        var binding: ItemMineAdBinding

        constructor(itemView: ItemMineAdBinding) : super(itemView.root){
            binding = itemView
        }
    }
}