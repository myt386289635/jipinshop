package com.example.administrator.jipinshop.activity.evakt.send.goods

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import com.aspsine.swipetoloadlayout.OnLoadMoreListener
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.adapter.SubmitCorveAdapter
import com.example.administrator.jipinshop.base.BaseActivity
import com.example.administrator.jipinshop.bean.SreachResultGoodsBean
import com.example.administrator.jipinshop.databinding.ActivityAddgoodsBinding
import com.example.administrator.jipinshop.util.ToastUtil
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/8/14
 * @Describe 添加商品
 */
class AddGoodsActivity : BaseActivity(), View.OnClickListener, TextWatcher, OnLoadMoreListener, AddGoodsView, SubmitCorveAdapter.OnClickItem {

    @Inject
    lateinit var mPresenter: AddGoodsPresenter

    private lateinit var mBinding: ActivityAddgoodsBinding
    private lateinit var mList: MutableList<SreachResultGoodsBean.DataBean>
    private lateinit var mAdapter: SubmitCorveAdapter
    private var page = 1
    private var refersh: Boolean = true
    private var articleId =""
    private var sreachText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_addgoods)
        mBinding.listener = this
        mBaseActivityComponent.inject(this)
        mPresenter.setView(this)
        initView()
    }

    private fun initView() {
        articleId = intent.getStringExtra("articleId")

        mList = mutableListOf()
        mBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = SubmitCorveAdapter(mList,this)
        mAdapter.setOnClick(this)
        mBinding.recyclerView.adapter = mAdapter

        mBinding.goodsEdit.addTextChangedListener(this)
        mBinding.swipeToLoad.setOnLoadMoreListener(this)
        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.cancel -> {
                finish()
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val view = currentFocus
            if (isHideInput(view, ev)) {
                showKeyboard(false)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun isHideInput(v: View?, ev: MotionEvent): Boolean {
        return if (v != null && v is EditText) {
            val l = intArrayOf(0, 0)
            v.getLocationInWindow(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + v.height
            val right = left + v.width
            !(ev.x > left && ev.x < right && ev.y > top
                    && ev.y < bottom)
        }else{
            false
        }
    }

    override fun afterTextChanged(s: Editable?) {
        page = 1
        refersh = true
        sreachText = s.toString()
        mPresenter.searchInventory(articleId,sreachText,page,this.bindToLifecycle())
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun onLoadMore() {
        page++
        refersh = false
        mPresenter.searchInventory(articleId,sreachText,page,this.bindToLifecycle())
    }

    fun dissLoading() {
        if (mBinding.swipeToLoad.isLoadingMore) {
            if (!mBinding.swipeToLoad.isLoadMoreEnabled) {
                mBinding.swipeToLoad.isLoadMoreEnabled = true
                mBinding.swipeToLoad.isLoadingMore = false
                mBinding.swipeToLoad.isLoadMoreEnabled = false
            } else {
                mBinding.swipeToLoad.isLoadingMore = false
            }
        }
    }

    fun initError(id: Int, title: String, content: String) {
        mBinding.netClude?.run {
            qsNet.visibility = View.VISIBLE
            errorImage.setBackgroundResource(id)
            errorTitle.text = title
            errorContent.text = content
        }
    }

    override fun onSuccess(bean: SreachResultGoodsBean) {
        if (refersh) {
            if (bean.data != null && bean.data.size != 0) {
                mBinding.netClude?.let {
                    it.qsNet.visibility = View.GONE
                }
                mBinding.recyclerView.visibility = View.VISIBLE
                mList.clear()
                mList.addAll(bean.data)
                mAdapter.notifyDataSetChanged()
            } else {
                if (TextUtils.isEmpty(sreachText)){
                    mList.clear()
                    mAdapter.notifyDataSetChanged()
                    mBinding.recyclerView.visibility = View.VISIBLE
                    mBinding.netClude?.run {
                        qsNet.visibility = View.GONE
                    }
                }else{
                    initError(R.mipmap.qs_nodata, "暂无数据", "暂时没有任何数据 ")
                    mBinding.recyclerView.visibility = View.GONE
                }
            }
        } else {
            dissLoading()
            if (bean.data != null && bean.data.size != 0) {
                mList.addAll(bean.data)
                mAdapter.notifyDataSetChanged()
                mBinding.swipeToLoad.isLoadMoreEnabled = false
            } else {
                page--
                ToastUtil.show("已经是最后一页了")
            }
        }
    }

    override fun onFile(error: String?) {
        if (refersh) {
            initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试")
            mBinding.recyclerView.visibility = View.GONE
        } else {
            dissLoading()
            page--
        }
        ToastUtil.show(error)
    }

    override fun onClickAdd(position: Int) {
        mPresenter.addGoods(position,articleId,mList[position].goodsId,this.bindToLifecycle())
    }

    override fun onClickCancle(position: Int) {
        mPresenter.deleteGoods(position,articleId,mList[position].goodsId,this.bindToLifecycle())
    }

    override fun onAdd(position: Int) {
        mList[position].related = 1
        mAdapter.notifyDataSetChanged()
    }

    override fun onDetele(position: Int) {
        mList[position].related = 0
        mAdapter.notifyDataSetChanged()
    }

    override fun onCommonFile(error: String?) {
        ToastUtil.show(error)
    }

}