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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

import top.flyyoung.www.flyyoung.Datas.Album;
import top.flyyoung.www.flyyoung.Fragments.MyPhotosFragment;
import top.flyyoung.www.flyyoung.MainActivity;
import top.flyyoung.www.flyyoung.R;
import top.flyyoung.www.flyyoung.Utils.HttpUtil;

/**
 * Created by 69133 on 2017/1/13.
 */

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.ViewHolder> {

    private List<Album> mAlbums;
    private Activity mActivity;

    public  class  ViewHolder extends  RecyclerView.ViewHolder{

        ImageView albumShowImage;
        TextView albumShowName;
        TextView albumShowCount;
        CardView albumCardView;

        public ViewHolder(View view)
        {
            super(view);
            albumShowImage=(ImageView) view.findViewById(R.id.album_list_ShowImage);
            albumShowName=(TextView)view.findViewById(R.id.album_list_Name);
            albumShowCount=(TextView)view.findViewById(R.id.album_list_Count);
            albumCardView=(CardView)view.findViewById(R.id.album_cardView);
        }

    }

    public AlbumsAdapter(List<Album> albumList,Activity activity){
        mAlbums=albumList;
        mActivity=activity;

    }
    @Override
    public void onBindViewHolder(AlbumsAdapter.ViewHolder holder, int position) {
       // super.onBindViewHolder(holder, position);

        Album album=mAlbums.get(position);

        holder.albumShowCount.setText(Integer.toString(album.getPhotosCount())+"张照片" );
        holder.albumShowName.setText(album.getAlbumName());

        Glide.with(mActivity).load(HttpUtil.WEBHOST+album.getFirstShow()).into(holder.albumShowImage);

    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.album_list_item,parent,false);

        final ViewHolder viewHolder=new ViewHolder(view);

        viewHolder.albumCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(parent.getContext(),"相册详情",Toast.LENGTH_SHORT).show();

                Album album=mAlbums.get(viewHolder.getAdapterPosition());


                MyPhotosFragment PhotoFragement=new MyPhotosFragment();
                Bundle bundle=new Bundle();
                bundle.putString("albumName",album.getAlbumName());
                bundle.putInt("albumID",album.getID());
                PhotoFragement.setArguments(bundle);

                MainActivity mainActivity=(MainActivity) mActivity;
                FragmentManager fragmentManager=mainActivity.getSupportFragmentManager();
                FragmentTransaction transaction=fragmentManager.beginTransaction();

                transaction.replace(R.id.main_frameLayout,PhotoFragement);
                transaction.addToBackStack(null);
                transaction.commit();


            }
        });

        return viewHolder;

    }

    @Override
    public int getItemCount() {
        return mAlbums.size();
    }
}
