package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;

import java.util.List;

import javax.xml.transform.Templates;

/**
 * @author 莫小婷
 * @create 2018/8/2
 * @Describe 二级评论
 */
public class ShoppingCommon2Adapter extends RecyclerView.Adapter<ShoppingCommon2Adapter.ViewHolder>{

    private List<String> mList;
    private Context mContext;
    private OnReplyLisenter mOnReplyLisenter;

    public void setOnReplyLisenter(OnReplyLisenter onReplyLisenter) {
        mOnReplyLisenter = onReplyLisenter;
    }

    public ShoppingCommon2Adapter(Context context) {
        mContext = context;
    }

    public void setList(List<String> list) {
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_common2,parent,false);
        ViewHolder  holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if(position != 0){
            holder.item_triangle.setVisibility(View.GONE);
        }else {
            holder.item_triangle.setVisibility(View.VISIBLE);
        }

        if(getItemCount() == 1){
            holder.item_replyLayout.setBackgroundResource(R.drawable.bg_common);
        } else {
           if(position == 0){
               holder.item_replyLayout.setBackgroundResource(R.drawable.bg_common_one);
           }else if(getItemCount() - 1 == position){
               holder.item_replyLayout.setBackgroundResource(R.drawable.bg_common_last);
           }else {
               holder.item_replyLayout.setBackgroundResource(R.drawable.bg_common_other);
           }
        }

        holder.item_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnReplyLisenter != null){
                    mOnReplyLisenter.onReply(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView item_triangle;
        private RelativeLayout item_replyLayout;
        private TextView item_head;
        private TextView item_name;
        private TextView item_content;
        private TextView item_time;
        private TextView item_reply;

        public ViewHolder(View itemView) {
            super(itemView);
            item_triangle = itemView.findViewById(R.id.item_triangle);
            item_replyLayout = itemView.findViewById(R.id.item_replyLayout);
            item_head = itemView.findViewById(R.id.item_head);
            item_name = itemView.findViewById(R.id.item_name);
            item_content = itemView.findViewById(R.id.item_content);
            item_time = itemView.findViewById(R.id.item_time);
            item_reply = itemView.findViewById(R.id.item_reply);
        }
    }

    public interface OnReplyLisenter{
        void onReply(int pos);
    }
}
