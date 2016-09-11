package com.mymiu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mymiu.R;

import java.util.List;
import java.util.Map;



public class MainGridAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String,Object>> list;
    public MainGridAdapter(Context context,List<Map<String,Object>> list){
        this.context=context;
        this.list=list;
    }
    public final class Widgets{
        private ImageView image;
        private TextView tv1;
    }

    @Override
    //返回adapter当中包含多少个item
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    //根据位置得到相应的item对象
    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    //根据位置得到相应的item对象的Id
    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        // TODO Auto-generated method stub
        Widgets widgets=null;
        if(arg1==null){
            widgets=new Widgets();
            LayoutInflater layoutInflater=LayoutInflater.from(context);
            arg1=layoutInflater.inflate(R.layout.main_gridview_item, null);
            widgets.image=(ImageView)arg1.findViewById(R.id.main_gridview_item_image);
            widgets.tv1=(TextView)arg1.findViewById(R.id.main_gridview_item_text);
            arg1.setTag(widgets);
        }else{
            widgets=(Widgets)arg1.getTag();
        }
        //绑定数据
        widgets.image.setBackgroundResource((Integer) list.get(arg0).get("image"));
        widgets.tv1.setText((Integer)list.get(arg0).get("text"));
        return arg1;
    }

}
