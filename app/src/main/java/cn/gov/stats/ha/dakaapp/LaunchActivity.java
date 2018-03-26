package cn.gov.stats.ha.dakaapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LaunchActivity extends Activity implements View.OnClickListener{
    private EditText mAccount;
    private EditText mPassword;
    private Button mLogin;

    private static final int LOGIN_JUDGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        mAccount = (EditText) findViewById(R.id.account);
        mPassword = (EditText)findViewById(R.id.password);
        mLogin = (Button)findViewById(R.id.login);

        mLogin.setOnClickListener(this);

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
                    try {
                        if (result.equals("success")) {
                            Toast.makeText(LaunchActivity.this,"登录成功！",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LaunchActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(LaunchActivity.this,"登陆失败，密码错误!",Toast.LENGTH_SHORT).show();
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
                        String result = HttpLink.LoginByPost(mAccount.getText().toString(),mPassword.getText().toString());
                        Bundle bundle = new Bundle();
                        bundle.putString("result",result);
                        Message message = new Message();
                        message.setData(bundle);
                        message.what = LOGIN_JUDGE;
                        handler.sendMessage(message);
                    }
                }).start();
            }
            break;
        }
//        final String id = mAccount.getText().toString();
//        final String password = mPassword.getText().toString();
//
//        if(id.equals("18538721898")&password.equals("123456")){
//            Intent intent = new Intent(LaunchActivity.this,MainActivity.class);
//            intent.putExtra("id1",id);
//            startActivity(intent);
//        }else {
//            Toast.makeText(LaunchActivity.this,"登陆失败，密码错误",Toast.LENGTH_SHORT).show();
//        }

    }
}
