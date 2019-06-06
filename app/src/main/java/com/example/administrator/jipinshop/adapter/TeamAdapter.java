package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.databinding.ItemTeamBinding;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/6/6
 * @Describe
 */
public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder>{

    private Context mContext;
    private List<String> mList;

    public TeamAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemTeamBinding binding  = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_team,viewGroup,false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.binding.itemContainer.getLayoutParams();
        if (position == 0){
            params.topMargin = (int) mContext.getResources().getDimension(R.dimen.y22);
        }else {
            params.topMargin =0;
        }
        holder.binding.itemContainer.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ItemTeamBinding binding;

        public ViewHolder(@NonNull ItemTeamBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
