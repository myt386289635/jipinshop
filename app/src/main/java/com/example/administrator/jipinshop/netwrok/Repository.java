package com.example.administrator.jipinshop.netwrok;

import com.example.administrator.jipinshop.bean.AddressBean;
import com.example.administrator.jipinshop.bean.AppVersionbean;
import com.example.administrator.jipinshop.bean.BudgetDetailBean;
import com.example.administrator.jipinshop.bean.CommentBean;
import com.example.administrator.jipinshop.bean.DailyTaskBean;
import com.example.administrator.jipinshop.bean.DefaultAddressBean;
import com.example.administrator.jipinshop.bean.EvaAttentBean;
import com.example.administrator.jipinshop.bean.EvaEvaBean;
import com.example.administrator.jipinshop.bean.EvaHotBean;
import com.example.administrator.jipinshop.bean.EvaluationListBean;
import com.example.administrator.jipinshop.bean.EvaluationTabBean;
import com.example.administrator.jipinshop.bean.FindDetailBean;
import com.example.administrator.jipinshop.bean.FindListBean;
import com.example.administrator.jipinshop.bean.FollowBean;
import com.example.administrator.jipinshop.bean.FreeBean;
import com.example.administrator.jipinshop.bean.FreeDetailBean;
import com.example.administrator.jipinshop.bean.FreeUserListBean;
import com.example.administrator.jipinshop.bean.HomeCommenBean;
import com.example.administrator.jipinshop.bean.ImageBean;
import com.example.administrator.jipinshop.bean.InvitationBean;
import com.example.administrator.jipinshop.bean.LoginBean;
import com.example.administrator.jipinshop.bean.MallBean;
import com.example.administrator.jipinshop.bean.MallDetailBean;
import com.example.administrator.jipinshop.bean.MyOrderBean;
import com.example.administrator.jipinshop.bean.MyWalletBean;
import com.example.administrator.jipinshop.bean.OrderbyTypeBean;
import com.example.administrator.jipinshop.bean.PagerStateBean;
import com.example.administrator.jipinshop.bean.PassedMoreBean;
import com.example.administrator.jipinshop.bean.PointDetailBean;
import com.example.administrator.jipinshop.bean.QuestionsBean;
import com.example.administrator.jipinshop.bean.RecommendFragmentBean;
import com.example.administrator.jipinshop.bean.ReportBean;
import com.example.administrator.jipinshop.bean.ShoppingDetailBean;
import com.example.administrator.jipinshop.bean.SignBean;
import com.example.administrator.jipinshop.bean.SignInsertBean;
import com.example.administrator.jipinshop.bean.SreachHistoryBean;
import com.example.administrator.jipinshop.bean.SreachResultArticlesBean;
import com.example.administrator.jipinshop.bean.SreachResultGoodsBean;
import com.example.administrator.jipinshop.bean.StartPageBean;
import com.example.administrator.jipinshop.bean.SucBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.SystemMessageBean;
import com.example.administrator.jipinshop.bean.TabBean;
import com.example.administrator.jipinshop.bean.TaobaoAccountBean;
import com.example.administrator.jipinshop.bean.TaskFinishBean;
import com.example.administrator.jipinshop.bean.TeamBean;
import com.example.administrator.jipinshop.bean.TopCategoryDetailBean;
import com.example.administrator.jipinshop.bean.TopCategorysListBean;
import com.example.administrator.jipinshop.bean.TrialCommonBean;
import com.example.administrator.jipinshop.bean.TryAllBean;
import com.example.administrator.jipinshop.bean.TryApplyBean;
import com.example.administrator.jipinshop.bean.TryBean;
import com.example.administrator.jipinshop.bean.TryDetailBean;
import com.example.administrator.jipinshop.bean.TryReportBean;
import com.example.administrator.jipinshop.bean.UnMessageBean;
import com.example.administrator.jipinshop.bean.UserInfoBean;
import com.example.administrator.jipinshop.bean.UserPageBean;
import com.example.administrator.jipinshop.bean.VoteBean;
import com.example.administrator.jipinshop.bean.WithdrawBean;
import com.example.administrator.jipinshop.bean.WithdrawDetailBean;
import com.example.administrator.jipinshop.util.UpDataUtil;

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
        return mAPIService.sign();
    }

    /**
     * 签到
     */
    public Observable<SignInsertBean> signInsert(){
        return mAPIService.signInsert();
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

//    /**
//     * 榜单二级分类商品列表
//     */
//    public Observable<SreachResultGoodsBean> goodsList2(String category2Id,String orderbyType){
//        return mAPIService.goodsList2(category2Id,orderbyType,"1");
//    }

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
     * 每日任务
     */
    public Observable<DailyTaskBean> DailytaskList(){
        return mAPIService.DailytaskList();
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
    public Observable<SuccessBean> exchange(String pointGoodsId , String addressId ,String total){
        return mAPIService.exchange(pointGoodsId,addressId,total);
    }

    /**
     * 邀请好友
     */
    public Observable<InvitationBean> invitation(){
        return mAPIService.invitation();
    }

    /**
     * 试用首页
     */
    public Observable<TryBean> tryIndex(){
        return mAPIService.tryIndex();
    }

    /**
     * 查看全部试用列表
     */
    public Observable<TryAllBean> tryAllList(String page){
        return mAPIService.tryAllList(page);
    }

    /**
     * 查看全部试用报告
     */
    public Observable<TryReportBean> tryReportList(String page,String orderbyType){
        return mAPIService.tryReportList(page,orderbyType);
    }

    /**
     * 试用详情
     */
    public Observable<TryDetailBean> tryDetail(String trialId){
        return mAPIService.tryDetail(trialId);
    }

    /**
     * 申请试用
     */
    public Observable<TryApplyBean> tryApply(String trialId){
        return mAPIService.tryApply(trialId);
    }

    /**
     * 试用名单列表
     */
    public  Observable<PassedMoreBean> passedUserList(String trialId){
        return mAPIService.passedUserList(trialId);
    }

    /**
     * 试用品查看全部试用报告
     */
    public  Observable<TryReportBean> reportAllList( String page ,String trialId){
        return mAPIService.reportAllList(page, trialId);
    }

    /**
     * 拉赞排行
     */
    public Observable<SucBean<TryDetailBean.DataBean.ApplyUserListBean>> voteUserList( String page , String trialId){
        return mAPIService.voteUserList(page, trialId);
    }

    /**
     * 我的试用列表
     */
    public Observable<TrialCommonBean> myTrialList(String status,String page){
        return mAPIService.myTrialList(status, page);
    }

    /**
     * 确认参与
     */
    public Observable<SuccessBean> myTrialConfirm(String trialId){
        return mAPIService.myTrialConfirm(trialId);
    }

    /**
     * 启动页
     */
    public Observable<StartPageBean> getStartupImgs(){
        return mAPIService.getStartupImgs();
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
     * 我的试用报告  （成功报告）
     */
    public Observable<SreachResultArticlesBean> myReportList(String page){
        return mAPIService.myReportList(page);
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
    public Observable<SuccessBean> withdraw(String realname , String account, String amount){
        return mAPIService.withdraw(realname, account, amount);
    }

    /**
     * 下级会员
     */
    public Observable<TeamBean> getSubUserList(int page){
        return mAPIService.getSubUserList(page);
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
     * 免单列表
     */
    public Observable<FreeBean> freeList(int page){
        return mAPIService.freeList(page);
    }

    /**
     * 免单详情
     */
    public Observable<FreeDetailBean> freeDetail(String freeId){
        return mAPIService.freeDetail(freeId);
    }

    /**
     * 免单抢购
     */
    public Observable<FreeDetailBean> freeApply(String freeId){
        return mAPIService.freeApply(freeId);
    }

    /**
     * 参与名单列表
     */
    public Observable<FreeUserListBean> freeUserList(String freeId , int page){
        return mAPIService.freeUserList(freeId, page);
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
}
