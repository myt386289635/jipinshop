package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.FindDetailBean;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/4/3
 * @Describe
 */
public class RelatedArticleAdapter extends RecyclerView.Adapter<RelatedArticleAdapter.ViewHolder> {

    private List<FindDetailBean.DataBean.RelatedArticleListBean> mList;
    private Context mContext;
    private OnClickRelatedItem mOnClickRelatedItem;

    public void setOnClickRelatedItem(OnClickRelatedItem onClickRelatedItem) {
        mOnClickRelatedItem = onClickRelatedItem;
    }

    public RelatedArticleAdapter(List<FindDetailBean.DataBean.RelatedArticleListBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_related,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.item_name.setText(mList.get(i).getTitle());
        GlideApp.loderImage(mContext,mList.get(i).getImg(),viewHolder.item_image,0,0);
        viewHolder.item_time.setText(mList.get(i).getCreateTime());

        viewHolder.itemView.setOnClickListener(v -> {
            if (mOnClickRelatedItem != null){
                mOnClickRelatedItem.onClickItem(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView item_image;
        private TextView item_name;
        private TextView item_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_image = itemView.findViewById(R.id.item_image);
            item_name = itemView.findViewById(R.id.item_name);
            item_time = itemView.findViewById(R.id.item_time);
        }
    }

    public interface OnClickRelatedItem {
        void onClickItem(int position);
    }
}
