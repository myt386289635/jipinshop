package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.FovalBean;
import com.example.administrator.jipinshop.view.TextViewDel;
import com.example.administrator.jipinshop.view.glide.imageloder.ImageManager;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2018/8/6
 * @Describe
 */
public class FovalAdapter extends RecyclerView.Adapter<FovalAdapter.ViewHolder>{

    private List<FovalBean.ListBean> mList;
    private Context mContext;

    public FovalAdapter(List<FovalBean.ListBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_foval,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mList.get(position).getState().equals("1")) {
            //商品
            holder.item_tags.setVisibility(View.VISIBLE);
            holder.item_price.setVisibility(View.VISIBLE);
            holder.item_priceOld.setVisibility(View.VISIBLE);
            holder.item_resouce.setVisibility(View.VISIBLE);

            int count = (mList.get(position).getGoodsRanklist().getGoodstypeList().size() >= 4 ? 4 : mList.get(position).getGoodsRanklist().getGoodstypeList().size());
            if(count != 0){
                holder.item_tags.removeAllViews();
                for (int i = 0; i < count; i++) {
                    View itemTypeView = LayoutInflater.from(mContext).inflate(R.layout.item_tag, null);
                    TextView textView = itemTypeView.findViewById(R.id.histroy_item);
                    textView.setText(mList.get(position).getGoodsRanklist().getGoodstypeList().get(i).getName());
                    holder.item_tags.addView(itemTypeView);
                }
            }else {
                holder.item_tags.setVisibility(View.GONE);
            }
            holder.item_priceOld.setColor(R.color.color_ACACAC);
            holder.item_priceOld.setTv(true);
            ImageManager.displayRoundImage(mList.get(position).getGoodsRanklist().getRankGoodImg(),holder.item_image,0,0,10);
            holder.item_name.setText(mList.get(position).getGoodsRanklist().getGoodsName());
            if(mList.get(position).getGoodsRanklist().getSourceStatus() == 1){
                holder.item_resouce.setText("京东：");
            }else  if(mList.get(position).getGoodsRanklist().getSourceStatus() == 2){
                holder.item_resouce.setText("淘宝：");
            }else  if(mList.get(position).getGoodsRanklist().getSourceStatus() == 3){
                holder.item_resouce.setText("天猫：");
            }
            holder.item_priceOld.setText("¥" + mList.get(position).getGoodsRanklist().getOtherPrice());
            holder.item_price.setText("¥" +mList.get(position).getGoodsRanklist().getActualPrice());
            holder.item_lookNum.setText(mList.get(position).getGoodsRanklist().getVisitCount());
        }else if (mList.get(position).getState().equals("2")) {
            //评测
            holder.item_tags.setVisibility(View.GONE);
            holder.item_price.setVisibility(View.GONE);
            holder.item_priceOld.setVisibility(View.GONE);
            holder.item_resouce.setVisibility(View.GONE);

            holder.item_name.setText(mList.get(position).getGoodsEvalway().getEvalWayName());
            holder.item_lookNum.setText(mList.get(position).getGoodsEvalway().getVisitCount());
            ImageManager.displayRoundImage(mList.get(position).getGoodsEvalway().getImgId(),holder.item_image,0,0,10);
        }else {
            //发现
            holder.item_tags.setVisibility(View.GONE);
            holder.item_price.setVisibility(View.GONE);
            holder.item_priceOld.setVisibility(View.GONE);
            holder.item_resouce.setVisibility(View.GONE);

            holder.item_name.setText(mList.get(position).getGoodsEvalway().getEvalWayName());
            holder.item_lookNum.setText(mList.get(position).getGoodsEvalway().getVisitCount());
            ImageManager.displayRoundImage(mList.get(position).getGoodsEvalway().getImgId(),holder.item_image,0,0,10);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends  RecyclerView.ViewHolder{

        private ImageView item_image;
        private TextView item_name;
        private FlexboxLayout item_tags;
        private TextView item_price;
        private TextViewDel item_priceOld;
        private TextView item_lookNum;
        private TextView item_resouce;

        public ViewHolder(View itemView) {
            super(itemView);
            item_image = itemView.findViewById(R.id.item_image);
            item_name = itemView.findViewById(R.id.item_name);
            item_tags = itemView.findViewById(R.id.item_tags);
            item_price = itemView.findViewById(R.id.item_price);
            item_priceOld = itemView.findViewById(R.id.item_priceOld);
            item_lookNum = itemView.findViewById(R.id.item_lookNum);
            item_resouce = itemView.findViewById(R.id.item_price_oldText);
        }
    }
}
