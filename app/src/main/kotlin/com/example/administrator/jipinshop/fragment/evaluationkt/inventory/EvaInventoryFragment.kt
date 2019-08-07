package com.example.administrator.jipinshop.fragment.evaluationkt.inventory

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.base.DBBaseFragment
import com.example.administrator.jipinshop.databinding.FragmentEvaInventoryBinding

/**
 * @author 莫小婷
 * @create 2019/8/6
 * @Describe
 */
class EvaInventoryFragment : DBBaseFragment(){

    private lateinit var mBinding : FragmentEvaInventoryBinding

    override fun initLayout(inflater: LayoutInflater?, container: ViewGroup?): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_eva_inventory,container,false)
        return mBinding.root
    }

    override fun initView() {

    }

}