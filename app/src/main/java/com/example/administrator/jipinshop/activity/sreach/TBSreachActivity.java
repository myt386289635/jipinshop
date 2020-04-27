package com.example.administrator.jipinshop.activity.sreach;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.WebActivity;
import com.example.administrator.jipinshop.activity.login.LoginActivity;
import com.example.administrator.jipinshop.activity.share.ShareActivity;
import com.example.administrator.jipinshop.activity.sreach.result.TBSreachResultActivity;
import com.example.administrator.jipinshop.adapter.KTTabAdapter4;
import com.example.administrator.jipinshop.adapter.ShoppingUserLikeAdapter;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.SimilerGoodsBean;
import com.example.administrator.jipinshop.bean.SreachHistoryBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.eventbus.SreachBus;
import com.example.administrator.jipinshop.databinding.ActivityTbSreachBinding;
import com.example.administrator.jipinshop.netwrok.RetrofitModule;
import com.example.administrator.jipinshop.util.DeviceUuidFactory;
import com.example.administrator.jipinshop.util.TaoBaoUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.DialogUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.trello.rxlifecycle2.android.ActivityEvent;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author 莫小婷
 * @create 2019/11/30
 * @Describe 淘宝搜索页面
 */
public class TBSreachActivity extends BaseActivity implements View.OnClickListener, TBSreachView, ShoppingUserLikeAdapter.OnItem, KTTabAdapter4.OnItem {

    @Inject
    TBSreachPresenter mPresenter;

    private ActivityTbSreachBinding mBinding;
    private Dialog mDialog;
    private List<SreachHistoryBean.DataBean.HotWordListBean> mHotText;
    private List<SreachHistoryBean.DataBean.LogListBean> mHistroyList;
    private boolean speach = false;//是否展开 默认不展开
    //猜你喜欢
    private List<SimilerGoodsBean.DataBean> mUserLikeList;
    private ShoppingUserLikeAdapter mLikeAdapter;
    private int[] usableHeightPrevious = {0};
    private String type = "2";//默认搜索类型为淘宝
    //tab
    private KTTabAdapter4 mTabAdapter;
    private List<String> titles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_tb_sreach);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        if (!TextUtils.isEmpty(getIntent().getStringExtra("type"))){
            type = getIntent().getStringExtra("type");
        }
        showKeyboardDelayed(mBinding.titleEdit);
        mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
        mDialog.show();
        mHotText = new ArrayList<>();
        mHistroyList = new ArrayList<>();
        //选择tab
        CommonNavigator commonNavigator = new CommonNavigator(this);
        titles = new ArrayList<>();
        titles.add("淘宝");
        titles.add("京东");
        titles.add("拼多多");
        mTabAdapter = new KTTabAdapter4(titles);
        mTabAdapter.setOnClick(this);
        mTabAdapter.setColor(getResources().getColor(R.color.color_202020),
                getResources().getColor(R.color.color_E25838));
        mTabAdapter.setTextSize(16f);
        commonNavigator.setAdapter(mTabAdapter);
        mBinding.titleContainer2.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer();
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable(){
            @Override
            public int getIntrinsicWidth() {
                return (int) getResources().getDimension(R.dimen.x130);
            }
        });
        if (type.equals("2")){ //淘宝
            mBinding.titleContainer2.onPageSelected(0);
            mBinding.titleContainer2.onPageScrolled(0, 0.0F, 0);
            mBinding.sreachULikeContainer.setVisibility(View.VISIBLE);
            mBinding.sreachProcess.setVisibility(View.VISIBLE);
        }else if (type.equals("1")){//京东
            mBinding.titleContainer2.onPageSelected(1);
            mBinding.titleContainer2.onPageScrolled(1, 0.0F, 0);
            mBinding.sreachULikeContainer.setVisibility(View.GONE);
            mBinding.sreachProcess.setVisibility(View.GONE);
        }else {//拼多多
            mBinding.titleContainer2.onPageSelected(2);
            mBinding.titleContainer2.onPageScrolled(2, 0.0F, 0);
            mBinding.sreachULikeContainer.setVisibility(View.GONE);
            mBinding.sreachProcess.setVisibility(View.GONE);
        }
        //猜你喜欢
        mBinding.sreachUserLike.setLayoutManager(new GridLayoutManager(this,2));
        mBinding.sreachUserLike.setNestedScrollingEnabled(false);
        mUserLikeList = new ArrayList<>();
        mLikeAdapter = new ShoppingUserLikeAdapter(mUserLikeList,this);
        mLikeAdapter.setOnItem(this);
        mBinding.sreachUserLike.setAdapter(mLikeAdapter);
        mBinding.swipeToLoad.setLoadMoreEnabled(false);
        //监听软键盘的弹出与收回
        mPresenter.setKeyListener(mBinding.detailContanier, usableHeightPrevious);

        mBinding.titleEdit.setOnEditorActionListener((textView, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                if (TextUtils.isEmpty(mBinding.titleEdit.getText().toString().trim())) {
                    ToastUtil.show("请输入搜索内容");
                    return false;
                }
                startActivity(new Intent(TBSreachActivity.this, TBSreachResultActivity.class)
                        .putExtra("content", mBinding.titleEdit.getText().toString())
                        .putExtra("type",type)
                );
            }
            return false;
        });
        mPresenter.searchLog("1",this.bindToLifecycle());
        if (type.equals("2")){
            Map<String,String> map =  DeviceUuidFactory.getIdfa(this);
            mPresenter.listSimilerGoods(map,this.bindToLifecycle());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back:
                finish();
                break;
            case R.id.sreach_historyMore:
                if (!speach){
                    //展开
                    if (mBinding.searchHistory.getFlexLines().size() > 8){
                        ViewGroup.LayoutParams layoutParams =  mBinding.searchHistory.getLayoutParams();
                        layoutParams.height = (int) getResources().getDimension(R.dimen.y720);
                        mBinding.searchHistory.setLayoutParams(layoutParams);
                    }else {
                        ViewGroup.LayoutParams layoutParams =  mBinding.searchHistory.getLayoutParams();
                        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        mBinding.searchHistory.setLayoutParams(layoutParams);
                    }
                    speach = true;
                    mBinding.sreachHistoryMore.setText("收起");
                    Drawable drawable= getResources().getDrawable(R.mipmap.right_up);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    drawable = DrawableCompat.wrap(drawable);
                    DrawableCompat.setTint(drawable, getResources().getColor(R.color.color_CACACA));
                    mBinding.sreachHistoryMore.setCompoundDrawables(null,null,drawable,null);
                }else {
                    //不展开
                    ViewGroup.LayoutParams layoutParams =  mBinding.searchHistory.getLayoutParams();
                    layoutParams.height = (int) getResources().getDimension(R.dimen.y270);
                    mBinding.searchHistory.setLayoutParams(layoutParams);
                    speach = false;
                    mBinding.sreachHistoryMore.setText("更多搜索历史");
                    Drawable drawable= getResources().getDrawable(R.mipmap.right_down);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    drawable = DrawableCompat.wrap(drawable);
                    DrawableCompat.setTint(drawable, getResources().getColor(R.color.color_CACACA));
                    mBinding.sreachHistoryMore.setCompoundDrawables(null,null,drawable,null);
                }
                break;
            case R.id.search_delete:
                DialogUtil.listingDetele(this, "确认删除全部历史记录？", "确定", "取消",false, v -> {
                    mDialog = (new ProgressDialogView()).createLoadingDialog(this, "正在加载...");
                    mDialog.show();
                    mPresenter.deleteAll(this.bindToLifecycle());
                });
                break;
            case R.id.sreach_value:
            case R.id.title_sreach:
                if (TextUtils.isEmpty(mBinding.titleEdit.getText().toString().trim())) {
                    ToastUtil.show("请输入搜索内容");
                    return;
                }
                startActivity(new Intent(TBSreachActivity.this, TBSreachResultActivity.class)
                        .putExtra("content", mBinding.titleEdit.getText().toString())
                        .putExtra("type",type)
                );
                break;
            case R.id.sreach_process:
                startActivity(new Intent(this, WebActivity.class)
                        .putExtra(WebActivity.url, RetrofitModule.H5_URL+"tbk-rule.html")
                        .putExtra(WebActivity.title,"极品城省钱攻略")
                        .putExtra(WebActivity.isShare,true)
                        .putExtra(WebActivity.shareTitle,"如何查找淘宝隐藏优惠券及下单返利？")
                        .putExtra(WebActivity.shareContent,"淘宝天猫90%的商品都能省，同时还有高额返利，淘好物，更省钱！")
                        .putExtra(WebActivity.shareImage,"https://jipincheng.cn/shengqian.png")
                );
                break;
        }
    }

    @Override
    public void jump(String content) {
        mBinding.titleEdit.setText(content);
        mBinding.titleEdit.setSelection(mBinding.titleEdit.getText().length());//将光标移至文字末尾
        startActivity(new Intent(TBSreachActivity.this, TBSreachResultActivity.class)
                .putExtra("content", mBinding.titleEdit.getText().toString())
                .putExtra("type",type)
        );
    }

    @Override
    public void Success(SreachHistoryBean sreachHistoryBean) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        speach = false;
        mHotText.addAll(sreachHistoryBean.getData().getHotWordList());
        mPresenter.initHot(this, mBinding.searchHot, mHotText);
        if (sreachHistoryBean.getData().getLogList().size() != 0){
            mBinding.sreachHisContainer.setVisibility(View.VISIBLE);
            mHistroyList.addAll(sreachHistoryBean.getData().getLogList());
            mPresenter.initHistroy(this,mBinding.searchHistory,mBinding.sreachHistoryMore,mHistroyList);
        }else {
            mBinding.sreachHisContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFaile(String error) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        ToastUtil.show(error);
    }

    @Override
    public void SuccessDeleteAll(SuccessBean successBean) {
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
        mBinding.sreachHisContainer.setVisibility(View.GONE);
        ToastUtil.show("清除成功");
    }

    @Override
    public void SuccessHistory(SreachHistoryBean sreachHistoryBean) {
        speach = false;
        if (sreachHistoryBean.getData().getLogList().size() != 0) {
            mBinding.sreachHisContainer.setVisibility(View.VISIBLE);
            mHistroyList.clear();
            mHistroyList.addAll(sreachHistoryBean.getData().getLogList());
            mPresenter.initHistroy(this,mBinding.searchHistory,mBinding.sreachHistoryMore,mHistroyList);
        }else {
            mBinding.sreachHisContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public void LikeSuccess(SimilerGoodsBean bean) {
        mUserLikeList.clear();
        mUserLikeList.addAll(bean.getData());
        mLikeAdapter.notifyDataSetChanged();
    }

    @Override
    public void keyShow() {
        mBinding.sreachBottomContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void keyHint() {
        mBinding.sreachBottomContainer.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void sreachHis(SreachBus sreachBus){
        if(sreachBus != null && sreachBus.getTag().equals(SreachActivity.sreachHistoryTag)){
            mPresenter.searchLog("2",this.bindUntilEvent(ActivityEvent.DESTROY));
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isHideInput(view, ev)) {
                showKeyboard(false);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isHideInput(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (ev.getX() > left && ev.getX() < right && ev.getY() > top
                    && ev.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onItemShare(int position) {
        if(TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token,""))){
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        TaoBaoUtil.openTB(this, () -> {
            startActivity(new Intent(TBSreachActivity.this, ShareActivity.class)
                    .putExtra("otherGoodsId",mUserLikeList.get(position).getOtherGoodsId())
            );
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
    }

    //tab点击的位置
    @Override
    public void onClick(int position) {
        mBinding.titleContainer2.onPageSelected(position);
        mBinding.titleContainer2.onPageScrolled(position, 0.0F, 0);
        if (position == 0){
            //搜淘宝
            type = "2";
            mBinding.sreachULikeContainer.setVisibility(View.VISIBLE);
            mBinding.sreachProcess.setVisibility(View.VISIBLE);
        }else if (position == 1){//京东
            type = "1";
            mBinding.sreachULikeContainer.setVisibility(View.GONE);
            mBinding.sreachProcess.setVisibility(View.GONE);
        }else {//拼多多
            type = "4";
            mBinding.sreachULikeContainer.setVisibility(View.GONE);
            mBinding.sreachProcess.setVisibility(View.GONE);
        }
    }
}
