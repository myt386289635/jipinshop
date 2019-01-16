package com.example.administrator.jipinshop.netwrok;

import com.blankj.utilcode.util.SPUtils;
import com.example.administrator.jipinshop.bean.AccountBean;
import com.example.administrator.jipinshop.bean.AppVersionbean;
import com.example.administrator.jipinshop.bean.CommentBean;
import com.example.administrator.jipinshop.bean.EvaluationListBean;
import com.example.administrator.jipinshop.bean.EvaluationTabBean;
import com.example.administrator.jipinshop.bean.FindDetailBean;
import com.example.administrator.jipinshop.bean.FindListBean;
import com.example.administrator.jipinshop.bean.FollowBean;
import com.example.administrator.jipinshop.bean.FovalBean;
import com.example.administrator.jipinshop.bean.HomeCommenBean;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.IntegralShopBean;
import com.example.administrator.jipinshop.bean.LoginBean;
import com.example.administrator.jipinshop.bean.LuckImageBean;
import com.example.administrator.jipinshop.bean.LuckselectBean;
import com.example.administrator.jipinshop.bean.MemberLevelBean;
import com.example.administrator.jipinshop.bean.PagerStateBean;
import com.example.administrator.jipinshop.bean.PointDetailBean;
import com.example.administrator.jipinshop.bean.RecommendFragmentBean;
import com.example.administrator.jipinshop.bean.RecordBean;
import com.example.administrator.jipinshop.bean.ShoppingDetailBean;
import com.example.administrator.jipinshop.bean.SignBean;
import com.example.administrator.jipinshop.bean.SignInsertBean;
import com.example.administrator.jipinshop.bean.SreachHistoryBean;
import com.example.administrator.jipinshop.bean.SreachResultArticlesBean;
import com.example.administrator.jipinshop.bean.SreachResultGoodsBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.SupplementBean;
import com.example.administrator.jipinshop.bean.SystemMessageBean;
import com.example.administrator.jipinshop.bean.TabBean;
import com.example.administrator.jipinshop.bean.UnMessageBean;
import com.example.administrator.jipinshop.bean.UserInfoBean;
import com.example.administrator.jipinshop.bean.UserPageBean;
import com.example.administrator.jipinshop.bean.VoteBean;
import com.example.administrator.jipinshop.util.UpDataUtil;
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
    public Observable<LoginBean> login(String mobile, String code){
        return mAPIService.login(mobile,code);
    }

    /**
     * 获取短信验证码
     */
    public Observable<SuccessBean> pushMessage(String mobile,String type){
        return  mAPIService.pushMessage(mobile,type);
    }

    /**
     * 退出登陆
     */
    public Observable<SuccessBean> logout(){
        return mAPIService.logout();
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
        return mAPIService.modelUser();
    }

    /**
     * 关注列表
     */
    public Observable<FollowBean> concer(String page){
        return mAPIService.concer(page);
    }

    /**
     * 收藏列表(商品)
     */
    public Observable<SreachResultGoodsBean> collect(String page,String type){
        return mAPIService.collect(page,type);
    }

    /**
     * 收藏列表（发现、评测、试用报告）
     */
    public Observable<SreachResultArticlesBean> collectArticle(String page,String type){
        return mAPIService.collectArticle(page,type);
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
        return mAPIService.feedBack(content);
    }

    /**
     * 取消关注
     */
    public  Observable<SuccessBean> concernDelete(String attentionUserId){
        return mAPIService.concernDelete(attentionUserId);
    }

    /**
     * 一键补签
     */
    public Observable<SupplementBean> Supplement(){
        return mAPIService.Supplement(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId));
    }

    /**
     * 添加关注
     */
    public Observable<SuccessBean> concernInsert(String attentionUserId){
        return mAPIService.concernInsert(attentionUserId);
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
        return mAPIService.luckselects(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId));
    }

    /**
     *  榜单二级菜单列表
     */
    public Observable<HomeCommenBean> goodRank(String goodsId){
        return mAPIService.goodRank(goodsId);
    }

    /**
     * 搜索列表
     */
    public Observable<SreachResultGoodsBean> searchGoods(String page ,String type, String goodsName){
        return mAPIService.searchGoods(page,goodsName,type);
    }

    /**
     * 搜索列表
     */
    public Observable<SreachResultArticlesBean> searchArticles(String page , String type, String goodsName){
        return mAPIService.searchArticles(page,goodsName,type);
    }

    /**
     * 商品详情
     */
    public Observable<ShoppingDetailBean> goodsRankDetailList(String goodsId){
        return mAPIService.goodsRankDetailList(goodsId);
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
     * 添加点赞
     */
    public Observable<VoteBean> snapInsert(Map<String,String> param){
        return mAPIService.snapInsert(param);
    }

    /**
     * 删除点赞
     */
    public Observable<VoteBean> snapDelete(Map<String,String> param){
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
    public Observable<SuccessBean> commentInsert(String targetId,String toUserId, String content, String parentId,String type){
        return mAPIService.commentInsert(targetId,toUserId,content,parentId,type);
    }

    /**
     * 获取未读消息
     */
    public Observable<UnMessageBean> unMessage(){
        return mAPIService.unMessage();
    }

    /**
     * 获取消息列表详情内容
     */
    public Observable<SystemMessageBean> messageAll(String page,String type){
        return mAPIService.messageAll(page,type);
    }

    /**
     * 查看未读消息
     */
    public Observable<SuccessBean> readMsg(String messageId){
        return mAPIService.readMsg(messageId);
    }

    /**
     * 获取评测tab
     */
    public Observable<EvaluationTabBean> evaTab(){
        return mAPIService.evaTab();
    }

    /**
     * 获取评测列表
     */
    public Observable<EvaluationListBean> evaluationList(String categoryId ,String page){
        return mAPIService.evaluationList(categoryId,page);
    }

    /**
     * 获取发现tab
     */
    public Observable<EvaluationTabBean> findTab(){
        return mAPIService.findTab();
    }

    /**
     * 获取发现列表
     */
    public Observable<FindListBean> findLis(String categoryId,String page){
        return mAPIService.findLis(categoryId,page);
    }

    /**
     * 文章详情
     */
    public Observable<FindDetailBean> findDetail(String findgoodsId,String type){
        return mAPIService.findDetail(findgoodsId,type,"1");//客户端(1安卓，2苹果端，3小程序)
    }

    /**
     * 个人主页
     */
    public Observable<UserPageBean> userPage(String attentionUserId,String page){
        return  mAPIService.userPage(attentionUserId, page);
    }

    /**
     * 积分明细
     */
    public Observable<PointDetailBean> pointDetail(){
        return mAPIService.pointDetail(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.userId));
    }

    /**
     * 积分商城
     */
    public Observable<IntegralShopBean> integralShopList(String page){
        return mAPIService.integralShopList(page);
    }

    /**
     * 版本更新
     */
    public Observable<AppVersionbean> getAppVersion(){
        return mAPIService.getAppVersion("1", UpDataUtil.getPackageVersionCode() + "");
    }

    /**
     * 页面检测
     */
    public Observable<PagerStateBean> pagerState(String type,String targetId){
        return mAPIService.pagerState(type,targetId);
    }

    /**
     * 搜索记录
     */
    public Observable<SreachHistoryBean> searchLog(){
        return mAPIService.searchLog();
    }

    /**
     * 删除所有搜索记录
     */
    public Observable<SuccessBean> searchDeleteAll(){
        return mAPIService.searchDeleteAll();
    }

    /**
     * 删除单条搜索记录
     */
    public Observable<SuccessBean> searchDelete(String id){
        return mAPIService.searchDelete(id);
    }

    /**
     * 获取粉丝列表
     */
    public Observable<FollowBean> fansList(String page){
        return mAPIService.fansList(page);
    }
}
