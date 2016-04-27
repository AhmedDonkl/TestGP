package com.ahmeddonkl.GP;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


// our ViewHolder.
// caches our views
class ViewHolder
{
    TextView mBusNum ;
    //TextView mBusPrice ;
    TextView mBUSPrice ;
    TextView mBusStations ;
}

public class BusAdapter extends ArrayAdapter<Bus> {

    Activity activity;
    int layoutResourceId;
    List<Bus> Buses = null;

    public BusAdapter(Activity activity,int layoutResourceId,List<Bus> Buses) {
        super(activity,layoutResourceId,Buses);
        this.activity = activity;
        this.layoutResourceId = layoutResourceId;
        this.Buses=Buses;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder  holder = null;

        if (convertView == null) {
            // inflate the layout
            LayoutInflater inflater = ((Activity)activity).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);

            // well set up the ViewHolder
            holder = new ViewHolder();
            holder.mBusNum = (TextView) convertView.findViewById(R.id.item_bus_num);
            //holder.mBusPrice = (TextView) convertView.findViewById(R.id.item_bus_price);

            holder.mBUSPrice = (TextView) convertView.findViewById(R.id.item_bus_arr);
            holder.mBusStations = (TextView) convertView.findViewById(R.id.item_bus_int);
            // store the holder with the view.
            convertView.setTag(holder);
        }
        else
        {
            // we've just avoided calling findViewById() on resource every time
            // just use the viewHolder
            holder = (ViewHolder) convertView.getTag();
        }
        Bus obj = Buses.get(position);
        holder.mBusNum.setText(String.valueOf(obj.getmNumber()));
        //holder.mBusPrice.setText(String.valueOf(obj.getmPrice()));
        holder.mBUSPrice.setText(String.valueOf(obj.getmPrice()));
        holder.mBusStations.setText(String.valueOf(obj.getmStations()));

        return convertView;
    }
}


