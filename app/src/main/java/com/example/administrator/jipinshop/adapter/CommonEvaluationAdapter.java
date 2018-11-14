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
import com.example.administrator.jipinshop.activity.home.evaluation.EvaluationDetailActivity;
import com.example.administrator.jipinshop.view.glide.imageloder.ImageManager;

import java.util.List;

public class CommonEvaluationAdapter extends RecyclerView.Adapter {

    private static final int HEAD = 1;
    private static final int CONTENT = 2;

    private List<String> mList;
    private Context mContext;

    public CommonEvaluationAdapter(List<String> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case HEAD:
                View head = LayoutInflater.from(mContext).inflate(R.layout.item_evaluation_head, parent, false);
                HeadViewHolder headViewHolder = new HeadViewHolder(head);
                return headViewHolder;
            case CONTENT:
                View content = LayoutInflater.from(mContext).inflate(R.layout.item_evaluation_content, parent, false);
                ContentViewHolder contentViewHolder = new ContentViewHolder(content);
                return contentViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type) {
            case HEAD:
                HeadViewHolder headViewHolder = (HeadViewHolder) holder;
                ImageManager.displayImage(MyApplication.imag,headViewHolder.mHeadImage,0,0);
                break;
            case CONTENT:
                ContentViewHolder contentViewHolder = (ContentViewHolder) holder;
                ImageManager.displayRoundImage(MyApplication.imag,contentViewHolder.content_image,0,0,10);
                ImageManager.displayCircleImage(MyApplication.imag,contentViewHolder.content_head,0,0);
                contentViewHolder.content_title.setText("23款网红榨汁机大测评，国货居然这么能打？");
                contentViewHolder.itemView.setOnClickListener(v -> {
                    //点击跳转到评测详情
                    mContext.startActivity(new Intent(mContext, EvaluationDetailActivity.class));
                });
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEAD;
        } else {
            return CONTENT;
        }
    }

    @Override
    public int getItemCount() {
        // TODO: 2018/8/1 有假数据
        return mList.size() == 0 ? 0 : mList.size() + 1;
    }

    class HeadViewHolder extends RecyclerView.ViewHolder {

        private ImageView mHeadImage;
        public HeadViewHolder(View itemView) {
            super(itemView);
            mHeadImage = itemView.findViewById(R.id.head_image);
        }
    }


    class ContentViewHolder extends RecyclerView.ViewHolder {

        private ImageView content_head;
        private TextView content_name;
        private TextView content_actionNum;
        private TextView content_attention;
        private ImageView content_image;
        private TextView content_time;
        private TextView content_commentNum;
        private TextView content_lookNum;
        private TextView content_title;

        public ContentViewHolder(View itemView) {
            super(itemView);
            content_head = itemView.findViewById(R.id.content_head);
            content_name = itemView.findViewById(R.id.content_name);
            content_actionNum = itemView.findViewById(R.id.content_actionNum);
            content_attention = itemView.findViewById(R.id.content_attention);
            content_image = itemView.findViewById(R.id.content_image);
            content_time = itemView.findViewById(R.id.content_time);
            content_commentNum = itemView.findViewById(R.id.content_commentNum);
            content_lookNum = itemView.findViewById(R.id.content_lookNum);
            content_title = itemView.findViewById(R.id.content_title);
        }
    }
}
