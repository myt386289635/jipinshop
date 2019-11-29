package com.example.administrator.jipinshop.view.dialog;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.TBShoppingDetailBean;
import com.example.administrator.jipinshop.databinding.DialogQualityBinding;
import com.example.administrator.jipinshop.view.SaleProgressView;
import com.example.administrator.jipinshop.view.textview.AlignTextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/11/29
 * @Describe
 */
public class DialogQuality extends BottomSheetDialogFragment {
    
    private List<TextView> mTextViews;
    private List<AlignTextView> mAlignTextViews;
    private List<SaleProgressView> mSaleProgressViews;
    private List<TBShoppingDetailBean.DataBean.ScoreOptionsListBean> mQualityList;
    private DialogQualityBinding binding;

    public static DialogQuality getInstance(String content) {
        DialogQuality fragment = new DialogQuality();
        Bundle bundle = new Bundle();
        bundle.putString("content",content);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置背景透明，才能显示出layout中诸如圆角的布局，否则会有白色底（框）
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme3);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.dialog_quality, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        binding.dialogCancle.setOnClickListener(v -> {
            dismiss();
        });
        mQualityList = new ArrayList<>();
        List<TBShoppingDetailBean.DataBean.ScoreOptionsListBean> list = new Gson().fromJson(getArguments().getString("content",""),new TypeToken<List<TBShoppingDetailBean.DataBean.ScoreOptionsListBean>>(){}.getType());
        if (list != null)
            mQualityList.addAll(list);
        mTextViews = new ArrayList<>();
        mAlignTextViews = new ArrayList<>();
        mSaleProgressViews = new ArrayList<>();
        mTextViews.add(binding.itemProgressbar1TextDes);
        mTextViews.add(binding.itemProgressbar2TextDes);
        mTextViews.add(binding.itemProgressbar3TextDes);
        mTextViews.add(binding.itemProgressbar4TextDes);
        mTextViews.add(binding.itemProgressbar5TextDes);
        mAlignTextViews.add(binding.itemProgressbar1Text);
        mAlignTextViews.add(binding.itemProgressbar2Text);
        mAlignTextViews.add(binding.itemProgressbar3Text);
        mAlignTextViews.add(binding.itemProgressbar4Text);
        mAlignTextViews.add(binding.itemProgressbar5Text);
        mSaleProgressViews.add(binding.itemProgressbar1);
        mSaleProgressViews.add(binding.itemProgressbar2);
        mSaleProgressViews.add(binding.itemProgressbar3);
        mSaleProgressViews.add(binding.itemProgressbar4);
        mSaleProgressViews.add(binding.itemProgressbar5);
        //综合评分
        binding.itemScore.setText(mQualityList.get(0).getScore());
        for (int i = 0; i < mQualityList.size()-1; i++) {
            BigDecimal b = new BigDecimal(mQualityList.get(i + 1).getScore());
            double result = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();//保留1位小数
            mTextViews.get(i).setText(result + "分");
            mAlignTextViews.get(i).setText(mQualityList.get(i + 1).getName());
            mSaleProgressViews.get(i).setTotalAndCurrentCount(100, Integer.valueOf(mQualityList.get(i + 1).getScore()) * 10);
        }
        if((mQualityList.size() - 1) <= 4){
            binding.itemProgressbar5Container.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setDimAmount(0.35f);
    }

}
