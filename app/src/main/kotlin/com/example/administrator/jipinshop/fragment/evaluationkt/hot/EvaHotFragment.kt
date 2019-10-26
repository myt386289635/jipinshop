package com.example.administrator.jipinshop.fragment.evaluationkt.hot

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aspsine.swipetoloadlayout.OnLoadMoreListener
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.blankj.utilcode.util.SPUtils
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.login.LoginActivity
import com.example.administrator.jipinshop.activity.minekt.userkt.UserActivity
import com.example.administrator.jipinshop.adapter.EvaHotAdapter
import com.example.administrator.jipinshop.base.DBBaseFragment
import com.example.administrator.jipinshop.bean.EvaAttentBean
import com.example.administrator.jipinshop.bean.EvaHotBean
import com.example.administrator.jipinshop.databinding.FragmentEvaluationCommonBinding
import com.example.administrator.jipinshop.util.ClickUtil
import com.example.administrator.jipinshop.util.ShopJumpUtil
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.util.sp.CommonDate
import java.math.BigDecimal
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/8/6
 * @Describe 评测模块——推荐列表
 */
class EvaHotFragment : DBBaseFragment(), OnRefreshListener, OnLoadMoreListener, EvaHotView, EvaHotAdapter.OnClickItem {

    @Inject
    lateinit var mPresenter : EvaHotPresenter

    private lateinit var mBinding : FragmentEvaluationCommonBinding
    private lateinit var mList: MutableList<EvaAttentBean.DataBean.ArticleBean>
    private lateinit var mAds: MutableList<EvaHotBean.AdsBean>
    private lateinit var mAdapter : EvaHotAdapter
    private var page = 1
    private var refersh: Boolean = true
    private var once : Boolean = true //第一次进入

//    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
//        super.setUserVisibleHint(isVisibleToUser)
//        if (isVisibleToUser && once){
//            mBinding.swipeToLoad.isRefreshing = true
//            once = false
//        }
//    }

    override fun initLayout(inflater: LayoutInflater?, container: ViewGroup?): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_evaluation_common, container, false)
        return mBinding.root
    }

    override fun initView() {
        mBaseFragmentComponent.inject(this)
        mPresenter.setView(this)
        context?.let {
            mBinding.swipeToLoad.setBackgroundColor(it.resources.getColor(R.color.color_F5F5F5))
        }

        mBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        mList = mutableListOf()
        mAds = mutableListOf()
        mAdapter = EvaHotAdapter(mList,mAds,context!!)
        mAdapter.setClick(this)
        mBinding.recyclerView.adapter = mAdapter

        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad)
        mBinding.swipeToLoad.setOnRefreshListener(this)
        mBinding.swipeToLoad.setOnLoadMoreListener(this)
        mBinding.swipeToLoad.post {
            mBinding.swipeToLoad.isRefreshing = true
            once = false
        }
    }

    override fun onLoadMore() {
        page++
        refersh = false
        mPresenter.recommendList(page,this.bindToLifecycle())
    }

    override fun onRefresh() {
        page = 1
        refersh = true
        mPresenter.recommendList(page,this.bindToLifecycle())
    }

    fun dissRefresh() {
        if (mBinding.swipeToLoad.isRefreshing) {
            if (!mBinding.swipeToLoad.isRefreshEnabled) {
                mBinding.swipeToLoad.isRefreshEnabled = true
                mBinding.swipeToLoad.isRefreshing = false
                mBinding.swipeToLoad.isRefreshEnabled = false
            } else {
                mBinding.swipeToLoad.isRefreshing = false
            }
        }
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

    override fun onSuccess(bean: EvaHotBean) {
        if (refersh) {
            dissRefresh()
            if (bean.data != null && bean.data.size != 0) {
                if (bean.count != 0){
                    ToastUtil.showTop("为您更新"+bean.count+"篇文章",context)
                }else{
                    ToastUtil.showTop("刷新成功",context)
                }
                mBinding.netClude?.let {
                    it.qsNet.visibility = View.GONE
                }
                mBinding.recyclerView.visibility = View.VISIBLE
                mList.clear()
                mAds.clear()
                mList.addAll(bean.data)
                mAds.addAll(bean.ads)
                mAdapter.notifyDataSetChanged()
            } else {
                initError(R.mipmap.qs_nodata, "暂无数据", "暂时没有任何数据 ")
                mBinding.recyclerView.visibility = View.GONE
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
            dissRefresh()
            initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试")
            mBinding.recyclerView.visibility = View.GONE
        } else {
            dissLoading()
            page--
        }
        ToastUtil.show(error)
    }

    override fun onClickAttent(userId: String, position: Int) {
        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
            startActivity(Intent(context, LoginActivity::class.java))
            return
        }
        mPresenter.concernInsert(position,userId,this.bindToLifecycle())
    }

    override fun onClickAttentCancle(userId: String, position: Int) {
        mPresenter.concernDelete(position,userId,this.bindToLifecycle())
    }

    override fun onClickItem(position: Int) {
        if (ClickUtil.isFastDoubleClick(800)) {
            return
        } else {
            val bigDecimal = BigDecimal(mList[position].pv)
            mList[position].pv = bigDecimal.toInt() + 1
            mList[position].articleReadData.clickCount = 1
            mAdapter.notifyDataSetChanged()
            ShopJumpUtil.jumpArticle(context, mList[position].articleId,
                    "" + mList[position].type, mList[position].contentType)
        }
    }

    override fun onClickUserinfo(userId: String) {
        startActivity(Intent(context, UserActivity::class.java)
                .putExtra("userid", userId)
        )
    }

    override fun onAttent(pos: Int) {
        ToastUtil.show("关注成功")
        mList[pos].user.follow = "1"
        mAdapter.notifyDataSetChanged()
    }

    override fun onCancleAttent(pos: Int) {
        ToastUtil.show("取消关注成功")
        mList[pos].user.follow = "0"
        mAdapter.notifyDataSetChanged()
    }

    override fun commenFile(error: String?) {
        ToastUtil.show(error)
    }
}