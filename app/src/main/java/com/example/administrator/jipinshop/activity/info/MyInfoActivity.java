package com.example.administrator.jipinshop.activity.info;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.activity.info.account.AccountManageActivity;
import com.example.administrator.jipinshop.activity.info.editname.EditNameActivity;
import com.example.administrator.jipinshop.base.BaseActivity;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.eventbus.EditNameBus;
import com.example.administrator.jipinshop.databinding.ActivityMyinfoBinding;
import com.example.administrator.jipinshop.util.ImageCompressUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.example.administrator.jipinshop.util.WheelViewUtil;
import com.example.administrator.jipinshop.util.sp.CommonDate;
import com.example.administrator.jipinshop.view.dialog.DialogUtil;
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView;
import com.example.administrator.jipinshop.view.dialog.SelectPicDialog;
import com.example.administrator.jipinshop.view.glide.GlideApp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;

import javax.inject.Inject;

import static com.example.administrator.jipinshop.util.wheelview.TimePickerView.Type.YEAR_MONTH_DAY;

/**
 * @author 莫小婷
 * @create 2018/8/7
 * @Describe 我的资料页面
 */
public class MyInfoActivity extends BaseActivity implements SelectPicDialog.ChoosePhotoCallback, View.OnClickListener, MyInfoView {

    private SelectPicDialog mDialog;
    private ActivityMyinfoBinding mBinding;
    @Inject
    MyInfoPresenter mPresenter;
    //性别选择器  数据
    private ArrayList<String> wheelList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_myinfo);
        mBinding.setListener(this);
        mBaseActivityComponent.inject(this);
        mPresenter.setView(this);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        mBinding.inClude.titleTv.setText("我的资料");
        if(!TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickImg))){
            GlideApp.loderCircleImage(this,SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickImg),mBinding.infoImage,R.drawable.rlogo,R.drawable.rlogo);
        }else {
            GlideApp.loderImage(this,R.drawable.rlogo,mBinding.infoImage,R.drawable.rlogo,R.drawable.rlogo);
        }
        if(TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickName))){
            mBinding.infoName.setText(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userPhone));
        }else {
            mBinding.infoName.setText(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userNickName));
        }
        mBinding.infoBirth.setText(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userBirthday));
        mBinding.infoSex.setText(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userGender));
        if(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.bindMobile,"0").equals("1")){
            mBinding.infoNumberPhone.setImageResource(R.mipmap.phone_sel);
        }else {
            mBinding.infoNumberPhone.setImageResource(R.mipmap.phone_nor);
        }
        if(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.bindWeixin,"0").equals("1")){
            mBinding.infoNumberWechat.setImageResource(R.mipmap.wechat_sel);
        }else {
            mBinding.infoNumberWechat.setImageResource(R.mipmap.wechat_nor);
        }
        if(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.bindWeibo,"0").equals("1")){
            mBinding.infoNumberWeiBo.setImageResource(R.mipmap.weibo_sel);
        }else {
            mBinding.infoNumberWeiBo.setImageResource(R.mipmap.weibo_nor);
        }

        wheelList = new ArrayList<>();
        wheelList.add("女");
        wheelList.add("男");
    }

    /**
     * 上传照片
     * @param picFile
     */
    @Override
    public void getAbsolutePicPath(String picFile) {
        Dialog mDialog = (new ProgressDialogView()).createLoadingDialog(MyInfoActivity.this, "请求中...");
        mDialog.show();
        //这里进行了图片旋转以及图片压缩后得到新图片
        picFile = ImageCompressUtil.getimage(this,ImageCompressUtil.getPicture(this,picFile));
        mPresenter.importCustomer(this.bindToLifecycle(),mDialog,new File(picFile));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.info_imageContainer:
                if(mDialog == null){
                    mDialog = new SelectPicDialog();
                    mDialog.setChoosePhotoCallback(this);
                }
                if(!mDialog.isAdded()){
                    mDialog.show(getSupportFragmentManager(), SelectPicDialog.TAG);
                }
                break;
            case R.id.info_nameContainer:
                startActivity(new Intent(this,EditNameActivity.class)
                        .putExtra("title","昵称")
                        .putExtra("value",mBinding.infoName.getText().toString())
                );
                break;
            case R.id.info_sexContainer:
                WheelViewUtil.alertBottomWheelOption(this, wheelList, (view1, postion) -> {
                    Dialog mDialog = (new ProgressDialogView()).createLoadingDialog(MyInfoActivity.this, "请求中...");
                    mDialog.show();
                    mPresenter.SaveUserInfo("2", wheelList.get(postion), MyInfoActivity.this.bindToLifecycle(), mDialog);
                });
                break;
            case R.id.info_birthContainer:
                WheelViewUtil.alertTimerPicker(this,1900,mBinding.infoBirth.getText().toString(), YEAR_MONTH_DAY, "yyyy-MM-dd", (WheelViewUtil.TimerPickerCallBack) date -> {
                    Dialog mDialog = (new ProgressDialogView()).createLoadingDialog(MyInfoActivity.this, "请求中...");
                    mDialog.show();
                    mPresenter.SaveUserInfo("3", date, MyInfoActivity.this.bindToLifecycle(), mDialog);
                });
                break;
            case R.id.info_numberContainer:
                //跳转到绑定账号页面
                startActivity(new Intent(this, AccountManageActivity.class));
                break;
            case R.id.info_exitLogin:
                //退出登陆
                DialogUtil.LoginDialog(this, "您确定要退出登录吗？","确定","取消", v -> {
                    Dialog mDialog = (new ProgressDialogView()).createLoadingDialog(this, "退出登录...");
                    mDialog.show();
                    mPresenter.loginOut(this.<SuccessBean>bindToLifecycle(),mDialog);
                });
                break;
        }
    }

    /**
     * 退出登陆
     * @param msg
     */
    @Override
    public void loginOutSuccess(SuccessBean msg) {
        if(msg.getCode() == 0){
            setResult(201);
            finish();
            ToastUtil.show("退出登录成功");
        }else {
            ToastUtil.show(msg.getMsg());
        }
    }

    /**
     * 修改生日
     * @param successBean
     */
    @Override
    public void EditBirthDaySuccess(SuccessBean successBean,String date) {
        if (successBean.getCode() == 0) {
            mBinding.infoBirth.setText(date);
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userBirthday, mBinding.infoBirth.getText().toString());
            ToastUtil.show( "修改成功");
        } else {
            ToastUtil.show(successBean.getMsg());
        }
    }

    /**
     * 修改性别
     * @param successBean
     */
    @Override
    public void EditGenderSuccess(SuccessBean successBean,String date) {
        if (successBean.getCode() == 0) {
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userGender, date);
            mBinding.infoSex.setText(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userGender));
            ToastUtil.show( "修改成功");
        } else {
            ToastUtil.show(successBean.getMsg());
        }
    }

    /**
     * 修改头像
     */
    @Override
    public void EditUserNickImgSuc(SuccessBean successBean, String date) {
        if (successBean.getCode() == 0) {
            GlideApp.loderCircleImage(this,date,mBinding.infoImage,R.mipmap.rlogo,0);
            SPUtils.getInstance(CommonDate.USER).put(CommonDate.userNickImg, date);
            EventBus.getDefault().post(new EditNameBus(EditNameActivity.tag,date,"4"));
            ToastUtil.show("修改成功");
        } else {
            ToastUtil.show(successBean.getMsg());
        }
    }

    /**
     * 上传图片回调成功
     */
    @Override
    public void uploadPicSuccess(Dialog mDialog, String picPath) {
        mPresenter.SaveUserInfo("4", picPath, MyInfoActivity.this.bindToLifecycle(), mDialog);
    }

    /**
     * 上传图片回调失败
     */
    @Override
    public void uploadPicFailed(String error) {
        ToastUtil.show(error);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void eidtInfo(EditNameBus bus){
        if(bus.getTag().equals(EditNameActivity.tag)){
            if(bus.getType().equals("1")){//修改姓名
                mBinding.infoName.setText(bus.getContent());
            }
        }else if(bus.getTag().equals(AccountManageActivity.tag)){
            //更新账号绑定
            if(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.bindWeixin,"0").equals("1")){
                mBinding.infoNumberWechat.setImageResource(R.mipmap.wechat_sel);
            }else {
                mBinding.infoNumberWechat.setImageResource(R.mipmap.wechat_nor);
            }
            if(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.bindWeibo,"0").equals("1")){
                mBinding.infoNumberWeiBo.setImageResource(R.mipmap.weibo_sel);
            }else {
                mBinding.infoNumberWeiBo.setImageResource(R.mipmap.weibo_nor);
            }
        }
    }
}
