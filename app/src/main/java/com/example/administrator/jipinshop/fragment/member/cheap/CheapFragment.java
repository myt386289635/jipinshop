package com.example.administrator.jipinshop.fragment.member.cheap;

import android.app.Dialog;
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
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2020/9/8
 * @Describe
 */
public class CheapFragment extends DBBaseFragment implements MemberCheapAdapter.OnItem, CheapView {

    @Inject
    CheapPresenter mPresenter;

    private FragmentCheapMemberBinding mBinding;
    private List<MemberNewBean.DataBean.LevelDetail4Bean.ListBeanX> mList;
    private MemberCheapAdapter mAdapter;
    private Dialog mDialog;

    public static CheapFragment getInstence(int number,List<MemberNewBean.DataBean.LevelDetail4Bean.ListBeanX> list){
        CheapFragment fragment = new CheapFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("number", number);
        bundle.putSerializable("data", (Serializable) list);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding  = DataBindingUtil.inflate(inflater,R.layout.fragment_cheap_member,container,false);
        mBaseFragmentComponent.inject(this);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mPresenter.setView(this);
        mList = new ArrayList<>();
        List<MemberNewBean.DataBean.LevelDetail4Bean.ListBeanX> list =
                (List<MemberNewBean.DataBean.LevelDetail4Bean.ListBeanX>) getArguments().getSerializable("data");
        if (list != null)
            mList.addAll(list);
        mBinding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        mBinding.recyclerView.setNestedScrollingEnabled(false);
        mAdapter = new MemberCheapAdapter(mList,getContext());
        mAdapter.setOnItem(this);
        mAdapter.setNumber(getArguments().getInt("number",0));
        mBinding.recyclerView.setAdapter(mAdapter);
        mBinding.recyclerView.setFocusable(false);//拒绝首次进入页面时滑动
    }

    @Override
    public void onItem(int position) {
        if (mList.get(position).getStatus() == 0){
            mDialog = (new ProgressDialogView()).createLoadingDialog(getContext(), "");
            mDialog.show();
            mPresenter.addAllowance(mList.get(position).getId(),position,this.bindToLifecycle());
        }
    }

    @Override
    public void onSuccess(int position) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show("领取成功");
        mList.get(position).setStatus(1);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFile(String error) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }
}
