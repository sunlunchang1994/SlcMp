package android.slc.medialoader.utils;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.provider.BaseColumns._ID;

/**
 * @author slc
 * @date 2020-09-28 13:23
 */
public class MediaLoaderUriUtils {
    private static final String TIME_FORMAT = "_yyyyMMdd_HHmmss";

    /**
     * @param timeFormatHeader 格式化的头(除去时间部分)
     * @param extension        后缀名
     * @return 返回时间格式化后的文件名
     */
    public static String getFileNameByTime(String timeFormatHeader, String extension) {
        return getTimeFormatName(timeFormatHeader) + "." + extension;
    }

    private static String getTimeFormatName(String timeFormatHeader) {
        final Date date = new Date(System.currentTimeMillis());
        final SimpleDateFormat dateFormat = new SimpleDateFormat("'" + timeFormatHeader + "'" + TIME_FORMAT,
                Locale.getDefault());
        return dateFormat.format(date);
    }

    public static Uri file2Uri(Context context, @NonNull final String filePath) {
        return file2Uri(context, filePath);
    }

    /**
     * File to uri.
     *
     * @param file The file.
     * @return uri
     */
    public static Uri file2Uri(Context context, @NonNull final File file) {
        Uri fileUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && context.getApplicationInfo().targetSdkVersion >= Build.VERSION_CODES.Q) {//兼容10.0及以上的写法
            Cursor cursor = context.getContentResolver().query(MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL), new String[]{}, MediaStore.Images.Media.DISPLAY_NAME + "= ?",
                    new String[]{file.getAbsolutePath()},
                    null);
            fileUri = null;
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    fileUri = ContentUris.withAppendedId(MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL), cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                }
                cursor.close();
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//兼容7.0及以上的写法
            fileUri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
        } else {
            fileUri = Uri.fromFile(file);
        }
        return fileUri;
    }

    public static Uri imageId2Uri(long id) {
        return id2Uri(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
    }

    public static Uri audioId2Uri(long id) {
        return id2Uri(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
    }

    public static Uri videoId2Uri(long id) {
        return id2Uri(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id);
    }

    public static Uri fileId2Uri(long id) {
        return id2Uri(MediaStore.Files.getContentUri(MediaStore.VOLUME_INTERNAL), id);
    }

    public static Uri id2Uri(Uri uri, long id) {
        return ContentUris.withAppendedId(uri, id);
    }

    public static Uri image2UriByInsert(Context context) {
        return image2UriByInsert(context, Environment.DIRECTORY_DCIM, getFileNameByTime("IMG", "jpg"));
    }

    public static Uri image2UriByInsert(Context context, @NonNull final String relativePath, @NonNull final String displayName) {
        return image2UriByInsert(context, relativePath, "image/jpeg", displayName);
    }

    public static Uri image2UriByInsert(Context context, @NonNull final String relativePath, @NonNull final String mimeType, @NonNull final String displayName) {
        return image2UriByInsert(context, newBuilder().setRelativePath(relativePath).setMimeType(mimeType).setDisplayName(displayName).build());
    }

    public static Uri image2UriByInsert(Context context, @NonNull final ContentValues contentValues) {
        return file2Uri(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
    }

    public static Uri file2Uri(Context context, @NonNull final Uri mediaUri, @NonNull final ContentValues contentValues) {
        Uri fileUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && context.getApplicationInfo().targetSdkVersion >= Build.VERSION_CODES.Q) {//兼容10.0及以上的写法
            fileUri = context.getContentResolver().insert(mediaUri, contentValues);
        } else {
            String relativePath = contentValues.getAsString("relative_path");
            relativePath = TextUtils.isEmpty(relativePath) ? "" : relativePath + File.separator;
            String fileName = contentValues.getAsString(MediaStore.MediaColumns.DISPLAY_NAME);
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + relativePath + fileName);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//兼容7.0及以上的写法
                fileUri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
            } else {
                fileUri = Uri.fromFile(file);
            }
        }
        return fileUri;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private ContentValues contentValues;

        public Builder() {
            contentValues = new ContentValues();
        }

        public Builder setDisplayName(String displayName) {
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, displayName);
            return this;
        }

        public Builder setMimeType(String mimeType) {
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, mimeType);
            return this;
        }

        public Builder setRelativePath(String relativePath) {
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, relativePath);
            return this;
        }

        public Builder put(String key, String value) {
            contentValues.put(key, value);
            return this;
        }

        public Builder putAll(ContentValues other) {
            contentValues.putAll(other);
            return this;
        }

        public Builder put(String key, Byte value) {
            contentValues.put(key, value);
            return this;
        }

        public Builder put(String key, Short value) {
            contentValues.put(key, value);
            return this;
        }

        public Builder put(String key, Integer value) {
            contentValues.put(key, value);
            return this;
        }

        public Builder put(String key, Long value) {
            contentValues.put(key, value);
            return this;
        }

        public Builder put(String key, Float value) {
            contentValues.put(key, value);
            return this;
        }

        public Builder put(String key, Double value) {
            contentValues.put(key, value);
            return this;
        }

        public Builder put(String key, Boolean value) {
            contentValues.put(key, value);
            return this;
        }

        public Builder put(String key, byte[] value) {
            contentValues.put(key, value);
            return this;
        }

        public ContentValues build() {
            return contentValues;
        }
    }
}
