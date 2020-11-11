package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.MemberNewBean;
import com.example.administrator.jipinshop.bean.PlayBean;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.view.itemDecoration.SectionedBaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/11/10
 * @Describe
 */
public class PlayRightAdapter extends SectionedBaseAdapter {

    private List<PlayBean.DataBean> mList;
    private Context mContext;

    public PlayRightAdapter(List<PlayBean.DataBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public Object getItem(int section, int position) {
        return null;
    }

    @Override
    public long getItemId(int section, int position) {
        return 0;
    }

    //栏目的个数
    @Override
    public int getSectionCount() {
        return mList.size();
    }

    //内容的个数
    @Override
    public int getCountForSection(int section) {
        return 1;
    }

    //内容布局
    @Override
    public View getItemView(int section, int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_play_right,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.list.clear();
        holder.list.addAll(mList.get(section).getList());
        holder.adapter.setOnItem(pos -> {
            ToastUtil.show("点击了" + pos);
        });
        holder.adapter.notifyDataSetChanged();
        return convertView;
    }

    //栏目布局
    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        ContentViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_play_title,parent,false);
            holder = new ContentViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ContentViewHolder) convertView.getTag();
        }
        holder.item_title.setText(mList.get(section).getCategoryTitle());
        return convertView;
    }

    public class ViewHolder {

        private RecyclerView recyclerView;
        private List<MemberNewBean.DataBean.LevelDetail7Bean.ListBeanXXX> list;
        private MemberVideoAdapter adapter;

        ViewHolder(View view) {
            recyclerView = view.findViewById(R.id.item_rv);
            recyclerView.setLayoutManager(new GridLayoutManager(mContext,3));
            list = new ArrayList<>();
            adapter = new MemberVideoAdapter(list,mContext,3);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setAdapter(adapter);
        }
    }

    public class ContentViewHolder {

        private TextView item_title;

        public ContentViewHolder(View view) {
            item_title = view.findViewById(R.id.item_title);
        }
    }
}
