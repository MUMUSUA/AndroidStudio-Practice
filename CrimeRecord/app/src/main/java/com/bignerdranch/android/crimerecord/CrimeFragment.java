package com.bignerdranch.android.crimerecord;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.AbstractWindowedCursor;
import android.database.Cursor;
import android.database.CursorWindow;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
        import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
        import android.widget.CheckBox;
        import android.widget.CompoundButton;
        import android.widget.CompoundButton.OnCheckedChangeListener;
        import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;


public class CrimeFragment extends Fragment implements View.OnClickListener{
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;

    private Crime mCrime;
    private EditText mTitleField;
    private EditText mPainterField;
    private EditText mDetailField;
    private ImageView picture;
    private Uri imageUri;
    private Bitmap b;
    private TextView mCount;
    private Button mDialog;
    private Button mSendCrimeButton;
    private Button mDateButton;
    private Button mCreateCrimeButton;
    private Button mReportCrimeButton;
    private CrimeDbLab mCrimeDbLab;
    private UUID crime_id;
    private CrimeLab mCrimeLab=CrimeLab.getInstance(CrimeFragment.this.getActivity());
//    public static final String EXTRA_CRIME_ID = "criminalintent.CRIME_ID";
//    public static CrimeFragment newInstance(UUID crimeId) {
//        Bundle args = new Bundle();
//        args.putSerializable(EXTRA_CRIME_ID, crimeId);
//
//        CrimeFragment fragment = new CrimeFragment();
//        fragment.setArguments(args);
//
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//mCrime = new Crime();


        mCrimeLab=CrimeLab.getInstance(CrimeFragment.this.getActivity());
        Intent intent=getActivity().getIntent();
        crime_id=(UUID)intent.getSerializableExtra("crime_id");
        Log.i("CrimeFragment","------------------>"+crime_id);
        mCrime=mCrimeLab.getCrime(crime_id);
        Log.i("CrimeFragment","------------------>"+mCrime.getId());

        Log.i("CrimeFragment","------------------>OK");
        mCrimeDbLab=CrimeDbLab.getInstance(getActivity());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, parent, false);
        mCount=(TextView)v.findViewById(R.id.count_TextView);
//        Button chooseFromAlbum = (Button) v.findViewById(R.id.choose_from_album);
        picture = (ImageView) v.findViewById(R.id.picture);

        mDialog=(Button)v.findViewById(R.id.bottom_dialog);
        mDialog.setOnClickListener(this);

        if(crime_id==null)
        {  picture.setImageResource(R.drawable.upload);
            picture.buildDrawingCache();
            b=picture.getDrawingCache();
            b=((BitmapDrawable)picture.getDrawable()).getBitmap();
            mCrime.setPhotoId(b);}
        else
            picture.setImageBitmap(mCrime.getPhotoId());
        Log.i("CrimeFragment","------------------>PhotoId"+mCrime.getId());
        mTitleField = (EditText)v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());

        mDateButton = (Button)v.findViewById(R.id.crime_date);
        mDateButton.setText(mCrime.getDate().toString());
        mDateButton.setEnabled(false);
        mPainterField=(EditText)v.findViewById(R.id.crime_painter);
        mPainterField.setText(mCrime.getPainter());
        mDetailField=(EditText) v.findViewById(R.id.crime_detail);
        mDetailField.setText(mCrime.getDesc());
        if(mCrime.getTitle()==null)
            mCount.setText("0");
        else
            mCount.setText(Integer.toString(mCrime.getTitle().length()));



        // 监听多个输入框
        TextChange textChange = new TextChange();
        mTitleField.addTextChangedListener(textChange);
        mDetailField.addTextChangedListener(textChange);
        mPainterField.addTextChangedListener(textChange);
        mTitleField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {

                mCrime.setTitle(c.toString());
                mCount.setText(Integer.toString(c.length()));
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank

            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });
        mDetailField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {

                mCrime.setDesc(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank

            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });
        mPainterField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {

                mCrime.setPainter(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank

            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });


        mCreateCrimeButton=(Button)v.findViewById(R.id.create_crime_Button);
        mCreateCrimeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mCrime=new Crime();
                mTitleField.setText("");
                mDetailField.setText("");
                mPainterField.setText("");
                picture.setImageBitmap(b);


            }
        });


        mSendCrimeButton=(Button)v.findViewById(R.id.send_crime_Button);
        mSendCrimeButton.setEnabled(false);
        mSendCrimeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Cursor cursor=mCrimeDbLab.queryCrime(mCrime);
                cursor.moveToFirst();
                if(cursor.getCount()==0){
                    Log.i("CrimeFragment","------------------>SendBefore");
                    mCrimeDbLab.addCrime(mCrime);
                    Log.i("CrimeFragment","------------------>SendAfter");}
                else{
                    Log.i("CrimeFragment","------------------>UpdateBefore");
                    mCrimeDbLab.updateCrime(mCrime);
                    Log.i("CrimeFragment","------------------>UpdateAfter");}



//                if(mCrimeLab.getCrimes().contains(mCrime)){
//          mCrimeLab.getCrimes().remove(mCrime);
//                }
//                mCrimeLab.getCrimes().add(mCrime);
//                for(Crime mCrime:mCrimeLab.getCrimes()){
//                    Log.i("Crime","------------------>crime"+mCrime.getPhotoId().toString());
//
//                }


//                Log.i("CrimeFragment","------------------>SendBefore");
//                mCrimeDbLab.addCrime(mCrime);
//                Log.i("CrimeFragment","------------------>SendAfter");
            }
        });

        mReportCrimeButton=(Button)v.findViewById(R.id.report_crime_Button);
        mReportCrimeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                mCrimeLab.getCrimes().clear();
                Log.i("CrimeFragment","------------------>mCrimeLab.getCrimes().size():"+mCrimeLab.getCrimes().size());
                Cursor cursor=mCrimeDbLab.queryCrimes(null,null);
                if(cursor==null)
                    Toast.makeText(getActivity(),"cursor is null!",Toast.LENGTH_SHORT).show();
                else{

                  //  Toast.makeText(getActivity(),"cursor's size is"+cursor.getCount(),Toast.LENGTH_SHORT).show();
                    cursor.moveToFirst();
                    while(!cursor.isAfterLast()){

                        Log.i("CrimeFragment","title:"+cursor.getString(cursor.getColumnIndex("title")));
                        Log.i("CrimeFragment","------------------>date:"+cursor.getLong(cursor.getColumnIndex("date")));
                        Crime c=new Crime();
                        c.setId(UUID.fromString(cursor.getString(cursor.getColumnIndex("uuid"))));
                        c.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                        c.setPainter(cursor.getString(cursor.getColumnIndex("painter")));
                        c.setDate(new Date(cursor.getLong(cursor.getColumnIndex("date"))));
                        c.setDesc(cursor.getString(cursor.getColumnIndex("desc")));
                        c.setPhotoId(convertToBitmap(cursor.getString(cursor.getColumnIndex("photo"))));


                        cursor.moveToNext();
                        mCrimeLab.getCrimes().add(c);
                    }
                }

                startActivity(new Intent(getActivity(),CrimeListActivity.class));
                Log.i("CrimeFragment","------------------>CrimeListActivity");
       //         startActivity(new Intent(getActivity(),CrimeListActivity.class));


            }
        });

        return v;
    }


    // EditText监听器
    class TextChange implements TextWatcher {

        @Override
        public void afterTextChanged(Editable arg0) {

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {

        }

        @Override
        public void onTextChanged(CharSequence cs, int start, int before,
                                  int count) {

            boolean Sign1 = mTitleField.getText().length() > 0;
            boolean Sign2 = mPainterField.getText().length() > 0;
            boolean Sign3 = mDetailField.getText().length() > 0;
            if (Sign1 &Sign2 & Sign3) {
                mSendCrimeButton.setEnabled(true);
            }
            // 在layout文件中，对Button的text属性应预先设置默认值，否则刚打开程序的时候Button是无显示的
            else {
                mSendCrimeButton.setEnabled(false);
            }
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bottom_dialog:
                //弹出对话框
                setDialog();
                break;
            case R.id.btn_choose_img:
                //选择照片按钮
                Toast.makeText(this.getActivity(), "请选择照片", Toast.LENGTH_SHORT).show();
                if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this.getActivity(), new String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
                } else {
                    openAlbum();
                }

                break;
            case R.id.btn_open_camera:
                //拍照按钮
                Toast.makeText(this.getActivity(), "即将打开相机", Toast.LENGTH_SHORT).show();
                // 创建File对象，用于存储拍照后的图片
                File outputImage = new File(getContext().getExternalCacheDir(), "output_image.jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT < 24) {
                    imageUri = Uri.fromFile(outputImage);
                } else {
                    imageUri = FileProvider.getUriForFile(this.getActivity(), "com.bignerdranch.android.crimerecord.fileprovider", outputImage);
                }
                // 启动相机程序
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);
                break;


        }
    }

    private void setDialog() {
        Dialog mCameraDialog = new Dialog(this.getActivity(), R.style.BottomDialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(this.getActivity()).inflate(
                R.layout.bottom_dialog, null);
        //初始化视图
        root.findViewById(R.id.btn_choose_img).setOnClickListener(this);
        root.findViewById(R.id.btn_open_camera).setOnClickListener(this);
//        root.findViewById(R.id.btn_cancel).setOnClickListener(this);
        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
//        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();

        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        mCameraDialog.show();
    }

    public Bitmap convertToBitmap(String base64String) {

        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);

        Bitmap bitmapResult = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        return bitmapResult;

    }


    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(getActivity(), "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        // 将拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(imageUri));
                        picture.setImageBitmap(bitmap);
                        mCrime.setPhotoId(bitmap);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    } else {
                        // 4.4以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(getActivity(), uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath); // 根据图片路径显示图片
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getActivity().getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            picture.setImageBitmap(bitmap);
            mCrime.setPhotoId(bitmap);
        } else {
            Toast.makeText(getActivity(), "failed to get image", Toast.LENGTH_SHORT).show();

        }
    }

}