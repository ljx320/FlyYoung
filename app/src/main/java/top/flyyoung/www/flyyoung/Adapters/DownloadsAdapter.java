package top.flyyoung.www.flyyoung.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import top.flyyoung.www.flyyoung.Datas.Download;
import top.flyyoung.www.flyyoung.R;

/**
 * Created by 69133 on 2017/1/13.
 */

public class DownloadsAdapter extends RecyclerView.Adapter<DownloadsAdapter.ViewHolder> {

    private  List<Download> mDownloads;

    public class  ViewHolder extends  RecyclerView.ViewHolder{

        TextView downloadTitle;
        TextView downloadSize;
        TextView downloadDescription;
        TextView downloadDate;
        public ViewHolder(View view){

            super(view);

             downloadTitle=(TextView)view.findViewById(R.id.downloads_list_Title) ;
             downloadSize=(TextView)view.findViewById(R.id.downloads_list_length) ;
             downloadDescription=(TextView)view.findViewById(R.id.downloads_list_description) ;
             downloadDate=(TextView)view.findViewById(R.id.downloads_list_Date) ;

        }

    }

    public DownloadsAdapter(List<Download> downloads){
        mDownloads=downloads;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.download_list_item,parent,false);

        ViewHolder viewHolder=new ViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Download download=mDownloads.get(position);
        holder.downloadDate.setText(download.getCreateDate().toString()+" "+download.getCreateTime());
        holder.downloadDescription.setText(download.getFileDescription().toString());
        holder.downloadSize.setText(download.getUseDescription().toString()+"kb");
        holder.downloadTitle.setText(download.getFileName().toString());
    }

    @Override
    public int getItemCount() {
        return mDownloads.size();
    }
}
