package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/9/1
 * @Describe
 */
public  class IndexAdapter  extends PagerAdapter {
    private Context mContext;
    private List<Integer> mList;
    private OnLick mOnLick;

    public void setOnLick(OnLick onLick) {
        mOnLick = onLick;
    }

    public IndexAdapter(Context context, List<Integer> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size() == 0 ? 0 : mList.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    //给ImageView设置显示的图片
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_index, container, false);
        ImageView imageView = view.findViewById(R.id.recommend_img_rotate);
        ImageView textView = view.findViewById(R.id.index_into);
        RelativeLayout relativeLayout = view.findViewById(R.id.Container);
        imageView.setImageResource(mList.get(position));
        if(position == 3){
            textView.setVisibility(View.VISIBLE);
            textView.setOnClickListener(view1 -> {
                if(mOnLick != null){
                    mOnLick.onLinkgo();
                }
            });
        }else {
            textView.setVisibility(View.GONE);
        }
        switch (position){
            case 0:
                relativeLayout.setBackgroundResource(R.color.color_FD7F2A);
                break;
            case 1:
                relativeLayout.setBackgroundResource(R.color.color_67B6F7);
                break;
            case 2:
                relativeLayout.setBackgroundResource(R.color.color_F76667);
                break;
            case 3:
                relativeLayout.setBackgroundResource(R.color.color_4FCACB);
                break;
        }
        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }


    public interface OnLick{
        void onLinkgo();
    }

}
