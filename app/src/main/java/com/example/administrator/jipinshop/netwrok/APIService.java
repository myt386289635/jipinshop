package com.example.administrator.jipinshop.netwrok;

import com.example.administrator.jipinshop.bean.Action11Bean;
import com.example.administrator.jipinshop.bean.Activity11Bean;
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
import com.example.administrator.jipinshop.bean.MyFreeBean;
import com.example.administrator.jipinshop.bean.MyOrderBean;
import com.example.administrator.jipinshop.bean.MyWalletBean;
import com.example.administrator.jipinshop.bean.OrderTBBean;
import com.example.administrator.jipinshop.bean.OrderbyTypeBean;
import com.example.administrator.jipinshop.bean.PagerStateBean;
import com.example.administrator.jipinshop.bean.PassedMoreBean;
import com.example.administrator.jipinshop.bean.PointDetailBean;
import com.example.administrator.jipinshop.bean.PopInfoBean;
import com.example.administrator.jipinshop.bean.PosterShareBean;
import com.example.administrator.jipinshop.bean.QuestionsBean;
import com.example.administrator.jipinshop.bean.RecommendFragmentBean;
import com.example.administrator.jipinshop.bean.ReportBean;
import com.example.administrator.jipinshop.bean.ScoreStatusBean;
import com.example.administrator.jipinshop.bean.ShoppingDetailBean;
import com.example.administrator.jipinshop.bean.SignBean;
import com.example.administrator.jipinshop.bean.SignInsertBean;
import com.example.administrator.jipinshop.bean.SimilerGoodsBean;
import com.example.administrator.jipinshop.bean.SreachBean;
import com.example.administrator.jipinshop.bean.SreachHistoryBean;
import com.example.administrator.jipinshop.bean.SreachResultArticlesBean;
import com.example.administrator.jipinshop.bean.SreachResultGoodsBean;
import com.example.administrator.jipinshop.bean.StartPageBean;
import com.example.administrator.jipinshop.bean.SucBean;
import com.example.administrator.jipinshop.bean.SuccessBean;
import com.example.administrator.jipinshop.bean.SystemMessageBean;
import com.example.administrator.jipinshop.bean.TBShoppingDetailBean;
import com.example.administrator.jipinshop.bean.TBSreachResultBean;
import com.example.administrator.jipinshop.bean.TabBean;
import com.example.administrator.jipinshop.bean.TaobaoAccountBean;
import com.example.administrator.jipinshop.bean.TaskFinishBean;
import com.example.administrator.jipinshop.bean.TeamBean;
import com.example.administrator.jipinshop.bean.TklBean;
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
import com.example.administrator.jipinshop.bean.V2FreeListBean;
import com.example.administrator.jipinshop.bean.VoteBean;
import com.example.administrator.jipinshop.bean.WithdrawBean;
import com.example.administrator.jipinshop.bean.WithdrawDetailBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface APIService {

    /**
     * 榜单首页接口  已修改
     */
    @GET("api/getTopGoodsList")
    Observable<RecommendFragmentBean> ranklist();

    /**
     * 登陆接口   已修改
     */
    @FormUrlEncoded
    @POST("api/mobileLogin")
    Observable<LoginBean> login(@Field("mobile") String mobile,@Field("code") String code,@Field("invitationCode")String invitationCode);


    /**
     * 发送验证码    已修改
     */
    @FormUrlEncoded
    @POST("api/sendMessage")
    Observable<SuccessBean> pushMessage(@Field("mobile") String mobile,@Field("type") String type,@Field("ticket") String ticket, @Field("randstr") String randstr);

    /**
     * 退出登陆    已修改
     */
    @POST("api/logout")
    Observable<SuccessBean> logout();

    /**
     * 取消关注  已修改
     */
    @FormUrlEncoded
    @POST("api/follow/delete")
    Observable<SuccessBean> concernDelete(@Field("attentionUserId") String attentionUserId);

    /**
     * 添加关注   已修改
     */
    @POST("api/follow")
    @FormUrlEncoded
    Observable<SuccessBean> concernInsert(@Field("attentionUserId") String attentionUserId);

    /**
     * 榜单tab的字段   已修改
     */
    @GET("api/v2/goodsCategoryList")
    Observable<TabBean> goodsCategory();

    /**
     * 榜单一级分类商品列表  已修改
     */
    @GET("api/goodsList")
    Observable<HomeCommenBean> goodRank(@QueryMap Map<String,String> param);

//    /**
//     * 榜单二级分类商品列表
//     */
//    @GET("api/goodsList2")
//    Observable<SreachResultGoodsBean> goodsList2(@Query("category2Id")String category2Id,@Query("orderbyType")String orderbyType,@Query("client")String client);

    /**
     * 商品详情  已修改
     */
    @GET("api/getGoodsInfo")
    Observable<ShoppingDetailBean> goodsRankDetailList(@Query("goodsId") String goodsId);

    /**
     * 添加收藏    已修改
     */
    @FormUrlEncoded
    @POST("api/collect/add")
    Observable<SuccessBean> collectInsert(@FieldMap Map<String,String> param);

    /**
     * 删除收藏    已修改
     */
    @GET("api/collect/delete")
    Observable<SuccessBean> collectDelete(@QueryMap Map<String,String> param);

    /**
     * 添加点赞   已修改
     */
    @FormUrlEncoded
    @POST("api/vote/add")
    Observable<VoteBean> snapInsert(@FieldMap Map<String,String> param);

    /**
     * 删除点赞    已修改
     */
    @GET("api/vote/delete")
    Observable<VoteBean> snapDelete(@QueryMap Map<String,String> param);

    /**
     * 查看评论列表   已修改
     */
    @GET("api/comment/list")
    Observable<CommentBean> comment(@QueryMap Map<String,String> param);

    /**
     * 添加评论     已修改
     */
    @FormUrlEncoded
    @POST("api/comment/add")
    Observable<SuccessBean> commentInsert(@Field("targetId") String targetId, @Field("toUserId") String toUserId,
                                          @Field("content") String content, @Field("parentId") String parentId,
                                          @Field("type") String type);

    /**
     * 版本更新   已修改
     */
    @GET("api/getAppVersion")
    Observable<AppVersionbean> getAppVersion(@Query("type") String type , @Query("clientVersionCode") String clientVersionCode);


    /**
     * 检测页面状态（登陆后需要检测 关注、收藏、点赞）
     */
    @GET("api/v2/view/status")
    Observable<PagerStateBean> pagerState(@Query("type") String type ,@Query("targetId") String targetId);

    /**
     * 获取用户信息  已修改
     */
    @GET("api/user/getUserInfo")
    Observable<UserInfoBean> modelUser();

    /**
     * 搜索记录
     */
    @GET("api/search/log")
    Observable<SreachHistoryBean> searchLog();

    /**
     * 删除所有搜索记录
     */
    @GET("api/search/deleteAll")
    Observable<SuccessBean> searchDeleteAll();

    /**
     * 删除单条搜索记录
     */
    @GET("api/search/delete")
    Observable<SuccessBean> searchDelete(@Query("id") String id);

    /**
     * 获取发现tab 已修改
     */
    @GET("api/found/categoryList")
    Observable<EvaluationTabBean> findTab();

    /**
     * 获取发现列表  已修改
     */
    @GET("api/found/list")
    Observable<FindListBean> findLis(@Query("categoryId") String categoryId,@Query("page") String page);

    /**
     * 获取评测tab  已修改
     */
    @GET("api/evaluation/categoryList")
    Observable<EvaluationTabBean> evaTab();

    /**
     * 获取评测列表  已修改
     */
    @GET("api/evaluation/list")
    Observable<EvaluationListBean> evaluationList(@Query("categoryId")String categoryId , @Query("page") String page);

    /**
     * 文章详情   已修改
     */
    @GET("api/article/detail")
    Observable<FindDetailBean> findDetail(@Query("articleId") String articleId,@Query("type") String type ,@Query("client") String client);

    /**
     * 获取未读消息  已修改
     */
    @GET("api/v2/message/count")
    Observable<UnMessageBean> unMessage();

    /**
     * 获取消息列表详情内容  已修改
     */
    @GET("api/message/selectAll")
    Observable<SystemMessageBean> messageAll(@Query("page") String page ,@Query("type") String type);

    /**
     * 查看未读消息  已修改
     */
    @GET("api/message/selectById")
    Observable<SuccessBean> readMsg(@Query("id") String id);

    /**
     * 上传图片  已修改
     */
    @POST("api/uploadImage")
    @Multipart
    Observable<ImageBean> importCustomer(@Part MultipartBody.Part importFile);

    /**
     * 用户信息修改  已修改
     */
    @FormUrlEncoded
    @POST("api/user/update")
    Observable<SuccessBean> userUpdate(@FieldMap Map<String,String> param);

    /**
     * 我要反馈  已修改
     */
    @POST("api/feedback/add")
    @FormUrlEncoded
    Observable<SuccessBean> feedBack(@Field("content") String content);

    /**
     * 获取关注列表  已修改
     */
    @GET("api/follow/list")
    Observable<FollowBean> concer(@Query("page") String page);

    /**
     * 获取粉丝列表  已修改
     */
    @GET("api/fans/list")
    Observable<FollowBean> fansList(@Query("page") String page);

    /**
     * 积分明细 已修该
     */
    @GET("api/point/list")
    Observable<PointDetailBean> pointDetail(@Query("page") String page);

    /**
     * 查询签到7天状态  已签到
     */
    @GET("api/signinDetail")
    Observable<SignBean> sign();

    /**
     * 签到   已修改
     */
    @POST("api/signin")
    Observable<SignInsertBean> signInsert();

    /**
     * app端第三方授权登录
     */
    @POST("api/thirdLogin")
    @FormUrlEncoded
    Observable<LoginBean> thirdLogin(@Field("accessToken") String accessToken,@Field("openid") String openid,@Field("channel") String channel);

    /**
     * 绑定手机号（app端第三方授权登录成功之后）
     */
    @POST("api/bindMobile")
    @FormUrlEncoded
    Observable<LoginBean> bindMobile(@Field("channel")String channel,@Field("openid") String openid,
                                     @Field("mobile")String mobile,@Field("code")String code,
                                     @Field("invitationCode") String invitationCode);

    /**
     * app端第三方绑定
     */
    @POST("api/user/bindThirdAccount")
    @FormUrlEncoded
    Observable<SuccessBean> bindThirdAccount(@Field("channel") String channel,@Field("openid") String openid,@Field("accessToken") String accessToken);

    /**
     *更换手机号验证码验证(验证旧手机号)
     */
    @POST("api/user/validateMobileCode")
    @FormUrlEncoded
    Observable<SuccessBean> validateMobileCode(@Field("code") String code);


    /**
     *更换手机号
     */
    @POST("api/user/changeMobile")
    @FormUrlEncoded
    Observable<SuccessBean> changeMobile(@Field("newMobile") String newMobile,@Field("code") String code);

    /**
     * 获取榜单分类列表
     */
    @GET("api/orderbyTypeList")
    Observable<OrderbyTypeBean> orderbyTypeList();

    /**
     * 查询收货地址
     */
    @GET("api/address/list")
    Observable<AddressBean> addresslist();

    /**
     * 添加收货地址
     */
    @FormUrlEncoded
    @POST("api/address/add")
    Observable<SuccessBean> addressAdd(@Field("username") String username, @Field("mobile") String mobile,
                                       @Field("address") String address, @Field("area") String area);

    /**
     * 修改收货地址
     */
    @FormUrlEncoded
    @POST("api/address/update")
    Observable<SuccessBean> addressUpdate(@Field("addressId") String addressId , @Field("username") String username,
                                          @Field("mobile") String mobile, @Field("address") String address ,
                                          @Field("area") String area);

    /**
     * 删除收货地址
     */
    @FormUrlEncoded
    @POST("api/address/delete")
    Observable<SuccessBean> addressDelete(@Field("addressId") String addressId);

    /**
     * 设置为默认地址
     */
    @FormUrlEncoded
    @POST("api/address/setDefault")
    Observable<SuccessBean> addressSetDefault(@Field("addressId") String addressId);

    /**
     * 我的订单列表
     */
    @GET("api/order/list")
    Observable<MyOrderBean> orderList(@Query("page") String page);

    /**
     * 确认收货
     */
    @FormUrlEncoded
    @POST("api/order/confirm")
    Observable<SuccessBean> orderConfirm(@Field("orderId") String orderId);

    /**
     * 每日任务
     */
    @GET("api/dailytask/list")
    Observable<DailyTaskBean> DailytaskList();

    /**
     * 完成任务领取极币
     */
    @FormUrlEncoded
    @POST("api/dailytask/finish")
    Observable<TaskFinishBean> taskFinish(@Field("type") String type);

    /**
     * 极币商城列表
     */
    @GET("api/point/getGoodslist")
    Observable<MallBean> mallList(@Query("page") String page);

    /**
     * 极币商城商品详情
     */
    @GET("api/point/getGoodsInfo")
    Observable<MallDetailBean> mallDetail(@Query("pointGoodsId") String pointGoodsId,@Query("client") String client);

    /**
     * 获取默认地址
     */
    @GET("api/address/default")
    Observable<DefaultAddressBean> defaultAddress();

    /**
     * 积分兑换
     */
    @FormUrlEncoded
    @POST("api/point/exchange")
    Observable<SuccessBean> exchange(@Field("pointGoodsId") String pointGoodsId ,@Field("addressId") String addressId,@Field("total") String total);

    /**
     * 邀请好友
     */
    @GET("api/user/getQRcodeImg")
    Observable<InvitationBean> invitation();

    /**
     * 试用首页
     */
    @GET("api/trial/index")
    Observable<TryBean> tryIndex();

    /**
     * 查看全部试用
     */
    @GET("api/trial/list")
    Observable<TryAllBean> tryAllList(@Query("page") String page);

    /**
     * 查看全部试用报告
     */
    @GET("api/trial/reportList")
    Observable<TryReportBean> tryReportList(@Query("page") String page,@Query("orderbyType") String orderbyType);

    /**
     * 试用详情
     */
    @GET("api/trial/detail")
    Observable<TryDetailBean> tryDetail(@Query("trialId") String trialId);

    /**
     * 申请试用
     */
    @FormUrlEncoded
    @POST("api/trial/apply")
    Observable<TryApplyBean> tryApply(@Field("trialId") String trialId);

    /**
     * 试用名单列表
     */
    @GET("api/trial/passedUserList")
    Observable<PassedMoreBean> passedUserList(@Query("trialId") String trialId);

    /**
     * 试用品查看全部试用报告
     */
    @GET("api/trial/reportAllList")
    Observable<TryReportBean> reportAllList(@Query("page") String page , @Query("trialId") String goodsId);

    /**
     * 拉赞排行
     */
    @GET("api/trial/voteUserList")
    Observable<SucBean<TryDetailBean.DataBean.ApplyUserListBean>> voteUserList(@Query("page") String page , @Query("trialId") String trialId);

    /**
     * 我的试用列表
     */
    @GET("api/my/trial/list")
    Observable<TrialCommonBean> myTrialList(@Query("status") String status , @Query("page") String page);

    /**
     * 确认参与
     */
    @FormUrlEncoded
    @POST("api/trial/confirm")
    Observable<SuccessBean> myTrialConfirm(@Field("trialId") String trialId);

    /**
     * 启动页
     */
    @GET("api/getStartupImgs")
    Observable<StartPageBean> getStartupImgs();

    /**
     * 获取我的某个试用品的试用报告
     */
    @GET("api/my/trial/reportInfo")
    Observable<ReportBean> myReportInfo(@Query("trialId") String trialId);

    /**
     * 保存试用报告(草稿箱)
     */
    @FormUrlEncoded
    @POST("api/trial/editReport")
    Observable<SuccessBean> saveReport(@Field("trialId") String trialId,@Field("title") String title, @Field("img") String img , @Field("content") String content);

    /**
     * 提交试用报告
     */
    @FormUrlEncoded
    @POST("api/trial/addReport")
    Observable<SuccessBean> submitReport(@Field("trialId") String trialId,@Field("title") String title, @Field("img") String img , @Field("content") String content);

    /**
     * 我的试用报告
     */
    @GET("api/my/trial/reportList")
    Observable<SreachResultArticlesBean> myReportList(@Query("page") String page);

    /**
     * 佣金汇总
     */
    @GET("api/v2/myCommssionSummary")
    Observable<MyWalletBean> myCommssionSummary();

    /**
     * 收入明细
     */
    @GET("api/getCommssionDetail")
    Observable<BudgetDetailBean> getCommssionDetail();

    /**
     * 提现明细
     */
    @GET("api/getWithdrawDetail")
    Observable<WithdrawDetailBean> getWithdrawDetail(@Query("page") int page);

    /**
     * 提现-温馨提示
     */
    @GET("api/getWithdrawNote")
    Observable<WithdrawBean> getWithdrawNote();

    /**
     * 提现
     */
    @FormUrlEncoded
    @POST("api/withdraw")
    Observable<SuccessBean> withdraw(@Field("realname") String realname , @Field("account") String account, @Field("amount") String amount);

    /**
     * 下级会员
     */
    @GET("api/getSubUserList")
    Observable<TeamBean> getSubUserList(@Query("page") int page);

    /**
     * 获取二维码邀请图片（多张）
     */
    @GET("api/user/getQRcodeImgs")
    Observable<InvitationBean> getQRcodeImgs();

    /**
     * 淘宝授权登录回调地址
     */
    @GET("api/taobao/returnUrl")
    Observable<SuccessBean> taobaoReturnUrl(@Query("code")String code , @Query("state") String state);

    /**
     * 获取支付宝账号
     */
    @GET("api/getTaobaoAccount")
    Observable<TaobaoAccountBean> taobaoAccount();

    /**
     * 获取专属淘宝购买链接地址
     */
    @GET("api/getGoodsBuyLink")
    Observable<ImageBean> goodsBuyLink(@Query("goodsId")String goodsId);

    /**
     * 轮播图
     */
    @GET("api/adList")
    Observable<SucBean<EvaluationTabBean.DataBean.AdListBean>> adList(@Query("location") String location);

    /**
     * 免单列表
     */
    @GET("api/free/list")
    Observable<FreeBean> freeList(@Query("page") int page);

    /**
     * 免单详情
     */
    @GET("api/free/detail")
    Observable<FreeDetailBean> freeDetail(@Query("freeId") String freeId, @Query("fromId") String fromId , @Query("fromType") String fromType);

    /**
     * 免单抢购
     */
    @FormUrlEncoded
    @POST("api/free/apply")
    Observable<FreeDetailBean> freeApply(@Field("freeId") String freeId);

    /**
     * 参与名单列表
     */
    @GET("api/v2/free/freeUserList")
    Observable<FreeUserListBean> freeUserList(@Query("freeId") String freeId , @Query("page") int page);

    /**
     * 榜单推荐分类列表
     */
    @GET("api/v2/getTopCategorysList")
    Observable<TopCategorysListBean> getTopCategorysList(@Query("page") int page);

    /**
     * 榜单推荐分类详情
     */
    @GET("api/v2/getTopCategoryDetail")
    Observable<TopCategoryDetailBean> getTopCategoryDetail(@Query("categoryId") String categoryId);

    /**
     * 榜单分类属性下商品列表
     */
    @GET("api/v2/goodsListByOrderbyCategoryId")
    Observable<SucBean<TopCategoryDetailBean.DataBean.RelatedGoodsListBean>> goodsListByOrderbyCategoryId(@Query("orderbyCategoryId") String orderbyCategoryId , @Query("client") String client);

    /**
     * 新品专区
     */
    @GET("api/v2/article/listnew")
    Observable<EvaluationListBean> newList(@Query("categoryId") String categoryId , @Query("page") int page);

    /**
     * 问题列表
     */
    @GET("api/v2/question/list")
    Observable<QuestionsBean> questionList(@Query("goodsCategoryId")String goodsCategoryId , @Query("page") int page);

    /**
     * 提交问题
     */
    @FormUrlEncoded
    @POST("api/v2/question/addQuestion")
    Observable<SuccessBean> addQuestion(@Field("goodsCategoryId") String goodsCategoryId , @Field("title") String title);

    /**
     * 查看更多评测推荐——分类榜单
     */
    @GET("api/v2/article/list")
    Observable<SucBean<TopCategoryDetailBean.DataBean.RelatedArticleListBean>> classiyArticleList(@Query("goodsCategoryId") String goodsCategoryId, @Query("page") int page);

    /**
     * 回答列表
     */
    @GET("api/v2/question/answerList")
    Observable<SucBean<QuestionsBean.DataBean.AnswerBean>> answerList(@Query("page") int page , @Query("questionId") String questionId);

    /**
     * 提交回答
     */
    @FormUrlEncoded
    @POST("api/v2/question/addAnswer")
    Observable<SuccessBean> addAnswer(@Field("content") String content ,@Field("questionId") String questionId);

    /**
     * 评测模块——关注列表
     */
    @GET("api/v2/article/myfollowList")
    Observable<EvaAttentBean> myfollowList(@Query("page") int page);

    /**
     * 评测模块——推荐列表
     */
    @GET("api/v2/article/recommendList")
    Observable<EvaHotBean> recommendList(@Query("page") int page);

    /**
     * 评测模块——评测列表  类目
     */
    @GET("api/v2/evaluation/categoryList")
    Observable<EvaluationTabBean> evaTab2();

    /**
     * 评测模块——评测列表
     */
    @GET("api/v2/article/indexEvaluationList")
    Observable<EvaEvaBean> indexEvaluationList(@Query("categoryId") String categoryId );

    /**
     * 评测模块——清单列表  类目
     */
    @GET("api/v2/listing/categoryList")
    Observable<EvaluationTabBean> inventTab();

    /**
     * 评测模块——清单列表
     */
    @GET("api/v2/article/listingList")
    Observable<EvaluationListBean> inventList(@Query("categoryId") String categoryId , @Query("page") int page);

    /**
     * 开箱 评测列表
     */
    @GET("api/v2/article/evaluationList")
    Observable<SucBean<EvaEvaBean.DataBean>> unBoxList(@Query("categoryId") String categoryId , @Query("evaluationType") String evaluationType, @Query("page") int page);

    /**
     * 横屏 评测列表
     */
    @GET("api/v2/article/evaluationList")
    Observable<SucBean<EvaEvaBean.List2Bean>> unBoxList2(@Query("categoryId") String categoryId , @Query("evaluationType") String evaluationType, @Query("page") int page);

    /**
     * 保存清单
     */
    @FormUrlEncoded
    @POST("api/v2/article/saveListing")
    Observable<FindDetailBean> saveListing(@FieldMap Map<String,String> param);

    /**
     * 发布清单
     */
    @FormUrlEncoded
    @POST("api/v2/article/publishListing")
    Observable<SuccessBean> publishListing(@Field("articleId") String articleId , @Field("content") String content,
                                            @Field("img") String img , @Field("title") String title);

    /**
     * 获取关联商品
     */
    @GET("api/v2/article/relatedGoods")
    Observable<SreachResultGoodsBean> relatedGoods(@Query("articleId") String articleId);

    /**
     * 搜索商品
     */
    @GET("api/v2/article/searchGoods")
    Observable<SreachResultGoodsBean> searchInventory(@Query("articleId") String articleId , @Query("keyword") String keyword , @Query("page") int page);

    /**
     * 添加商品
     */
    @FormUrlEncoded
    @POST("api/v2/article/addGoods")
    Observable<SuccessBean> addGoods(@Field("articleId") String articleId , @Field("goodsId") String goodsId);

    /**
     * 取消关联商品
     */
    @FormUrlEncoded
    @POST("api/v2/article/deleteGoods")
    Observable<SuccessBean> deleteGoods(@Field("articleId") String articleId , @Field("goodsId") String goodsId);

    /**
     * 搜索列表（商品）
     */
    @GET("api/v2/search")
    Observable<SreachBean> searchGoods(@Query("page") String page, @Query("keyword") String keyword, @Query("type")String type);

    /**
     * 搜索列表（清单、评测、试用报告）
     */
    @GET("api/v2/search")
    Observable<SreachResultArticlesBean> searchArticles(@Query("page") String page, @Query("keyword") String keyword, @Query("type")String type);

    /**
     * 搜索列表（问答）
     */
    @GET("api/v2/search")
    Observable<QuestionsBean> searchQuestions(@Query("page") String page, @Query("keyword") String keyword, @Query("type")String type);

    /**
     * 获取收藏列表(商品)
     */
    @GET("api/v2/collect/list")
    Observable<SucBean<TopCategoryDetailBean.DataBean.RelatedGoodsListBean>> collect(@Query("page") String page ,@Query("type") String type);

    /**
     * 获取收藏列表（清单、评测、试用报告）
     */
    @GET("api/v2/collect/list")
    Observable<SreachResultArticlesBean> collectArticle(@Query("page") String page ,@Query("type") String type);

    /**
     * 获取收藏列表（问答）
     */
    @GET("api/v2/collect/list")
    Observable<QuestionsBean> collectQuestions(@Query("page") String page ,@Query("type") String type);

    /**
     * 通过对方userId获取用户信息(用户中心)
     */
    @GET("api/user/getUserByUserId")
    Observable<UserInfoBean> getUserByUserId(@Query("userId") String userId);

    /**
     * 个人主页内容列表（测评，清单）
     */
    @GET("api/v2/user/user/article/listByUserId")
    Observable<SreachResultArticlesBean> userArticle(@Query("page") int page , @Query("targetUserId") String targetUserId , @Query("type") String type);

    /**
     * 个人主页内容列表（问答）
     */
    @GET("api/v2/user/user/article/listByUserId")
    Observable<QuestionsBean> userQuestions(@Query("page") int page , @Query("targetUserId") String targetUserId , @Query("type") String type);

    /**
     * 我的发布——我的清单列表
     */
    @GET("api/v2/user/my/article/listingList")
    Observable<SreachResultArticlesBean> publishListing(@Query("page") int page, @Query("status") String status);

    /**
     * 删除清单
     */
    @FormUrlEncoded
    @POST("api/v2/user/my/article/delete")
    Observable<SuccessBean> ListingDelete(@Field("articleId") String articleId);

    /**
     * 我的发布——我的问答列表
     */
    @GET("api/v2/user/my/question/list")
    Observable<QuestionsBean> publishQuestion(@Query("page") int page, @Query("status") String status);

    /**
     * 删除话题
     */
    @FormUrlEncoded
    @POST("api/v2/user/my/question/delete")
    Observable<SuccessBean> questionDelete(@Field("questionId") String questionId);

    /**
     * 获取弹窗信息
     */
    @GET("api/v2/getPopInfo")
    Observable<PopInfoBean> getPopInfo();

    /**
     * 我的免单记录
     */
    @GET("api/v3/user/freeList")
    Observable<MyFreeBean>  myFreeList(@Query("applyStatus") String applyStatus , @Query("page") int page);

    /**
     * 我的订单（淘宝订单）
     */
    @GET("api/v2/user/myTaobaoOrderList")
    Observable<OrderTBBean> myTaobaoOrderList(@Query("page") int page , @Query("status") String status);

    /**
     * 鼓励评分
     */
    @FormUrlEncoded
    @POST("api/v2/addScore")
    Observable<SuccessBean> addScore(@Field("content") String content, @Field("score") int score);

    /**
     * 评分状态
     */
    @GET("api/v2/getScoreStatus")
    Observable<ScoreStatusBean> getScoreStatus();

    /**
     * 是否开启双11活动
     */
    @GET("api/v2/open11")
    Observable<ScoreStatusBean> open11();

    /**
     * 双11活动首页
     */
    @GET("api/v2/activity11/index")
    Observable<Action11Bean> activity11Index();

    /**
     * 双11活动首页商品列表
     */
    @GET("api/v2/activity11/goodsList")
    Observable<SucBean<Action11Bean.DataBean.GoodsDataListBean>> activity11GoodsList(@Query("page") int page);

    /**
     * 双11活动专题商品列表
     */
    @GET("api/v2/activity11/category/goodsList")
    Observable<Activity11Bean> activity11Category(@Query("articleCategoryId") String articleCategoryId, @Query("page") int page);

    /**
     * 免单列表
     */
    @GET("api/v2/free/list")
    Observable<V2FreeListBean> freeList(@Query("page") int page , @Query("type") String type);

    /**
     * 免单详情
     */
    @GET("api/v2/free/detail")
    Observable<FreeDetailBean> freeDetail(@Query("freeId") String freeId);

    /**
     * 免单抢购
     */
    @FormUrlEncoded
    @POST("api/v2/free/apply")
    Observable<ImageBean> freeApply2(@Field("freeId") String freeId , @Field("type") String type);

    /**
     * 生成邀请免单首页海报
     */
    @POST("api/v2/free/getShareInfo")
    Observable<PosterShareBean> createFreePosterIndex();

    /**
     * 生成邀请免单详情页海报
     */
    @FormUrlEncoded
    @POST("api/v2/free/createFreePoster")
    Observable<ImageBean> createFreePoster(@Field("freeId") String freeId);

    /**
     * 填写邀请码
     */
    @FormUrlEncoded
    @POST("api/user/addInvitationCode")
    Observable<SuccessBean> addInvitationCode(@Field("invitationCode") String invitationCode);

    /**
     * 淘客商品详情
     */
    @GET("api/tbk/goodsDetail")
    Observable<TBShoppingDetailBean> tbGoodsDetail(@Query("otherGoodsId") String otherGoodsId);

    /**
     * 猜你喜欢
     */
    @GET("api/tbk/listSimilerGoods")
    Observable<SimilerGoodsBean> listSimilerGoods(@QueryMap Map<String,String> map);

    /**
     * 搜索结果
     */
    @GET("api/tbk/searchGoods")
    Observable<TBSreachResultBean> searchTBGoods(@QueryMap Map<String,String> map);

    /**
     * 获取专属淘客链接
     */
    @GET("api/tbk/getGoodsClickUrl")
    Observable<ImageBean> getGoodsClickUrl(@Query("goodsBuyLink") String goodsBuyLink , @Query("otherGoodsId") String otherGoodsId);

    /**
     * 通过淘口令获取商品信息
     */
    @GET("api/tbk/getGoodsByTkl")
    Observable<TklBean> getGoodsByTkl(@Query("tkl") String tkl);
}
