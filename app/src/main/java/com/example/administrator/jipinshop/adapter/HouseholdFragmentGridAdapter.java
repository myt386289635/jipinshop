package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.HealthFragmentGridBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HouseholdFragmentGridAdapter  extends BaseAdapter{

    private List<HealthFragmentGridBean> mList;
    private Context mContext;
    private OnItem mOnItem;

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    public HouseholdFragmentGridAdapter(List<HealthFragmentGridBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.size() == 0 ? null : mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_health_grid, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        holder.mItemName.setText(mList.get(i).getName());
        if(mList.get(i).getTag()){
            holder.mItemName.setTextColor(mContext.getResources().getColor(R.color.color_E31436));
        }else {
            holder.mItemName.setTextColor(mContext.getResources().getColor(R.color.color_ACACAC));
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnItem != null){
                    mOnItem.onItem(i);
                }
            }
        });

        return view;
    }

    class ViewHolder {

        private TextView mItemName;

        ViewHolder(View view) {
            mItemName = view.findViewById(R.id.item_name);
        }
    }

    public interface OnItem{
        void onItem(int pos);
    }
}
