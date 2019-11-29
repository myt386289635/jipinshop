package com.example.administrator.jipinshop.view.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.DialogParameterAdapter;
import com.example.administrator.jipinshop.bean.TBShoppingDetailBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/11/29
 * @Describe
 */
public class DialogParameter extends BottomSheetDialogFragment {

    private TextView dialog_cancle;
    private RecyclerView dialog_rv;
    private List<TBShoppingDetailBean.DataBean.ParametersListBean> mParameterList;
    private DialogParameterAdapter mAdapter;

    public static DialogParameter getInstance(String content) {
        DialogParameter fragment = new DialogParameter();
        Bundle bundle = new Bundle();
        bundle.putString("content",content);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置背景透明，才能显示出layout中诸如圆角的布局，否则会有白色底（框）
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme2);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_parameter, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        dialog_cancle = view.findViewById(R.id.dialog_cancle);
        dialog_rv = view.findViewById(R.id.dialog_rv);

        dialog_rv.setLayoutManager(new LinearLayoutManager(getContext()));
        mParameterList = new ArrayList<>();
        List<TBShoppingDetailBean.DataBean.ParametersListBean> list = new Gson().fromJson(getArguments().getString("content",""),new TypeToken<List<TBShoppingDetailBean.DataBean.ParametersListBean>>(){}.getType());
        if (list != null)
            mParameterList.addAll(list);
        mAdapter = new DialogParameterAdapter(mParameterList,getContext());
        dialog_rv.setAdapter(mAdapter);
        dialog_cancle.setOnClickListener(v -> {
            dismiss();
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setDimAmount(0.35f);
    }

}
