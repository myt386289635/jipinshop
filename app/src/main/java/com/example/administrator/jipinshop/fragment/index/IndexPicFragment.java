package com.example.administrator.jipinshop.fragment.index;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.base.DBBaseFragment;

/**
 * @author 莫小婷
 * @create 2020/4/17
 * @Describe 引导页图片
 */
public class IndexPicFragment extends DBBaseFragment {

    private ImageView mImageView;
    private ImageView index_into;
    private RelativeLayout mContainer;

    public static IndexPicFragment getInstence(){
        IndexPicFragment fragment = new IndexPicFragment();
        return fragment;
    }

    @Override
    public View initLayout(LayoutInflater inflater, ViewGroup container) {
        View view =  inflater.inflate(R.layout.item_index,container,false);
        mImageView = view.findViewById(R.id.recommend_img_rotate);
        index_into = view.findViewById(R.id.index_into);
        mContainer = view.findViewById(R.id.Container);
        return view;
    }

    @Override
    public void initView() {
        index_into.setVisibility(View.GONE);
        mContainer.setBackgroundColor(Color.WHITE);
        mImageView.setImageResource(R.mipmap.guide_android5);
    }
}
