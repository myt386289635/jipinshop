package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.minekt.UserActivity;
import com.example.administrator.jipinshop.bean.QuestionsBean;
import com.example.administrator.jipinshop.databinding.ItemQuestionBinding;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/7/3
 * @Describe 话题问答
 */
public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {

    private List<QuestionsBean.DataBean> mList;
    private Context mContext;
    private OnClickView mView;

    public void setView(OnClickView view) {
        mView = view;
    }


    public QuestionsAdapter(List<QuestionsBean.DataBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemQuestionBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_question,viewGroup,false);
        ViewHolder holder  = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        viewHolder.itemView.setOnClickListener(v -> {
            if (mView != null){
                mView.onClickArticle(position);
            }
        });
        viewHolder.binding.itemImage.setOnClickListener(v -> {
            if (mList.get(position).getAnswer() != null && !TextUtils.isEmpty(mList.get(position).getAnswer().getUserId())){
                if (mView != null){
                    mView.onClickUserInfo(mList.get(position).getAnswer().getUserId());
                }
            }
        });
        viewHolder.binding.setDate(mList.get(position));
        viewHolder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ItemQuestionBinding binding;

        public ViewHolder(@NonNull ItemQuestionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnClickView{
        void onClickArticle(int position);//话题详情
        void onClickUserInfo(String userId);//点击进入个人详情
    }
}
