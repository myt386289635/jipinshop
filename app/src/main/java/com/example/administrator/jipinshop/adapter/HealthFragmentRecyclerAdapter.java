package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.jipinshop.MyApplication;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.fragment.home.health.HealthFragmentPresenter;
import com.example.administrator.jipinshop.util.DistanceHelper;
import com.example.administrator.jipinshop.view.glide.imageloder.ImageManager;

import java.util.List;

public class HealthFragmentRecyclerAdapter   extends RecyclerView.Adapter<HealthFragmentRecyclerAdapter.ViewHolder>{

    private List<String> mList;
    private Context mContext;
    private HealthFragmentPresenter mPresenter;
    private OnItem mOnItem;

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    public HealthFragmentRecyclerAdapter(List<String> list, Context context, HealthFragmentPresenter presenter) {
        mList = list;
        mContext = context;
        mPresenter = presenter;
    }

    @NonNull
    @Override
    public HealthFragmentRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.health_item, viewGroup, false);
        HealthFragmentRecyclerAdapter.ViewHolder holder = new HealthFragmentRecyclerAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HealthFragmentRecyclerAdapter.ViewHolder viewHolder, final int position) {

        viewHolder.mItemTitle.setText(position + 1 + "");

        ViewGroup.LayoutParams layoutParams = viewHolder.mItemProgressbar1Copy.getLayoutParams();
        ///104是dp值 满的
        layoutParams.width = DistanceHelper.dip2px(104);

        viewHolder.mItemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnItem != null){
                    mOnItem.onItemclick(position);
                }
            }
        });

        if(position == 0){
            viewHolder.mItemTitleContanier.setPadding(0,mContext.getResources().getDimensionPixelSize(R.dimen.x36),0,0);
        }

        String str = "<font color='#E31436'>推荐理由：</font>飞利浦电动牙刷hx3216是飞利浦推出入门级产品，采取声波震动技术原理，每分钟23000次/分，1种模式，是初次使用电动牙刷的首选。";
        viewHolder.mItemReason.setText(Html.fromHtml(str));

        ImageManager.displayRoundImage(MyApplication.imag,viewHolder.mItemImage,0,0,10);
    }

    @Override
    public int getItemCount() {
        // TODO: 2018/7/31 有假数据
        return mList.size() == 0 ? 0 : mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mItemTitle;
        private LinearLayout mItemTitleContanier;
        private ImageView mItemImage;
        private TextView mItemName;
        private TextView mItemTag1;
        private TextView mItemTag2;
        private TextView mItemPrice;
        private RelativeLayout mItemPriceContainer;
        private TextView mItemPriceOld;
        private LinearLayout mItemPriceOldContainer;
        private TextView mItemLookNum;
        private LinearLayout mItemLookNumContainer;
        private TextView mItemReason;
        private TextView mItemScore;
        private LinearLayout mItemScoreContainer;
        private TextView mItemProgressbar1Text;
        private View mItemProgressbar1;
        private ProgressBar mItemProgressbar1Copy;
        private RelativeLayout mItemProgressbar1Container;
        private TextView mItemProgressbar2Text;
        private View mItemProgressbar2;
        private ProgressBar mItemProgressbar2Copy;
        private RelativeLayout mItemProgressbar2Container;
        private TextView mItemProgressbar3Text;
        private View mItemProgressbar3;
        private ProgressBar mItemProgressbar3Copy;
        private RelativeLayout mItemProgressbar3Container;
        private TextView mItemProgressbar4Text;
        private View mItemProgressbar4;
        private ProgressBar mItemProgressbar4Copy;
        private RelativeLayout mItemProgressbar4Container;
        private TextView item_savePrice;
        private TextView item_goodsFrom;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemTitle = itemView.findViewById(R.id.item_title);
            mItemTitleContanier = itemView.findViewById(R.id.item_title_contanier);
            mItemImage = itemView.findViewById(R.id.item_image);
            mItemName = itemView.findViewById(R.id.item_name);
            mItemTag1 = itemView.findViewById(R.id.item_tag1);
            mItemTag2 = itemView.findViewById(R.id.item_tag2);
            mItemPrice = itemView.findViewById(R.id.item_price);
            mItemPriceContainer = itemView.findViewById(R.id.item_price_container);
            mItemPriceOld = itemView.findViewById(R.id.item_price_old);
            mItemPriceOldContainer = itemView.findViewById(R.id.item_price_old_container);
            mItemLookNum = itemView.findViewById(R.id.item_lookNum);
            mItemLookNumContainer = itemView.findViewById(R.id.item_lookNum_container);
            mItemReason = itemView.findViewById(R.id.item_reason);
            mItemScore = itemView.findViewById(R.id.item_score);
            mItemScoreContainer = itemView.findViewById(R.id.item_score_container);
            mItemProgressbar1Text = itemView.findViewById(R.id.item_progressbar1_text);
            mItemProgressbar1 = itemView.findViewById(R.id.item_progressbar1);
            mItemProgressbar1Copy = itemView.findViewById(R.id.item_progressbar1_copy);
            mItemProgressbar1Container = itemView.findViewById(R.id.item_progressbar1_container);
            mItemProgressbar2Text = itemView.findViewById(R.id.item_progressbar2_text);
            mItemProgressbar2 = itemView.findViewById(R.id.item_progressbar2);
            mItemProgressbar2Copy = itemView.findViewById(R.id.item_progressbar2_copy);
            mItemProgressbar2Container = itemView.findViewById(R.id.item_progressbar2_container);
            mItemProgressbar3Text = itemView.findViewById(R.id.item_progressbar3_text);
            mItemProgressbar3 = itemView.findViewById(R.id.item_progressbar3);
            mItemProgressbar3Copy = itemView.findViewById(R.id.item_progressbar3_copy);
            mItemProgressbar3Container = itemView.findViewById(R.id.item_progressbar3_container);
            mItemProgressbar4Text = itemView.findViewById(R.id.item_progressbar4_text);
            mItemProgressbar4 = itemView.findViewById(R.id.item_progressbar4);
            mItemProgressbar4Copy = itemView.findViewById(R.id.item_progressbar4_copy);
            mItemProgressbar4Container = itemView.findViewById(R.id.item_progressbar4_container);
            item_savePrice = itemView.findViewById(R.id.item_savePrice);
            item_goodsFrom = itemView.findViewById(R.id.item_goodsFrom);
        }
    }

    public interface OnItem{
        void onItemclick(int pos);
    }
}
