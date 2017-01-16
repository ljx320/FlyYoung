package top.flyyoung.www.flyyoung.Adapters;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.flyyoung.www.flyyoung.Datas.Download;
import top.flyyoung.www.flyyoung.MainActivity;
import top.flyyoung.www.flyyoung.R;
import top.flyyoung.www.flyyoung.Utils.HttpUtil;

/**
 * Created by 69133 on 2017/1/13.
 */

public class DownloadsAdapter extends RecyclerView.Adapter<DownloadsAdapter.ViewHolder> {
    private String SDCardRoot;
    private  List<Download> mDownloads;

    private MainActivity mainActivity;

    public class  ViewHolder extends  RecyclerView.ViewHolder{

        TextView downloadTitle;
        TextView downloadSize;
        TextView downloadDescription;
        TextView downloadDate;
        Button downloadButton;
        public ViewHolder(View view){

            super(view);

             downloadTitle=(TextView)view.findViewById(R.id.downloads_list_Title) ;
             downloadSize=(TextView)view.findViewById(R.id.downloads_list_length) ;
             downloadDescription=(TextView)view.findViewById(R.id.downloads_list_description) ;
             downloadDate=(TextView)view.findViewById(R.id.downloads_list_Date) ;
            downloadButton=(Button)view.findViewById(R.id.downloads_downloadButton);
        }

    }

    public DownloadsAdapter(List<Download> downloads, MainActivity activity){
        mDownloads=downloads;
        mainActivity=activity;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.download_list_item,parent,false);

        final ViewHolder viewHolder=new ViewHolder(view);

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String fileName=simpleDateFormat.format(new Date())+".rar";

        SDCardRoot =mainActivity.getApplicationContext().getFilesDir().getAbsolutePath()+ File.separator+fileName;
        viewHolder.downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Download myDownload=mDownloads.get(viewHolder.getAdapterPosition());
                String Address="Download/Get?fileName="+myDownload.getFilePath()+"";
                HttpUtil.sendOkHttpRequest(Address, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()){

                            InputStream inputStream=response.body().byteStream();
                            FileOutputStream fileOutputStream=null;

                            try{

                                fileOutputStream=new FileOutputStream(SDCardRoot);
                                byte[] buffer=new byte[2084];

                                int len=0;
                                while ((len=inputStream.read(buffer))!=-1){
                                    fileOutputStream.write(buffer,0,len);

                                }
                                fileOutputStream.flush();

                                new DownloadFileTask().execute();

                            }
                            catch (IOException e){

                                e.printStackTrace();
                            }

                        }
                        else
                        {


                        }
                    }
                });

            }
        });

        return  viewHolder;
    }

    class  DownloadFileTask extends AsyncTask<Void,Integer,Boolean>{

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            //super.onPostExecute(aBoolean);

            Toast.makeText(mainActivity,R.string.download_downloadSucces,Toast.LENGTH_SHORT).show();
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            return null;
        }
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
