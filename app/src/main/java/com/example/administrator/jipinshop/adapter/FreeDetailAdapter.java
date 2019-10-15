package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.FreeDetailBean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/10/11
 * @Describe 免单详情表格
 */
public class FreeDetailAdapter extends RecyclerView.Adapter{

    private final static int HEAD = 1;
    private final static int CONTENT = 2;

    private List<FreeDetailBean.DataBean.FreeRuleParametersBean> mList;
    private Context mContext;

    public FreeDetailAdapter(List<FreeDetailBean.DataBean.FreeRuleParametersBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEAD;
        } else {
            return CONTENT;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case HEAD:
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_free_node,viewGroup,false);
                holder = new ViewHolder(view);
                break;
            case CONTENT:
                View view1 = LayoutInflater.from(mContext).inflate(R.layout.item_free_node2,viewGroup,false);
                holder = new ContentViewHolder(view1);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        int type = getItemViewType(position);
        switch (type){
            case HEAD:
                ViewHolder holder = (ViewHolder) viewHolder;

                break;
            case CONTENT:
                ContentViewHolder contentViewHolder = (ContentViewHolder) viewHolder;
                int pos = position - 1;
                if (pos % 2 == 0){
                    contentViewHolder.item_rank.setBackgroundColor(Color.WHITE);
                    contentViewHolder.item_price.setBackgroundColor(Color.WHITE);
                }else {
                    contentViewHolder.item_rank.setBackgroundColor(mContext.getResources().getColor(R.color.color_F5F5F5));
                    contentViewHolder.item_price.setBackgroundColor(mContext.getResources().getColor(R.color.color_F5F5F5));
                }

                if (pos ==  mList.size() - 1 ){
                    contentViewHolder.item_bottomLine.setVisibility(View.VISIBLE);
                }else {
                    contentViewHolder.item_bottomLine.setVisibility(View.GONE);
                }

                contentViewHolder.item_rank.setText(mList.get(pos).getTotal() + "份");
                contentViewHolder.item_price.setText(mList.get(pos).getFreePrice() + "元");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() == 0 ? 0 : mList.size() + 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class ContentViewHolder extends  RecyclerView.ViewHolder{

        private TextView item_rank,item_price;
        private View item_bottomLine;

        public ContentViewHolder(@NonNull View itemView) {
            super(itemView);
            item_rank = itemView.findViewById(R.id.item_rank);
            item_price = itemView.findViewById(R.id.item_price);
            item_bottomLine = itemView.findViewById(R.id.item_bottomLine);
        }
    }
}
