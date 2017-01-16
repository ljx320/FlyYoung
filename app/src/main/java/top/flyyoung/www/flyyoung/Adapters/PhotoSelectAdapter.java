package top.flyyoung.www.flyyoung.Adapters;

import android.app.Activity;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import top.flyyoung.www.flyyoung.Datas.Photo;
import top.flyyoung.www.flyyoung.Fragments.PhotoLookFragment;
import top.flyyoung.www.flyyoung.MainActivity;
import top.flyyoung.www.flyyoung.R;

/**
 * Created by 69133 on 2017/1/16.
 */

public class PhotoSelectAdapter extends RecyclerView.Adapter<PhotoSelectAdapter.ViewHolder> {

    private List<String> mPhotos;
    public ArrayList<String> mSelectedPhotos;
    private MainActivity mainActivity;


    public class  ViewHolder extends RecyclerView.ViewHolder{

         ImageView mImageView;
         CheckBox mCheckbox;
        public ViewHolder(View view){
            super(view);
            mImageView=(ImageView)view.findViewById(R.id.photoSelect_image);
            mCheckbox=(CheckBox)view.findViewById(R.id.photoSlect_checkBox);

        }

    }

    public  PhotoSelectAdapter(MainActivity activity,List<String> photos){
        mainActivity=activity;
        mPhotos=photos;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
      //  super.onBindViewHolder(holder, position, payloads);

        String imageURL=mPhotos.get(position);
        Glide.with(mainActivity).load(imageURL).into(holder.mImageView);
    }


    @Override
    public PhotoSelectAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
    View view=LayoutInflater.from(mainActivity).inflate(R.layout.photo_select_album,parent,false);

        final ViewHolder viewHolder=new ViewHolder(view);
        mSelectedPhotos=new ArrayList<String>();
        viewHolder.mCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.mCheckbox.isChecked()){

                    mSelectedPhotos.add(mPhotos.get(viewHolder.getAdapterPosition()));
                }
                else {

                    mSelectedPhotos.remove(mPhotos.get(viewHolder.getAdapterPosition()));
                }
            }
        });
        return  viewHolder;

    }

    @Override
    public int getItemCount() {
        return mPhotos.size();
    }

    public ArrayList<String> getSelectedPhots(){

        return mSelectedPhotos;
    }
}
