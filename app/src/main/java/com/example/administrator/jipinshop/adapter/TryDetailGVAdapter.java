package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
public class TryDetailGVAdapter extends BaseAdapter {

    private Context mContext;
    private List<TryDetailBean.DataBean.PassedUserListBean> mList;

    public TryDetailGVAdapter(Context context, List<TryDetailBean.DataBean.PassedUserListBean> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size() > 6 ? 6 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

       ViewHolder holder;
       if(convertView == null){
           convertView  = LayoutInflater.from(mContext).inflate(R.layout.item_try_passlist,parent,false);
           holder = new ViewHolder(convertView);
           convertView.setTag(holder);
       }else {
           holder = (ViewHolder) convertView.getTag();
       }

       GlideApp.loderCircleImage(mContext,mList.get(position).getUserAvatar(),holder.mImageView,0,0);
       holder.mTextView.setText(mList.get(position).getUserNickname());

        return convertView;
    }

    class ViewHolder{

        private ImageView mImageView;
        private TextView mTextView;

        public ViewHolder(View view) {

            mImageView = view.findViewById(R.id.item_image);
            mTextView  = view.findViewById(R.id.item_name);
        }
    }
}
