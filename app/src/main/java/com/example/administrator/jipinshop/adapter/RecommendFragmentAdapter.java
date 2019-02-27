package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.RecommendFragmentBean;
import com.example.administrator.jipinshop.databinding.RecommendItemBinding;
import com.example.administrator.jipinshop.util.WeakRefHandler;
import com.example.administrator.jipinshop.view.glide.GlideApp;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;

public class RecommendFragmentAdapter extends RecyclerView.Adapter {

    private final int HEAD = 1;
    private final int CONTENT = 2;
    private final int FOOT = 3;
//    private final int HEAD2 = 4;

    private List<RecommendFragmentBean.DataBean> mList;
    private Context mContext;
    private OnItem mOnItem;
    private List<RecommendFragmentBean.AdListBean> image;
//    private List<RecommendFragmentBean.OrderbyTypeListBean> mOrderbyTypeListBeans;

//    public void setOrderbyTypeListBeans(List<RecommendFragmentBean.OrderbyTypeListBean> orderbyTypeListBeans) {
//        mOrderbyTypeListBeans = orderbyTypeListBeans;
//    }

    public void setHeadImage(List<RecommendFragmentBean.AdListBean> image) {
        this.image = image;
    }

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    public RecommendFragmentAdapter(List<RecommendFragmentBean.DataBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder holder = null;
        switch (i) {
            case HEAD:
                View view1 = LayoutInflater.from(mContext).inflate(R.layout.item_recommend, viewGroup, false);
                holder = new ContentViewHolder(view1);
                break;
            case CONTENT:
                RecommendItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.recommend_item, viewGroup, false);
                holder = new HeadViewHolder(binding);
                break;
            case FOOT:
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_foot, viewGroup, false);
                holder = new FootViewHolder(view);
                break;
//            case HEAD2:
//                View view2 = LayoutInflater.from(mContext).inflate(R.layout.item_recommendhead, viewGroup, false);
//                holder = new Head2ViewHolder(view2);
//                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int pos) {
        int type = getItemViewType(pos);
        switch (type) {
//            case HEAD2:
//                Head2ViewHolder head2ViewHolder = (Head2ViewHolder) holder;
//                GlideApp.loderImage(mContext,mOrderbyTypeListBeans.get(0).getImg(),head2ViewHolder.item_hotTopImg,0,0);
//                GlideApp.loderImage(mContext,mOrderbyTypeListBeans.get(1).getImg(),head2ViewHolder.item_luxuryTopImg,0,0);
//                GlideApp.loderImage(mContext,mOrderbyTypeListBeans.get(2).getImg(),head2ViewHolder.item_newTopImg,0,0);
//                GlideApp.loderImage(mContext,mOrderbyTypeListBeans.get(3).getImg(),head2ViewHolder.item_performanceTopImg,0,0);
//                head2ViewHolder.item_hotTopText.setText(mOrderbyTypeListBeans.get(0).getName());
//                head2ViewHolder.item_luxuryTopText.setText(mOrderbyTypeListBeans.get(1).getName());
//                head2ViewHolder.item_newTopText.setText(mOrderbyTypeListBeans.get(2).getName());
//                head2ViewHolder.item_performanceTopText.setText(mOrderbyTypeListBeans.get(3).getName());
//                head2ViewHolder.item_hotTop.setOnClickListener(v -> {
//                    if (mOnItem != null) {
//                        mOnItem.onTabItem(0);
//                    }
//                });
//                head2ViewHolder.item_luxuryTop.setOnClickListener(v -> {
//                    if (mOnItem != null) {
//                        mOnItem.onTabItem(1);
//                    }
//                });
//                head2ViewHolder.item_newTop.setOnClickListener(v -> {
//                    if (mOnItem != null) {
//                        mOnItem.onTabItem(2);
//                    }
//                });
//                head2ViewHolder.item_performanceTop.setOnClickListener(v -> {
//                    if (mOnItem != null) {
//                        mOnItem.onTabItem(3);
//                    }
//                });
//                break;
            case HEAD:
                ContentViewHolder contentViewHolder = (ContentViewHolder) holder;
                if(image.size() == 1){
                    contentViewHolder.recommend_image.setVisibility(View.VISIBLE);
                    contentViewHolder.mViewPager.setVisibility(View.GONE);
                    contentViewHolder.detail_point.setVisibility(View.GONE);
                    if(TextUtils.isEmpty(image.get(0).getImg())){
                        contentViewHolder.recommend_image.setBackgroundResource(R.mipmap.remmonent_banner);
                    }else {
                        GlideApp.loderImage(mContext,image.get(0).getImg(),contentViewHolder.recommend_image,0,0);
                    }
                }else {
                    contentViewHolder.recommend_image.setVisibility(View.GONE);
                    contentViewHolder.mViewPager.setVisibility(View.VISIBLE);
                    contentViewHolder.detail_point.setVisibility(View.VISIBLE);
                    contentViewHolder.mPagerAdapter.setViewPager(contentViewHolder.mViewPager);
                    contentViewHolder.mViewPager.setAdapter(contentViewHolder.mPagerAdapter);
                    contentViewHolder.initBanner(image, mContext,contentViewHolder.point
                            , contentViewHolder.detail_point, contentViewHolder.mPagerAdapter);
                    if(contentViewHolder.flag){
                        new Thread(() -> {
                            while (true) {
                                try {
                                    Thread.sleep(5000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                contentViewHolder.handler.sendEmptyMessage(100);
                            }
                        }).start();
                        contentViewHolder.flag = false;
                    }
                }
                break;
            case CONTENT:
                HeadViewHolder viewHolder = (HeadViewHolder) holder;

                final int position = pos - 1;
                viewHolder.getBinding().setPosition(pos + "");
                viewHolder.getBinding().setDate(mList.get(position));

                viewHolder.itemView.setOnClickListener(view -> {
                    if (mOnItem != null) {
                        mOnItem.onItem(position);
                    }
                });

                GlideApp.loderRoundImage(mContext,mList.get(position).getImg(),viewHolder.getBinding().itemImage,R.color.transparent,R.color.transparent);
                viewHolder.initTags(viewHolder.getBinding().itemFlex,position);

                if(mList.get(position).getSource() == 1){
                    viewHolder.getBinding().itemGoodsFrom.setText("京东：");
                }else  if(mList.get(position).getSource() == 2){
                    viewHolder.getBinding().itemGoodsFrom.setText("淘宝：");
                }else  if(mList.get(position).getSource() == 3){
                    viewHolder.getBinding().itemGoodsFrom.setText("天猫：");
                }
                viewHolder.getBinding().itemPriceOld.setTv(true);
                viewHolder.getBinding().itemPriceOld.setColor(R.color.color_ACACAC);

                if(!mList.get(position).getActualPrice().equals(mList.get(position).getOtherPrice())){
                    viewHolder.getBinding().itemPriceOldContainer.setVisibility(View.VISIBLE);
                }else {
                    viewHolder.getBinding().itemPriceOldContainer.setVisibility(View.GONE);
                }

                String str = "<font color='#151515' >推荐理由：</font>" + mList.get(position).getRecommendReason();
                viewHolder.getBinding().itemReason.setText(Html.fromHtml(str));

                // 立刻刷新界面
                viewHolder.getBinding().executePendingBindings();
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEAD;
        }
//        else if (position == 1){
//            return HEAD2;
//        }
        else if (position == getItemCount() - 1){
            return FOOT;
        } else {
            return CONTENT;
        }
    }

    @Override
    public int getItemCount() {
        return mList == null || mList.size() == 0 ? 0 : mList.size() + 2;
    }

    public class HeadViewHolder extends RecyclerView.ViewHolder {

        private RecommendItemBinding binding;

        public HeadViewHolder(RecommendItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public RecommendItemBinding getBinding() {
            return binding;
        }

        public void setBinding(RecommendItemBinding binding) {
            this.binding = binding;
        }

        public void initTags(FlexboxLayout flexboxLayout, int pos) {
            flexboxLayout.removeAllViews();
            for (int i = 0; i < mList.get(pos).getGoodsTagsList().size(); i++) {
                View itemTypeView = LayoutInflater.from(mContext).inflate(R.layout.item_goodstag, null);
                TextView textView = itemTypeView.findViewById(R.id.item_tag);
                textView.setText(mList.get(pos).getGoodsTagsList().get(i).getName());
                flexboxLayout.addView(itemTypeView);
            }
        }
    }


    class ContentViewHolder extends RecyclerView.ViewHolder {
        private ImageView recommend_image;
        private ViewPager mViewPager;
        private LinearLayout detail_point;

        private RecommendPageAdapter mPagerAdapter;
        private List<ImageView> point;
        private Handler.Callback mCallback = msg -> {
            if(msg.what == 100){
                if (mViewPager != null) {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                }
            }
            return true;
        };
        private Handler handler = new WeakRefHandler(mCallback, Looper.getMainLooper());

        private Boolean flag = true;

        public ContentViewHolder(View itemView) {
            super(itemView);
            recommend_image = itemView.findViewById(R.id.recommend_image);
            mViewPager = itemView.findViewById(R.id.view_pager);
            detail_point = itemView.findViewById(R.id.detail_point);

            mPagerAdapter = new RecommendPageAdapter(mContext);
            point = new ArrayList<>();
            mPagerAdapter.setList(image);
            mPagerAdapter.setPoint(point);
        }

        public void initBanner(List<RecommendFragmentBean.AdListBean> mBannerList , Context context ,
                               List<ImageView> point, LinearLayout mDetailPoint, RecommendPageAdapter mBannerAdapter){
            point.clear();
            mDetailPoint.removeAllViews();
            for (int i = 0; i < mBannerList.size(); i++) {
                ImageView imageView = new ImageView(context);
                point.add(imageView);
                if (i == 0) {
                    imageView.setImageResource(R.drawable.banner_down);
                } else {
                    imageView.setImageResource(R.drawable.banner_up);
                }
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.leftMargin =context.getResources().getDimensionPixelSize(R.dimen.x4);
                layoutParams.rightMargin = context.getResources().getDimensionPixelSize(R.dimen.x4);
                mDetailPoint.addView(imageView, layoutParams);
            }
            mBannerAdapter.notifyDataSetChanged();
        }
    }

    class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(View itemView) {
            super(itemView);
        }
    }


    public interface OnItem {
        void onItem(int pos);
//        void onTabItem(int pos);
    }
}
