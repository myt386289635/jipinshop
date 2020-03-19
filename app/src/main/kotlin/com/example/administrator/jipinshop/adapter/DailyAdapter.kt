package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.bean.CircleListBean
import com.example.administrator.jipinshop.databinding.ItemDailyBinding
import com.example.administrator.jipinshop.util.ToastUtil

/**
 * @author 莫小婷
 * @create 2020/3/17
 * @Describe
 */
class DailyAdapter :RecyclerView.Adapter<DailyAdapter.ViewHolder>{

    private var mList: MutableList<CircleListBean.DataBean>
    private var mContext: Context
    private lateinit var mOnClickItem: OnClickItem

    constructor(mList: MutableList<CircleListBean.DataBean>, mContext: Context){
        this.mList = mList
        this.mContext = mContext
    }

    fun setOnClick(onClickItem: OnClickItem){
        mOnClickItem = onClickItem
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): ViewHolder {
        var itemDailyBinding = DataBindingUtil.inflate<ItemDailyBinding>(LayoutInflater.from(mContext), R.layout.item_daily, viewGroup, false)
        var holder = ViewHolder(itemDailyBinding)
        return holder
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.run {
            mBinding.data = mList[pos]
            for (i in mList[pos].commentList.indices){
                when(i){
                    0 -> {
                        mBinding.comment1 = mList[pos].commentList[0]
                    }
                    1 -> {
                        mBinding.comment2 = mList[pos].commentList[1]
                    }
                    2 -> {
                        mBinding.comment3 = mList[pos].commentList[2]
                    }
                    3 -> {
                        mBinding.comment4 = mList[pos].commentList[3]
                    }
                    4 -> {
                        mBinding.comment5 = mList[pos].commentList[4]
                    }
                }
            }
            mBinding.executePendingBindings()
            holder.list.clear()
            holder.list.addAll(mList[pos].imgList)
            holder.setGridViewHeight()
            holder.mGirdAdapter.setOnClick(object : DailyGirdAdapter.OnGridItem{
                override fun onGrid(position: Int, view: View) {
//                    ToastUtil.show("点击图片位置$position")
                }
            })
            holder.mGirdAdapter.notifyDataSetChanged()
            mBinding.itemContainer.setOnClickListener {
                if (mList[pos].goodsInfo != null){
                    mOnClickItem.onDetailClick(pos)
                }
            }
            mBinding.itemShare.setOnClickListener {
                mOnClickItem.onShareClick(pos)
            }
            mBinding.itemGoodShare.setOnClickListener {
                mOnClickItem.onShareClick(pos)
            }
            mBinding.itemCommentContainer1.setOnClickListener {}
            mBinding.itemCommentContainer2.setOnClickListener {}
            mBinding.itemCommentContainer3.setOnClickListener {}
            mBinding.itemCommentContainer4.setOnClickListener {}
            mBinding.itemCommentContainer5.setOnClickListener {}
            mBinding.itemCommentCopy1.setOnClickListener {
                mOnClickItem.onCommentClick(mList[pos].commentList[0].copyContent)
            }
            mBinding.itemCommentCopy2.setOnClickListener {
                mOnClickItem.onCommentClick(mList[pos].commentList[1].copyContent)
            }
            mBinding.itemCommentCopy3.setOnClickListener {
                mOnClickItem.onCommentClick(mList[pos].commentList[2].copyContent)
            }
            mBinding.itemCommentCopy4.setOnClickListener {
                mOnClickItem.onCommentClick(mList[pos].commentList[3].copyContent)
            }
            mBinding.itemCommentCopy5.setOnClickListener {
                mOnClickItem.onCommentClick(mList[pos].commentList[4].copyContent)
            }
        }
    }

     inner class ViewHolder : RecyclerView.ViewHolder{

         var mBinding : ItemDailyBinding
         var mGirdAdapter: DailyGirdAdapter
         var list: MutableList<String>

        constructor(binding: ItemDailyBinding) : super(binding.root){
            mBinding = binding
            list = mutableListOf()
            mGirdAdapter = DailyGirdAdapter(list, mContext)
            binding.itemGridView.adapter = mGirdAdapter
            binding.itemGridView.selector = ColorDrawable(Color.TRANSPARENT)
        }

         //刷新时，需要重新设置一下gridView高度
         fun setGridViewHeight() {
             // 固定列宽，有多少列
             val numColumns = 3
             var totalHeight = 0
             // 计算每一列的高度之和
             var i = 0
             while (i < mGirdAdapter.count) {
                 // 获取gridview的每一个item
                 val listItem = mGirdAdapter.getView(i, null, mBinding.itemGridView)
                 listItem.measure(0, 0)
                 // 获取item的高度和
                 totalHeight += listItem.measuredHeight
                 i += numColumns
             }
             // 获取gridview的布局参数
             val params = mBinding.itemGridView.layoutParams
             params.height = totalHeight
             mBinding.itemGridView.layoutParams = params
         }
    }

    interface OnClickItem{
        fun onDetailClick(position: Int)
        fun onShareClick(position: Int)
        fun onCommentClick(content: String)
    }
}