package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.MallBean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/3/12
 * @Describe
 */
public class SignMallAdapter extends RecyclerView.Adapter<SignMallAdapter.ContentViewHolder> {

    private Context mContext;
    private List<MallBean.DataBean> mList;
    private OnItemListener mOnItemListener;

    public SignMallAdapter(Context context, List<MallBean.DataBean> list) {
        mContext = context;
        mList = list;
    }

    public void setOnItemListener(OnItemListener onItemListener) {
        mOnItemListener = onItemListener;
    }

    @NonNull
    @Override
    public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.item_mall,viewGroup,false);
        ContentViewHolder holder  = new ContentViewHolder(contentView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContentViewHolder contentViewHolder, int pos) {
        if( pos % 2 == 0){
            //左边
            GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) contentViewHolder.item_container.getLayoutParams();
            layoutParams.leftMargin = mContext.getResources().getDimensionPixelSize(R.dimen.x14);
            layoutParams.rightMargin = mContext.getResources().getDimensionPixelSize(R.dimen.x7);

        }else {
            //右边
            GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) contentViewHolder.item_container.getLayoutParams();
            layoutParams.leftMargin = mContext.getResources().getDimensionPixelSize(R.dimen.x7);
            layoutParams.rightMargin = mContext.getResources().getDimensionPixelSize(R.dimen.x14);
        }

        contentViewHolder.itemView.setOnClickListener(v -> {
            if(mOnItemListener != null){
                mOnItemListener.onItemIntegralDetail(pos);
            }
        });

        if (mList.get(pos).getType() == 1){
            contentViewHolder.item_tag.setVisibility(View.VISIBLE);
        }else {
            contentViewHolder.item_tag.setVisibility(View.GONE);
        }

        Glide.with(mContext).load(mList.get(pos).getImg()).into(contentViewHolder.item_image);
        contentViewHolder.item_code.setText(mList.get(pos).getExchangePoint() + "极币");
        contentViewHolder.item_name.setText(mList.get(pos).getGoodsName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class ContentViewHolder extends  RecyclerView.ViewHolder{

        private RelativeLayout item_container;
        private ImageView item_image,item_tag;
        private TextView item_name;
        private TextView item_code;

        public ContentViewHolder(View itemView) {
            super(itemView);
            item_container = itemView.findViewById(R.id.item_container);
            item_image = itemView.findViewById(R.id.item_image);
            item_name = itemView.findViewById(R.id.item_name);
            item_code = itemView.findViewById(R.id.item_code);
            item_tag = itemView.findViewById(R.id.item_tag);
        }
    }

    public interface OnItemListener{
        void onItemIntegralDetail(int position);
    }
}
