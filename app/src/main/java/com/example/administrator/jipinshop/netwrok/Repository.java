package com.example.administrator.jipinshop.netwrok;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.bean.AccountBean;
import com.example.administrator.jipinshop.bean.FollowBean;
import com.example.administrator.jipinshop.bean.FovalBean;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.LoginBean;
import com.example.administrator.jipinshop.bean.LuckImageBean;
import com.example.administrator.jipinshop.bean.LuckselectBean;
import com.example.administrator.jipinshop.bean.RecommendFragmentBean;
import com.example.administrator.jipinshop.bean.RecordBean;
import com.example.administrator.jipinshop.bean.SignBean;
import com.example.administrator.jipinshop.bean.SignInsertBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.SupplementBean;
import com.example.administrator.jipinshop.bean.TabBean;
import com.example.administrator.jipinshop.bean.UserInfoBean;
import com.example.administrator.jipinshop.bean.json.LoginJson;
import com.example.administrator.jipinshop.bean.json.PushMessageJson;
import com.example.administrator.jipinshop.util.sp.CommonDate;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

public class Repository {

    @Inject
    APIService mAPIService;

    @Inject
    public Repository() {
    }

    /**
     * 榜单首页接口
     */
    public Observable<RecommendFragmentBean> ranklist() {
        return mAPIService.ranklist();
    }

    /**
     * 登陆接口
     */
    public Observable<LoginBean> login(LoginJson loginJson){
        return mAPIService.login(loginJson);
    }

    /**
     * 获取短信验证码
     */
    public Observable<SuccessBean> pushMessage( PushMessageJson pushMessageJson){
        return  mAPIService.pushMessage(pushMessageJson);
    }

    /**
     * 退出登陆
     */
    public Observable<SuccessBean> logout(){
        return mAPIService.logout(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId));
    }

    /**
     * 用户信息修改
     */
    public Observable<SuccessBean> userUpdate(Map<String,String> param){
        return mAPIService.userUpdate(param);
    }

    /**
     * 获取佣金金额
     */
    public Observable<AccountBean> account(){
        return mAPIService.account(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId));
    }

    /**
     * 获取用户信息
     */
    public Observable<UserInfoBean> modelUser(){
        return mAPIService.modelUser(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId));
    }

    /**
     * 关注列表
     */
    public Observable<FollowBean> concer(String page){
        return mAPIService.concer(page,SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId));
    }

    /**
     * 收藏列表
     */
    public Observable<FovalBean> collect(String page){
        return mAPIService.collect(page,SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId));
    }

    /**
     * 查询签到7天状态
     */
   public Observable<SignBean> sign(){
        return mAPIService.sign(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId));
    }

    /**
     * 签到
     */
    public Observable<SignInsertBean> signInsert(){
        return mAPIService.signInsert(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId));
    }

    /**
     * 提现
     */
    public Observable<SuccessBean> lipay(String phone,String amount, String real_name){
        return mAPIService.lipay(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId),phone,amount,real_name);
    }

    /**
     * 提现记录
     */
    public Observable<RecordBean> alipay(){
        return mAPIService.alipay(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId));
    }

    /**
     * 我要反馈
     */
    public Observable<SuccessBean> feedBack(String content){
        return mAPIService.feedBack(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId),content);
    }

    /**
     * 取消关注
     */
    public  Observable<SuccessBean> concerDelete(String concerId){
        return mAPIService.concerDelete(concerId);
    }

    /**
     * 一键补签
     */
    public Observable<SupplementBean> Supplement(){
        return mAPIService.Supplement(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId));
    }

    /**
     * 关注
     */
    public Observable<SuccessBean> concerInsert(String attentionUserId){
        return mAPIService.concerInsert(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId),attentionUserId);
    }

    /**
     * 抽奖结果
     */
    public Observable<LuckselectBean> luckselect(){
        return mAPIService.luckselect(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId));
    }

    /**
     * 上传图片
     */
    public Observable<ImageBean> importCustomer(MultipartBody.Part importFile){
        return mAPIService.importCustomer(importFile);
    }

    /**
     * 榜单tab的字段
     */
    public Observable<TabBean> goodsCategory(){
        return mAPIService.goodsCategory();
    }

    /**
     * 获取抽奖图片
     */
    public  Observable<LuckImageBean> luckselects(){
        return mAPIService.luckselects();
    }
}
