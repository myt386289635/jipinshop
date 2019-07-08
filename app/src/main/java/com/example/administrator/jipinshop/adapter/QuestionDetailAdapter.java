package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
                headViewHolder.headBinding.executePendingBindings();
                break;
            case content:
                ContentViewHolder contentViewHolder = (ContentViewHolder) viewHolder;
                int pos = position - 1;
                contentViewHolder.contentBinding.setDate(mList.get(pos));
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
}
