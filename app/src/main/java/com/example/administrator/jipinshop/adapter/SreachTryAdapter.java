package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
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
        View Content2View = LayoutInflater.from(mContext).inflate(R.layout.item_try_content2,viewGroup,false);
        ViewHolder holder = new ViewHolder(Content2View);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewHolder.try_image.getLayoutParams();
        if(position == 0){
            layoutParams.topMargin = (int) mContext.getResources().getDimension(R.dimen.y48);
        }else {
            layoutParams.topMargin = 0;
        }
        viewHolder.try_image.setLayoutParams(layoutParams);
        GlideApp.loderImage(mContext,mList.get(position).getImg(),viewHolder.try_image,0,0);
        GlideApp.loderCircleImage(mContext,mList.get(position).getUser().getAvatar(),viewHolder.try_head,R.mipmap.rlogo,0);
        viewHolder.try_name.setText(mList.get(position).getTitle());
        viewHolder.try_nickName.setText(mList.get(position).getUser().getNickname());
        viewHolder.try_pv.setText(mList.get(position).getPv() + "阅读");
        viewHolder.itemView.setOnClickListener(v -> {
            if(mOnItem != null){
                mOnItem.onItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView try_image,try_head;
        private TextView try_name,try_nickName,try_pv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            try_image = itemView.findViewById(R.id.try_image);
            try_head = itemView.findViewById(R.id.try_head);
            try_name = itemView.findViewById(R.id.try_name);
            try_nickName = itemView.findViewById(R.id.try_nickName);
            try_pv = itemView.findViewById(R.id.try_pv);
        }
    }

    public interface OnItem {
        void onItem(int pos);
    }
}