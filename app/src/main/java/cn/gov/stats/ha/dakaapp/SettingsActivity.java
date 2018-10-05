package cn.gov.stats.ha.dakaapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class SettingsActivity extends Activity {

    private String uid,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_settings);

        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        password = intent.getStringExtra("password");
    }
}
