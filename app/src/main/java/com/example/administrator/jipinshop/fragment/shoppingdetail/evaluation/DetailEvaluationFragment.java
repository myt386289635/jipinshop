package com.example.administrator.jipinshop.fragment.shoppingdetail.evaluation;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.DetailEvaluationAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.databinding.FragmentEvaluationDetailBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/8/21
 * @Describe
 */
public class DetailEvaluationFragment extends DBBaseFragment implements OnRefreshListener {

    private FragmentEvaluationDetailBinding mBinding;

    private DetailEvaluationAdapter mEvaluationAdapter;
    private List<String> mEvaluationList;
    private Boolean once = true;

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_evaluation_detail,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBinding.swipeTarget.setLayoutManager(new LinearLayoutManager(getContext()));
        mEvaluationList = new ArrayList<>();
        mEvaluationAdapter = new DetailEvaluationAdapter(getContext(),mEvaluationList);
        mBinding.swipeTarget.setAdapter(mEvaluationAdapter);

        mBinding.swipeToLoad.setOnRefreshListener(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && once){
            mBinding.swipeToLoad.setRefreshing(true);
            once = false;
        }
    }

    @Override
    public void onRefresh() {
        if(mBinding.swipeToLoad != null && mBinding.swipeToLoad.isRefreshing()){
            mBinding.swipeToLoad.setRefreshing(false);
        }
        // TODO: 2018/8/21 请求网络数据
        for (int i = 0; i < 10; i++) {
            mEvaluationList.add("");
        }
        mEvaluationAdapter.notifyDataSetChanged();
    }
}
