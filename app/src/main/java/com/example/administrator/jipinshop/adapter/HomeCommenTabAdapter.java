package com.example.administrator.jipinshop.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.ChildrenTabBean;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/1/5
 * @Describe
 */
public class HomeCommenTabAdapter extends BaseAdapter{

    private List<ChildrenTabBean> mChildrenBeans;
    private Context mContext;
    private OnItem mOnItem;

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    public HomeCommenTabAdapter(List<ChildrenTabBean> list, Context context) {
        mChildrenBeans = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        if(mChildrenBeans.size() < 10){
            return mChildrenBeans.size();
        }else {
            return 10;
        }
    }

    @Override
    public Object getItem(int position) {
        return mChildrenBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.children_tab, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(mChildrenBeans.get(position).getTag()){
            holder.mItemName.setTextColor(mContext.getResources().getColor(R.color.color_E31436));
        }else {
            holder.mItemName.setTextColor(mContext.getResources().getColor(R.color.color_ACACAC));
        }
        if (!GlideApp.isDestroy((Activity) mContext)){//解决gridview未创建时因计算item高度而造成的图片加载崩溃问题
            GlideApp.loderImage(mContext,mChildrenBeans.get(position).getImg(),holder.mImageView,0,0);
        }
        holder.mItemName.setText(mChildrenBeans.get(position).getName());
        convertView.setOnClickListener(v -> {
            if(mOnItem != null){
                mOnItem.onItem(position);
            }
        });
        return convertView;
    }

    public class ViewHolder {

        private TextView mItemName;
        private ImageView mImageView;

        ViewHolder(View view) {
            mItemName = view.findViewById(R.id.item_tabText);
            mImageView = view.findViewById(R.id.item_tabimg);
        }
    }

    public interface OnItem{
        void onItem(int pos);
    }
}
