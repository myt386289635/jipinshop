package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.DailyTaskBean;
import com.example.administrator.jipinshop.util.ToastUtil;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/3/12
 * @Describe
 */
public class ExtendableListViewAdapter extends BaseExpandableListAdapter {

    private List<DailyTaskBean.DataBean> groupList;
    private Context mContext;
    private OnClickItem mOnClickItem;

    public void setOnClickItem(OnClickItem onClickItem) {
        mOnClickItem = onClickItem;
    }

    public ExtendableListViewAdapter(List<DailyTaskBean.DataBean> groupList, Context context) {
        this.groupList = groupList;
        mContext = context;
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groupList.size() != 0 ? 1: 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //分组和子选项是否持有稳定的ID, 就是说底层数据的改变会不会影响到它们
    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.partent_item,parent,false);
            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);
        }else {
            groupViewHolder = (GroupViewHolder)convertView.getTag();
        }
        groupViewHolder.mTitle.setText(groupList.get(groupPosition).getTitle() + "   +" + groupList.get(groupPosition).getPoint());
        if(isExpanded){
            groupViewHolder.group_right.setImageResource(R.mipmap.right_up);
            groupViewHolder.group_divider.setVisibility(View.INVISIBLE);
        }else{
            groupViewHolder.group_right.setImageResource(R.mipmap.right_down);
            groupViewHolder.group_divider.setVisibility(View.VISIBLE);
        }
        switch (groupList.get(groupPosition).getType()){
            case 1://邀请好友
                groupViewHolder.group_sure.setText("立即邀请");
                break;
            case 3://企业认证
            case 2://个人认证
                groupViewHolder.group_sure.setText("立即认证");
                break;
            case 4://点赞
                groupViewHolder.group_sure.setText("立即点赞");
                break;
            case 5://分享
                groupViewHolder.group_sure.setText("立即分享");
                break;
            case 6://阅读文章
                groupViewHolder.group_sure.setText("立即阅读");
                break;
            case 7://评论文章
                groupViewHolder.group_sure.setText("立即评论");
                break;
            case 8://发文奖励
                groupViewHolder.group_sure.setText("立即发文");
                break;
        }
        groupViewHolder.group_sure.setOnClickListener(v -> {
            if (mOnClickItem != null){
                mOnClickItem.onClickItem(groupList.get(groupPosition).getLocation(),groupPosition);
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.child_item,parent,false);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        }else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        childViewHolder.mTextView.setText(groupList.get(groupPosition).getContent());
        return convertView;
    }

    //指定位置上的子元素是否可选中
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public class GroupViewHolder{
        private TextView mTitle;
        private TextView group_sure;
        private ImageView group_right;
        private View group_divider;

        public GroupViewHolder(View view) {
            mTitle = view.findViewById(R.id.group_title);
            group_sure = view.findViewById(R.id.group_sure);
            group_right = view.findViewById(R.id.group_right);
            group_divider = view.findViewById(R.id.group_divider);
        }
    }

    public class ChildViewHolder{
        private TextView mTextView;

        public ChildViewHolder(View view) {
            mTextView = view.findViewById(R.id.expand_child);
        }
    }

    public interface OnClickItem{
        void onClickItem(int location,int position);
    }
}
