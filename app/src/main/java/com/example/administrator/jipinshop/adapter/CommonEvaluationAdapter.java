package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.EvaluationListBean;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import java.util.List;

public class CommonEvaluationAdapter extends RecyclerView.Adapter {

    private static final int HEAD = 1;
    private static final int CONTENT = 2;

    private List<EvaluationListBean.DataBean> mList;
    private Context mContext;
    private String headImg = "";//数据head图片

    public CommonEvaluationAdapter(List<EvaluationListBean.DataBean> list, Context context) {
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
                View content = LayoutInflater.from(mContext).inflate(R.layout.item_sreachfind, parent, false);
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
                if(TextUtils.isEmpty(headImg)){
                    GlideApp.loderImage(mContext,R.drawable.evaluating_banner,headViewHolder.mHeadImage,0,0);
                }else {
                    GlideApp.loderImage(mContext,headImg,headViewHolder.mHeadImage,0,0);
                }
                break;
            case CONTENT:
                ContentViewHolder viewHolder = (ContentViewHolder) holder;
                position = position - 1;
                viewHolder.title.setText(mList.get(position).getTitle());
                GlideApp.loderRoundImage(mContext,mList.get(position).getImg(),viewHolder.item_image);
                GlideApp.loderCircleImage(mContext,mList.get(position).getUser().getAvatar(),viewHolder.item_head,R.mipmap.rlogo,0);
                viewHolder.item_name.setText(mList.get(position).getUser().getNickname());
                viewHolder.item_pv.setText(mList.get(position).getPv() + "阅读");
                if(mList.get(position).getUser().getAuthentication() == 0){
                    //普通用户
                    viewHolder.item_grade.setVisibility(View.GONE);
                }else if(mList.get(position).getUser().getAuthentication() == 1){
                    //个人认证
                    viewHolder.item_grade.setVisibility(View.VISIBLE);
                    viewHolder.item_grade.setImageResource(R.mipmap.grade_peroson);
                }else {
                    //企业认证
                    viewHolder.item_grade.setVisibility(View.VISIBLE);
                    viewHolder.item_grade.setImageResource(R.mipmap.grade_peroson);
                }
                viewHolder.itemView.setOnClickListener(v -> {

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

        private TextView title;
        private ImageView item_image;
        private ImageView item_head;
        private ImageView item_grade;
        private TextView item_name;
        private TextView item_pv;

        public ContentViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_title);
            item_image = itemView.findViewById(R.id.item_image);
            item_head = itemView.findViewById(R.id.item_head);
            item_grade = itemView.findViewById(R.id.item_grade);
            item_name = itemView.findViewById(R.id.item_name);
            item_pv = itemView.findViewById(R.id.item_pv);
        }
    }
}
