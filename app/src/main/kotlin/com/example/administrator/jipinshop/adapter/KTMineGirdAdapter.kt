package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.bean.MyWalletBean
import com.example.administrator.jipinshop.bean.SuccessBean
import com.example.administrator.jipinshop.databinding.ItemMineGridBinding
import com.example.administrator.jipinshop.util.ShopJumpUtil
import com.example.administrator.jipinshop.util.UmApp.AppStatisticalUtil
import com.trello.rxlifecycle2.LifecycleTransformer

/**
 * @author 莫小婷
 * @create 2020/10/30
 * @Describe
 */
class KTMineGirdAdapter : RecyclerView.Adapter<KTMineGirdAdapter.ViewHolder>{

    private var mList: MutableList<MyWalletBean.DataBean.AdListBean>
    private var mContent: Context
    private lateinit var appStatisticalUtil: AppStatisticalUtil
    private lateinit var transformer: LifecycleTransformer<SuccessBean>
    private var name: String = ""

    constructor(mList: MutableList<MyWalletBean.DataBean.AdListBean>, mContent: Context){
        this.mList = mList
        this.mContent = mContent
    }

    fun setAppStatisticalUtil(appStatisticalUtil : AppStatisticalUtil){
        this.appStatisticalUtil = appStatisticalUtil
    }

    fun setTransformer(transformer : LifecycleTransformer<SuccessBean>){
        this.transformer = transformer
    }

    fun setName(name: String){
        this.name = name
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ViewHolder {
        var itemMineGridBinding = DataBindingUtil.inflate<ItemMineGridBinding>(LayoutInflater.from(mContent), R.layout.item_mine_grid, viewGroup, false)
        return ViewHolder(itemMineGridBinding)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.run {
            binding.bean = mList[position]
            binding.executePendingBindings()
            itemView.setOnClickListener {
                if (!TextUtils.isEmpty(name)){
                    appStatisticalUtil.addEvent(name + (position + 1) ,transformer)
                }
                ShopJumpUtil.openBanner(mContent,mList[position].type,mList[position].targetId,
                        mList[position].title,mList[position].source, mList[position].remark)
            }
        }
    }

    class ViewHolder : RecyclerView.ViewHolder{

        var binding: ItemMineGridBinding

        constructor(itemView: ItemMineGridBinding) : super(itemView.root){
            binding = itemView
        }
    }
}