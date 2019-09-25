package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.FreeBean;
import com.example.administrator.jipinshop.databinding.ItemFreeOneBinding;
import com.example.administrator.jipinshop.databinding.ItemFreeTwoBinding;

import java.util.List;

import static com.example.administrator.jipinshop.util.TimeUtil.dateAddOneDay;
import static com.example.administrator.jipinshop.util.TimeUtil.getCountTimeByLong;

/**
 * @author 莫小婷
 * @create 2019/6/19
 * @Describe 免单首页
 */
public class FreeAdapter extends RecyclerView.Adapter{

    private static final int HEAD = 1;
    private static final int SECEND = 2;

    private List<FreeBean.DataBean> mList;
    private Context mContext;
    //用于退出 Activity,避免 Countdown，造成资源浪费。
    private SparseArray<CountDownTimer> countDownCounters ;
    private OnClickItem mOnClickItem;

    public void setOnClickItem(OnClickItem onClickItem) {
        mOnClickItem = onClickItem;
    }

    public FreeAdapter(List<FreeBean.DataBean> list, Context context) {
        mList = list;
        mContext = context;
        this.countDownCounters = new SparseArray<>();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return HEAD;
        }else {
            return SECEND;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        RecyclerView.ViewHolder holder = null;
        switch (type){
            case HEAD:
                ItemFreeOneBinding oneBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_free_one,viewGroup,false);
                holder = new OneViewHolder(oneBinding);
                break;
            case SECEND:
                ItemFreeTwoBinding twoBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_free_two,viewGroup,false);
                holder = new TwoViewHolder(twoBinding);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type){
            case HEAD:
                OneViewHolder oneViewHolder = (OneViewHolder) holder;
                oneViewHolder.oneBinding.setDate(mList.get(position));
                if(mList.get(position).getStatus() == 0){//即将开始
                    oneViewHolder.oneBinding.itemCountdown.setVisibility(View.VISIBLE);
                    oneViewHolder.oneBinding.itemProgressbarContainer.setVisibility(View.GONE);
                    oneViewHolder.oneBinding.itemButton.setBackgroundResource(R.drawable.bg_freethree);
                    oneViewHolder.oneBinding.itemButton.setTextColor(mContext.getResources().getColor(R.color.color_F76B1C));
                    oneViewHolder.oneBinding.itemButton.setText("即将开始");
                    CountDownTimer countDownTimer = countDownCounters.get(oneViewHolder.oneBinding.itemCountdown.hashCode());
                    if (countDownTimer != null) {
                        //将复用的倒计时清除
                        countDownTimer.cancel();
                    }
                    long timer = 0;
                    timer = dateAddOneDay(mList.get(position).getActivitiesStartTime()) - System.currentTimeMillis();
                    if (timer > 0) {
                        countDownTimer = new CountDownTimer(timer, 1000) {
                            public void onTick(long millisUntilFinished) {
                                oneViewHolder.oneBinding.itemFreeCountdown.setText("开抢倒计时："+ getCountTimeByLong(millisUntilFinished));
                            }
                            public void onFinish() {
                                oneViewHolder.oneBinding.itemCountdown.setVisibility(View.GONE);
                                oneViewHolder.oneBinding.itemProgressbarContainer.setVisibility(View.VISIBLE);
                                oneViewHolder.oneBinding.itemButton.setBackgroundResource(R.drawable.bg_freeone);
                                oneViewHolder.oneBinding.itemButton.setTextColor(mContext.getResources().getColor(R.color.color_white));
                                oneViewHolder.oneBinding.itemButton.setText("立即抢购");
                            }
                        }.start();
                        //将此 countDownTimer 放入list.
                        countDownCounters.put(oneViewHolder.oneBinding.itemCountdown.hashCode(), countDownTimer);
                    }else {
                        oneViewHolder.oneBinding.itemCountdown.setVisibility(View.GONE);
                        oneViewHolder.oneBinding.itemProgressbarContainer.setVisibility(View.VISIBLE);
                        oneViewHolder.oneBinding.itemButton.setBackgroundResource(R.drawable.bg_freeone);
                        oneViewHolder.oneBinding.itemButton.setTextColor(mContext.getResources().getColor(R.color.color_white));
                        oneViewHolder.oneBinding.itemButton.setText("立即抢购");
                    }
                }else if(mList.get(position).getStatus() == 1){//1进行中
                    oneViewHolder.oneBinding.itemCountdown.setVisibility(View.GONE);
                    oneViewHolder.oneBinding.itemProgressbarContainer.setVisibility(View.VISIBLE);
                    oneViewHolder.oneBinding.itemButton.setBackgroundResource(R.drawable.bg_freeone);
                    oneViewHolder.oneBinding.itemButton.setTextColor(mContext.getResources().getColor(R.color.color_white));
                    oneViewHolder.oneBinding.itemButton.setText("立即抢购");
                }else if(mList.get(position).getStatus() == 2){//2已售罄
                    oneViewHolder.oneBinding.itemCountdown.setVisibility(View.GONE);
                    oneViewHolder.oneBinding.itemProgressbarContainer.setVisibility(View.VISIBLE);
                    oneViewHolder.oneBinding.itemButton.setBackgroundResource(R.drawable.bg_freetwo);
                    oneViewHolder.oneBinding.itemButton.setTextColor(mContext.getResources().getColor(R.color.color_white));
                    oneViewHolder.oneBinding.itemButton.setText("已售罄");
                }
                oneViewHolder.oneBinding.itemProgressbar.setTotalAndCurrentCount(mList.get(position).getCount(),mList.get(position).getUserCount());
                oneViewHolder.oneBinding.itemOtherPrice.setTv(true);
                oneViewHolder.oneBinding.itemOtherPrice.setColor(R.color.color_ACACAC);
                oneViewHolder.oneBinding.executePendingBindings();
                oneViewHolder.itemView.setOnClickListener(v -> {
                    if(mOnClickItem != null){
                        mOnClickItem.onClickItem(position);
                    }
                });
                break;
            case SECEND:
                TwoViewHolder twoViewHolder = (TwoViewHolder) holder;
                twoViewHolder.twoBinding.setDate(mList.get(position));
                if(mList.get(position).getStatus() == 0){//即将开始
                    twoViewHolder.twoBinding.itemCountdown.setVisibility(View.VISIBLE);
                    twoViewHolder.twoBinding.itemProgressbarContainer.setVisibility(View.GONE);
                    twoViewHolder.twoBinding.itemButton.setBackgroundResource(R.drawable.bg_freethree);
                    twoViewHolder.twoBinding.itemButton.setTextColor(mContext.getResources().getColor(R.color.color_F76B1C));
                    twoViewHolder.twoBinding.itemButton.setText("即将开始");
                    CountDownTimer countDownTimer = countDownCounters.get(twoViewHolder.twoBinding.itemCountdown.hashCode());
                    if (countDownTimer != null) {
                        //将复用的倒计时清除
                        countDownTimer.cancel();
                    }
                    long timer = 0;
                    timer = dateAddOneDay(mList.get(position).getActivitiesStartTime()) - System.currentTimeMillis();
                    if (timer > 0) {
                        countDownTimer = new CountDownTimer(timer, 1000) {
                            public void onTick(long millisUntilFinished) {
                                twoViewHolder.twoBinding.itemFreeCountdown.setText("开抢倒计时："+ getCountTimeByLong(millisUntilFinished));
                            }
                            public void onFinish() {
                                twoViewHolder.twoBinding.itemCountdown.setVisibility(View.GONE);
                                twoViewHolder.twoBinding.itemProgressbarContainer.setVisibility(View.VISIBLE);
                                twoViewHolder.twoBinding.itemButton.setBackgroundResource(R.drawable.bg_freeone);
                                twoViewHolder.twoBinding.itemButton.setTextColor(mContext.getResources().getColor(R.color.color_white));
                                twoViewHolder.twoBinding.itemButton.setText("立即抢购");
                            }
                        }.start();
                        //将此 countDownTimer 放入list.
                        countDownCounters.put(twoViewHolder.twoBinding.itemCountdown.hashCode(), countDownTimer);
                    }else {
                        twoViewHolder.twoBinding.itemCountdown.setVisibility(View.GONE);
                        twoViewHolder.twoBinding.itemProgressbarContainer.setVisibility(View.VISIBLE);
                        twoViewHolder.twoBinding.itemButton.setBackgroundResource(R.drawable.bg_freeone);
                        twoViewHolder.twoBinding.itemButton.setTextColor(mContext.getResources().getColor(R.color.color_white));
                        twoViewHolder.twoBinding.itemButton.setText("立即抢购");
                    }
                }else if(mList.get(position).getStatus() == 1){//1进行中
                    twoViewHolder.twoBinding.itemCountdown.setVisibility(View.GONE);
                    twoViewHolder.twoBinding.itemProgressbarContainer.setVisibility(View.VISIBLE);
                    twoViewHolder.twoBinding.itemButton.setBackgroundResource(R.drawable.bg_freeone);
                    twoViewHolder.twoBinding.itemButton.setTextColor(mContext.getResources().getColor(R.color.color_white));
                    twoViewHolder.twoBinding.itemButton.setText("立即抢购");
                }else if(mList.get(position).getStatus() == 2){//2已售罄
                    twoViewHolder.twoBinding.itemCountdown.setVisibility(View.GONE);
                    twoViewHolder.twoBinding.itemProgressbarContainer.setVisibility(View.VISIBLE);
                    twoViewHolder.twoBinding.itemButton.setBackgroundResource(R.drawable.bg_freetwo);
                    twoViewHolder.twoBinding.itemButton.setTextColor(mContext.getResources().getColor(R.color.color_white));
                    twoViewHolder.twoBinding.itemButton.setText("已售罄");
                }
                twoViewHolder.twoBinding.itemProgressbar.setTotalAndCurrentCount(mList.get(position).getCount(),mList.get(position).getUserCount());
                twoViewHolder.twoBinding.itemOtherPrice.setTv(true);
                twoViewHolder.twoBinding.itemOtherPrice.setColor(R.color.color_ACACAC);
                twoViewHolder.twoBinding.executePendingBindings();
                twoViewHolder.itemView.setOnClickListener(v -> {
                    if(mOnClickItem != null){
                        mOnClickItem.onClickItem(position);
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class OneViewHolder extends  RecyclerView.ViewHolder{

        private ItemFreeOneBinding oneBinding;

        public OneViewHolder(@NonNull ItemFreeOneBinding oneBinding) {
            super(oneBinding.getRoot());
            this.oneBinding = oneBinding;
        }
    }

    class TwoViewHolder extends RecyclerView.ViewHolder{

        private ItemFreeTwoBinding twoBinding;

        public TwoViewHolder(@NonNull ItemFreeTwoBinding twoBinding) {
            super(twoBinding.getRoot());
            this.twoBinding = twoBinding;
        }
    }


    public interface OnClickItem{
        void onClickItem(int position);
    }
}
