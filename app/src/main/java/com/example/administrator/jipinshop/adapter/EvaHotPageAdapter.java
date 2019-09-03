package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.EvaHotBean;
import com.example.administrator.jipinshop.util.ShopJumpUtil;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/8/8
 * @Describe
 */
public class EvaHotPageAdapter extends PagerAdapter {

    private Context mContext;
    private List<EvaHotBean.AdsBean> mList;

    public EvaHotPageAdapter(Context context, List<EvaHotBean.AdsBean> list) {
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
        GlideApp.loderRoundImage(mContext,mList.get(position).getImg(),imageView);
        imageView.setOnClickListener(v -> {
            if (mList.get(position).getType() == 2 || mList.get(position).getType() == 7){
                //评测详情、清单详情web
                ShopJumpUtil.jumpArticle(mContext,mList.get(position).getObjectId(),mList.get(position).getType() + "",1);//固定的
            }else if (mList.get(position).getType() == 71){
                //清单详情json
                ShopJumpUtil.jumpArticle(mContext,mList.get(position).getObjectId(),"7",3);//固定的
            }
        });
        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

}
