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
import java.util.Calendar;
import java.util.Collections;
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
            //将list倒序
            Collections.reverse(list);
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
                recordItemBean.setTag(jsonObject.getString("tag"));
                recordItemBean.setInRange(jsonObject.getString("inRange"));
                recordItemBean.setDate(date);

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

        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");

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
        String final_date = sdf2.format(result_time);

        sdf3.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String week = getWeek(sdf3.format(result_time));

        return final_date+week;
    }

    private String getWeek(String time) {
        String week = "  ";
        SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf4.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int wek=c.get(Calendar.DAY_OF_WEEK);

        switch (wek){
            case 1:
                week += "星期日";
                break;
            case 2:
                week += "星期一";
                break;
            case 3:
                week += "星期二";
                break;
            case 4:
                week += "星期三";
                break;
            case 5:
                week += "星期四";
                break;
            case 6:
                week += "星期五";
                break;
            case 7:
                week += "星期六";
                break;

        }
        return week;
    }

}
