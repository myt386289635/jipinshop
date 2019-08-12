package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.bean.EvaAttentBean
import com.example.administrator.jipinshop.databinding.ItemEvaAttent2Binding
import com.example.administrator.jipinshop.databinding.ItemEvaAttentBinding
import com.example.administrator.jipinshop.util.snap.GravitySnapHelper

/**
 * @author 莫小婷
 * @create 2019/8/6
 * @Describe
 */
class EvaAttentAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val one = 1
    private val two = 2

    private lateinit var mList: MutableList<EvaAttentBean.DataBean>
    private lateinit var context: Context
    private lateinit var mOnClickItem: OnClickItem

    fun setClick(onClickItem: OnClickItem){
        mOnClickItem = onClickItem
    }

    //父类有构造函数，子类的构造函数需要显示调用父类的构造函数
    constructor(mList: MutableList<EvaAttentBean.DataBean>, context: Context) : this(){
        this.mList = mList
        this.context = context
    }

    override fun getItemViewType(position: Int): Int {
        when(mList[position].type){
            2 -> return one
            else -> return two
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): RecyclerView.ViewHolder {
        var holder : RecyclerView.ViewHolder? = null
        when(type){
            one -> {
                var itemEvaAttentBinding = DataBindingUtil.inflate<ItemEvaAttentBinding>(LayoutInflater.from(context), R.layout.item_eva_attent, viewGroup, false)
                holder = OneViewHolder(itemEvaAttentBinding)
            }
            two -> {
                var itemEvaAttent2Binding = DataBindingUtil.inflate<ItemEvaAttent2Binding>(LayoutInflater.from(context) ,R.layout.item_eva_attent2, viewGroup, false)
                holder = TwoViewHolder(itemEvaAttent2Binding)
            }
        }
        return holder!!
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder : RecyclerView.ViewHolder, position: Int) {
        var type = getItemViewType(position)
        when(type){
            one ->{
                var oneViewHolder : OneViewHolder = holder as OneViewHolder
                oneViewHolder.run {
                    list.clear()
                    if (mList[position].userList != null){
                        list.addAll(mList[position].userList)
                    }
                    mPagerAdapter.setClick(object : EvaAttent2Adapter.OnClickItem{
                        override fun onClickAttent(userId: String, pos: Int) {
                           mOnClickItem.onClickAttent2(userId,pos,position)
                        }

                        override fun onClickAttentCancle(userId: String, pos: Int) {
                            mOnClickItem.onClickAttentCancle2(userId,pos,position)
                        }

                        override fun onClickUserinfo(userId: String) {//进入个人详情页
                            mOnClickItem.onClickUserinfo(userId)
                        }


                    })
                    mPagerAdapter.notifyDataSetChanged()
                }
            }
            two -> {
                var twoViewHolder : TwoViewHolder = holder as TwoViewHolder
                twoViewHolder.run {
                    binding.data = mList[position].article
                    itemView.setOnClickListener{//进入文章详情
                        mOnClickItem.onClickItem(position)
                    }
                    binding.itemUserImg.setOnClickListener {//进入个人详情页
                        mOnClickItem.onClickUserinfo(mList[position].article.user.userId)
                    }
                    if (mList[position].article.user.follow == "0"){
                        binding.itemAttention.visibility = View.VISIBLE
                        binding.itemAttention.setBackgroundResource(R.drawable.bg_attention_new)
                        binding.itemAttention.text = "关  注"
                        binding.itemAttention.setTextColor(context.resources.getColor(R.color.color_E25838))
                        binding.itemAttention.setOnClickListener {
                            //关注
                            mOnClickItem.onClickAttent(mList[position].article.user.userId,position)
                        }
                    }else if(mList[position].article.user.follow == "2"){
                        binding.itemAttention.visibility = View.VISIBLE
                        binding.itemAttention.setBackgroundResource(R.drawable.bg_attentioned_new)
                        binding.itemAttention.text = "已关注"
                        binding.itemAttention.setTextColor(context.resources.getColor(R.color.color_9D9D9D))
                        binding.itemAttention.setOnClickListener {
                            //取消关注
                            mOnClickItem.onClickAttentCancle(mList[position].article.user.userId,position)
                        }
                    } else{
                        binding.itemAttention.visibility = View.GONE
                    }
                    binding.executePendingBindings()
                }
            }
        }
    }

    inner class OneViewHolder : RecyclerView.ViewHolder{

        var binding : ItemEvaAttentBinding
        var list: MutableList<EvaAttentBean.DataBean.UserListBean>
        val mPagerAdapter: EvaAttent2Adapter

        constructor(binding: ItemEvaAttentBinding) : super(binding.root){
            this.binding = binding

            binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            list = mutableListOf()
            mPagerAdapter = EvaAttent2Adapter(list,context)
            val mySnapHelper = GravitySnapHelper(Gravity.START)
            mySnapHelper.attachToRecyclerView(binding.recyclerView)
            binding.recyclerView.adapter = mPagerAdapter
            binding.recyclerView.isFocusable = false //让recyclerView失去焦点
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
        fun onClickUserinfo(userId : String) //点击进入个人详情页
        fun onClickAttent(userId: String , position: Int) //关注逻辑
        fun onClickAttentCancle(userId: String, position: Int) //取消关注
        fun onClickAttent2(userId: String , pos: Int , fpos : Int) //推荐列表：关注逻辑
        fun onClickAttentCancle2(userId: String, pos: Int , fpos: Int) //推荐列表：取消关注
    }
}