package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.MallBean;
import com.example.administrator.jipinshop.databinding.ItemMallHotBinding;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/3/12
 * @Describe 赚钱——热门兑换
 */
public class SignMallAdapter extends RecyclerView.Adapter<SignMallAdapter.ContentViewHolder> {

    private Context mContext;
    private List<MallBean.DataBean> mList;
    private OnItemListener mOnItemListener;

    public SignMallAdapter(Context context, List<MallBean.DataBean> list) {
        mContext = context;
        mList = list;
    }

    public void setOnItemListener(OnItemListener onItemListener) {
        mOnItemListener = onItemListener;
    }

    @NonNull
    @Override
    public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemMallHotBinding contentView = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_mall_hot,viewGroup,false);
        ContentViewHolder holder  = new ContentViewHolder(contentView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContentViewHolder contentViewHolder, int pos) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) contentViewHolder.mBinding.itemContainer.getLayoutParams();
        if( pos == mList.size() -1){
            //最后一个
            layoutParams.leftMargin = mContext.getResources().getDimensionPixelSize(R.dimen.x20);
            layoutParams.rightMargin = mContext.getResources().getDimensionPixelSize(R.dimen.x20);
        }else {
            layoutParams.leftMargin = mContext.getResources().getDimensionPixelSize(R.dimen.x20);
            layoutParams.rightMargin = 0;
        }
        contentViewHolder.itemView.setOnClickListener(v -> {
            if(mOnItemListener != null){
                mOnItemListener.onItemIntegralDetail(pos);
            }
        });
        Glide.with(mContext).load(mList.get(pos).getImg()).into(contentViewHolder.mBinding.itemImage);
        contentViewHolder.mBinding.itemCode.setText(mList.get(pos).getExchangePoint() + "极币");
        contentViewHolder.mBinding.itemName.setText(mList.get(pos).getGoodsName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class ContentViewHolder extends  RecyclerView.ViewHolder{

        private ItemMallHotBinding mBinding;

        public ContentViewHolder(ItemMallHotBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }
    }

    public interface OnItemListener{
        void onItemIntegralDetail(int position);
    }
}
