package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.util.DistanceHelper;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/11/26
 * @Describe
 */
public class ShoppingImageAdapter extends RecyclerView.Adapter<ShoppingImageAdapter.ViewHolder> {

    private List<String> mDetailList;
    private Context mContext;

    public ShoppingImageAdapter(List<String> detailList, Context context) {
        mDetailList = detailList;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_image,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Glide.with(mContext)
                .load(mDetailList.get(position))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        double width = resource.getIntrinsicWidth();
                        double height = resource.getIntrinsicHeight();
                        double screenWidth = DistanceHelper.getAndroiodScreenwidthPixels(mContext);
                        RelativeLayout.LayoutParams layoutParams  = (RelativeLayout.LayoutParams) viewHolder.item_img.getLayoutParams();
                        layoutParams.width = (int) screenWidth;
                        layoutParams.height = (int) ((height / width ) * screenWidth);
                        viewHolder.item_img.setLayoutParams(layoutParams);
                        return false;
                    }
                })
                .into(viewHolder.item_img);
    }

    @Override
    public int getItemCount() {
        return mDetailList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView item_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_img = itemView.findViewById(R.id.item_img);
        }
    }
}
