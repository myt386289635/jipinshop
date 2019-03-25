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
import com.example.administrator.jipinshop.bean.TryDetailBean;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/3/23
 * @Describe
 */
public class TryDetailRVAdapter extends RecyclerView.Adapter{

    private static final int TEXT = 1;
    private static final int IMG = 2;

    private Context mContext;
    private List<TryDetailBean.DataBean.GoodsContentListBean> mList;

    public TryDetailRVAdapter(Context context, List<TryDetailBean.DataBean.GoodsContentListBean> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getItemViewType(int position) {
        if(mList.get(position).getType().equals("1")){
            return TEXT;
        }else {
            return IMG;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        RecyclerView.ViewHolder holder = null;
        switch (i){
            case TEXT:
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_text,viewGroup,false);
                holder = new TextViewHolder(view);
                break;
            case IMG:
                View view1 = LayoutInflater.from(mContext).inflate(R.layout.item_img,viewGroup,false);
                holder = new ImgViewHolder(view1);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type){
            case TEXT:
                TextViewHolder textViewHolder = (TextViewHolder) holder;
                textViewHolder.mTextView.setText(mList.get(position).getValue());
                break;
            case IMG:
                ImgViewHolder imgViewHolder = (ImgViewHolder) holder;
                GlideApp.loderImage(mContext,mList.get(position).getValue(),imgViewHolder.mImageView,0,0);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class TextViewHolder extends RecyclerView.ViewHolder{

        private TextView mTextView;

        public TextViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.item_text);
        }
    }

    class ImgViewHolder extends RecyclerView.ViewHolder{

        private ImageView mImageView;

        public ImgViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.item_image);
        }
    }
}
