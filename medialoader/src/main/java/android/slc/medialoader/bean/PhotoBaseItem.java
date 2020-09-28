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

package android.slc.medialoader.bean;


import android.os.Parcel;
import android.slc.medialoader.bean.i.IPhotoBaseItem;

/**
 * Created by Taurus on 16/8/28.
 */
public class PhotoBaseItem extends BaseItem implements IPhotoBaseItem {
    private int width, height;

    public PhotoBaseItem() {
    }

    public PhotoBaseItem(long id, String displayName, String path) {
        super(id, displayName, path);
    }

    public PhotoBaseItem(long id, String displayName, String path, long size) {
        super(id, displayName, path, size);
    }

    public PhotoBaseItem(long id, String displayName, String path, long size, long modified) {
        super(id, displayName, path, size, modified);
    }

    public PhotoBaseItem(long id, String displayName, String path, long size, long modified, int width, int height) {
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

    protected PhotoBaseItem(Parcel in) {
        super(in);
        this.width = in.readInt();
        this.height = in.readInt();
    }

    public static final Creator<PhotoBaseItem> CREATOR = new Creator<PhotoBaseItem>() {
        @Override
        public PhotoBaseItem createFromParcel(Parcel source) {
            return new PhotoBaseItem(source);
        }

        @Override
        public PhotoBaseItem[] newArray(int size) {
            return new PhotoBaseItem[size];
        }
    };
}
