package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.bean.SuccessBean
import com.example.administrator.jipinshop.bean.TbkIndexBean
import com.example.administrator.jipinshop.util.DistanceHelper
import com.example.administrator.jipinshop.util.ShopJumpUtil
import com.example.administrator.jipinshop.util.UmApp.AppStatisticalUtil
import com.example.administrator.jipinshop.view.glide.GlideApp
import com.trello.rxlifecycle2.LifecycleTransformer

/**
 * @author 莫小婷
 * @create 2020/4/20
 * @Describe 宫格
 */
class KTMain2GridAdapter : RecyclerView.Adapter<KTMain2GridAdapter.ViewHolder>{

    private var mList: MutableList<TbkIndexBean.DataBean.BoxListBean>
    private var mContext: Context
    private lateinit var appStatisticalUtil: AppStatisticalUtil
    private lateinit var  transformer : LifecycleTransformer<SuccessBean>

    constructor(list: MutableList<TbkIndexBean.DataBean.BoxListBean>, context: Context){
        mList = list
        mContext = context
    }

    fun setAppStatisticalUtil(appStatisticalUtil : AppStatisticalUtil){
        this.appStatisticalUtil = appStatisticalUtil
    }

    fun setTransformer(transformer : LifecycleTransformer<SuccessBean>){
        this.transformer = transformer
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.kthome_tab, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder : ViewHolder, position: Int) {
        holder.run {
            mItemName.text = mList[position].title
            GlideApp.loderImage(mContext,mList[position].iconUrl,mImageView,0,0)
            itemView.setOnClickListener {
                appStatisticalUtil.addEvent("shouye_gongge." + (position + 1),transformer)
                ShopJumpUtil.openCommen(mContext,mList[position].type,mList[position].targetId,
                        mList[position].title)
            }
            var wight = mContext.resources.getDimension(R.dimen.x120)
            var zz =( DistanceHelper.getAndroiodScreenwidthPixels(mContext) - (wight * 5) ) / 6
            var result = 0f
            if (zz > 0){
                result = zz
            }
            var layoutParams =  item_container.layoutParams as LinearLayout.LayoutParams
            layoutParams.rightMargin = result.toInt()
            if (position == 0 || position == 1){
                layoutParams.leftMargin = result.toInt()
            }else{
                layoutParams.leftMargin = 0
            }
            item_container.layoutParams = layoutParams
        }
    }

    inner class ViewHolder : RecyclerView.ViewHolder{

        var mItemName: TextView
        var mImageView: ImageView
        var item_container : LinearLayout

        constructor(view: View) : super(view){
            mItemName = view.findViewById(R.id.item_name)
            mImageView = view.findViewById(R.id.item_image)
            item_container = view.findViewById(R.id.item_container)
        }
    }
}