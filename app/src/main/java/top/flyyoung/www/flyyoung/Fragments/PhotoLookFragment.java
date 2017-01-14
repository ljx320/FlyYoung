package top.flyyoung.www.flyyoung.Fragments;

import android.animation.Animator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import top.flyyoung.www.flyyoung.MainActivity;
import top.flyyoung.www.flyyoung.R;
import top.flyyoung.www.flyyoung.Utils.HttpUtil;

/**
 * Created by 69133 on 2017/1/14.
 */

public class PhotoLookFragment extends Fragment{

    private  final int ORITION_RIGHT=1;
    private final int ORTION_LEFT=2;

    private ImageView mPhotoLookView;

    private  int nowPosition;
    private ArrayList<String>  photoUrls;
    private String nowPhotoUrl;

    private ScaleGestureDetector mScaleDetector;

    private float rawX;
    private  float resultX;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.photo_look,container,false);

        mPhotoLookView=(ImageView) view.findViewById(R.id.photoLook);


        MainActivity.MyTouchListener myOnTouchListener=new MainActivity.MyTouchListener(){

            @Override
            public boolean onTouchEvent(MotionEvent event) {
          //      Toast.makeText(getActivity(),"触屏事件",Toast.LENGTH_SHORT).show();

//                mScaleDetector.onTouchEvent(event);

                final int action= MotionEventCompat.getActionMasked(event);
                switch (action){
                    case MotionEvent.ACTION_UP:

                         resultX=event.getX();

                        try
                        {

                            if (resultX>rawX){


                                nowPosition=nowPosition-1;
                                LoadNowImage(ORTION_LEFT);
                            }
                            if (resultX<rawX){
                                nowPosition=nowPosition+1;
                                LoadNowImage(ORITION_RIGHT);

                            }
                        }catch (Exception e){
                            Toast.makeText(getActivity(),"已经没有照片了",Toast.LENGTH_SHORT).show();
                        }


                        break;
                    case  MotionEvent.ACTION_DOWN:
                        rawX=event.getX();
                        break;
                }



                return false;
            }
        };

        MainActivity mainActivity=(MainActivity) getActivity();
        mainActivity.registerMyTouchListener(myOnTouchListener);

        Bundle bundle=getArguments();
        if (bundle!=null){

            nowPosition=bundle.getInt("nowposition");
            photoUrls=bundle.getStringArrayList("photoUrlList");
//            nowPhotoUrl=photoUrls.get(nowPosition);
//
//            Glide.with(getActivity()).load(HttpUtil.WEBHOST+nowPhotoUrl).into(mPhotoLookView);
            LoadNowImage(ORTION_LEFT);
        }

        return  view;
    }

    private void LoadNowImage(int orition)
    {
        nowPhotoUrl=photoUrls.get(nowPosition);
        switch (orition){

            case ORTION_LEFT:
                Glide.with(getActivity()).load(HttpUtil.WEBHOST+nowPhotoUrl).animate(android.R.anim.slide_in_left).into(mPhotoLookView);
                break;
            case  ORITION_RIGHT:
                Glide.with(getActivity()).load(HttpUtil.WEBHOST+nowPhotoUrl).animate(android.R.anim.slide_out_right).into(mPhotoLookView);
                break;
        }




    }
}
