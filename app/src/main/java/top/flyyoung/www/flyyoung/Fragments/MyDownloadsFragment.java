package top.flyyoung.www.flyyoung.Fragments;

import android.content.Intent;
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
import android.util.Log;
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
import top.flyyoung.www.flyyoung.Adapters.DownloadsAdapter;
import top.flyyoung.www.flyyoung.Datas.Download;
import top.flyyoung.www.flyyoung.MainActivity;
import top.flyyoung.www.flyyoung.R;
import top.flyyoung.www.flyyoung.Utils.HttpUtil;

/**
 * Created by 69133 on 2017/1/13.
 */

public class MyDownloadsFragment extends Fragment {
private SwipeRefreshLayout mDownloadRefreshLayout;
    private RecyclerView mDownloadRecyclerView;
    private FloatingActionButton mDownloadFAB;
    private Toolbar mDownloadToolbar;

    private List<Download> mDownloads;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.my_downloads,container,false);
        mDownloadRefreshLayout=(SwipeRefreshLayout) view.findViewById(R.id.Downloads_SwipeRefreshLayout);
        mDownloadRecyclerView=(RecyclerView)view.findViewById(R.id.Downloads_RecyclerView);
        mDownloadFAB=(FloatingActionButton)view.findViewById(R.id.Downloads_FAB);
        mDownloadToolbar=(Toolbar)view.findViewById(R.id.Downloads_Toolbar);
        mDownloadToolbar.setTitle(R.string.downloads_toolBarTitle);
        mDownloadToolbar.setTitleTextColor(Color.WHITE);
        mDownloadFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity mainActivity=(MainActivity)getActivity();
                FragmentManager fragmentManager=mainActivity.getSupportFragmentManager();
                FragmentTransaction transaction=fragmentManager.beginTransaction();


                AddDownloadFragment downloadFragment=new AddDownloadFragment();

                transaction.replace(R.id.main_frameLayout,downloadFragment);
                transaction.addToBackStack(null);
                transaction.commit();


            }
        });

        mDownloadRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadDownloads();
            }
        });
        LoadDownloads();
        return view;
    }

    private  void LoadDownloads(){

        String address="Download/get";
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
if (response.isSuccessful()){
     mDownloads=new Gson().fromJson(response.body().string(),new TypeToken<List<Download>>(){}.getType());

    new LoadDwonloadTask().execute();

}
            }
        });


    }

    class  LoadDwonloadTask extends AsyncTask<Void,Integer,Boolean>{

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

            mDownloadRefreshLayout.setRefreshing(true);
            mDownloadRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));


            MainActivity mainActivity=(MainActivity) getActivity();

            mDownloadRecyclerView.setAdapter(new DownloadsAdapter(mDownloads,mainActivity));
            mDownloadRefreshLayout.setRefreshing(false);
        }
    }
}
