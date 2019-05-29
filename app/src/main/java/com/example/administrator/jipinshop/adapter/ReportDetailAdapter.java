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
import com.example.administrator.jipinshop.bean.TryDetailBean;
import com.example.administrator.jipinshop.util.DistanceHelper;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/5/24
 * @Describe
 */
public class ReportDetailAdapter extends RecyclerView.Adapter{
    private static final int TEXT = 1;
    private static final int IMG = 2;

    private Context mContext;
    private List<TryDetailBean.DataBean.GoodsContentListBean> mList;

    public ReportDetailAdapter(Context context, List<TryDetailBean.DataBean.GoodsContentListBean> list) {
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
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_report_text1,viewGroup,false);
                holder = new TextViewHolder(view);
                break;
            case IMG:
                View view1 = LayoutInflater.from(mContext).inflate(R.layout.item_report_img,viewGroup,false);
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
                imgViewHolder.mImageViewClose.setVisibility(View.GONE);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imgViewHolder.mImageView.getLayoutParams();
                layoutParams.width = (int) (DistanceHelper.getAndroiodScreenwidthPixels(mContext) - mContext.getResources().getDimension(R.dimen.x28) - mContext.getResources().getDimension(R.dimen.x28));
                layoutParams.height = (int) (mList.get(position).getHeight() * layoutParams.width / mList.get(position).getWidth());
                imgViewHolder.mImageView.setLayoutParams(layoutParams);
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
            mTextView = itemView.findViewById(R.id.report_editText);
        }
    }

    class ImgViewHolder extends RecyclerView.ViewHolder{

        private ImageView mImageView;
        private ImageView mImageViewClose;

        public ImgViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.report_img);
            mImageViewClose = itemView.findViewById(R.id.report_imgClose);
        }
    }
}
