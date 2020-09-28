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
import android.slc.medialoader.bean.i.IAudioBaseItem;

/**
 * Created by Taurus on 16/8/28.
 */
public class AudioBaseItem extends BaseItem implements IAudioBaseItem {
    private long duration;

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

    public AudioBaseItem() {
    }

    protected AudioBaseItem(Parcel in) {
        super(in);
        this.duration = in.readLong();
    }

    public static final Creator<AudioBaseItem> CREATOR = new Creator<AudioBaseItem>() {
        @Override
        public AudioBaseItem createFromParcel(Parcel source) {
            return new AudioBaseItem(source);
        }

        @Override
        public AudioBaseItem[] newArray(int size) {
            return new AudioBaseItem[size];
        }
    };
}
