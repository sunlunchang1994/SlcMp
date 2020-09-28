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

import android.net.Uri;
import android.os.Parcel;
import android.slc.medialoader.bean.i.IBaseItem;

/**
 * Created by Taurus on 16/8/28.
 */
public abstract class BaseItem implements IBaseItem {
    protected long id;
    protected String displayName;
    protected String path;
    protected long size;
    protected long modified;
    protected String extension;
    protected Uri uri;

    public BaseItem() {
    }

    public BaseItem(long id, String displayName, String path) {
        this(id, displayName, path, 0);
    }

    public BaseItem(long id, String displayName, String path, long size) {
        this(id, displayName, path, size, 0);
    }

    public BaseItem(long id, String displayName, String path, long size, long modified) {
        this.id = id;
        this.displayName = displayName;
        this.path = path;
        this.size = size;
        this.modified = modified;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getModified() {
        return modified;
    }

    public void setModified(long modified) {
        this.modified = modified;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.displayName);
        dest.writeString(this.path);
        dest.writeLong(this.size);
        dest.writeLong(this.modified);
        dest.writeString(this.extension);
        dest.writeParcelable(this.uri, flags);
    }

    protected BaseItem(Parcel in) {
        this.id = in.readLong();
        this.displayName = in.readString();
        this.path = in.readString();
        this.size = in.readLong();
        this.modified = in.readLong();
        this.extension = in.readString();
        this.uri = in.readParcelable(Uri.class.getClassLoader());
    }
}
