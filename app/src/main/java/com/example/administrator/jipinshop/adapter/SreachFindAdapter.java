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
import com.example.administrator.jipinshop.bean.SreachResultArticlesBean;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/1/9
 * @Describe 评测的
 */
public class SreachFindAdapter extends RecyclerView.Adapter<SreachFindAdapter.ViewHolder> {

    private List<SreachResultArticlesBean.DataBean> mList;
    private Context mContext;
    private OnItem mOnItem;

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    public SreachFindAdapter(List<SreachResultArticlesBean.DataBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_sreachfind,viewGroup,false);
        ViewHolder holder= new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.title.setText(mList.get(i).getTitle());
        GlideApp.loderRoundImage(mContext,mList.get(i).getImg(),viewHolder.item_image);
        GlideApp.loderCircleImage(mContext,mList.get(i).getUser().getAvatar(),viewHolder.item_head,R.mipmap.rlogo,0);
        viewHolder.item_name.setText(mList.get(i).getUser().getNickname());
        viewHolder.item_pv.setText(mList.get(i).getPv() + "阅读");
        if(mList.get(i).getUser().getAuthentication().equals("0")){
            //普通用户
            viewHolder.item_grade.setVisibility(View.GONE);
        }else if(mList.get(i).getUser().getAuthentication().equals("1")){
            //个人认证
            viewHolder.item_grade.setVisibility(View.VISIBLE);
            viewHolder.item_grade.setImageResource(R.mipmap.grade_peroson);
        }else {
            //企业认证
            viewHolder.item_grade.setVisibility(View.VISIBLE);
            viewHolder.item_grade.setImageResource(R.mipmap.grade_enterprise);
        }
        viewHolder.itemView.setOnClickListener(v -> {
            if(mOnItem != null){
                mOnItem.onItem(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private ImageView item_image;
        private ImageView item_head;
        private ImageView item_grade;
        private TextView item_name;
        private TextView item_pv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_title);
            item_image = itemView.findViewById(R.id.item_image);
            item_head = itemView.findViewById(R.id.item_head);
            item_grade = itemView.findViewById(R.id.item_grade);
            item_name = itemView.findViewById(R.id.item_name);
            item_pv = itemView.findViewById(R.id.item_pv);
        }
    }

    public interface OnItem {
        void onItem(int pos);
    }

}
