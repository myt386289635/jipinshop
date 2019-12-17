package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.bean.EvaAttentBean
import com.example.administrator.jipinshop.bean.EvaHotBean
import com.example.administrator.jipinshop.databinding.ItemEvaAttent2Binding
import com.example.administrator.jipinshop.databinding.ItemEvaHotBinding

/**
 * @author 莫小婷
 * @create 2019/8/7
 * @Describe
 */
class EvaHotAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val one = 1
    private val two = 2

    private lateinit var mList: MutableList<EvaAttentBean.DataBean.ArticleBean>
    private lateinit var mAds: MutableList<EvaHotBean.AdsBean>
    private lateinit var context: Context
    private lateinit var mOnClickItem: OnClickItem

    fun setClick(onClickItem: OnClickItem){
        mOnClickItem = onClickItem
    }

    constructor(mList: MutableList<EvaAttentBean.DataBean.ArticleBean>,mAds: MutableList<EvaHotBean.AdsBean>, context: Context) : this(){
        this.mList = mList
        this.mAds = mAds
        this.context = context
    }

    override fun getItemViewType(position: Int): Int {
        when(position == 0){
            true -> return one
            false -> return two
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): RecyclerView.ViewHolder {
        var holder : RecyclerView.ViewHolder? = null
        when(type){
            one -> {
                var itemEvaHotBinding = DataBindingUtil.inflate<ItemEvaHotBinding>(LayoutInflater.from(context), R.layout.item_eva_hot, viewGroup, false)
                holder = OneViewHolder(itemEvaHotBinding)
            }
            two -> {
                var itemEvaAttent2Binding = DataBindingUtil.inflate<ItemEvaAttent2Binding>(LayoutInflater.from(context) , R.layout.item_eva_attent2, viewGroup, false)
                holder = TwoViewHolder(itemEvaAttent2Binding)
            }
        }
        return holder!!
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var type = getItemViewType(position)
        when(type){
            one ->{
                var oneViewHolder : OneViewHolder = holder as OneViewHolder
                oneViewHolder.run {
                    list.clear()
                    list.addAll(mAds)
                    mPagerAdapter.notifyDataSetChanged()
                }
            }
            two -> {
                var twoViewHolder : TwoViewHolder = holder as TwoViewHolder
                var pos = position - 1
                twoViewHolder.run {
                    binding.data = mList[pos]
                    itemView.setOnClickListener{
                        mOnClickItem.onClickItem(pos)
                    }
                    if (mList[pos].user.follow == "0"){
                        //未关注
                        binding.itemAttention.setBackgroundResource(R.drawable.bg_attention)
                        binding.itemAttention.text = "关  注"
                        binding.itemAttention.setTextColor(context.resources.getColor(R.color.color_E25838))
                        binding.itemAttention.setOnClickListener {
                            mOnClickItem.onClickAttent(mList[pos].user.userId,pos)
                        }
                    }else{
                        //关注
                        binding.itemAttention.setBackgroundResource(R.drawable.bg_attentioned_new)
                        binding.itemAttention.text = "已关注"
                        binding.itemAttention.setTextColor(context.resources.getColor(R.color.color_9D9D9D))
                        binding.itemAttention.setOnClickListener {
                            mOnClickItem.onClickAttentCancle(mList[pos].user.userId,pos)
                        }
                    }
                    binding.itemUserImg.setOnClickListener {//进入个人详情页
                        mOnClickItem.onClickUserinfo(mList[pos].user.userId)
                    }
                    binding.executePendingBindings()
                }
            }
        }
    }

    inner class OneViewHolder : RecyclerView.ViewHolder{

        var binding : ItemEvaHotBinding
        var list: MutableList<EvaHotBean.AdsBean>
        val mPagerAdapter: EvaHotPageAdapter

        constructor(binding: ItemEvaHotBinding) : super(binding.root){
            this.binding = binding

            list = mutableListOf()
            mPagerAdapter = EvaHotPageAdapter(context,list)
            binding.viewPager.adapter = mPagerAdapter
            binding.viewPager.offscreenPageLimit = mList.size - 1
            binding.viewPager.pageMargin = context.resources.getDimension(R.dimen.x30).toInt()
            binding.viewpagerContainer.setOnTouchListener { v, event ->
                v.performClick()
                binding.viewPager.dispatchTouchEvent(event)
            }
        }
    }

    class TwoViewHolder : RecyclerView.ViewHolder{

        var binding : ItemEvaAttent2Binding

        constructor(binding : ItemEvaAttent2Binding) : super(binding.root){
            this.binding = binding
        }
    }

    interface OnClickItem{
        fun onClickItem(position: Int)//点击进入文章详情页面
        fun onClickAttent(userId: String , position: Int) //关注逻辑
        fun onClickAttentCancle(userId: String, position: Int) //取消关注
        fun onClickUserinfo(userId : String) //点击进入个人详情页
    }

}
