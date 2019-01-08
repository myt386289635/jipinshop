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
import com.example.administrator.jipinshop.bean.SreachHistoryBean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/1/8
 * @Describe
 */
public class SreachAdapter extends RecyclerView.Adapter<SreachAdapter.ViewHolder>{

    private List<SreachHistoryBean.DataBean.LogListBean> mHistroyList;
    private Context mContext;
    private OnItem mOnItem;

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    public SreachAdapter(List<SreachHistoryBean.DataBean.LogListBean> histroyList, Context context) {
        mHistroyList = histroyList;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view  = LayoutInflater.from(mContext).inflate(R.layout.item_sreach,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.name.setText(mHistroyList.get(i).getWord());
        viewHolder.des.setOnClickListener(v -> {
            if(mOnItem != null){
                mOnItem.onItemDelete(i);
            }
        });
        viewHolder.itemView.setOnClickListener(v -> {
            if(mOnItem != null){
                mOnItem.onItemClick(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHistroyList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private ImageView des;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            des = itemView.findViewById(R.id.item_dec);
        }
    }

    public interface OnItem{
        void onItemClick(int position);
        void onItemDelete(int position);
    }
}
