package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.databinding.ItemEvaAttent2Binding
import com.example.administrator.jipinshop.databinding.ItemEvaAttentBinding
import com.example.administrator.jipinshop.databinding.ItemEvaHotBinding
import com.example.administrator.jipinshop.util.ToastUtil

/**
 * @author 莫小婷
 * @create 2019/8/7
 * @Describe
 */
class EvaHotAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val one = 1
    private val two = 2

    private lateinit var mList: MutableList<String>
    private lateinit var context: Context

    constructor(mList: MutableList<String>, context: Context) : this(){
        this.mList = mList
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
                    for ( i in 0 .. 3 ){
                        list.add("https://weiliicimg9.pstatp.com/weili/l/480566081290502202.webp")
                    }
                    mPagerAdapter.notifyDataSetChanged()
                }
            }
            two -> {
                var twoViewHolder : TwoViewHolder = holder as TwoViewHolder
                twoViewHolder.run {

                    itemView.setOnClickListener{
                        ToastUtil.show("" + position)
                    }
                }
            }
        }
    }

    inner class OneViewHolder : RecyclerView.ViewHolder{

        var binding : ItemEvaHotBinding
        var list: MutableList<String>
        val mPagerAdapter: InvitationNewAdapter

        constructor(binding: ItemEvaHotBinding) : super(binding.root){
            this.binding = binding

            list = mutableListOf()
            mPagerAdapter = InvitationNewAdapter(context,list)
            mPagerAdapter.setImgCenter(true)
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


}
