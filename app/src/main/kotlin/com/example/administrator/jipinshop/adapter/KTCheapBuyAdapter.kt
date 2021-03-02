package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.CountDownTimer
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.WebActivity
import com.example.administrator.jipinshop.bean.NewPeopleBean
import com.example.administrator.jipinshop.databinding.ItemCheapbuyContent2Binding
import com.example.administrator.jipinshop.databinding.ItemCheapbuyContentBinding
import com.example.administrator.jipinshop.databinding.ItemCheapbuyHeadBinding
import com.example.administrator.jipinshop.netwrok.RetrofitModule
import com.example.administrator.jipinshop.util.TimeUtil

/**
 * @author 莫小婷
 * @create 2020/1/3
 * @Describe
 */
class KTCheapBuyAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private val HEAD = 1
    private val CONTENT = 2
    private val HEAD2 = 3

    private var mList: MutableList<NewPeopleBean.DataBean.AllowanceGoodsListBean>
    private var mContent: Context
    private var mOnClickItem: OnClickItem? = null
    private var mBean: NewPeopleBean? = null
    private var countDownCounters: SparseArray<CountDownTimer>

    fun setBean(bean: NewPeopleBean?){
        mBean = bean
    }

    fun setOnClickItem(onClickItem: OnClickItem?) {
        mOnClickItem = onClickItem
    }


    constructor(context: Context, mList: MutableList<NewPeopleBean.DataBean.AllowanceGoodsListBean>){
        mContent = context
        this.mList = mList
        this.countDownCounters = SparseArray()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0){
            HEAD
        }else if ( position <= 3){
            HEAD2
        } else {
            CONTENT
        }
    }

    //为RecyclerView添加头布局
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is GridLayoutManager) {
            val gridLayoutManager = layoutManager as GridLayoutManager?
            gridLayoutManager!!.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (getItemViewType(position) == HEAD2) {
                        1
                    } else {
                        gridLayoutManager.spanCount
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): RecyclerView.ViewHolder {
        var holder : RecyclerView.ViewHolder? = null
        when(type){
            HEAD -> {
                var itemCheapbuyHeadBinding = DataBindingUtil.inflate<ItemCheapbuyHeadBinding>(LayoutInflater.from(mContent), R.layout.item_cheapbuy_head, viewGroup, false)
                holder = HeadViewHolder(itemCheapbuyHeadBinding)
            }
            HEAD2 -> {
                var itemContentBinding = DataBindingUtil.inflate<ItemCheapbuyContent2Binding>(LayoutInflater.from(mContent) , R.layout.item_cheapbuy_content2, viewGroup, false)
                holder = ContentViewHolder2(itemContentBinding)
            }
            CONTENT -> {
                var itemNewForeBinding = DataBindingUtil.inflate<ItemCheapbuyContentBinding>(LayoutInflater.from(mContent) , R.layout.item_cheapbuy_content, viewGroup, false)
                holder = ContentViewHolder(itemNewForeBinding)
            }
        }
        return holder!!
    }

    override fun getItemCount(): Int {
        return if (mList.size == 0){
            0
        }else{
            mList.size + 1
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var type = getItemViewType(position)
        when(type){
            HEAD -> {
                var headViewHolder : HeadViewHolder = holder as HeadViewHolder
                headViewHolder.run {
                    mBean?.let {
                        binding.itemCheapPrice.text = it.data.allowance + "元"
                        binding.itemTempPrice.text = it.data.tmpAllowance + "元"
                        //倒计时
                        var timer = (it.data.endTime * 1000) - System.currentTimeMillis()
                        var countDownTimer :CountDownTimer? = countDownCounters.get(binding.itemTime.hashCode())
                        countDownTimer?.cancel()
                        if (timer > 0) {
                            countDownTimer = object : CountDownTimer(timer, 1000) {
                                override fun onTick(millisUntilFinished: Long) {
                                    binding.itemTime.text = TimeUtil.getCountTimeByLong4(millisUntilFinished)
                                }

                                override fun onFinish() {
                                    binding.itemTime.text = "0秒"
                                }
                            }.start()
                            countDownCounters.put(binding.itemTime.hashCode(), countDownTimer)
                        }else{
                            binding.itemTime.text = "0秒"
                        }
                    }
                    binding.itemRule.setOnClickListener {
                        mContent.startActivity(Intent(mContent, WebActivity::class.java)
                                .putExtra(WebActivity.url, RetrofitModule.H5_URL + "th-rule.html")
                                .putExtra(WebActivity.title, "规则说明")
                        )
                    }
                }
            }
            HEAD2 -> {
                var viewHolder : ContentViewHolder2 = holder as ContentViewHolder2
                var pos = position - 1
                viewHolder.run {
                    binding.data = mList[pos]
                    binding.executePendingBindings()
                    if (mList[pos].isBuy == "1"){
                        binding.itemTag.setImageResource(R.mipmap.bg_cheap11)
                    }else{
                        binding.itemTag.setImageResource(R.mipmap.bg_cheap6)
                    }
                    itemView.setOnClickListener {
                        mOnClickItem?.onBuy(pos)
                    }
                }
            }
            CONTENT -> {
                var contentViewHolder : ContentViewHolder = holder as ContentViewHolder
                var pos = position - 1
                contentViewHolder.run {
                    binding.data = mList[pos]
                    binding.executePendingBindings()
                    if (mList[pos].isBuy == "1"){
                        binding.itemTag.setImageResource(R.mipmap.bg_cheap11)
                    }else{
                        binding.itemTag.setImageResource(R.mipmap.bg_cheap6)
                    }
                    binding.itemOtherPrice.setTv(true)
                    binding.itemOtherPrice.setColor(R.color.color_9D9D9D)
                    itemView.setOnClickListener {
                        mOnClickItem?.onBuy(pos)
                    }
                }
            }
        }
    }

    inner class ContentViewHolder: RecyclerView.ViewHolder{

        var binding: ItemCheapbuyContentBinding

        constructor(binding: ItemCheapbuyContentBinding) : super(binding.root){
            this.binding = binding
        }
    }

    inner class ContentViewHolder2 :  RecyclerView.ViewHolder{

        var binding: ItemCheapbuyContent2Binding

        constructor(binding: ItemCheapbuyContent2Binding) : super(binding.root){
            this.binding = binding
        }
    }

    inner class HeadViewHolder: RecyclerView.ViewHolder{

        var binding: ItemCheapbuyHeadBinding

        constructor(binding: ItemCheapbuyHeadBinding) : super(binding.root){
            this.binding = binding
        }
    }

    interface OnClickItem {
        fun onBuy(position: Int)
    }
}