package top.flyyoung.www.flyyoung.Adapters;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import top.flyyoung.www.flyyoung.Datas.Photo;
import top.flyyoung.www.flyyoung.Fragments.PhotoLookFragment;
import top.flyyoung.www.flyyoung.MainActivity;
import top.flyyoung.www.flyyoung.R;
import top.flyyoung.www.flyyoung.Utils.HttpUtil;

/**
 * Created by 69133 on 2017/1/16.
 */

public class PhotosItemsAddAdapter extends RecyclerView.Adapter<PhotosItemsAddAdapter.ViewHolder> {

    private List<String> mPhotos;
    private Activity mActivity;

    public class ViewHolder extends  RecyclerView.ViewHolder{
        ImageView PhotImageView;
        CardView PhotoCardView;
        public ViewHolder(View view){

            super(view);

            PhotImageView=(ImageView) view.findViewById(R.id.addPhoto_item_Image);
            PhotoCardView=(CardView)view.findViewById(R.id.addPhoto_item_Cardview);

        }


    }

    public  PhotosItemsAddAdapter(List<String> photos,Activity activity){

        mPhotos=photos;
        mActivity=activity;

    }

    @Override
    public void onBindViewHolder(PhotosItemsAddAdapter.ViewHolder holder, int position) {
//        super.onBindViewHolder(holder, position, payloads);

        String photo=mPhotos.get(position);
        Glide.with(mActivity).load(photo).into(holder.PhotImageView);

    }

    @Override
    public PhotosItemsAddAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.add_photo_item,parent,false);
        final PhotosItemsAddAdapter.ViewHolder viewHolder=new PhotosItemsAddAdapter.ViewHolder(view);
        viewHolder.PhotoCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(parent.getContext(),"查看详情",Toast.LENGTH_SHORT).show();

                Toast.makeText(parent.getContext(),"666",Toast.LENGTH_SHORT).show();



            }
        });

        return  viewHolder;

    }

    @Override
    public int getItemCount() {
        return mPhotos.size();
    }




}
