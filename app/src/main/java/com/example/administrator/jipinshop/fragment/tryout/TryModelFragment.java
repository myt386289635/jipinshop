package com.example.administrator.jipinshop.fragment.tryout;

import android.content.Context;
import android.content.res.ColorStateList;
import android.databinding.DataBindingUtil;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.HomeFragmentAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.databinding.TryModelFragmentBinding;
import com.example.administrator.jipinshop.fragment.tryout.freemodel.FreeFragment;
import com.example.administrator.jipinshop.fragment.tryout.trymodel.TryFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/6/18
 * @Describe 试用首页
 */
public class TryModelFragment extends DBBaseFragment{

    private TryModelFragmentBinding mBinding;
    private List<Fragment> mFragments;
    private HomeFragmentAdapter mAdapter;
    private Boolean once = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && once){
            mFragments.add(new TryFragment());
            mFragments.add(new FreeFragment());
            mAdapter.setFragments(mFragments);
            mBinding.viewPager.setAdapter(mAdapter);
            mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
            initTabLayout(mFragments,getContext(),mBinding.tabLayout);
            once = false;
        }
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.try_model_fragment,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        setStatusBarHight(mBinding.statusBar,getContext());
        mFragments = new ArrayList<>();
        mAdapter = new HomeFragmentAdapter(getChildFragmentManager());
    }


    public void setStatusBarHight(LinearLayout StatusBar , Context context){
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            int statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
            ViewGroup.LayoutParams layoutParams = StatusBar.getLayoutParams();
            layoutParams.height = statusBarHeight;
        }
    }

    public void initTabLayout(List<Fragment> mFragments, Context context, TabLayout mTabLayout) {
        final List<Integer> textLether = new ArrayList<>();
        for (int i = 0; i < mFragments.size(); i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.tablayout_home, null);
            TextView textView = view.findViewById(R.id.tab_name);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
            if (i == 0) {
                textView.setText("新品试用");
            } else{
                textView.setText("免单");
            }
            mTabLayout.getTabAt(i).setCustomView(view);
            int a = (int) textView.getPaint().measureText(textView.getText().toString());
            textLether.add(a);
        }
        mTabLayout.setSelectedTabIndicatorColor(context.getResources().getColor(R.color.transparent));
        mTabLayout.setTabRippleColor(ColorStateList.valueOf(context.getResources().getColor(R.color.transparent)));
        mTabLayout.post(() -> {
            //拿到tabLayout的mTabStrip属性
            LinearLayout mTabStrip = (LinearLayout) mTabLayout.getChildAt(0);
            int totle = textLether.get(0) + textLether.get(1);
            int dp10 = (mTabLayout.getWidth() - totle) /  mFragments.size();
            for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                View tabView = mTabStrip.getChildAt(i);
                tabView.setPadding(0, 0, 0, 0);
                int width = textLether.get(i) + dp10;
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                        tabView.getLayoutParams();
                params.width = width;
                params.leftMargin = dp10  / 2;
                params.rightMargin = dp10  / 2;
                tabView.setLayoutParams(params);
                tabView.invalidate();
            }
        });
    }
}
