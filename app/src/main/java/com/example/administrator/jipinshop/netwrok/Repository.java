package com.example.administrator.jipinshop.netwrok;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.bean.AccountBean;
import com.example.administrator.jipinshop.bean.CommentBean;
import com.example.administrator.jipinshop.bean.CommentInsertBean;
import com.example.administrator.jipinshop.bean.ElectricityFragmentBean;
import com.example.administrator.jipinshop.bean.FollowBean;
import com.example.administrator.jipinshop.bean.FovalBean;
import com.example.administrator.jipinshop.bean.HealthFragmentBean;
import com.example.administrator.jipinshop.bean.HouseholdFragmentBean;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.KitchenFragmentBean;
import com.example.administrator.jipinshop.bean.LoginBean;
import com.example.administrator.jipinshop.bean.LuckImageBean;
import com.example.administrator.jipinshop.bean.LuckselectBean;
import com.example.administrator.jipinshop.bean.RecommendFragmentBean;
import com.example.administrator.jipinshop.bean.RecordBean;
import com.example.administrator.jipinshop.bean.ShoppingDetailBean;
import com.example.administrator.jipinshop.bean.SignBean;
import com.example.administrator.jipinshop.bean.SignInsertBean;
import com.example.administrator.jipinshop.bean.SnapSelectBean;
import com.example.administrator.jipinshop.bean.SreachResultBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.SupplementBean;
import com.example.administrator.jipinshop.bean.SystemMessageBean;
import com.example.administrator.jipinshop.bean.TabBean;
import com.example.administrator.jipinshop.bean.UnMessageBean;
import com.example.administrator.jipinshop.bean.UserInfoBean;
import com.example.administrator.jipinshop.bean.json.LoginJson;
import com.example.administrator.jipinshop.bean.json.PushMessageJson;
import com.example.administrator.jipinshop.util.sp.CommonDate;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

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
    public Observable<LuckImageBean> luckselects(){
        return mAPIService.luckselects();
    }

    /**
     *  榜单二级菜单列表
     */
    public Observable<HealthFragmentBean> goodRank(String goodsId){
        return mAPIService.goodRank("0",goodsId);
    }

    /**
     * 榜单二级菜单列表
     */
    public Observable<KitchenFragmentBean> goodRank2(String goodsId){
        return mAPIService.goodRank2("0",goodsId);
    }

    /**
     * 榜单二级菜单列表
     */
    public Observable<HouseholdFragmentBean> goodRank3(String goodsId){
        return mAPIService.goodRank3("0",goodsId);
    }

    /**
     * 榜单二级菜单列表
     */
    public Observable<ElectricityFragmentBean> goodRank4(String goodsId){
        return mAPIService.goodRank4("0",goodsId);
    }

    /**
     * 搜索列表
     */
    public Observable<SreachResultBean> searchGoods(String goodsName){
        return mAPIService.searchGoods(goodsName);
    }

    /**
     * 商品详情
     */
    public Observable<ShoppingDetailBean> goodsRankDetailList(String goodsId){
        return mAPIService.goodsRankDetailList(goodsId);
    }

    /**
     * 判断用户是否收藏此文章或者商品
     */
    public Observable<SuccessBean> isCollect(Map<String,String> param){
        return  mAPIService.isCollect(param);
    }

    /**
     * 添加收藏
     */
    public Observable<SuccessBean> collectInsert(Map<String,String> param){
        return mAPIService.collectInsert(param);
    }

    /**
     * 删除收藏
     */
    public Observable<SuccessBean> collectDelete(Map<String,String> param){
        return mAPIService.collectDelete(param);
    }

    /**
     * 判断用户是否点赞此文章或者商品
     */
    public Observable<SnapSelectBean> snapSelect(Map<String,String> param){
        return mAPIService.snapSelect(param);
    }

    /**
     * 添加点赞
     */
    public Observable<SuccessBean> snapInsert(Map<String,String> param){
        return mAPIService.snapInsert(param);
    }

    /**
     * 删除点赞
     */
    public Observable<SuccessBean> snapDelete(Map<String,String> param){
        return  mAPIService.snapDelete(param);
    }

    /**
     * 查看评论列表
     */
    public Observable<CommentBean> comment(Map<String,String> param){
        return mAPIService.comment(param);
    }

    /**
     * 添加评论
     */
    public Observable<CommentInsertBean> commentInsert(String articId, String content, String parentId){
        return mAPIService.commentInsert(articId,SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId),
                content,parentId);
    }

    /**
     * 获取未读消息
     */
    public Observable<UnMessageBean> unMessage(){
        return mAPIService.unMessage(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId));
    }

    /**
     * 获取消息列表详情内容
     */
    public Observable<SystemMessageBean> messageAll(String page){
        return mAPIService.messageAll(page,SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId),"1");
    }
}
