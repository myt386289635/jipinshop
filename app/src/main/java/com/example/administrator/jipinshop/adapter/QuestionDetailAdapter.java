package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.QuestionsBean;
import com.example.administrator.jipinshop.databinding.ItemQuestionDetaicontentBinding;
import com.example.administrator.jipinshop.databinding.ItemQuestionDetaiheadBinding;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/7/3
 * @Describe 答题区详情
 */
public class QuestionDetailAdapter extends RecyclerView.Adapter {

    private static final int head = 1;
    private static final int content = 2;

    private List<QuestionsBean.DataBean.AnswerBean> mList;
    private Context mContext;
    private QuestionsBean.DataBean mBean;
    private OnClickLayout mOnClickLayout;

    public void setOnClickLayout(OnClickLayout onClickLayout) {
        mOnClickLayout = onClickLayout;
    }

    public void setBean(QuestionsBean.DataBean bean) {
        mBean = bean;
    }

    public QuestionDetailAdapter(List<QuestionsBean.DataBean.AnswerBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return head;
        }else {
            return content;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        RecyclerView.ViewHolder holder = null;
        switch (type){
            case head:
                ItemQuestionDetaiheadBinding headBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_question_detaihead,viewGroup,false);
                holder = new HeadViewHolder(headBinding);
                break;
            case content:
                ItemQuestionDetaicontentBinding contentBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_question_detaicontent,viewGroup,false);
                holder = new ContentViewHolder(contentBinding);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        int type = getItemViewType(position);
        switch (type){
            case head:
                HeadViewHolder headViewHolder = (HeadViewHolder) viewHolder;
                headViewHolder.headBinding.setDate(mBean);
                if (mBean.getUser().getFollow().equals("0")){
                    //未关注
                    headViewHolder.headBinding.itemAttention.setText("关  注");
                    headViewHolder.headBinding.itemAttention.setBackgroundResource(R.drawable.bg_my_attentioned2);
                    headViewHolder.headBinding.itemAttention.setTextColor(Color.WHITE);
                    headViewHolder.headBinding.itemAttention.setOnClickListener(v -> {
                        if (mOnClickLayout != null){
                            mOnClickLayout.onClickFollow();
                        }
                    });
                }else {
                    //已关注
                    headViewHolder.headBinding.itemAttention.setText("已关注");
                    headViewHolder.headBinding.itemAttention.setBackgroundResource(R.drawable.bg_my_attention);
                    headViewHolder.headBinding.itemAttention.setTextColor(mContext.getResources().getColor(R.color.color_ACACAC));
                    headViewHolder.headBinding.itemAttention.setOnClickListener(v -> {
                        if (mOnClickLayout != null){
                            mOnClickLayout.onClickUnFollow();
                        }
                    });
                }
                headViewHolder.headBinding.executePendingBindings();
                break;
            case content:
                ContentViewHolder contentViewHolder = (ContentViewHolder) viewHolder;
                int pos = position - 1;
                contentViewHolder.contentBinding.setDate(mList.get(pos));
                if (mList.get(pos).getVote().equals("1")){
                    //点赞过
                    contentViewHolder.contentBinding.itemGood.setTextColor(mContext.getResources().getColor(R.color.color_E84643));
                    Drawable drawable= mContext.getResources().getDrawable(R.mipmap.question_good);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    contentViewHolder.contentBinding.itemGood.setCompoundDrawables(null,null,drawable,null);
                    contentViewHolder.contentBinding.itemGood.setOnClickListener(v -> {
                        if (mOnClickLayout != null){
                            mOnClickLayout.onClickUnGood(pos);
                        }
                    });
                }else {
                    //未点赞
                    contentViewHolder.contentBinding.itemGood.setTextColor(mContext.getResources().getColor(R.color.color_9D9D9D));
                    Drawable drawable= mContext.getResources().getDrawable(R.mipmap.question_ungood);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    contentViewHolder.contentBinding.itemGood.setCompoundDrawables(null,null,drawable,null);
                    contentViewHolder.contentBinding.itemGood.setOnClickListener(v -> {
                        if (mOnClickLayout != null){
                            mOnClickLayout.onClickGood(pos);
                        }
                    });
                }
                contentViewHolder.contentBinding.executePendingBindings();
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    class HeadViewHolder extends RecyclerView.ViewHolder{

        private ItemQuestionDetaiheadBinding headBinding;

        public HeadViewHolder(@NonNull ItemQuestionDetaiheadBinding headBinding) {
            super(headBinding.getRoot());
            this.headBinding = headBinding;
        }
    }

    class ContentViewHolder extends RecyclerView.ViewHolder{

        private ItemQuestionDetaicontentBinding contentBinding;

        public ContentViewHolder(@NonNull ItemQuestionDetaicontentBinding contentBinding) {
            super(contentBinding.getRoot());
            this.contentBinding = contentBinding;
        }
    }

    public interface OnClickLayout{
        void onClickGood(int postion);//点赞
        void onClickUnGood(int position);//取消点赞
        void onClickFollow();//关注
        void onClickUnFollow();//取消关注
    }
}
