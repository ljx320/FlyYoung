package top.flyyoung.www.flyyoung.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import top.flyyoung.www.flyyoung.R;

/**
 * Created by 69133 on 2017/1/16.
 */

public class AddArticleFragment extends Fragment{

    private Toolbar mArticleToolber;

    private String mCatalogName;
    private int mCatalogID;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.add_article,container,false);
        mArticleToolber=(Toolbar) view.findViewById(R.id.addarticle_Toolbar);

        Bundle bundle=getArguments();
        if (bundle!=null){

            mCatalogName=bundle.getString("catalogName");
            mCatalogID=bundle.getInt("catalogID");

            mArticleToolber.setTitle(mCatalogName);
            mArticleToolber.setTitleTextColor(Color.WHITE);

        }



        return  view;
    }
}
