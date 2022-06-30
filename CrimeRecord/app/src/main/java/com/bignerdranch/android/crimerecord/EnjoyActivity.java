package com.bignerdranch.android.crimerecord;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;
import java.util.Calendar;

import static android.R.attr.bitmap;

public class EnjoyActivity extends AppCompatActivity {

    private CrimeLab mCrimeLab=CrimeLab.getInstance();
    private UUID ID;
    private Bitmap bp;
    private CardView mCardView;
    private ImageView newsPhoto;
    private TextView newsTitle;
    private TextView newsPainter;
    private TextView newsDesc;
    private ImageButton mImageButton;
    private Context context;
//    private TextView newsDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enjoy);

        newsPhoto= (ImageView) findViewById(R.id.news_info_photo);
        newsTitle= (TextView) findViewById(R.id.news_info_title);
        newsPainter=(TextView) findViewById(R.id.news_info_painter);
        newsDesc= (TextView) findViewById(R.id.news_info_desc);
//        newsDate= (TextView) findViewById(R.id.news_info_date);
        mCardView=(CardView) findViewById(R.id.card_view);
        mImageButton=(ImageButton) findViewById(R.id.news_info_update);
        Intent intent=getIntent();
        UUID crime_id=(UUID)intent.getSerializableExtra("crime_id");
        ID=crime_id;
        Log.i("CrimeFragment","------------------>"+crime_id);
        final Crime item= mCrimeLab.getCrime(crime_id);

        Log.i("CrimeFragment","------------------>"+item.getId());

        bp=item.getPhotoId();

        newsPhoto.setImageBitmap(item.getPhotoId());
        newsPainter.setText(item.getPainter()+"\n"+item.getDate().toString());
//        newsPhoto.setImageBitmap(getPic("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.51yuansu.com%2Fpic3%2Fcover%2F02%2F85%2F60%2F5a61d21b8e174_610.jpg&refer=http%3A%2F%2Fpic.51yuansu.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1658718034&t=952a03ff0751eece8b041ad7ba499553"));
        newsTitle.setText(item.getTitle());
        newsDesc.setText(item.getDesc());

        newsPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                final View imgEntryView = inflater.inflate(R.layout.dialog_photo, null);
// 加载自定义的布局文件
                final AlertDialog dialog = new AlertDialog.Builder(EnjoyActivity.this).create();
                ImageView img = (ImageView) imgEntryView.findViewById(R.id.large_image);
                img.setImageBitmap(bp);
                dialog.setView(imgEntryView); // 自定义dialog
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
                // 点击大图关闭dialog
                imgEntryView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View paramView) {
                        dialog.cancel();
                    }
                });

                imgEntryView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        viewSaveToImage(imgEntryView);
                        return true;
                    }
                });


            }
        });


//        newsDate.setText(item.getDate().toString());
//        mCardView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                Bitmap b=loadBitmapFromView(mCardView);
//                Intent intent = new Intent(Intent.ACTION_SEND);
//                intent.setType("image/*");
//                intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
//                intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), b, null,null)));
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(Intent.createChooser(intent, "Share"));
//                return false;
//            }
//        });

//        mCardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        mImageButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent intent2=new Intent(EnjoyActivity.this,CrimeActivity.class);
                                                intent2.putExtra("crime_id",ID);
                                                startActivity(intent2);
                                            }
                                        }
        );

    }

    public String convertToBase64(Bitmap bitmap) {

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG,100,os);

        byte[] byteArray = os.toByteArray();

        return Base64.encodeToString(byteArray, 0);

    }

    private void viewSaveToImage(View view) {
        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        view.setDrawingCacheBackgroundColor(Color.WHITE);


        // 把一个View转换成图片
        Bitmap cachebmp = loadBitmapFromView(view);

        FileOutputStream fos;
        String imagePath = "";
        try {
            // 判断手机设备是否有SD卡
            boolean isHasSDCard = Environment.getExternalStorageState().equals(
                    android.os.Environment.MEDIA_MOUNTED);
            if (isHasSDCard) {
                // SD卡根目录
                File sdRoot = Environment.getExternalStorageDirectory();
                File file = new File(sdRoot, Calendar.getInstance().getTimeInMillis()+".png");
                fos = new FileOutputStream(file);
                imagePath = file.getAbsolutePath();
                Toast.makeText(this,"保存成功！",Toast.LENGTH_SHORT).show();
                Log.i("CrimeFragment","------------------>创建文件成功!");
            } else {
                Log.i("CrimeFragment","------------------>创建文件失败!");
                Toast.makeText(this,"保存失败！",Toast.LENGTH_SHORT).show();
                throw new Exception("创建文件失败!");

            }

            cachebmp.compress(Bitmap.CompressFormat.PNG, 90, fos);

            fos.flush();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        view.destroyDrawingCache();
    }

    private Bitmap loadBitmapFromView(View v) {
        int w = v.getWidth();
        int h = v.getHeight();

        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);

        c.drawColor(Color.WHITE);
        /** 如果不设置canvas画布为白色，则生成透明 */

        v.layout(0, 0, w, h);
        v.draw(c);

        return bmp;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_enjoy,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item0:
                viewSaveToImage(mCardView);
                break;

            case R.id.menu_item1:
                Bitmap b=loadBitmapFromView(mCardView);
                Intent intentg = new Intent(Intent.ACTION_SEND);
                intentg.setType("image/*");
                intentg.putExtra(Intent.EXTRA_SUBJECT, "分享");
                intentg.putExtra(Intent.EXTRA_STREAM, Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), b, null,null)));
                intentg.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(intentg, "Share"));

                break;

            case R.id.menu_item2:
                Toast.makeText(this,"Music stop!",Toast.LENGTH_SHORT).show();
                this.stopService(new Intent(this,MusicPlayService.class));
//                mMediaPlayer.stop();
                break;

            case R.id.menu_item3:
                Toast.makeText(this,"Music play!",Toast.LENGTH_SHORT).show();
                this.startService(new Intent(this,MusicPlayService.class));

                break;


            default:break;

        }
        return super.onOptionsItemSelected(item);
    }
}
