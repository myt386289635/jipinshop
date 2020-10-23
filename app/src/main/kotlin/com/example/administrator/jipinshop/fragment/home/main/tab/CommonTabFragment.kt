package com.example.administrator.jipinshop.fragment.home.main.tab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.adapter.KTMain2GridAdapter
import com.example.administrator.jipinshop.base.DBBaseFragment
import com.example.administrator.jipinshop.bean.TbkIndexBean
import com.google.gson.Gson

/**
 * @author 莫小婷
 * @create 2020/10/22
 * @Describe
 */
class CommonTabFragment : DBBaseFragment(){

    //位置和数据
    private lateinit var bean: TbkIndexBean.DataBean
    private var position = 0
    private lateinit var gridView: GridView

    private lateinit var list : MutableList<TbkIndexBean.DataBean.BoxCategoryListBean.ListBean>
    private lateinit var mAdapter: KTMain2GridAdapter

    companion object{
        //position父类位置
        fun getInstance(type : String, position: Int): CommonTabFragment {
            var fragment = CommonTabFragment()
            var bundle = Bundle()
            bundle.putString("date", type)
            bundle.putInt("position", position)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initLayout(inflater: LayoutInflater, container: ViewGroup?): View {
        var rootView = inflater.inflate(R.layout.fragment_tab, container, false)
        gridView = rootView.findViewById(R.id.grid_view)
        return rootView
    }

    override fun initView() {
        arguments?.let {
            bean = Gson().fromJson(it.getString("date"),TbkIndexBean.DataBean::class.java)
            position = it.getInt("position", 0)
        }

        list = mutableListOf()
        list.addAll(bean.boxCategoryList[position].list)
        mAdapter = KTMain2GridAdapter(list,context!!)
        gridView.adapter = mAdapter
    }

}