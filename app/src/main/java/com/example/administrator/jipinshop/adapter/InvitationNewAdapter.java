package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.view.glide.GlideApp;
import com.example.administrator.jipinshop.view.glide.RoundTransform;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/6/10
 * @Describe
 */
public class InvitationNewAdapter extends PagerAdapter{

    private Context mContext;
    private List<String> mList;
    private Boolean imgCenter = false;

    public void setImgCenter(Boolean imgCenter) {
        this.imgCenter = imgCenter;
    }

    public InvitationNewAdapter(Context context, List<String> list) {
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_invitation, container, false);
        ImageView imageView = view.findViewById(R.id.recommend_img_rotate);
        if (imgCenter){
            GlideApp.loderRoundImage(mContext,mList.get(position),imageView);
        }else {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions
                    .transform(new RoundTransform(20,0, RoundTransform.CornerType.ALL));
            if (mContext != null) {
                Glide.with(mContext)
                        .load(mList.get(position))
                        .apply(requestOptions)
                        .into(imageView);
            }
        }
        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

}
