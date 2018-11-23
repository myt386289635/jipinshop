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

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.CommentBean;
import com.example.administrator.jipinshop.util.FileManager;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/8/3
 * @Describe
 */
public class CommenListAdapter extends RecyclerView.Adapter<CommenListAdapter.ViewHolder> {

    private List<CommentBean.ListBean> mList;
    private Context mContext;
    private OnItemReply mOnItemReply;
    private OnMoreUp mOnMoreUp;
    private OnGoodItem mOnGoodItem;

    public void setOnGoodItem(OnGoodItem onGoodItem) {
        mOnGoodItem = onGoodItem;
    }

    private List<Integer> sets;//记录每个留言的二级展示条数

    public void setSets(List<Integer> sets) {
        this.sets = sets;
    }

    public void setOnMoreUp(OnMoreUp onMoreUp) {
        mOnMoreUp = onMoreUp;
    }

    public void setOnItemReply(OnItemReply onItemReply) {
        mOnItemReply = onItemReply;
    }

    public CommenListAdapter(List<CommentBean.ListBean> list, Context context) {
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
            holder.mAdapter.setNumber(sets.get(position));
            //点击二级评论更多
            holder.mAdapter.setOnReplyLisenter(pos -> {
                if(mOnItemReply != null){
                    mOnItemReply.onItemTwoReply(pos,holder.mAdapter,position);
                }
            });
            holder.mAdapter.setOnMoreUp(() -> {
                if(mOnMoreUp != null){
                    mOnMoreUp.onUp(position,holder.mAdapter);
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
        if (mList.get(position).getUserShopmember() != null) {
            if(!TextUtils.isEmpty(mList.get(position).getUserShopmember().getUserNickImg())){
                GlideApp.loderCircleImage(mContext,mList.get(position).getUserShopmember().getUserNickImg(),holder.item_image,R.mipmap.rlogo,0);
//                ImageManager.displayCircleImage(mList.get(position).getUserShopmember().getUserNickImg(),holder.item_image,0,R.mipmap.rlogo);
            }else {
                GlideApp.loderCircleImage(mContext, R.drawable.rlogo,holder.item_image,R.mipmap.rlogo,R.drawable.rlogo);
//                ImageManager.displayImage("drawable://" + R.drawable.rlogo,holder.item_image,R.drawable.rlogo,R.drawable.rlogo);
            }
            if (!TextUtils.isEmpty(mList.get(position).getUserShopmember().getUserNickName())){
                holder.item_name.setText(mList.get(position).getUserShopmember().getUserNickName());
            }else {
                holder.item_name.setText(FileManager.editPhone(mList.get(position).getUserShopmember().getUserPhone()));
            }
        }else {
            holder.item_name.setText("游客");
            GlideApp.loderCircleImage(mContext, R.drawable.rlogo,holder.item_image,R.mipmap.rlogo,R.drawable.rlogo);
//            ImageManager.displayImage("drawable://" + R.drawable.rlogo,holder.item_image,R.drawable.rlogo,R.drawable.rlogo);
        }
        if(!TextUtils.isEmpty(mList.get(position).getSnapNum()) && !mList.get(position).getSnapNum().equals("0")){
            holder.item_goodNum.setText(mList.get(position).getSnapNum());
        }else {
            holder.item_goodNum.setText("");
        }
        Drawable drawable;
        if(mList.get(position).isUserSnap() == 1){
            drawable= mContext.getResources().getDrawable(R.mipmap.appreciate_sel);
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
        holder.item_goodNum.setOnClickListener(v -> {
            if(mOnGoodItem != null){
                mOnGoodItem.onGood(mList.get(position).isUserSnap(),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends  RecyclerView.ViewHolder {

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
        void onItemTwoReply(int pos,ShoppingCommon2Adapter mAdapter,int postion);
    }

    public interface OnMoreUp{
        void onUp(int position,ShoppingCommon2Adapter mAdapter);
    }
    public interface OnGoodItem{
        void onGood(int flag,int position);
    }
}
