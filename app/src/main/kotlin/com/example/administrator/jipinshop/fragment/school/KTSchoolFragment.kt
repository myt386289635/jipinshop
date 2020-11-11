package com.example.administrator.jipinshop.fragment.school

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.school.search.SchoolSearchActivity
import com.example.administrator.jipinshop.adapter.KTSchoolAdapter
import com.example.administrator.jipinshop.base.DBBaseFragment
import com.example.administrator.jipinshop.bean.SchoolHomeBean
import com.example.administrator.jipinshop.databinding.FragmentSchoolBinding
import com.example.administrator.jipinshop.util.ToastUtil
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2020/7/14
 * @Describe 省钱教程
 */
class KTSchoolFragment : DBBaseFragment(), View.OnClickListener, OnRefreshListener, KTSchoolView {

    @Inject
    lateinit var mPresenter: KTSchoolPresenter
    private lateinit var mBinding : FragmentSchoolBinding
    private lateinit var mAdapter : KTSchoolAdapter
    private var once: Boolean = true

    companion object{
        @JvmStatic
        fun getInstance(): KTSchoolFragment {
            return KTSchoolFragment()
        }
    }

    override fun initLayout(inflater: LayoutInflater, container: ViewGroup?): View {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_school,container,false)
        mBinding.listener = this
        return mBinding.root
    }

    override fun initView() {
        mBaseFragmentComponent.inject(this)
        mPresenter.setView(this)
        mPresenter.setStatusBarHight(mBinding.statusBar,context!!)

        mBinding.swipeTarget.layoutManager = LinearLayoutManager(context)
        mAdapter = KTSchoolAdapter(context!!)
        mBinding.swipeTarget.adapter = mAdapter

        mBinding.swipeToLoad.setOnRefreshListener(this)
        mBinding.swipeToLoad.isRefreshing = true
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.school_searchContainer -> {
                //商学院搜索
                startActivity(Intent(context, SchoolSearchActivity::class.java))
            }
            R.id.school_balck -> {
                activity?.let {
                    it.finish()
                }
            }
        }
    }

    override fun onRefresh() {
        mPresenter.courseIndex(this.bindToLifecycle())
    }

    override fun onSuccess(bean: SchoolHomeBean) {
        mBinding.swipeToLoad.isRefreshing = false
        mAdapter.setDate(bean)
        mAdapter.notifyDataSetChanged()
        once = false
    }

    override fun onFile(error: String?) {
        mBinding.swipeToLoad.isRefreshing = false
        ToastUtil.show(error)
    }

}