package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.databinding.ItemBudgetBinding;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/3/7
 * @Describe 收入明细
 */
public class BudgetDetailAdapter extends RecyclerView.Adapter<BudgetDetailAdapter.ViewHolder>{

    private Context mContext;
    private List<String> mList;

    public BudgetDetailAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemBudgetBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_budget,viewGroup,false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.binding.itemContainer.getLayoutParams();
        if (position == mList.size() - 1){
            params.bottomMargin = (int) mContext.getResources().getDimension(R.dimen.y30);
        }else {
            params.bottomMargin = 0;
        }
        holder.binding.itemContainer.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ItemBudgetBinding binding;

        public ViewHolder(ItemBudgetBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
