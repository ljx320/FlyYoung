package top.flyyoung.www.flyyoung.Utils;

import java.io.File;
import java.util.Date;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by 69133 on 2017/1/11.
 */

public class HttpUtil {
    private static final String WEBHOST_ADDRESS = "http://www.flyyoung.top/api/";
    public static final String WEBHOST = "http://www.flyyoung.top";



    public static void sendOkHttpRequest(String address, Callback callback) {

        String webAddress = WEBHOST_ADDRESS + address;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(webAddress).build();
        client.newCall(request).enqueue(callback);


    }


    public static  void uploadMultiFile(String address,String filePath,Callback callback){
        String webAddress = WEBHOST_ADDRESS + address;



        File file=new File(filePath);
        RequestBody fileBody=RequestBody.create(MediaType.parse("application/octet-stream"),file);
        RequestBody requestBody=new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image","result.jpg",fileBody)
                .build();

        Request request=new Request.Builder()
                .url(webAddress)
                .post(requestBody)
                .build();

        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(callback);


    }

    public static void PostJson(String address,String json,Callback callback){
        String webAddress = WEBHOST_ADDRESS + address;
        OkHttpClient okHttpClient=new OkHttpClient();
        RequestBody requestBody=RequestBody.create(MediaType.parse("application/json"),json);
        Request request=new Request.Builder().url(webAddress).post(requestBody).build();
        okHttpClient.newCall(request).enqueue(callback);

    }


}
