package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.home.evaluation.EvaluationDetailActivity;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.bean.EvaluationListBean;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.glide.imageloder.ImageManager;

import java.util.List;

public class CommonEvaluationAdapter extends RecyclerView.Adapter {

    private static final int HEAD = 1;
    private static final int CONTENT = 2;

    private List<EvaluationListBean.ListBean> mList;
    private Context mContext;
    private OnClickItem mOnClickItem;

    public void setOnClickItem(OnClickItem onClickItem) {
        mOnClickItem = onClickItem;
    }

    public CommonEvaluationAdapter(List<EvaluationListBean.ListBean> list, Context context) {
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
                ImageManager.displayImage("drawable://"+R.drawable.evaluating_banner,headViewHolder.mHeadImage,0,0);
                break;
            case CONTENT:
                ContentViewHolder contentViewHolder = (ContentViewHolder) holder;
                ImageManager.displayRoundImage(mList.get(position -1).getImgId(),contentViewHolder.content_image,0,0,10);
                ImageManager.displayCircleImage(mList.get(position -1).getUserShopmember().getUserNickImg(),contentViewHolder.content_head,0,R.mipmap.rlogo);
                contentViewHolder.content_title.setText(mList.get(position - 1).getEvalWayName());
                contentViewHolder.itemView.setOnClickListener(v -> {
                    if(!SPUtils.getInstance(CommonDate.USER).getBoolean(CommonDate.userLogin,false)){
                        mContext.startActivity(new Intent(mContext, LoginActivity.class));
                        return;
                    }
                    if (ClickUtil.isFastDoubleClick(800)) {
                        return;
                    }else{
                        //点击跳转到评测详情
                        mContext.startActivity(new Intent(mContext, EvaluationDetailActivity.class)
                                .putExtra("id",mList.get(position - 1).getEvalWayId())
                        );
                    }
                });

                contentViewHolder.content_name.setText(mList.get(position -1).getUserShopmember().getUserNickName());
                contentViewHolder.content_actionNum.setText("粉丝数:" + mList.get(position -1).getUserShopmember().getFansCount());
                if (mList.get(position -1).getConcernNum() == 0) {
                    contentViewHolder.content_attention.setBackgroundResource(R.drawable.bg_attention);
                    contentViewHolder.content_attention.setText("+关注");
                    contentViewHolder.content_attention.setTextColor(mContext.getResources().getColor(R.color.color_E31436));
                }else {
                    contentViewHolder.content_attention.setBackgroundResource(R.drawable.bg_attentioned);
                    contentViewHolder.content_attention.setText("已关注");
                    contentViewHolder.content_attention.setTextColor(mContext.getResources().getColor(R.color.color_white));
                }
                contentViewHolder.content_lookNum.setText(mList.get(position -1).getVisitCount());
                contentViewHolder.content_commentNum.setText(mList.get(position -1).getCommentNum());
                if(TextUtils.isEmpty(mList.get(position -1).getShowTime())){
                    contentViewHolder.content_time.setText(mList.get(position -1).getPublishTime().split(" ")[0]);
                }else {
                    contentViewHolder.content_time.setText(mList.get(position -1).getShowTime());
                }
                contentViewHolder.content_attention.setOnClickListener(v -> {
                    if (mList.get(position -1).getConcernNum() == 0) {
                        //添加关注
                        if(mOnClickItem != null){
                            mOnClickItem.onAttenInsItem(mList.get(position -1).getUserId(),position -1);
                        }
                    }else {
                        //删除关注
                        if(mOnClickItem != null){
                            mOnClickItem.onAttenDecItem(mList.get(position -1).getUserId(),position -1);
                        }
                    }
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

    public interface OnClickItem{
        void onAttenInsItem(String attentionUserId,int pos);
        void onAttenDecItem(String attentionUserId,int pos);
    }
}
