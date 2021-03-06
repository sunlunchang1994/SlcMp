/*
 * Copyright 2016 jiajunhui
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package android.slc.mp.po;


import android.os.Parcel;
import android.slc.mp.po.i.IPhotoItem;

/**
 * Created by slc
 */
public class PhotoItem extends BaseItem implements IPhotoItem {
    private int width, height;

    public PhotoItem() {
    }

    public PhotoItem(long id, String displayName, String path) {
        super(id, displayName, path);
    }

    public PhotoItem(long id, String displayName, String path, long size) {
        super(id, displayName, path, size);
    }

    public PhotoItem(long id, String displayName, String path, long size, long modified) {
        super(id, displayName, path, size, modified);
    }

    public PhotoItem(long id, String displayName, String path, long size, long modified, int width, int height) {
        super(id, displayName, path, size, modified);
        this.width = width;
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public boolean isLongImg() {
        return height / width > 3 || width / height > 3;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
    }

    protected PhotoItem(Parcel in) {
        super(in);
        this.width = in.readInt();
        this.height = in.readInt();
    }

    public static final Creator<PhotoItem> CREATOR = new Creator<PhotoItem>() {
        @Override
        public PhotoItem createFromParcel(Parcel source) {
            return new PhotoItem(source);
        }

        @Override
        public PhotoItem[] newArray(int size) {
            return new PhotoItem[size];
        }
    };
}
