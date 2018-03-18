package cn.gov.stats.ha.dakaapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.net.URL;

public class MainActivity extends Activity implements View.OnClickListener{
    private String id;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        id = intent.getStringExtra("id1");
        url = "http://192.168.1.101:8080/tjjdaka/MyServlet";
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.morcome:
                new HttpThread(url,id,"morcame").start();
                break;
            case R.id.morleave:
                new HttpThread(url,id,"morleave").start();
                break;
            case R.id.aftcome:
                new HttpThread(url,id,"aftcome").start();
                break;
            case R.id.aftleave:
                new HttpThread(url,id,"aftleave").start();
                break;
        }

    }
}
