package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.jipinshop.MyApplication;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.CommentBean;
import com.example.administrator.jipinshop.view.glide.imageloder.ImageManager;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/8/2
 * @Describe
 */
public class ShoppingCommonAdapter extends RecyclerView.Adapter<ShoppingCommonAdapter.ViewHolder> {

    private List<CommentBean.ListBean> mList;
    private Context mContext;
    private OnItemReply mOnItemReply;

    public void setOnItemReply(OnItemReply onItemReply) {
        mOnItemReply = onItemReply;
    }

    public ShoppingCommonAdapter(List<CommentBean.ListBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view  = LayoutInflater.from(mContext).inflate(R.layout.item_common,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if(mList.get(position).getUserCommentList() != null && mList.get(position).getUserCommentList().size() != 0){
            holder.recycler_view.setVisibility(View.VISIBLE);
            holder.mAdapter.setList(mList.get(position).getUserCommentList());
            if(mList.get(position).getUserCommentList().size() > 2){
                holder.mAdapter.setNumber(2);
            }else {
                holder.mAdapter.setNumber(mList.get(position).getUserCommentList().size());
            }
            //二级评论的更多
            holder.mAdapter.setOnReplyLisenter(pos -> {
                if(mOnItemReply != null){
                    mOnItemReply.onItemTwoReply(position);
                }
            });
            holder.recycler_view.setAdapter(holder.mAdapter);
        }else {
            holder.recycler_view.setVisibility(View.GONE);
        }

        holder.item_reply.setOnClickListener(view -> {
            if(mOnItemReply != null){
                mOnItemReply.onItemReply(position,holder.item_reply);
            }
        });
        if(!TextUtils.isEmpty(mList.get(position).getFromImg())){
            ImageManager.displayCircleImage(mList.get(position).getFromImg(),holder.item_image,0,R.mipmap.rlogo);
        }else {
            ImageManager.displayImage("drawable://" + R.drawable.rlogo,holder.item_image,R.drawable.rlogo,R.drawable.rlogo);
        }

        holder.item_name.setText(mList.get(position).getFromNickname());
        if(!TextUtils.isEmpty(mList.get(position).getSnapNum()) && !mList.get(position).getSnapNum().equals("0")){
            holder.item_goodNum.setText(mList.get(position).getSnapNum());
        }
        Drawable drawable;
        if(mList.get(position).isUserSnap()){
            drawable= mContext.getResources().getDrawable(R.mipmap.appreciate_big);
        }else {
            drawable= mContext.getResources().getDrawable(R.mipmap.appreciate_nor);
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        holder.item_goodNum.setCompoundDrawables(drawable,null,null,null);
        holder.item_content.setText(mList.get(position).getContent());
        if(!TextUtils.isEmpty(mList.get(position).getShowTime())){
            holder.item_time.setText(mList.get(position).getShowTime());
        }else {
            holder.item_time.setText(mList.get(position).getCreateTime().split(" ")[0]);
        }

    }

    @Override
    public int getItemCount() {
        if(mList == null || mList.size() == 0){
            return 0;
        }else if(mList.size() >= 3){
            return 3;
        }else {
            return mList.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView item_image;
        private TextView item_name;
        private TextView item_goodNum;
        private TextView item_content;
        private TextView item_time,item_reply;
        private RecyclerView recycler_view;
        private ShoppingCommon2Adapter mAdapter;

        public ViewHolder(View itemView) {
            super(itemView);
            item_image = itemView.findViewById(R.id.item_image);
            item_name = itemView.findViewById(R.id.item_name);
            item_goodNum = itemView.findViewById(R.id.item_goodNum);
            item_content = itemView.findViewById(R.id.item_content);
            item_time = itemView.findViewById(R.id.item_time);
            recycler_view = itemView.findViewById(R.id.recycler_view);
            item_reply  = itemView.findViewById(R.id.item_reply);

            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext) {
                @Override
                public boolean canScrollVertically() {
                    // 直接禁止垂直滑动
                    return false;
                }
            };
            recycler_view.setLayoutManager(layoutManager);
            mAdapter = new ShoppingCommon2Adapter(mContext);
        }
    }

    public interface OnItemReply{
        void onItemReply(int pos,TextView textView);
        void onItemTwoReply(int pos);
    }

}
