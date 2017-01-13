package top.flyyoung.www.flyyoung.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.flyyoung.www.flyyoung.Adapters.ArticlesAdapter;
import top.flyyoung.www.flyyoung.Adapters.BlobsAdapter;
import top.flyyoung.www.flyyoung.Datas.Article;
import top.flyyoung.www.flyyoung.R;
import top.flyyoung.www.flyyoung.Utils.HttpUtil;

/**
 * Created by 69133 on 2017/1/13.
 */

public class MyArticleFragment extends Fragment {

    private View ArticlesView;
    private Toolbar ArticlesToolBar;
    private RecyclerView ArticlesRecyclerView;
    private SwipeRefreshLayout ArticlesRefreshLayout;
    private List<Article> mArticles;
    private FloatingActionButton ArticlesAddFAB;


    private static  final  int UPDATE_TEXT=2;

    private Handler handler=new Handler(){

        public  void handleMessage(Message msg){

            switch (msg.what){
                case UPDATE_TEXT:


                    ArticlesRefreshLayout.setRefreshing(true);



                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    ArticlesRecyclerView.setLayoutManager(layoutManager);


                    ArticlesAdapter adapter = new ArticlesAdapter(mArticles, getActivity());
                    ArticlesRecyclerView.setAdapter(adapter);

                    ArticlesRefreshLayout.setRefreshing(false);


                    break;

                default:
                    break;

            }
        }

    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ArticlesView = inflater.inflate(R.layout.my_articles, container, false);

        ArticlesToolBar = (Toolbar) ArticlesView.findViewById(R.id.articles_toolbar);
        ArticlesToolBar.setTitle(R.string.articles_toolBarTitle);
        ArticlesToolBar.setTitleTextColor(Color.WHITE);
        ArticlesRecyclerView = (RecyclerView) ArticlesView.findViewById(R.id.articles_RecycleView);
        ArticlesRefreshLayout = (SwipeRefreshLayout) ArticlesView.findViewById(R.id.articles_RefreshLayout);
        ArticlesAddFAB=(FloatingActionButton)ArticlesView.findViewById(R.id.articles_AddArticle);

        ArticlesAddFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"添加文章",Toast.LENGTH_SHORT).show();
            }
        });

        LoadArticles();

        ArticlesRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadArticles();
            }
        });

        return ArticlesView;
    }

    private void LoadArticles() {
        String address = "article/get";
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    mArticles=new Gson().fromJson(response.body().string(),new TypeToken<List<Article>>(){}.getType());

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            Message message=new Message();
                            message.what=UPDATE_TEXT;
                            handler.sendMessage(message);


                        }
                    }).start();


                }
            }
        });


    }
}
