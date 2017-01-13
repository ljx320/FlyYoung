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
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.flyyoung.www.flyyoung.R;
import top.flyyoung.www.flyyoung.Utils.HttpUtil;

/**
 * Created by 69133 on 2017/1/13.
 */

public class AddBlobFragment extends Fragment {

    private Toolbar mAddBlobToolbar;
    private Button mAddBlobSureButton;
private ImageView mAddBlobWeatherImage;
    private TextView mAddBlobMyLocation;
private TextView mAddBlobWeatherVal;
    private LocationClient mLocationClient;
    private Button mAdBlobAddImageButton;

    private Context mContext;
    private Activity mActivity;

    private  String mWeatherImage;
    private ImageView mAddBlobSelectedImage;

    private final  int CHOOSE_PHOTO=6;

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




        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){

            case CHOOSE_PHOTO:
                ContentResolver resolver=getContext().getContentResolver();

                try{
                    InputStream inputStream=resolver.openInputStream(data.getData());
                    Bitmap bitmap= BitmapFactory.decodeStream(inputStream);

                    mAddBlobSelectedImage.setImageBitmap(bitmap);
                    mAddBlobSelectedImage.setVisibility(View.VISIBLE);
                }
                catch (FileNotFoundException e){

                    e.printStackTrace();
                }


                break;
            default:
        }
    }

    private void openAlbum(){

        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
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
