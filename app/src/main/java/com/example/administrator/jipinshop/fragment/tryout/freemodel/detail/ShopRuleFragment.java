package com.example.administrator.jipinshop.fragment.tryout.freemodel.detail;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.base.DBBaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 莫小婷
 * @create 2019/6/24
 * @Describe 免单详情——规则说明
 */
public class ShopRuleFragment extends DBBaseFragment {

    @BindView(R.id.free_rule)
    TextView mFreeRule;
    private Unbinder unbinder;
    private String html;

    public static ShopRuleFragment getInstance(String date) {
        ShopRuleFragment fragment = new ShopRuleFragment();
        Bundle bundle = new Bundle();
        bundle.putString("date", date);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_free_rule, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initView() {
        if (getArguments() != null)
            html = getArguments().getString("date","");
        mFreeRule.setText(Html.fromHtml(html));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
