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
    TextView mBusPrice ;
    TextView mBusStart ;
    TextView mBusStations ;
    TextView mBusWaiting ;
    TextView mBusDuration ;
    TextView mBusDistance;
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
            holder.mBusPrice = (TextView) convertView.findViewById(R.id.item_bus_price);
            holder.mBusDistance = (TextView) convertView.findViewById(R.id.item_bus_distance);
            holder.mBusDuration = (TextView) convertView.findViewById(R.id.item_bus_duration);
            holder.mBusStations = (TextView) convertView.findViewById(R.id.item_bus_stations);
            holder.mBusStart = (TextView) convertView.findViewById(R.id.item_bus_startTime);
            holder.mBusWaiting = (TextView) convertView.findViewById(R.id.item_bus_waiting);

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
        holder.mBusNum.setText(String.valueOf(obj.getmNumber().intValue()));
        holder.mBusPrice.setText(String.valueOf(obj.getmPrice())+" LE");
        holder.mBusStations.setText(obj.getSouDestStations());
        holder.mBusWaiting.setText(obj.getWaiting());
        holder.mBusStart.setText(obj.getmStartTime());
        holder.mBusDuration.setText(obj.getDuration());
        holder.mBusDistance.setText(obj.getDistance());

        return convertView;
    }


}


