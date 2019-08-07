package com.example.administrator.jipinshop.fragment.evaluationkt.ieva

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.base.DBBaseFragment
import com.example.administrator.jipinshop.databinding.FragmentEvaEvaBinding

/**
 * @author 莫小婷
 * @create 2019/8/6
 * @Describe
 */
class EvaEvaFragment : DBBaseFragment(){

    private lateinit var mBinding : FragmentEvaEvaBinding

    override fun initLayout(inflater: LayoutInflater?, container: ViewGroup?): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_eva_eva,container,false)
        return mBinding.root
    }

    override fun initView() {

    }

}