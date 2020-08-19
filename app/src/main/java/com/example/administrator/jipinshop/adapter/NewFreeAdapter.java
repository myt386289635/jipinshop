package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.NewFreeBean;
import com.example.administrator.jipinshop.databinding.ItemNewFree1Binding;
import com.example.administrator.jipinshop.databinding.ItemNewFree2Binding;
import com.example.administrator.jipinshop.databinding.ItemNewFree3Binding;
import com.example.administrator.jipinshop.util.TimeUtil;
import com.example.administrator.jipinshop.util.html.CustomerTagHandler_1;
import com.example.administrator.jipinshop.util.html.HtmlParser;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2020/5/22
 * @Describe
 */
public class NewFreeAdapter extends RecyclerView.Adapter {

    private static final int HEAD1 = 1;
    private static final int HEAD2 = 2;
    private static final int CONTENT1 = 3;

    private Context mContext;
    private List<NewFreeBean.DataBean> mList;
    private long endTime = 0;//免单结束时间
    private OnClickItem mOnClickItem;
    //用于退出 Activity,避免 Countdown，造成资源浪费。
    private SparseArray<CountDownTimer> countDownCounters ;
    private NewFreeBean.Ad2Bean ad2;
    private NewFreeBean.Ad1Bean ad1;

    public void setAd2(NewFreeBean.Ad2Bean ad2) {
        this.ad2 = ad2;
    }

    public void setAd1(NewFreeBean.Ad1Bean ad1) {
        this.ad1 = ad1;
    }

    /**
     * 清空当前 CountTimeDown 资源
     */
    public void cancelAllTimers() {
        if (countDownCounters == null) {
            return;
        }
        for (int i = 0, length = countDownCounters.size(); i < length; i++) {
            CountDownTimer cdt = countDownCounters.get(countDownCounters.keyAt(i));
            if (cdt != null) {
                cdt.cancel();
            }
        }
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        mOnClickItem = onClickItem;
    }

    public NewFreeAdapter(Context context, List<NewFreeBean.DataBean> list) {
        mContext = context;
        mList = list;
        this.countDownCounters = new SparseArray<>();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return HEAD1;
        }else if (position == mList.size() + 1){
            return HEAD2;
        }else {
            return CONTENT1;
        }
    }

    //为RecyclerView添加头布局
    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (getItemViewType(position) == HEAD1 || getItemViewType(position) == HEAD2) {
                        return gridLayoutManager.getSpanCount();
                    }else {
                        return 1;
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case HEAD1:
                ItemNewFree1Binding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_new_free1,viewGroup,false);
                holder = new HeadViewHolder(binding);
                break;
            case HEAD2:
                ItemNewFree2Binding binding2 = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_new_free2,viewGroup,false);
                holder = new Head2ViewHolder(binding2);
                break;
            case CONTENT1:
                ItemNewFree3Binding binding3 = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_new_free3,viewGroup,false);
                holder = new ContentViewHolder(binding3);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case HEAD1:
                HeadViewHolder holder1 = (HeadViewHolder) holder;
                holder1.binding.itemCopy.setOnClickListener(v -> mOnClickItem.onCopy());
                if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))){
                    holder1.binding.itemCountDown.setVisibility(View.GONE);
                    holder1.binding.itemEndText.setVisibility(View.VISIBLE);
                    holder1.binding.itemEndText.setText("登陆后领取免单资格");
                }else {
                    long timer = 0;
                    timer = (endTime * 1000) - System.currentTimeMillis();
                    CountDownTimer countDownTimer = countDownCounters.get(holder1.binding.itemCountDown.hashCode());
                    if (countDownTimer != null) {
                        //将复用的倒计时清除
                        countDownTimer.cancel();
                    }
                    if (timer > 0) {
                        countDownTimer = new CountDownTimer(timer, 1000) {
                            public void onTick(long millisUntilFinished) {
                                holder1.binding.itemEndText.setVisibility(View.GONE);
                                holder1.binding.itemCountDown.setText(TimeUtil.getCountTimeByLong2(millisUntilFinished) + "后将失效");
                            }
                            public void onFinish() {
                                holder1.binding.itemCountDown.setVisibility(View.GONE);
                                holder1.binding.itemEndText.setVisibility(View.VISIBLE);
                                holder1.binding.itemEndText.setText("您的免单权益已经失效分享好友来抢吧!");
                            }
                        }.start();
                        //将此 countDownTimer 放入list.
                        countDownCounters.put(holder1.binding.itemCountDown.hashCode(), countDownTimer);
                    }else {
                        //到期了
                        holder1.binding.itemCountDown.setVisibility(View.GONE);
                        holder1.binding.itemEndText.setVisibility(View.VISIBLE);
                        holder1.binding.itemEndText.setText("您的免单权益已经失效分享好友来抢吧!");
                    }
                }
                break;
            case HEAD2:
                Head2ViewHolder holder2 = (Head2ViewHolder) holder;
                String html = "选择本页面商品下单需先支付，确认收货后根据实付金额全额返现到您的账户中，金额可提现。" +
                        "所以真的是<b><font size='19'>免单！ </font></b><b><font size='15'>（仅限APP内第一单）</font></b>";
                holder2.binding.itemRule.setText(HtmlParser.buildSpannedText(html,new CustomerTagHandler_1()));
                holder2.binding.itemCopy.setOnClickListener(v -> mOnClickItem.onCopy());
                holder2.binding.itemOne.setOnClickListener(v -> mOnClickItem.onLeft(ad1));
                holder2.binding.itemTwo.setOnClickListener(v -> mOnClickItem.onRight(ad2));
                GlideApp.loderImage(mContext,ad1.getImg(),holder2.binding.itemOne,0,0);
                GlideApp.loderImage(mContext,ad2.getImg(),holder2.binding.itemTwo,0,0);
                break;
            case CONTENT1:
                ContentViewHolder holder3 = (ContentViewHolder) holder;
                int pos = position - 1;
                holder3.binding.setDate(mList.get(pos));
                holder3.binding.executePendingBindings();
                if (mList.get(pos).getIsBuy().equals("1")){
                    holder3.binding.itemTag.setImageResource(R.mipmap.new_purchased);
                }else {
                    holder3.binding.itemTag.setImageResource(R.mipmap.new_welfare);
                }
                holder3.binding.itemOtherPrice.setTv(true);
                holder3.binding.itemOtherPrice.setColor(R.color.color_989898);
                holder3.binding.itemImage.post(() -> {
                    ViewGroup.LayoutParams layoutParams = holder3.binding.itemImage.getLayoutParams();
                    layoutParams.height = holder3.binding.itemImage.getWidth();
                    holder3.binding.itemImage.setLayoutParams(layoutParams);
                });
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder3.binding.itemContainer.getLayoutParams();
                if (pos % 2 != 0){//右
                    layoutParams.leftMargin = (int) mContext.getResources().getDimension(R.dimen.x5);
                    layoutParams.rightMargin = (int) mContext.getResources().getDimension(R.dimen.x30);
                }else {//左
                    layoutParams.leftMargin = (int) mContext.getResources().getDimension(R.dimen.x30);
                    layoutParams.rightMargin = (int) mContext.getResources().getDimension(R.dimen.x5);
                }
                holder3.binding.itemContainer.setLayoutParams(layoutParams);
                holder3.binding.itemContainer.setOnClickListener(v -> {
                    mOnClickItem.onBuy(pos);
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (mList.size() == 0){
            return 0;
        }else {
            return  mList.size() + 2;
        }
    }

    class HeadViewHolder extends RecyclerView.ViewHolder{

        private ItemNewFree1Binding binding;

        public HeadViewHolder(@NonNull ItemNewFree1Binding binding ) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    class Head2ViewHolder extends RecyclerView.ViewHolder{

        private ItemNewFree2Binding binding;

        public Head2ViewHolder(@NonNull ItemNewFree2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    class ContentViewHolder extends RecyclerView.ViewHolder{

        private  ItemNewFree3Binding binding;

        public ContentViewHolder(@NonNull ItemNewFree3Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnClickItem {

        void onBuy(int position);
        void onCopy();
        void onLeft(NewFreeBean.Ad1Bean ad1);
        void onRight(NewFreeBean.Ad2Bean ad2);

    }
}
