package top.flyyoung.www.flyyoung.Fragments;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.flyyoung.www.flyyoung.Adapters.PhotosAdapter;
import top.flyyoung.www.flyyoung.Datas.Photo;
import top.flyyoung.www.flyyoung.MainActivity;
import top.flyyoung.www.flyyoung.R;
import top.flyyoung.www.flyyoung.Utils.HttpUtil;

/**
 * Created by 69133 on 2017/1/13.
 */

public class MyPhotosFragment extends Fragment {

    private Toolbar mPhotoToolbar;
    private SwipeRefreshLayout mPhotoRefreshLayout;
    private RecyclerView mPhotoRecycler;
    private FloatingActionButton mPhotoFAB;

    private List<Photo> mPhotos;
    private int mAlbumID;

    private String mAlbumName;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.photo_info,container,false);

        mPhotoToolbar=(Toolbar) view.findViewById(R.id.photo_Toolbar);
        mPhotoRefreshLayout=(SwipeRefreshLayout) view.findViewById(R.id.photo_RefreshLayout);
        mPhotoRecycler=(RecyclerView)view.findViewById(R.id.photo_RecyclerView);
        mPhotoFAB=(FloatingActionButton)view.findViewById(R.id.photo_FAB);


        Bundle bundle=getArguments();
        if (bundle!=null){

            mAlbumName=bundle.getString("albumName");
            mAlbumID=bundle.getInt("albumID");

            mPhotoToolbar.setTitle(mAlbumName);
            mPhotoToolbar.setTitleTextColor(Color.WHITE);
            LoadPhotos();
        }
        mPhotoRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadPhotos();
            }
        });

        mPhotoFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddPhotoFragment addPhotoFragment=new AddPhotoFragment();

                Bundle bundle1=new Bundle();
                bundle1.putInt("myAlbumID",mAlbumID);
                bundle1.putString("myAlbumName",mAlbumName);
                addPhotoFragment.setArguments(bundle1);
                MainActivity activity=(MainActivity)getActivity();
                FragmentManager fragmentManager=activity.getSupportFragmentManager();
                FragmentTransaction transaction=fragmentManager.beginTransaction();

                transaction.replace(R.id.main_frameLayout,addPhotoFragment);
                transaction.addToBackStack(null);
                transaction.commit();


            }
        });

        return view;
    }

    private void LoadPhotos(){

        String address="Album/Get?id="+mAlbumID+"";
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    mPhotos=new Gson().fromJson(response.body().string(),new TypeToken<List<Photo>>(){}.getType());
                    new LoadPhotosTask().execute();
                }
                else
                {


                }
            }
        });

    }

    class LoadPhotosTask extends AsyncTask<Void,Integer,Boolean>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            mPhotoRefreshLayout.setRefreshing(true);
            mPhotoRecycler.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
            mPhotoRecycler.setAdapter(new PhotosAdapter(mPhotos,getActivity()));

            mPhotoRefreshLayout.setRefreshing(false);
        }
    }
}
