package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.MemberNewBean;
import com.example.administrator.jipinshop.databinding.ItemVideoMemberBinding;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/9/10
 * @Describe
 */
public class MemberVideoAdapter extends RecyclerView.Adapter<MemberVideoAdapter.ViewHolder> {

    private List<MemberNewBean.DataBean.LevelDetail7Bean.ListBeanXXX> mList;
    private Context mContext;
    private OnItem mOnItem;

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    public MemberVideoAdapter(List<MemberNewBean.DataBean.LevelDetail7Bean.ListBeanXXX> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemVideoMemberBinding view  = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_video_member,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.mBinding.setBean(mList.get(position));
        viewHolder.mBinding.executePendingBindings();
        viewHolder.itemView.setOnClickListener(v -> {
            if (mOnItem != null)
                mOnItem.onItem(position);
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ItemVideoMemberBinding mBinding;

        public ViewHolder(@NonNull ItemVideoMemberBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }
    }

    public interface OnItem{
        void onItem(int position);
    }
}
