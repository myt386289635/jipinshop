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
import com.example.administrator.jipinshop.bean.TryDetailBean;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/3/25
 * @Describe
 */
public class TryDetailApplyRVAdapter extends RecyclerView.Adapter<TryDetailApplyRVAdapter.ViewHolder>{

    private Context mContext;
    private List<TryDetailBean.DataBean.ApplyUserListBean> mList;

    public TryDetailApplyRVAdapter(Context context, List<TryDetailBean.DataBean.ApplyUserListBean> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_try_apply,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        GlideApp.loderCircleImage(mContext,mList.get(i).getUserAvatar(),viewHolder.item_image,0,0);
        if(i == 0){
            viewHolder.item_sort.setVisibility(View.GONE);
            viewHolder.item_topSort.setVisibility(View.VISIBLE);
            viewHolder.item_topSort.setBackgroundResource(R.drawable.bg_sort1);
            viewHolder.item_topSort.setText("1");
        }else if (i == 1){
            viewHolder.item_sort.setVisibility(View.GONE);
            viewHolder.item_topSort.setVisibility(View.VISIBLE);
            viewHolder.item_topSort.setBackgroundResource(R.drawable.bg_sort2);
            viewHolder.item_topSort.setText("2");
        }else if (i == 2){
            viewHolder.item_sort.setVisibility(View.GONE);
            viewHolder.item_topSort.setVisibility(View.VISIBLE);
            viewHolder.item_topSort.setBackgroundResource(R.drawable.bg_sort3);
            viewHolder.item_topSort.setText("3");
        }else {
            viewHolder.item_sort.setVisibility(View.VISIBLE);
            viewHolder.item_topSort.setVisibility(View.INVISIBLE);
            viewHolder.item_sort.setText( (i + 1 )+ "");
        }
        viewHolder.item_name.setText(mList.get(i).getUserNickname());
        viewHolder.item_goodNum.setText("拉赞数" + mList.get(i).getVoteCount());
    }

    @Override
    public int getItemCount() {
        return mList.size() > 7 ? 7 : mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView item_topSort, item_sort,item_name,item_goodNum;
        private ImageView item_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_topSort = itemView.findViewById(R.id.item_topSort);
            item_sort = itemView.findViewById(R.id.item_sort);
            item_name = itemView.findViewById(R.id.item_name);
            item_goodNum = itemView.findViewById(R.id.item_goodNum);
            item_image = itemView.findViewById(R.id.item_image);
        }
    }
}
