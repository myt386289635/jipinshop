package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.CommentBean;
import com.example.administrator.jipinshop.util.FileManager;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/8/2
 * @Describe 二级评论
 */
public class ShoppingCommon2Adapter extends RecyclerView.Adapter<ShoppingCommon2Adapter.ViewHolder> {

    //总共的二级评论数量
    private List<CommentBean.ListBean.UserCommentListBean> mList;
    private Context mContext;
    private OnReplyLisenter mOnReplyLisenter;
    private OnMoreUp mOnMoreUp;

    private int number = 0;//展开的评论条数

    public void setNumber(int number) {
        this.number = number;
    }

    public void setOnMoreUp(OnMoreUp onMoreUp) {
        mOnMoreUp = onMoreUp;
    }

    public void setOnReplyLisenter(OnReplyLisenter onReplyLisenter) {
        mOnReplyLisenter = onReplyLisenter;
    }

    public ShoppingCommon2Adapter(Context context) {
        mContext = context;
    }

    public void setList(List<CommentBean.ListBean.UserCommentListBean> list) {
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_common2, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if (position != 0) {
            holder.item_triangle.setVisibility(View.GONE);
        } else {
            holder.item_triangle.setVisibility(View.VISIBLE);
        }

        if (getItemCount() == 1) {
            holder.item_more.setVisibility(View.GONE);
            holder.item_replyLayout.setBackgroundResource(R.drawable.bg_common);
        } else {
            if (position == 0) {
                holder.item_more.setVisibility(View.GONE);
                holder.item_replyLayout.setBackgroundResource(R.drawable.bg_common_one);
            } else if (getItemCount() - 1 == position) {
                holder.item_replyLayout.setBackgroundResource(R.drawable.bg_common_last);
                if (mList.size() <= number) {
                    if(number == mList.size() && number != 2){
                        holder.item_more.setVisibility(View.VISIBLE);
                        holder.item_time.setVisibility(View.GONE);
                        holder.item_down.setVisibility(View.GONE);
                        holder.item_up.setVisibility(View.VISIBLE);
                        holder.item_up.setOnClickListener(v -> {
                            if (mOnMoreUp != null) {
                                mOnMoreUp.onUp();
                            }
                        });
                    }else {
                        holder.item_more.setVisibility(View.GONE);
                    }
                } else {
                    holder.item_more.setVisibility(View.VISIBLE);
                    int num = 0;
                    if (number == 2) {
                        if (number < mList.size() && mList.size() >= 10) {
                            holder.item_time.setVisibility(View.VISIBLE);
                            holder.item_down.setVisibility(View.GONE);
                            holder.item_up.setVisibility(View.GONE);
                            num = number + 8;
                            holder.item_time.setText("共"+mList.size()+"条回复");
                        } else if (number < mList.size() && mList.size() < 10) {
                            holder.item_time.setVisibility(View.VISIBLE);
                            holder.item_down.setVisibility(View.GONE);
                            holder.item_up.setVisibility(View.GONE);
                            num = mList.size();
                            holder.item_time.setText("共" + (mList.size()) + "条回复");
                        }else {
                            holder.item_more.setVisibility(View.GONE);
                        }
                    } else {
                        if (number < mList.size() && mList.size() - number >= 10) {
                            holder.item_time.setVisibility(View.GONE);
                            holder.item_down.setVisibility(View.VISIBLE);
                            holder.item_up.setVisibility(View.GONE);
                            num = number + 10;
                            holder.item_down.setText("查看10条评论");
                        } else if (number < mList.size() && mList.size() - number < 10) {
                            holder.item_time.setVisibility(View.GONE);
                            holder.item_down.setVisibility(View.VISIBLE);
                            holder.item_up.setVisibility(View.GONE);
                            num = mList.size();
                            holder.item_down.setText("查看" + (mList.size() - number) + "条评论");
                        }else {
                            holder.item_more.setVisibility(View.GONE);
                        }
                    }
                    int finalNum = num;
                    holder.item_time.setOnClickListener(v -> {
                        if (mOnReplyLisenter != null) {
                            mOnReplyLisenter.onReply(finalNum);
                        }
                    });
                    holder.item_down.setOnClickListener(v -> {
                        if (mOnReplyLisenter != null) {
                            mOnReplyLisenter.onReply(finalNum);
                        }
                    });
                }
            } else {
                holder.item_more.setVisibility(View.GONE);
                holder.item_replyLayout.setBackgroundResource(R.drawable.bg_common_other);
            }
        }

        if(position < mList.size()){
            holder.item_content.setText(mList.get(position).getContent());
            if (!TextUtils.isEmpty(mList.get(position).getUserShopmember().getUserNickName())){
                holder.item_name.setText(mList.get(position).getUserShopmember().getUserNickName());
            }else {
                holder.item_name.setText(FileManager.editPhone(mList.get(position).getUserShopmember().getUserPhone()));
            }
        }else {
            Log.d("moxiaoting", "数据越界了你知道么？" + position + "，数组大小：" + mList.size());
        }
    }

    @Override
    public int getItemCount() {
        return number;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView item_triangle;
        private RelativeLayout item_replyLayout, item_more;
        private TextView item_name;
        private TextView item_content;
        private TextView item_time;
        private TextView item_down;
        private TextView item_up;

        public ViewHolder(View itemView) {
            super(itemView);
            item_triangle = itemView.findViewById(R.id.item_triangle);
            item_replyLayout = itemView.findViewById(R.id.item_replyLayout);
            item_name = itemView.findViewById(R.id.item_name);
            item_content = itemView.findViewById(R.id.item_content);
            item_more = itemView.findViewById(R.id.item_more);
            item_time = itemView.findViewById(R.id.item_time);
            item_down = itemView.findViewById(R.id.item_down);
            item_up = itemView.findViewById(R.id.item_up);
        }
    }

    public interface OnReplyLisenter {
        void onReply(int pos);//这个是 需要暂时多少条数据
    }

    public interface OnMoreUp{
        void onUp();
    }
}
