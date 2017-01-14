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
 * Created by 69133 on 2017/1/13.
 */

public class PhotosAdapter  extends RecyclerView.Adapter<PhotosAdapter.ViewHolder> {


    private List<Photo> mPhotos;
    private Activity mActivity;

    public class ViewHolder extends  RecyclerView.ViewHolder{
        ImageView PhotImageView;
        CardView PhotoCardView;
        public ViewHolder(View view){

            super(view);

            PhotImageView=(ImageView) view.findViewById(R.id.photo_list_image);
            PhotoCardView=(CardView)view.findViewById(R.id.photo_list_cardView);

        }


    }

    public  PhotosAdapter(List<Photo> photos,Activity activity){

        mPhotos=photos;
        mActivity=activity;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        super.onBindViewHolder(holder, position, payloads);

        Photo photo=mPhotos.get(position);
        Glide.with(mActivity).load(HttpUtil.WEBHOST+ photo.getPhotoUrl().toString()).into(holder.PhotImageView);

    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_list_item,parent,false);
        final ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.PhotoCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(parent.getContext(),"查看详情",Toast.LENGTH_SHORT).show();

                ArrayList<String> photoUrlList=new ArrayList<String>();

                for (Photo photo:mPhotos){

                    photoUrlList.add(photo.getPhotoDescription().toString());
                }

                Bundle bundle=new Bundle();
                bundle.putStringArrayList("photoUrlList",photoUrlList);
                bundle.putInt("nowposition",viewHolder.getAdapterPosition());
                PhotoLookFragment photoLookFragment=new PhotoLookFragment();
                photoLookFragment.setArguments(bundle);


                MainActivity mainActivity=(MainActivity)mActivity;
                FragmentManager  fragmentManager=mainActivity.getSupportFragmentManager();
                FragmentTransaction transaction=fragmentManager.beginTransaction();

                transaction.replace(R.id.main_frameLayout,photoLookFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        return  viewHolder;

    }

    @Override
    public int getItemCount() {
        return mPhotos.size();
    }
}
