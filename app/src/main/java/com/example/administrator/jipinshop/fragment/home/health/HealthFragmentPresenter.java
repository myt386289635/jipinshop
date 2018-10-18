package com.example.administrator.jipinshop.fragment.home.health;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.administrator.jipinshop.adapter.HealthFragmentGridAdapter;
import com.example.administrator.jipinshop.bean.HealthFragmentGridBean;
import com.example.administrator.jipinshop.netwrok.Repository;

import java.util.List;

import javax.inject.Inject;

public class HealthFragmentPresenter {

    Repository mRepository;

    @Inject
    public HealthFragmentPresenter(Repository repository) {
        mRepository = repository;
    }


    public void refreshGirdView(Context context, SwipeToLoadLayout mSwipeToLoadLayout, List<HealthFragmentGridBean> gridViewList,
                                int pos,HealthFragmentGridAdapter mAdapter,RecyclerView mRecyclerView){
        if(mSwipeToLoadLayout.isRefreshing()){
            Toast.makeText(context, "正在刷新数据，请稍后再试", Toast.LENGTH_SHORT).show();
            return;
        }
        for (int i = 0; i < gridViewList.size(); i++) {
            gridViewList.get(i).setTag(false);
        }
        gridViewList.get(pos).setTag(true);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(0);
        mSwipeToLoadLayout.setRefreshing(true);
    }

}
