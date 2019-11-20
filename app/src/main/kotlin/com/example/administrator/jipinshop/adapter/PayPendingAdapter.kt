package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.CountDownTimer
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.bean.MyFreeBean
import com.example.administrator.jipinshop.databinding.ItemMyFreepayBinding
import com.example.administrator.jipinshop.util.TimeUtil.dateAddOneDay
import com.example.administrator.jipinshop.util.TimeUtil.getCountTimeByLong

/**
 * @author 莫小婷
 * @create 2019/9/26
 * @Describe
 */
class PayPendingAdapter : RecyclerView.Adapter<PayPendingAdapter.ViewHolder>{

    private var mContext: Context
    private var mList: MutableList<MyFreeBean.DataBean>
    private lateinit var mOnClickListener: OnClickListener
    private val countDownCounters: SparseArray<CountDownTimer>

    fun setClick(onClickListener: OnClickListener){
        mOnClickListener = onClickListener
    }

    constructor(mList: MutableList<MyFreeBean.DataBean>, mContext: Context){
        this.mContext = mContext
        this.mList = mList
        this.countDownCounters = SparseArray()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ViewHolder {
        var binding = DataBindingUtil.inflate<ItemMyFreepayBinding>(LayoutInflater.from(mContext), R.layout.item_my_freepay, viewGroup, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return  mList.size
    }

    override fun onBindViewHolder(holder : ViewHolder, position: Int) {
        holder.mBinding.let {
            it.date = mList[position]
            it.executePendingBindings()
            it.itemBuy.setOnClickListener {
                mOnClickListener.onBuy(position)
            }
            var countDownTimer: CountDownTimer? = countDownCounters.get(it.itemTime.hashCode())
            countDownTimer?.cancel()
            var timer : Long = dateAddOneDay(mList[position].dendlineTime) - System.currentTimeMillis()
            if (timer <= 0){
                timer = 0
            }
            countDownTimer = object : CountDownTimer(timer, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    it.itemTime.text = "剩余购买时间：" + getCountTimeByLong(millisUntilFinished)
                }

                override fun onFinish() {
                    it.itemTime.text = "剩余购买时间：0秒"
                    it.itemBuy.setBackgroundResource(R.drawable.bg_tab_eva4)
                    it.itemBuy.setOnClickListener {}
                }
            }.start()
            //将此 countDownTimer 放入list.
            countDownCounters.put(it.itemTime.hashCode(), countDownTimer)
        }
    }

    class ViewHolder : RecyclerView.ViewHolder{

        var mBinding: ItemMyFreepayBinding

        constructor(binding: ItemMyFreepayBinding) : super(binding.root){
            mBinding= binding
        }
    }

    interface OnClickListener{
        fun onBuy(position: Int)  //进入淘宝购买
    }

    /**
     * 清空当前 CountTimeDown 资源
     */
    fun cancelAllTimers() {
        for (i in 0 until countDownCounters.size()) {
            var cdt = countDownCounters.get(countDownCounters.keyAt(i))
            cdt?.cancel()
        }
    }
}