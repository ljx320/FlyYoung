package top.flyyoung.www.flyyoung.Utils;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 69133 on 2017/1/16.
 */

public class BitmapUtil {



    public static String getBitmapPath(Bitmap bitmap,String imageType){
        String filePath="";
        FileOutputStream fileOutputStream=null;

        try{
            String saveDir= Environment.getExternalStorageDirectory()+"/"+imageType+"";
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
}
