package top.flyyoung.www.flyyoung;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.flyyoung.www.flyyoung.Utils.HttpUtil;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private Toolbar mToolBar;
    private EditText mTelText;
    private EditText mPasswordText;
    private CheckBox mRememberCheckBox;
    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = getSharedPreferences("user_data", MODE_PRIVATE);
        mToolBar = (Toolbar) findViewById(R.id.login_toolbar);
        mToolBar.setTitle(R.string.app_login);

        mToolBar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(mToolBar);


        mTelText = (EditText) findViewById(R.id.login_tel_text);
        mPasswordText = (EditText) findViewById(R.id.login_password_text);
        mRememberCheckBox = (CheckBox) findViewById(R.id.login_remember);
        mLoginButton = (Button) findViewById(R.id.login_right_login);

        Boolean rememberme = preferences.getBoolean("rememberme", false);
        if (rememberme) {

            String userTel = preferences.getString("tel", "");
            String userPassword = preferences.getString("password", "");
            mTelText.setText(userTel);
            mPasswordText.setText(userPassword);

        }


        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String telVal = mTelText.getText().toString();
                final String passwordVal = mPasswordText.getText().toString();

                if ("".equals(telVal)) {

                    Toast.makeText(LoginActivity.this, R.string.login_telWaring, Toast.LENGTH_SHORT).show();
                }

                if ("".equals(passwordVal)) {
                    Toast.makeText(LoginActivity.this, R.string.login_passwordWaring, Toast.LENGTH_SHORT).show();
                }
                String address = "Martron/get?tel=" + telVal + "&password=" + passwordVal + "";
                HttpUtil.sendOkHttpRequest(address, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, R.string.http_requsetfailed, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        if (response.isSuccessful()) {
                            Gson gson = new Gson();
                            final boolean result = gson.fromJson(response.body().string(), Boolean.class);
                            Log.d("result", String.valueOf(result));
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {


                                    if (result) {

                                        if (mRememberCheckBox.isChecked()) {

                                            SharedPreferences.Editor editor = getSharedPreferences("user_data", MODE_PRIVATE).edit();

                                            editor.putBoolean("rememberme", true);
                                            editor.putString("tel", telVal);
                                            editor.putString("password", passwordVal);
                                            editor.apply();
                                        }

                                        MainActivity.ActionStart(LoginActivity.this, telVal);
                                        finish();


                                    } else {
                                        Toast.makeText(LoginActivity.this, R.string.login_matchfailed, Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                        } else {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, R.string.http_requsetfailed, Toast.LENGTH_SHORT).show();
                                }
                            });


                        }
                    }
                });

            }
        });


    }
}
