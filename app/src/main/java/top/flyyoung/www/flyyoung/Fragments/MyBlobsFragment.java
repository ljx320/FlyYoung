package top.flyyoung.www.flyyoung.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.flyyoung.www.flyyoung.Adapters.BlobsAdapter;
import top.flyyoung.www.flyyoung.Datas.Blobs;
import top.flyyoung.www.flyyoung.R;
import top.flyyoung.www.flyyoung.Utils.HttpUtil;

/**
 * Created by 69133 on 2017/1/12.
 */

public class MyBlobsFragment extends Fragment {

    private View BlobsView;
    private Toolbar BlobsToolBar;
    private List<Blobs> mBlobs;
    private RecyclerView BlobsRecyclerView;
    private SwipeRefreshLayout BlobsRefrshView;
    private FloatingActionButton BlobsFAB;

    private static  final  int UPDATE_TEXT=1;

    private Handler handler=new Handler(){

        public  void handleMessage(Message msg){

            switch (msg.what){
                case UPDATE_TEXT:


                    BlobsRefrshView.setRefreshing(true);



                    StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                    BlobsRecyclerView.setLayoutManager(layoutManager);


                    BlobsAdapter adapter = new BlobsAdapter(mBlobs, getActivity());
                    BlobsRecyclerView.setAdapter(adapter);

                    BlobsRefrshView.setRefreshing(false);


                    break;

                default:
                    break;

            }
        }

    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);




    }

    private  void LoadBlobs()
    {

        String address = "Blob/get";
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


                if (response.isSuccessful()) {

                    mBlobs = new Gson().fromJson(response.body().string(), new TypeToken<List<Blobs>>() {
                    }.getType());


                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            Message message=new Message();
                            message.what=UPDATE_TEXT;
                            handler.sendMessage(message);


                        }
                    }).start();




                } else {




                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        BlobsView = inflater.inflate(R.layout.my_blobs, container, false);
        BlobsToolBar = (Toolbar) BlobsView.findViewById(R.id.myblobs_toolBar);
        BlobsRefrshView = (SwipeRefreshLayout) BlobsView.findViewById(R.id.blobs_RefreshLayout);
        BlobsToolBar.setTitle(R.string.blobs_toolBarTitle);
        BlobsToolBar.setTitleTextColor(Color.WHITE);
        BlobsRecyclerView = (RecyclerView) BlobsView.findViewById(R.id.blobs_recyclerView);
        BlobsFAB=(FloatingActionButton)BlobsView.findViewById(R.id.blobs_AddBlob);
        BlobsRefrshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadBlobs();
            }
        });

        BlobsFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"添加点滴",Toast.LENGTH_SHORT).show();
            }
        });
        LoadBlobs();


        return BlobsView;
    }




}
