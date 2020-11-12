package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.TbkIndexBean;
import com.example.administrator.jipinshop.databinding.ItemPlayResultBinding;
import com.example.administrator.jipinshop.util.ShopJumpUtil;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/11/12
 * @Describe
 */
public class PlaySreachResultAdapter extends RecyclerView.Adapter<PlaySreachResultAdapter.ViewHolder> {

    private List<TbkIndexBean.DataBean.BoxCategoryListBean.ListBean> mList;
    private Context mContext;

    public PlaySreachResultAdapter(List<TbkIndexBean.DataBean.BoxCategoryListBean.ListBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemPlayResultBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_play_result,viewGroup,false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.mBinding.setBean(mList.get(position));
        viewHolder.mBinding.executePendingBindings();
        viewHolder.itemView.setOnClickListener(v -> {
            ShopJumpUtil.openBanner(mContext, mList.get(position).getType() + "",
                    mList.get(position).getTargetId(), mList.get(position).getTitle(),
                    mList.get(position).getSource(),mList.get(position).getRemark());
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ItemPlayResultBinding mBinding;

        public ViewHolder(@NonNull ItemPlayResultBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }
    }
}
