package android.slc.mp.po;

import android.net.Uri;
import android.os.Parcel;
import android.slc.mp.po.i.IPhotoItem;

/**
 * 添加拍照的item
 * 该类什么都不需要做
 * @author slc
 * @date 2019/11/26 10:06
 */
public class AddPhotoItem implements IPhotoItem {
    @Override
    public void setWidth(int width) {

    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public void setHeight(int height) {

    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public boolean isLongImg() {
        return false;
    }

    @Override
    public void setMediaTypeTag(int mediaTypeTag) {

    }

    @Override
    public int getMediaTypeTag() {
        return 0;
    }

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public void setId(long id) {

    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public void setDisplayName(String displayName) {

    }

    @Override
    public String getPath() {
        return null;
    }

    @Override
    public void setPath(String path) {

    }

    @Override
    public Uri getUri() {
        return null;
    }

    @Override
    public void setUri(Uri uri) {

    }

    @Override
    public long getSize() {
        return 0;
    }

    @Override
    public void setSize(long size) {

    }

    @Override
    public long getModified() {
        return 0;
    }

    @Override
    public void setModified(long modified) {

    }

    @Override
    public String getExtension() {
        return null;
    }

    @Override
    public void setExtension(String extension) {

    }

    @Override
    public boolean isChecked() {
        return false;
    }

    @Override
    public void setChecked(boolean checked) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public AddPhotoItem() {
    }

    protected AddPhotoItem(Parcel in) {
    }

    public static final Creator<AddPhotoItem> CREATOR = new Creator<AddPhotoItem>() {
        @Override
        public AddPhotoItem createFromParcel(Parcel source) {
            return new AddPhotoItem(source);
        }

        @Override
        public AddPhotoItem[] newArray(int size) {
            return new AddPhotoItem[size];
        }
    };
}
