package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.SystemMessageBean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/8/4
 * @Describe
 */
public class SystemMessageAdapter extends RecyclerView.Adapter<SystemMessageAdapter.ViewHolder>{

    private Context mContext;
    private List<SystemMessageBean.DataBean> mList;
    private OnItem mOnItem;

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    public SystemMessageAdapter(Context context, List<SystemMessageBean.DataBean> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_system,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.item_content.setText(mList.get(position).getContent());
        holder.item_name.setText(mList.get(position).getTitle());
        holder.item_time.setText(mList.get(position).getCreateTime().split(" ")[0].replace("-","."));
        holder.itemView.setOnClickListener(v -> {
            mOnItem.onItemClick(position);
        });
        holder.itemView.setOnLongClickListener(v -> {
            mOnItem.onItemLongClick(position);
            return false;
        });
        if (mList.get(position).getStatus() == 0) {
            //未读
            holder.item_unred.setVisibility(View.VISIBLE);
        }else {
            holder.item_unred.setVisibility(View.GONE);
        }
        if (mList.get(position).getType().equals("0")){
            holder.item_more.setVisibility(View.GONE);
        }else {
            holder.item_more.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView item_name;
        private TextView item_time;
        private TextView item_content;
        private TextView item_more;
        private ImageView item_unred;

        public ViewHolder(View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.item_name);
            item_time = itemView.findViewById(R.id.item_time);
            item_content = itemView.findViewById(R.id.item_content);
            item_more = itemView.findViewById(R.id.item_more);
            item_unred = itemView.findViewById(R.id.item_unred);
        }
    }

    public interface OnItem{
        void onItemClick(int position);
        void onItemLongClick(int position);
    }
}
