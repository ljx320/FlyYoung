package top.flyyoung.www.flyyoung.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.flyyoung.www.flyyoung.Datas.Blobs;
import top.flyyoung.www.flyyoung.Datas.UploadFileResult;
import top.flyyoung.www.flyyoung.MainActivity;
import top.flyyoung.www.flyyoung.R;
import top.flyyoung.www.flyyoung.Utils.HttpUtil;

/**
 * Created by 69133 on 2017/1/13.
 */

public class AddBlobFragment extends Fragment {

    private Toolbar mAddBlobToolbar;
    private Button mAddBlobSureButton;
    private Button mAddBlobImageCamera;
private ImageView mAddBlobWeatherImage;
    private TextView mAddBlobMyLocation;
private TextView mAddBlobWeatherVal;
    private LocationClient mLocationClient;
    private Button mAdBlobAddImageButton;
private EditText mAddBlobContent;
    private Context mContext;
    private Activity mActivity;

    private  String mWeatherImage;
    private ImageView mAddBlobSelectedImage;

    private final int TAKE_PHOTO=7;
    private final  int CHOOSE_PHOTO=6;
    private final  int RESUL_OK=-1;
    private Uri imageUri;

    private Bitmap mBlobShowImage;

    private List<UploadFileResult>  mBlobShowImageResult;
    private String mBlobShowImageVal;

    private Boolean PostBlobResult;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.add_blob,container,false);
        mAdBlobAddImageButton=(Button)view.findViewById(R.id.addblob_addImageButton);
        mAddBlobWeatherVal=(TextView)view.findViewById(R.id.AddBlob_weatherVal);
        mAddBlobToolbar=(Toolbar) view.findViewById(R.id.addBlob_Toolbar);
        mAddBlobSureButton=(Button)view.findViewById(R.id.addBlob_SureButton);
        mAddBlobMyLocation=(TextView)view.findViewById(R.id.addBlob_myLocation);
        mAddBlobToolbar.setTitle(R.string.addblob_ToolBar);
        mAddBlobToolbar.setTitleTextColor(Color.WHITE);
        mAddBlobWeatherImage=(ImageView)view.findViewById(R.id.addBlob_weatherImage);
        mAddBlobSelectedImage=(ImageView)view.findViewById(R.id.addblob_SelectedImage);
        mAddBlobImageCamera=(Button)view.findViewById(R.id.addblob_ImageCamera);
        mAddBlobContent=(EditText)view.findViewById(R.id.addblob_blobContent);
        mContext=getContext();
        mActivity=getActivity();

        mLocationClient=new LocationClient(mContext.getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());

        mAdBlobAddImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlbum();
            }
        });

        mAddBlobImageCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File outputImage=new File(getActivity().getExternalCacheDir(),"out_put.jpg");
                try{
                    if (outputImage.exists()){

                        outputImage.delete();
                    }
                    outputImage.createNewFile();

                }catch (IOException e){

                    e.printStackTrace();
                }

                if (Build.VERSION.SDK_INT>=24){

                    imageUri= FileProvider.getUriForFile(getActivity(),"top.flyyoung.www.flyyoung.fileprovider",outputImage);
                }else
                {
                    imageUri=Uri.fromFile(outputImage);

                }

                Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,TAKE_PHOTO);
            }
        });



        LoadWeatherImage();

        List<String> permissionList=new ArrayList<>();

        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){

            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);

        }

        if (ContextCompat.checkSelfPermission(mActivity,Manifest.permission.READ_PHONE_STATE)!=PackageManager.PERMISSION_GRANTED){

            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }

        if (ContextCompat.checkSelfPermission(mActivity,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){

            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        }

        if (!permissionList.isEmpty()){
            String[] permissions=permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(mActivity,permissions,1);

        }
        else
        {
            requestLocation();

        }

        mAddBlobSureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String blobContent=mAddBlobContent.getText().toString();

                if ("".equals(blobContent)){

                    Toast.makeText(getActivity(),R.string.Addblob_WarnigNoContent,Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mBlobShowImage==null){
                    Toast.makeText(getActivity(),R.string.Addblob_WarnigNoImage,Toast.LENGTH_SHORT).show();
                    return;

                }
               String filePath= getBitmapPath(mBlobShowImage);
                String address="Blob/PostImage";

               HttpUtil.uploadMultiFile(address, filePath, new Callback() {
                   @Override
                   public void onFailure(Call call, IOException e) {

                   }

                   @Override
                   public void onResponse(Call call, Response response) throws IOException {
        if (response.isSuccessful()){
            String responseResult=response.body().string();
          //  Log.d("result", responseResult);
            mBlobShowImageResult=new Gson().fromJson(responseResult,new TypeToken<List<UploadFileResult>>(){}.getType());
             new ImageUploadFinishiTask().execute();
        }
                       else {


        }
                   }
               });


            }
        });


        return view;
    }


    class  ImageUploadFinishiTask extends  AsyncTask<Void,Integer,Boolean>{

        @Override
        protected Boolean doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {


            for (UploadFileResult result :mBlobShowImageResult){
                mBlobShowImageVal=result.getName();

            }

            if (!mBlobShowImageVal.isEmpty()){

                PostInfosToService();
            }

           // Toast.makeText(getActivity(),mBlobShowImageVal,Toast.LENGTH_SHORT).show();
        }
    }

private  void InfosClear(){

    mAddBlobContent.setText("");
    mAddBlobSelectedImage.setVisibility(View.GONE);

}
    private  void PostInfosToService()
    {
        Blobs blob=new Blobs();
        blob.setBlobContent(mAddBlobContent.getText().toString());
        blob.setBlobImage(mBlobShowImageVal);

        SimpleDateFormat dateString=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeString=new SimpleDateFormat("HH:mm");


        blob.setCreateDate(dateString.format(new Date()));
        blob.setCreateTime(timeString.format(new Date()));

        blob.setID(0);
        blob.setShowCustomer(true);
        blob.setWeather(mAddBlobWeatherVal.getText().toString());
        blob.setBlobLocation(mAddBlobMyLocation.getText().toString());

        String JsonResult=new Gson().toJson(blob,blob.getClass());

        Log.d("blob", JsonResult);

        HttpUtil.PostJson("Blob/Post", JsonResult, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("blobresult", "error");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){

                    PostBlobResult=new Gson().fromJson(response.body().string(),Boolean.class);

                    new PostBlobResultTask().execute();

                }
                else
                {
                    Log.d("blobresult", "failed");

                }
            }
        });

    }


    class  PostBlobResultTask extends  AsyncTask<Void,Integer,Boolean>{

        @Override
        protected Boolean doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
           // super.onPostExecute(aBoolean);

            if (PostBlobResult){

Toast.makeText(getActivity(),R.string.Addblob_makeSuccess,Toast.LENGTH_SHORT).show();

                InfosClear();
//
//                Timer timer=new Timer();
//                TimerTask task=new TimerTask() {
//                    @Override
//                    public void run() {
//                        MainActivity activity=(MainActivity)getActivity();
//
//                        FragmentManager fragmentManager = activity.getSupportFragmentManager();
//                        final FragmentTransaction trasaction = fragmentManager.beginTransaction();
//
//                        MyBlobsFragment   mBlobFragment = new MyBlobsFragment();
//                        trasaction.replace(R.id.main_frameLayout, mBlobFragment);
//                        trasaction.commit();
//
//
//
//                    }
//                };
//                timer.schedule(task,1000*1);

            }
            else
            {
                Toast.makeText(getActivity(),R.string.Addblob_makeFailed,Toast.LENGTH_SHORT).show();

            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){




            case CHOOSE_PHOTO:

                if (resultCode==RESUL_OK){

                    ContentResolver resolver=getContext().getContentResolver();

                    try{
                        InputStream inputStream=resolver.openInputStream(data.getData());
                        mBlobShowImage= BitmapFactory.decodeStream(inputStream);

                        mAddBlobSelectedImage.setImageBitmap(mBlobShowImage);
                        mAddBlobSelectedImage.setVisibility(View.VISIBLE);


                    }
                    catch (FileNotFoundException e){

                        e.printStackTrace();
                    }
                }



                break;
            case TAKE_PHOTO:


                if (resultCode==RESUL_OK){
                    ContentResolver resolver=getContext().getContentResolver();
                    try{
                        mBlobShowImage=BitmapFactory.decodeStream(resolver.openInputStream(imageUri));



                        mAddBlobSelectedImage.setImageBitmap(mBlobShowImage);
                        mAddBlobSelectedImage.setVisibility(View.VISIBLE);


                    }
                    catch (FileNotFoundException e){

                        e.printStackTrace();
                    }

                }

            default:
        }
    }


    private String getBitmapPath(Bitmap bitmap){
        String filePath="";
        FileOutputStream fileOutputStream=null;

        try{
            String saveDir= Environment.getExternalStorageDirectory()+"/Blob_Photos";
            File dir=new File(saveDir);

            if (!dir.exists()){

                dir.mkdir();
            }

            SimpleDateFormat t=new SimpleDateFormat("yyyyMMddhhmmssSSS");
            String filename="BB"+(t.format(new Date()))+".jpg";
            File file=new File(saveDir,filename);

            fileOutputStream=new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
            filePath=file.getPath();



        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (fileOutputStream!=null){
                try{
                    fileOutputStream.close();
                }
                catch (Exception e){

                    e.printStackTrace();
                }

            }

            return  filePath;
        }

    }

    private void openAlbum(){

        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("'application/octet-stream");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    private void LoadWeatherImage(){

        String address="Weather/Get";
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){

                    mWeatherImage=new Gson().fromJson(response.body().string(),String.class);

                    new LoadWeatherTask().execute();
                }
            }
        });
    }

    class LoadWeatherTask extends AsyncTask<Void,Integer,Boolean>{


        @Override
        protected Boolean doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
           // super.onPostExecute(aBoolean);
            mAddBlobWeatherVal.setText(mWeatherImage);
            Glide.with(mActivity).load(mWeatherImage).into(mAddBlobWeatherImage);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:

                if (grantResults.length>0){
                    for ( int result:grantResults){
                        if (result!=PackageManager.PERMISSION_GRANTED){

                            Toast.makeText(mActivity,"您已经拒绝了位置请求",Toast.LENGTH_SHORT).show();

                            return;
                        }

                    }

                    requestLocation();

                }else
                {
                    Toast.makeText(mActivity,"发生了未知的错误",Toast.LENGTH_SHORT).show();

                }

                break;
            default:


        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mLocationClient.stop();
    }

    private void  requestLocation(){
        initLocation();
        mLocationClient.start();
    }


    private void initLocation(){

        LocationClientOption option=new LocationClientOption();
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);

    }

    public class MyLocationListener implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            StringBuilder stringBuilder=new StringBuilder();

            stringBuilder.append(bdLocation.getCountry()).append(" ");
            stringBuilder.append(bdLocation.getProvince()).append(" ");
            stringBuilder.append(bdLocation.getCity()).append(" ");
            stringBuilder.append(bdLocation.getDistrict()).append(" ");
            stringBuilder.append(bdLocation.getStreet()).append(" ");
            stringBuilder.append( bdLocation.getStreetNumber());



            mAddBlobMyLocation.setText(stringBuilder);

        }
    }

}
