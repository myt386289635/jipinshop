package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.ChildrenTabBean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/1/5
 * @Describe
 */
public class HomeCommenTabAdapter extends RecyclerView.Adapter<HomeCommenTabAdapter.ViewHolder>{

    private List<ChildrenTabBean> mChildrenBeans;
    private Context mContext;
    private OnItem mOnItem;

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    public HomeCommenTabAdapter(List<ChildrenTabBean> list, Context context) {
        mChildrenBeans = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view = LayoutInflater.from(mContext).inflate(R.layout.children_tab, viewGroup, false);
       ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        holder.mItemName.setText(mChildrenBeans.get(i).getName());
        if(mChildrenBeans.get(i).getTag()){
            holder.mItemName.setTextColor(mContext.getResources().getColor(R.color.color_E31436));
        }else {
            holder.mItemName.setTextColor(mContext.getResources().getColor(R.color.color_ACACAC));
        }
        holder.itemView.setOnClickListener(view -> {
            if(mOnItem != null){
                mOnItem.onItem(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mChildrenBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mItemName;

        ViewHolder(View view) {
            super(view);
            mItemName = view.findViewById(R.id.item_name);
        }
    }

    public interface OnItem{
        void onItem(int pos);
    }
}
