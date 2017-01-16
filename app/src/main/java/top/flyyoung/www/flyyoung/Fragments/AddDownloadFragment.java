package top.flyyoung.www.flyyoung.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.flyyoung.www.flyyoung.Datas.Download;
import top.flyyoung.www.flyyoung.Datas.UploadFileResult;
import top.flyyoung.www.flyyoung.R;
import top.flyyoung.www.flyyoung.Utils.HttpUtil;

/**
 * Created by 69133 on 2017/1/16.
 */

public class AddDownloadFragment extends Fragment {

private EditText mDownloadName;
    private  EditText mDwonloadVersion;
    private  EditText mDownloadDescription;
    private Button mDownloadFile;
    private CheckBox mDownloadShow;
    private Button mDwonloadSure;
    private Toolbar mDwonloadToolbar;
    private TextView mDwonloadFileResult;

    private Boolean mMakeFileResult;

    protected   static final  int FILE_SELECT_CODE=0;

    private  List<UploadFileResult> mUploadFileResult;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.add_download,container,false);


        mDownloadName=(EditText)view.findViewById(R.id.addDownload_name);
        mDwonloadVersion=(EditText)view.findViewById(R.id.addDownload_version);
        mDownloadDescription=(EditText)view.findViewById(R.id.addDownload_description);
        mDownloadFile=(Button)view.findViewById(R.id.addDownload_file);
        mDownloadShow=(CheckBox)view.findViewById(R.id.addDownload_showCustomer);
        mDwonloadSure=(Button)view.findViewById(R.id.addDownload_makesure);
        mDwonloadToolbar=(Toolbar)view.findViewById(R.id.addDownload_toolbar);
mDwonloadFileResult=(TextView)view.findViewById(R.id.addDownload_fileresult);


        mDwonloadToolbar.setTitle(R.string.download_Toolbar);
        mDwonloadToolbar.setTitleTextColor(Color.WHITE);

        mDownloadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                try{

                startActivityForResult(intent,FILE_SELECT_CODE);


                }catch (android.content.ActivityNotFoundException ex){
                    ex.printStackTrace();

                }
            }
        });

        mDwonloadSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String DownloadName=mDownloadName.getText().toString();
                String DownloadVersion=mDwonloadVersion.getText().toString();
                String DownloadDescription=mDownloadDescription.getText().toString();
                String DownloadFile=mDwonloadFileResult.getText().toString();

                Boolean DownloadShow=true;
                if (!mDownloadShow.isChecked()){
                    DownloadShow=false;

                }

                if ("".equals(DownloadName) )
                {
                    Toast.makeText(getActivity(),R.string.download_NameWarning,Toast.LENGTH_SHORT).show();
                    return;

                }

                if ("".equals(mDownloadDescription)){

                    Toast.makeText(getActivity(),R.string.download_DescriptionWarning,Toast.LENGTH_SHORT).show();
                    return;
                }

                if ("".equals(DownloadFile)){
                    Toast.makeText(getActivity(),R.string.download_FileWarning,Toast.LENGTH_SHORT).show();
                    return;

                }
                String address="Download/PostFile";
                HttpUtil.uploadFile(address,DownloadFile , new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        if (response.isSuccessful()){

                            mUploadFileResult=new Gson().fromJson(response.body().string(),new TypeToken<List<UploadFileResult>>(){}.getType());

                            new UploadFileTask().execute();
                        }
                        else
                        {


                        }
                    }
                });


            }
        });

        return  view;

    }


    class  UploadFileTask extends AsyncTask<Void,Integer,Boolean>{

        @Override
        protected Boolean doInBackground(Void... params) {
            return null;
        }
        @Override
        protected void onPostExecute(Boolean aBoolean) {
           // super.onPostExecute(aBoolean);

            if (mUploadFileResult.size()>0){

               for (UploadFileResult result:mUploadFileResult){

                   PublishDownload(result.getName(),Integer.toString(result.getSize()) );
               }

            }
        }
    }



    private  void PublishDownload(String ServicePath,String fileSize)
    {
        String DownloadName=mDownloadName.getText().toString();
        String DownloadVersion=mDwonloadVersion.getText().toString();
        String DownloadDescription=mDownloadDescription.getText().toString();
        String DownloadFile=mDwonloadFileResult.getText().toString();

        Boolean DownloadShow=true;
        if (!mDownloadShow.isChecked()){
            DownloadShow=false;

        }

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("HH:mm");

        Download newDownload=new Download();
        newDownload.setID(0);
        newDownload.setCreateDate(simpleDateFormat.format(new Date()));
        newDownload.setCreateTime(simpleDateFormat1.format(new Date()));
        newDownload.setFileDescription(DownloadDescription);
        newDownload.setFileName(DownloadName);
        newDownload.setFilePath(ServicePath);
        newDownload.setFileVersion(DownloadVersion);
        newDownload.setShowCustomer(DownloadShow);
        newDownload.setUseDescription(fileSize);
        String json=new Gson().toJson(newDownload,Download.class);

        String address="Download/Post";
        HttpUtil.PostJson(address, json, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful())
                    {

                        mMakeFileResult=new Gson().fromJson(response.body().string(),Boolean.class);

                        new MakeFileTask().execute();


                    }
                else
                    {


                    }
            }
        });



    }

    class  MakeFileTask extends AsyncTask<Void,Integer,Boolean>{
        @Override
        protected Boolean doInBackground(Void... params) {
            return null;
        }
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            // super.onPostExecute(aBoolean);

           if (mMakeFileResult)
           {
               Toast.makeText(getActivity(),R.string.download_MakeFileSuccess,Toast.LENGTH_SHORT).show();

               mDownloadName.setText("");
              mDwonloadVersion.setText("");
               mDownloadDescription.setText("");
               mDwonloadFileResult.setText("");


           }
            else
           {
               Toast.makeText(getActivity(),R.string.download_MakeFileFailed,Toast.LENGTH_SHORT).show();

           }
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){

            case  FILE_SELECT_CODE:
if (resultCode==-1){
//    String resultCodesVal=Integer.toString(resultCode);
   // Toast.makeText(getContext(),resultCodesVal,Toast.LENGTH_SHORT).show();

  String result=data.getData().getPath();

    mDwonloadFileResult.setText(result);
    break;

}


        }

        super.onActivityResult(requestCode,resultCode,data);
    }
}
