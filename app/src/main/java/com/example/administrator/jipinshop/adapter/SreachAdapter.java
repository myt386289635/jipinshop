package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/8/13
 * @Describe
 */
public class SreachAdapter extends BaseAdapter{

    private List<String> mList;
    private Context mContent;
    private OnItem mOnItem;

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    public SreachAdapter(List<String> list, Context content) {
        mList = list;
        mContent = content;
    }

    @Override
    public int getCount() {
        return 10;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(mContent).inflate(R.layout.item_sreach,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItem != null) {
                    mOnItem.onItem(position);
                }
            }
        });

        return convertView;
    }

    class ViewHolder {
        private TextView item_name;
        public ViewHolder(View view) {
            item_name  = view.findViewById(R.id.item_name);
        }
    }

    public interface OnItem {
        void onItem(int pos);
    }
}
