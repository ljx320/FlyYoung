package top.flyyoung.www.flyyoung;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import top.flyyoung.www.flyyoung.Fragments.MyAlbumsFragment;
import top.flyyoung.www.flyyoung.Fragments.MyArticleFragment;
import top.flyyoung.www.flyyoung.Fragments.MyBlobsFragment;
import top.flyyoung.www.flyyoung.Fragments.MyDownloadsFragment;
import top.flyyoung.www.flyyoung.Fragments.MyMessagesFragment;

public class MainActivity extends AppCompatActivity {
    private NavigationView mMainNavagitionView;
    private TextView mMenuUserTel;

    private DrawerLayout mDrawerLayout;

    private MyBlobsFragment mBlobFragment;
    private MyArticleFragment mArticleFragment;
    private MyAlbumsFragment mAlbumFragment;
    private MyDownloadsFragment mDownloadsFragment;
    private MyMessagesFragment mMessagesFragment;

    private   long exitTime=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String userTel = intent.getStringExtra("martron_tel");
        mMainNavagitionView = (NavigationView) findViewById(R.id.main_navigationView);
        View headView = mMainNavagitionView.getHeaderView(0);
        mMenuUserTel = (TextView) headView.findViewById(R.id.menu_main_tel);
        mMenuUserTel.setText(userTel);
        mDrawerLayout=(DrawerLayout)findViewById(R.id.main_drawerLayout);


        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction trasaction = fragmentManager.beginTransaction();
        if (mBlobFragment == null) {
            mBlobFragment = new MyBlobsFragment();
            trasaction.replace(R.id.main_frameLayout, mBlobFragment);
            trasaction.commit();

        }



        mMainNavagitionView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                item.setChecked(true);
                switch (item.getItemId()) {

                    case R.id.menu_article:
                        if (mArticleFragment == null) {

                            mArticleFragment = new MyArticleFragment();
                        }
                        replaceFragement(mArticleFragment);
                        break;
                    case R.id.menu_blod:

                        if (mBlobFragment == null) {

                            mBlobFragment = new MyBlobsFragment();
                        }
                        replaceFragement(mBlobFragment);

                        break;
                    case  R.id.menu_album:
                        if (mAlbumFragment==null)
                        {
                            mAlbumFragment=new MyAlbumsFragment();

                        }
                        replaceFragement(mAlbumFragment);
                        break;
                    case R.id.menu_download:

                        if (mDownloadsFragment==null){

                            mDownloadsFragment=new MyDownloadsFragment();
                        }
                        replaceFragement(mDownloadsFragment);
                        break;
                    case R.id.menu_message:
                        if (mMessagesFragment==null){


                            mMessagesFragment=new MyMessagesFragment();
                        }
                        replaceFragement(mMessagesFragment);
                        break;

                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });


    }

    public static void ActionStart(Context context, String tel) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("martron_tel", tel);
        context.startActivity(intent);


    }


    private  void replaceFragement(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frameLayout,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }


    public interface  MyTouchListener{
        public  boolean onTouchEvent(MotionEvent event);

    }

    private ArrayList<MyTouchListener> myTouchListeners=new ArrayList<MyTouchListener>();

    public  void registerMyTouchListener(MyTouchListener listener){

        myTouchListeners.add(listener);
    }
    public void unRegisterMyTouchListener(MyTouchListener listener){
        myTouchListeners.remove(listener);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MyTouchListener listener:myTouchListeners){
            listener.onTouchEvent(ev);

        }

        return  super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_DOWN){

            if ((System.currentTimeMillis()-exitTime)>2000){
                Toast.makeText(getApplicationContext(),"再按一次退出程序",Toast.LENGTH_SHORT).show();
                exitTime=System.currentTimeMillis();

            }
            else {

                finish();
                System.exit(0);

            }

            return  true;


        }

        return  super.onKeyDown(keyCode,event);
    }
}
