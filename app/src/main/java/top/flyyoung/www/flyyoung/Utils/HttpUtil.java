package top.flyyoung.www.flyyoung.Utils;

import android.webkit.MimeTypeMap;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private static final String[][] MIME_MapTable={
            //{后缀名，    MIME类型}
            {".3gp",    "video/3gpp"},
            {".apk",    "application/vnd.android.package-archive"},
            {".asf",    "video/x-ms-asf"},
            {".avi",    "video/x-msvideo"},
            {".bin",    "application/octet-stream"},
            {".bmp",      "image/bmp"},
            {".c",        "text/plain"},
            {".class",    "application/octet-stream"},
            {".conf",    "text/plain"},
            {".cpp",    "text/plain"},
            {".doc",    "application/msword"},
            {".exe",    "application/octet-stream"},
            {".gif",    "image/gif"},
            {".gtar",    "application/x-gtar"},
            {".gz",        "application/x-gzip"},
            {".h",        "text/plain"},
            {".htm",    "text/html"},
            {".html",    "text/html"},
            {".jar",    "application/java-archive"},
            {".java",    "text/plain"},
            {".jpeg",    "image/jpeg"},
            {".jpg",    "image/jpeg"},
            {".js",        "application/x-javascript"},
            {".log",    "text/plain"},
            {".m3u",    "audio/x-mpegurl"},
            {".m4a",    "audio/mp4a-latm"},
            {".m4b",    "audio/mp4a-latm"},
            {".m4p",    "audio/mp4a-latm"},
            {".m4u",    "video/vnd.mpegurl"},
            {".m4v",    "video/x-m4v"},
            {".mov",    "video/quicktime"},
            {".mp2",    "audio/x-mpeg"},
            {".mp3",    "audio/x-mpeg"},
            {".mp4",    "video/mp4"},
            {".mpc",    "application/vnd.mpohun.certificate"},
            {".mpe",    "video/mpeg"},
            {".mpeg",    "video/mpeg"},
            {".mpg",    "video/mpeg"},
            {".mpg4",    "video/mp4"},
            {".mpga",    "audio/mpeg"},
            {".msg",    "application/vnd.ms-outlook"},
            {".ogg",    "audio/ogg"},
            {".pdf",    "application/pdf"},
            {".png",    "image/png"},
            {".pps",    "application/vnd.ms-powerpoint"},
            {".ppt",    "application/vnd.ms-powerpoint"},
            {".prop",    "text/plain"},
            {".rar",    "application/x-rar-compressed"},
            {".rc",        "text/plain"},
            {".rmvb",    "audio/x-pn-realaudio"},
            {".rtf",    "application/rtf"},
            {".sh",        "text/plain"},
            {".tar",    "application/x-tar"},
            {".tgz",    "application/x-compressed"},
            {".txt",    "text/plain"},
            {".wav",    "audio/x-wav"},
            {".wma",    "audio/x-ms-wma"},
            {".wmv",    "audio/x-ms-wmv"},
            {".wps",    "application/vnd.ms-works"},
            //{".xml",    "text/xml"},
            {".xml",    "text/plain"},
            {".z",        "application/x-compress"},
            {".zip",    "application/zip"},
            {"",        "*/*"}
    };

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

    public static  void uploadFile(String address,String filePath,Callback callback){
        String webAddress = WEBHOST_ADDRESS + address;



        File file=new File(filePath);
        String fileName=file.getName();
        String extension=fileName.substring(fileName.lastIndexOf(".")+1);
String typeResult="";
        for (int i=0;i< MIME_MapTable.length;i++){

            if (extension.equals(MIME_MapTable[i][0])){
                typeResult=MIME_MapTable[i][1];

            }
        }

        RequestBody fileBody=RequestBody.create(MediaType.parse("application/octet-stream"),file);
        RequestBody requestBody=new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(typeResult,"result."+extension+"",fileBody)
                .build();

        Request request=new Request.Builder()
                .url(webAddress)
                .post(requestBody)
                .build();

        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(callback);


    }

    public static  void uploadMultiFile(String address, List<String> filePaths, Callback callback){
        String webAddress = WEBHOST_ADDRESS + address;


        List<RequestBody> mRequestBodys=new ArrayList<RequestBody>();

        for (String filePath:filePaths){
            File file=new File(filePath);
            RequestBody fileBody=RequestBody.create(MediaType.parse("application/octet-stream"),file);


            mRequestBodys.add(fileBody);

        }

        MultipartBody.Builder mBody=new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (RequestBody requestBody:mRequestBodys){

            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String dateString=simpleDateFormat.format(new Date())+".jpg";
            mBody.addFormDataPart("file",dateString,requestBody);

        }


        RequestBody body=mBody.build();

        Request request=new Request.Builder()
                .url(webAddress)
                .post(body)
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
