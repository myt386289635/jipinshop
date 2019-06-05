package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.databinding.ItemBudgetTwoBinding;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/6/5
 * @Describe
 */
public class BudgetTwoAdapter extends RecyclerView.Adapter<BudgetTwoAdapter.ViewHolder>{

    private List<String> mList;
    private Context mContext;

    public BudgetTwoAdapter(List<String> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemBudgetTwoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_budget_two,viewGroup,false);
        ViewHolder holder  = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ItemBudgetTwoBinding binding;

        public ViewHolder(@NonNull ItemBudgetTwoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
