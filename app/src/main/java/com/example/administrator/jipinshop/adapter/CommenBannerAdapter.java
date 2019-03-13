package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.jipinshop.R;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/3/13
 * @Describe 公共轮播图adapter
 */
public class CommenBannerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener{
    private Context mContext;
    private List<ImageView> point;
    private ViewPager mViewPager;
    private List<String> mList;

    public CommenBannerAdapter(Context context) {
        mContext = context;
    }

    public void setPoint(List<ImageView> point) {
        this.point = point;
    }

    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
    }

    public void setList(List<String> list) {
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size() == 0 ? 0 : Integer.MAX_VALUE;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    //给ImageView设置显示的图片
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_common_banner, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.recommend_img_rotate);
        Glide.with(mContext).load(mList.get(position % mList.size())).into(imageView);
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
            if (i == position % mList.size()){
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
