package top.flyyoung.www.flyyoung.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.w3c.dom.Text;

import java.util.List;

import top.flyyoung.www.flyyoung.Datas.Blobs;
import top.flyyoung.www.flyyoung.R;
import top.flyyoung.www.flyyoung.Utils.HttpUtil;


/**
 * Created by 69133 on 2017/1/12.
 */

public class BlobsAdapter extends RecyclerView.Adapter<BlobsAdapter.ViewHolder> {

    private List<Blobs> mBlobs;
    private  Context mContext;

    public BlobsAdapter(List<Blobs> blobses, Context context) {

        mBlobs = blobses;
        mContext=context;
    }

    @Override
    public BlobsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.blob_list_items,parent,false);
        ViewHolder holder=new ViewHolder(view);

        return  holder;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //super.onBindViewHolder(holder, position);

        Blobs blob=mBlobs.get(position);

        if (blob.getBlobLocation().isEmpty()){

            holder.blobLocationContainer.setVisibility(View.GONE);
        }
        holder.blobLocation.setText(blob.getBlobLocation().toString());
        holder.blobContent.setText(blob.getBlobContent().toString());
        holder.blobCreateDate.setText(blob.getCreateDate().toString()+" "+blob.getCreateTime().toString());



        Glide.with(mContext).load(HttpUtil.WEBHOST+blob.getBlobImage()).animate(android.R.anim.slide_in_left).thumbnail(0.1f).into(holder.blobShowImage);

        Glide.with(mContext).load(blob.getWeather()).into(holder.blobWeatherImage);

    }



    @Override
    public int getItemCount() {
        return mBlobs.size();
    }



    public  class  ViewHolder extends RecyclerView.ViewHolder{
        View blobItemView;
        ImageView blobShowImage;
        ImageView blobWeatherImage;
        TextView blobCreateDate;
        TextView blobContent;
        TextView blobLocation;
        LinearLayout blobLocationContainer;

        public ViewHolder(View view){
            super(view);
            blobItemView=view;
            blobShowImage=(ImageView)view.findViewById(R.id.bloblist_item_showImage);
            blobWeatherImage=(ImageView)view.findViewById(R.id.bloblist_item_weather);
            blobCreateDate=(TextView)view.findViewById(R.id.bloblist_item_date);
            blobContent=(TextView)view.findViewById(R.id.bloblist_item_blobContent);
            blobLocation=(TextView)view.findViewById(R.id.bloblist_Item_location);
            blobLocationContainer=(LinearLayout)view.findViewById(R.id.bloblist_LocationContainer);

        }

    }
}
