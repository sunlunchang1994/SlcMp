package android.slc.medialoader.bean;

import android.os.Parcel;
import android.slc.medialoader.bean.i.IFileBaseItem;

/**
 * Created by Taurus on 2017/5/23.
 */

public class FileBaseItem extends BaseItem implements IFileBaseItem {

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

    public FileBaseItem() {
    }

    protected FileBaseItem(Parcel in) {
        super(in);
        this.mime = in.readString();
    }

    public static final Creator<FileBaseItem> CREATOR = new Creator<FileBaseItem>() {
        @Override
        public FileBaseItem createFromParcel(Parcel source) {
            return new FileBaseItem(source);
        }

        @Override
        public FileBaseItem[] newArray(int size) {
            return new FileBaseItem[size];
        }
    };
}
