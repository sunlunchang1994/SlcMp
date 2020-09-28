package android.slc.mp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.slc.medialoader.utils.MediaLoaderUriUtils;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author slc
 * @date 2019/11/28 9:30
 */
public class SlcMpFilePickerUtils {
    private final static int tailorX = 280, tailorY = 280;

    public static void takePhoto(Context context, ActivityResultCaller activityResultCaller, ActivityResultCallback<Uri> activityResultCallback) {
        takePhoto(context, activityResultCaller, MediaLoaderUriUtils.image2UriByInsert(context), activityResultCallback);
    }

    public static void takePhoto(Context context, ActivityResultCaller activityResultCaller, Uri photoUri, ActivityResultCallback<Uri> activityResultCallback) {
        activityResultCaller.registerForActivityResult(new ActivityResultContract<Uri, Uri>() {
            @NonNull
            @Override
            public Intent createIntent(@NonNull Context context, Uri input) {
                final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, input);
                return intent;
            }

            @Override
            public Uri parseResult(int resultCode, @Nullable Intent intent) {
                if (resultCode == Activity.RESULT_OK)
                    return photoUri;
                else {
                    return null;
                }

            }
        }, activityResultCallback).launch(photoUri);
    }

    public static void cutOutPhoto(Context context, ActivityResultCaller activityResultCaller, Uri photoUri, ActivityResultCallback<Uri> activityResultCallback) {
        cutOutPhoto(context, activityResultCaller, photoUri, MediaLoaderUriUtils.image2UriByInsert(context), activityResultCallback);
    }

    public static void cutOutPhoto(Context context, ActivityResultCaller activityResultCaller, Uri photoUri, Uri outPutUri, ActivityResultCallback<Uri> activityResultCallback) {
        Bundle bundle = new Bundle();
        bundle.putString("crop", "true");
        bundle.putInt("aspectX", tailorX);
        bundle.putInt("aspectY", tailorY);
        bundle.putInt("outputX", tailorX);
        bundle.putInt("outputY", tailorY);
        bundle.putBoolean("scale", true);
        bundle.putParcelable(MediaStore.EXTRA_OUTPUT, outPutUri);
        bundle.putString("outputFormat", Bitmap.CompressFormat.PNG.toString());
        bundle.putBoolean("noFaceDetection", true);
        cutOutPhoto(context, activityResultCaller, photoUri, bundle, activityResultCallback);
    }

    public static void cutOutPhoto(Context context, ActivityResultCaller activityResultCaller, Uri photoUri, Bundle bundle, ActivityResultCallback<Uri> activityResultCallback) {
        activityResultCaller.registerForActivityResult(new ActivityResultContract<Uri, Uri>() {
            @NonNull
            @Override
            public Intent createIntent(@NonNull Context context, Uri input) {
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(photoUri, context.getContentResolver().getType(input));
                intent.putExtras(bundle);
                return intent;
            }

            @Override
            public Uri parseResult(int resultCode, @Nullable Intent intent) {
                if (resultCode == Activity.RESULT_OK)
                    return bundle.getParcelable(MediaStore.EXTRA_OUTPUT);
                else {
                    return null;
                }

            }
        }, activityResultCallback).launch(photoUri);
    }

    /*public static void cutOutPhoto(Activity activity, int requestCode, Uri photoUri) {
        cutOutPhoto(activity, requestCode, photoUri, newCutPhotoPhotoSavePath());
    }

    public static void cutOutPhoto(Activity activity, int requestCode, Uri photoUri, String savePathDir) {
        Bundle bundle = new Bundle();
        bundle.putString("crop", "true");
        bundle.putInt("aspectX", tailorX);
        bundle.putInt("aspectY", tailorY);
        bundle.putInt("outputX", tailorX);
        bundle.putInt("outputY", tailorY);
        bundle.putParcelable(MediaStore.EXTRA_OUTPUT, UriUtils.image2UriByInsert(activity, new File(TextUtils.isEmpty(savePathDir) ? newCutPhotoPhotoSavePath() : savePathDir)));
        bundle.putString("outputFormat", Bitmap.CompressFormat.PNG.toString());
        bundle.putBoolean("noFaceDetection", true);
        cutOutPhoto(activity, requestCode, photoUri, bundle);
    }

    public static void cutOutPhoto(Activity activity, int requestCode, Uri photoUri, Bundle bundle) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(photoUri, "image/*");
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, requestCode);
    }*/

    /**
     * 获取一个新照片存储路径
     * 路径获取规则为本机照片存储路径加上过根据时间戳生成的文件名
     *
     * @return
     */
    public static String newTakePhotoSavePath() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()
                + "/Camera/" + SlcMpFilePickerUtils.getFileNameByTime("IMG", "jpg");
    }

    /**
     * 获取一个新照片存储路径
     * 路径获取规则为本机照片存储路径加上过根据时间戳生成的文件名
     *
     * @return
     */
    public static String newCutPhotoPhotoSavePath() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()
                + "/Camera/" + SlcMpFilePickerUtils.getFileNameByTime("IMG", "png");
    }

    /**
     * 通知扫描
     *
     * @param context
     * @param filePath
     */
    public static void notifyMediaScannerScanFile(Context context, MediaScannerConnection.MediaScannerConnectionClient mediaScannerConnectionClient,
                                                  final String... filePath) {
        MediaScannerConnection.scanFile(context, filePath, null, mediaScannerConnectionClient);
    }

    /**
     * @param timeFormatHeader 格式化的头(除去时间部分)
     * @param extension        后缀名
     * @return 返回时间格式化后的文件名
     */
    public static String getFileNameByTime(String timeFormatHeader, String extension) {
        return getTimeFormatName(timeFormatHeader) + "." + extension;
    }

    //TODO 后加
    private static final String TIME_FORMAT = "_yyyyMMdd_HHmmss";

    private static String getTimeFormatName(String timeFormatHeader) {
        final Date date = new Date(System.currentTimeMillis());
        //必须要加上单引号
        final SimpleDateFormat dateFormat = new SimpleDateFormat("'" + timeFormatHeader + "'" + TIME_FORMAT,
                Locale.getDefault());
        return dateFormat.format(date);
    }
}
