package com.example.administrator.jipinshop.view.dialog;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.util.FileManager;
import com.example.administrator.jipinshop.util.ImageCompressUtil;
import com.example.administrator.jipinshop.util.ImageHelper;
import com.example.administrator.jipinshop.util.permission.HasPermissionsUtil;
import com.yanzhenjie.permission.Permission;

import java.io.File;

import static android.app.Activity.RESULT_CANCELED;

/**
 * @author 莫小婷
 * @create 2018/8/7
 * @Describe 图片选择dialog
 */
public class SelectPicDialog extends BottomSheetDialogFragment {

    public static final String TAG = "SelectPicDialog";
    private String sdDir = "";

    public static final int REQUEST_ALBUM = 1;
    public static final int REQUEST_CAMERA = 2;

    private TextView dialog_pic;
    private TextView dialog_cerame;
    private TextView dialog_diss;

    private ChoosePhotoCallback mChoosePhotoCallback;
    private File mSavePhotoFile;//拍照得到的图片

    public void setChoosePhotoCallback(ChoosePhotoCallback choosePhotoCallback) {
        mChoosePhotoCallback = choosePhotoCallback;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_select_pic, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        sdDir = FileManager.externalFiles(getContext());
        dialog_pic = view.findViewById(R.id.dialog_pic);
        dialog_cerame = view.findViewById(R.id.dialog_cerame);
        dialog_diss = view.findViewById(R.id.dialog_diss);

        dialog_diss.setOnClickListener(v -> dismiss());
        //调用相册
        dialog_pic.setOnClickListener(v -> selectPicFromAlbum());
        //调用相机
        dialog_cerame.setOnClickListener(v -> selectPicFromCamera());
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setDimAmount(0.35f);
    }

    /**
     * 调用相机
     */
    private void selectPicFromCamera() {
        HasPermissionsUtil.permission(getContext(),new HasPermissionsUtil(){
            //有权限了
            @Override
            public void hasPermissionsSuccess() {
                super.hasPermissionsSuccess();
                mSavePhotoFile = new File(sdDir, "jpcHeadPic_" + System.currentTimeMillis() + ".jpg");
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//7.0及以上
                    Uri uriForFile = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".provider", mSavePhotoFile);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
                    cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                } else {
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mSavePhotoFile));
                }
                startActivityForResult(cameraIntent, REQUEST_CAMERA);
            }
        },Permission.CAMERA);

    }


    /**
     * 调用相册
     */
    private void selectPicFromAlbum() {
        HasPermissionsUtil.permission(getContext(),new HasPermissionsUtil(){
            //有权限了
            @Override
            public void hasPermissionsSuccess() {
                super.hasPermissionsSuccess();
                Intent albumIntent = new Intent();
                albumIntent.setAction(Intent.ACTION_PICK);
                albumIntent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(albumIntent, REQUEST_ALBUM);
            }
        },Manifest.permission.READ_EXTERNAL_STORAGE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            dismiss();
//            Toast.makeText(getActivity(), "取消", Toast.LENGTH_LONG).show();
            return;
        }
        if (requestCode == REQUEST_ALBUM) {
            Uri imageUri = data.getData();
            String imgPathSel = ImageHelper.getImageAbsolutePath(getActivity(), imageUri);

            //这里进行了图片旋转以及图片压缩后得到新图片
            imgPathSel = ImageCompressUtil.getimage(getContext(),ImageCompressUtil.getPicture(getContext(),imgPathSel));

            if (mChoosePhotoCallback != null) {
                mChoosePhotoCallback.getAbsolutePicPath(new File(imgPathSel));
            }
            dismiss();

        } else if (requestCode == REQUEST_CAMERA) {

            String imgPathSel = mSavePhotoFile.getPath();
            //这里进行了图片旋转以及图片压缩后得到新图片
            imgPathSel = ImageCompressUtil.getimage(getContext(),ImageCompressUtil.getPicture(getContext(),imgPathSel));

            if (mChoosePhotoCallback != null) {
                mChoosePhotoCallback.getAbsolutePicPath(new File(imgPathSel));
            }
            dismiss();
        }
    }

    public interface ChoosePhotoCallback {
        void getAbsolutePicPath(File picFile);
    }

}
