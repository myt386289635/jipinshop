package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.minekt.UserActivity;
import com.example.administrator.jipinshop.bean.SreachResultArticlesBean;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/3/21
 * @Describe 试用的
 */
public class SreachTryAdapter extends RecyclerView.Adapter<SreachTryAdapter.ViewHolder>{

    private List<SreachResultArticlesBean.DataBean> mList;
    private Context mContext;
    private OnItem mOnItem;

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    public SreachTryAdapter(List<SreachResultArticlesBean.DataBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View Content2View = LayoutInflater.from(mContext).inflate(R.layout.item_sreach_try,viewGroup,false);
        ViewHolder holder = new ViewHolder(Content2View);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        GlideApp.loderRoundImage(mContext,mList.get(position).getImg(),viewHolder.item_image,0,0);
        GlideApp.loderCircleImage(mContext,mList.get(position).getUser().getAvatar(),viewHolder.item_userImg,R.mipmap.rlogo,0);
        viewHolder.item_name.setText(mList.get(position).getTitle());
        viewHolder.item_userName.setText(mList.get(position).getUser().getNickname());
        viewHolder.item_pv.setText(mList.get(position).getPv() + "阅读");
        viewHolder.itemView.setOnClickListener(v -> {
            if(mOnItem != null){
                mOnItem.onItem(position);
            }
        });
        viewHolder.item_userImg.setOnClickListener(v -> {
            mContext.startActivity(new Intent(mContext, UserActivity.class)
                    .putExtra("userid",mList.get(position).getUser().getUserId())
            );
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView item_image,item_userImg;
        private TextView item_name,item_userName,item_pv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_image = itemView.findViewById(R.id.item_image);
            item_userImg = itemView.findViewById(R.id.item_userImg);
            item_name = itemView.findViewById(R.id.item_name);
            item_userName = itemView.findViewById(R.id.item_userName);
            item_pv = itemView.findViewById(R.id.item_pv);
        }
    }

    public interface OnItem {
        void onItem(int pos);
    }
}
