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
import com.example.administrator.jipinshop.bean.MoneyRecordBean;
import com.example.administrator.jipinshop.bean.MyFreeBean;
import com.example.administrator.jipinshop.bean.MyOrderBean;
import com.example.administrator.jipinshop.bean.MyWalletBean;
import com.example.administrator.jipinshop.bean.NewFreeBean;
import com.example.administrator.jipinshop.bean.NewPeopleBean;
import com.example.administrator.jipinshop.bean.NewPopInfoBean;
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
import com.example.administrator.jipinshop.bean.MessageBean;
import com.example.administrator.jipinshop.bean.TBCategoryBean;
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

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface APIService {

    /**
     * 榜单首页接口
     */
    @GET("api/getTopGoodsList")
    Observable<RecommendFragmentBean> ranklist();

    /**
     * 登陆接口
     */
    @FormUrlEncoded
    @POST("api/mobileLogin")
    Observable<LoginBean> login(@Field("mobile") String mobile,@Field("code") String code,@Field("invitationCode")String invitationCode);

    /**
     * 发送验证码
     */
    @FormUrlEncoded
    @POST("api/sendMessage")
    Observable<SuccessBean> pushMessage(@Field("mobile") String mobile,@Field("type") String type,@Field("ticket") String ticket, @Field("randstr") String randstr);

    /**
     * 退出登陆
     */
    @POST("api/logout")
    Observable<SuccessBean> logout();

    /**
     * 取消关注
     */
    @FormUrlEncoded
    @POST("api/follow/delete")
    Observable<SuccessBean> concernDelete(@Field("attentionUserId") String attentionUserId);

    /**
     * 添加关注
     */
    @POST("api/follow")
    @FormUrlEncoded
    Observable<SuccessBean> concernInsert(@Field("attentionUserId") String attentionUserId);

    /**
     * 榜单tab的字段
     */
    @GET("api/v2/goodsCategoryList")
    Observable<TabBean> goodsCategory();

    /**
     * 榜单一级分类商品列表
     */
    @GET("api/goodsList")
    Observable<HomeCommenBean> goodRank(@QueryMap Map<String,String> param);

    /**
     * 商品详情
     */
    @GET("api/getGoodsInfo")
    Observable<ShoppingDetailBean> goodsRankDetailList(@Query("goodsId") String goodsId);

    /**
     * 添加收藏
     */
    @FormUrlEncoded
    @POST("api/collect/add")
    Observable<SuccessBean> collectInsert(@FieldMap Map<String,String> param);

    /**
     * 删除收藏
     */
    @GET("api/collect/delete")
    Observable<SuccessBean> collectDelete(@QueryMap Map<String,String> param);

    /**
     * 添加点赞
     */
    @FormUrlEncoded
    @POST("api/vote/add")
    Observable<VoteBean> snapInsert(@FieldMap Map<String,String> param);

    /**
     * 删除点赞
     */
    @GET("api/vote/delete")
    Observable<VoteBean> snapDelete(@QueryMap Map<String,String> param);

    /**
     * 查看评论列表
     */
    @GET("api/comment/list")
    Observable<CommentBean> comment(@QueryMap Map<String,String> param);

    /**
     * 添加评论
     */
    @FormUrlEncoded
    @POST("api/comment/add")
    Observable<SuccessBean> commentInsert(@Field("targetId") String targetId, @Field("toUserId") String toUserId,
                                          @Field("content") String content, @Field("parentId") String parentId,
                                          @Field("type") String type);

    /**
     * 版本更新
     */
    @GET("api/getAppVersion")
    Observable<AppVersionbean> getAppVersion(@Query("type") String type , @Query("clientVersionCode") String clientVersionCode);


    /**
     * 检测页面状态（登陆后需要检测 关注、收藏、点赞）
     */
    @GET("api/v2/view/status")
    Observable<PagerStateBean> pagerState(@Query("type") String type ,@Query("targetId") String targetId);

    /**
     * 获取用户信息
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
     * 获取发现tab
     */
    @GET("api/found/categoryList")
    Observable<EvaluationTabBean> findTab();

    /**
     * 获取发现列表
     */
    @GET("api/found/list")
    Observable<FindListBean> findLis(@Query("categoryId") String categoryId,@Query("page") String page);

    /**
     * 获取评测tab
     */
    @GET("api/evaluation/categoryList")
    Observable<EvaluationTabBean> evaTab();

    /**
     * 获取评测列表
     */
    @GET("api/evaluation/list")
    Observable<EvaluationListBean> evaluationList(@Query("categoryId")String categoryId , @Query("page") String page);

    /**
     * 文章详情
     */
    @GET("api/article/detail")
    Observable<FindDetailBean> findDetail(@Query("articleId") String articleId,@Query("type") String type ,@Query("client") String client);

    /**
     * 获取未读消息
     */
    @GET("api/v2/message/count")
    Observable<UnMessageBean> unMessage();

    /**
     * 获取各类消息列表
     */
    @GET("api/v3/message/selectAll")
    Observable<MessageAllBean> messageAll(@Query("page") int page , @Query("categoryId") String categoryId);

    /**
     * 消息首页
     */
    @GET("api/v3/message/categoryList")
    Observable<MessageBean> message();


    /**
     * 查看未读消息 已读
     */
    @FormUrlEncoded
    @POST("api/v3/message/updateAll")
    Observable<SuccessBean> readMsg(@Field("categoryId") String categoryId);

    /**
     * 上传图片
     */
    @POST("api/uploadImage")
    @Multipart
    Observable<ImageBean> importCustomer(@Part MultipartBody.Part importFile);

    /**
     * 用户信息修改
     */
    @FormUrlEncoded
    @POST("api/user/update")
    Observable<SuccessBean> userUpdate(@FieldMap Map<String,String> param);

    /**
     * 我要反馈
     */
    @POST("api/v3/user/feedback/add")
    @FormUrlEncoded
    Observable<SuccessBean> feedBack(@Field("content") String content , @Field("type") String type);

    /**
     * 获取关注列表
     */
    @GET("api/follow/list")
    Observable<FollowBean> concer(@Query("page") String page);

    /**
     * 获取粉丝列表
     */
    @GET("api/fans/list")
    Observable<FollowBean> fansList(@Query("page") String page);

    /**
     * 积分明细
     */
    @GET("api/point/list")
    Observable<PointDetailBean> pointDetail(@Query("page") String page);

    /**
     * 签到
     */
    @POST("api/v4/point/signin")
    Observable<SignInsertBean> signInsert();

    /**
     * 补签
     */
    @FormUrlEncoded
    @POST("api/v4/point/signin2")
    Observable<SignInsertBean> noSignin(@Field("day") int day);

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
     * 赚钱页面
     */
    @GET("api/v4/point/dailytask/index")
    Observable<DailyTaskBean> DailytaskIndex();

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
    Observable<SucBeanT<MyOrderBean.DataBean>> exchange(@Field("pointGoodsId") String pointGoodsId , @Field("addressId") String addressId, @Field("total") String total);

    /**
     * 邀请好友
     */
    @GET("api/user/getQRcodeImg")
    Observable<InvitationBean> invitation();

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
     * 佣金汇总
     */
    @GET("api/v4/myCommssionSummary")
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
    Observable<WithdrawBean> withdraw(@Field("realname") String realname , @Field("account") String account, @Field("amount") String amount);

    /**
     * 获取二维码邀请图片（多张）
     */
    @GET("api/v2/user/getQRcodeImgs")
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
    Observable<SucBean<TBSreachResultBean.DataBean>> collect(@Query("page") String page ,@Query("type") String type);

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
     * 我的免单记录
     */
    @GET("api/v3/user/freeList")
    Observable<MyFreeBean>  myFreeList(@Query("applyStatus") String applyStatus , @Query("page") int page);

    /**
     * 我的订单（淘宝订单）
     */
    @GET("api/v3/user/myTaobaoOrderList")
    Observable<OrderTBBean> myTaobaoOrderList(@Query("page") int page , @Query("status") String status ,
                                              @Query("source") String source);

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
     * 填写邀请码
     */
    @FormUrlEncoded
    @POST("api/user/addInvitationCode")
    Observable<SuccessBean> addInvitationCode(@Field("invitationCode") String invitationCode);

    /**
     * 淘客商品详情
     */
    @GET("api/v3/tbk/goodsDetail")
    Observable<TBShoppingDetailBean> tbGoodsDetail(@Query("otherGoodsId") String otherGoodsId ,
                                                   @Query("source") String source);

    /**
     * 猜你喜欢
     */
    @GET("api/tbk/listSimilerGoods")
    Observable<SimilerGoodsBean> listSimilerGoods(@QueryMap Map<String,String> map);

    /**
     * 搜索结果
     */
    @GET("api/v3/tbk/searchGoods")
    Observable<TBSreachResultBean> searchTBGoods(@QueryMap Map<String,String> map);

    /**
     * 获取专属淘客链接
     */
    @GET("api/v3/tbk/getGoodsClickUrl")
    Observable<ClickUrlBean> getGoodsClickUrl(@Query("source") String source , @Query("otherGoodsId") String otherGoodsId);

    /**
     * 通过淘口令获取商品信息
     */
    @GET("api/getInfoByKeyword")
    Observable<TklBean> getGoodsByTkl(@Query("keyword") String keyword);

    /**
     * 各平台一级分类列表(商品来源1京东 2淘宝 4拼多多)
     */
    @GET("api/v2/tbk/category1")
    Observable<JDBean> tbkCategory(@Query("source") String source);

    /**
     * 淘客首页数据
     */
    @GET("api/v6/tbk/index")
    Observable<TbkIndexBean> tbkIndex();

    /**
     * 今日推荐
     */
    @GET("api/v4/tbk/commendGoodsList")
    Observable<TBSreachResultBean> commendGoodsList(@QueryMap Map<String,String> map);

    /**
     * 首页通用页面
     */
    @GET("api/tbk/getGoodsListByCategory1")
    Observable<TbCommonBean> getGoodsListByCategory1(@QueryMap Map<String,String> map);

    /**
     * 首页专题页面
     */
    @GET("api/tbk/getGoodsListBySubjectId")
    Observable<TBSreachResultBean> getGoodsListBySubjectId(@QueryMap Map<String,String> map);

    /**
     * 首页通用页的10宫格框详情接口
     */
    @GET("api/tbk/getGoodsListByCategory2")
    Observable<TBSreachResultBean> getGoodsListByCategory2(@QueryMap Map<String,String> map);

    /**
     * 抢购
     */
    @FormUrlEncoded
    @POST("api/allowance/apply")
    Observable<ImageBean> allowanceApply(@Field("allowanceGoodsId") String allowanceGoodsId);

    /**
     * 特惠购首页
     */
    @GET("api/allowance/index")
    Observable<NewPeopleBean> allowanceIndex(@Query("page") int page);

    /**
     * 获取首页邀请返弹窗信息
     */
    @GET("api/v4/getPopInfo")
    Observable<PopBean> getPopInfo(@Query("type") String type);

    /**
     * 获取首页系统活动弹窗信息
     */
    @GET("api/v4/getPopInfo")
    Observable<PopInfoBean> getPopInfo();

    /**
     * 获取首页新人一系列弹窗 4个互斥弹窗
     */
    @GET("api/v4/getNewPopInfo")
    Observable<NewPopInfoBean> getNewPopInfo();

    /**
     * 商品图文描述
     */
    @GET("api/v3/tbk/getGoodsDescImgs")
    Observable<SucBean<String>> getGoodsDescImgs(@Query("otherGoodsId") String otherGoodsId, @Query("source") String source);

    /**
     * 津贴使用记录
     */
    @GET("api/allowance/myAllowanceList")
    Observable<AllowanceRecordBean> myAllowanceList(@Query("page") int page);

    /**
     * 福利兑换列表
     */
    @GET("api/welfareList")
    Observable<WelfareBean> welfareList();

    /**
     *  后台统计
     */
    @GET("api/addEvent")
    Observable<SuccessBean> addEvent(@Query("eventId") String eventId);

    /**
     * 提现申请
     */
    @FormUrlEncoded
    @POST("api/hongbao/withdraw")
    Observable<SuccessBean> withdraw(@Field("amount") String amount);

    /**
     * 提现记录
     */
    @GET("api/hongbao/withdrawLog")
    Observable<MoneyRecordBean> withdrawLog();

    /**
     * 支付宝授权登录回调地址
     */
    @GET("api/hongbao/alipay/returnUrl")
    Observable<SucBeanT<String>> alipayLogin(@Query("authCode") String authCode);

    /**
     * 绑定支付宝
     */
    @FormUrlEncoded
    @POST("api/hongbao/bindingAlipay")
    Observable<SuccessBean> bindingAlipay(@Field("realname") String realname);

    /**
     * 获取支付宝授权请求参数
     */
    @GET("api/hongbao/alipay/getAuthInfo")
    Observable<SucBeanT<String>> getAlipayAuthInfo();

    /**
     * 特惠购详情页接口
     */
    @GET("api/allowance/detail")
    Observable<TBShoppingDetailBean> newGoodsDetail(@Query("allowanceGoodsId") String otherGoodsId);

    /**
     * 全部分类（每日精选/宣传素材）
     */
    @GET("api/moment/categoryList")
    Observable<CircleTitleBean> circleTitle(@Query("type") String type);

    /**
     * 圈子列表
     */
    @GET("api/v2/moment/list")
    Observable<CircleListBean> circleList(@QueryMap Map<String,String> map);

    /**
     * 成功分享一次
     */
    @GET("api/moment/addShare")
    Observable<SuccessBean> addShare(@Query("momentId") String momentId);

    /**
     * 获取创建分享内容
     */
    @GET("api/v4/tbk/getGoodsShareInfo")
    Observable<ShareBean> getGoodsShareInfo(@Query("otherGoodsId") String otherGoodsId,
                                            @Query("shareImgLocation") int shareImgLocation,
                                            @Query("source") String source);

    /**
     * 下载图片
     */
    @GET
    @Streaming
    Observable<ResponseBody> downLoadImg(@Url String url);

    /**
     * 下载apk
     */
    @Streaming
    @GET
    Call<ResponseBody> downloadFile(@Url String url);

    /**
     * 获取上级信息（头像和微信）
     */
    @GET("api/v3/user/getParentInfo")
    Observable<TeacherBean> getParentInfo();

    /**
     * 拼多多、京东专题页列表
     */
    @GET("api/v2/tbk/getOtherGoodsListByCategory1")
    Observable<TBSreachResultBean> getOtherGoodsListByCategory(@Query("category1Id") String category1Id,
                                                               @Query("page") int page,
                                                               @Query("source") String source);

    /**
     * 一键登录
     */
    @FormUrlEncoded
    @POST("api/signOnce")
    Observable<LoginBean> JVerifyLogin(@Field("loginToken") String loginToken);

    /**
     * 一键绑定
     */
    @FormUrlEncoded
    @POST("api/bindMobileOnce")
    Observable<LoginBean> JVerifyBind(@Field("channel") String channel, @Field("loginToken") String loginToken ,
                                      @Field("openid") String openid);

    /**
     * 获取隐私协议版本
     */
    @GET("api/v3/getPrivateVersion")
    Observable<ImageBean> getPrivateVersion();

    /**
     * 注销账号
     */
    @FormUrlEncoded
    @POST("api/destroyAccount")
    Observable<SuccessBean> destroyAccount(@Field("mobile") String mobile,@Field("code") String code);

    /**
     * 0元购免单首页列表
     */
    @GET("api/v3/free/list")
    Observable<NewFreeBean> freeList2();

    /**
     * 0元购免单详情页面
     */
    @GET("api/v3/free/detail")
    Observable<TBShoppingDetailBean> freeDetail2(@Query("freeId") String freeId);

    /**
     * 0元购免单购买
     */
    @FormUrlEncoded
    @POST("api/v3/free/apply")
    Observable<ImageBean> freeApply2(@Field("otherGoodsId") String otherGoodsId);

    /**
     * 红包分享 统计
     */
    @GET("api/v2/hongbao/share")
    Observable<SuccessBean> shareCount(@Query("hongbao2Id") String hongbao2Id, @Query("type") String type , @Query("shareType") String shareType);

    /**
     * 生成分享红包活动海报
     */
    @FormUrlEncoded
    @POST("api/v2/hongbao/createPosterImg")
    Observable<ImageBean> hbCreatePosterImg(@Field("hongbao2Id") String hongbao2Id);

    /**
     * 获取提现信息
     */
    @GET("api/v2/hongbao/getWithdrawInfo")
    Observable<WithdrawInfoBean> getWithdrawInfo();

    /**
     * 获取0元购免单分享海报
     */
    @POST("api/v3/free/getIndexPosterImg")
    Observable<ImageBean> freeGetIndexPosterImg();

    /**
     * 获取红包活动信息
     */
    @GET("api/v2/hongbao/getHongbaoActivityInfo")
    Observable<ActionHBBean> getHongbaoActivityInfo();

    /**
     * 我的团队
     */
    @GET("api/v3/user/getMyTeamInfo")
    Observable<TeamBean> getMyTeamInfo();

    /**
     * 我的粉丝列表
     */
    @GET("api/v3/user/getSubUserList")
    Observable<FansBean> getSubUserList(@QueryMap HashMap<String,String> map);

    /**
     * 获取粉丝详情信息
     */
    @GET("api/v3/user/getSubUserDetail")
    Observable<SubUserBean> getSubUserDetail(@Query("subUserId") String subUserId);

    /**
     * 我的收益
     */
    @GET("api/v3/myCommssionSum")
    Observable<MyWalletBean> myCommssionSum();

    /**
     * 历史概况
     */
    @GET("api/v3/getCommssionViewList")
    Observable<WalletHistoryBean> getCommssionViewList(@QueryMap HashMap<String, String> map);

    /**
     * 收益详情
     */
    @GET("api/v3/getCommssionDetail")
    Observable<CommssionDetailBean> getCommssionDetail2(@Query("orderTime") String orderTime , @Query("type") String type);

    /**
     * 获取专属活动链接
     */
    @GET("api/v3/tbk/genByAct")
    Observable<ImageBean> genByAct(@Query("objectId") String objectId , @Query("source") String source);

    /**
     * 抽奖记录
     */
    @GET("api/v3/user/prizeLogList")
    Observable<PrizeLogBean> prizeLogList();

    /**
     * 获取分享数据
     */
    @GET("api/v3/user/getShareInfo")
    Observable<ShareInfoBean> getShareInfo(@Query("type") int type , @Query("groupId") String groupId);

    /**
     * 关闭首页底部跑马灯消息
     */
    @POST("api/v3/tbk/closeIndexMessage")
    Observable<SuccessBean> closeIndexMessage();

    /**
     * 商学院首页
     */
    @GET("api/course/index")
    Observable<SchoolHomeBean> courseIndex();

    /**
     * 商学院专题页
     */
    @GET("api/course/listByCategoryId")
    Observable<SucBean<SchoolHomeBean.DataBean.CategoryListBean.CourseListBean>> listByCategoryId(@Query("categoryId") String categoryId, @Query("page") int page);

    /**
     * 视频详情
     */
    @GET("api/course/videoDetail")
    Observable<VideoBean> videoDetail(@Query("courseId") String courseId);

    /**
     * 添加转发次数
     */
    @GET("api/course/addShare")
    Observable<SuccessBean> addShareCourse(@Query("courseId") String courseId);

    /**
     * 搜索记录
     */
    @GET("api/course/search/log")
    Observable<SreachHistoryBean> searchCourseLog();

    /**
     * 删除全部搜索记录
     */
    @GET("api/course/search/deleteAll")
    Observable<SuccessBean> searchCourseDeleteAll();

    /**
     * 搜索课程
     */
    @GET("api/course/search")
    Observable<SucBean<SchoolHomeBean.DataBean.CategoryListBean.CourseListBean>> courseSearch(@Query("page") int page, @Query("word") String word);

    /**
     * 查询关联订单
     */
    @GET("api/v3/tbk/searchTbOrder")
    Observable<TbOrderBean> searchTbOrder(@Query("tradeId") String tradeId);

    /**
     * 找回订单
     */
    @FormUrlEncoded
    @POST("api/v3/tbk/findBackTbOrder")
    Observable<SuccessBean> findBackTbOrder(@Field("tradeId") String tradeId);

    /**
     * 添加消息推送token
     */
    @GET("api/addToken")
    Observable<SuccessBean> addToken(@Query("type") int type ,@Query("messageToken") String token);

    /**
     * 新会员接口
     */
    @GET("api/v6/user/levelIndex")
    Observable<MemberNewBean> levelIndex();

    /**
     * 购买会员页面
     */
    @GET("api/v2/order/listVipList")
    Observable<MemberBuyBean> listVipList();

    /**
     * 微信支付
     */
    @FormUrlEncoded
    @POST("api/v2/order/wxpay/pay")
    Observable<WxPayBean> wxpay(@Field("type") String type);

    /**
     * 支付宝支付
     */
    @FormUrlEncoded
    @POST("api/v2/order/alipay/pay")
    Observable<ImageBean> alipay(@Field("type") String type);

    /**
     * 家庭成员列表
     */
    @GET("api/family/list")
    Observable<FamilyBean> familyList();

    /**
     * 家庭确认加入
     */
    @FormUrlEncoded
    @POST("api/family/confirm")
    Observable<SuccessBean> familyConfirm(@Field("id") String id , @Field("userId") String userId);

    /**
     * 会员0元购首页
     */
    @GET("api/v2/allowance/vipIndex")
    Observable<NewFreeBean> vipIndex();

    /**
     * 会员商品详情
     */
    @GET("api/v2/allowance/vipDetail")
    Observable<TBShoppingDetailBean> zeroDetail(@Query("allowanceGoodsId") String allowanceGoodsId);

    /**
     * 会员0元购抢购
     */
    @FormUrlEncoded
    @POST("api/v2/allowance/vipApply")
    Observable<ImageBean> zeroApply(@Field("allowanceGoodsId") String allowanceGoodsId);

    /**
     * 领取津贴
     */
    @FormUrlEncoded
    @POST("api/allowance/addAllowance")
    Observable<SuccessBean> addAllowance(@Field("id") String id);

    /**
     * 获取首页浮窗活动信息
     */
    @GET("api/getIndexActivityInfo")
    Observable<SucBeanT<TbkIndexBean.DataBean.Ad1ListBean>> getIndexActivityInfo();

    /**
     * 浏览记录
     */
    @GET("api/v4/tbk/goodsHistory")
    Observable<TBSreachResultBean> goodsHistory(@Query("page") int page);

    /**
     * 吃喝玩乐列表
     */
    @GET("api/box/listAll")
    Observable<PlayBean> boxListAll();

    /**
     * 吃喝玩乐搜索
     */
    @GET("api/box/search")
    Observable<SucBean<TbkIndexBean.DataBean.BoxCategoryListBean.ListBean>> boxSearch(@Query("keyword") String keyword);

    /**
     * 获取拼团信息
     */
    @GET("api/group/info")
    Observable<GroupInfoBean> groupInfo(@Query("groupId") String groupId);

    /**
     * 拼团创建
     */
    @FormUrlEncoded
    @POST("api/group/create")
    Observable<SuccessBean> groupCreate(@Field("otherGoodsId") String otherGoodsId , @Field("source") String source);

    /**
     * 查看拼团状态
     */
    @GET("api/group/status")
    Observable<SucBeanT<String>> groupStatus(@Query("otherGoodsId") String otherGoodsId);

    /**
     * 秒杀分类列表
     */
    @GET("api/seckill/categoryList")
    Observable<SeckillTabBean> categoryList();

    /**
     * 秒杀商品列表
     */
    @GET("api/seckill/list")
    Observable<SeckillBean> seckillList(@Query("category2Id") String  category2Id,
                                        @Query("categoryId") String categoryId ,
                                        @Query("page") int page);

    /**
     * 秒杀详情
     */
    @GET("api/seckill/detail")
    Observable<TBShoppingDetailBean> seckillDetail(@Query("seckillGoodsId") String seckillGoodsId);

    /**
     * 极币支付
     */
    @FormUrlEncoded
    @POST("api/order/point/pay")
    Observable<SuccessBean> pointPay(@Field("type") int type);

    /**
     * 相似推荐
     */
    @GET("api/tbk/listLikeGoods")
    Observable<SimilerGoodsBean> listLikeGoods(@Query("otherGoodsId") String otherGoodsId);

    /**
     * 获取商品详情里评论信息
     */
    @GET("api/v4/tbk/getFeedback")
    Observable<CommenBean> getFeedback(@Query("otherGoodsId") String otherGoodsId);

    /**
     * 获取店铺地址
     */
    @GET("api/v3/tbk/getShopUrl")
    Observable<ImageBean> getShopUrl(@Query("otherGoodsId") String otherGoodsId);

    /**
     * 获取买多少返多少专属购买链接
     */
    @GET("api/v3/tbk/getReturnGoodsClickUrl")
    Observable<ClickUrlBean> getReturnGoodsClickUrl(@Query("otherGoodsId") String otherGoodsId);

    /**
     * 获取买多少返多少弹窗信息
     */
    @GET("api/v3/tbk/getReturnGoodsInfo")
    Observable<PopBean> getReturnGoodsInfo(@Query("otherGoodsId") String otherGoodsId);

    /**
     * 热销淘宝分类
     */
    @GET("api/v3/tbk/topCategory")
    Observable<TBCategoryBean> topCategory();

    /**
     * 热销列表
     */
    @GET("api/v3/tbk/topGoodsList")
    Observable<SimilerGoodsBean> topGoodsList(@QueryMap HashMap<String,String> map);

    /**
     * 输入兑换码
     */
    @GET("api/exchange/code")
    Observable<SuccessBean> exchangeCode(@Query("code") String code);
}
