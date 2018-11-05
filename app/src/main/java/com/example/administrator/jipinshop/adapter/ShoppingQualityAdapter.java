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
 * @create 2018/8/2
 * @Describe 品质保证
 */
public class ShoppingQualityAdapter extends BaseAdapter{

    private List<String> mList;
    private Context mContext;

    public ShoppingQualityAdapter(List<String> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size() == 0 ? 0 : mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_quality,viewGroup,false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        if(i == 0){
            holder.item_line2.setVisibility(View.INVISIBLE);
        }

        if(i == mList.size() - 1){
            holder.item_line1.setVisibility(View.GONE);
        }

        holder.item_name.setText(mList.get(i));

        return view;
    }

    class ViewHolder{

        private TextView item_name;
        private View item_line1;
        private View item_line2;

        public ViewHolder(View view) {
            item_name = view.findViewById(R.id.item_name);
            item_line1 = view.findViewById(R.id.item_line1);
            item_line2 = view.findViewById(R.id.item_line2);
        }
    }
}
