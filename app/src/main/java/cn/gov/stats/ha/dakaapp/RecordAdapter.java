package cn.gov.stats.ha.dakaapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by xy on 2018/10/6.
 */

public class RecordAdapter extends BaseAdapter {

    private List<RecordItemBean> mList;
    private LayoutInflater mInflater;

    public RecordAdapter(RecordActivity context, List<RecordItemBean> list){
        mList = list;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
         if(view == null){
             viewHolder = new ViewHolder();
             view = mInflater.inflate(R.layout.item_layout,null);
             viewHolder.itemIcon = (ImageView)view.findViewById(R.id.item_icon);
             viewHolder.itemTag = (TextView)view.findViewById(R.id.item_tag);
             viewHolder.itemDate = (TextView)view.findViewById(R.id.item_date);
             view.setTag(viewHolder);
         }else {
             viewHolder = (ViewHolder)view.getTag();
         }
         switch (mList.get(i).getInRange()){
             case "YES":
                 viewHolder.itemIcon.setImageResource(R.mipmap.blue);
                 break;
             case "NO":
                 viewHolder.itemIcon.setImageResource(R.mipmap.red);
                 break;
         }
         viewHolder.itemTag.setText(mList.get(i).getTag());
         viewHolder.itemDate.setText(mList.get(i).getDate());
        return view;
    }

    class ViewHolder{
        public ImageView itemIcon;
        public TextView itemTag,itemDate;
    }
}
