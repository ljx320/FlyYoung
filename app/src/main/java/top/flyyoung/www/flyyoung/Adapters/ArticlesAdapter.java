package top.flyyoung.www.flyyoung.Adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import java.util.List;

import top.flyyoung.www.flyyoung.Datas.Article;
import top.flyyoung.www.flyyoung.Fragments.MyArticleInfoFragment;
import top.flyyoung.www.flyyoung.MainActivity;
import top.flyyoung.www.flyyoung.R;
import top.flyyoung.www.flyyoung.Utils.HttpUtil;

/**
 * Created by 69133 on 2017/1/13.
 */

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {

    private List<Article> mArticles;
    private Activity mContext;



    public  ArticlesAdapter(List<Article> articles,Activity context){
        mArticles=articles;
        mContext=context;




    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        private ImageView ArticlesListImage;
        private TextView ArticlesListTitle;
        private TextView ArticlesListDescription;
        private TextView ArticlesListCatalog;
        private TextView ArticlesListDate;
        private CardView ArticlesListContainer;

        public ViewHolder(View view) {
            super(view);

            ArticlesListImage=(ImageView) view.findViewById(R.id.article_list_image);
            ArticlesListTitle=(TextView)view.findViewById(R.id.article_list_title);
            ArticlesListDescription=(TextView)view.findViewById(R.id.article_list_description);
            ArticlesListCatalog=(TextView)view.findViewById(R.id.article_list_catalog);
            ArticlesListDate=(TextView)view.findViewById(R.id.article_list_date);
            ArticlesListContainer=(CardView)view.findViewById(R.id.article_list_Container);
        }

    }


    @Override
    public void onBindViewHolder(ArticlesAdapter.ViewHolder holder, int position) {
        //super.onBindViewHolder(holder, position);

        Article article=mArticles.get(position);
        holder.ArticlesListCatalog.setText(article.getArticleCatalog().toString());
        holder.ArticlesListDate.setText(article.getCreateDate().toString()+" "+article.getCreateTime());
        holder.ArticlesListDescription.setText(article.getArticleDescription().toString());
        holder.ArticlesListTitle.setText(article.getArticleTitle().toString());
        Glide.with(mContext).load(article.getArticleImage()).into(holder.ArticlesListImage);
    }

    @Override
    public ArticlesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.article_list_item,parent,false);



        final ViewHolder holder=new ViewHolder(view);

        holder.ArticlesListContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                Article article=mArticles.get(position);

                MyArticleInfoFragment fm=new MyArticleInfoFragment();

                Bundle bundle=new Bundle();
                int articleID=article.getID();
                bundle.putInt("articleID",articleID);
                fm.setArguments(bundle);


                MainActivity mainActivity=(MainActivity)mContext;
                FragmentManager fragmentManager=mainActivity.getSupportFragmentManager();
                FragmentTransaction transaction=fragmentManager.beginTransaction();
                transaction.replace(R.id.main_frameLayout,fm);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        return  holder;
    }



    @Override
    public int getItemCount() {
        return mArticles.size();
    }
}
