package com.example.administrator.jipinshop.fragment.evaluation;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.HomeFragmentAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.EvaluationTabBean;
import com.example.administrator.jipinshop.databinding.FragmentEvaluationBinding;
import com.example.administrator.jipinshop.fragment.evaluation.common.CommonEvaluationFragment;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class EvaluationFragment extends DBBaseFragment implements EvaluationView {

    public static final String tag = "EvaluationFragment2CommonEvaluationFragment";

    @Inject
    EvaluationFragmentPresenter mPresenter;

    private FragmentEvaluationBinding mBinding;
    private List<Fragment> mFragments;
    private HomeFragmentAdapter mAdapter;

    private List<EvaluationTabBean.ListBean> tabTitle;
    private List<TextView> tabTextView;

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_evaluation,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);
        mPresenter.setStatusBarHight(mBinding.statusBar,getContext());
        mPresenter.setView(this);

        mFragments = new ArrayList<>();
        tabTitle = new ArrayList<>();
        tabTextView= new ArrayList<>();

        mFragments.add(CommonEvaluationFragment.getInstance(CommonEvaluationFragment.ONE));
        mFragments.add(CommonEvaluationFragment.getInstance(CommonEvaluationFragment.TWO));
        mFragments.add(CommonEvaluationFragment.getInstance(CommonEvaluationFragment.THREE));
        mFragments.add(CommonEvaluationFragment.getInstance(CommonEvaluationFragment.FORE));
//        mFragments.add(CommonEvaluationFragment.getInstance(CommonEvaluationFragment.FIVE));
        mAdapter = new HomeFragmentAdapter(getChildFragmentManager());
        mAdapter.setFragments(mFragments);
        mBinding.viewPager.setAdapter(mAdapter);
        mBinding.viewPager.setOffscreenPageLimit(3);
        mBinding.tabLayout.setupWithViewPager( mBinding.viewPager);

        initDate();
        mPresenter.initTab(this.bindToLifecycle());
    }

    @Override
    public void onSucTab(EvaluationTabBean bean) {
        tabTitle.clear();
        tabTitle.addAll(bean.getList());
        SPUtils.getInstance().put(CommonDate.EvaluationTab,new Gson().toJson(bean));
        mPresenter.initTabLayout(getContext(),mBinding.tabLayout,tabTitle,tabTextView);
        EventBus.getDefault().post(EvaluationFragment.tag);
    }

    @Override
    public void onFaile(String error) {
        EventBus.getDefault().post(EvaluationFragment.tag);
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取缓存内容
     */
    public void initDate(){
        if(!TextUtils.isEmpty(SPUtils.getInstance().getString(CommonDate.EvaluationTab,""))){
            EvaluationTabBean bean = new Gson().fromJson(SPUtils.getInstance().getString(CommonDate.EvaluationTab),EvaluationTabBean.class);
            tabTitle.addAll(bean.getList());
        }else {
            //没有缓存时
            for (int i = 0; i < 4; i++) {
                EvaluationTabBean.ListBean listBean = new EvaluationTabBean.ListBean();
                if (i== 0){
                    listBean.setCategoryName("精选榜");
                }else if(i == 1){
                    listBean.setCategoryName("个护健康");
                }else if(i == 2){
                    listBean.setCategoryName("厨卫电器");
                }else {
                    listBean.setCategoryName("生活电器");
                }
//                else{
//                    listBean.setCategoryName("家用大电");
//                }
                tabTitle.add(listBean);
            }
        }
        mPresenter.initTabLayout(getContext(),mBinding.tabLayout,tabTitle,tabTextView);
    }
}
