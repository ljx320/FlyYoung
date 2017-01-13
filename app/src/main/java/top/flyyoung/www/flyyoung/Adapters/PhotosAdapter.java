package top.flyyoung.www.flyyoung.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import top.flyyoung.www.flyyoung.Datas.Photo;
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
        public ViewHolder(View view){

            super(view);

            PhotImageView=(ImageView) view.findViewById(R.id.photo_list_image);

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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_list_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);

        return  viewHolder;

    }

    @Override
    public int getItemCount() {
        return mPhotos.size();
    }
}
