package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.FindListBean;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import java.util.List;

public class CommonFindAdapter extends RecyclerView.Adapter<CommonFindAdapter.ViewHolder> {

    private List<FindListBean.DataBean> mList;
    private Context mContext;

    public CommonFindAdapter(List<FindListBean.DataBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_sreacharticle, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.title.setText(mList.get(i).getTitle());
        GlideApp.loderRoundImage(mContext,mList.get(i).getImg(),viewHolder.item_image);
        GlideApp.loderCircleImage(mContext,mList.get(i).getUser().getAvatar(),viewHolder.item_head,R.mipmap.rlogo,0);
        viewHolder.item_name.setText(mList.get(i).getUser().getNickname());
        viewHolder.item_pv.setText(mList.get(i).getPv() + "阅读");
        if(mList.get(i).getUser().getAuthentication() == 0){
            //普通用户
            viewHolder.item_grade.setVisibility(View.GONE);
        }else if(mList.get(i).getUser().getAuthentication() == 1){
            //个人认证
            viewHolder.item_grade.setVisibility(View.VISIBLE);
            viewHolder.item_grade.setImageResource(R.mipmap.grade_peroson);
        }else {
            //企业认证
            viewHolder.item_grade.setVisibility(View.VISIBLE);
            viewHolder.item_grade.setImageResource(R.mipmap.grade_peroson);
        }
        viewHolder.itemView.setOnClickListener(v -> {
            Toast.makeText(mContext, "点击了", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return mList.size() == 0 ? 0 : mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView item_image;
        private ImageView item_head;
        private ImageView item_grade;
        private TextView item_name;
        private TextView item_pv;
        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_title);
            item_image = itemView.findViewById(R.id.item_image);
            item_head = itemView.findViewById(R.id.item_head);
            item_grade = itemView.findViewById(R.id.item_grade);
            item_name = itemView.findViewById(R.id.item_name);
            item_pv = itemView.findViewById(R.id.item_pv);
        }
    }
}
