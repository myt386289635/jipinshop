package com.example.administrator.jipinshop.fragment.shoppingdetail.commen;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.shoppingdetail.ReShoppingDetailActivity;
import com.example.administrator.jipinshop.adapter.DetailCommenAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.databinding.FragmentCommenDetailBinding;
import com.example.administrator.jipinshop.view.glide.imageloder.ImageManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/8/21
 * @Describe
 */
public class DetailCommenFragment extends DBBaseFragment implements OnRefreshListener, DetailCommenAdapter.OnItemReply {

    private FragmentCommenDetailBinding mBinding;

    private DetailCommenAdapter mCommenAdapter;
    private List<String> mCommenList;
    private Boolean once = true;


    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_commen_detail,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBinding.swipeTarget.setLayoutManager(new LinearLayoutManager(getContext()));
        mCommenList = new ArrayList<>();
        mCommenAdapter = new DetailCommenAdapter(getContext(),mCommenList);
        mCommenAdapter.setOnItemReply(this);
        mBinding.swipeTarget.setAdapter(mCommenAdapter);

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
        for (int i = 0; i < 5; i++) {
            mCommenList.add("");
        }
        mCommenAdapter.notifyDataSetChanged();
    }

    /**
     * 点击一级评论回复按钮
     *
     * @param pos
     */
    @Override
    public void onItemReply(int pos, TextView textView) {
        ((ReShoppingDetailActivity)getActivity()).showkeyboard(true);
    }

    /**
     * 点击二级评论回复按钮
     *
     * @param pos
     */
    @Override
    public void onItemTwoReply(int pos) {
        ((ReShoppingDetailActivity)getActivity()).showkeyboard(true);
    }
}
