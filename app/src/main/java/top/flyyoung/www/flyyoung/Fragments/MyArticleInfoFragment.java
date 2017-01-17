package top.flyyoung.www.flyyoung.Fragments;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.flyyoung.www.flyyoung.Datas.Article;
import top.flyyoung.www.flyyoung.R;
import top.flyyoung.www.flyyoung.Utils.HttpUtil;

/**
 * Created by 69133 on 2017/1/13.
 */

public class MyArticleInfoFragment extends Fragment {
private Toolbar articleInfosToolbar;
    private int mArticleID;
    private  static  final  int UPDATE_TEXT=4;
    private  Article mArticle;


    private TextView mArticleTitle;
    private  TextView mArticleDate;
    private TextView mArticleCount;
    private WebView mArticleContent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.article_infos,container,false);

        articleInfosToolbar=(Toolbar) view.findViewById(R.id.aritcleInfos_toolbar);
        mArticleTitle=(TextView)view.findViewById(R.id.articleInfos_Title);
        mArticleDate=(TextView)view.findViewById(R.id.articleInfos_Date);
        mArticleCount=(TextView)view.findViewById(R.id.articleInfos_ReadCount);
        mArticleContent=(WebView) view.findViewById(R.id.articleInfos_Content);

        Bundle bundle=getArguments();
        if (bundle!=null){

            mArticleID=bundle.getInt("articleID");
//            articleInfosToolbar.setTitle(Integer.toString(mArticleID));
            LoadArticle();
        }


        return  view;
    }
//private Handler handler=new Handler(){
//
//  public void handleMessage(Message msg){
//      switch (msg.what){
//
//          case UPDATE_TEXT:
//              articleInfosToolbar.setTitle(mArticle.getArticleTitle());
//              break;
//          default:
//              break;
//
//      }
//
//  }
//
//};
    private  void LoadArticle()
    {
            String address="article/get?id="+mArticleID+"";
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()){
                    mArticle=new Gson().fromJson(response.body().string(),Article.class);
//
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            Message message=new Message();
//                            message.what=UPDATE_TEXT;
//                            handler.handleMessage(message);
//
//                        }
//                    }).start();
                    new InitArticleInfos().execute();
                }
                else
                {


                }


            }
        });

    }

    class InitArticleInfos extends AsyncTask<Void,Integer,Boolean>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            return  true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            articleInfosToolbar.setTitle(mArticle.getArticleTitle());
            articleInfosToolbar.setTitleTextColor(Color.WHITE);
            mArticleTitle.setText(mArticle.getArticleTitle().toString());
            mArticleDate.setText("时间："+ mArticle.getCreateDate().toString()+" "+mArticle.getCreateTime().toString());
            mArticleCount.setText("阅读："+Integer.toString(mArticle.getReadCount()) );

            //mArticleContent.getSettings().setDefaultTextEncodingName("utf-8");
                mArticleContent.loadData(mArticle.getArticleContent(),"text/html; charset=utf-8",null) ;



        }



    }


}
