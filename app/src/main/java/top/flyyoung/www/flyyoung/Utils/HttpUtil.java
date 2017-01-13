package top.flyyoung.www.flyyoung.Utils;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

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


}
