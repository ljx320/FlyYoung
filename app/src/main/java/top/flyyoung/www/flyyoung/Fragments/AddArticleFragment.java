package top.flyyoung.www.flyyoung.Fragments;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jp.wasabeef.richeditor.RichEditor;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.flyyoung.www.flyyoung.Datas.Article;
import top.flyyoung.www.flyyoung.Datas.UploadFileResult;
import top.flyyoung.www.flyyoung.MainActivity;
import top.flyyoung.www.flyyoung.R;
import top.flyyoung.www.flyyoung.Utils.BitmapUtil;
import top.flyyoung.www.flyyoung.Utils.HttpUtil;

/**
 * Created by 69133 on 2017/1/16.
 */

public class AddArticleFragment extends Fragment{

    private Toolbar mArticleToolber;

    private String mCatalogName;
    private int mCatalogID;
    private Uri imageUri;
    private RichEditor mArticleContent;

    private ImageView mActionBold;
    private ImageView mActionStrikeThrough;
    private ImageView mActionUnderline;
    private ImageView mActionBgColor;
    private ImageView mAciontColor;
    private ImageView mActionBlockquote;
    private ImageView mActionLink;
    private ImageView mActionPicture;


    private  PopupWindow mpopupView;
    private MainActivity mainActivity;

    private final  int TAKE_PHOTO=20;
    private final int CHOOSE_PHOTO=21;
    private final int CHOOSE_PHOTO2=22;
    private final  int RESUL_OK=-1;

    private final  int COLOR_RED=1;
    protected final int COLOR_YEELOW=2;
    private  final int COLOR_BLUE=3;
    private final int COLOR_GREEN=4;
    private  final  int COLOR_PICK=5;


    private Button mArticleImageButton;
    private ImageView mArticleImageShow;
    private TextView mArticleImageURL;

    private EditText mArticleTitle;
    private EditText mAritcleDescription;
    private EditText mArticleKeyWords;
    private CheckBox mArticleShowCustomer;


    private List<UploadFileResult> mPostResult;
    private  String mArticleImage;
    private Boolean mArticleResult;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.menu_addArticle_publish:

                PostImae();
                break;

        }
        return  true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_addarticle,menu);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.add_article,container,false);
        mArticleToolber=(Toolbar) view.findViewById(R.id.addarticle_Toolbar);
        mainActivity=(MainActivity)getActivity();
        mArticleContent=(RichEditor)view.findViewById(R.id.addarticle_Content);
        mArticleContent.setEditorFontSize(16);
      //  mArticleContent.setEditorFontColor(Color.BLACK);
        mArticleContent.setEditorBackgroundColor(Color.WHITE);
        mArticleContent.setPadding(10,10,10,10);
        mArticleContent.setPlaceholder("输入文章内容...");

         mActionBold=(ImageView)view.findViewById(R.id.article_action_bold);
          mActionStrikeThrough=(ImageView)view.findViewById(R.id.article_action_strikethrough);
          mActionUnderline=(ImageView)view.findViewById(R.id.article_action_underline);
          mActionBgColor=(ImageView)view.findViewById(R.id.article_action_bgcolor);
          mAciontColor=(ImageView)view.findViewById(R.id.article_action_color);
          mActionBlockquote=(ImageView)view.findViewById(R.id.article_action_blockquote);
          mActionLink=(ImageView)view.findViewById(R.id.article_action_link);
          mActionPicture=(ImageView)view.findViewById(R.id.article_action_picture);

        mArticleImageButton=(Button)view.findViewById(R.id.addArticle_ShowImage);
        mArticleImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlbum2();
            }
        });
        mArticleImageShow=(ImageView)view.findViewById(R.id.addArticle_ShowImageView);
        mArticleImageURL=(TextView)view.findViewById(R.id.addArticle_ShowImageURL);
          mArticleTitle=(EditText)view.findViewById(R.id.addArticle_Title);
          mAritcleDescription=(EditText)view.findViewById(R.id.addArticle_Description);
          mArticleKeyWords=(EditText)view.findViewById(R.id.addArticle_KeyWords);
          mArticleShowCustomer=(CheckBox)view.findViewById(R.id.addArticle_ShowCustomer);

        mActionBold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mArticleContent.setBold();
                Toast.makeText(mainActivity,"已设置为粗体",Toast.LENGTH_SHORT).show();
            }
        });
        mActionStrikeThrough.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArticleContent.setStrikeThrough();
                Toast.makeText(mainActivity,"已设置为横向穿过",Toast.LENGTH_SHORT).show();
            }
        });

        mActionUnderline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArticleContent.setUnderline();
                Toast.makeText(mainActivity,"已设置为下划线",Toast.LENGTH_SHORT).show();
            }
        });

        mActionBgColor.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
//
                initPopupWindowBgColor();
            }
        });

        mAciontColor.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                initPopupWindowTextColor();
            }
        });
        mActionPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                initPopupWindowPicture();
               // mArticleContent.insertImage("","");
            }
        });

        mActionLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                initPopupWindow();


            }
        });

        mActionBlockquote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArticleContent.setBlockquote();
                Toast.makeText(mainActivity,"已设置引用段落",Toast.LENGTH_SHORT).show();
            }
        });


        Bundle bundle=getArguments();
        if (bundle!=null){

            mCatalogName=bundle.getString("catalogName");
            mCatalogID=bundle.getInt("catalogID");

            mArticleToolber.setTitle(mCatalogName);
            mArticleToolber.setTitleTextColor(Color.WHITE);

        }

         mainActivity.setSupportActionBar(mArticleToolber);
        setHasOptionsMenu(true);



        return  view;
    }


private  void CheckInfos(){

    String picturePath=mArticleImageURL.getText().toString();
    if ("".equals(picturePath)){

        Toast.makeText(mainActivity,"展示照片一定要选择哦",Toast.LENGTH_SHORT).show();

        return;
    }

    String articleTilte=mArticleTitle.getText().toString();
    if ("".equals(articleTilte)){
        Toast.makeText(mainActivity,"文章标题不能缺少哦",Toast.LENGTH_SHORT).show();

        return;

    }
    String articleDescription=mAritcleDescription.getText().toString();
    if ("".equals(articleDescription)){
        Toast.makeText(mainActivity,"简单的介绍一下内容嘛",Toast.LENGTH_SHORT).show();

        return;

    }

    String articleKeyWords=mArticleKeyWords.getText().toString();
    if ("".equals(articleKeyWords)){
        Toast.makeText(mainActivity,"关键词不能缺少哦",Toast.LENGTH_SHORT).show();

        return;

    }


}

    private  void PostImae(){
        CheckInfos();
String address="Article/PostImage";
        HttpUtil.uploadMultiFile(address, mArticleImageURL.getText().toString(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
        if (response.isSuccessful()){

            mPostResult=new Gson().fromJson(response.body().string(),new TypeToken<List<UploadFileResult>>(){}.getType());

            new PostArticleImageTask().execute();

        }
                else
        {


        }
            }
        });

    }

    class  PostArticleImageTask extends AsyncTask<Void,Integer,Boolean>{

        @Override
        protected Boolean doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            // super.onPostExecute(aBoolean);
            //mArticleImage
           if (mPostResult.size()>0){
               for (UploadFileResult result:mPostResult){

                   mArticleImage=result.getName();
                   PostArticles();

               }

           }
        }

    }

    private  void PostArticles(){

        Article newArticle=new Article();

        newArticle.setArticleCatalog(mCatalogName);
        newArticle.setArticleCatalogID(mCatalogID);
        newArticle.setArticleContent(mArticleContent.getHtml());
        newArticle.setArticleDescription(mAritcleDescription.getText().toString());
        newArticle.setArticleImage(mArticleImage);
        newArticle.setArticleTitle(mArticleTitle.getText().toString());
        newArticle.setArticleWords(mArticleKeyWords.getText().toString());
        newArticle.setCreateDate("");
        newArticle.setCreateDate("");
        newArticle.setID(0);
        newArticle.setReadCount(0);

        if (mArticleShowCustomer.isChecked()){

            newArticle.setShowCustomer(true);
        }
        else
        {

            newArticle.setShowCustomer(false);
        }
        String json=new Gson().toJson(newArticle,Article.class);

        String address="Article/Post";
        HttpUtil.PostJson(address, json, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
            if (response.isSuccessful()){
                mArticleResult=new Gson().fromJson(response.body().string(),Boolean.class);

new PostArticleTask().execute();

            }
                else {


            }
            }
        });


    }

class  PostArticleTask extends  AsyncTask<Void,Integer,Boolean>{

    @Override
    protected Boolean doInBackground(Void... params) {
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        // super.onPostExecute(aBoolean);
        //mArticleImage
        if (mArticleResult){
Toast.makeText(mainActivity,"文章发布成功",Toast.LENGTH_SHORT).show();


            mArticleTitle.setText("");
            mAritcleDescription.setText("");
            mArticleKeyWords.setText("");
            mArticleContent.setHtml("");
            mArticleImageURL.setText("");
            mArticleImageShow.setVisibility(View.INVISIBLE);

        }
        else
        {
            Toast.makeText(mainActivity,"文章发布失败",Toast.LENGTH_SHORT).show();

        }
    }

}


    private  void initPopupWindow(){


        View v=mainActivity.getLayoutInflater().inflate(R.layout.popup_link,null);
        mpopupView=new PopupWindow(v, Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT,true);
        mpopupView.setTouchable(true);
        mpopupView.setOutsideTouchable(true);
        mpopupView.setFocusable(true);
        mpopupView.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        Button sureButton=(Button)mpopupView.getContentView().findViewById(R.id.popup_link_sure);
      final   EditText linkNameText=(EditText)mpopupView.getContentView().findViewById(R.id.popup_link_name);
        final  EditText linkUrlText=(EditText)mpopupView.getContentView().findViewById(R.id.popup_link_url);
        sureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameVal=linkNameText.getText().toString();
                String urlVal=linkUrlText.getText().toString();

                if ("".equals(nameVal)){

                    Toast.makeText(mainActivity,"链接名称不能为空哦",Toast.LENGTH_SHORT).show();
                    return;
                }

                if ("".equals(urlVal)){
                    Toast.makeText(mainActivity,"链接地址不能为空哦",Toast.LENGTH_SHORT).show();
                    return;
                }

                mArticleContent.insertLink(urlVal,nameVal);

                mpopupView.dismiss();


            }
        });


        mpopupView.showAsDropDown(mActionLink);





    }

    private  void initPopupWindowPicture(){


        View v=mainActivity.getLayoutInflater().inflate(R.layout.popup_picture,null);
        mpopupView=new PopupWindow(v, Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT,true);
        mpopupView.setTouchable(true);
        mpopupView.setOutsideTouchable(true);
        mpopupView.setFocusable(true);
        mpopupView.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        final CardView pictureCameara=(CardView) mpopupView.getContentView().findViewById(R.id.popup_picture_Camera);
        final CardView pictureAlbum=(CardView) mpopupView.getContentView().findViewById(R.id.popup_picture_Album);

        pictureCameara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCemara();
                mpopupView.dismiss();
            }
        });

        pictureAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlbum();
                mpopupView.dismiss();
            }
        });

        mpopupView.showAtLocation(mArticleContent, Gravity.BOTTOM,0,0);

    }

    private void openAlbum(){

        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    private void openAlbum2(){

        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){

            case  CHOOSE_PHOTO:

                if (resultCode==RESUL_OK){

                    ContentResolver resolver=getContext().getContentResolver();

                    try{
                        InputStream inputStream=resolver.openInputStream(data.getData());
                        String imageUrl=data.getData().toString();
                        mArticleContent.insertImage(imageUrl,"选择的照片");

                    }
                    catch (FileNotFoundException e){

                        e.printStackTrace();
                    }
                }
                break;



            case TAKE_PHOTO:


                if (resultCode==RESUL_OK){
                    ContentResolver resolver=getContext().getContentResolver();
                    try{
                      Bitmap mBlobShowImage= BitmapFactory.decodeStream(resolver.openInputStream(imageUri));
                       // Toast.makeText(mainActivity,data.getData().toString(),Toast.LENGTH_SHORT).show();
                        String imageUrl=getBitmapPath(mBlobShowImage);


                       // Toast.makeText(mainActivity, mBlobShowImage.getConfig().toString(),Toast.LENGTH_SHORT).show();
                        mArticleContent.insertImage(imageUrl,"拍摄的图片");




                    }
                    catch (FileNotFoundException e){

                        e.printStackTrace();
                    }

                }
                break;

            case  CHOOSE_PHOTO2:

                if (resultCode==RESUL_OK) {


                    ContentResolver resolver = getContext().getContentResolver();

                    try {
                        InputStream inputStream = resolver.openInputStream(data.getData());

                        Bitmap mBlobShowImage = BitmapFactory.decodeStream(inputStream);


                        mArticleImageShow.setImageBitmap(mBlobShowImage);
                        mArticleImageShow.setVisibility(View.VISIBLE);
                        String bitmapResult = getBitmapPath(mBlobShowImage);
                        mArticleImageURL.setText(bitmapResult);


                    } catch (FileNotFoundException e) {

                        e.printStackTrace();
                    }

                }



                break;

            default:


        }
    }

    private void initPopupWindowBgColor(){

        View v=mainActivity.getLayoutInflater().inflate(R.layout.popup_bgcolor,null);
        mpopupView=new PopupWindow(v, Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT,true);
        mpopupView.setTouchable(true);
        mpopupView.setOutsideTouchable(true);
        mpopupView.setFocusable(true);
        mpopupView.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

      final   ImageView  bgColor_red=(ImageView) mpopupView.getContentView().findViewById(R.id.popup_bgcolor_red);
        final   ImageView  bgColor_yelow=(ImageView) mpopupView.getContentView().findViewById(R.id.popup_bgcolor_yellow);
        final   ImageView  bgColor_blue=(ImageView) mpopupView.getContentView().findViewById(R.id.popup_bgcolor_blue);
        final   ImageView  bgColor_green=(ImageView) mpopupView.getContentView().findViewById(R.id.popup_bgcolor_green);
        final   ImageView  bgColor_pick=(ImageView) mpopupView.getContentView().findViewById(R.id.popup_bgcolor_pick);


        bgColor_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetBgColor(COLOR_RED);
            }
        });
        bgColor_yelow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetBgColor(COLOR_YEELOW);
            }
        });
        bgColor_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetBgColor(COLOR_BLUE);
            }
        });
        bgColor_green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetBgColor(COLOR_GREEN);
            }
        });
        bgColor_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetBgColor(COLOR_PICK);
            }
        });

        mpopupView.showAsDropDown(mActionBgColor);
    }

    private void SetTextColor(int ColorInt)
    {
switch (ColorInt){

    case  COLOR_GREEN:

        mArticleContent.setTextColor(Color.GREEN);

        Toast.makeText(mainActivity,"字体颜色已设置为绿色",Toast.LENGTH_SHORT).show();
        break;
    case  COLOR_BLUE:
        mArticleContent.setTextColor(Color.BLUE);
        Toast.makeText(mainActivity,"字体颜色已设置为蓝色",Toast.LENGTH_SHORT).show();
        break;
    case  COLOR_PICK:
        mArticleContent.setTextColor(Color.LTGRAY);
        Toast.makeText(mainActivity,"字体颜色已设置为亮灰",Toast.LENGTH_SHORT).show();
        break;
    case COLOR_RED:
        mArticleContent.setTextColor(Color.RED);
        Toast.makeText(mainActivity,"字体颜色已设置为红色",Toast.LENGTH_SHORT).show();
        break;
    case  COLOR_YEELOW:
        mArticleContent.setTextColor(Color.YELLOW);
        Toast.makeText(mainActivity,"字体颜色已设置为黄色",Toast.LENGTH_SHORT).show();
        break;

}

        mpopupView.dismiss();

    }



    private void initPopupWindowTextColor(){

        View v=mainActivity.getLayoutInflater().inflate(R.layout.popup_bgcolor,null);
        mpopupView=new PopupWindow(v, Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT,true);
        mpopupView.setTouchable(true);
        mpopupView.setOutsideTouchable(true);
        mpopupView.setFocusable(true);
        mpopupView.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        final   ImageView  bgColor_red=(ImageView) mpopupView.getContentView().findViewById(R.id.popup_bgcolor_red);
        final   ImageView  bgColor_yelow=(ImageView) mpopupView.getContentView().findViewById(R.id.popup_bgcolor_yellow);
        final   ImageView  bgColor_blue=(ImageView) mpopupView.getContentView().findViewById(R.id.popup_bgcolor_blue);
        final   ImageView  bgColor_green=(ImageView) mpopupView.getContentView().findViewById(R.id.popup_bgcolor_green);
        final   ImageView  bgColor_pick=(ImageView) mpopupView.getContentView().findViewById(R.id.popup_bgcolor_pick);


        bgColor_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetTextColor(COLOR_RED);
            }
        });
        bgColor_yelow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetTextColor(COLOR_YEELOW);
            }
        });
        bgColor_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetTextColor(COLOR_BLUE);
            }
        });
        bgColor_green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetTextColor(COLOR_GREEN);
            }
        });
        bgColor_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetTextColor(COLOR_PICK);
            }
        });

        mpopupView.showAsDropDown(mActionBgColor);
    }

    private void SetBgColor(int ColorInt)
    {
        switch (ColorInt){

            case  COLOR_GREEN:

                mArticleContent.setTextBackgroundColor(Color.GREEN);

                Toast.makeText(mainActivity,"字体背景色已设置为绿色",Toast.LENGTH_SHORT).show();
                break;
            case  COLOR_BLUE:
                mArticleContent.setTextBackgroundColor(Color.BLUE);
                Toast.makeText(mainActivity,"字体背景色已设置为蓝色",Toast.LENGTH_SHORT).show();
                break;
            case  COLOR_PICK:
                mArticleContent.setTextBackgroundColor(Color.LTGRAY);
                Toast.makeText(mainActivity,"字体背景色已设置为亮灰",Toast.LENGTH_SHORT).show();
                break;
            case COLOR_RED:
                mArticleContent.setTextBackgroundColor(Color.RED);
                Toast.makeText(mainActivity,"字体背景色已设置为红色",Toast.LENGTH_SHORT).show();
                break;
            case  COLOR_YEELOW:
                mArticleContent.setTextBackgroundColor(Color.YELLOW);
                Toast.makeText(mainActivity,"字体背景色已设置为黄色",Toast.LENGTH_SHORT).show();
                break;

        }

        mpopupView.dismiss();

    }

    private String getBitmapPath(Bitmap bitmap){
        String filePath="";
        FileOutputStream fileOutputStream=null;

        try{
            String saveDir= Environment.getExternalStorageDirectory()+"/Blob_Photos";
            File dir=new File(saveDir);

            if (!dir.exists()){

                dir.mkdir();
            }

            SimpleDateFormat t=new SimpleDateFormat("yyyyMMddhhmmssSSS");
            String filename="BB"+(t.format(new Date()))+".jpg";
            File file=new File(saveDir,filename);

            fileOutputStream=new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
            filePath=file.getPath();



        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (fileOutputStream!=null){
                try{
                    fileOutputStream.close();
                }
                catch (Exception e){

                    e.printStackTrace();
                }

            }

            return  filePath;
        }

    }

    private  void openCemara(){

        File outputImage=new File(getActivity().getExternalCacheDir(),"out_put_artilcephots.jpg");
        try{
            if (outputImage.exists()){

                outputImage.delete();
            }
            outputImage.createNewFile();

        }catch (IOException e){

            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT>=24){
            //  imageUri= Uri.fromFile(outputImage);
            imageUri= FileProvider.getUriForFile(getActivity(),"top.flyyoung.www.flyyoung.fileprovider",outputImage);
        }else
        {
            imageUri= Uri.fromFile(outputImage);

        }

        Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,TAKE_PHOTO);
    }
}
