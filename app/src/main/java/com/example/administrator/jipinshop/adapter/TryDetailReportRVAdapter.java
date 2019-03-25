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
import com.example.administrator.jipinshop.bean.TryDetailBean;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/3/25
 * @Describe
 */
public class TryDetailReportRVAdapter extends RecyclerView.Adapter<TryDetailReportRVAdapter.ViewHolder>{

    private Context mContext;
    private List<TryDetailBean.DataBean.ReportArticleListBean> mList;
    private OnItemClick mOnItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }


    public TryDetailReportRVAdapter(Context context, List<TryDetailBean.DataBean.ReportArticleListBean> reportListBeans) {
        mContext = context;
        mList = reportListBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_try_content2,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewHolder.try_image.getLayoutParams();
        viewHolder.try_image.setLayoutParams(layoutParams);
        GlideApp.loderImage(mContext,mList.get(position).getImg(),viewHolder.try_image,0,0);
        viewHolder.try_name.setText(mList.get(position).getTitle());
        viewHolder.try_nickName.setText(mList.get(position).getUser().getNickname());
        GlideApp.loderCircleImage(mContext,mList.get(position).getUser().getAvatar(),viewHolder.try_head,0,0);
        viewHolder.try_pv.setText(mList.get(position).getPv() + "阅读");
        viewHolder.itemView.setOnClickListener(v -> {
            if(mOnItemClick != null){
                mOnItemClick.onItemReportClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size() >  7 ? 7 : mList.size();
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
