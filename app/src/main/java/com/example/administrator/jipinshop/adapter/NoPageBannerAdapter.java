package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.jipinshop.R;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/11/13
 * @Describe 不滑动的轮播图
 */
public class NoPageBannerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener{

    private Context mContext;
    private List<ImageView> point;
    private ViewPager mViewPager;
    private List<String> mList;
    private Boolean imgCenter = false;
    private Boolean imgFixCenter = false;
    private TextView pagerIndex;
    private int type = 0;//点的样式  默认0
    private int refresh = 0; //是否需要刷新 0：不需要 ；非0：需要

    public void setRefresh(int refresh) {
        this.refresh = refresh;
    }

    public void setImgFixCenter(Boolean imgFixCenter) {
        this.imgFixCenter = imgFixCenter;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setPagerIndex(TextView pagerIndex) {
        this.pagerIndex = pagerIndex;
    }

    public NoPageBannerAdapter(Context context) {
        mContext = context;
    }

    public void setImgCenter(Boolean imgCenter) {
        this.imgCenter = imgCenter;
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
        return mList.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        if (refresh != 0){
            return POSITION_NONE;
        }else {
            return super.getItemPosition(object);
        }
    }

    //给ImageView设置显示的图片
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_common_banner, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.recommend_img_rotate);
        if (imgCenter){
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }else if (imgFixCenter){
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
        Glide.with(mContext).load(mList.get(position)).into(imageView);
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
        if (type == 0){//默认样式
            for (int i = 0; i < point.size(); i++) {
                if (i == position){
                    point.get(i).setImageResource(R.drawable.banner_down);
                }else {
                    point.get(i).setImageResource(R.drawable.banner_up);
                }
            }
        }else if (type == 1){
            for (int i = 0; i < point.size(); i++) {
                if (i == position){
                    point.get(i).setImageResource(R.mipmap.member_yes);
                }else {
                    point.get(i).setImageResource(R.mipmap.member_no);
                }
            }
        }
        if (pagerIndex != null){
            pagerIndex.setText((position+1) + "/" + mList.size());
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
