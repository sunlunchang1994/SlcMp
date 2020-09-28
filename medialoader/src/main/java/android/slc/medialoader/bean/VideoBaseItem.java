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
import android.slc.medialoader.bean.i.IVideoBaseItem;

/**
 * Created by Taurus on 16/8/28.
 */
public class VideoBaseItem extends BaseItem implements IVideoBaseItem {

    private long duration;

    public VideoBaseItem() {
    }

    public VideoBaseItem(long id, String displayName, String path, long size, long modified, long duration) {
        super(id, displayName, path, size, modified);
        this.duration = duration;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeLong(this.duration);
    }

    protected VideoBaseItem(Parcel in) {
        super(in);
        this.duration = in.readLong();
    }

    public static final Creator<VideoBaseItem> CREATOR = new Creator<VideoBaseItem>() {
        @Override
        public VideoBaseItem createFromParcel(Parcel source) {
            return new VideoBaseItem(source);
        }

        @Override
        public VideoBaseItem[] newArray(int size) {
            return new VideoBaseItem[size];
        }
    };
}
