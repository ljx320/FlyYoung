package top.flyyoung.www.flyyoung.Adapters;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import top.flyyoung.www.flyyoung.Datas.Article;
import top.flyyoung.www.flyyoung.Datas.ArticleCatalog;
import top.flyyoung.www.flyyoung.Fragments.AddArticleFragment;
import top.flyyoung.www.flyyoung.Fragments.MyArticleInfoFragment;
import top.flyyoung.www.flyyoung.MainActivity;
import top.flyyoung.www.flyyoung.R;

/**
 * Created by 69133 on 2017/1/16.
 */

public class ArticleCatalogsAdapter extends RecyclerView.Adapter<ArticleCatalogsAdapter.myViewHolder> {

    private List<ArticleCatalog> mCatalogs;
private  MainActivity mainActivity;
    public  class  myViewHolder extends  RecyclerView.ViewHolder{


        CardView mCatalogCardView;
        TextView mCatalogName;

        public  myViewHolder(View view){

            super(view);
            mCatalogCardView=(CardView)view.findViewById(R.id.article_catalog_Cardview);
            mCatalogName=(TextView)view.findViewById(R.id.article_catalog_Catalogname);


        }

    }


    public  ArticleCatalogsAdapter(List<ArticleCatalog> catalogs,MainActivity activity){
        mCatalogs=catalogs;
        mainActivity=activity;

    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
       // super.onBindViewHolder(holder, position);

        ArticleCatalog catalog=mCatalogs.get(position);

        holder.mCatalogName.setText(catalog.getCatalogName());



    }

    @Override
    public ArticleCatalogsAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.article_catalog_item,parent,false);

        final myViewHolder viewHolder=new myViewHolder(view);

        viewHolder.mCatalogCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArticleCatalog catalog=mCatalogs.get(viewHolder.getAdapterPosition());

               // Toast.makeText(mainActivity,catalog.getCatalogName(),Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager=mainActivity.getSupportFragmentManager();
                FragmentTransaction transaction=fragmentManager.beginTransaction();

                AddArticleFragment fragment=new AddArticleFragment();
                Bundle bundle=new Bundle();
                bundle.putString("catalogName",catalog.getCatalogName());
                bundle.putInt("catalogID",catalog.getID());
                fragment.setArguments(bundle);
                transaction.replace(R.id.main_frameLayout,fragment);

                transaction.commit();


            }
        });
        return  viewHolder;
    }



    @Override
    public int getItemCount() {
        return mCatalogs.size();
    }

}
