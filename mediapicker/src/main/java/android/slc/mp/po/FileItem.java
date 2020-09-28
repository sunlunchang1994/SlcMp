package android.slc.mp.po;

import android.os.Parcel;
import android.slc.mp.po.i.IFileItem;

/**
 * Created by slc
 */

public class FileItem extends BaseItem implements IFileItem {

    private String mime;

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.mime);
    }

    public FileItem() {
    }

    protected FileItem(Parcel in) {
        super(in);
        this.mime = in.readString();
    }

    public static final Creator<FileItem> CREATOR = new Creator<FileItem>() {
        @Override
        public FileItem createFromParcel(Parcel source) {
            return new FileItem(source);
        }

        @Override
        public FileItem[] newArray(int size) {
            return new FileItem[size];
        }
    };
}
