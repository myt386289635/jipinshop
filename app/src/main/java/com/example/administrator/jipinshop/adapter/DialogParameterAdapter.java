package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.TBShoppingDetailBean;
import com.example.administrator.jipinshop.databinding.ItemParameterDialogBinding;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/11/29
 * @Describe
 */
public class DialogParameterAdapter extends RecyclerView.Adapter<DialogParameterAdapter.ViewHolder> {

    private List<TBShoppingDetailBean.DataBean.ParametersListBean> mParameterList;
    private Context mContext;

    public DialogParameterAdapter(List<TBShoppingDetailBean.DataBean.ParametersListBean> parameterList, Context context) {
        mParameterList = parameterList;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemParameterDialogBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_parameter_dialog,viewGroup,false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if (position == 0){
            viewHolder.binding.itemContainer.setBackgroundResource(R.drawable.bg_f5f5f5_4);
        }else {
            viewHolder.binding.itemContainer.setBackgroundResource(R.drawable.bg_f5f5f5_3);
        }
        viewHolder.binding.itemTitle.setText(mParameterList.get(position).getName());
        viewHolder.binding.itemContent.setText(mParameterList.get(position).getValue());
    }

    @Override
    public int getItemCount() {
        return mParameterList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ItemParameterDialogBinding binding;

        public ViewHolder(@NonNull ItemParameterDialogBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
