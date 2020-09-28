package android.slc.slcmediapicker;

import android.app.Application;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.slc.mediaglide.BrowsePhotoLoad;
import android.slc.mediaglide.FolderLoad;
import android.slc.mediaglide.ImageLoad;
import android.slc.mp.SlcMp;
import android.slc.mp.SlcMpConfig;

/**
 * @author slc
 * @date 2020-09-25 15:40
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        //builder.detectFileUriExposure();//https://blog.csdn.net/tanghongchang123/article/details/68490946/
        //StrictMode.setVmPolicy(builder.build());
        SlcMp.getInstance().initMp(this);
        SlcMp.getInstance().initGlobalMpConfig(new SlcMpConfig.Builder().loadPhoto().loadVideo().loadAudio().setImageLoad(new ImageLoad())
                .setFolderLoad(new FolderLoad()).setBrowsePhotoLoad(new BrowsePhotoLoad()).setSpanCount(3).build());
    }
}
