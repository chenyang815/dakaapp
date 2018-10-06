package cn.gov.stats.ha.dakaapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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

        @Override
        protected void onPostExecute(List<RecordItemBean> list) {
            super.onPostExecute(list);
            RecordAdapter adapter = new RecordAdapter(RecordActivity.this,list);
            mListView.setAdapter(adapter);
        }
    }

    private List<RecordItemBean> getJsonData(String uid){
        String jsonString = HttpLink.listByUid(uid);
        List<RecordItemBean> recordItemBeanList = new ArrayList<>();
        RecordItemBean recordItemBean;
        try {
            JSONArray jsonArray=new JSONArray(jsonString);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject =jsonArray.getJSONObject(i);
                String date1 = jsonObject.getString("date");
                String date = utcToDate(date1);
                recordItemBean = new RecordItemBean();
                recordItemBean.tag = jsonObject.getString("tag");
                recordItemBean.date = date;
                recordItemBean.inRange = jsonObject.getString("inRange");
                recordItemBeanList.add(recordItemBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recordItemBeanList;


    }

    private String utcToDate(String date1) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS+SSSS");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日 HH点mm分");
        Date result_date;
        Long result_time = null;
        try {
            sdf1.setTimeZone(TimeZone.getTimeZone("GMT00:00"));
            result_date = sdf1.parse(date1);
            result_time = result_date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        sdf2.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return sdf2.format(result_time);
    }

}
