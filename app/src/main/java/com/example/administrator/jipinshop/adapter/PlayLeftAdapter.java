package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.PlayBean;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/11/10
 * @Describe
 */
public class PlayLeftAdapter extends BaseAdapter {

    private List<PlayBean.DataBean> mList;
    private Context mContext;
    private int select = 0;//选中的位置 默认是第一个
    private OnItem mOnItem;

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    public PlayLeftAdapter(List<PlayBean.DataBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    public void setSelect(int select) {
        this.select = select;
    }

    @Override
    public int getCount() {
        return mList.size();
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
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_play_left,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position == select){
            holder.item_tag.setVisibility(View.VISIBLE);
            holder.item_text.setTextColor(mContext.getResources().getColor(R.color.color_E25838));
            holder.item_text.getPaint().setFakeBoldText(true);
            holder.item_container.setBackgroundColor(mContext.getResources().getColor(R.color.color_white));
            holder.item_text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);
        }else {
            holder.item_tag.setVisibility(View.INVISIBLE);
            holder.item_text.setTextColor(mContext.getResources().getColor(R.color.color_202020));
            holder.item_text.getPaint().setFakeBoldText(false);
            holder.item_container.setBackgroundColor(mContext.getResources().getColor(R.color.color_F5F5F5));
            holder.item_text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f);
        }
        holder.item_text.setText(mList.get(position).getCategoryTitle());
        holder.item_container.setOnClickListener(v -> {
            if (mOnItem != null){
                mOnItem.onItemSelect(position);
            }
        });
        return convertView;
    }

    public class ViewHolder {

        private RelativeLayout item_container;
        private View item_tag;
        private TextView item_text;

        ViewHolder(View view) {
            item_container = view.findViewById(R.id.item_container);
            item_tag = view.findViewById(R.id.item_tag);
            item_text = view.findViewById(R.id.item_text);
        }
    }

    public interface OnItem{
        void onItemSelect(int position);
    }
}
