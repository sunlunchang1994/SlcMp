package android.slc.mp.po;

import android.os.Parcel;
import android.slc.mp.po.i.IBaseItem;

/**
 * Created by slc
 */
public abstract class BaseItem extends android.slc.medialoader.bean.BaseItem implements IBaseItem {
    protected boolean checked;
    protected int mediaTypeTag;

    public BaseItem() {
    }

    public BaseItem(long id, String displayName, String path) {
        this(id, displayName, path, 0);
    }

    public BaseItem(long id, String displayName, String path, long size) {
        this(id, displayName, path, size, 0);
    }

    public BaseItem(long id, String displayName, String path, long size, long modified) {
        super(id, displayName, path, size, modified);
    }

    @Override
    public void setMediaTypeTag(int mediaTypeTag) {
        this.mediaTypeTag = mediaTypeTag;
    }

    @Override
    public int getMediaTypeTag() {
        return mediaTypeTag;
    }

    @Override
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public boolean isChecked() {
        return checked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeByte(this.checked ? (byte) 1 : (byte) 0);
        dest.writeInt(this.mediaTypeTag);
    }

    protected BaseItem(Parcel in) {
        super(in);
        this.checked = in.readByte() != 0;
        this.mediaTypeTag = in.readInt();
    }

}
