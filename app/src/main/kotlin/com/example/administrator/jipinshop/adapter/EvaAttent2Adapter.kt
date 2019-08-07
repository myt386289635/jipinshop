package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.databinding.ItemEvaAttent3Binding

/**
 * @author 莫小婷
 * @create 2019/8/6
 * @Describe
 */
class EvaAttent2Adapter() : RecyclerView.Adapter<EvaAttent2Adapter.ViewHolder>() {

    private lateinit var mList: MutableList<String>
    private lateinit var context: Context

    //父类有构造函数，子类的构造函数需要显示调用父类的构造函数
    constructor(mList: MutableList<String>, context: Context) : this(){
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

        }
    }


    class ViewHolder : RecyclerView.ViewHolder{

        var binding : ItemEvaAttent3Binding

        constructor(binding : ItemEvaAttent3Binding) : super(binding.root){
            this.binding = binding
        }
    }
}