package com.example.administrator.jipinshop.fragment.home.main.tab

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.adapter.KTMainGridAdapter
import com.example.administrator.jipinshop.base.DBBaseFragment
import com.example.administrator.jipinshop.bean.TbkIndexBean
import com.example.administrator.jipinshop.view.gridview.MyGridView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.item_foot.*

/**
 * @author 莫小婷
 * @create 2019/12/12
 * @Describe 首页 4宫格图（品质大牌、白菜好物、新品专区、每日签到）
 */
class KTTabFragment : DBBaseFragment(){

    private lateinit var mGirdView : MyGridView
    private lateinit var mList: MutableList<TbkIndexBean.DataBean.BoxListBean>
    private var fatherPos = 0
    private lateinit var mAdapter: KTMainGridAdapter

    companion object{
        fun getInstance(pos : Int , list: MutableList<TbkIndexBean.DataBean.BoxListBean>) : KTTabFragment {
            var fragment = KTTabFragment()
            var bundle = Bundle()
            var json = Gson().toJson(list, object : TypeToken<List<TbkIndexBean.DataBean.BoxListBean>>(){}.type)
            bundle.putString("gson",json)
            bundle.putInt("pos", pos)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initLayout(inflater: LayoutInflater, container: ViewGroup?): View {
        val rootView = inflater.inflate(R.layout.fragment_tab, container, false)
        mGirdView = rootView.findViewById(R.id.grid_view)
        mGirdView.numColumns = 4
        return rootView
    }

    override fun initView() {
        mList = mutableListOf()
        fatherPos = arguments?.getInt("pos" , 0) ?: 0
        if (arguments?.getString("gson") != null){
            var orgainList : MutableList<TbkIndexBean.DataBean.BoxListBean> = mutableListOf()
            orgainList.addAll(Gson().fromJson(arguments?.getString("gson"), object : TypeToken<List<TbkIndexBean.DataBean.BoxListBean>>(){}.type))
            if (fatherPos == 0){
                if (orgainList.size <= 4){
                    mList.addAll(orgainList)
                }else{
                    for (i in 0 until 4){
                        mList.add(orgainList[i])
                    }
                }
            }else {//第二页
                for (i in 4 until orgainList.size){
                    mList.add(orgainList[i])
                }
            }
        }
        mAdapter = KTMainGridAdapter(mList,context!!)
        mGirdView.adapter = mAdapter
        mGirdView.selector = ColorDrawable(Color.TRANSPARENT)
    }

}
