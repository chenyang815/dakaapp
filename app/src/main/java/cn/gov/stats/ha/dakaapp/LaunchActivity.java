package cn.gov.stats.ha.dakaapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class LaunchActivity extends Activity implements View.OnClickListener{
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private EditText mAccount;
    private EditText mPassword;
    private Button mLogin;
    private CheckBox rememberAccount;
    private CheckBox rememberPassword;

    private static final int LOGIN_JUDGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        mAccount = (EditText) findViewById(R.id.account);
        mPassword = (EditText)findViewById(R.id.password);
        mLogin = (Button)findViewById(R.id.login);
        rememberAccount = (CheckBox)findViewById(R.id.remember_account);
        rememberPassword = (CheckBox)findViewById(R.id.remember_password);

        boolean isRemenberAccount=preferences.getBoolean("remember_account",false);
        boolean isRemenberPassword=preferences.getBoolean("remember_password",false);

        mLogin.setOnClickListener(this);

        if(isRemenberAccount){
            String remuid=preferences.getString("remuid","");
            mAccount.setText(remuid);
            rememberAccount.setChecked(true);
        }
        if(isRemenberPassword){
            String rempassword=preferences.getString("rempassword","");
            mPassword.setText(rempassword);
            rememberPassword.setChecked(true);
        }

    }



    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                case LOGIN_JUDGE:{
                    Bundle bundle = new Bundle();
                    bundle = msg.getData();
                    String result = bundle.getString("result");
                    String uid = bundle.getString("uid");
                    String password = bundle.getString("password");
                    try {
                        if (result.equals("success")) {

                            editor = preferences.edit();
                            if(rememberAccount.isChecked()){
                                editor.putBoolean("remember_account",true);
                                editor.putString("remuid",uid);
                            }{
                                editor.clear();
                            }
                            if(rememberPassword.isChecked()){
                                editor.putBoolean("remember_password",true);
                                editor.putString("rempassword",password);
                            }{
                                editor.clear();
                            }
                            editor.commit();

                            Toast.makeText(LaunchActivity.this,"登录成功！",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LaunchActivity.this,MainActivity.class);
                            intent.putExtra("uid",uid);
                            intent.putExtra("password",password);
                            startActivity(intent);
                        }
                        else if(result.equals("fail")){
                            Toast.makeText(LaunchActivity.this,"登陆失败，账号或密码错误!",Toast.LENGTH_SHORT).show();
                        }

                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
    };


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.login:{
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //使用下面类里的函数，连接servlet，返回一个result，使用handler处理这个result
                        final String uid = mAccount.getText().toString();
                        final String password = mPassword.getText().toString();

                        String result = HttpLink.loginByPost(uid,password);
                        Bundle bundle = new Bundle();
                        bundle.putString("result",result);
                        bundle.putString("uid",uid);
                        bundle.putString("password",password);
                        Message message = new Message();
                        message.setData(bundle);
                        message.what = LOGIN_JUDGE;
                        handler.sendMessage(message);
                    }
                }).start();
            }
            break;
        }

    }
}
