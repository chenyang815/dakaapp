package cn.gov.stats.ha.dakaapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;

public class MainActivity extends Activity implements View.OnClickListener{

    private Button morCome,morLeave,aftCome,aftLeave,dakalog;

    private static final int DAKASUCC  = 1;
    private static final int DAKALOG  = 2;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        morCome = (Button)findViewById(R.id.morcome);
        morLeave = (Button)findViewById(R.id.morleave);
        aftCome = (Button)findViewById(R.id.aftcome);
        aftLeave = (Button)findViewById(R.id.aftleave);
        dakalog = (Button)findViewById(R.id.dakalog);

        morCome.setOnClickListener(this);
        morLeave.setOnClickListener(this);
        aftCome.setOnClickListener(this);
        aftLeave.setOnClickListener(this);
        dakalog.setOnClickListener(this);

        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
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
                    try {
                        if (result.equals("success")) {
                            Toast.makeText(MainActivity.this,"打卡成功！",Toast.LENGTH_SHORT).show();
                        }
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
                break;
                case DAKALOG:{
                    Bundle bundle = new Bundle();
                    bundle = msg.getData();
                    String result = bundle.getString("result");

                    Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
                    Log.d("json", result);

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
                        String result = HttpLink.dakaByPost(uid,"上班");
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
                        String result = HttpLink.dakaByPost(uid,"morleave");
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
                    String result = HttpLink.dakaByPost(uid,"aftcome");
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
                        String result = HttpLink.dakaByPost(uid,"aftleave");
                        Bundle bundle = new Bundle();
                        bundle.putString("result",result);
                        Message message = new Message();
                        message.setData(bundle);
                        message.what = DAKASUCC;
                        handler.sendMessage(message);
                    }
                }).start();
                break;
            case R.id.dakalog:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String result = HttpLink.listByUid(uid);
                        Bundle bundle = new Bundle();
                        bundle.putString("result",result);
                        Message message = new Message();
                        message.setData(bundle);
                        message.what = DAKALOG;
                        handler.sendMessage(message);
                    }
                }).start();
                break;
        }

    }
}
