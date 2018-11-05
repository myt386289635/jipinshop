package com.example.administrator.jipinshop.fragment.shoppingdetail.shop;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.adapter.ShoppingBannerAdapter;
import com.example.administrator.jipinshop.adapter.ShoppingQualityAdapter;
import com.example.administrator.jipinshop.adapter.ShoppingmParameterAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.ShoppingDetailBean;
import com.example.administrator.jipinshop.databinding.FragmentShopDetailBinding;
import com.example.administrator.jipinshop.util.WeakRefHandler;
import com.example.administrator.jipinshop.view.goodview.GoodView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2018/8/21
 * @Describe
 */
public class DetailShopFragment extends DBBaseFragment {

    @Inject
    DetailShopPresenter mPresenter;

    private FragmentShopDetailBinding mBinding;
    //品质保证、售后服务
    private ShoppingQualityAdapter mQualityAdapter;
    private List<String> mQualityList;

    //产品参数
    private ShoppingmParameterAdapter mParameterAdapter;
    private List<ShoppingDetailBean.GoodsRankdetailEntityBean.ParametersListBean> mParameterList;

    //banner
    private ShoppingBannerAdapter mBannerAdapter;
    private List<String> mBannerList;
    private List<ImageView> point;
    private Handler.Callback mCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (mBinding.viewPager != null) {
                mBinding.viewPager.setCurrentItem(mBinding.viewPager.getCurrentItem() + 1);
            }
            return true;
        }
    };
    private Handler mHandler = new WeakRefHandler(mCallback, Looper.getMainLooper());
    private Boolean stopThread = true;

    private GoodView mGoodView;

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_shop_detail,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);
        mBannerAdapter = new ShoppingBannerAdapter(getContext());
        mBannerList = new ArrayList<>();
        point = new ArrayList<>();
        mBannerAdapter.setPoint(point);
        mBannerAdapter.setList(mBannerList);
        mBannerAdapter.setViewPager(mBinding.viewPager);
        mBinding.viewPager.setAdapter(mBannerAdapter);
        mBinding.viewPager.setCurrentItem(mBannerList.size() * 10);
        //轮播图设置值
        mPresenter.initBanner(mBannerList, getContext(), point, mBinding.detailPoint, mBannerAdapter);
        new Thread(new MyRunble()).start();

        //品质保证
        mQualityList = new ArrayList<>();
        mQualityAdapter = new ShoppingQualityAdapter(mQualityList, getContext());
        mBinding.detailQuality.setAdapter(mQualityAdapter);
        mBinding.detailQuality.setSelector(new ColorDrawable(Color.TRANSPARENT));

        //售后服务
        mBinding.detailService.setAdapter(mQualityAdapter);
        mBinding.detailService.setSelector(new ColorDrawable(Color.TRANSPARENT));

        //产品参数
        mParameterList = new ArrayList<>();
        mParameterAdapter = new ShoppingmParameterAdapter(mParameterList, getContext());
        mBinding.detailParameter.setAdapter(mParameterAdapter);
        mBinding.detailParameter.setSelector(new ColorDrawable(Color.TRANSPARENT));

        //解决scrollView焦点问题，导致打开页面不在顶部
        mBinding.detailQuality.setFocusable(false);
        mBinding.detailService.setFocusable(false);
        mBinding.detailParameter.setFocusable(false);

        mGoodView = new GoodView(getContext());
        mBinding.detailGood.setOnClickListener(view -> {
            mGoodView.setText("+1");
            mGoodView.setTextColor(getResources().getColor(R.color.color_E31436));
            mGoodView.show(view);
        });
    }


    /******************轮播图需要*********************/
    public class MyRunble implements Runnable {

        @Override
        public void run() {
            while (stopThread) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(100);
            }
        }
    }

    @Override
    public void onDestroyView() {
        stopThread = false;
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroyView();
    }
}
