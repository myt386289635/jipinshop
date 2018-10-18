package com.example.administrator.jipinshop.view.dialog;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.jipinshop.R;
import com.example.administrator.jipinshop.util.DistanceHelper;
import com.example.administrator.jipinshop.util.FileManager;
import com.example.administrator.jipinshop.util.ImageCompressUtil;
import com.example.administrator.jipinshop.util.ImageHelper;
import com.example.administrator.jipinshop.view.RuntimeRationale;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Setting;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

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
        if (AndPermission.hasPermissions(getContext(), Permission.Group.CAMERA)) {
            //有权限了
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
        } else {
            requestPermission(REQUEST_CAMERA, Permission.Group.CAMERA);
        }
    }


    /**
     * 调用相册
     */
    private void selectPicFromAlbum() {
        if (AndPermission.hasPermissions(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //有权限了
            Intent albumIntent = new Intent();
            albumIntent.setAction(Intent.ACTION_PICK);
            albumIntent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(albumIntent, REQUEST_ALBUM);
        } else {
            requestPermission(REQUEST_ALBUM, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
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

    private void requestPermission(final int type, String... permissions) {
        AndPermission.with(this)
                .runtime()
                .permission(permissions)
                .rationale(new RuntimeRationale())
                .onGranted(permissions1 -> {
//                        Toast.makeText(getContext(), "成功", Toast.LENGTH_SHORT).show();
                    if (type == REQUEST_ALBUM) {
                        selectPicFromAlbum();
                    } else {
                        selectPicFromCamera();
                    }
                })
                .onDenied(permissions12 -> {
//                        Toast.makeText(getContext(), "失败", Toast.LENGTH_SHORT).show();
                    if (AndPermission.hasAlwaysDeniedPermission(getContext(), permissions12)) {
                        showSettingDialog(getContext(), permissions12);
                    }
                })
                .start();
    }

    /**
     * Display setting dialog.
     * 这个是用户勾了再也不要提示后，请求权限失败调用该方法
     */
    public void showSettingDialog(Context context, final List<String> permissions) {
        List<String> permissionNames = Permission.transformText(context, permissions);
        String message = "点击设置打开" + permissionNames.get(0) + "权限";
        String title = "该操作需要访问您的" + permissionNames.get(0) + "权限";
        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.setting, (dialog, which) -> setPermission())
                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                })
                .show();
    }

    /**
     * Set permissions.
     */
    private void setPermission() {
        AndPermission.with(this)
                .runtime()
                .setting()
                .onComeback(() -> {
//                        Toast.makeText(getContext(), R.string.message_setting_comeback, Toast.LENGTH_SHORT).show();
                })
                .start();
    }

    public interface ChoosePhotoCallback {
        void getAbsolutePicPath(File picFile);
    }

}
