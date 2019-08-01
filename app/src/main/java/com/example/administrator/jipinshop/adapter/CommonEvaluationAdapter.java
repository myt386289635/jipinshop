package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.EvaluationListBean;
import com.example.administrator.jipinshop.bean.EvaluationTabBean;
import com.example.administrator.jipinshop.util.ClickUtil;
import com.example.administrator.jipinshop.util.ShopJumpUtil;
import com.example.administrator.jipinshop.util.WeakRefHandler;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CommonEvaluationAdapter extends RecyclerView.Adapter {

    private static final int HEAD = 1;
    private static final int CONTENT = 2;

    private List<EvaluationListBean.DataBean> mList;
    private Context mContext;
    private List<EvaluationTabBean.DataBean.AdListBean> mAdListBeans;

    private Boolean isFlag;

    public CommonEvaluationAdapter(List<EvaluationListBean.DataBean> list, Context context,Boolean isFlag) {
        mList = list;
        mContext = context;
        this.isFlag = isFlag;
    }

    public void setAdListBeans(List<EvaluationTabBean.DataBean.AdListBean> adListBeans) {
        mAdListBeans = adListBeans;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case HEAD:
                View head = LayoutInflater.from(mContext).inflate(R.layout.item_evaluation_head, parent, false);
                HeadViewHolder headViewHolder = new HeadViewHolder(head);
                return headViewHolder;
            case CONTENT:
                View content = LayoutInflater.from(mContext).inflate(R.layout.item_sreachfind, parent, false);
                ContentViewHolder contentViewHolder = new ContentViewHolder(content);
                return contentViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type) {
            case HEAD:
                HeadViewHolder headViewHolder = (HeadViewHolder) holder;
                if (isFlag) {
                    if(mAdListBeans.size() != 1){
                        headViewHolder.view_pager.setVisibility(View.VISIBLE);
                        headViewHolder.detail_point.setVisibility(View.VISIBLE);
                        headViewHolder. head_image.setVisibility(View.GONE);
                        headViewHolder.mPagerAdapter.setViewPager(headViewHolder.view_pager);
                        headViewHolder.view_pager.setAdapter(headViewHolder.mPagerAdapter);
                        headViewHolder.initBanner(mAdListBeans, mContext,headViewHolder.point
                                , headViewHolder.detail_point, headViewHolder.mPagerAdapter);
                        new Thread(() -> {
                            while (true) {
                                try {
                                    Thread.sleep(5000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                headViewHolder.handler.sendEmptyMessage(100);
                            }
                        }).start();
                    }else {
                        headViewHolder.view_pager.setVisibility(View.GONE);
                        headViewHolder.detail_point.setVisibility(View.GONE);
                        headViewHolder.head_image.setVisibility(View.VISIBLE);
                        if(TextUtils.isEmpty(mAdListBeans.get(0).getImg())){
                            GlideApp.loderImage(mContext,R.drawable.evaluating_banner,headViewHolder.head_image,0,0);
                        }else {
                            GlideApp.loderImage(mContext,mAdListBeans.get(0).getImg(),headViewHolder.head_image,0,0);
                        }
                    }
                    isFlag = false;
                }
                break;
            case CONTENT:
                ContentViewHolder viewHolder = (ContentViewHolder) holder;
                if(mAdListBeans.size() != 0){
                    position = position - 1;
                }
                viewHolder.title.setText(mList.get(position).getTitle());
                GlideApp.loderRoundImage(mContext,mList.get(position).getImg(),viewHolder.item_image);
                GlideApp.loderCircleImage(mContext,mList.get(position).getUser().getAvatar(),viewHolder.item_head,R.mipmap.rlogo,0);
                viewHolder.item_name.setText(mList.get(position).getUser().getNickname());
                viewHolder.item_pv.setText(mList.get(position).getPv() + "阅读");
                if(mList.get(position).getUser().getAuthentication() == 0){
                    //普通用户
                    viewHolder.item_grade.setVisibility(View.GONE);
                }else if(mList.get(position).getUser().getAuthentication() == 1){
                    //个人认证
                    viewHolder.item_grade.setVisibility(View.VISIBLE);
                    viewHolder.item_grade.setImageResource(R.mipmap.grade_peroson);
                }else {
                    //企业认证
                    viewHolder.item_grade.setVisibility(View.VISIBLE);
                    viewHolder.item_grade.setImageResource(R.mipmap.grade_enterprise);
                }
                int finalPosition = position;
                viewHolder.itemView.setOnClickListener(v -> {
                    if (ClickUtil.isFastDoubleClick(800)) {
                        return;
                    }else{
                        BigDecimal bigDecimal = new BigDecimal(mList.get(finalPosition).getPv());
                        mList.get(finalPosition).setPv((bigDecimal.intValue() + 1) + "");
                        notifyDataSetChanged();
                        ShopJumpUtil.jumpArticle(mContext,mList.get(finalPosition).getArticleId(),
                                mList.get(finalPosition).getType(),mList.get(finalPosition).getContentType());
                    }
                });
                viewHolder.item_head.setOnClickListener(v -> {
                    // TODO: 2019/1/11  个人主页
                });
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(mAdListBeans.size() == 0){
            return CONTENT;
        }else {
            if (position == 0) {
                return HEAD;
            } else {
                return CONTENT;
            }
        }
    }

    @Override
    public int getItemCount() {
        if(mAdListBeans.size() == 0){
            return  mList.size() == 0 ? 0 : mList.size();
        }else {
            return mList.size() == 0 ? 0 : mList.size() + 1;
        }
    }

    class HeadViewHolder extends RecyclerView.ViewHolder {

        private ViewPager view_pager;
        private LinearLayout detail_point;
        private ImageView head_image;

        private HomePagerAdapter mPagerAdapter;
        private List<ImageView> point;

        private Handler.Callback mCallback = msg -> {
            if(msg.what == 100){
                if (view_pager != null) {
                    view_pager.setCurrentItem(view_pager.getCurrentItem() + 1);
                }
            }
            return true;
        };
        private Handler handler = new WeakRefHandler(mCallback, Looper.getMainLooper());

        public HeadViewHolder(View itemView) {
            super(itemView);
            view_pager = itemView.findViewById(R.id.view_pager);
            detail_point = itemView.findViewById(R.id.detail_point);
            head_image = itemView.findViewById(R.id.head_image);

            mPagerAdapter = new HomePagerAdapter(mContext);
            point = new ArrayList<>();
            mPagerAdapter.setList(mAdListBeans);
            mPagerAdapter.setPoint(point);
        }

        public void initBanner(List<EvaluationTabBean.DataBean.AdListBean> mBannerList , Context context , List<ImageView> point, LinearLayout mDetailPoint, HomePagerAdapter mBannerAdapter){
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


    class ContentViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView item_image;
        private ImageView item_head;
        private ImageView item_grade;
        private TextView item_name;
        private TextView item_pv;

        public ContentViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_title);
            item_image = itemView.findViewById(R.id.item_image);
            item_head = itemView.findViewById(R.id.item_head);
            item_grade = itemView.findViewById(R.id.item_grade);
            item_name = itemView.findViewById(R.id.item_name);
            item_pv = itemView.findViewById(R.id.item_pv);
        }
    }
}
