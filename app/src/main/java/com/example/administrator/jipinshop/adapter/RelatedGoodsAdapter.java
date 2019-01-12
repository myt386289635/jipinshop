package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.FindDetailBean;
import com.example.administrator.jipinshop.view.TextViewDel;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/1/11
 * @Describe
 */
public class RelatedGoodsAdapter extends RecyclerView.Adapter<RelatedGoodsAdapter.ViewHolder>{

    private List<FindDetailBean.DataBean.RelatedGoodsListBean> mBeans;
    private Context mContext;
    private OnItem mOnItem;

    public void setOnItem(OnItem onItem) {
        mOnItem = onItem;
    }

    public RelatedGoodsAdapter(List<FindDetailBean.DataBean.RelatedGoodsListBean> beans, Context context) {
        mBeans = beans;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_related_item,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        GlideApp.loderRoundImage(mContext,mBeans.get(i).getImg(),viewHolder.item_image);
        viewHolder.item_name.setText(mBeans.get(i).getGoodsName());
        viewHolder.item_buy.setOnClickListener(v -> {
            if(mOnItem != null){
                mOnItem.onItemClick(i);
            }
        });

        if (mBeans.get(i).getStatus().equals("1")){
            //上架
            viewHolder.item_overdue.setVisibility(View.GONE);
            viewHolder.item_promalContainer.setVisibility(View.VISIBLE);
            viewHolder.item_price.setVisibility(View.VISIBLE);
            viewHolder.item_price.setText("¥" + mBeans.get(i).getActualPrice());
            if(mBeans.get(i).getSource() == 1){
                viewHolder.item_promal.setText("京东价");
            }else  if(mBeans.get(i).getSource() == 2){
                viewHolder.item_promal.setText("淘宝价");
            }else  if(mBeans.get(i).getSource() == 3){
                viewHolder.item_promal.setText("天猫价");
            }
            viewHolder.item_priceOld.setText("¥" + mBeans.get(i).getOtherPrice());
            viewHolder.item_priceOld.setTv(true);
            viewHolder.item_priceOld.setColor(R.color.color_ACACAC);
        }else if(mBeans.get(i).getStatus().equals("-1")){
            //优惠券过期
            viewHolder.item_overdue.setVisibility(View.VISIBLE);
            viewHolder.item_promalContainer.setVisibility(View.GONE);
            viewHolder.item_price.setVisibility(View.GONE);
            viewHolder.item_overdue.setText("¥" + mBeans.get(i).getOtherPrice());
        }
    }

    @Override
    public int getItemCount() {
        return mBeans.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView item_image;
        private TextView item_name;
        private TextView item_promal;
        private TextViewDel item_priceOld;
        private TextView item_price;
        private TextView item_buy;
        private LinearLayout item_promalContainer;
        private TextView item_overdue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_image = itemView.findViewById(R.id.item_image);
            item_name = itemView.findViewById(R.id.item_name);
            item_promal = itemView.findViewById(R.id.item_promal);
            item_priceOld = itemView.findViewById(R.id.item_priceOld);
            item_price = itemView.findViewById(R.id.item_price);
            item_buy = itemView.findViewById(R.id.item_buy);
            item_promalContainer = itemView.findViewById(R.id.item_promalContainer);
            item_overdue = itemView.findViewById(R.id.item_overdue);
        }
    }

    public interface OnItem{
        void onItemClick(int position);
    }
}
