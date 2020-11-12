package com.example.administrator.jipinshop.adapter

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Handler
import android.os.Looper
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.school.SchoolSpecialActivity
import com.example.administrator.jipinshop.bean.SchoolHomeBean
import com.example.administrator.jipinshop.bean.TbkIndexBean
import com.example.administrator.jipinshop.databinding.ItemSchoolContentBinding
import com.example.administrator.jipinshop.databinding.ItemSchoolHeadBinding
import com.example.administrator.jipinshop.util.DistanceHelper
import com.example.administrator.jipinshop.util.ShopJumpUtil
import com.example.administrator.jipinshop.util.WeakRefHandler
import com.example.administrator.jipinshop.view.viewpager.TouchViewPager

/**
 * @author 莫小婷
 * @create 2020/7/14
 * @Describe
 */
class KTSchoolAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private var HEAD = 1
    private var CONTENT = 2

    private var mBean: SchoolHomeBean? = null
    private var mContext: Context

    constructor(mContext: Context){
        this.mContext = mContext
    }

    fun setDate(bean: SchoolHomeBean?){
        mBean = bean
    }

    override fun getItemViewType(position: Int): Int {
       return when(position){
            0->{
                HEAD
            }
            else -> {
                CONTENT
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, type: Int): RecyclerView.ViewHolder {
        var holder : RecyclerView.ViewHolder? = null
        when(type){
            HEAD -> {
                var itemSchoolHeadBinding = DataBindingUtil.inflate<ItemSchoolHeadBinding>(LayoutInflater.from(mContext), R.layout.item_school_head, viewGroup, false)
                holder = HeadViewHolder(itemSchoolHeadBinding)
            }
            CONTENT -> {
                var itemSchoolContentBinding = DataBindingUtil.inflate<ItemSchoolContentBinding>(LayoutInflater.from(mContext), R.layout.item_school_content, viewGroup, false)
                holder = ContentViewHolder(itemSchoolContentBinding)
            }
        }
        return holder!!
    }

    override fun getItemCount(): Int {
        return mBean?.let {
            it.data.categoryList.size + 1
        } ?: 0
    }

    override fun onBindViewHolder(holder : RecyclerView.ViewHolder, position: Int) {
        var type = getItemViewType(position)
        when(type){
            HEAD -> {
                var headViewHolder : HeadViewHolder = holder as HeadViewHolder
                headViewHolder.run {
                    //轮播图
                    pagerAdapter.setOnClickItem(object : KTPagerAdapter.OnClickItem {
                        override fun onClickItem(postion: Int) {
                            ShopJumpUtil.openBanner(mContext,adListBeans[postion].type,
                                    adListBeans[postion].objectId,adListBeans[postion].name,
                                    adListBeans[postion].source,adListBeans[postion].remark)
                        }
                    })
                    mBean?.let {
                        adListBeans.clear()
                        if (it.data.adList.size > 1){
                            for (i in 0 .. (it.data.adList.size + 1)){
                                when (i) {
                                    0 -> {//加入最后一个
                                        adListBeans.add(it.data.adList[it.data.adList.size - 1])
                                    }
                                    it.data.adList.size + 1 -> {//加入第一个
                                        adListBeans.add(it.data.adList[0])
                                    }
                                    else -> {//正常数据
                                        adListBeans.add(it.data.adList[i - 1])
                                    }
                                }
                            }
                        }else{
                            adListBeans.addAll(it.data.adList)
                        }
                        initBanner()
                    }
                    //九宫格
                    gridList.clear()
                    gridList.addAll(mBean!!.data.boxList)
                    if (gridList.size > 4){
                        binding.gridPoint.visibility = View.VISIBLE
                    }else{
                        binding.gridPoint.visibility = View.GONE
                    }
                    gridAdapter.notifyDataSetChanged()
                }
            }
            CONTENT -> {
                var contentViewHolder : ContentViewHolder = holder as ContentViewHolder
                contentViewHolder.run {
                    var pos = position - 1
                    binding.data = mBean!!.data.categoryList[pos]
                    binding.executePendingBindings()
                    list.clear()
                    list.addAll(mBean!!.data.categoryList[pos].courseList)
                    adapter.notifyDataSetChanged()
                    adapter.setClick(object : KTSchoolRVAdapter.OnMoreClick{
                        override fun onMore() {
                            mContext.startActivity(Intent(mContext, SchoolSpecialActivity::class.java)
                                    .putExtra("categoryId",mBean!!.data.categoryList[pos].id)
                                    .putExtra("title",mBean!!.data.categoryList[pos].name)
                            )
                        }
                    })
                    binding.itemMore.setOnClickListener {
                        //更多
                        mContext.startActivity(Intent(mContext, SchoolSpecialActivity::class.java)
                                .putExtra("categoryId",mBean!!.data.categoryList[pos].id)
                                .putExtra("title",mBean!!.data.categoryList[pos].name)
                        )
                    }
                }
            }
        }
    }

    inner class HeadViewHolder : RecyclerView.ViewHolder, ViewPager.OnPageChangeListener {

        lateinit var binding: ItemSchoolHeadBinding
        //轮播图
        var pagerAdapter: KTPagerAdapter
        var point: MutableList<ImageView>
        var adListBeans : MutableList<TbkIndexBean.DataBean.Ad1ListBean>
        private var currentItem: Int = 0
        private var count : Int = 0
        private var mCallback : Handler.Callback = Handler.Callback{
            if (it.what == 100) {
                if (count > 1){
                    currentItem = currentItem % (count + 1) + 1
                    if (currentItem == 1) {
                        binding.mainViewpager.setCurrentItem(currentItem, false)
                        handler.sendEmptyMessage(100)
                    }else{
                        binding.mainViewpager.currentItem = currentItem
                        handler.sendEmptyMessageDelayed(100,5000)
                    }
                }
            }
            true
        }
        var handler = WeakRefHandler(mCallback, Looper.getMainLooper())
        //九宫格
        var gridList: MutableList<SchoolHomeBean.DataBean.BoxListBean>
        var gridAdapter: KTSchoolGVAdapter

        constructor(itemView: ItemSchoolHeadBinding) : super(itemView.root){
            binding = itemView
            //轮播图
            pagerAdapter = KTPagerAdapter(mContext)
            point = mutableListOf()
            adListBeans = mutableListOf()
            pagerAdapter.setList(adListBeans)
            pagerAdapter.setPoint(point)
            pagerAdapter.setImgCenter(true)
            binding.mainViewpager.adapter = pagerAdapter
            binding.mainViewpager.addOnPageChangeListener(this)
            binding.mainViewpager.setOnTouchListener(object : TouchViewPager.OnTouchListener {
                override fun startAutoPlay() {
                    handler.removeCallbacksAndMessages(null)
                    handler.sendEmptyMessageDelayed(100,5000)
                }

                override fun stopAutoPlay() {
                    handler.removeCallbacksAndMessages(null)
                }

            })

            //九宫格
            gridList = mutableListOf()
            gridAdapter = KTSchoolGVAdapter(gridList,mContext)
            var linearLayoutManager = LinearLayoutManager(mContext,LinearLayout.HORIZONTAL,false)
            binding.gridViewpager.layoutManager= linearLayoutManager
            binding.gridViewpager.adapter = gridAdapter
            binding.gridViewpager.isNestedScrollingEnabled = false
            binding.gridViewpager.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    //划出去的宽度
                    var isResult = getResult(linearLayoutManager)
                    //可划出去的总宽度
                    var totleWith = getTotleWith(linearLayoutManager)
                    //线条的总宽度
                    var lineWith = mContext.resources.getDimension(R.dimen.x60)
                    //结果
                    var result  = (lineWith / totleWith) * isResult
                    var layoutParams =  binding.point.layoutParams as RelativeLayout.LayoutParams
                    layoutParams.leftMargin = result.toInt()
                    binding.point.layoutParams = layoutParams
                }
            })
        }

        override fun onPageScrollStateChanged(state: Int) {
            when (state) {
                0//No operation
                -> if (currentItem === 0) {
                    binding.mainViewpager.setCurrentItem(count, false)
                } else if (currentItem === count + 1) {
                    binding.mainViewpager.setCurrentItem(1, false)
                }
                1//start Sliding
                -> if (currentItem === count + 1) {
                    binding.mainViewpager.setCurrentItem(1, false)
                } else if (currentItem === 0) {
                    binding.mainViewpager.setCurrentItem(count, false)
                }
                2//end Sliding
                -> {
                }
            }
        }

        override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}

        override fun onPageSelected(position: Int) {
            for (i in point.indices) {
                if (i == toRealPosition(position)) {
                    point[i].setImageResource(R.drawable.banner_down)
                } else {
                    point[i].setImageResource(R.drawable.banner_up)
                }
            }
            currentItem = position
        }

        fun initBanner() {
            count = adListBeans.size - 2
            handler.removeCallbacksAndMessages(null)//刷新时，要删除handler的请求
            handler.sendEmptyMessageDelayed(100,5000)
            binding.mainViewpager.setCurrentItem(1,false)
            point.clear()
            binding.mainPoint.removeAllViews()
            for (i in 0 until count) {
                val imageView = ImageView(mContext)
                point.add(imageView)
                if (i == 0) {
                    imageView.setImageResource(R.drawable.banner_down)
                } else {
                    imageView.setImageResource(R.drawable.banner_up)
                }
                val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                layoutParams.leftMargin = mContext.resources.getDimensionPixelSize(R.dimen.x4)
                layoutParams.rightMargin = mContext.resources.getDimensionPixelSize(R.dimen.x4)
                binding.mainPoint.addView(imageView, layoutParams)
            }
            pagerAdapter.notifyDataSetChanged()
            if (count > 1){
                binding.mainPoint.visibility = View.VISIBLE
            }else{
                binding.mainPoint.visibility = View.INVISIBLE
            }
        }

        //返回真实的位置
        fun toRealPosition(position: Int): Int {
            var realPosition: Int = 0
            if (count !== 0) {
                realPosition = (position - 1) % count
            }
            if (realPosition < 0)
                realPosition += count
            return realPosition
        }

        fun getResult(linearLayoutManager: LinearLayoutManager) : Int{
            //找到即将移出屏幕Item的position,position是移出屏幕item的数量
            var position = linearLayoutManager.findFirstVisibleItemPosition()
            //根据position找到这个Item
            var firstVisiableChildView = linearLayoutManager.findViewByPosition(position)
            //获取Item的宽
            var itemWidth = firstVisiableChildView?.width ?: 0
            //算出该Item还未移出屏幕的高度
            var itemRight = firstVisiableChildView?.right ?: 0
            //position移出屏幕的数量*高度得出移动的距离
            var iposition = position * itemWidth
            //因为横着的RecyclerV第一个取到的Item position为零所以计算时需要加一个宽
            var iResult = iposition - itemRight + itemWidth
            return  iResult
        }

        fun getTotleWith(linearLayoutManager: LinearLayoutManager) : Int{
            //找到即将移出屏幕Item的position,position是移出屏幕item的数量
            var position = linearLayoutManager.findFirstVisibleItemPosition()
            //根据position找到这个Item
            var firstVisiableChildView = linearLayoutManager.findViewByPosition(position)
            //获取Item的宽
            var itemWidth = firstVisiableChildView?.width ?: 0

            return ((itemWidth * gridList.size) - DistanceHelper.getAndroiodScreenwidthPixels(mContext))
        }
    }

    inner class ContentViewHolder : RecyclerView.ViewHolder{

        var binding: ItemSchoolContentBinding
        var list: MutableList<SchoolHomeBean.DataBean.CategoryListBean.CourseListBean>
        var adapter : KTSchoolRVAdapter

        constructor(itemView: ItemSchoolContentBinding) : super(itemView.root){
            binding = itemView

            list = mutableListOf()
            var linearLayoutManager = LinearLayoutManager(mContext,LinearLayout.HORIZONTAL,false)
            adapter = KTSchoolRVAdapter(list,mContext)
            binding.itemRv.layoutManager= linearLayoutManager
            binding.itemRv.adapter = adapter
            binding.itemRv.isNestedScrollingEnabled = false
        }
    }
}