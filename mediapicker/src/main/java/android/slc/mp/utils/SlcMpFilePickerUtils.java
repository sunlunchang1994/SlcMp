package android.slc.mp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.slc.medialoader.utils.MediaLoaderUriUtils;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.net.UriCompat;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author slc
 * @date 2019/11/28 9:30
 */
public class SlcMpFilePickerUtils {
    public static void takePhoto(Context context,
                                 ActivityResultCaller activityResultCaller,
                                 ActivityResultCallback<Uri> activityResultCallback) {
        takePhoto(context, activityResultCaller, MediaLoaderUriUtils.image2UriByInsert(context), activityResultCallback);
    }

    public static void takePhoto(Context context,
                                 ActivityResultCaller activityResultCaller,
                                 Uri photoUri,
                                 ActivityResultCallback<Uri> activityResultCallback) {
        activityResultCaller.registerForActivityResult(new ActivityResultContract<Uri, Uri>() {
            @NonNull
            @Override
            public Intent createIntent(@NonNull Context context, Uri input) {
                final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, input);
                return intent;
            }

            @Override
            public Uri parseResult(int resultCode, @Nullable Intent intent) {
                if (resultCode == Activity.RESULT_OK) {
                    return photoUri;
                } else {
                    return null;
                }
            }
        }, result -> {
            if (result != null) {
                MediaLoaderUriUtils.notifyMediaScannerScanFile(context.getApplicationContext(), (path, uri) -> new Handler(Looper.getMainLooper()).post(() -> activityResultCallback.onActivityResult(uri)), result);
            } else {
                activityResultCallback.onActivityResult(result);
            }
        }).launch(photoUri);
    }

    public static void cutOutPhoto(Context context,
                                   ActivityResultCaller activityResultCaller,
                                   Uri photoUri,
                                   ActivityResultCallback<Uri> activityResultCallback) {
        cutOutPhoto(context, activityResultCaller, photoUri, MediaLoaderUriUtils.image2UriByInsert(context), activityResultCallback);
    }

    public static void cutOutPhoto(Context context,
                                   ActivityResultCaller activityResultCaller,
                                   Uri photoUri,
                                   Uri outPutUri,
                                   ActivityResultCallback<Uri> activityResultCallback) {
        Bundle bundle = new Bundle();
        bundle.putString("crop", "true");
        bundle.putInt("aspectX", 1);
        bundle.putInt("aspectY", 1);
        bundle.putInt("outputX", 256);
        bundle.putInt("outputY", 256);
        bundle.putBoolean("scale", true);
        //这里是坑
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            bundle.putParcelable(MediaStore.EXTRA_OUTPUT, outPutUri);
        } else {
            bundle.putParcelable(MediaStore.EXTRA_OUTPUT, Uri.fromFile(MediaLoaderUriUtils.uri2File(context, outPutUri)));
        }

        bundle.putBoolean("return-data", false);
        bundle.putString("outputFormat", Bitmap.CompressFormat.PNG.toString());
        bundle.putBoolean("noFaceDetection", true);
        cutOutPhoto(context, activityResultCaller, photoUri, bundle, activityResultCallback);
    }

    public static void cutOutPhoto(Context context,
                                   ActivityResultCaller activityResultCaller,
                                   Uri photoUri,
                                   Bundle bundle,
                                   ActivityResultCallback<Uri> activityResultCallback) {
        activityResultCaller.registerForActivityResult(new ActivityResultContract<Uri, Uri>() {
            @NonNull
            @Override
            public Intent createIntent(@NonNull Context context, Uri input) {
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent.setDataAndType(input, context.getContentResolver().getType(input));
                intent.putExtras(bundle);
                return intent;
            }

            @Override
            public Uri parseResult(int resultCode, @Nullable Intent intent) {
                if (resultCode == Activity.RESULT_OK) {
                    return bundle.getParcelable(MediaStore.EXTRA_OUTPUT);
                } else {
                    return null;
                }

            }
        }, result -> {
            if (result != null) {
                MediaLoaderUriUtils.notifyMediaScannerScanFile(context.getApplicationContext(), (path, uri) -> new Handler(Looper.getMainLooper()).post(() -> activityResultCallback.onActivityResult(uri)), result);
            } else {
                activityResultCallback.onActivityResult(result);
            }
        }).launch(photoUri);
    }

}
