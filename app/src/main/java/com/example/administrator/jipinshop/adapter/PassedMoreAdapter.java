package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.PassedMoreBean;
import com.example.administrator.jipinshop.bean.TryDetailBean;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/3/26
 * @Describe
 */
public class PassedMoreAdapter extends RecyclerView.Adapter<PassedMoreAdapter.ViewHolder> {

    private Context mContext;
    private List<PassedMoreBean.DataBean> mList;

    public PassedMoreAdapter(Context context, List<PassedMoreBean.DataBean> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View convertView  = LayoutInflater.from(mContext).inflate(R.layout.item_try_passlist,viewGroup,false);
        ViewHolder holder = new ViewHolder(convertView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        GlideApp.loderCircleImage(mContext,mList.get(position).getUserAvatar(),holder.mImageView,0,0);
        holder.mTextView.setText(mList.get(position).getUserNickname());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends  RecyclerView.ViewHolder{

        private ImageView mImageView;
        private TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mImageView = view.findViewById(R.id.item_image);
            mTextView  = view.findViewById(R.id.item_name);
        }
    }
}
