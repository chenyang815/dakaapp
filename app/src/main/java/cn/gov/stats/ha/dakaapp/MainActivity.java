package cn.gov.stats.ha.dakaapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;

public class MainActivity extends Activity implements View.OnClickListener{
    private String id;
    private String url;

    private static final int DAKASUCC = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent intent = getIntent();
//        id = intent.getStringExtra("id1");
//        url = "http://192.168.1.101:8080/tjjdaka/DakaServlert";
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
                        String result = HttpLink.DakaByPost("admin","morcome");
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
                        String result = HttpLink.DakaByPost("admin","morleave");
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
                    String result = HttpLink.DakaByPost("admin","aftcome");
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
                        String result = HttpLink.DakaByPost("admin","aftleave");
                        Bundle bundle = new Bundle();
                        bundle.putString("result",result);
                        Message message = new Message();
                        message.setData(bundle);
                        message.what = DAKASUCC;
                        handler.sendMessage(message);
                    }
                }).start();
                break;
        }

    }
}
