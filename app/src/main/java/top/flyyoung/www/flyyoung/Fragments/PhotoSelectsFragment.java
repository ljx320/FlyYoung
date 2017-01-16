package top.flyyoung.www.flyyoung.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import top.flyyoung.www.flyyoung.Adapters.PhotoSelectAdapter;
import top.flyyoung.www.flyyoung.MainActivity;
import top.flyyoung.www.flyyoung.R;

/**
 * Created by 69133 on 2017/1/16.
 */

public class PhotoSelectsFragment extends Fragment {

    private Toolbar mImageToolbar;
    private RecyclerView mImageRecyclerView;
private MainActivity mMainActicity;
    private List<String> mPhotos;
    private PhotoSelectAdapter mSelectAdapter;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_photoselect,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.menu_photSelect:

                FragmentManager fragmentManager=mMainActicity.getSupportFragmentManager();
                FragmentTransaction transaction=fragmentManager.beginTransaction();

                AddPhotoFragment fragment=new AddPhotoFragment();



                Bundle bundle=new Bundle();

                Bundle myBundle=getArguments();
                if (myBundle!=null)
                {
                    int myAlbumID=myBundle.getInt("myAlbumID");
                    String myAlbumName=myBundle.getString("myAlbumName");
                    bundle.putInt("myAlbumID",myAlbumID);
                    bundle.putString("myAlbumName",myAlbumName);
                }

                ArrayList<String> selectedPhots=mSelectAdapter.getSelectedPhots();
                bundle.putStringArrayList("mselectedPhgots",selectedPhots);
                fragment.setArguments(bundle);

                transaction.replace(R.id.main_frameLayout,fragment);
                transaction.commit();

                break;

        }
        return  true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.my_photoselects,container,false);
        mImageToolbar=(Toolbar) view.findViewById(R.id.photoSelect_Toolbar);
        mImageRecyclerView=(RecyclerView) view.findViewById(R.id.photoSelect_Recyclerview);
        mMainActicity=(MainActivity)getActivity();
        mImageToolbar.setTitle(R.string.PhotoSelect_Toolbar);
        mImageToolbar.setTitleTextColor(Color.WHITE);
        mPhotos=new ArrayList<String>();

        mMainActicity.setSupportActionBar(mImageToolbar);
        setHasOptionsMenu(true);

        if (ContextCompat.checkSelfPermission(mMainActicity, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(mMainActicity,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
        else
        {
            OpenAlbum();

        }

        return  view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){

            case 1:
                if (grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){

                    OpenAlbum();
                }
                else
                {
                    Toast.makeText(getActivity(),"您拒绝了权限",Toast.LENGTH_SHORT).show();

                }

        }
    }



    private void  OpenAlbum(){

        final  String[] projectionPhotos={
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_TAKEN

        };

        Cursor cursor=MediaStore.Images.Media.query(mMainActicity.getContentResolver(),MediaStore.Images.Media.EXTERNAL_CONTENT_URI,projectionPhotos,"",null,MediaStore.Images.Media.DATE_TAKEN+" DESC");

        while (cursor.moveToNext()){
            String imagePath=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            mPhotos.add(imagePath);


        }

        mSelectAdapter=new PhotoSelectAdapter(mMainActicity,mPhotos);
        mImageRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mImageRecyclerView.setAdapter(mSelectAdapter);

      //  mSelectAdapter.notifyDataSetChanged();
    }
}
