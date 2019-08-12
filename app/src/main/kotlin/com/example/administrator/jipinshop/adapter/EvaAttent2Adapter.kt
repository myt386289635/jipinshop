package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.bean.EvaAttentBean
import com.example.administrator.jipinshop.databinding.ItemEvaAttent3Binding
import com.example.administrator.jipinshop.view.glide.GlideApp

/**
 * @author 莫小婷
 * @create 2019/8/6
 * @Describe
 */
class EvaAttent2Adapter() : RecyclerView.Adapter<EvaAttent2Adapter.ViewHolder>() {

    private lateinit var mList: MutableList<EvaAttentBean.DataBean.UserListBean>
    private lateinit var context: Context
    private lateinit var mOnClickItem: OnClickItem

    fun setClick(onClickItem: OnClickItem){
        mOnClickItem = onClickItem
    }

    //父类有构造函数，子类的构造函数需要显示调用父类的构造函数
    constructor(mList: MutableList<EvaAttentBean.DataBean.UserListBean>, context: Context) : this(){
        this.mList = mList
        this.context = context
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ViewHolder {
       var itemEvaAttent3Binding = DataBindingUtil.inflate<ItemEvaAttent3Binding>(LayoutInflater.from(context), R.layout.item_eva_attent3,viewGroup,false)
       var holder = ViewHolder(itemEvaAttent3Binding)
        return holder
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.run {
             var layoutParams : RecyclerView.LayoutParams = binding.itemContainer.layoutParams as RecyclerView.LayoutParams
            if ( position == mList.size - 1){
                layoutParams.rightMargin = context.resources.getDimension(R.dimen.x30).toInt()
            }else{
                layoutParams.rightMargin = 0
            }
            binding.itemContainer.layoutParams = layoutParams

            binding.data = mList[position]
            if (mList[position].imgs.size == 1){
                GlideApp.loderRoundImage(context,mList[position].imgs[0],binding.itemImage1)
            }else if (mList[position].imgs.size == 2){
                GlideApp.loderLeftRoundImage(context,mList[position].imgs[0],binding.itemImage1,10)
                GlideApp.loderRightRoundImage(context,mList[position].imgs[1],binding.itemImage2,10)
            }else if (mList[position].imgs.size == 3){
                GlideApp.loderLeftRoundImage(context,mList[position].imgs[0],binding.itemImage1,10)
                GlideApp.loderImage(context,mList[position].imgs[1],binding.itemImage2,0,0)
                GlideApp.loderRightRoundImage(context,mList[position].imgs[2],binding.itemImage3,10)
            }
            itemView.setOnClickListener {
                mOnClickItem.onClickUserinfo(mList[position].userId)
            }
            if (TextUtils.isEmpty(mList[position].follow) || mList[position].follow == "0"){
                binding.itemAttention.setTextColor(context.resources.getColor(R.color.color_E25838))
                binding.itemAttention.text = "关注"
                binding.itemAttention.setOnClickListener {
                    mOnClickItem.onClickAttent(mList[position].userId,position)
                }
            }else {
                binding.itemAttention.setTextColor(context.resources.getColor(R.color.color_9D9D9D))
                binding.itemAttention.text = "已关注"
                binding.itemAttention.setOnClickListener {
                    mOnClickItem.onClickAttentCancle(mList[position].userId,position)
                }
            }
            binding.executePendingBindings()
        }
    }


    class ViewHolder : RecyclerView.ViewHolder{

        var binding : ItemEvaAttent3Binding

        constructor(binding : ItemEvaAttent3Binding) : super(binding.root){
            this.binding = binding
        }
    }

    interface OnClickItem{
        fun onClickUserinfo(userId : String) //点击进入个人详情页
        fun onClickAttent(userId: String , pos: Int) //关注逻辑
        fun onClickAttentCancle(userId: String, pos: Int) //取消关注
    }
}