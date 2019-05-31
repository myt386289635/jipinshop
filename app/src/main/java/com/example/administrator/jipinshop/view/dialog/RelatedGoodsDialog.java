package com.example.administrator.jipinshop.view.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.RelatedGoodsAdapter;
import com.example.administrator.jipinshop.bean.FindDetailBean;
import com.example.administrator.jipinshop.view.recyclerView.MaxHeightRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/1/11
 * @Describe
 */
public class RelatedGoodsDialog extends BottomSheetDialogFragment {

    private RelatedGoodsAdapter mAdapter;
    private List<FindDetailBean.DataBean.RelatedGoodsListBean> mBeans;
    private MaxHeightRecyclerView recyclerView;
    private OnItem mOnItem;

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    public static RelatedGoodsDialog getInstance(ArrayList<FindDetailBean.DataBean.RelatedGoodsListBean> beans) {
        RelatedGoodsDialog  fragment = new RelatedGoodsDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("list",beans);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_related_goods, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        dialog.getWindow().setDimAmount(0.35f);

        /***禁止下滑关闭，但是点击背景可以关闭***/
        final View view = getView();
        view.post(() -> {
            View parent = (View) view.getParent();
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) (parent).getLayoutParams();
            CoordinatorLayout.Behavior behavior = params.getBehavior();
            BottomSheetBehavior mBottomSheetBehavior = (BottomSheetBehavior) behavior;
            mBottomSheetBehavior.setHideable(false);
        });
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        ImageView close = view.findViewById(R.id.close);

        mBeans = new ArrayList<>();
        mBeans.addAll(getArguments().getParcelableArrayList("list"));
        mAdapter = new RelatedGoodsAdapter(mBeans,getContext());
        mAdapter.setOnItem(position -> {
            if(mOnItem != null){
                mOnItem.onItemClick(position);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

        close.setOnClickListener(v -> dismiss());
    }

    public interface OnItem{
        void onItemClick(int position);
    }
}
