package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/8/6
 * @Describe
 */
public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.ViewHolder> {

    private List<String> mList;
    private Context mContext;
    private String type;

    public CouponAdapter(List<String> list, Context context, String type) {
        mList = list;
        mContext = context;
        this.type = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_coupon,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(type.equals("1")){
            //未使用
            holder.item_use.setVisibility(View.VISIBLE);
            holder.container.setBackgroundResource(R.mipmap.coupon_unused);
        }else if(type.equals("2")){
            //已使用
            holder.item_use.setVisibility(View.GONE);
            holder.container.setBackgroundResource(R.mipmap.coupon_used);
        }else {
            //已过期
            holder.item_use.setVisibility(View.GONE);
            holder.container.setBackgroundResource(R.mipmap.coupon_used);
        }

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView item_money;
        private TextView item_notice;
        private TextView item_name;
        private TextView item_time;
        private TextView item_use;
        private RelativeLayout container;

        public ViewHolder(View itemView) {
            super(itemView);
            item_money = itemView.findViewById(R.id.item_money);
            item_notice = itemView.findViewById(R.id.item_notice);
            item_name = itemView.findViewById(R.id.item_name);
            item_time = itemView.findViewById(R.id.item_time);
            item_use = itemView.findViewById(R.id.item_use);
            container = itemView.findViewById(R.id.container);
        }
    }
}
