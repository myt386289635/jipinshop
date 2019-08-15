package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.EvaluationListBean;
import com.example.administrator.jipinshop.databinding.ItemNewareaBinding;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/6/28
 * @Describe 新品区域 adapter
 */
public class NewAreaAdapter extends RecyclerView.Adapter<NewAreaAdapter.ViewHolder> {

    private Context mContext;
    private List<EvaluationListBean.DataBean> mList;
    private OnItem mOnItem;

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    public NewAreaAdapter(Context context, List<EvaluationListBean.DataBean> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemNewareaBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_newarea,viewGroup,false);
        ViewHolder holder= new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.binding.setDate(mList.get(i));
        if(mList.get(i).getUser().getAuthentication() == 0){
            //普通用户
            viewHolder.binding.itemGrade.setVisibility(View.GONE);
        }else if(mList.get(i).getUser().getAuthentication() == 1){
            //个人认证
            viewHolder.binding.itemGrade.setVisibility(View.VISIBLE);
            viewHolder.binding.itemGrade.setImageResource(R.mipmap.grade_peroson);
        }else {
            //企业认证
            viewHolder.binding.itemGrade.setVisibility(View.VISIBLE);
            viewHolder.binding.itemGrade.setImageResource(R.mipmap.grade_enterprise);
        }
        viewHolder.itemView.setOnClickListener(v -> {
            if(mOnItem != null){
                mOnItem.onItem(i);
            }
        });
        viewHolder.binding.itemHead.setOnClickListener(v -> {
            // TODO: 2019/1/11 个人主页
        });
        viewHolder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ItemNewareaBinding binding;

        public ViewHolder(@NonNull ItemNewareaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnItem {
        void onItem(int pos);
    }
}
