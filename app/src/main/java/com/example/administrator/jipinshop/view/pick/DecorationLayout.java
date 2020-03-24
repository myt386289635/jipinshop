package com.example.administrator.jipinshop.view.pick;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.util.FileManager;
import com.example.administrator.jipinshop.util.NavigationBarUtil;
import com.example.administrator.jipinshop.util.ToastUtil;
import com.github.ielse.imagewatcher.ImageWatcher;
import com.github.ielse.imagewatcher.ImageWatcherHelper;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class DecorationLayout extends FrameLayout implements ViewPager.OnPageChangeListener{

    private ImageWatcherHelper mHolder;
    private View vDownload;
    private int currentPosition;
    private int mPagerPositionOffsetPixels;
    private List<Uri> mDataList;
    private Context mContext;
    private LinearLayout mLinearLayout;

    public void setDataList(List<Uri> dataList) {
        mDataList = dataList;
    }

    public DecorationLayout(Context context) {
        this(context, null);
        mContext = context;
    }

    public DecorationLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        final FrameLayout vContainer = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.layout_watcher_decoration, this);
        mLinearLayout = vContainer.findViewById(R.id.navigationBar);
        if (NavigationBarUtil.checkDeviceHasNavigationBar(context)){
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mLinearLayout.getLayoutParams();
            layoutParams.height = NavigationBarUtil.getVirtualBarHeigh(context);
            mLinearLayout.setLayoutParams(layoutParams);
        }
    }

    public void attachImageWatcher(ImageWatcherHelper iwHelper) {
        mHolder = iwHelper;
    }

//
//    @Override
//    public void onClick(View v) {
//        if (mPagerPositionOffsetPixels != 0) return;
//        if (mDataList == null || mDataList.size() == 0) return;
//        if (v.getId() == R.id.vDownload) {
//            final int clickPosition = currentPosition;
//            HasPermissionsUtil.permission(mContext,new HasPermissionsUtil(){
//                @Override
//                public void hasPermissionsSuccess() {
//                    super.hasPermissionsSuccess();
//                    Glide.with(mContext)
//                            .asBitmap()
//                            .load(mDataList.get(clickPosition))
//                            .into(new SimpleTarget<Bitmap>() {
//                                @Override
//                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                                    saveFile(resource);
//                                }
//                            });
//                }
//            }, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        }
//    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
        mPagerPositionOffsetPixels = i1;
        notifyAlphaChanged(v);
    }

    @Override
    public void onPageSelected(int i) {
        currentPosition = i;
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    private void notifyAdapterItemChanged(int position, Uri newUri) {
        if (mHolder != null) {
            final ImageWatcher iw = mHolder.getImageWatcher();
            if (iw != null) {
                iw.notifyItemChanged(position, newUri);
            }
        }
    }

    private void notifyAlphaChanged(float p) {
        if (0 < p && p <= 0.2f) {
            float r = (0.2f - p) * 5;
            setAlpha(r);
        } else if (0.8f <= p && p < 1) {
            float r = (p - 0.8f) * 5;
            setAlpha(r);
        } else if (p == 0) {
            setAlpha(1f);
        } else {
            setAlpha(0f);
        }
    }

    /**
     * 保存图片
     * @param bm
     * @throws IOException
     */
    public void saveFile(Bitmap bm ){
        String sdDir = FileManager.externalFiles(mContext);
        String str =  sdDir + "/"+ System.currentTimeMillis() + "bitmap.jpg";
        File myCaptureFile = new File(str);
        if (myCaptureFile.exists()) {
            myCaptureFile.delete();
        }
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();
            //刷新图库
            MediaStore.Images.Media.insertImage(mContext.getContentResolver(),
                    myCaptureFile.getAbsolutePath(), myCaptureFile.getName(), null);
            ToastUtil.show("保存地址：" + str);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //把图片保存后声明这个广播事件通知系统相册有新图片到来
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(myCaptureFile);
        intent.setData(uri);
        mContext.sendBroadcast(intent);
    }

}
