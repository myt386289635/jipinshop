package com.example.administrator.jipinshop.fragment.member.cheap;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.MemberCheapAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.MemberNewBean;
import com.example.administrator.jipinshop.databinding.FragmentCheapMemberBinding;
import com.example.administrator.jipinshop.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/9/8
 * @Describe
 */
public class CheapFragment extends DBBaseFragment implements MemberCheapAdapter.OnItem {

    private FragmentCheapMemberBinding mBinding;
    private List<MemberNewBean.DataBean.LevelDetail4Bean.ListBeanX> mList;
    private MemberCheapAdapter mAdapter;

    public static CheapFragment getInstence(int number){
        CheapFragment fragment = new CheapFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("number", number);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding  = DataBindingUtil.inflate(inflater,R.layout.fragment_cheap_member,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mList = new ArrayList<>();
        mBinding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        mBinding.recyclerView.setNestedScrollingEnabled(false);
        mAdapter = new MemberCheapAdapter(mList,getContext());
        mAdapter.setOnItem(this);
        mAdapter.setNumber(getArguments().getInt("number",0));
        mBinding.recyclerView.setAdapter(mAdapter);
        mBinding.recyclerView.setFocusable(false);//拒绝首次进入页面时滑动
    }

    public void setDate(List<MemberNewBean.DataBean.LevelDetail4Bean.ListBeanX> list){
        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItem(int position) {
        // TODO: 2020/9/10
        ToastUtil.show("点击了第" + position + "个");
    }
}
