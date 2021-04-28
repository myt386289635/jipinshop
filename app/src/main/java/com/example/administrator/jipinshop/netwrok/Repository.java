package com.example.administrator.jipinshop.netwrok;

import com.example.administrator.jipinshop.bean.ActionHBBean;
import com.example.administrator.jipinshop.bean.AddressBean;
import com.example.administrator.jipinshop.bean.AllowanceRecordBean;
import com.example.administrator.jipinshop.bean.AppVersionbean;
import com.example.administrator.jipinshop.bean.BudgetDetailBean;
import com.example.administrator.jipinshop.bean.CircleListBean;
import com.example.administrator.jipinshop.bean.CircleTitleBean;
import com.example.administrator.jipinshop.bean.ClickUrlBean;
import com.example.administrator.jipinshop.bean.CommenBean;
import com.example.administrator.jipinshop.bean.CommentBean;
import com.example.administrator.jipinshop.bean.CommssionDetailBean;
import com.example.administrator.jipinshop.bean.DailyTaskBean;
import com.example.administrator.jipinshop.bean.DefaultAddressBean;
import com.example.administrator.jipinshop.bean.EvaAttentBean;
import com.example.administrator.jipinshop.bean.EvaEvaBean;
import com.example.administrator.jipinshop.bean.EvaHotBean;
import com.example.administrator.jipinshop.bean.EvaluationListBean;
import com.example.administrator.jipinshop.bean.EvaluationTabBean;
import com.example.administrator.jipinshop.bean.FamilyBean;
import com.example.administrator.jipinshop.bean.FansBean;
import com.example.administrator.jipinshop.bean.FindDetailBean;
import com.example.administrator.jipinshop.bean.FindListBean;
import com.example.administrator.jipinshop.bean.FollowBean;
import com.example.administrator.jipinshop.bean.GroupInfoBean;
import com.example.administrator.jipinshop.bean.HomeCommenBean;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.InvitationBean;
import com.example.administrator.jipinshop.bean.JDBean;
import com.example.administrator.jipinshop.bean.LoginBean;
import com.example.administrator.jipinshop.bean.MallBean;
import com.example.administrator.jipinshop.bean.MallDetailBean;
import com.example.administrator.jipinshop.bean.MemberBuyBean;
import com.example.administrator.jipinshop.bean.MemberNewBean;
import com.example.administrator.jipinshop.bean.MessageAllBean;
import com.example.administrator.jipinshop.bean.MessageBean;
import com.example.administrator.jipinshop.bean.MoneyRecordBean;
import com.example.administrator.jipinshop.bean.MyFreeBean;
import com.example.administrator.jipinshop.bean.MyOrderBean;
import com.example.administrator.jipinshop.bean.MyWalletBean;
import com.example.administrator.jipinshop.bean.NewFreeBean;
import com.example.administrator.jipinshop.bean.NewPeopleBean;
import com.example.administrator.jipinshop.bean.OrderTBBean;
import com.example.administrator.jipinshop.bean.OrderbyTypeBean;
import com.example.administrator.jipinshop.bean.PagerStateBean;
import com.example.administrator.jipinshop.bean.PlayBean;
import com.example.administrator.jipinshop.bean.PointDetailBean;
import com.example.administrator.jipinshop.bean.PopBean;
import com.example.administrator.jipinshop.bean.PopInfoBean;
import com.example.administrator.jipinshop.bean.PrizeLogBean;
import com.example.administrator.jipinshop.bean.QuestionsBean;
import com.example.administrator.jipinshop.bean.RecommendFragmentBean;
import com.example.administrator.jipinshop.bean.ReportBean;
import com.example.administrator.jipinshop.bean.SchoolHomeBean;
import com.example.administrator.jipinshop.bean.ScoreStatusBean;
import com.example.administrator.jipinshop.bean.SeckillBean;
import com.example.administrator.jipinshop.bean.SeckillTabBean;
import com.example.administrator.jipinshop.bean.ShareBean;
import com.example.administrator.jipinshop.bean.ShareInfoBean;
import com.example.administrator.jipinshop.bean.ShoppingDetailBean;
import com.example.administrator.jipinshop.bean.SignInsertBean;
import com.example.administrator.jipinshop.bean.SimilerGoodsBean;
import com.example.administrator.jipinshop.bean.SreachBean;
import com.example.administrator.jipinshop.bean.SreachHistoryBean;
import com.example.administrator.jipinshop.bean.SreachResultArticlesBean;
import com.example.administrator.jipinshop.bean.SreachResultGoodsBean;
import com.example.administrator.jipinshop.bean.SubUserBean;
import com.example.administrator.jipinshop.bean.SucBean;
import com.example.administrator.jipinshop.bean.SucBeanT;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.TBShoppingDetailBean;
import com.example.administrator.jipinshop.bean.TBSreachResultBean;
import com.example.administrator.jipinshop.bean.TabBean;
import com.example.administrator.jipinshop.bean.TaobaoAccountBean;
import com.example.administrator.jipinshop.bean.TaskFinishBean;
import com.example.administrator.jipinshop.bean.TbCommonBean;
import com.example.administrator.jipinshop.bean.TbOrderBean;
import com.example.administrator.jipinshop.bean.TbkIndexBean;
import com.example.administrator.jipinshop.bean.TeacherBean;
import com.example.administrator.jipinshop.bean.TeamBean;
import com.example.administrator.jipinshop.bean.TklBean;
import com.example.administrator.jipinshop.bean.TopCategoryDetailBean;
import com.example.administrator.jipinshop.bean.TopCategorysListBean;
import com.example.administrator.jipinshop.bean.UnMessageBean;
import com.example.administrator.jipinshop.bean.UserInfoBean;
import com.example.administrator.jipinshop.bean.VideoBean;
import com.example.administrator.jipinshop.bean.VoteBean;
import com.example.administrator.jipinshop.bean.WalletHistoryBean;
import com.example.administrator.jipinshop.bean.WelfareBean;
import com.example.administrator.jipinshop.bean.WithdrawBean;
import com.example.administrator.jipinshop.bean.WithdrawDetailBean;
import com.example.administrator.jipinshop.bean.WithdrawInfoBean;
import com.example.administrator.jipinshop.bean.WxPayBean;
import com.example.administrator.jipinshop.util.UpDataUtil;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;

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
    public Observable<LoginBean> login(String mobile, String code, String invitationCode){
        return mAPIService.login(mobile,code,invitationCode);
    }

    /**
     * 获取短信验证码
     */
    public Observable<SuccessBean> pushMessage(String mobile, String type, String ticket, String randstr){
        return  mAPIService.pushMessage(mobile,type,ticket,randstr);
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
     * 签到
     */
    public Observable<SignInsertBean> signInsert(){
        return mAPIService.signInsert();
    }

    /**
     * 补签
     */
    public Observable<SignInsertBean> noSignin(int day){
        return mAPIService.noSignin(day);
    }

    /**
     * 我要反馈
     */
    public Observable<SuccessBean> feedBack(String content, String type){
        return mAPIService.feedBack(content,type);
    }

    /**
     * 取消关注
     */
    public  Observable<SuccessBean> concernDelete(String attentionUserId){
        return mAPIService.concernDelete(attentionUserId);
    }

    /**
     * 添加关注
     */
    public Observable<SuccessBean> concernInsert(String attentionUserId){
        return mAPIService.concernInsert(attentionUserId);
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
     *  榜单一级分类商品列表
     */
    public Observable<HomeCommenBean> goodRank(Map<String,String> param){
        return mAPIService.goodRank(param);
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
     *  消息首页
     */
    public Observable<MessageBean> message(){
        return mAPIService.message();
    }

    /**
     * 获取各类消息列表
     */
    public Observable<MessageAllBean> messageAll(int page , String categoryId){
        return mAPIService.messageAll(page,categoryId);
    }

    /**
     * 查看未读消息 已读
     */
    public Observable<SuccessBean> readMsg(String categoryId){
        return mAPIService.readMsg(categoryId);
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
     * 积分明细
     */
    public Observable<PointDetailBean> pointDetail(String page){
        return mAPIService.pointDetail(page);
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

    /**
     * app端第三方授权登录
     */
    public Observable<LoginBean> thirdLogin(String accessToken, String openid,String channel){
        return mAPIService.thirdLogin(accessToken,openid,channel);
    }

    /**
     * 绑定手机号（app端微信授权登录成功之后）
     */
    public Observable<LoginBean> bindMobile(String channel,String openid,String mobile,String code,String invitationCode){
        return mAPIService.bindMobile(channel,openid,mobile,code,invitationCode);
    }

    /**
     * app端第三方绑定
     */
    public Observable<SuccessBean> bindThirdAccount(String channel, String openid, String accessToken){
        return mAPIService.bindThirdAccount(channel, openid, accessToken);
    }

    /**
     *更换手机号验证码验证(验证旧手机号)
     */
    public Observable<SuccessBean> validateMobileCode(String code){
        return mAPIService.validateMobileCode(code);
    }

    /**
     *更换手机号
     */
    public Observable<SuccessBean> changeMobile(String newMobile,String code){
        return mAPIService.changeMobile(newMobile,code);
    }

    /**
     * 获取榜单分类列表
     */
    public Observable<OrderbyTypeBean> orderbyTypeList(){
        return mAPIService.orderbyTypeList();
    }

    /**
     * 查询收货地址
     */
    public Observable<AddressBean> addresslist(){
        return mAPIService.addresslist();
    }

    /**
     * 添加收货地址
     */
    public Observable<SuccessBean> addressAdd(String username, String mobile,String address,String area){
        return mAPIService.addressAdd(username,mobile,address,area);
    }

    /**
     * 修改收货地址
     */
    public Observable<SuccessBean> addressUpdate(String addressId , String username,
                                          String mobile, String address,String area){
        return mAPIService.addressUpdate(addressId,username,mobile,address,area);
    }

    /**
     * 删除收货地址
     */
    public Observable<SuccessBean> addressDelete(String addressId){
        return mAPIService.addressDelete(addressId);
    }

    /**
     * 设置为默认地址
     */
    public Observable<SuccessBean> addressSetDefault(String addressId){
        return mAPIService.addressSetDefault(addressId);
    }

    /**
     * 我的订单列表
     */
    public Observable<MyOrderBean> orderList(String page){
        return mAPIService.orderList(page);
    }

    /**
     * 确认收货
     */
    public Observable<SuccessBean> orderConfirm(String orderId){
        return mAPIService.orderConfirm(orderId);
    }

    /**
     * 赚钱页面
     */
    public Observable<DailyTaskBean> DailytaskIndex(){
        return mAPIService.DailytaskIndex();
    }

    /**
     * 完成任务领取极币
     */
    public Observable<TaskFinishBean> taskFinish(String type){
        return mAPIService.taskFinish(type);
    }

    /**
     * 极币商城列表
     */
    public Observable<MallBean> mallList(String page){
        return mAPIService.mallList(page);
    }

    /**
     * 极币商城商品详情
     */
    public Observable<MallDetailBean> mallDetail(String pointGoodsId){
        return mAPIService.mallDetail(pointGoodsId,"1");
    }

    /**
     * 获取默认地址
     */
    public Observable<DefaultAddressBean> defaultAddress(){
        return mAPIService.defaultAddress();
    }

    /**
     * 积分兑换
     */
    public Observable<SucBeanT<MyOrderBean.DataBean>> exchange(String pointGoodsId , String addressId , String total){
        return mAPIService.exchange(pointGoodsId,addressId,total);
    }

    /**
     * 邀请好友
     */
    public Observable<InvitationBean> invitation(){
        return mAPIService.invitation();
    }

    /**
     * 获取我的某个试用品的试用报告
     */
    public Observable<ReportBean> myReportInfo(String trialId){
        return mAPIService.myReportInfo(trialId);
    }

    /**
     * 保存试用报告(草稿箱)
     */
    public Observable<SuccessBean> saveReport(String trialId,  String title, String img , String content){
        return mAPIService.saveReport(trialId, title, img, content);
    }

    /**
     * 提交试用报告
     */
    public Observable<SuccessBean> submitReport(String trialId, String title, String img , String content){
        return mAPIService.submitReport(trialId, title, img, content);
    }

    /**
     * 佣金汇总
     */
    public Observable<MyWalletBean> myCommssionSummary(){
        return mAPIService.myCommssionSummary();
    }

    /**
     * 收入明细
     */
    public Observable<BudgetDetailBean> getCommssionDetail(){
        return mAPIService.getCommssionDetail();
    }

    /**
     * 提现明细
     */
    public Observable<WithdrawDetailBean> getWithdrawDetail(int page){
        return mAPIService.getWithdrawDetail(page);
    }

    /**
     * 提现-温馨提示
     */
    public Observable<WithdrawBean> getWithdrawNote(){
        return mAPIService.getWithdrawNote();
    }

    /**
     * 提现
     */
    public Observable<WithdrawBean> withdraw(String realname , String account, String amount){
        return mAPIService.withdraw(realname, account, amount);
    }

    /**
     * 获取二维码邀请图片（多张）
     */
    public Observable<InvitationBean> getQRcodeImgs(){
        return mAPIService.getQRcodeImgs();
    }

    /**
     * 淘宝授权登录回调地址
     */
    public Observable<SuccessBean> taobaoReturnUrl(String code , String state){
        return mAPIService.taobaoReturnUrl(code, state);
    }

    /**
     * 获取支付宝账号
     */
    public Observable<TaobaoAccountBean> taobaoAccount(){
        return mAPIService.taobaoAccount();
    }

    /**
     * 获取专属淘宝购买链接地址
     */
    public Observable<ImageBean> goodsBuyLink(String goodsId){
        return mAPIService.goodsBuyLink(goodsId);
    }

    /**
     * 轮播图
     */
    public Observable<SucBean<EvaluationTabBean.DataBean.AdListBean>> adList(String location){
        return mAPIService.adList(location);
    }

    /**
     * 榜单推荐分类列表
     */
    public Observable<TopCategorysListBean> getTopCategorysList(int page){
        return mAPIService.getTopCategorysList(page);
    }

    /**
     * 榜单推荐分类详情
     */
    public Observable<TopCategoryDetailBean> getTopCategoryDetail(String categoryId){
        return mAPIService.getTopCategoryDetail(categoryId);
    }

    /**
     * 榜单分类属性下商品列表
     */
    public Observable<SucBean<TopCategoryDetailBean.DataBean.RelatedGoodsListBean>> goodsListByOrderbyCategoryId(String orderbyCategoryId){
        return mAPIService.goodsListByOrderbyCategoryId(orderbyCategoryId, "1");
    }

    /**
     * 新品专区
     */
    public Observable<EvaluationListBean> newList(String categoryId ,int page){
        return mAPIService.newList(categoryId, page);
    }

    /**
     * 问题列表
     */
    public Observable<QuestionsBean> questionList(String goodsCategoryId , int page){
        return mAPIService.questionList(goodsCategoryId, page);
    }

    /**
     * 提交问题
     */
    public Observable<SuccessBean> addQuestion(String goodsCategoryId , String title){
        return mAPIService.addQuestion(goodsCategoryId, title);
    }

    /**
     * 查看更多评测推荐——分类榜单
     */
    public Observable<SucBean<TopCategoryDetailBean.DataBean.RelatedArticleListBean>> classiyArticleList(String goodsCategoryId,int page){
        return mAPIService.classiyArticleList(goodsCategoryId, page);
    }

    /**
     * 回答列表
     */
    public Observable<SucBean<QuestionsBean.DataBean.AnswerBean>> answerList(int page , String questionId){
        return mAPIService.answerList(page, questionId);
    }

    /**
     * 提交回答
     */
    public Observable<SuccessBean> addAnswer(String content , String questionId){
        return mAPIService.addAnswer(content, questionId);
    }

    /**
     * 评测模块——关注列表
     */
    public Observable<EvaAttentBean> myfollowList(int page){
        return mAPIService.myfollowList(page);
    }

    /**
     * 评测模块——推荐列表
     */
    public Observable<EvaHotBean> recommendList(int page){
        return mAPIService.recommendList(page);
    }

    /**
     * 评测模块——评测列表  类目
     */
    public Observable<EvaluationTabBean> evaTab2(){
        return mAPIService.evaTab2();
    }

    /**
     * 评测模块——评测列表
     */
    public Observable<EvaEvaBean> indexEvaluationList(String categoryId ){
        return mAPIService.indexEvaluationList(categoryId);
    }

    /**
     * 评测模块——清单列表  类目
     */
    public Observable<EvaluationTabBean> inventTab(){
        return mAPIService.inventTab();
    }

    /**
     * 评测模块——清单列表
     */
    public Observable<EvaluationListBean> inventList(String categoryId ,int page){
        return mAPIService.inventList(categoryId, page);
    }

    /**
     * 开箱/横屏 评测列表
     */
    public Observable<SucBean<EvaEvaBean.DataBean>> unBoxList(String categoryId ,String evaluationType, int page){
        return mAPIService.unBoxList(categoryId, evaluationType, page);
    }

    /**
     * 开箱/横屏 评测列表
     */
    public Observable<SucBean<EvaEvaBean.List2Bean>> unBoxList2(String categoryId ,String evaluationType, int page){
        return mAPIService.unBoxList2(categoryId, evaluationType, page);
    }

    /**
     * 保存清单
     */
    public Observable<FindDetailBean> saveListing(Map<String,String> param){
        return mAPIService.saveListing(param);
    }

    /**
     * 发布清单
     */
    public Observable<SuccessBean> publishListing(String articleId , String content, String img , String title){
        return mAPIService.publishListing(articleId, content, img, title);
    }

    /**
     * 获取关联商品
     */
    public Observable<SreachResultGoodsBean> relatedGoods(String articleId){
        return mAPIService.relatedGoods(articleId);
    }

    /**
     * 搜索商品
     */
    public Observable<SreachResultGoodsBean> searchInventory(String articleId , String keyword , int page){
        return mAPIService.searchInventory(articleId, keyword, page);
    }

    /**
     * 添加商品
     */
    public Observable<SuccessBean> addGoods(String articleId , String goodsId){
        return mAPIService.addGoods(articleId, goodsId);
    }

    /**
     * 取消关联商品
     */
    public Observable<SuccessBean> deleteGoods(String articleId , String goodsId){
        return mAPIService.deleteGoods(articleId, goodsId);
    }

    /**
     * 搜索列表 (商品)
     */
    public Observable<SreachBean> searchGoods(String page , String type, String goodsName){
        return mAPIService.searchGoods(page,goodsName,type);
    }

    /**
     * 搜索列表 (清单、评测、试用报告)
     */
    public Observable<SreachResultArticlesBean> searchArticles(String page , String type, String goodsName){
        return mAPIService.searchArticles(page,goodsName,type);
    }

    /**
     * 搜索列表 (问答)
     */
    public Observable<QuestionsBean> searchQuestions(String page , String type, String goodsName){
        return mAPIService.searchQuestions(page,goodsName,type);
    }


    /**
     * 收藏列表(商品)
     */
    public Observable<SucBean<TBSreachResultBean.DataBean>> collect(String page,String type){
        return mAPIService.collect(page,type);
    }

    /**
     * 收藏列表（清单、评测、试用报告）
     */
    public Observable<SreachResultArticlesBean> collectArticle(String page,String type){
        return mAPIService.collectArticle(page,type);
    }

    /**
     * 收藏列表 (问答)
     */
    public Observable<QuestionsBean> collectQuestions(String page , String type){
        return mAPIService.collectQuestions(page,type);
    }

    /**
     * 通过对方userId获取用户信息(用户中心)
     */
    public Observable<UserInfoBean> getUserByUserId(String userId){
        return mAPIService.getUserByUserId(userId);
    }

    /**
     * 个人主页内容列表（测评，清单）
     */
    public Observable<SreachResultArticlesBean> userArticle(int page , String targetUserId , String type){
        return mAPIService.userArticle(page, targetUserId, type);
    }

    /**
     * 个人主页内容列表（问答）
     */
    public Observable<QuestionsBean> userQuestions(int page , String targetUserId , String type){
        return mAPIService.userQuestions(page, targetUserId, type);
    }

    /**
     * 我的发布——我的清单列表
     */
    public Observable<SreachResultArticlesBean> publishListing(int page, String status){
        return mAPIService.publishListing(page, status);
    }

    /**
     * 删除清单
     */
    public Observable<SuccessBean> ListingDelete(String articleId){
        return mAPIService.ListingDelete(articleId);
    }

    /**
     * 我的发布——我的问答列表
     */
    public Observable<QuestionsBean> publishQuestion(int page, String status){
        return mAPIService.publishQuestion(page, status);
    }

    /**
     * 删除话题
     */
    public Observable<SuccessBean> questionDelete(String questionId){
        return mAPIService.questionDelete(questionId);
    }

    /**
     * 获取弹窗信息
     */
    public Observable<PopInfoBean> getPopInfo(){
        return mAPIService.getPopInfo();
    }

    /**
     * 我的免单记录
     */
    public Observable<MyFreeBean>  myFreeList(String applyStatus , int page){
        return mAPIService.myFreeList(applyStatus,page);
    }

    /**
     * 我的订单（淘宝订单）
     */
    public Observable<OrderTBBean> myTaobaoOrderList(int page , String status  , String source){
        return mAPIService.myTaobaoOrderList(page, status,source);
    }

    /**
     * 鼓励评分
     */
    public Observable<SuccessBean> addScore(String content, int score){
        return mAPIService.addScore(content, score);
    }

    /**
     * 评分状态
     */
    public Observable<ScoreStatusBean> getScoreStatus(){
        return mAPIService.getScoreStatus();
    }

    /**
     * 填写邀请码
     */
    public Observable<SuccessBean> addInvitationCode(String invitationCode){
        return mAPIService.addInvitationCode(invitationCode);
    }

    /**
     * 淘客商品详情
     */
    public Observable<TBShoppingDetailBean> tbGoodsDetail(String otherGoodsId,String source){
        return mAPIService.tbGoodsDetail(otherGoodsId,source);
    }

    /**
     * 猜你喜欢
     */
    public Observable<SimilerGoodsBean> listSimilerGoods(Map<String,String> map){
        return mAPIService.listSimilerGoods(map);
    }

    /**
     * 搜索结果
     */
    public Observable<TBSreachResultBean> searchTBGoods(Map<String,String> map){
        return mAPIService.searchTBGoods(map);
    }

    /**
     * 获取专属淘客链接
     */
    public Observable<ClickUrlBean> getGoodsClickUrl(String source , String otherGoodsId){
        return mAPIService.getGoodsClickUrl(source, otherGoodsId);
    }

    /**
     * 通过淘口令获取商品信息
     */
    public Observable<TklBean> getGoodsByTkl(String keyword){
        return mAPIService.getGoodsByTkl(keyword);
    }

    /**
     * 淘宝客一级分类列表
     */
    public Observable<JDBean> tbkCategory(String source){
        return mAPIService.tbkCategory(source);
    }

    /**
     * 淘客首页数据
     */
    public Observable<TbkIndexBean> tbkIndex(){
        return mAPIService.tbkIndex();
    }

    /**
     * 今日推荐
     */
    public Observable<TBSreachResultBean> commendGoodsList(Map<String,String> map){
        return mAPIService.commendGoodsList(map);
    }

    /**
     * 首页通用页面
     */
    public Observable<TbCommonBean> getGoodsListByCategory1(Map<String,String> map){
        return mAPIService.getGoodsListByCategory1(map);
    }

    /**
     * 首页专题页面
     */
    public Observable<TBSreachResultBean> getGoodsListBySubjectId(Map<String,String> map){
        return mAPIService.getGoodsListBySubjectId(map);
    }

    /**
     * 首页通用页的10宫格框详情接口
     */
    public Observable<TBSreachResultBean> getGoodsListByCategory2(Map<String,String> map){
        return mAPIService.getGoodsListByCategory2(map);
    }

    /**
     * 抢购
     */
    public Observable<ImageBean> allowanceApply(String allowanceGoodsId){
        return mAPIService.allowanceApply(allowanceGoodsId);
    }

    /**
     * 特惠购首页
     */
    public Observable<NewPeopleBean> allowanceIndex(int page){
        return mAPIService.allowanceIndex(page);
    }

    /**
     * 商品图文描述
     */
    public Observable<SucBean<String>> getGoodsDescImgs(String otherGoodsId,String source){
        return mAPIService.getGoodsDescImgs(otherGoodsId,source);
    }

    /**
     * 津贴使用记录
     */
    public Observable<AllowanceRecordBean> myAllowanceList(int page){
        return mAPIService.myAllowanceList(page);
    }

    /**
     * 福利兑换列表
     */
    public Observable<WelfareBean> welfareList(){
        return mAPIService.welfareList();
    }

    /**
     *  后台统计
     */
    public Observable<SuccessBean> addEvent(String eventId){
        return mAPIService.addEvent(eventId);
    }

    /**
     * 提现申请
     */
    public Observable<SuccessBean> withdraw(String amount){
        return mAPIService.withdraw(amount);
    }

    /**
     * 提现记录
     */
    public Observable<MoneyRecordBean> withdrawLog(){
        return mAPIService.withdrawLog();
    }

    /**
     * 支付宝授权登录回调地址
     */
    public Observable<SucBeanT<String>> alipayLogin(String authCode){
        return mAPIService.alipayLogin(authCode);
    }

    /**
     * 绑定支付宝
     */
    public Observable<SuccessBean> bindingAlipay(String realname){
        return mAPIService.bindingAlipay(realname);
    }

    /**
     * 获取支付宝授权请求参数
     */
    public Observable<SucBeanT<String>> getAlipayAuthInfo(){
        return mAPIService.getAlipayAuthInfo();
    }

    /**
     * 新人商品详情
     */
    public Observable<TBShoppingDetailBean> newGoodsDetail(String otherGoodsId){
        return mAPIService.newGoodsDetail(otherGoodsId);
    }

    /**
     * 全部分类（每日精选/宣传素材）
     */
    public Observable<CircleTitleBean> circleTitle(String type){
        return mAPIService.circleTitle(type);
    }

    /**
     * 圈子列表
     */
    public Observable<CircleListBean> circleList(Map<String,String> map){
        return mAPIService.circleList(map);
    }

    /**
     * 成功分享一次
     */
    public Observable<SuccessBean> addShare(String momentId){
        return mAPIService.addShare(momentId);
    }

    /**
     * 获取创建分享内容
     */
    public Observable<ShareBean> getGoodsShareInfo(String otherGoodsId, int shareImgLocation,String source){
        return mAPIService.getGoodsShareInfo(otherGoodsId, shareImgLocation,source);
    }

    /**
     * 下载图片
     */
    public Observable<ResponseBody> downLoadImg(String url){
        return mAPIService.downLoadImg(url);
    }

    /**
     * 获取上级信息（头像和微信）
     */
    public Observable<TeacherBean> parentInfo(){
        return mAPIService.getParentInfo();
    }

    /**
     * 拼多多、京东专题页列表
     */
    public Observable<TBSreachResultBean> getOtherGoodsListByCategory( String category1Id, int page, String source){
        return mAPIService.getOtherGoodsListByCategory(category1Id, page, source);
    }


    /**
     * 一键登录
     */
    public Observable<LoginBean> JVerifyLogin(String loginToken){
        return mAPIService.JVerifyLogin(loginToken);
    }

    /**
     * 一键绑定
     */
    public Observable<LoginBean> JVerifyBind(String loginToken , String openid){
        return mAPIService.JVerifyBind("1",loginToken,openid);
    }

    /**
     * 获取隐私协议版本
     */
    public Observable<ImageBean> getPrivateVersion(){
        return mAPIService.getPrivateVersion();
    }

    /**
     * 注销账号
     */
    public Observable<SuccessBean> destroyAccount(String mobile,String code){
        return mAPIService.destroyAccount(mobile, code);
    }


    /**
     * 0元购免单首页列表
     */
    public Observable<NewFreeBean> freeList2(){
        return mAPIService.freeList2();
    }

    /**
     * 0元购免单详情页面
     */
    public Observable<TBShoppingDetailBean> freeDetail2(String freeId){
        return mAPIService.freeDetail2(freeId);
    }

    /**
     * 0元购免单购买
     */
    public Observable<ImageBean> freeApply2(String otherGoodsId){
        return mAPIService.freeApply2(otherGoodsId);
    }

    /**
     * 红包分享 统计
     */
    public Observable<SuccessBean> shareCount(String hongbao2Id,String type,String shareType){
        return mAPIService.shareCount(hongbao2Id, type, shareType);
    }

    /**
     * 生成分享红包活动海报
     */
    public Observable<ImageBean> hbCreatePosterImg(String hongbao2Id){
        return mAPIService.hbCreatePosterImg(hongbao2Id);
    }

    /**
     * 获取提现信息
     */
    public Observable<WithdrawInfoBean> getWithdrawInfo(){
        return mAPIService.getWithdrawInfo();
    }

    /**
     * 获取0元购免单分享海报
     */
    public Observable<ImageBean> freeGetIndexPosterImg(){
        return mAPIService.freeGetIndexPosterImg();
    }

    /**
     * 获取红包活动信息
     */
    public Observable<ActionHBBean> getHongbaoActivityInfo(){
        return mAPIService.getHongbaoActivityInfo();
    }

    /**
     * 我的团队
     */
    public Observable<TeamBean> getMyTeamInfo(){
        return mAPIService.getMyTeamInfo();
    }

    /**
     * 我的粉丝列表
     */
    public Observable<FansBean> getSubUserList(HashMap<String,String> map){
        return mAPIService.getSubUserList(map);
    }

    /**
     * 获取粉丝详情信息
     */
    public Observable<SubUserBean> getSubUserDetail(String subUserId){
        return mAPIService.getSubUserDetail(subUserId);
    }

    /**
     * 我的收益
     */
    public Observable<MyWalletBean> myCommssionSum(){
        return mAPIService.myCommssionSum();
    }

    /**
     * 历史概况
     */
    public Observable<WalletHistoryBean> getCommssionViewList(HashMap<String, String> map){
        return mAPIService.getCommssionViewList(map);
    }

    /**
     * 收益详情
     */
    public Observable<CommssionDetailBean> getCommssionDetail2(String orderTime , String type){
        return mAPIService.getCommssionDetail2(orderTime, type);
    }

    /**
     * 获取专属活动链接
     */
    public Observable<ImageBean> genByAct(String objectId ,String source){
        return mAPIService.genByAct(objectId, source);
    }

    /**
     * 抽奖记录
     */
    public Observable<PrizeLogBean> prizeLogList(){
        return mAPIService.prizeLogList();
    }


    /**
     * 获取分享数据
     */
    public Observable<ShareInfoBean> getShareInfo(int type,String groupId){
        return mAPIService.getShareInfo(type,groupId);
    }

    /**
     * 获取分享数据
     */
    public Observable<ShareInfoBean> getShareInfo(int type){
        return getShareInfo(type,"");
    }

    /**
     * 关闭首页底部跑马灯消息
     */
    public Observable<SuccessBean> closeIndexMessage(){
        return mAPIService.closeIndexMessage();
    }

    /**
     * 商学院首页
     */
    public Observable<SchoolHomeBean> courseIndex(){
        return mAPIService.courseIndex();
    }

    /**
     * 商学院专题页
     */
    public Observable<SucBean<SchoolHomeBean.DataBean.CategoryListBean.CourseListBean>> listByCategoryId(String categoryId,int page){
        return mAPIService.listByCategoryId(categoryId, page);
    }

    /**
     * 视频详情
     */
    public Observable<VideoBean> videoDetail(String courseId){
        return mAPIService.videoDetail(courseId);
    }

    /**
     * 添加转发次数
     */
    public Observable<SuccessBean> addShareCourse(String courseId){
        return mAPIService.addShareCourse(courseId);
    }

    /**
     * 搜索记录
     */
    public Observable<SreachHistoryBean> searchCourseLog(){
        return mAPIService.searchCourseLog();
    }

    /**
     * 删除全部搜索记录
     */
    public Observable<SuccessBean> searchCourseDeleteAll(){
        return mAPIService.searchCourseDeleteAll();
    }

    /**
     * 搜索课程
     */
    public Observable<SucBean<SchoolHomeBean.DataBean.CategoryListBean.CourseListBean>> courseSearch(int page, String word){
        return mAPIService.courseSearch(page, word);
    }

    /**
     * 查询关联订单
     */
    public Observable<TbOrderBean> searchTbOrder(String tradeId){
        return mAPIService.searchTbOrder(tradeId);
    }

    /**
     * 找回订单
     */
    public Observable<SuccessBean> findBackTbOrder(String tradeId){
        return mAPIService.findBackTbOrder(tradeId);
    }

    /**
     * 添加消息推送token
     */
    public Observable<SuccessBean> addToken(int type, String token){
        return mAPIService.addToken(type,token);
    }

    /**
     * 新会员接口
     */
    public Observable<MemberNewBean> levelIndex(){
        return mAPIService.levelIndex();
    }

    /**
     * 微信支付
     */
    public Observable<WxPayBean> wxpay(String type){
        return mAPIService.wxpay(type);
    }

    /**
     * 支付宝支付
     */
    public Observable<ImageBean> alipay(String type){
        return mAPIService.alipay(type);
    }

    /**
     * 家庭成员列表
     */
    public Observable<FamilyBean> familyList(){
        return mAPIService.familyList();
    }

    /**
     * 家庭确认加入
     */
    public Observable<SuccessBean> familyConfirm(String id , String userId){
        return mAPIService.familyConfirm(id, userId);
    }

    /**
     * 会员0元购首页
     */
    public Observable<NewFreeBean> vipIndex(){
        return mAPIService.vipIndex();
    }

    /**
     * 会员商品详情
     */
    public Observable<TBShoppingDetailBean> zeroDetail(String allowanceGoodsId){
        return mAPIService.zeroDetail(allowanceGoodsId);
    }

    /**
     * 会员0元购抢购
     */
    public Observable<ImageBean> zeroApply(String allowanceGoodsId){
        return mAPIService.zeroApply(allowanceGoodsId);
    }

    /**
     * 领取津贴
     */
    public Observable<SuccessBean> addAllowance(String id){
        return mAPIService.addAllowance(id);
    }

    /**
     * 获取首页浮窗活动信息
     */
    public Observable<SucBeanT<TbkIndexBean.DataBean.Ad1ListBean>> getIndexActivityInfo(){
        return mAPIService.getIndexActivityInfo();
    }

    /**
     * 浏览记录
     */
    public Observable<TBSreachResultBean> goodsHistory(int page){
        return mAPIService.goodsHistory(page);
    }

    /**
     * 吃喝玩乐列表
     */
    public Observable<PlayBean> boxListAll(){
        return mAPIService.boxListAll();
    }

    /**
     * 吃喝玩乐搜索
     */
    public Observable<SucBean<TbkIndexBean.DataBean.BoxCategoryListBean.ListBean>> boxSearch(String keyword){
        return mAPIService.boxSearch(keyword);
    }

    /**
     * 获取拼团信息
     */
    public Observable<GroupInfoBean> groupInfo(String groupId){
        return mAPIService.groupInfo(groupId);
    }

    /**
     * 拼团创建
     */
    public Observable<SuccessBean> groupCreate(String otherGoodsId ,String source){
        return mAPIService.groupCreate(otherGoodsId, source);
    }

    /**
     * 查看拼团状态
     */
    public Observable<SucBeanT<String>> groupStatus(String otherGoodsId){
        return mAPIService.groupStatus(otherGoodsId);
    }

    /**
     * 获取首页拼团弹窗信息
     */
    public Observable<PopBean> getGroupDialog(){
        return mAPIService.getPopInfo("4");
    }

    /**
     * 获取会员购买订单信息
     */
    public Observable<MemberBuyBean> listVipList(){
        return mAPIService.listVipList();
    }

    /**
     * 秒杀分类列表
     */
    public Observable<SeckillTabBean> categoryList(){
        return mAPIService.categoryList();
    }

    /**
     * 秒杀商品列表
     */
    public Observable<SeckillBean> seckillList(String  category2Id,String categoryId , int page){
        return mAPIService.seckillList(category2Id ,categoryId, page);
    }

    /**
     * 秒杀详情
     */
    public Observable<TBShoppingDetailBean> seckillDetail(String seckillGoodsId){
        return mAPIService.seckillDetail(seckillGoodsId);
    }

    /**
     * 极币支付
     */
    public Observable<SuccessBean> pointPay(int type){
        return mAPIService.pointPay(type);
    }

    /**
     * 相似推荐
     */
    public Observable<SimilerGoodsBean> listLikeGoods(String otherGoodsId){
        return mAPIService.listLikeGoods(otherGoodsId);
    }

    /**
     * 获取商品详情里评论信息
     */
    public Observable<CommenBean> getFeedback(String otherGoodsId){
        return mAPIService.getFeedback(otherGoodsId);
    }

    /**
     * 获取店铺地址
     */
    public Observable<ImageBean> getShopUrl(String otherGoodsId){
        return mAPIService.getShopUrl(otherGoodsId);
    }

    /**
     * 获取买多少返多少专属购买链接
     */
    public Observable<ClickUrlBean> getReturnGoodsClickUrl(String otherGoodsId){
        return mAPIService.getReturnGoodsClickUrl(otherGoodsId);
    }

    /**
     * 获取买多少返多少弹窗信息
     */
    public Observable<PopBean> getReturnGoodsInfo(String otherGoodsId){
        return mAPIService.getReturnGoodsInfo(otherGoodsId);
    }
}
