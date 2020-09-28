package android.slc.mp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.slc.medialoader.bean.i.IBaseFolder;
import android.slc.medialoader.bean.i.IBaseItem;
import android.slc.mp.ui.activity.SlcMpActivity;
import android.widget.ImageView;

import android.slc.medialoader.bean.i.IFileProperty;

import java.util.ArrayList;
import java.util.HashMap;

public class SlcMpConfig {
    private IImageLoad mImageLoad;
    private IImageFolderLoad mFolderLoad;
    private IImageBrowseLoad mBrowsePhotoLoad;
    private Bundle mBundle;
    private Class<?> mTargetUi;

    private SlcMpConfig(IImageLoad imageLoad, IImageFolderLoad folderLoad, IImageBrowseLoad browsePhotoLoad, @NonNull Bundle bundle
            , Class<?> targetUi) {
        this.mImageLoad = imageLoad;
        this.mFolderLoad = folderLoad;
        this.mBrowsePhotoLoad = browsePhotoLoad;
        this.mBundle = bundle;
        this.mTargetUi = targetUi;
    }

    public void loadImage(ImageView imageView, IBaseItem item, int itemType) {
        if (mImageLoad != null) {
            mImageLoad.loadImage(imageView, item, itemType);
        }
    }

    public void loadFolder(ImageView imageView, IBaseFolder item, int itemType) {
        if (mFolderLoad != null) {
            mFolderLoad.loadImage(imageView, item, itemType);
        }
    }

    public void loadBrowsePhoto(Context context, IBaseItem item, IImageBrowseLoadCallBack imageBrowseLoadCallBack) {
        if (mBrowsePhotoLoad != null) {
            mBrowsePhotoLoad.loadImage(context, item, imageBrowseLoadCallBack);
        }
    }

    public IImageLoad getImageLoad() {
        return mImageLoad;
    }

    public IImageFolderLoad getFolderLoad() {
        return mFolderLoad;
    }

    public IImageBrowseLoad getBrowsePhotoLoad() {
        return mBrowsePhotoLoad;
    }

    public Bundle getBundle() {
        return mBundle;
    }

    public Class<?> getTargetUi() {
        return mTargetUi;
    }

    public void ensureMpConfig(SlcMpConfig slcMpConfig) {
        if (this.mImageLoad == null) {
            this.mImageLoad = slcMpConfig.getImageLoad();
        }
        if (this.mFolderLoad == null) {
            this.mFolderLoad = slcMpConfig.getFolderLoad();
        }
        if (this.mBrowsePhotoLoad == null) {
            this.mBrowsePhotoLoad = slcMpConfig.getBrowsePhotoLoad();
        }
        if (mBundle == null) {
            mBundle = (Bundle) slcMpConfig.getBundle().clone();
            return;
        }
        ArrayList<Integer> mediaTypeList = mBundle.getIntegerArrayList(SlcMp.Key.KEY_MEDIA_TYPE_LIST);
        if (mediaTypeList == null || mediaTypeList.isEmpty()) {
            mBundle.putIntegerArrayList(SlcMp.Key.KEY_MEDIA_TYPE_LIST,
                    slcMpConfig.getBundle().getIntegerArrayList(SlcMp.Key.KEY_MEDIA_TYPE_LIST));
            mBundle.putSerializable(SlcMp.Key.KEY_MEDIA_TYPE_TITLE_LIST,
                    slcMpConfig.getBundle().getSerializable(SlcMp.Key.KEY_MEDIA_TYPE_TITLE_LIST));
            mBundle.putSerializable(SlcMp.Key.KEY_MEDIA_TYPE_FILE_PROPERTY_LIST,
                    slcMpConfig.getBundle().getSerializable(SlcMp.Key.KEY_MEDIA_TYPE_FILE_PROPERTY_LIST));
        }
        ArrayList<Integer> mediaTypeMuddyList = mBundle.getIntegerArrayList(SlcMp.Key.KEY_MEDIA_TYPE_MUDDY_LIST);
        if (mediaTypeMuddyList == null || mediaTypeMuddyList.isEmpty()) {
            mBundle.putIntegerArrayList(SlcMp.Key.KEY_MEDIA_TYPE_MUDDY_LIST,
                    slcMpConfig.getBundle().getIntegerArrayList(SlcMp.Key.KEY_MEDIA_TYPE_MUDDY_LIST));
        }
        if (mBundle.getInt(SlcMp.Key.KEY_SPAN_COUNT, SlcMp.Value.VALUE_SPAN_COUNT_DEF_VALUE) < 1) {
            mBundle.putInt(SlcMp.Key.KEY_SPAN_COUNT, slcMpConfig.getBundle().getInt(SlcMp.Key.KEY_SPAN_COUNT));
        }
        if (mBundle.getInt(SlcMp.Key.KEY_MAX_PICKER, 0) < 1) {
            mBundle.putInt(SlcMp.Key.KEY_MAX_PICKER, slcMpConfig.getBundle().getInt(SlcMp.Key.KEY_MAX_PICKER,
                    SlcMp.Value.VALUE_DEF_MAX_PICKER));
        }
        if (mTargetUi == null) {
            mTargetUi = slcMpConfig.getTargetUi() == null ? SlcMpActivity.class : slcMpConfig.getTargetUi();
        }
        mBundle.putBoolean(SlcMp.Key.KEY_IS_MULTIPLE_SELECTION, mBundle.getBoolean(SlcMp.Key.KEY_IS_MULTIPLE_SELECTION,
                slcMpConfig.getBundle().getBoolean(SlcMp.Key.KEY_IS_MULTIPLE_SELECTION,
                        SlcMp.Value.VALUE_DEF_MULTIPLE_SELECTION)));
        mBundle.putBoolean(SlcMp.Key.KEY_IS_MULTIPLE_MEDIA_TYPE, mBundle.getBoolean(SlcMp.Key.KEY_IS_MULTIPLE_MEDIA_TYPE,
                slcMpConfig.getBundle().getBoolean(SlcMp.Key.KEY_IS_MULTIPLE_MEDIA_TYPE,
                        SlcMp.Value.VALUE_DEF_MULTIPLE_MEDIA_TYPE)));
    }

    public interface IImageLoad {
        void loadImage(ImageView imageView, IBaseItem item, int itemType);
    }
    public interface IImageFolderLoad {
        void loadImage(ImageView imageView, IBaseFolder item, int itemType);
    }

    public interface IImageBrowseLoad {
        void loadImage(Context context, IBaseItem item, IImageBrowseLoadCallBack iImageBrowseLoadCallBack);
    }

    public interface IImageBrowseLoadCallBack {
        Object getTag();

        void load(Bitmap bitmap);

        void loadStart(@Nullable Drawable placeholder);

        void loadFailed(@Nullable Drawable errorDrawable);
    }

    public static class Builder {
        private IImageLoad mImageLoad;
        private IImageFolderLoad mFolderLoad;
        private IImageBrowseLoad mBrowsePhotoLoad;
        private Class<?> mtTargetUi;
        private Bundle mBundle = new Bundle();
        private ArrayList<Integer> mMediaTypeList = new ArrayList<>();
        private ArrayList<Integer> mMediaTypeMuddyList = new ArrayList<>();
        private HashMap<Integer, String> mMediaTypeFileTitleArray = new HashMap<>();
        private HashMap<Integer, IFileProperty> mMediaTypeFilePropertyArray = new HashMap<>();

        public Builder() {

        }

        public Builder setImageLoad(IImageLoad imageLoad) {
            this.mImageLoad = imageLoad;
            return this;
        }

        public Builder setFolderLoad(IImageFolderLoad folderLoad) {
            this.mFolderLoad = folderLoad;
            return this;
        }

        public Builder setBrowsePhotoLoad(IImageBrowseLoad browsePhotoLoad) {
            this.mBrowsePhotoLoad = browsePhotoLoad;
            return this;
        }

        public Builder loadPhoto() {
            loadPhoto("");
            return this;
        }

        public Builder loadPhoto(String title) {
            loadPhoto(title, null);
            return this;
        }

        public Builder loadPhoto(IFileProperty fileProperty) {
            loadPhoto(null, fileProperty);
            return this;
        }

        public Builder loadPhoto(String title, IFileProperty fileProperty) {
            loadFile(SlcMp.MEDIA_TYPE_PHOTO, title, fileProperty);
            return this;
        }

        public Builder loadVideo() {
            loadVideo("");
            return this;
        }

        public Builder loadVideo(String title) {
            loadVideo(title, null);
            return this;
        }

        public Builder loadVideo(IFileProperty fileProperty) {
            loadVideo(null, fileProperty);
            return this;
        }

        public Builder loadVideo(String title, IFileProperty fileProperty) {
            loadFile(SlcMp.MEDIA_TYPE_VIDEO, title, fileProperty);
            return this;
        }

        public Builder loadAudio() {
            loadAudio("");
            return this;
        }

        public Builder loadAudio(String title) {
            loadAudio(title, null);
            return this;
        }

        public Builder loadAudio(IFileProperty fileProperty) {
            loadAudio(null, fileProperty);
            return this;
        }

        public Builder loadAudio(String title, IFileProperty fileProperty) {
            loadFile(SlcMp.MEDIA_TYPE_AUDIO, title, fileProperty);
            return this;
        }

        public Builder loadFile() {
            mMediaTypeList.add(SlcMp.MEDIA_TYPE_FILE);
            return this;
        }

        public Builder loadFile(IFileProperty fileProperty) {
            loadFile();
            mMediaTypeFilePropertyArray.put(SlcMp.MEDIA_TYPE_FILE, fileProperty);
            return this;
        }

        public Builder loadFile(int fileType, String title, IFileProperty fileProperty) {
            mMediaTypeList.add(fileType);
            mMediaTypeFileTitleArray.put(fileType, title);
            mMediaTypeFilePropertyArray.put(fileType, fileProperty);
            return this;
        }

        public Builder muddyPhoto() {
            mMediaTypeMuddyList.add(SlcMp.MEDIA_TYPE_PHOTO);
            return this;
        }

        public Builder muddyVideo() {
            mMediaTypeMuddyList.add(SlcMp.MEDIA_TYPE_VIDEO);
            return this;
        }

        public Builder muddyAudio() {
            mMediaTypeMuddyList.add(SlcMp.MEDIA_TYPE_AUDIO);
            return this;
        }

        public Builder muddyFile() {
            mMediaTypeMuddyList.add(SlcMp.MEDIA_TYPE_FILE);
            return this;
        }

        public Builder muddyFileType(int fileType) {
            mMediaTypeMuddyList.add(fileType);
            return this;
        }

        public Builder setSpanCount(int spanCount) {
            mBundle.putInt(SlcMp.Key.KEY_SPAN_COUNT, spanCount);
            return this;
        }

        public Builder setMaxPicker(int count) {
            mBundle.putInt(SlcMp.Key.KEY_MAX_PICKER, count < 1 ? 1 : count);
            return this;
        }

        public Builder setIsMultipleSelection(boolean isMultipleSelection) {
            mBundle.putBoolean(SlcMp.Key.KEY_IS_MULTIPLE_SELECTION, isMultipleSelection);
            return this;
        }

        public Builder setIsMultipleMediaType(boolean isMultipleMediaType) {
            mBundle.putBoolean(SlcMp.Key.KEY_IS_MULTIPLE_MEDIA_TYPE, isMultipleMediaType);
            return this;
        }

        public Builder setTargetUi(Class<?> targetUi) {
            this.mtTargetUi = targetUi;
            return this;
        }

        public SlcMpConfig build() {
            mBundle.putIntegerArrayList(SlcMp.Key.KEY_MEDIA_TYPE_LIST, mMediaTypeList);
            mBundle.putIntegerArrayList(SlcMp.Key.KEY_MEDIA_TYPE_MUDDY_LIST, mMediaTypeMuddyList);
            mBundle.putSerializable(SlcMp.Key.KEY_MEDIA_TYPE_TITLE_LIST, mMediaTypeFileTitleArray);
            mBundle.putSerializable(SlcMp.Key.KEY_MEDIA_TYPE_FILE_PROPERTY_LIST, mMediaTypeFilePropertyArray);
            return new SlcMpConfig(mImageLoad, mFolderLoad, this.mBrowsePhotoLoad, mBundle, mtTargetUi);
        }
    }
}
