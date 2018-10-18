package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.jipinshop.MyApplication;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.WebActivity;
import com.example.administrator.jipinshop.activity.home.find.FindDetailActivity;
import com.example.administrator.jipinshop.view.glide.imageloder.ImageManager;

import java.util.List;

public class CommonFindAdapter extends RecyclerView.Adapter<CommonFindAdapter.ViewHolder> {

    private List<String> mList;
    private Context mContext;

    public CommonFindAdapter(List<String> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_find, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ImageManager.displayRoundImage(MyApplication.imag,holder.mItemImage,0,0,10);
        holder.itemView.setOnClickListener(view -> {
            //点击跳转到webView详情
            mContext.startActivity(new Intent(mContext, WebActivity.class));
        });
    }

    @Override
    public int getItemCount() {
        // TODO: 2018/8/1 有假数据
        return mList.size() == 0 ? 0 : mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mItemImage;
        private TextView mItemName;
        private TextView mItemDescription;
        private TextView mItemTime;
        private TextView mItemLookNum;
        public ViewHolder(View itemView) {
            super(itemView);
            mItemImage = itemView.findViewById(R.id.item_image);
            mItemName = itemView.findViewById(R.id.item_name);
            mItemDescription = itemView.findViewById(R.id.item_description);
            mItemTime = itemView.findViewById(R.id.item_time);
            mItemLookNum = itemView.findViewById(R.id.item_lookNum);
        }
    }
}
