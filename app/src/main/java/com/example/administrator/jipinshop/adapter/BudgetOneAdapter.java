package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.BudgetDetailBean;
import com.example.administrator.jipinshop.databinding.ItemBudgetOneBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/6/5
 * @Describe
 */
public class BudgetOneAdapter extends RecyclerView.Adapter<BudgetOneAdapter.ViewHolder>{

    private List<BudgetDetailBean.DataBean> mList;
    private Context mContext;
    private RecyclerView.RecycledViewPool mViewPool;

    public BudgetOneAdapter(List<BudgetDetailBean.DataBean> list, Context context) {
        mList = list;
        mContext = context;
        mViewPool = new RecyclerView.RecycledViewPool();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemBudgetOneBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_budget_one,viewGroup,false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.list.clear();
        holder.list.addAll(mList.get(position).getCommissionDetailList());
        holder.mAdapter.notifyDataSetChanged();
        holder.binding.setValue(mList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends  RecyclerView.ViewHolder{

        private ItemBudgetOneBinding binding;
        private BudgetTwoAdapter mAdapter;
        private List<BudgetDetailBean.DataBean.CommissionDetailListBean> list;

        public ViewHolder(@NonNull ItemBudgetOneBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            binding.recyclerView.setLayoutManager(layoutManager);
            if (mViewPool != null) {
                binding.recyclerView.setRecycledViewPool(mViewPool);
            }
            list = new ArrayList<>();
            mAdapter = new BudgetTwoAdapter(list,mContext);
            binding.recyclerView.setAdapter(mAdapter);
        }
    }
}
