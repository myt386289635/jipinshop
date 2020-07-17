package com.example.administrator.jipinshop.activity.school.search

import android.app.Dialog
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.example.administrator.jipinshop.R
import com.example.administrator.jipinshop.activity.school.search.result.SchoolResultActivity
import com.example.administrator.jipinshop.base.BaseActivity
import com.example.administrator.jipinshop.bean.SreachHistoryBean
import com.example.administrator.jipinshop.databinding.ActivitySreachSchoolBinding
import com.example.administrator.jipinshop.util.ToastUtil
import com.example.administrator.jipinshop.view.dialog.DialogUtil
import com.example.administrator.jipinshop.view.dialog.ProgressDialogView
import javax.inject.Inject

/**
 * @author 莫小婷
 * @create 2020/7/16
 * @Describe 搜索首页
 */
class SchoolSearchActivity : BaseActivity(), View.OnClickListener, SchoolSearchView {

    @Inject
    lateinit var mPresenter: SchoolSearchPresenter
    private lateinit var mBinding : ActivitySreachSchoolBinding
    private var mDialog: Dialog? = null
    private lateinit var mHotText: MutableList<SreachHistoryBean.DataBean.HotWordListBean>
    private lateinit var mHistroyList: MutableList<SreachHistoryBean.DataBean.LogListBean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_sreach_school)
        mBinding.listener = this
        mBaseActivityComponent.inject(this)
        mPresenter.setView(this)
        initView()
    }

    private fun initView() {
        showKeyboardDelayed(mBinding.searchEdit)
        mDialog = ProgressDialogView().createLoadingDialog(this, "")
        mDialog?.let {
            if (!it.isShowing)
                it.show()
        }
        mHotText = mutableListOf()
        mHistroyList = mutableListOf()
        mBinding.searchEdit.setOnEditorActionListener { textView, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEND
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || event != null && KeyEvent.KEYCODE_ENTER == event!!.keyCode && KeyEvent.ACTION_DOWN == event!!.action) {
                if (TextUtils.isEmpty(mBinding.searchEdit.text.toString().trim())) {
                    ToastUtil.show("请输入搜索内容")
                    return@setOnEditorActionListener false
                }
                startActivity(Intent(this, SchoolResultActivity::class.java)
                        .putExtra("content", mBinding.searchEdit.text.toString())
                )
            }
            return@setOnEditorActionListener false
        }
    }

    override fun onResume() {
        super.onResume()
        mPresenter.setDate(this.bindToLifecycle())
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.title_back -> {
                finish()
            }
            R.id.search_search ->{
                //搜索
                if (TextUtils.isEmpty(mBinding.searchEdit.text.toString().trim())) {
                    ToastUtil.show("请输入搜索内容")
                    return
                }
                startActivity(Intent(this, SchoolResultActivity::class.java)
                        .putExtra("content", mBinding.searchEdit.text.toString())
                )
            }
            R.id.search_delete -> {
                //删除
                DialogUtil.listingDetele(this, "确认删除全部历史记录？", "确定", "取消", false) { v ->
                    mDialog = ProgressDialogView().createLoadingDialog(this, "")
                    mDialog?.let {
                        if (!it.isShowing)
                            it.show()
                    }
                    mPresenter.searchCourseDeleteAll(this.bindToLifecycle())
                }
            }
        }
    }

    override fun onSuccess(bean: SreachHistoryBean) {
        mDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
        mHotText.clear()
        mHotText.addAll(bean.data.hotWordList)
        mPresenter.initHot(this, mBinding.searchHot, mHotText)
        if (bean.data.logList.size != 0) {
            mBinding.sreachHisContainer.visibility = View.VISIBLE
            mHistroyList.clear()
            mHistroyList.addAll(bean.data.logList)
            mPresenter.initHistroy(this, mBinding.searchHistory, mHistroyList)
        } else {
            mBinding.sreachHisContainer.visibility = View.GONE
        }
    }

    override fun onFile(error: String?) {
        mDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
        ToastUtil.show(error)
    }

    override fun onDelect() {
        mDialog?.let {
            if (it.isShowing){
                it.dismiss()
            }
        }
        ToastUtil.show("清除成功")
        mBinding.sreachHisContainer.visibility = View.GONE
    }

    override fun jump(content: String) {
        mBinding.searchEdit.setText(content)
        mBinding.searchEdit.setSelection(mBinding.searchEdit.text.length)//将光标移至文字末尾
        startActivity(Intent(this, SchoolResultActivity::class.java)
                .putExtra("content", mBinding.searchEdit.text.toString())
        )
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val view = currentFocus
            if (isHideInput(view, ev)) {
                showKeyboard(false)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun isHideInput(v: View?, ev: MotionEvent): Boolean {
        if (v != null && v is EditText) {
            val l = intArrayOf(0, 0)
            v.getLocationInWindow(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + v.height
            val right = left + v.width
            return !(ev.x > left && ev.x < right && ev.y > top
                    && ev.y < bottom)
        }
        return false
    }

}