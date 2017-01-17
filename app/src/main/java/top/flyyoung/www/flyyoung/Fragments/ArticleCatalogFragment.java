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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import top.flyyoung.www.flyyoung.Adapters.ArticleCatalogsAdapter;
import top.flyyoung.www.flyyoung.Datas.ArticleCatalog;
import top.flyyoung.www.flyyoung.MainActivity;
import top.flyyoung.www.flyyoung.R;
import top.flyyoung.www.flyyoung.Utils.HttpUtil;

/**
 * Created by 69133 on 2017/1/16.
 */

public class ArticleCatalogFragment extends Fragment {

    private Toolbar mCatalogToolbar;
    private RecyclerView mCatalogRecycler;
    private SwipeRefreshLayout mCatalogRefresh;

    private List<ArticleCatalog> mCatalogs;
    private FloatingActionButton mCatalogAddButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.article_catalog_select, container, false);
        mCatalogToolbar = (Toolbar) view.findViewById(R.id.Article_catalog_Toolbar);
        mCatalogRecycler = (RecyclerView) view.findViewById(R.id.Article_catalog_Recycler);
        mCatalogRefresh = (SwipeRefreshLayout) view.findViewById(R.id.Article_catalog_refresh);
        mCatalogAddButton=(FloatingActionButton)view.findViewById(R.id.Article_catalog_add);
        mCatalogToolbar.setTitle(R.string.article_catalog_toolbar);
        mCatalogToolbar.setTitleTextColor(Color.WHITE);

        mCatalogRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mCatalogRefresh.setRefreshing(true);
                LoadCatalogs();

                mCatalogRefresh.setRefreshing(false);
            }
        });

        mCatalogAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity=(MainActivity)getActivity();
                FragmentManager fragmentManager=mainActivity.getSupportFragmentManager();
                FragmentTransaction transaction=fragmentManager.beginTransaction();

                AddCatalogFragment catalogFragment=new AddCatalogFragment();
                transaction.replace(R.id.main_frameLayout,catalogFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        LoadCatalogs();
        return view;
    }


    private void LoadCatalogs() {

        String address = "Article/GetCatalog";
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {

                    mCatalogs = new Gson().fromJson(response.body().string(), new TypeToken<List<ArticleCatalog>>() {
                    }.getType());

                    new LoadCatalogTask().execute();
                } else {


                }
            }
        });

    }

    class LoadCatalogTask extends AsyncTask<Void, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
//            super.onPostExecute(aBoolean);
            MainActivity mainActivity = (MainActivity) getActivity();
            mCatalogRecycler.setLayoutManager(new LinearLayoutManager(mainActivity));


            mCatalogRecycler.setAdapter(new ArticleCatalogsAdapter(mCatalogs, mainActivity));

        }
    }
}
