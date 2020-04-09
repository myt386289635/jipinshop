package com.example.administrator.jipinshop.fragment.circle.daily

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.aspsine.swipetoloadlayout.OnLoadMoreListener
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.blankj.utilcode.util.SPUtils
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.home.MainActivity
import com.example.administrator.jipinshop.activity.login.LoginActivity
import com.example.administrator.jipinshop.activity.shoppingdetail.tbshoppingdetail.TBShoppingDetailActivity
import com.example.administrator.jipinshop.activity.web.TaoBaoWebActivity
import com.example.administrator.jipinshop.adapter.DailyAdapter
import com.example.administrator.jipinshop.adapter.KTTabAdapter2
import com.example.administrator.jipinshop.adapter.KTTabAdapter3
import com.example.administrator.jipinshop.base.DBBaseFragment
import com.example.administrator.jipinshop.bean.CircleListBean
import com.example.administrator.jipinshop.bean.CircleTitleBean
import com.example.administrator.jipinshop.databinding.FragmentDailyBinding
import com.example.administrator.jipinshop.util.ShareUtils
import com.example.administrator.jipinshop.util.TaoBaoUtil
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.util.share.PlatformUtil
import com.example.administrator.jipinshop.util.sp.CommonDate
import com.example.administrator.jipinshop.view.dialog.DialogUtil
import com.example.administrator.jipinshop.view.dialog.PopView
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView
import com.example.administrator.jipinshop.view.dialog.ShareBoardDialog4
import com.example.administrator.jipinshop.view.pick.DecorationLayout
import com.github.ielse.imagewatcher.ImageWatcherHelper
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.bean.SHARE_MEDIA
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2020/3/16
 * @Describe 每日精选
 */
class DailyFragment : DBBaseFragment(), KTTabAdapter2.OnClickItem, KTTabAdapter3.OnClickItem, View.OnClickListener, PopView.OnClick, OnRefreshListener, OnLoadMoreListener, DailyView, DailyAdapter.OnClickItem, ShareBoardDialog4.onShareListener {

    @Inject
    lateinit var mPresenter: DailyPresenter

    private lateinit var mBinding: FragmentDailyBinding
    //二级菜单
    private lateinit var mTabAdapter: KTTabAdapter2
    private lateinit var mTitle : MutableList<CircleTitleBean.DataBean>
    private var FirstSet = 0//二级菜单位置
    //三级菜单
    private lateinit var mSendTitle : MutableList<CircleTitleBean.DataBean.ChildrenBean>
    private lateinit var mTabSendAdapter: KTTabAdapter3
    private var SendSet = 0//三级菜单位置
    //pop菜单
    private lateinit var mPop: PopView
    //列表
    private lateinit var mList: MutableList<CircleListBean.DataBean>
    private lateinit var mAdapter: DailyAdapter
    private var once = true
    private var type = "1" //默认是每日精选
    private var page = 1
    private var refersh: Boolean = true
    private var mDialog: Dialog? = null
    private var mShareBoardDialog: ShareBoardDialog4? = null
    private var mSharePosition = -1;//分享位置
    private lateinit var mShareImages : MutableList<String>
    private var sharePyqCode = 0//分享朋友圈返回code
    private var iwHelper: ImageWatcherHelper? = null
    private var layDecoration: DecorationLayout? = null

    companion object{
        fun getInstance(type : String): DailyFragment {
            var fragment = DailyFragment()
            var bundle = Bundle()
            bundle.putString("type", type)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && once) {
            if (arguments!!.getString("type","1") != "1") {
                type = arguments!!.getString("type","1")
                mPresenter.circleTitle(type,this.bindToLifecycle())
            }
        }
    }

    override fun initLayout(inflater: LayoutInflater?, container: ViewGroup?): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_daily,container,false)
        mBinding.listener = this
        mBaseFragmentComponent.inject(this)
        mPresenter.setView(this)
        return mBinding.root
    }

    override fun initView() {
        mTitle = mutableListOf()
        var commonNavigator = CommonNavigator(context)
        commonNavigator.leftPadding = context!!.resources.getDimension(R.dimen.x5).toInt()
        commonNavigator.rightPadding = context!!.resources.getDimension(R.dimen.x5).toInt()
        mTabAdapter = KTTabAdapter2(mTitle,this)
        commonNavigator.adapter = mTabAdapter
        mBinding.dailyMainMenu.navigator = commonNavigator

        mSendTitle = mutableListOf()
        var commonNav = CommonNavigator(context)
        commonNav.leftPadding = context!!.resources.getDimension(R.dimen.x20).toInt()
        commonNav.rightPadding = context!!.resources.getDimension(R.dimen.x20).toInt()
        mTabSendAdapter = KTTabAdapter3(mSendTitle,this)
        commonNav.adapter = mTabSendAdapter
        mBinding.dailyMenu.navigator = commonNav

        mPop = PopView(context,this)

        mShareImages = mutableListOf()
        mList = mutableListOf()
        type = arguments!!.getString("type","1")
        mAdapter = DailyAdapter(mList,context!!)
        mAdapter.setOnClick(this)
        mBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        mBinding.recyclerView.adapter = mAdapter

        mPresenter.solveScoll(mBinding.recyclerView,mBinding.swipeToLoad)
        mBinding.swipeToLoad.setOnRefreshListener(this)
        mBinding.swipeToLoad.setOnLoadMoreListener(this)
        if (type == "1"){
            mPresenter.circleTitle(type,this.bindToLifecycle())
        }
    }

    //二级菜单点击事件
    override fun onFirstMenu(index: Int) {
        FirstSet = index
        mBinding.dailyMainMenu.onPageSelected(FirstSet)//选中该index位置
        mBinding.dailyMainMenu.onPageScrolled(FirstSet, 0.0F, 0)//滑到该index位置
        //三级菜单数据
        if (mTitle[FirstSet].children.size == 0){
            mBinding.dailySendMenu.visibility = View.GONE
        }else{
            mBinding.dailySendMenu.visibility = View.VISIBLE
            mSendTitle.clear()
            mSendTitle.addAll(mTitle[FirstSet].children)
            SendSet = 0
            mTabSendAdapter.notifyDataSetChanged()
            mBinding.dailyMenu.onPageSelected(SendSet)
            mBinding.dailyMenu.onPageScrolled(SendSet,0.0F, 0)
        }
        mDialog = ProgressDialogView().createLoadingDialog(context, "")
        mDialog?.show()
        onRefresh()
    }

    //三级菜单点击事件
    override fun onSendMenu(index: Int) {
        SendSet = index
        mBinding.dailyMenu.onPageSelected(SendSet)
        mBinding.dailyMenu.onPageScrolled(SendSet,0.0F, 0)
        mDialog = ProgressDialogView().createLoadingDialog(context, "")
        mDialog?.show()
        onRefresh()
    }

    override fun onClick(v: View) {
       when(v.id){
           R.id.daily_down -> {
               mPop.show(mBinding.dailyMainMenu,mSendTitle,SendSet)
           }
       }
    }

    //pop下拉菜单点击时间
    override fun onPopItemOnClick(pos: Int) {
        onSendMenu(pos)
    }

    override fun onLoadMore() {
        page++
        refersh = false
        if (mTitle[FirstSet].children.size == 0){
            mPresenter.circleList(mTitle[FirstSet].id,"",type,page,this.bindToLifecycle())
        }else{
            mPresenter.circleList(mTitle[FirstSet].id,mSendTitle[SendSet].id,type,page,this.bindToLifecycle())
        }
    }

    override fun onRefresh() {
        page = 1
        refersh = true
        if (mTitle[FirstSet].children.size == 0){
            mPresenter.circleList(mTitle[FirstSet].id,"",type,page,this.bindToLifecycle())
        }else{
            mPresenter.circleList(mTitle[FirstSet].id,mSendTitle[SendSet].id,type,page,this.bindToLifecycle())
        }
    }

    fun dissRefresh() {
        if (mBinding.swipeToLoad.isRefreshing) {
            if (!mBinding.swipeToLoad.isRefreshEnabled) {
                mBinding.swipeToLoad.isRefreshEnabled = true
                mBinding.swipeToLoad.isRefreshing = false
                mBinding.swipeToLoad.isRefreshEnabled = false
            } else {
                mBinding.swipeToLoad.isRefreshing = false
            }
        }
        mDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
    }

    fun dissLoading() {
        if (mBinding.swipeToLoad.isLoadingMore) {
            if (!mBinding.swipeToLoad.isLoadMoreEnabled) {
                mBinding.swipeToLoad.isLoadMoreEnabled = true
                mBinding.swipeToLoad.isLoadingMore = false
                mBinding.swipeToLoad.isLoadMoreEnabled = false
            } else {
                mBinding.swipeToLoad.isLoadingMore = false
            }
        }
    }

    fun initError(id: Int, title: String, content: String) {
        mBinding.netClude?.run {
            qsNet.visibility = View.VISIBLE
            errorImage.setBackgroundResource(id)
            errorTitle.text = title
            errorContent.text = content
        }
    }

    override fun onTabSuc(bean: CircleTitleBean) {
        mTitle.clear()
        mTitle.addAll(bean.data)
        if (bean.data[FirstSet].children.size == 0){
            mBinding.dailySendMenu.visibility = View.GONE
        }else{
            mBinding.dailySendMenu.visibility = View.VISIBLE
            mSendTitle.clear()
            mSendTitle.addAll(bean.data[FirstSet].children)
            mTabSendAdapter.notifyDataSetChanged()
        }
        mTabAdapter.notifyDataSetChanged()
        mBinding.swipeToLoad.isRefreshing = true
    }

    override fun onTabFile(error: String?) {
        dissRefresh()
        ToastUtil.show(error)
    }

    override fun onListSuc(bean: CircleListBean) {
        if (refersh) {
            dissRefresh()
            if (bean.data != null && bean.data.size != 0) {
                if (mBinding.recyclerView.visibility == View.GONE){
                    //从暂无数据时变为有一条数据时，为了解决与上拉加载的冲突
                    mBinding.swipeToLoad.isLoadMoreEnabled = false
                }
                mBinding.netClude?.let {
                    it.qsNet.visibility = View.GONE
                }
                mBinding.recyclerView.visibility = View.VISIBLE
                mList.clear()
                mList.addAll(bean.data)
                mAdapter.notifyDataSetChanged()
            } else {
                initError(R.mipmap.qs_nodata, "暂无数据", "暂时没有任何数据 ")
                mBinding.recyclerView.visibility = View.GONE
            }
        } else {
            dissLoading()
            if (bean.data != null && bean.data.size != 0) {
                mList.addAll(bean.data)
                mAdapter.notifyDataSetChanged()
                mBinding.swipeToLoad.isLoadMoreEnabled = false
            } else {
                page--
                ToastUtil.show("已经是最后一页了")
            }
        }
    }

    override fun onListFile(error: String?) {
        if (refersh) {
            dissRefresh()
            initError(R.mipmap.qs_net, "网络出错", "哇哦，网络出错了，换个姿势下滑页面试试")
            mBinding.recyclerView.visibility = View.GONE
        } else {
            dissLoading()
            page--
        }
        ToastUtil.show(error)
    }

    override fun onDetailClick(position: Int) {
        startActivity(Intent(context, TBShoppingDetailActivity::class.java)
                .putExtra("otherGoodsId", mList[position].goodsInfo.otherGoodsId)
        )
    }

    override fun onShareClick(position: Int) {
        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))){
            startActivity(Intent(context,LoginActivity::class.java))
        }else{
            val specialId = SPUtils.getInstance(CommonDate.USER).getString(CommonDate.relationId, "")
            if (TextUtils.isEmpty(specialId) || specialId == "null") run {
                TaoBaoUtil.aliLogin { topAuthCode ->
                    startActivity(Intent(context, TaoBaoWebActivity::class.java)
                            .putExtra(TaoBaoWebActivity.url, "https://oauth.taobao.com/authorize?response_type=code&client_id=25612235&redirect_uri=https://www.jipincheng.cn/qualityshop-api/api/taobao/returnUrl&state=" + SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token) + "&view=wap")
                            .putExtra(TaoBaoWebActivity.title, "淘宝授权")
                    )
                }
            } else {
                mSharePosition = position
                if (mShareBoardDialog == null) {
                    mShareBoardDialog = ShareBoardDialog4.getInstance("批量存图")
                    mShareBoardDialog?.setOnShareListener(this)
                }
                mShareBoardDialog?.let {
                    if (!it.isAdded){
                        it.show(childFragmentManager,"ShareBoardDialog")
                    }
                }
            }
        }
    }

    override fun onCommentClick(content: String) {
        if (TextUtils.isEmpty(SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token, ""))) {
            startActivity(Intent(context, LoginActivity::class.java))
            return
        }
        val specialId = SPUtils.getInstance(CommonDate.USER).getString(CommonDate.relationId, "")
        if (TextUtils.isEmpty(specialId) || specialId == "null") run {
            TaoBaoUtil.aliLogin { topAuthCode ->
                startActivity(Intent(context, TaoBaoWebActivity::class.java)
                        .putExtra(TaoBaoWebActivity.url, "https://oauth.taobao.com/authorize?response_type=code&client_id=25612235&redirect_uri=https://www.jipincheng.cn/qualityshop-api/api/taobao/returnUrl&state=" + SPUtils.getInstance(CommonDate.USER).getString(CommonDate.token) + "&view=wap")
                        .putExtra(TaoBaoWebActivity.title, "淘宝授权")
                )
            }
        } else {
            var clip = context!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            var clipData = ClipData.newPlainText("jipinshop", content)
            clip.primaryClip = clipData
            SPUtils.getInstance().put(CommonDate.CLIP, content)
            DialogUtil.LoginDialog(context,"评论内容复制成功","去微信粘贴","暂不粘贴"){
                var intent : Intent? = PlatformUtil.sharePYQ_images(context)
                intent?.let {
                    startActivity(intent)
                }
            }
        }
    }

    override fun share(share_media: SHARE_MEDIA?) {
        mPresenter.addShare(mList[mSharePosition].id,this.bindToLifecycle())
        var bigDecimal = BigDecimal(mList[mSharePosition].shareNumber)
        mList[mSharePosition].shareNumber = "" + (bigDecimal.toInt() + 1)
        mAdapter.notifyDataSetChanged()
        mDialog = ProgressDialogView().createLoadingDialog(context, "")
        mDialog?.let {
            if (!it.isShowing)
                it.show()
        }
        if (mList[mSharePosition].goodsInfo == null){
            onShare(share_media,"")
        }else{
            mPresenter.getGoodsShareInfo(share_media,mList[mSharePosition].goodsInfo.otherGoodsId,1,this.bindToLifecycle())
        }
    }

    override fun download(type: Int) {
        mPresenter.addShare(mList[mSharePosition].id,this.bindToLifecycle())
        var bigDecimal = BigDecimal(mList[mSharePosition].shareNumber)
        mList[mSharePosition].shareNumber = "" + (bigDecimal.toInt() + 1)
        mAdapter.notifyDataSetChanged()
        mDialog = ProgressDialogView().createLoadingDialog(context, "")
        mDialog?.let {
            if (!it.isShowing)
                it.show()
        }
        if (mList[mSharePosition].goodsInfo == null){
            onShare(SHARE_MEDIA.WEIXIN_CIRCLE,"")
        }else{
            mPresenter.getGoodsShareInfo(SHARE_MEDIA.WEIXIN_CIRCLE,mList[mSharePosition].goodsInfo.otherGoodsId,1,this.bindToLifecycle())
        }
    }

    override fun onShareSuccess(shareImage: String,share_media: SHARE_MEDIA?) {
        onShare(share_media,shareImage)
    }

    override fun onShareFile(share_media: SHARE_MEDIA?) {
        onShare(share_media,"")
    }

    private fun onShare(share_media: SHARE_MEDIA?, temp: String){
        var images = mutableListOf<String>()
        images.addAll(mList[mSharePosition].imgList)
        if (!TextUtils.isEmpty(temp)){
            images[0] = temp
        }
        when (share_media) {
            SHARE_MEDIA.SINA -> {
                var clip = context!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                var clipData = ClipData.newPlainText("jipinshop", mList[mSharePosition].content)
                clip.primaryClip = clipData
                SPUtils.getInstance().put(CommonDate.CLIP, mList[mSharePosition].content)
                ToastUtil.show("文案已复制到粘贴板,分享后长按粘贴")
                ShareUtils(context,share_media,mDialog)
                        .shareImages(activity,mList[mSharePosition].content,images)
            }
            SHARE_MEDIA.WEIXIN,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN_CIRCLE -> {
                mPresenter.downLoadImg(context!!,images,share_media,this.bindToLifecycle())
            }
        }
    }

    /**
     * 图片下载完成
     */
    override fun downLoadSuc(share_media: SHARE_MEDIA?,imageUris: ArrayList<Uri>) {
        dissRefresh()
        var clip = context!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        var clipData = ClipData.newPlainText("jipinshop", mList[mSharePosition].content)
        clip.primaryClip = clipData
        SPUtils.getInstance().put(CommonDate.CLIP, mList[mSharePosition].content)
        when(share_media){
            SHARE_MEDIA.WEIXIN -> {
                ToastUtil.show("文案已复制到粘贴板,分享后长按粘贴")
                var intent: Intent? = PlatformUtil.shareWX_images(context, imageUris)
                intent?.let {
                    startActivityForResult(intent, 301)
                }
            }
            SHARE_MEDIA.QQ -> {
                ToastUtil.show("文案已复制到粘贴板,分享后长按粘贴")
                var intent: Intent? = PlatformUtil.shareQQ_images(context, imageUris)
                intent?.let {
                    startActivityForResult(intent, 302)
                }
            }
            SHARE_MEDIA.WEIXIN_CIRCLE -> {
                DialogUtil.sharePYQDialog(context) {
                    var intent: Intent? = PlatformUtil.sharePYQ_images(context)
                    intent?.let {
                        sharePyqCode = 303
                        startActivity(intent)
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(context).onActivityResult(requestCode, resultCode, data)
        when(requestCode){//分享微信与QQ返回
            301,302 -> {
                ToastUtil.show("图片分享成功,复制评论区内容才能获得收益哦")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        UMShareAPI.get(context).release()
    }

    override fun onResume() {
        super.onResume()
        if (sharePyqCode == 303){
            ToastUtil.show("图片分享成功,复制评论区内容才能获得收益哦")
            sharePyqCode = 0
        }
        mDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity is ImageWatcherHelper.Provider) {
            iwHelper = (activity as ImageWatcherHelper.Provider).iwHelper()
        }
        if (activity is MainActivity) {
            layDecoration = (activity as MainActivity).layDecoration
        }
    }

    override fun onGridImage(fatherPos: Int, sonPos: Int, imgs: MutableList<String>, view: View) {
        iwHelper?.let { iwHelper ->
            var pictureList : MutableList<Uri> = mutableListOf()
            for (i in imgs.indices) {
                pictureList.add(Uri.parse(imgs[i]))
            }
            layDecoration?.setDataList(pictureList)
            val mVisiblePictureList = SparseArray<ImageView>()
            mVisiblePictureList.put(sonPos, view as ImageView)
            iwHelper.show(view, mVisiblePictureList, pictureList)
        }
    }

}