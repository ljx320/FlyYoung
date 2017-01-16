package top.flyyoung.www.flyyoung.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.flyyoung.www.flyyoung.Adapters.AlbumsAdapter;
import top.flyyoung.www.flyyoung.Adapters.PhotosItemsAddAdapter;
import top.flyyoung.www.flyyoung.Datas.Album;
import top.flyyoung.www.flyyoung.Datas.UploadFileResult;
import top.flyyoung.www.flyyoung.MainActivity;
import top.flyyoung.www.flyyoung.R;
import top.flyyoung.www.flyyoung.Utils.BitmapUtil;
import top.flyyoung.www.flyyoung.Utils.HttpUtil;

/**
 * Created by 69133 on 2017/1/15.
 */

public class AddPhotoFragment extends Fragment {

    private  String mAlbumName;
    private int mAlbumID;
    private Toolbar mAddPhotoToolbar;
    private RecyclerView mAddPhotoRecyclerView;
    private Uri imageUri;
    private MainActivity mMainActivity;
private String mOnePhoto;
    private List<String> mPotos;
    private Bitmap mBlobShowImage;
    private final int TAKE_PHOTO=11;

    private final  int RESUL_OK=-1;
    private String mResults;


    private PhotosItemsAddAdapter mPhotosAdapter;


    public void setPhotos(List<String> selectedPhotos){
        mPotos.addAll(selectedPhotos);
        mPhotosAdapter.notifyDataSetChanged();

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.add_photo,container,false);
        mMainActivity=(MainActivity) getActivity();
        mAddPhotoToolbar=(Toolbar)view.findViewById(R.id.addPhoto_Toolbar);

        mAddPhotoRecyclerView=(RecyclerView)view.findViewById(R.id.addPhoto_RecyclerView);
        mMainActivity=(MainActivity) getActivity();
        mPotos=new ArrayList<String>();

        mAddPhotoRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        mPhotosAdapter=new PhotosItemsAddAdapter(mPotos,getActivity());

        mAddPhotoRecyclerView.setAdapter(mPhotosAdapter);

//        mAddPhotoToolbar.setTitle(R.string.AddPhoto_Toolbar);
//        mAddPhotoToolbar.setTitleTextColor(Color.WHITE);



mMainActivity.setSupportActionBar(mAddPhotoToolbar);
        setHasOptionsMenu(true);
        Bundle bundle=getArguments();

        if (bundle!=null){

            mAlbumID=bundle.getInt("myAlbumID");
            mAlbumName=bundle.getString("myAlbumName");

            mAddPhotoToolbar.setTitle(mAlbumName);
            mAddPhotoToolbar.setTitleTextColor(Color.WHITE);


           try
           {
               ArrayList<String> selectedPhotos=bundle.getStringArrayList("mselectedPhgots");
               mPotos.addAll(selectedPhotos);

               mPhotosAdapter.notifyDataSetChanged();

           }catch (Exception e){
               e.printStackTrace();

           }

        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

       mMainActivity.getMenuInflater().inflate(R.menu.menu_addphoto,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.menu_addPhoto_camera:
                File outputImage=new File(getActivity().getExternalCacheDir(),"out_put_phots.jpg");
                try{
                    if (outputImage.exists()){

                        outputImage.delete();
                    }
                    outputImage.createNewFile();

                }catch (IOException e){

                    e.printStackTrace();
                }

                if (Build.VERSION.SDK_INT>=24){
                  //  imageUri= Uri.fromFile(outputImage);
                    imageUri= FileProvider.getUriForFile(getActivity(),"top.flyyoung.www.flyyoung.fileprovider",outputImage);
                }else
                {
                    imageUri= Uri.fromFile(outputImage);

                }

                Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,TAKE_PHOTO);

                break;

            case  R.id.menu_addPhoto_Publish:

                String address="Photo/PostPhotos?albumid="+mAlbumID+"";
                HttpUtil.uploadMultiFile(address, mPotos, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        if (response.isSuccessful()){
                            List<UploadFileResult> results=new Gson().fromJson(response.body().string(),new TypeToken<List<UploadFileResult>>(){}.getType());

                            mResults=Integer.toString(results.size()) ;

                            new PublisPhotosTask().execute();
                        }


                    }
                });

                break;


            case  R.id.menu_addPhoto_Album:

                FragmentManager fragmentManager=mMainActivity.getSupportFragmentManager();
                FragmentTransaction transaction=fragmentManager.beginTransaction();
                PhotoSelectsFragment selectsFragment=new PhotoSelectsFragment();

                Bundle bundle=new Bundle();
                bundle.putInt("myAlbumID",mAlbumID);
                bundle.putString("myAlbumName",mAlbumName);
                selectsFragment.setArguments(bundle);

                transaction.replace(R.id.main_frameLayout,selectsFragment);
                transaction.addToBackStack(null);
                transaction.commit();

                break;

        }
        return  true;
    }



    private class PublisPhotosTask extends AsyncTask<Void,Integer,Boolean>{


        @Override
        protected Boolean doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
           // super.onPostExecute(aBoolean);

            Toast.makeText(getActivity(),"成功发布了"+mResults+"张图片",Toast.LENGTH_SHORT).show();

            mPotos.clear();
            mPotos=new ArrayList<>();
            mPhotosAdapter.notifyDataSetChanged();

            Timer timer=new Timer();
            TimerTask task=new TimerTask() {
                @Override
                public void run() {

                    FragmentManager fragmentManager=mMainActivity.getSupportFragmentManager();
                    FragmentTransaction transaction=fragmentManager.beginTransaction();

                    MyPhotosFragment photosFragment=new MyPhotosFragment();

                    Bundle bundle=new Bundle();
                   bundle.putString("albumName", mAlbumName);
                    bundle.putInt("albumID",mAlbumID);
                    photosFragment.setArguments(bundle);

                    transaction.replace(R.id.main_frameLayout,photosFragment);
                    transaction.commit();

                }
            };
            timer.schedule(task,1000*1);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){





            case TAKE_PHOTO:


                if (resultCode==RESUL_OK){
                    ContentResolver resolver=getContext().getContentResolver();
                    try{
                        mBlobShowImage=BitmapFactory.decodeStream(resolver.openInputStream(imageUri));



                       mOnePhoto= BitmapUtil.getBitmapPath(mBlobShowImage,"AddPhotos");
                        mPotos.add(mOnePhoto);

                        mPhotosAdapter.notifyDataSetChanged();


                    }
                    catch (FileNotFoundException e){

                        e.printStackTrace();
                    }

                }

            default:


        }
    }
}
