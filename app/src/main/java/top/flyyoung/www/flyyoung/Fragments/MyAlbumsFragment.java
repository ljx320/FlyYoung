package top.flyyoung.www.flyyoung.Fragments;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.flyyoung.www.flyyoung.Adapters.AlbumsAdapter;
import top.flyyoung.www.flyyoung.Datas.Album;
import top.flyyoung.www.flyyoung.Datas.Message;
import top.flyyoung.www.flyyoung.R;
import top.flyyoung.www.flyyoung.Utils.HttpUtil;

/**
 * Created by 69133 on 2017/1/13.
 */

public class MyAlbumsFragment extends Fragment {

    private SwipeRefreshLayout mAlbumRefreshLayout;
    private RecyclerView mAlbumRecyclerView;
    private Toolbar mAlbumToolbar;

    private List<Album> mAlbums;

    private  final  int MESSAGE_ID=5;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.my_albums,container,false);
        mAlbumRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.album_SwipeRefreshLayout);
        mAlbumRecyclerView=(RecyclerView)view.findViewById(R.id.album_RecycleLayout);
        mAlbumToolbar=(Toolbar)view.findViewById(R.id.album_listToolbar);

        mAlbumToolbar.setTitle(R.string.albums_toolBarTile);
        mAlbumToolbar.setTitleTextColor(Color.WHITE);

        LoadAlbums();

        mAlbumRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadAlbums();
            }
        });
        return  view;



    }

    private void LoadAlbums(){

        String address="Album/get";
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()){

                    mAlbums=new Gson().fromJson(response.body().string(),new TypeToken<List<Album>>(){}.getType());


                    new LoadAlbumTask().execute();
                }
            }
        });

    }

    class LoadAlbumTask extends AsyncTask<Void,Integer,Boolean>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            mAlbumRefreshLayout.setRefreshing(true);
            mAlbumRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
            mAlbumRecyclerView.setAdapter(new AlbumsAdapter(mAlbums,getActivity()));
            mAlbumRefreshLayout.setRefreshing(false);

        }
    }


}
