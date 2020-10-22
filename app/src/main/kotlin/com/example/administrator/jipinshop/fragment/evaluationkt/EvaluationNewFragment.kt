package com.example.administrator.jipinshop.fragment.evaluationkt

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.sreach.SreachActivity
import com.example.administrator.jipinshop.adapter.HomeFragmentAdapter
import com.example.administrator.jipinshop.base.DBBaseFragment
import com.example.administrator.jipinshop.databinding.FragmentEvaluationNewBinding
import com.example.administrator.jipinshop.fragment.evaluationkt.attent.EvaAttentFrament
import com.example.administrator.jipinshop.fragment.evaluationkt.hot.EvaHotFragment
import com.example.administrator.jipinshop.fragment.evaluationkt.unpacking.EvaUnPackingFragment
import com.example.administrator.jipinshop.fragment.evaluationkt.zcompare.EvaCompareFragment

/**
 * @author 莫小婷
 * @create 2019/8/6
 * @Describe 新版评测fragment
 */
class EvaluationNewFragment : DBBaseFragment(), View.OnClickListener {

    private lateinit var mBinding : FragmentEvaluationNewBinding
    private var mList: MutableList<Fragment> = mutableListOf()
    private var mTitle : ArrayList<String> = arrayListOf("关注","推荐","开箱","横评")
    private lateinit var mAdapter : HomeFragmentAdapter
    private var boolean : Boolean = true
    private var type = "1" // 1:fragment 2:activity

    companion object{
        @JvmStatic
        fun getInstance(type: String): EvaluationNewFragment {
            var fragment = EvaluationNewFragment()
            var bundle = Bundle()
            bundle.putString("type",type)//1:fragment 2:activity
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initLayout(inflater: LayoutInflater, container: ViewGroup): View {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_evaluation_new,container,false)
        mBinding.listener = this
        return mBinding.root
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.home_sreach  ->{
                startActivity(Intent(context,SreachActivity::class.java))
            }
            R.id.title_back -> {
                activity?.let {
                    it.finish()
                }
            }
        }
    }

    override fun initView() {
        context?.let {
            setStatusBarHight(mBinding.statusBar,it)
            mBinding.tabLayout.tabRippleColor = ColorStateList.valueOf(it.resources.getColor(R.color.transparent))
            mBinding.tabLayout.setTabTextColors(it.resources.getColor(R.color.color_9D9D9D),it.resources.getColor(R.color.color_202020))
        }

        mAdapter = HomeFragmentAdapter(childFragmentManager)
        mAdapter.setFragments(mList)
        mAdapter.setTitle(mTitle)
        mBinding.viewPager.adapter = mAdapter
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager)
        arguments?.let {
            type = it.getString("type","1")
            if (type == "2"){
                mBinding.titleBack.visibility = View.VISIBLE
                mList.add(EvaAttentFrament())
                mList.add(EvaHotFragment())
                mList.add(EvaUnPackingFragment())
                mList.add(EvaCompareFragment())
                mAdapter.notifyDataSetChanged()
                mBinding.viewPager.offscreenPageLimit = mList.size - 1
                mBinding.viewPager.currentItem = 1
            }else{
                mBinding.titleBack.visibility = View.GONE
            }
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        arguments?.let {
            type = it.getString("type","1")
        }
        if (isVisibleToUser && boolean && type == "1"){
            mList.add(EvaAttentFrament())
            mList.add(EvaHotFragment())
            mList.add(EvaUnPackingFragment())
            mList.add(EvaCompareFragment())
            mAdapter.notifyDataSetChanged()
            mBinding.viewPager.offscreenPageLimit = mList.size - 1
            mBinding.viewPager.currentItem = 1
            boolean = false
        }
    }

    fun setStatusBarHight(statusBar: LinearLayout, context: Context){
        var resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            var statusBarHeight = context.resources.getDimensionPixelSize(resourceId)
            var layoutParams = statusBar.layoutParams
            layoutParams.height = statusBarHeight
        }
    }
}