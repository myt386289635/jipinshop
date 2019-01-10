package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.EvaluationTabBean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/1/10
 * @Describe
 */
public class HomePagerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener{

    private List<EvaluationTabBean.DataBean.AdListBean> mAdListBeans;
    private Context mContext;
    private List<ImageView> point;
    private ViewPager mViewPager;


    public HomePagerAdapter(Context context) {
        mContext = context;
    }

    public void setPoint(List<ImageView> point) {
        this.point = point;
    }

    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
    }

    public void setList(List<EvaluationTabBean.DataBean.AdListBean> list) {
        mAdListBeans = list;
    }

    @Override
    public int getCount() {
        return mAdListBeans.size() == 0 ? 0 : Integer.MAX_VALUE;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    //给ImageView设置显示的图片
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_banner, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.recommend_img_rotate);
        Glide.with(mContext).load(mAdListBeans.get(position % mAdListBeans.size()).getImg()).into(imageView);
        container.addView(view);
        mViewPager.addOnPageChangeListener(this);
        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < point.size(); i++) {
            if (i == position % mAdListBeans.size()){
                point.get(i).setImageResource(R.drawable.banner_down);
            }else {
                point.get(i).setImageResource(R.drawable.banner_up);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
