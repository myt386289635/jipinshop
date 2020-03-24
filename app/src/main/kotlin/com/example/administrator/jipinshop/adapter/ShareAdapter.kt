package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.databinding.ItemShareImageBinding
import com.example.administrator.jipinshop.view.glide.GlideApp

/**
 * @author 莫小婷
 * @create 2020/3/19
 * @Describe
 */
class ShareAdapter : RecyclerView.Adapter<ShareAdapter.ViewHolder> {

    private var mList: MutableList<String>
    private var mContext: Context
    private lateinit var mSet: MutableList<Int> //记录所有图片是否被选中，1选中，0未选中
    private lateinit var mOnClickItem: OnClickItem
    private var mPosition: Int = 0 //推广图位置

    constructor(mList: MutableList<String>, mContext: Context) {
        this.mList = mList
        this.mContext = mContext
    }

    fun setSet(set: MutableList<Int>){
        mSet = set
    }

    fun setPosition(position: Int){
        mPosition = position
    }

    fun setOnClick(onClickItem: OnClickItem){
        mOnClickItem = onClickItem
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ViewHolder {
        var itemShareImageBinding = DataBindingUtil.inflate<ItemShareImageBinding>(LayoutInflater.from(mContext), R.layout.item_share_image, viewGroup, false)
        return ViewHolder(itemShareImageBinding)
    }

    override fun getItemCount(): Int {
       return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.run {
            GlideApp.loderImage(mContext,mList[pos],mBinding.itemImage,0,0)
            mBinding.itemCheckBox.setOnCheckedChangeListener(null)
            mBinding.itemCheckBox.isChecked = mSet[pos] == 1
            if ( pos == mPosition){
                mBinding.itemTag.visibility = View.VISIBLE
            }else{
                mBinding.itemTag.visibility = View.GONE
            }
            mBinding.itemImage.setOnClickListener {
                mOnClickItem.onItem(it,pos)
            }
            mBinding.itemCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
                mOnClickItem.onClickCheck(isChecked , pos)
            }
        }
    }

    class ViewHolder : RecyclerView.ViewHolder{

        var mBinding: ItemShareImageBinding

        constructor(binding: ItemShareImageBinding) : super(binding.root){
            mBinding = binding
        }
    }

    interface OnClickItem{
        fun onClickCheck(isChecked : Boolean , pos: Int)
        fun onItem(view: View, position: Int)
    }
}