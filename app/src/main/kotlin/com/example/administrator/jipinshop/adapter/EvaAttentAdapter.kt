package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.administrator.jipinshop.R
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

    private lateinit var mList: MutableList<String>
    private lateinit var context: Context
    private lateinit var mOnClickItem: OnClickItem

    fun setClick(onClickItem: OnClickItem){
        mOnClickItem = onClickItem
    }

    //父类有构造函数，子类的构造函数需要显示调用父类的构造函数
    constructor(mList: MutableList<String>, context: Context) : this(){
        this.mList = mList
        this.context = context
    }

    override fun getItemViewType(position: Int): Int {
        when(position % 5){
            0 -> return one
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
                    for ( i in 0 .. position ){
                        list.add("")
                    }
                    mPagerAdapter.notifyDataSetChanged()
                }
            }
            two -> {
                var twoViewHolder : TwoViewHolder = holder as TwoViewHolder
                twoViewHolder.run {

                    itemView.setOnClickListener{
                        mOnClickItem.onClickItem(position)
                    }
                }
            }
        }
    }

    inner class OneViewHolder : RecyclerView.ViewHolder{

        var binding : ItemEvaAttentBinding
        var list: MutableList<String>
        val mPagerAdapter: EvaAttent2Adapter

        constructor(binding: ItemEvaAttentBinding) : super(binding.root){
            this.binding = binding

            binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            list = mutableListOf()
            mPagerAdapter = EvaAttent2Adapter(list,context)
            val mySnapHelper = GravitySnapHelper(Gravity.START)
            mySnapHelper.attachToRecyclerView(binding.recyclerView)
            binding.recyclerView.adapter = mPagerAdapter
        }
    }

    class TwoViewHolder : RecyclerView.ViewHolder{

        var binding : ItemEvaAttent2Binding

        constructor(binding : ItemEvaAttent2Binding) : super(binding.root){
            this.binding = binding
        }
    }

    interface OnClickItem{
        fun onClickItem(position: Int)
    }
}