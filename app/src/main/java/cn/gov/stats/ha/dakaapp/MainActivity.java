package cn.gov.stats.ha.dakaapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import java.net.URL;

public class MainActivity extends Activity implements View.OnClickListener{

    private Button morCome,morLeave,aftCome,aftLeave;
    private Button mSettings,mRecord;

    private static final int DAKASUCC  = 1;
    private String uid,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        morCome = (Button)findViewById(R.id.morcome);
        morLeave = (Button)findViewById(R.id.morleave);
        aftCome = (Button)findViewById(R.id.aftcome);
        aftLeave = (Button)findViewById(R.id.aftleave);
        mSettings = (Button)findViewById(R.id.settings);
        mRecord = (Button)findViewById(R.id.record);

        morCome.setOnClickListener(this);
        morLeave.setOnClickListener(this);
        aftCome.setOnClickListener(this);
        aftLeave.setOnClickListener(this);
        mSettings.setOnClickListener(this);
        mRecord.setOnClickListener(this);

        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        password = intent.getStringExtra("password");
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                case DAKASUCC:{
                    Bundle bundle = new Bundle();
                    bundle = msg.getData();
                    String result = bundle.getString("result");
                    if (result.equals("YES")) {
                        Toast.makeText(MainActivity.this,"打卡成功！",Toast.LENGTH_SHORT).show();
                    }else if (result.equals("NO")){
                        Toast.makeText(MainActivity.this,"已打卡，未在规定时段！",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            }
        }
    };



    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.morcome:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String result = HttpLink.dakaByPost(uid,"上午-上班");
                        Bundle bundle = new Bundle();
                        bundle.putString("result",result);
                        Message message = new Message();
                        message.setData(bundle);
                        message.what = DAKASUCC;
                        handler.sendMessage(message);
                    }
                }).start();
                break;
            case R.id.morleave:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String result = HttpLink.dakaByPost(uid,"上午-下班");
                        Bundle bundle = new Bundle();
                        bundle.putString("result",result);
                        Message message = new Message();
                        message.setData(bundle);
                        message.what = DAKASUCC;
                        handler.sendMessage(message);
                    }
                }).start();
                break;
            case R.id.aftcome:
                new Thread(new Runnable() {
                @Override
                public void run() {
                    String result = HttpLink.dakaByPost(uid,"下午-上班");
                    Bundle bundle = new Bundle();
                    bundle.putString("result",result);
                    Message message = new Message();
                    message.setData(bundle);
                    message.what = DAKASUCC;
                    handler.sendMessage(message);
                }
            }).start();
                break;
            case R.id.aftleave:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String result = HttpLink.dakaByPost(uid,"下午-下班");
                        Bundle bundle = new Bundle();
                        bundle.putString("result",result);
                        Message message = new Message();
                        message.setData(bundle);
                        message.what = DAKASUCC;
                        handler.sendMessage(message);
                    }
                }).start();
                break;
            case R.id.settings:
                Intent intent1 = new Intent(MainActivity.this,SettingsActivity.class);
                intent1.putExtra("uid",uid);
                intent1.putExtra("password",password);
                startActivity(intent1);
                break;
            case R.id.record:
                Intent intent2 = new Intent(MainActivity.this,RecordActivity.class);
                intent2.putExtra("uid",uid);
                intent2.putExtra("password",password);
                startActivity(intent2);
                break;

        }

    }
}
