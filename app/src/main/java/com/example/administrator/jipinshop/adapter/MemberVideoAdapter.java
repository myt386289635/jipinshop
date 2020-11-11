package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.MemberNewBean;
import com.example.administrator.jipinshop.databinding.ItemVideoMemberBinding;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/9/10
 * @Describe
 */
public class MemberVideoAdapter extends RecyclerView.Adapter<MemberVideoAdapter.ViewHolder> {

    private List<MemberNewBean.DataBean.LevelDetail7Bean.ListBeanXXX> mList;
    private Context mContext;
    private OnItem mOnItem;
    private int type = 1;//1是会员月卡  2是会员里的吃喝玩乐 3是fragment的吃喝玩乐

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    public MemberVideoAdapter(List<MemberNewBean.DataBean.LevelDetail7Bean.ListBeanXXX> list, Context context , int type) {
        mList = list;
        mContext = context;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemVideoMemberBinding view  = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_video_member,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.mBinding.setBean(mList.get(position));
        viewHolder.mBinding.executePendingBindings();
        viewHolder.itemView.setOnClickListener(v -> {
            if (mOnItem != null)
                mOnItem.onItem(position);
        });
        if (type == 1){//会员月卡
            viewHolder.mBinding.itemName.setVisibility(View.GONE);
            viewHolder.mBinding.itemTag.setText(mList.get(position).getTitle());
        }else if (type == 3){//fragment的吃喝玩乐
            viewHolder.mBinding.itemName.setVisibility(View.VISIBLE);
            viewHolder.mBinding.itemTag.setText(mList.get(position).getSubTitle());
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewHolder.mBinding.itemImage.getLayoutParams();
            layoutParams.topMargin = 0;
            viewHolder.mBinding.itemImage.setLayoutParams(layoutParams);
            RelativeLayout.LayoutParams itemTagLp = (RelativeLayout.LayoutParams) viewHolder.mBinding.itemTag.getLayoutParams();
            itemTagLp.bottomMargin = (int) mContext.getResources().getDimension(R.dimen.y30);
            viewHolder.mBinding.itemTag.setLayoutParams(itemTagLp);
        }else {
            viewHolder.mBinding.itemName.setVisibility(View.VISIBLE);
            viewHolder.mBinding.itemTag.setText(mList.get(position).getSubTitle());
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ItemVideoMemberBinding mBinding;

        public ViewHolder(@NonNull ItemVideoMemberBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }
    }

    public interface OnItem{
        void onItem(int position);
    }
}
