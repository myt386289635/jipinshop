package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.home.classification.ClassifyActivity;
import com.example.administrator.jipinshop.bean.SreachBean;
import com.example.administrator.jipinshop.view.glide.CircleImageView;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/8/15
 * @Describe
 */
public class SreachPagerAdapter extends RecyclerView.Adapter<SreachPagerAdapter.ViewHolder> {

    private List<SreachBean.GoodsCategoryListBean> list;
    private Context mContext;

    public SreachPagerAdapter(List<SreachBean.GoodsCategoryListBean> list, Context context) {
        this.list = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_sreach_pager,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.mTextView.setText(list.get(i).getCategoryName());
        GlideApp.loderImage(mContext, list.get(i).getImg(),viewHolder.mImageView,0,0);
        viewHolder.itemView.setOnClickListener(v -> {
            mContext.startActivity(new Intent(mContext, ClassifyActivity.class)
                    .putExtra("title",list.get(i).getCategoryName() + "榜单")
                    .putExtra("id",list.get(i).getCategoryId())
            );
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView mImageView;
        private TextView mTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.item_image);
            mTextView = itemView.findViewById(R.id.item_text);
        }
    }
}
