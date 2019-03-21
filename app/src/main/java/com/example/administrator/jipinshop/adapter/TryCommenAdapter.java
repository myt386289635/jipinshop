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
import com.example.administrator.jipinshop.bean.TryReportBean;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/3/20
 * @Describe
 */
public class TryCommenAdapter extends RecyclerView.Adapter<TryCommenAdapter.ViewHolder>{

    private Context mContext;
    private List<TryReportBean.DataBean> mList;
    private OnItemClick mOnItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

    public TryCommenAdapter(Context context, List<TryReportBean.DataBean> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View Content2View = LayoutInflater.from(mContext).inflate(R.layout.item_try_content2,viewGroup,false);
        ViewHolder holder = new ViewHolder(Content2View);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder content2ViewHolder, int position) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) content2ViewHolder.try_image.getLayoutParams();
        if(position == 0){
            layoutParams.topMargin = (int) mContext.getResources().getDimension(R.dimen.y48);
        }else {
            layoutParams.topMargin = 0;
        }
        content2ViewHolder.try_image.setLayoutParams(layoutParams);
        GlideApp.loderImage(mContext,mList.get(position).getImg(),content2ViewHolder.try_image,0,0);
        content2ViewHolder.try_name.setText(mList.get(position).getTitle());
        content2ViewHolder.try_nickName.setText(mList.get(position).getUser().getNickname());
        GlideApp.loderCircleImage(mContext,mList.get(position).getUser().getAvatar(),content2ViewHolder.try_head,0,0);
        content2ViewHolder.try_pv.setText(mList.get(position).getPv() + "阅读");
        content2ViewHolder.itemView.setOnClickListener(v -> {
            if(mOnItemClick != null){
                mOnItemClick.onItemReportClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView try_image,try_head;
        private TextView try_name,try_nickName,try_pv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            try_image = itemView.findViewById(R.id.try_image);
            try_head = itemView.findViewById(R.id.try_head);
            try_name = itemView.findViewById(R.id.try_name);
            try_nickName = itemView.findViewById(R.id.try_nickName);
            try_pv = itemView.findViewById(R.id.try_pv);
        }
    }

    public interface OnItemClick{
        void onItemReportClick(int position);
    }
}
