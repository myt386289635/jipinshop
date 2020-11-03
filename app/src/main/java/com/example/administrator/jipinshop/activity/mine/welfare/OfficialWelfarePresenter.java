package com.example.administrator.jipinshop.activity.mine.welfare;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;

import com.example.administrator.jipinshop.netwrok.Repository;
import com.example.administrator.jipinshop.util.FileManager;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.io.File;
import java.util.Observable;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * @author 莫小婷
 * @create 2020/11/2
 * @Describe
 */
public class OfficialWelfarePresenter {

    private Repository mRepository;
    private OfficialWelfareView mView;

    @Inject
    public OfficialWelfarePresenter(Repository repository) {
        mRepository = repository;
    }

    public void setView(OfficialWelfareView view) {
        mView = view;
    }

    public void downLoadImg(Context context , String url , LifecycleTransformer<ResponseBody> transformer){
        mRepository.downLoadImg(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(responseBody -> {
                    byte[] bys = responseBody.bytes();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bys, 0, bys.length);
                    FileManager.saveFile(bitmap, context);
                    mView.onSuccess();
                }, throwable -> {
                    mView.onFile(throwable.getMessage());
                });
    }
}
