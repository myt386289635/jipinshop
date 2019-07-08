package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.TopCategoryDetailBean;
import com.example.administrator.jipinshop.databinding.ItemArticleBinding;

import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/7/2
 * @Describe 小分类榜单——评测推荐列表更多
 */
public class ArticleMoreAdapter extends RecyclerView.Adapter<ArticleMoreAdapter.ViewHolder> {

    private Context mContext;
    private List<TopCategoryDetailBean.DataBean.RelatedArticleListBean> mList;
    private OnClickView mView;

    public void setView(OnClickView view) {
        mView = view;
    }

    public ArticleMoreAdapter(Context context, List<TopCategoryDetailBean.DataBean.RelatedArticleListBean> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemArticleBinding articleBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_article,viewGroup,false);
        ViewHolder holder = new ViewHolder(articleBinding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        viewHolder.articleBinding.itemTitleContainer.setVisibility(View.GONE);
        viewHolder.itemView.setOnClickListener(v -> {
            if (mView != null){
                mView.onClickArticle(position);
            }
        });
        viewHolder.articleBinding.setDate(mList.get(position));
        viewHolder.articleBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ItemArticleBinding articleBinding;

        public ViewHolder(@NonNull ItemArticleBinding articleBinding) {
            super(articleBinding.getRoot());
            this.articleBinding = articleBinding;
        }
    }

    public interface OnClickView{
        void onClickArticle(int position);//评测推荐
    }
}
