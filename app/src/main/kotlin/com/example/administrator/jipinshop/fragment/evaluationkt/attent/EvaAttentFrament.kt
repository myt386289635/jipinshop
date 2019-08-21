package com.example.administrator.jipinshop.fragment.evaluationkt.attent

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aspsine.swipetoloadlayout.OnLoadMoreListener
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.minekt.userkt.UserActivity
import com.example.administrator.jipinshop.adapter.EvaAttentAdapter
import com.example.administrator.jipinshop.base.DBBaseFragment
import com.example.administrator.jipinshop.bean.EvaAttentBean
import com.example.administrator.jipinshop.databinding.FragmentEvaluationCommonBinding
import com.example.administrator.jipinshop.util.ClickUtil
import com.example.administrator.jipinshop.util.ShopJumpUtil
import com.example.administrator.jipinshop.util.ToastUtil
import java.math.BigDecimal
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2019/8/6
 * @Describe 评测模块——关注列表
 */
class EvaAttentFrament : DBBaseFragment(), OnRefreshListener, OnLoadMoreListener, EvaAttentAdapter.OnClickItem, EvaAttentView {

    @Inject
    lateinit var mPresenter : EvaAttentPresenter

    private lateinit var mBinding : FragmentEvaluationCommonBinding
    private lateinit var mList: MutableList<EvaAttentBean.DataBean>
    private lateinit var mAdapter: EvaAttentAdapter
    private var page = 1
    private var refersh: Boolean = true
    private var once : Boolean = true //第一次进入

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && !once){//以后的每一次进入该页面都需要刷新该页面
            mBinding.recyclerView.scrollToPosition(0)
            mBinding.swipeToLoad.isLoadMoreEnabled = false
            refresh()
        }
    }

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
        mAdapter = EvaAttentAdapter(mList,context!!)
        mAdapter.setClick(this)
        mBinding.recyclerView.adapter = mAdapter

        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad)
        mBinding.swipeToLoad.setOnRefreshListener(this)
        mBinding.swipeToLoad.setOnLoadMoreListener(this)
        mBinding.swipeToLoad.isRefreshing = true
    }

    override fun onLoadMore() {
        page++
        refersh = false
        mPresenter.myfollowList(page,this.bindToLifecycle())
    }

    override fun onRefresh() {
        page = 1
        refersh = true
        mPresenter.myfollowList(page,this.bindToLifecycle())
    }

    fun refresh(){
        if (!mBinding.swipeToLoad.isRefreshEnabled) {
            mBinding.swipeToLoad.isRefreshEnabled = true
            mBinding.swipeToLoad.isRefreshing = true
            mBinding.swipeToLoad.isRefreshEnabled = false
        } else {
            mBinding.swipeToLoad.isRefreshing = true
        }
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

    //进入文章详情
    override fun onClickItem(position: Int) {
        if (ClickUtil.isFastDoubleClick(800)) {
            return
        } else {
            val bigDecimal = BigDecimal(mList[position].article.pv)
            mList[position].article.pv = bigDecimal.toInt() + 1
            mAdapter.notifyDataSetChanged()
            ShopJumpUtil.jumpArticle(context, mList[position].article.articleId,
                    "" + mList[position].article.type, mList[position].article.contentType)
        }
    }

    //进入个人详情页
    override fun onClickUserinfo(userId: String) {
        startActivity(Intent(context, UserActivity::class.java)
                .putExtra("userid", userId)
        )
    }

    //文章列表：添加关注逻辑
    override fun onClickAttent(userId: String, position: Int) {
        mPresenter.concernInsert(position,userId,this.bindToLifecycle())
    }

    //文章列表：取消关注
    override fun onClickAttentCancle(userId: String, position: Int) {
        mPresenter.concernDelete(position,userId,this.bindToLifecycle())
    }

    //推荐列表：添加关注逻辑
    override fun onClickAttent2(userId: String, pos: Int, fpos: Int) {
        mPresenter.concernInsert2(pos,fpos,userId,this.bindToLifecycle())
    }

    //推荐列表：取消关注
    override fun onClickAttentCancle2(userId: String, pos: Int, fpos: Int) {
        mPresenter.concernDelete2(pos,fpos,userId,this.bindToLifecycle())
    }

    override fun onSuccess(bean: EvaAttentBean) {
        if (refersh) {
            dissRefresh()
            if (bean.data != null && bean.data.size != 0) {
                mBinding.netClude?.let {
                    it.qsNet.visibility = View.GONE
                }
                mBinding.recyclerView.visibility = View.VISIBLE
                mList.clear()
                mList.addAll(bean.data)
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
        if (once){//第一次请求结束后改变标示
            once = false
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
        if (once){//第一次请求结束后改变标示
            once = false
        }
    }

    /**
     * 接口返回——关注失败
     */
    override fun commenFile(error: String?) {
        ToastUtil.show(error)
    }

    /**
     * 接口返回——文章列表：取消关注
     */
    override fun onCancleAttent(pos: Int) {
        ToastUtil.show("取消关注成功")
        mList[pos].article.user.follow = "0"
        mAdapter.notifyDataSetChanged()
    }

    /**
     *接口返回——文章列表：关注成功
     */
    override fun onAttent(pos: Int) {
        ToastUtil.show("关注成功")
        mList[pos].article.user.follow = "2"
        mAdapter.notifyDataSetChanged()
    }

    /**
     * 接口返回——推荐列表：关注成功
     */
    override fun onAttent2(pos: Int, fpos: Int) {
        ToastUtil.show("关注成功")
        mList[fpos].userList[pos].follow = "1"
        mAdapter.notifyDataSetChanged()
    }

    /**
     *接口返回——推荐列表：取消关注
     */
    override fun onCancleAttent2(pos: Int, fpos: Int) {
        ToastUtil.show("取消关注成功")
        mList[fpos].userList[pos].follow = "0"
        mAdapter.notifyDataSetChanged()
    }

}