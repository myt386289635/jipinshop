package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.TitleBean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/1/4
 * @Describe tab目录
 */
public class HomeTabAdapter extends RecyclerView.Adapter<HomeTabAdapter.ViewHolder>{

    private List<TitleBean> mBeans;
    private Context mContext;
    private OnClickItem mOnClickItem;

    public HomeTabAdapter(List<TitleBean> title, Context context) {
        this.mBeans = title;
        mContext = context;
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        mOnClickItem = onClickItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.tab_item,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.mTextView.setText(mBeans.get(i).getString());
        if(mBeans.get(i) != null && mBeans.get(i).getTag()){
            viewHolder.mTextView.setTextColor(mContext.getResources().getColor(R.color.color_2B2B2B));
            viewHolder.mTextView.setTextSize(16);
            viewHolder.mView.setVisibility(View.VISIBLE);
        }else {
            viewHolder.mTextView.setTextColor(mContext.getResources().getColor(R.color.color_2B2B2B));
            viewHolder.mTextView.setTextSize(16);
            viewHolder.mView.setVisibility(View.GONE);
        }

        if (i == 0){
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewHolder.mTextView.getLayoutParams();
            layoutParams.leftMargin = mContext.getResources().getDimensionPixelSize(R.dimen.x28);
        }else {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewHolder.mTextView.getLayoutParams();
            layoutParams.leftMargin = mContext.getResources().getDimensionPixelSize(R.dimen.x10);
        }

        viewHolder.mTextView.setOnClickListener(v -> {
            if(mOnClickItem != null){
                mOnClickItem.onClickItem(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBeans.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView mTextView;
        View mView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tab_text);
            mView = itemView.findViewById(R.id.tab_line);
        }
    }

    public interface OnClickItem{
        void onClickItem(int pos);
    }
}
