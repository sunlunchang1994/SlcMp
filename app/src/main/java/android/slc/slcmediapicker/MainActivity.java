package android.slc.slcmediapicker;

import android.net.Uri;
import android.os.Bundle;
import android.slc.mp.SlcMp;
import android.slc.mp.SlcMpConfig;
import android.slc.mp.utils.SlcMpFilePickerUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        findViewById(R.id.btn_look).setOnClickListener(v -> {
            SlcMp.getInstance()
                    .with(MainActivity.this)
                    .setMpConfig(new SlcMpConfig
                            .Builder()
                            .loadPhoto()
                            .loadAudio()
                            .loadVideo()
                            .loadFile(AttachmentUtils.TYPE_WORD, AttachmentUtils.TYPE_NAME_WORD, AttachmentUtils.Word)
                            .loadFile(AttachmentUtils.TYPE_EXCEL, AttachmentUtils.TYPE_NAME_EXCEL, AttachmentUtils.Excel)
                            .loadFile(AttachmentUtils.TYPE_PPT, AttachmentUtils.TYPE_NAME_PPT, AttachmentUtils.Ppt)
                            .loadFile(AttachmentUtils.TYPE_PDF, AttachmentUtils.TYPE_NAME_PDF, AttachmentUtils.Pdf)
                            .loadFile(AttachmentUtils.TYPE_COMPRESSION, AttachmentUtils.TYPE_NAME_COMPRESSION, AttachmentUtils.Compression)
                            .setMaxPicker(9)
                            .build())
                    .observe(MainActivity.this, iBaseItems -> {
                        Glide.with(MainActivity.this).load(iBaseItems.get(0).getUri())
                                .into((ImageView) findViewById(R.id.ivTest));
                    })
                    .build();
        });
        findViewById(R.id.btn_takePhoto).setOnClickListener(v -> {
            SlcMp.getInstance().takePhoto(MainActivity.this, MainActivity.this, result -> {
                if (result == null) {
                    return;
                }
                SlcMpFilePickerUtils.cutOutPhoto(MainActivity.this, MainActivity.this, result, result1 -> {
                    if (result1 != null) {
                        Glide.with(MainActivity.this).load(result1)
                                .into((ImageView) findViewById(R.id.ivTest));
                    }
                });
            });
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
