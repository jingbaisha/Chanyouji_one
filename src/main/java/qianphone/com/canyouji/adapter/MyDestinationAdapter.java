package qianphone.com.canyouji.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import qianphone.com.canyouji.R;
import qianphone.com.qftravel.bean.Destination;

/**
 * Created by Admin on 2016/12/6.
 */

public class MyDestinationAdapter extends BaseAdapter {

    private HashMap<Integer, ArrayList<Destination>> destinations = new HashMap<>();
    private HashMap<Integer, String> areaData = new HashMap<>();
    private ArrayList<MyItemGridAdapter> adapters = new ArrayList<>();

    public void setData(HashMap<Integer, ArrayList<Destination>> destinations) {

        areaData.put(1, "国外.亚洲");
        areaData.put(2, "国外.欧洲");
        areaData.put(3, "美洲、大洋洲、非洲与南极洲");
        areaData.put(99, "国内.港澳台");
        areaData.put(999, "国内.大陆");

        this.destinations = destinations;
        Set<Map.Entry<Integer, ArrayList<Destination>>> set = destinations.entrySet();
        for (Map.Entry<Integer, ArrayList<Destination>> entry : set) {
            ArrayList<Destination> data = entry.getValue();
            adapters.add(new MyItemGridAdapter(data));
        }
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return adapters.size();
    }

    @Override
    public MyItemGridAdapter getItem(int position) {
        return adapters.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.destination_list_item, parent, false);
        }
        GridView gridView = (GridView) convertView.findViewById(R.id.destination_gridview);
        MyItemGridAdapter adapter = adapters.get(position);
        gridView.setAdapter(adapter);
        TextView areaTv = (TextView) convertView.findViewById(R.id.area_tv);

        Destination destination = adapter.getItem(0);
        areaTv.setText(areaData.get(destination.getCategory()));
        return convertView;
    }
}
