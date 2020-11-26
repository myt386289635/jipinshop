package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.EvaluationTabBean;
import com.example.administrator.jipinshop.databinding.ItemTakeOutBinding;
import com.example.administrator.jipinshop.util.ShopJumpUtil;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/11/26
 * @Describe
 */
public class TakeOutAdapter extends RecyclerView.Adapter<TakeOutAdapter.ViewHolder> {

    private List<EvaluationTabBean.DataBean.AdListBean> mList;
    private Context mContext;

    public TakeOutAdapter(List<EvaluationTabBean.DataBean.AdListBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTakeOutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_take_out,parent,false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        GlideApp.loderRoundImage(mContext,mList.get(position).getImg(),viewHolder.mBinding.itemImage);
        viewHolder.mBinding.itemImage.setOnClickListener(v -> {
            ShopJumpUtil.openBanner(mContext,mList.get(position).getType() + "",
                    mList.get(position).getObjectId(),mList.get(position).getName(),mList.get(position).getSource()
                    ,mList.get(position).getRemark() );
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ItemTakeOutBinding mBinding;

        public ViewHolder(@NonNull ItemTakeOutBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }
    }
}
