package top.flyyoung.www.flyyoung.Fragments;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.flyyoung.www.flyyoung.Adapters.AlbumsAdapter;
import top.flyyoung.www.flyyoung.Datas.Album;
import top.flyyoung.www.flyyoung.MainActivity;
import top.flyyoung.www.flyyoung.R;
import top.flyyoung.www.flyyoung.Utils.HttpUtil;

/**
 * Created by 69133 on 2017/1/15.
 */

public class AddPhotoFragment extends Fragment {

    private  String mAlbumName;
    private int mAlbumID;
    private Toolbar mAddPhotoToolbar;

    private MainActivity mMainActivity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.add_photo,container,false);
        mMainActivity=(MainActivity) getActivity();
        mAddPhotoToolbar=(Toolbar)view.findViewById(R.id.addPhoto_Toolbar);
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
        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

       mMainActivity.getMenuInflater().inflate(R.menu.menu_addphoto,menu);
    }


}
