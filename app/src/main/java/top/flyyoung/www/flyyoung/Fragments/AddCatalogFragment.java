package top.flyyoung.www.flyyoung.Fragments;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.flyyoung.www.flyyoung.Datas.ArticleCatalog;
import top.flyyoung.www.flyyoung.R;
import top.flyyoung.www.flyyoung.Utils.HttpUtil;

/**
 * Created by 69133 on 2017/1/17.
 */

public class AddCatalogFragment extends Fragment {

    private Toolbar mCatalogToolbar;
    private EditText mCatalogName;
    private EditText mCatalogShort;
    private Button mCatalogSure;
    private CheckBox mCatalogFlags;
    private  Boolean mCatalogResult;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.add_articlecatalog,container,false);
        mCatalogToolbar=(Toolbar)view.findViewById(R.id.Catalog_Toolbar);
        mCatalogToolbar.setTitle(R.string.Catalog_Toolbar);
        mCatalogToolbar.setTitleTextColor(Color.WHITE);
        mCatalogFlags=(CheckBox)view.findViewById(R.id.Article_catalog_Flags);
        mCatalogName=(EditText)view.findViewById(R.id.Article_catalog_Name);
        mCatalogShort=(EditText)view.findViewById(R.id.Article_catalog_Short);
        mCatalogSure=(Button)view.findViewById(R.id.Article_catalog_Sure);

        mCatalogSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String CatalogName=mCatalogName.getText().toString();
                String CatalogShort=mCatalogShort.getText().toString();

                if ("".equals(CatalogName) ){

                    Toast.makeText(getActivity(),"分类名称要填写哦",Toast.LENGTH_SHORT).show();
                    return;
                }

                if ("".equals(CatalogShort)){

                    Toast.makeText(getActivity(),"分类简称要填写哦",Toast.LENGTH_SHORT).show();
                    return;
                }

                ArticleCatalog newCatalog=new ArticleCatalog();
                newCatalog.setID(0);
                newCatalog.setArticleCount(0);
                newCatalog.setCatalogLevel(1);
                newCatalog.setCatalogName(CatalogName);
                newCatalog.setCatalogShoet(CatalogShort);
                newCatalog.setCatalogSuiper(0);

                if (mCatalogFlags.isChecked()){
                    newCatalog.setFlags(1);

                }
                else
                {

                    newCatalog.setFlags(0);
                }

                String json=new Gson().toJson(newCatalog,ArticleCatalog.class);
                String Address="Article/PostCatalog";
                HttpUtil.PostJson(Address, json, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        if (response.isSuccessful()){

                        mCatalogResult=new Gson().fromJson(response.body().string(),Boolean.class);
new uploadCatalogTask().execute();

                        }
                        else
                        {


                        }
                    }
                });


            }
        });

        return  view;
    }

    class  uploadCatalogTask extends AsyncTask<Void,Integer,Boolean>{

        @Override
        protected Boolean doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {

            if (mCatalogResult){

                Toast.makeText(getActivity(),"分类添加成功",Toast.LENGTH_SHORT).show();
                mCatalogName.setText("");
                mCatalogShort.setText("");
            }
            else
            {
                Toast.makeText(getActivity(),"分类添加失败",Toast.LENGTH_SHORT).show();

            }


        }
    }
}
