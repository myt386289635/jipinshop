package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import com.blankj.utilcode.util.SPUtils
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.login.LoginActivity
import com.example.administrator.jipinshop.bean.SuccessBean
import com.example.administrator.jipinshop.bean.TbkIndexBean
import com.example.administrator.jipinshop.databinding.ItemMain2GridBinding
import com.example.administrator.jipinshop.util.ShopJumpUtil
import com.example.administrator.jipinshop.util.UmApp.AppStatisticalUtil
import com.example.administrator.jipinshop.util.sp.CommonDate
import com.example.administrator.jipinshop.view.dialog.DialogUtil
import com.example.administrator.jipinshop.view.glide.GlideApp
import com.trello.rxlifecycle2.LifecycleTransformer

/**
 * @author 莫小婷
 * @create 2020/12/28
 * @Describe
 */
class KTMain2TopGridAdapter : RecyclerView.Adapter<KTMain2TopGridAdapter.ViewHolder>{

    private var mContext: Context
    private var mList:MutableList<TbkIndexBean.DataBean.BoxCategoryListBean.ListBean>
    private lateinit var appStatisticalUtil: AppStatisticalUtil
    private lateinit var transformer : LifecycleTransformer<SuccessBean>

    constructor(mContext: Context, mList: MutableList<TbkIndexBean.DataBean.BoxCategoryListBean.ListBean>){
        this.mContext = mContext
        this.mList = mList
    }

    fun setAppStatisticalUtil(appStatisticalUtil : AppStatisticalUtil){
        this.appStatisticalUtil = appStatisticalUtil
    }

    fun setTransformer(transformer : LifecycleTransformer<SuccessBean>){
        this.transformer = transformer
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ViewHolder {
        var itemMain2GridBinding = DataBindingUtil.inflate<ItemMain2GridBinding>(LayoutInflater.from(mContext), R.layout.item_main2_grid, viewGroup, false)
        return ViewHolder(itemMain2GridBinding)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.run {
            binding.itemName.text = mList[position].title
            GlideApp.loderImage(mContext,mList[position].iconUrl,binding.itemImage,0,0)
            itemView.setOnClickListener {
                if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
                    mContext.startActivity(Intent(mContext, LoginActivity::class.java))
                    return@setOnClickListener
                }
                appStatisticalUtil.addEvent("shouye_top_gongge." + (position + 1),transformer)
                if (mList[position].popStatus == "1" &&
                        SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userMemberGrade, "0") == "0") {
                    DialogUtil.memberGuideDialog(mContext)
                }else{
                    ShopJumpUtil.openBanner(mContext,mList[position].type,mList[position].targetId,
                            mList[position].title,mList[position].source,mList[position].remark)
                }
            }
        }
    }

    inner class ViewHolder : RecyclerView.ViewHolder{

        var binding: ItemMain2GridBinding

        constructor(itemView: ItemMain2GridBinding) : super(itemView.root){
            binding = itemView
        }
    }
}