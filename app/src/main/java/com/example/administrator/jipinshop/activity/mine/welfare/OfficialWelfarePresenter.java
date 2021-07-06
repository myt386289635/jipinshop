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

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
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

    public void downLoadImg(Context context, String url, LifecycleTransformer<ResponseBody> transformer) {
        mRepository.downLoadImg(url)
                .compose(transformer)
                .map(new Function<ResponseBody, File>() {
                    @Override
                    public File apply(ResponseBody responseBody) throws Exception {
                        byte[] bys = responseBody.bytes();
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bys, 0, bys.length);
                        return FileManager.saveFile(bitmap, context);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(file -> {
                    mView.onSuccess();
                }, throwable -> {
                    mView.onFile(throwable.getMessage());
                });
    }
}
