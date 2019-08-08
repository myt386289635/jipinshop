package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.databinding.ItemEvaEva1Binding
import com.example.administrator.jipinshop.databinding.ItemEvaEva2Binding
import com.example.administrator.jipinshop.databinding.ItemEvaEva3Binding
import com.example.administrator.jipinshop.util.ToastUtil

/**
 * @author 莫小婷
 * @create 2019/8/7
 * @Describe
 */
class EvaEvaAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    private val one = 1
    private val two = 2
    private val three = 3

    private lateinit var mList1: MutableList<String>//开箱评测
    private lateinit var mList2: MutableList<String>//对比评测
    private lateinit var context: Context

    constructor(mList1: MutableList<String>, mList2: MutableList<String>, context: Context) : this(){
        this.mList1 = mList1
        this.mList2 = mList2
        this.context = context
    }

    override fun getItemViewType(position: Int): Int {//mList.size 是开箱评测列表数量
        if (position == 0){
            return one
        }else if (position <= mList1.size && position < 4){
            return two
        }else if (position == mList1.size + 1 && mList1.size < 4){
            return one
        }else if (position == 4 && mList1.size >= 4){
            return one
        } else{
            return three
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): RecyclerView.ViewHolder {
       var holder : RecyclerView.ViewHolder? = null
        when (type){
            one -> {
                var itemEvaEva1Binding = DataBindingUtil.inflate<ItemEvaEva1Binding>(LayoutInflater.from(context), R.layout.item_eva_eva1, viewGroup, false)
                holder = OneHolderView(itemEvaEva1Binding)
            }
            two -> {
                var itemEvaEva2Binding = DataBindingUtil.inflate<ItemEvaEva2Binding>(LayoutInflater.from(context), R.layout.item_eva_eva2, viewGroup, false)
                holder = TwoHolderView(itemEvaEva2Binding)
            }
            three -> {
                var itemEvaEva3Binding = DataBindingUtil.inflate<ItemEvaEva3Binding>(LayoutInflater.from(context), R.layout.item_eva_eva3, viewGroup, false)
                holder = ThreeHolderView(itemEvaEva3Binding)
            }
        }
        return holder!!
    }

    override fun getItemCount(): Int {
        return if (mList1.size > 3 && mList2.size >= 3){
            8
        }else if (mList1.size > 3 && mList2.size < 3){
            5 + mList2.size
        }else if (mList2.size > 3 && mList1.size <= 3){
            5 + mList1.size
        }else {
            mList1.size + mList2.size + 2
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var type = getItemViewType(position)
        when(type){
            one ->{
                var oneViewHolder : OneHolderView = holder as OneHolderView
                oneViewHolder.run {
                    if (position == 0){
                        mBinding.itemTitle.text = "开箱评测"
                        mBinding.itemAll.setOnClickListener {
                            ToastUtil.show("开箱评测")
                        }
                    }else{
                        mBinding.itemTitle.text = "对比评测"
                        mBinding.itemAll.setOnClickListener {
                            ToastUtil.show("对比评测")
                        }
                    }
                }
            }
            two -> {
                var twoViewHolder : TwoHolderView = holder as TwoHolderView
                twoViewHolder.run {


                }
            }
            three -> {
                var threeViewHolder : ThreeHolderView = holder as ThreeHolderView
            }
        }
    }

    class OneHolderView : RecyclerView.ViewHolder {

        var mBinding : ItemEvaEva1Binding

        constructor(binding: ItemEvaEva1Binding) : super(binding.root){
            mBinding = binding
        }

    }

    class TwoHolderView : RecyclerView.ViewHolder{

        var mBinding: ItemEvaEva2Binding

        constructor(binding: ItemEvaEva2Binding) : super(binding.root){
            mBinding = binding
        }
    }

    class ThreeHolderView : RecyclerView.ViewHolder{

        var mBinding: ItemEvaEva3Binding

        constructor(binding: ItemEvaEva3Binding) : super(binding.root){
            mBinding = binding
        }
    }
}