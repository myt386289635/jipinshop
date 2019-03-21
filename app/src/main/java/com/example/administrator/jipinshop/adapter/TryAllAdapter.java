package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.tryout.TryAllActivity;
import com.example.administrator.jipinshop.bean.TryAllBean;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/3/20
 * @Describe
 */
public class TryAllAdapter extends RecyclerView.Adapter<TryAllAdapter.ViewHolder>{

    private Context mContext;
    private List<TryAllBean.DataBean> mList;
    private OnItemClick mOnItemClick;

    public TryAllAdapter(Context context, List<TryAllBean.DataBean> list) {
        mContext = context;
        mList = list;
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View Content1View = LayoutInflater.from(mContext).inflate(R.layout.item_try_content1,viewGroup,false);
        ViewHolder holder = new ViewHolder(Content1View);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewHolder.try_image.getLayoutParams();
        if(position == 0){
            layoutParams.topMargin = (int) mContext.getResources().getDimension(R.dimen.y48);
        }else {
            layoutParams.topMargin = (int) mContext.getResources().getDimension(R.dimen.y24);
        }
        viewHolder.try_image.setLayoutParams(layoutParams);
        viewHolder.itemView.setOnClickListener(v -> {
            if(mOnItemClick != null){
                mOnItemClick.onItemDetailClick(position);
            }
        });
        GlideApp.loderImage(mContext,mList.get(position).getImg(),viewHolder.try_image,0,0);
        viewHolder.try_name.setText(mList.get(position).getName());
        viewHolder.try_num.setText(mList.get(position).getCount() + "");
        BigDecimal bigDecimal = new BigDecimal(mList.get(position).getActualPrice());
        viewHolder.try_price.setText("¥" + bigDecimal.setScale(2,BigDecimal.ROUND_DOWN).toString());
        if(mList.get(position).getStatus() == 2){
            //进行中
            viewHolder.try_button.setVisibility(View.VISIBLE);
            viewHolder.try_button1.setVisibility(View.INVISIBLE);
        } else if (mList.get(position).getStatus() == 3) {
            //试用中
            viewHolder.try_button.setVisibility(View.INVISIBLE);
            viewHolder.try_button1.setVisibility(View.VISIBLE);
            viewHolder.try_button1.setText("试用中");
        }else {
            //已结束
            viewHolder.try_button.setVisibility(View.INVISIBLE);
            viewHolder.try_button1.setVisibility(View.VISIBLE);
            viewHolder.try_button1.setText("已结束");
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView try_image;
        private TextView try_name,try_num,try_price,try_button,try_button1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            try_image = itemView.findViewById(R.id.try_image);
            try_name = itemView.findViewById(R.id.try_name);
            try_num = itemView.findViewById(R.id.try_num);
            try_price = itemView.findViewById(R.id.try_price);
            try_button = itemView.findViewById(R.id.try_button);
            try_button1 = itemView.findViewById(R.id.try_button1);
        }
    }

    public interface OnItemClick{
        void onItemDetailClick(int position);
    }
}
