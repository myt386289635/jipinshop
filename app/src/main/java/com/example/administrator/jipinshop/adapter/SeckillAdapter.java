package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.SeckillBean;
import com.example.administrator.jipinshop.databinding.ItemSeckillBinding;
import com.example.administrator.jipinshop.util.ToastUtil;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/12/30
 * @Describe
 */
public class SeckillAdapter extends RecyclerView.Adapter<SeckillAdapter.ViewHolder> {

    private Context mContext;
    private List<SeckillBean.DataBean> mList;
    private OnItem mOnItem;

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    public SeckillAdapter(Context context, List<SeckillBean.DataBean> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemSeckillBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_seckill,viewGroup,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.mBinding.setData(mList.get(i));
        viewHolder.mBinding.itemShare.setVisibility(View.GONE);
        if (mList.get(i).getStatus() == 3){
            viewHolder.mBinding.itemProgressbarNotice.setText("未开始");
            viewHolder.mBinding.itemProgressbarNotice.setTextColor(Color.WHITE);
            viewHolder.mBinding.itemProgressbar.setSideColor(mContext.getResources().getColor(R.color.color_D8D8D8));
            viewHolder.mBinding.itemProgressbar.setTotalAndCurrentCount(mList.get(i).getTotal(),0);
            viewHolder.mBinding.itemBuy.setBackgroundResource(R.drawable.bg_d8d8d8);
            viewHolder.mBinding.itemBuy.setTextSize(11);
            viewHolder.mBinding.itemBuy.setText("未开始");
        }else if (mList.get(i).getStatus() == 2){
            if (mList.get(i).getTotal() != mList.get(i).getSoldTotal()){
                //未卖完
                BigDecimal totle = new BigDecimal(mList.get(i).getTotal() + "");
                BigDecimal soldTotal = new BigDecimal(mList.get(i).getSoldTotal() + "");
                double value = soldTotal.divide(totle,2,BigDecimal.ROUND_HALF_UP).doubleValue();
                viewHolder.mBinding.itemProgressbarNotice.setText("已抢"+(value * 100)+"%");
                viewHolder.mBinding.itemProgressbarNotice.setTextColor(mContext.getResources().getColor(R.color.color_C66D10));
                viewHolder.mBinding.itemProgressbar.setSideColor(mContext.getResources().getColor(R.color.color_F1F1F1));
                viewHolder.mBinding.itemProgressbar.setTotalAndCurrentCount(mList.get(i).getTotal(),mList.get(i).getSoldTotal());
                viewHolder.mBinding.itemBuy.setBackgroundResource(R.drawable.bg_group);
                viewHolder.mBinding.itemBuy.setTextSize(18);
                viewHolder.mBinding.itemBuy.setText("抢");
                viewHolder.mBinding.itemShare.setVisibility(View.VISIBLE);
            }else {
                //卖完了
                viewHolder.mBinding.itemProgressbarNotice.setText("已抢完");
                viewHolder.mBinding.itemProgressbarNotice.setTextColor(Color.WHITE);
                viewHolder.mBinding.itemProgressbar.setSideColor(mContext.getResources().getColor(R.color.color_D8D8D8));
                viewHolder.mBinding.itemProgressbar.setTotalAndCurrentCount(mList.get(i).getTotal(),0);
                viewHolder.mBinding.itemBuy.setBackgroundResource(R.drawable.bg_d8d8d8);
                viewHolder.mBinding.itemBuy.setTextSize(18);
                viewHolder.mBinding.itemBuy.setText("抢");
            }
        }else {
            viewHolder.mBinding.itemProgressbarNotice.setText("已结束");
            viewHolder.mBinding.itemProgressbarNotice.setTextColor(Color.WHITE);
            viewHolder.mBinding.itemProgressbar.setSideColor(mContext.getResources().getColor(R.color.color_D8D8D8));
            viewHolder.mBinding.itemProgressbar.setTotalAndCurrentCount(mList.get(i).getTotal(),0);
            viewHolder.mBinding.itemBuy.setBackgroundResource(R.drawable.bg_d8d8d8);
            viewHolder.mBinding.itemBuy.setTextSize(11);
            viewHolder.mBinding.itemBuy.setText("已结束");
        }
        viewHolder.mBinding.itemPriceOld.setTv(true);
        viewHolder.mBinding.itemPriceOld.setColor(R.color.color_9D9D9D);
        viewHolder.mBinding.executePendingBindings();
        viewHolder.mBinding.itemShare.setOnClickListener(v -> {
            //分享
            mOnItem.onItemShare(i);
        });
        viewHolder.itemView.setOnClickListener(v -> {
            if (mList.get(i).getStatus() == 3){
                ToastUtil.show("活动未开始，稍等片刻");
            }else if (mList.get(i).getStatus() == 2){
                if (mList.get(i).getTotal() != mList.get(i).getSoldTotal()){
                    //秒杀详情
                    mOnItem.onDetail(i);
                }else {
                    ToastUtil.show("商品已抢完，下次请早点来哦");
                }
            }else {
                ToastUtil.show("活动已结束，下次请早点来哦");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ItemSeckillBinding mBinding;

        public ViewHolder(@NonNull ItemSeckillBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }
    }

    public interface OnItem{
        void onItemShare(int position);
        void onDetail(int position);
    }
}
