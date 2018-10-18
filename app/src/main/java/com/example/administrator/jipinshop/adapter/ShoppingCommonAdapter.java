package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.jipinshop.MyApplication;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.view.glide.imageloder.ImageManager;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/8/2
 * @Describe
 */
public class ShoppingCommonAdapter extends RecyclerView.Adapter<ShoppingCommonAdapter.ViewHolder> {

    private List<String> mList;
    private Context mContext;
    private OnItemReply mOnItemReply;

    public void setOnItemReply(OnItemReply onItemReply) {
        mOnItemReply = onItemReply;
    }

    public ShoppingCommonAdapter(List<String> list, Context context) {
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

//        if(position != 0){
//            holder.recycler_view.setVisibility(View.VISIBLE);
//            holder.mAdapter.setList(mList);
//            //二级评论的回复
//            holder.mAdapter.setOnReplyLisenter(pos -> {
//                if(mOnItemReply != null){
//                    mOnItemReply.onItemTwoReply(pos);
//                }
//            });
//            holder.recycler_view.setAdapter(holder.mAdapter);
//        }

        holder.item_reply.setOnClickListener(view -> {
            if(mOnItemReply != null){
                mOnItemReply.onItemReply(position,holder.item_reply);
            }
        });
        ImageManager.displayCircleImage(MyApplication.imag,holder.item_image,0,0);
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

//            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext) {
//                @Override
//                public boolean canScrollVertically() {
//                    // 直接禁止垂直滑动
//                    return false;
//                }
//            };
//            recycler_view.setLayoutManager(layoutManager);
//            mAdapter = new ShoppingCommon2Adapter(mContext);
        }
    }

    public interface OnItemReply{
        void onItemReply(int pos,TextView textView);
        void onItemTwoReply(int pos);
    }

}
