package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.WebActivity;
import com.example.administrator.jipinshop.activity.mall.MallActivity;
import com.example.administrator.jipinshop.activity.sign.detail.IntegralDetailActivity;
import com.example.administrator.jipinshop.bean.DailyTaskBean;
import com.example.administrator.jipinshop.bean.MallBean;
import com.example.administrator.jipinshop.bean.MyWalletBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.databinding.ItemMallBinding;
import com.example.administrator.jipinshop.databinding.ItemSignHead1Binding;
import com.example.administrator.jipinshop.databinding.ItemSignHead2Binding;
import com.example.administrator.jipinshop.databinding.ItemSignHead3Binding;
import com.example.administrator.jipinshop.databinding.ItemSignHead4Binding;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;
import com.example.administrator.jipinshop.util.ShopJumpUtil;
import com.example.administrator.jipinshop.util.UmApp.AppStatisticalUtil;
import com.example.administrator.jipinshop.view.glide.GlideApp;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2021/3/16
 * @Describe
 */
public class SignAdapter extends RecyclerView.Adapter {

    private static final int head1 = 1;
    private static final int head2 = 2;
    private static final int head3 = 3;
    private static final int head4 = 4;
    private static final int content = 5;

    private Context mContext;
    private List<MallBean.DataBean> mList;
    private int set = 0;//左边 0  右边1
    private OnItem mOnItem;
    private String type;//1:fragment 2:activity
    private DailyTaskBean mBean;
    private AppStatisticalUtil appStatisticalUtil;
    private LifecycleTransformer<SuccessBean> transformer;

    public SignAdapter(Context context, List<MallBean.DataBean> list) {
        mContext = context;
        mList = list;
    }

    public void setAppStatisticalUtil(AppStatisticalUtil appStatisticalUtil) {
        this.appStatisticalUtil = appStatisticalUtil;
    }

    public void setTransformer(LifecycleTransformer<SuccessBean> transformer) {
        this.transformer = transformer;
    }

    public void setBean(DailyTaskBean bean) {
        mBean = bean;
    }

    public void setSet(int set) {
        this.set = set;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return head1;
        }else if (position == 1){
            return head2;
        }else if (position == 2){
            return head3;
        }else if (position == 3){
            return head4;
        }else {
            return content;
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
                    if (getItemViewType(position) == content) {
                        return 1;
                    }else {
                        return gridLayoutManager.getSpanCount();
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
            case head1:
                ItemSignHead1Binding head1View = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_sign_head1,viewGroup,false);
                holder  = new Head1ViewHolder(head1View);
                break;
            case head2:
                ItemSignHead2Binding head2View = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_sign_head2,viewGroup,false);
                holder  = new Head2ViewHolder(head2View);
                break;
            case head3:
                ItemSignHead3Binding head3Binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_sign_head3,viewGroup,false);
                holder  = new Head3ViewHolder(head3Binding);
                break;
            case head4:
                ItemSignHead4Binding head4Binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_sign_head4,viewGroup,false);
                holder  = new Head4ViewHolder(head4Binding);
                break;
            case content:
                ItemMallBinding contentView = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_mall,viewGroup,false);
                holder  = new ContentViewHolder(contentView);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case head1:
                Head1ViewHolder head1ViewHolder = (Head1ViewHolder) holder;
                if (type.equals("1")){
                    head1ViewHolder.mBinding.titleBack.setVisibility(View.GONE);
                }else {
                    head1ViewHolder.mBinding.titleBack.setVisibility(View.VISIBLE);
                }
                setStatusBarHight(head1ViewHolder.mBinding.statusBar);
                head1ViewHolder.mBinding.itemCode.setText(mBean.getData().getPoint() + "");
                if (mBean.getData().getIsSignin() == 0){
                    //未签到
                    head1ViewHolder.mBinding.itemSign.setText("签到");
                    head1ViewHolder.mBinding.itemSign.setBackgroundResource(R.drawable.bg_sign9);
                }else {
                    head1ViewHolder.mBinding.itemSign.setText("已签到");
                    head1ViewHolder.mBinding.itemSign.setBackgroundResource(R.drawable.bg_d8d8d8);
                }
                RelativeLayout[] containers = new RelativeLayout[]{head1ViewHolder.mBinding.itemSign1Container,
                        head1ViewHolder.mBinding.itemSign2Container, head1ViewHolder.mBinding.itemSign3Container,
                        head1ViewHolder.mBinding.itemSign4Container, head1ViewHolder.mBinding.itemSign5Container,
                        head1ViewHolder.mBinding.itemSign6Container, head1ViewHolder.mBinding.itemSign7Container};
                TextView[] titles = new TextView[]{head1ViewHolder.mBinding.itemSign1Title,
                        head1ViewHolder.mBinding.itemSign2Title,head1ViewHolder.mBinding.itemSign3Title,
                        head1ViewHolder.mBinding.itemSign4Title,head1ViewHolder.mBinding.itemSign5Title,
                        head1ViewHolder.mBinding.itemSign6Title,head1ViewHolder.mBinding.itemSign7Title};
                TextView[] codes = new TextView[]{head1ViewHolder.mBinding.itemSign1Code,
                        head1ViewHolder.mBinding.itemSign2Code,head1ViewHolder.mBinding.itemSign3Code,
                        head1ViewHolder.mBinding.itemSign4Code,head1ViewHolder.mBinding.itemSign5Code,
                        head1ViewHolder.mBinding.itemSign6Code,head1ViewHolder.mBinding.itemSign7Code};
                TextView[] signs = new TextView[]{head1ViewHolder.mBinding.itemSign1,
                        head1ViewHolder.mBinding.itemSign2,head1ViewHolder.mBinding.itemSign3,
                        head1ViewHolder.mBinding.itemSign4,head1ViewHolder.mBinding.itemSign5,
                        head1ViewHolder.mBinding.itemSign6,head1ViewHolder.mBinding.itemSign7};
                for (int i = 0; i < mBean.getData().getSigninList().size(); i++) {
                    titles[i].setTextColor(mContext.getResources().getColor(R.color.color_white));
                    signs[i].setTextColor(mContext.getResources().getColor(R.color.color_white));
                    if (i != 6){
                        codes[i].setText("+" + mBean.getData().getSigninList().get(i).getPoint());
                    }else {
                        codes[i].setText("最高可得100极币");
                        codes[i].setTextColor(mContext.getResources().getColor(R.color.color_white));
                    }
                    switch (mBean.getData().getSigninList().get(i).getStatus()){
                        case -1://补签
                            int finalI = i;
                            containers[i].setBackgroundResource(R.drawable.bg_sign8);
                            containers[i].setOnClickListener(v -> {
                                if (mOnItem != null)
                                    mOnItem.onNoSign(finalI);
                            });
                            signs[i].setText("补签");
                            break;
                        case 0://待签
                            containers[i].setBackgroundResource(R.drawable.bg_sign6);
                            containers[i].setOnClickListener(v -> {});
                            signs[i].setText("待签到");
                            break;
                        case 1://已签到
                            containers[i].setBackgroundResource(R.drawable.bg_sign7);
                            containers[i].setOnClickListener(v -> {});
                            titles[i].setTextColor(mContext.getResources().getColor(R.color.color_9D9D9D));
                            if (i == 6){
                                codes[i].setTextColor(mContext.getResources().getColor(R.color.color_202020));
                            }
                            signs[i].setText("已签到");
                            signs[i].setTextColor(mContext.getResources().getColor(R.color.color_202020));
                            break;
                    }
                }
                head1ViewHolder.mBinding.titleBack.setOnClickListener(v -> {
                    if(mOnItem != null){
                        mOnItem.onFinish();
                    }
                });
                head1ViewHolder.mBinding.signDetail.setOnClickListener(v -> {
                    mContext.startActivity(new Intent(mContext, IntegralDetailActivity.class));
                });
                head1ViewHolder.mBinding.itemRule.setOnClickListener(v -> {
                    mContext.startActivity(new Intent(mContext, WebActivity.class)
                            .putExtra(WebActivity.url, RetrofitModule.H5_URL + "rule.html")
                            .putExtra(WebActivity.title, "规则说明")
                    );
                });
                head1ViewHolder.mBinding.itemSign.setOnClickListener(v -> {
                    if(mOnItem != null){
                        mOnItem.onSign();
                    }
                });
                break;
            case head2:
                Head2ViewHolder head2ViewHolder = (Head2ViewHolder) holder;
                head2ViewHolder.list.clear();
                if (set == 0){
                    //左边
                    head2ViewHolder.mBinding.itemRuleLeft.setBackgroundResource(R.drawable.bg_sign11);
                    head2ViewHolder.mBinding.itemRuleRight.setBackgroundResource(R.drawable.bg_d8d8d8_5);
                    head2ViewHolder.list.addAll(mBean.getData().getList1());
                }else {
                    //右边
                    head2ViewHolder.mBinding.itemRuleLeft.setBackgroundResource(R.drawable.bg_d8d8d8_6);
                    head2ViewHolder.mBinding.itemRuleRight.setBackgroundResource(R.drawable.bg_sign10);
                    head2ViewHolder.list.addAll(mBean.getData().getList2());
                }
                head2ViewHolder.adapter.setType(set);
                head2ViewHolder.adapter.setOnClickJump(pos -> {
                    if(mOnItem != null){
                        mOnItem.onRuleJump(set ,pos);
                    }
                });
                head2ViewHolder.adapter.notifyDataSetChanged();
                head2ViewHolder.mBinding.itemRuleLeft.setOnClickListener(v -> {
                    if(mOnItem != null){
                        mOnItem.onLeft();
                    }
                });
                head2ViewHolder.mBinding.itemRuleRight.setOnClickListener(v -> {
                    if(mOnItem != null){
                        mOnItem.onRight();
                    }
                });
                break;
            case head3:
                Head3ViewHolder head3ViewHolder = (Head3ViewHolder) holder;
                head3ViewHolder.list.clear();
                head3ViewHolder.list.addAll(mBean.getData().getBoxList());
                head3ViewHolder.adapter.notifyDataSetChanged();
                head3ViewHolder.mBinding.itemShop.setOnClickListener(v -> {
                    mContext.startActivity(new Intent(mContext, MallActivity.class)
                            .putExtra("from",1)
                    );
                });
                GlideApp.loderImage(mContext,mBean.getData().getAd1().getImg(),head3ViewHolder.mBinding.itemAdImage,0,0);
                GlideApp.loderImage(mContext,mBean.getData().getAd2().getImg(),head3ViewHolder.mBinding.itemAd2,0,0);
                head3ViewHolder.mBinding.itemAdImage.setOnClickListener(v -> {
                    ShopJumpUtil.openBanner(mContext,mBean.getData().getAd1().getType(),
                            mBean.getData().getAd1().getObjectId(),mBean.getData().getAd1().getName(),
                            mBean.getData().getAd1().getSource() , mBean.getData().getAd1().getRemark());
                });
                head3ViewHolder.mBinding.itemAd2.setOnClickListener(v -> {
                    ShopJumpUtil.openBanner(mContext,mBean.getData().getAd2().getType(),
                            mBean.getData().getAd2().getObjectId(),mBean.getData().getAd2().getName(),
                            mBean.getData().getAd2().getSource(),mBean.getData().getAd2().getRemark());
                });
                break;
            case head4:
                Head4ViewHolder head4ViewHolder = (Head4ViewHolder) holder;
                head4ViewHolder.list.clear();
                head4ViewHolder.list.addAll(mBean.getData().getGoodsList());
                head4ViewHolder.adapter.notifyDataSetChanged();
                head4ViewHolder.adapter.setOnItemListener(position1 -> {
                    if(mOnItem != null){
                        mOnItem.onHotDetail(position1);
                    }
                });
                break;
            case content:
                ContentViewHolder contentViewHolder = (ContentViewHolder) holder;
                int pos  = position - 4;
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) contentViewHolder.mBinding.itemContainer.getLayoutParams();
                RelativeLayout.LayoutParams imageParams = (RelativeLayout.LayoutParams) contentViewHolder.mBinding.itemImage.getLayoutParams();
                if( pos % 2 == 0){
                    //左边
                    layoutParams.leftMargin = mContext.getResources().getDimensionPixelSize(R.dimen.x20);
                    layoutParams.rightMargin = 0;
                    imageParams.leftMargin = mContext.getResources().getDimensionPixelSize(R.dimen.x20);
                    imageParams.rightMargin = mContext.getResources().getDimensionPixelSize(R.dimen.x10);
                }else {
                    //右边
                    layoutParams.leftMargin = 0;
                    layoutParams.rightMargin = mContext.getResources().getDimensionPixelSize(R.dimen.x20);
                    imageParams.leftMargin = mContext.getResources().getDimensionPixelSize(R.dimen.x10);
                    imageParams.rightMargin = mContext.getResources().getDimensionPixelSize(R.dimen.x20);
                }
                contentViewHolder.itemView.setOnClickListener(v -> {
                    if(mOnItem != null){
                        mOnItem.onMoreDetail(pos);
                    }
                });
                if (mList.get(pos).getType() == 1){
                    contentViewHolder.mBinding.itemTag.setVisibility(View.VISIBLE);
                }else {
                    contentViewHolder.mBinding.itemTag.setVisibility(View.GONE);
                }
                Glide.with(mContext).load(mList.get(pos).getImg()).into(contentViewHolder.mBinding.itemImage);
                contentViewHolder.mBinding.itemCode.setText(mList.get(pos).getExchangePoint() + "极币");
                contentViewHolder.mBinding.itemName.setText(mList.get(pos).getGoodsName());
                contentViewHolder.mBinding.itemPrice.setTv(true);
                contentViewHolder.mBinding.itemPrice.setColor(R.color.color_ACACAC);
                contentViewHolder.mBinding.itemPrice.setText(mList.get(pos).getOtherPrice() + "元");
                contentViewHolder.mBinding.itemSaled.setText("已售：" + mList.get(pos).getSoldTotal() + "件");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() == 0 ? 0 : mList.size() + 4;
    }

    public void setStatusBarHight(LinearLayout StatusBar){
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            int statusBarHeight = mContext.getResources().getDimensionPixelSize(resourceId);
            ViewGroup.LayoutParams layoutParams = StatusBar.getLayoutParams();
            layoutParams.height = statusBarHeight;
        }
    }

    class Head1ViewHolder extends RecyclerView.ViewHolder{

        private ItemSignHead1Binding mBinding;

        public Head1ViewHolder(@NonNull ItemSignHead1Binding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }
    }

    class Head2ViewHolder extends RecyclerView.ViewHolder{

        private ItemSignHead2Binding mBinding;
        private List<DailyTaskBean.DataBean.ListBean> list;
        private KTSignAdapter adapter;

        public Head2ViewHolder(@NonNull ItemSignHead2Binding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;

            list = new ArrayList<>();
            adapter = new KTSignAdapter(list,mContext);
            mBinding.itemRule.setLayoutManager(new LinearLayoutManager(mContext));
            mBinding.itemRule.setNestedScrollingEnabled(false);
            mBinding.itemRule.setFocusable(false);//拒绝首次进入页面时滑动
            mBinding.itemRule.setAdapter(adapter);
        }
    }

    class Head3ViewHolder extends RecyclerView.ViewHolder{

        private ItemSignHead3Binding mBinding;
        private List<MyWalletBean.DataBean.AdListBean> list;
        private KTMineGirdAdapter adapter;

        public Head3ViewHolder(@NonNull ItemSignHead3Binding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;

            list = new ArrayList<>();
            adapter = new KTMineGirdAdapter(list,mContext);
            adapter.setAppStatisticalUtil(appStatisticalUtil);
            adapter.setTransformer(transformer);
            adapter.setName("zhuanqian_gongge.");
            mBinding.itemAd.setLayoutManager(new GridLayoutManager(mContext,4));
            mBinding.itemAd.setNestedScrollingEnabled(false);
            mBinding.itemAd.setFocusable(false);//拒绝首次进入页面时滑动
            mBinding.itemAd.setAdapter(adapter);
        }
    }

    class Head4ViewHolder extends RecyclerView.ViewHolder{

        private ItemSignHead4Binding mBinding;
        private List<MallBean.DataBean> list;
        private SignMallAdapter adapter;

        public Head4ViewHolder(@NonNull ItemSignHead4Binding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;

            list = new ArrayList<>();
            adapter = new SignMallAdapter(mContext,list);
            mBinding.itemHot.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
            mBinding.itemHot.setFocusable(false);//拒绝首次进入页面时滑动
            mBinding.itemHot.setAdapter(adapter);
        }
    }

    class ContentViewHolder extends RecyclerView.ViewHolder{

        private ItemMallBinding mBinding;

        public ContentViewHolder(@NonNull ItemMallBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }
    }

    public interface OnItem{
        void onMoreDetail(int position);//更多商品跳转
        void onLeft();//点击新人任务
        void onRight();//点击日常任务
        void onRuleJump(int set ,int position);//点击任务跳转
        void onHotDetail(int position);//热门商品跳转
        void onFinish();//关闭按钮
        void onSign();//签到
        void onNoSign(int day);//补签
    }
}
