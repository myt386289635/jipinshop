package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.FollowBean;
import com.example.administrator.jipinshop.view.glide.imageloder.ImageManager;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/8/7
 * @Describe
 */
public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.ViewHolder> {

    private List<FollowBean.ListBean> mList;
    private Context mContext;
    private OnItemClick mOnItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

    public FollowAdapter(List<FollowBean.ListBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_follow,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(v -> {
            if(mOnItemClick != null){
                mOnItemClick.onItem(position);
            }
        });
        ImageManager.displayCircleImage(mList.get(position).getFrom_thumb_img(),holder.item_image,R.mipmap.rlogo,R.mipmap.rlogo);
        holder.item_name.setText(mList.get(position).getFrom_nickname());

        holder.item_attention.setOnClickListener(view -> {
            //取消关注
            if (mOnItemClick != null) {
                mOnItemClick.onItemDecAtten(position, holder.item_attention);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends  RecyclerView.ViewHolder{

        private ImageView item_image;
        private TextView item_name;
        private TextView item_attention;

        public ViewHolder(View itemView) {
            super(itemView);
            item_image = itemView.findViewById(R.id.item_image);
            item_name = itemView.findViewById(R.id.item_name);
            item_attention = itemView.findViewById(R.id.item_attention);
        }
    }

   public interface OnItemClick{
        void onItem(int pos);

//        void onItemAtten(int pos,TextView textView);//添加关注

        void onItemDecAtten(int pos, TextView textView);//取消关注
    }

}
