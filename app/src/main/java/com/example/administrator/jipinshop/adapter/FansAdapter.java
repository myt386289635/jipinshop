package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.FollowBean;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/1/16
 * @Describe
 */
public class FansAdapter extends RecyclerView.Adapter<FansAdapter.ViewHolder> {

    private List<FollowBean.DataBean> mList;
    private Context mContext;
    private OnItemClick mOnItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

    public FansAdapter(List<FollowBean.DataBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_follow,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.itemView.setOnClickListener(v -> {
            if(mOnItemClick != null){
                mOnItemClick.onItem(position);
            }
        });
        GlideApp.loderCircleImage(mContext,mList.get(position).getAvatar(),holder.item_image,R.mipmap.rlogo,0);
        holder.item_name.setText(mList.get(position).getNickname());
        if(mList.get(position).getStatus() == 1){
            //互相关注了
            holder.item_attention.setBackgroundResource(R.drawable.bg_my_attention);
            holder.item_attention.setTextColor(mContext.getResources().getColor(R.color.color_ACACAC));
            holder.item_attention.setText("相互关注");
        }else {
            //粉丝
            holder.item_attention.setBackgroundResource(R.drawable.bg_my_attentioned);
            holder.item_attention.setTextColor(mContext.getResources().getColor(R.color.color_E31436));
            holder.item_attention.setText("+关注");
        }


        holder.item_attention.setOnClickListener(view -> {
            if(mList.get(position).getStatus() == 1){
                //取消关注
                if (mOnItemClick != null) {
                    mOnItemClick.onItemDecAtten(position, holder.item_attention);
                }
            }else {
                if (mOnItemClick != null) {
                    mOnItemClick.onItemAtten(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends  RecyclerView.ViewHolder{
        private ImageView item_image;
        private TextView item_name;
        private TextView item_attention;
        public ViewHolder(View itemView) {
            super(itemView);
            item_image = itemView.findViewById(R.id.item_image);
            item_name = itemView.findViewById(R.id.item_name);
            item_attention = itemView.findViewById(R.id.item_attention);
        }
    }

    public interface OnItemClick{
        void onItem(int pos);

        void onItemAtten(int pos);//添加关注

        void onItemDecAtten(int pos, TextView textView);//取消关注
    }
}
