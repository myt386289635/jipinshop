package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/7/3
 * @Describe 答题区详情
 */
public class QuestionDetailAdapter extends RecyclerView.Adapter {

    private static final int head = 1;
    private static final int content = 2;

    private List<String> mList;
    private Context mContext;

    public QuestionDetailAdapter(List<String> list, Context context) {
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

                break;
            case content:

                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    class HeadViewHolder extends RecyclerView.ViewHolder{

        public HeadViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class ContentViewHolder extends RecyclerView.ViewHolder{

        public ContentViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
