package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.RecordBean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/8/8
 * @Describe
 */
public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {

    private Context mContext;
    private List<RecordBean.ListBean> mList;

    public RecordAdapter(Context context, List<RecordBean.ListBean> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_intergral_detail,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.item_name.setText("【提现】" + mList.get(position).getPay_name());
        holder.item_code.setText("+" + mList.get(position).getAmount());
        holder.item_time.setText(mList.get(position).getAccountTime());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView item_name;
        private TextView item_time;
        private TextView item_code;

        public ViewHolder(View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.item_name);
            item_time = itemView.findViewById(R.id.item_time);
            item_code = itemView.findViewById(R.id.item_code);

        }
    }
}
