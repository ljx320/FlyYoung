package top.flyyoung.www.flyyoung.Fragments;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.flyyoung.www.flyyoung.Datas.Album;
import top.flyyoung.www.flyyoung.MainActivity;
import top.flyyoung.www.flyyoung.R;
import top.flyyoung.www.flyyoung.Utils.HttpUtil;

/**
 * Created by 69133 on 2017/1/16.
 */

public class AddAlbumFragment extends Fragment {

    private List<String> list=new ArrayList<String>();

    private Toolbar mToolbar;
    private ArrayAdapter<String> adapter;
    private Spinner mSpinner;
    private MainActivity mActicvtity;

    private int mSelectedSubject=1;

    private EditText mAlbumName;
    private EditText mAlbumDescription;
    private CheckBox mAlbumShowCustomer;
    private Button mAlbumAddButton;

 private    Boolean mRuselt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.add_album,container,false);
        mSpinner=(Spinner)view.findViewById(R.id.AddAlbum_Spinner);
        mToolbar=(Toolbar)view.findViewById(R.id.AddAlbum_Toolbar);

        mAlbumName=(EditText)view.findViewById(R.id.AddAlbum_AlbumName);
        mAlbumDescription=(EditText)view.findViewById(R.id.AddAlbum_Description);
        mAlbumShowCustomer=(CheckBox)view.findViewById(R.id.AddAlbum_ShowCustomer);
        mAlbumAddButton=(Button)view.findViewById(R.id.AddAlbum_addButton);

        mAlbumAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String AlbumName=mAlbumName.getText().toString();

                if ("".equals(AlbumName)){
                    Toast.makeText(mActicvtity,R.string.Album_AlbumNameWaring,Toast.LENGTH_SHORT).show();
                    return;
                }

                Album album=new Album();
                album.setAlbumDescription(mAlbumDescription.getText().toString());
                album.setAlbumName(AlbumName);
                album.setAlbumSuject(mSelectedSubject);
                album.setFirstShow("");
                album.setFlags(1);
                album.setPhotosCount(0);
                album.setID(0);
                if (mAlbumShowCustomer.isChecked()){
                    album.setShowCustomer(true);

                }
                else
                {
                    album.setShowCustomer(false);
                }

                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat simpleDateFormat2=new SimpleDateFormat("HH:mm");
                album.setCreateDate(simpleDateFormat.format(new Date()));
                album.setCreateTime(simpleDateFormat2.format(new Date()));

                String address="Album/Post";
                String json=new Gson().toJson(album,Album.class);
                HttpUtil.PostJson(address, json, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()){

                        mRuselt=new Gson().fromJson(response.body().string(),Boolean.class);

                        new AddAlbumTask().execute();
                    }
                        else
                    {


                    }
                    }
                });

            }
        });

        mActicvtity=(MainActivity)getActivity();

        mToolbar.setTitle(R.string.Album_AddButton);
        mToolbar.setTitleTextColor(Color.WHITE);

        list.add("最爱");
        list.add("人物");
        list.add("风景");
        list.add("动物");
        list.add("游记");
        list.add("卡通");
        list.add("生活");
        list.add("其他");
        adapter=new ArrayAdapter<String>(mActicvtity,android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectedSubject=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return  view;
    }

    class  AddAlbumTask extends AsyncTask<Void,Integer,Boolean>{

        @Override
        protected Boolean doInBackground(Void... params) {
            return true;
        }
        @Override
        protected void onPostExecute(Boolean b) {
         //   super.onPostExecute(b);
if (mRuselt)
{
    Toast.makeText(mActicvtity,R.string.Album_succes,Toast.LENGTH_SHORT).show();
    mAlbumName.setText("");
    mAlbumDescription.setText("");

}
            else {

    Toast.makeText(mActicvtity,R.string.Album_failed,Toast.LENGTH_SHORT).show();
}

        }
    }
}
