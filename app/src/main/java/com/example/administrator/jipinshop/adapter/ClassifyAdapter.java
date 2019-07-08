package com.example.administrator.jipinshop.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.bean.TopCategoryDetailBean;
import com.example.administrator.jipinshop.databinding.ItemArticleBinding;
import com.example.administrator.jipinshop.databinding.ItemClassifyBinding;
import com.example.administrator.jipinshop.databinding.ItemGoodsBinding;
import com.example.administrator.jipinshop.databinding.ItemPageListBinding;
import com.example.administrator.jipinshop.util.snap.GravitySnapHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 莫小婷
 * @create 2019/7/1
 * @Describe 小分类榜单
 */
public class ClassifyAdapter extends RecyclerView.Adapter {

    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int FOUR = 4;

    private Context mContext;
    private List<TopCategoryDetailBean.DataBean.RelatedGoodsListBean> mGoodsString;//商品列表数据
    private List<TopCategoryDetailBean.DataBean.RelatedItemCategotyListBean> mPageList;//各种page榜单
    private List<TopCategoryDetailBean.DataBean.RelatedArticleListBean> mArticleList;//评测推荐列表
    private List<String> relatedQuestionList;
    private List<String> relatedPediaList;
    private OnClickView mView;
    private String title = "";

    public void setRelatedQuestionList(List<String> relatedQuestionList) {
        this.relatedQuestionList = relatedQuestionList;
    }

    public void setRelatedPediaList(List<String> relatedPediaList) {
        this.relatedPediaList = relatedPediaList;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setView(OnClickView view) {
        mView = view;
    }

    public ClassifyAdapter(Context context) {
        mContext = context;
    }

    public void setGoodsString(List<TopCategoryDetailBean.DataBean.RelatedGoodsListBean> goodsString) {
        mGoodsString = goodsString;
    }

    public void setPageList(List<TopCategoryDetailBean.DataBean.RelatedItemCategotyListBean> pageList) {
        mPageList = pageList;
    }

    public void setArticleList(List<TopCategoryDetailBean.DataBean.RelatedArticleListBean> articleList) {
        mArticleList = articleList;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mGoodsString.size()){
            return ONE;
        }else if (position == mGoodsString.size() || position == mGoodsString.size() + 1){
            return TWO;
        }else if (position - (mGoodsString.size() + 2) < mPageList.size()){
            return THREE;
        }else if (position - (mGoodsString.size() + 2 + mPageList.size()) < mArticleList.size()){
            return FOUR;
        }else {
            return  -1;//未知布局
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        RecyclerView.ViewHolder holder = null;
        switch (type){
            case ONE:
                ItemGoodsBinding goodsBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_goods,viewGroup,false);
                holder = new OneViewHolder(goodsBinding);
                break;
            case TWO:
                ItemClassifyBinding classifyBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_classify,viewGroup,false);
                holder = new TwoViewHolder(classifyBinding);
                break;
            case THREE:
                ItemPageListBinding pageListBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_page_list,viewGroup,false);
                holder = new ThreeViewHolder(pageListBinding);
                break;
            case FOUR:
                ItemArticleBinding articleBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.item_article,viewGroup,false);
                holder = new FourViewHolder(articleBinding);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        int type = getItemViewType(position);
        switch (type){
            case ONE:
                OneViewHolder oneViewHolder = (OneViewHolder) viewHolder;
                int onePosition = position;
                oneViewHolder.itemView.setOnClickListener(v ->{
                    if (mView != null){
                        mView.onClickGoods(onePosition);
                    }
                });
                oneViewHolder.goodsBinding.setDate(mGoodsString.get(onePosition));
                oneViewHolder.goodsBinding.setPosition(onePosition + 1);
                oneViewHolder.goodsBinding.executePendingBindings();
                break;
            case TWO:// TODO: 2019/7/3 未完成
                TwoViewHolder twoViewHolder = (TwoViewHolder) viewHolder;
                int twoPosition = position - mGoodsString.size();
                if (twoPosition == 0){
                    twoViewHolder.classifyBinding.itemContainer.setBackgroundResource(R.drawable.bg_classfiy_one);
                    twoViewHolder.classifyBinding.itemTitle.setTextColor(mContext.getResources().getColor(R.color.color_white));
                    twoViewHolder.classifyBinding.itemTitle.setText(title + "百科");
                    twoViewHolder.classifyBinding.itemMore.setVisibility(View.VISIBLE);
                    twoViewHolder.classifyBinding.itemAction.setVisibility(View.GONE);
                    twoViewHolder.classifyBinding.itemTextContent1.setTextColor(mContext.getResources().getColor(R.color.color_white));
                    twoViewHolder.classifyBinding.itemTextContent2.setTextColor(mContext.getResources().getColor(R.color.color_white));
                    twoViewHolder.classifyBinding.itemTextContent3.setTextColor(mContext.getResources().getColor(R.color.color_white));
                    twoViewHolder.itemView.setOnClickListener(v ->{
                        if (mView != null){
                            mView.onEncyclopedias();
                        }
                    });
                    twoViewHolder.classifyBinding.setString(relatedPediaList);
                    twoViewHolder.classifyBinding.executePendingBindings();
                }else {
                    twoViewHolder.classifyBinding.itemContainer.setBackgroundResource(R.drawable.bg_classfiy_two);
                    twoViewHolder.classifyBinding.itemTitle.setTextColor(mContext.getResources().getColor(R.color.color_565252));
                    twoViewHolder.classifyBinding.itemTitle.setText("话题问答");
                    twoViewHolder.classifyBinding.itemMore.setVisibility(View.GONE);
                    twoViewHolder.classifyBinding.itemAction.setVisibility(View.VISIBLE);
                    twoViewHolder.classifyBinding.itemTextContent1.setTextColor(mContext.getResources().getColor(R.color.color_565252));
                    twoViewHolder.classifyBinding.itemTextContent2.setTextColor(mContext.getResources().getColor(R.color.color_565252));
                    twoViewHolder.classifyBinding.itemTextContent3.setTextColor(mContext.getResources().getColor(R.color.color_565252));
                    twoViewHolder.itemView.setOnClickListener(v -> {
                        if (mView != null){
                            mView.onQuestions();
                        }
                    });
                    twoViewHolder.classifyBinding.setString(relatedQuestionList);
                    twoViewHolder.classifyBinding.executePendingBindings();
                }
                break;
            case THREE:
                ThreeViewHolder threeViewHolder = (ThreeViewHolder) viewHolder;
                int threePosition = position - mGoodsString.size() - 2;
                threeViewHolder.mList.clear();
                threeViewHolder.mList.addAll(mPageList.get(threePosition).getGoodsList());
                threeViewHolder.mPagerAdapter.notifyDataSetChanged();
                threeViewHolder.pageListBinding.itemTitle.setText(mPageList.get(threePosition).getName());
                break;
            case FOUR:
                FourViewHolder fourViewHolder = (FourViewHolder) viewHolder;
                int fourPosition = position - mGoodsString.size() - 2 - mPageList.size();
                if (fourPosition == 0){
                    fourViewHolder.articleBinding.itemTitleContainer.setVisibility(View.VISIBLE);
                }else {
                    fourViewHolder.articleBinding.itemTitleContainer.setVisibility(View.GONE);
                }
                fourViewHolder.articleBinding.itemMore.setOnClickListener(v ->{
                    if (mView != null){
                        mView.onMore();
                    }
                });
                fourViewHolder.articleBinding.itemContainer.setOnClickListener(v -> {
                    if (mView != null){
                        mView.onClickArticle(fourPosition);
                    }
                });
                fourViewHolder.articleBinding.setDate(mArticleList.get(fourPosition));
                fourViewHolder.articleBinding.executePendingBindings();
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (mGoodsString.size() != 0){
            return mGoodsString.size() + mPageList.size() + mArticleList.size() + 2;
        }else {
            return 0;
        }
    }

    class OneViewHolder extends RecyclerView.ViewHolder{

        private ItemGoodsBinding goodsBinding;

        public OneViewHolder(@NonNull ItemGoodsBinding goodsBinding) {
            super(goodsBinding.getRoot());
            this.goodsBinding = goodsBinding;
        }
    }

    class TwoViewHolder extends RecyclerView.ViewHolder{

        private ItemClassifyBinding classifyBinding;

        public TwoViewHolder(@NonNull ItemClassifyBinding classifyBinding) {
            super(classifyBinding.getRoot());
            this.classifyBinding = classifyBinding;
        }
    }

    class ThreeViewHolder extends RecyclerView.ViewHolder{

        private ItemPageListBinding pageListBinding;
        private ClassifyPagerAdapter mPagerAdapter;
        private List<TopCategoryDetailBean.DataBean.RelatedItemCategotyListBean.GoodsListBean> mList;

        public ThreeViewHolder(@NonNull ItemPageListBinding pageListBinding) {
            super(pageListBinding.getRoot());
            this.pageListBinding = pageListBinding;

            pageListBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL,false));
            mList = new ArrayList<>();
            mPagerAdapter = new ClassifyPagerAdapter(mContext,mList);
            GravitySnapHelper mySnapHelper = new GravitySnapHelper(Gravity.START);
            mySnapHelper.attachToRecyclerView(pageListBinding.recyclerView);
            pageListBinding.recyclerView.setAdapter(mPagerAdapter);
        }
    }

    class FourViewHolder extends RecyclerView.ViewHolder{

        private ItemArticleBinding articleBinding;

        public FourViewHolder(@NonNull ItemArticleBinding articleBinding) {
            super(articleBinding.getRoot());
            this.articleBinding = articleBinding;
        }
    }

    public interface OnClickView{
        void onClickGoods(int position);//点击商品
        void onEncyclopedias();//百科
        void onQuestions();//问答
        void onClickArticle(int position);//评测推荐
        void onMore();//点击评测更多
    }
}
