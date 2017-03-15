package com.luoshihai.app;//package com.hrfax.picturecompress;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.luoshihai.xxphotoview.PhotoView;

import net.bither.util.NativeUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//特伦特C


public class MainActivity extends AppCompatActivity {
    private static final int PHOTO_REQUEST_TAKEPHOTO = 11;// 拍照
    private String mCameraFilePath;
    Bitmap bmp;
    private File sdcardTempFile;
    PhotoView mAlbumIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mAlbumIv = (PhotoView) findViewById(R.id.iv_album);
        mAlbumIv.enable();
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paizhao();

            }
        });
    }

    public static String getNowDateTime(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    private void Paizhao() {
        String outFileFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
        String outFilePath = getNowDateTime("yyyyMMdd_HHmmssSSS") + ".jpg";
        sdcardTempFile = new File(outFileFolder, outFilePath);
        mCameraFilePath = sdcardTempFile.getAbsolutePath();


        Intent intent3 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 指定调用相机拍照后照片的储存路径
        intent3.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(sdcardTempFile));
        startActivityForResult(intent3, PHOTO_REQUEST_TAKEPHOTO);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PHOTO_REQUEST_TAKEPHOTO) {
            bmp = BitmapFactory.decodeFile(mCameraFilePath);
            int h = bmp.getHeight();
            int w = bmp.getWidth();
            String downsize = Formatter.formatFileSize(MainActivity.this, new File(mCameraFilePath).length());
            Log.w("MainActivity", "压缩前" + downsize + h + "-->" + w);

            Log.w("MainActivity", "压缩前:" + SystemClock.currentThreadTimeMillis());
            //第一个参数表示压缩源 bitmap
            //第二个参数表示压缩之后图片保存的路径
            //第三个参数表示压缩后图片的最大的大小 单位为k
            NativeUtil.compressBitmap(bmp, mCameraFilePath, 300);

            //第一个参数表示压缩源 bitmap
            //第二个参数表示压缩之后图片保存的路径
            //第三个参数表示是否使用是否采用哈弗曼表数据计算 品质相差5-10倍
            NativeUtil.compressBitmap(bmp,mCameraFilePath,true);
            Log.w("MainActivity", "压缩后:" + SystemClock.currentThreadTimeMillis());


            h = bmp.getHeight();
            w = bmp.getWidth();


            downsize = Formatter.formatFileSize(MainActivity.this, new File(mCameraFilePath).length());
            Log.w("MainActivity", downsize + h + "-->" + w);

            Glide.with(MainActivity.this).load(mCameraFilePath).into(mAlbumIv);


        }


        super.onActivityResult(requestCode, resultCode, data);
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
