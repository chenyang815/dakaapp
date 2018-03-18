package cn.gov.stats.ha.dakaapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LaunchActivity extends Activity {
    private EditText mAccount;
    private EditText mPassword;
    private Button mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        mAccount = (EditText) findViewById(R.id.account);
        mPassword = (EditText)findViewById(R.id.password);
        mLogin = (Button)findViewById(R.id.login);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String id = mAccount.getText().toString();
                final String password = mPassword.getText().toString();

                if(id.equals("18538721898")&password.equals("123456")){
                    Intent intent = new Intent(LaunchActivity.this,MainActivity.class);
                    intent.putExtra("id1",id);
                    startActivity(intent);
                }else {
                    Toast.makeText(LaunchActivity.this,"登陆失败，密码错误",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}
