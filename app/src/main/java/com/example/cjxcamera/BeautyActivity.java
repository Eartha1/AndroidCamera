package com.example.cjxcamera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lcw.library.stickerview.Sticker;
import com.lcw.library.stickerview.StickerLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BeautyActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView picture,sticker1,sticker2,sticker3,sticker4;
    private Button savePhoto;
    private TextView back,num,tvSticker,tvEdit;
    private StickerLayout mStickerLayout;
    private HorizontalScrollView hsvSticker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beauty);
        setTitle("美颜");

        picture = findViewById(R.id.picture);
        picture.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.timg));
        /*
        Intent intent=getIntent();
        if(intent!=null)
        {
            Bitmap bitmap = intent.getParcelableExtra("bitmap");
            picture.setImageBitmap(bitmap);
        }*/

        savePhoto = findViewById(R.id.btn_savephoto);
        savePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //获取Bitmap
                Bitmap bitmap =((BitmapDrawable)picture.getDrawable()).getBitmap();
                //以时间戳的方式命名
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                Date date = new Date(System.currentTimeMillis());
                String str = simpleDateFormat.format(date);
                //创建文件夹
                File mFolder = new File(getFilesDir() + "/sample");
                if (!mFolder.exists()) {
                    mFolder.mkdir();
                }
                //创建文件--you have to create file, before writing into stream.
                String bitName = str +".jpg";
                String fileName = mFolder.getAbsolutePath() + "/" + bitName;
                File imgFile = new File(fileName);
                Log.d("path",fileName);
                try {
                    if(!imgFile.exists()){
                        imgFile.createNewFile();
                    }
                } catch (IOException e)
                {
                    e.printStackTrace();
                }

                FileOutputStream out;
                try{
                    out = new FileOutputStream(imgFile);
                    // 格式为 JPEG，照相机拍出的图片为JPEG格式的，PNG格式的不能显示在相册中
                    if(bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out))
                    {
                        out.flush();
                        out.close();
                        // 插入图库
                        MediaStore.Images.Media.insertImage( BeautyActivity.this.getContentResolver(), imgFile.getAbsolutePath(), bitName, null);
                    }
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                // 发送广播，通知刷新图库的显示
                BeautyActivity.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + fileName)));
                Toast.makeText(BeautyActivity.this,"保存照片成功",Toast.LENGTH_SHORT).show();

            }
        });

        back = findViewById(R.id.back);
        back.setOnClickListener(this);


        mStickerLayout = findViewById(R.id.sticker_layout);
        sticker1 = findViewById(R.id.iv_sticker_01);
        sticker1.setOnClickListener(this);
        sticker2 = findViewById(R.id.iv_sticker_02);
        sticker2.setOnClickListener(this);
        sticker3 = findViewById(R.id.iv_sticker_03);
        sticker3.setOnClickListener(this);
        sticker4 = findViewById(R.id.iv_sticker_04);
        sticker4.setOnClickListener(this);


        hsvSticker = findViewById(R.id.hsv_sticker);
        hsvSticker.setVisibility(View.GONE);
        num = findViewById(R.id.num);
        num.setVisibility(View.INVISIBLE);
        num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                 int change = Integer.parseInt(s.toString());
                 Log.d("num1",String.valueOf(change));
                 if(change == 1) {
                     hsvSticker.setVisibility(View.VISIBLE);
                     tvSticker.setTextColor(0xE6F886E8);
                 }
                  else {
                     hsvSticker.setVisibility(View.GONE);
                     tvSticker.setTextColor(0xFF888888);
                 }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        tvSticker = findViewById(R.id.sticker);
        tvSticker.setOnClickListener(this);
        tvEdit = findViewById(R.id.edit);
        tvEdit.setOnClickListener(this);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mStickerLayout.removeAllSticker();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.back:
                Intent intentback = new Intent(BeautyActivity.this,MainActivity.class);
                startActivity(intentback);
                break;

            case R.id.iv_sticker_01:
                Sticker sticker1 = new Sticker(BeautyActivity.this, BitmapFactory.decodeResource(BeautyActivity.this.getResources(), R.drawable.sticker1));
                mStickerLayout.addSticker(sticker1);
                break;

            case R.id.iv_sticker_02:
                Sticker sticker2 = new Sticker(BeautyActivity.this, BitmapFactory.decodeResource(BeautyActivity.this.getResources(), R.drawable.sticker2));
                mStickerLayout.addSticker(sticker2);
                break;

            case R.id.iv_sticker_03:
                Sticker sticker3 = new Sticker(BeautyActivity.this, BitmapFactory.decodeResource(BeautyActivity.this.getResources(), R.drawable.sticker3));
                mStickerLayout.addSticker(sticker3);
                break;

            case R.id.iv_sticker_04:
                Sticker sticker4 = new Sticker(BeautyActivity.this, BitmapFactory.decodeResource(BeautyActivity.this.getResources(), R.drawable.sticker4));
                mStickerLayout.addSticker(sticker4);
                break;

            case R.id.sticker:
                num.setText("1");
                break;

            case R.id.edit:
                num.setText("2");
                break;
        }
    }
}
