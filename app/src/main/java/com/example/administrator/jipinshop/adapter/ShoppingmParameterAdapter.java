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
 * @Describe 产品参数
 */
public class ShoppingmParameterAdapter extends BaseAdapter {

    private List<String> mList;
    private Context mContext;

    public ShoppingmParameterAdapter(List<String> list, Context context) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_parameter,viewGroup,false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }


        return view;
    }

    class ViewHolder{

        private TextView item_name;
        private TextView item_content;

        public ViewHolder(View view) {

            item_name = view.findViewById(R.id.item_name);
            item_content = view.findViewById(R.id.item_content);
        }
    }
}
