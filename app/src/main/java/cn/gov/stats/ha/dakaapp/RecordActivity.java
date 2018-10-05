package cn.gov.stats.ha.dakaapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends Activity {

    private String uid,password;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_record);

        mListView = (ListView)findViewById(R.id.record_list);

        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        password = intent.getStringExtra("password");

        new MyAsyncTask().execute(uid);
    }


    class MyAsyncTask extends AsyncTask<String,Void,List<RecordItemBean>>{

        @Override
        protected List<RecordItemBean> doInBackground(String... strings) {
            return getJsonData(strings[0]);
        }
    }

    private List<RecordItemBean> getJsonData(String uid){
        List<RecordItemBean> listRecordItemBean = new ArrayList<>();
        String jsonString = HttpLink.listByUid(uid);
        try {
            JSONArray jsonArray=new JSONArray(jsonString);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject =jsonArray.getJSONObject(i);
                String uuid = jsonObject.getString("uid");
                Log.d("xyz", uuid);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listRecordItemBean;

    }
}
