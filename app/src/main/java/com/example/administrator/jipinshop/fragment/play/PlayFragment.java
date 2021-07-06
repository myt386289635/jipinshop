package com.example.administrator.jipinshop.fragment.play;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.sreach.play.PlaySreachActivity;
import com.example.administrator.jipinshop.adapter.KTPagerAdapter3;
import com.example.administrator.jipinshop.adapter.PlayLeftAdapter;
import com.example.administrator.jipinshop.adapter.PlayRightAdapter;
import com.example.administrator.jipinshop.base.DBBaseFragment;
import com.example.administrator.jipinshop.bean.EvaluationTabBean;
import com.example.administrator.jipinshop.bean.PlayBean;
import com.example.administrator.jipinshop.databinding.FragmentPalyBinding;
import com.example.administrator.jipinshop.util.ShopJumpUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.WeakRefHandler;
import com.example.administrator.jipinshop.view.viewpager.TouchViewPager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2020/11/10
 * @Describe
 */
public class PlayFragment extends DBBaseFragment implements OnRefreshListener, PlayLeftAdapter.OnItem, PlayView, KTPagerAdapter3.OnClickItem, View.OnClickListener {

    @Inject
    PlayPresenter mPresenter;

    //右边
    private FragmentPalyBinding mBinding;
    private PlayRightAdapter mRightAdapter;
    private List<PlayBean.DataBean> mList;
    private Boolean[] once = {true};
    //轮播图
    private KTPagerAdapter3 pagerAdapter;
    private List<ImageView> pagerPoint;
    private List<EvaluationTabBean.DataBean.AdListBean> pagerList;
    private int currentItem = 0;
    private int count = 0;
    private Handler.Callback mCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 100){
                if (count > 1){
                    currentItem = currentItem % (count + 1) + 1;
                    if (currentItem == 1) {
                        mBinding.playViewpager.setCurrentItem(currentItem, false);
                        mHandler.sendEmptyMessage(100);
                    }else{
                        mBinding.playViewpager.setCurrentItem(currentItem);
                        mHandler.sendEmptyMessageDelayed(100,5000);
                    }
                }
            }
            return true;
        }
    };
    private Handler mHandler = new WeakRefHandler(mCallback, Looper.getMainLooper());
    //左边
    private PlayLeftAdapter mLeftAdapter;
    private int mSection = 0;//选中的位置
    private boolean isScrollListener = true;//是否开启右边滑动监听

    public static PlayFragment getInstance(){
        return new PlayFragment();
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_paly,container,false);
        mBinding.setListener(this);
        return mBinding.getRoot();
    }

    @Override
    public void initView() {
        mBaseFragmentComponent.inject(this);
        mPresenter.setView(this);
        mPresenter.setStatusBarHight(mBinding.statusBar,getContext());
        mPresenter.solveScoll(mBinding.swipeToLoad,mBinding,mBinding.appbar,once);

        //轮播图
        pagerAdapter = new KTPagerAdapter3(getContext());
        pagerPoint  = new ArrayList<>();
        pagerList = new ArrayList<>();
        pagerAdapter.setList(pagerList);
        pagerAdapter.setPoint(pagerPoint);
        pagerAdapter.setImgCenter(true);
        pagerAdapter.setOnClickItem(this);
        mBinding.playViewpager.setAdapter(pagerAdapter);
        mBinding.playViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {}

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < pagerPoint.size(); i++) {
                    if (i == toRealPosition(position)) {
                        pagerPoint.get(i).setImageResource(R.drawable.banner_down);
                    } else {
                        pagerPoint.get(i).setImageResource(R.drawable.banner_up);
                    }
                }
                currentItem = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case 0://No operation
                        if (currentItem == 0) {
                            mBinding.playViewpager.setCurrentItem(count, false);
                        } else if (currentItem == count + 1) {
                            mBinding.playViewpager.setCurrentItem(1, false);
                        }
                        break;
                    case 1:
                        if (currentItem == count + 1) {
                            mBinding.playViewpager.setCurrentItem(1, false);
                        } else if (currentItem == 0) {
                            mBinding.playViewpager.setCurrentItem(count, false);
                        }
                        break;
                }
            }
        });
        mBinding.playViewpager.setOnTouchListener(new TouchViewPager.OnTouchListener() {
            @Override
            public void startAutoPlay() {
                mHandler.removeMessages(100);
                mHandler.sendEmptyMessageDelayed(100,5000);
            }

            @Override
            public void stopAutoPlay() {
                mHandler.removeMessages(100);
            }
        });
        //右边数据
        mList = new ArrayList<>();
        mRightAdapter = new PlayRightAdapter(mList,getContext());
        mBinding.listView.setAdapter(mRightAdapter);
        mBinding.listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_TOUCH_SCROLL){
                    //正在滑动
                    isScrollListener = true;//滑动时恢复触发滑动监听
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (isScrollListener){
                    int section = mRightAdapter.getSectionForPosition(firstVisibleItem);//获取当前的head
                    if (mSection != section) {
                        mLeftAdapter.setSelect(section);
                        mLeftAdapter.notifyDataSetChanged();
                        mSection = section;
                    }
                }
            }
        });
        //左边数据
        mLeftAdapter = new PlayLeftAdapter(mList,getContext());
        mLeftAdapter.setSelect(mSection);
        mLeftAdapter.setOnItem(this);
        mBinding.leftList.setAdapter(mLeftAdapter);

        mBinding.swipeToLoad.setOnRefreshListener(this);
        mBinding.swipeToLoad.setRefreshing(true);
    }

    @Override
    public void onRefresh() {
        mPresenter.boxListAll(this.bindToLifecycle());
    }

    @Override
    public void onSuccess(PlayBean bean) {
        stopRefresh();
        //轮播图
        pagerList.clear();
        if (bean.getAd().size() > 1){
            for (int i = 0; i <= bean.getAd().size() + 1; i++) {//轮播图数据
                if (i == 0){//加入最后一个
                    pagerList.add(bean.getAd().get(bean.getAd().size() - 1));
                }else if ( i == (bean.getAd().size() + 1)){//加入第一个
                    pagerList.add(bean.getAd().get(0));
                }else {//正常数据
                    pagerList.add(bean.getAd().get(i - 1));
                }
            }
        }else{
            pagerList.addAll(bean.getAd());
        }
        initBanner();
        //商城列表
        mList.clear();
        mList.addAll(bean.getData());
        mRightAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFile(String error) {
        stopRefresh();
        ToastUtil.show(error);
    }

    //停止刷新
    public void stopRefresh(){
        if (mBinding.swipeToLoad != null && mBinding.swipeToLoad.isRefreshing()) {
            if(!mBinding.swipeToLoad.isRefreshEnabled()){
                mBinding.swipeToLoad.setRefreshEnabled(true);
                mBinding.swipeToLoad.setRefreshing(false);
                mBinding.swipeToLoad.setRefreshEnabled(false);
            }else {
                mBinding.swipeToLoad.setRefreshing(false);
            }
        }
        once[0] = false;
    }

    //返回真实的位置
    public int toRealPosition(int position) {
        int realPosition = 0;
        if (count != 0) {
            realPosition = (position - 1) % count;
        }
        if (realPosition < 0)
            realPosition += count;
        return realPosition;
    }

    //初始化轮播图
    public void initBanner() {
        if (pagerList.size() > 1){
            count = pagerList.size() - 2;
        }else{
            count = pagerList.size();
        }
        mHandler.removeMessages(100);//刷新时，要删除handler的请求
        mHandler.sendEmptyMessageDelayed(100,5000);
        pagerPoint.clear();
        mBinding.playPoint.removeAllViews();
        for (int i = 0; i < count; i++) {
            ImageView imageView = new ImageView(getContext());
            pagerPoint.add(imageView);
            if (i == 0) {
                imageView.setImageResource(R.drawable.banner_down);
            } else {
                imageView.setImageResource(R.drawable.banner_up);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = getResources().getDimensionPixelSize(R.dimen.x4);
            layoutParams.rightMargin = getResources().getDimensionPixelSize(R.dimen.x4);
            mBinding.playPoint.addView(imageView, layoutParams);
        }
        pagerAdapter.notifyDataSetChanged();
        if (count > 1){
            mBinding.playPoint.setVisibility(View.VISIBLE);
            mBinding.playViewpager.setCurrentItem(1,false);
        }else{
            mBinding.playPoint.setVisibility(View.INVISIBLE);
            mBinding.playViewpager.setCurrentItem(0,false);
        }
        mBinding.playViewpager.setOffscreenPageLimit(pagerList.size() - 1);//防止图片重叠的情况
    }

    @Override
    public void onItemSelect(int position) {
        isScrollListener = false;//点击时禁止触发滑动监听
        mSection = position;
        int y = (int) getResources().getDimension(R.dimen.y1);
        mBinding.listView.setSelectionFromTop((position * 2) , -y);//list滑动到固定position
        mLeftAdapter.setSelect(mSection);
        mLeftAdapter.notifyDataSetChanged();
    }

    //轮播点击
    @Override
    public void onClickItem(int postion) {
        ShopJumpUtil.openBanner(getContext(), pagerList.get(postion).getType() + "",
                pagerList.get(postion).getObjectId(), pagerList.get(postion).getName(),
                pagerList.get(postion).getSource(), pagerList.get(postion).getRemark());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.paly_search:
                //进入搜索
                startActivity(new Intent(getContext(), PlaySreachActivity.class));
                break;
            case R.id.paly_balck:
                if (getActivity() != null)
                    getActivity().finish();
                break;
        }
    }
}
