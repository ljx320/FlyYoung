package top.flyyoung.www.flyyoung.Fragments;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.print.PrintHelper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
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
import top.flyyoung.www.flyyoung.Adapters.MessagesAdapter;
import top.flyyoung.www.flyyoung.Datas.Message;
import top.flyyoung.www.flyyoung.R;
import top.flyyoung.www.flyyoung.Utils.HttpUtil;

/**
 * Created by 69133 on 2017/1/13.
 */

public class MyMessagesFragment extends Fragment {

    private SwipeRefreshLayout mMessageRefreshLayout;
    private RecyclerView mMessageRecyclerView;
    private Toolbar mMessageToolbar;

    private List<Message> mMessages;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.my_messages,container,false);
        mMessageToolbar=(Toolbar)view.findViewById(R.id.messages_Toolbar);
        mMessageRecyclerView=(RecyclerView)view.findViewById(R.id.messages_RecyclerView);
        mMessageRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.messages_SwipeRefreshLayout);
        mMessageToolbar.setTitle(R.string.messages_toolBarTitle);
        mMessageToolbar.setTitleTextColor(Color.WHITE);
        mMessageRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadMessages();
            }
        });

        LoadMessages();
        return  view;
    }

    private void  LoadMessages(){
String address="Message/get";
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){

                mMessages=new Gson().fromJson(response.body().string(),new TypeToken<List<Message>>(){}.getType());
                    new LoadMessagesTask().execute();

                }
            }
        });

    }



    class LoadMessagesTask extends AsyncTask<Void,Integer,Boolean>{

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
            //
            mMessageRefreshLayout.setRefreshing(true);
            mMessageRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mMessageRecyclerView.setAdapter(new MessagesAdapter(mMessages));


            mMessageRefreshLayout.setRefreshing(false);
        }
    }
}
