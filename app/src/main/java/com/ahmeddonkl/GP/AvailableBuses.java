package com.ahmeddonkl.GP;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.ahmeddonkl.GP.Service.API.API;
import com.ahmeddonkl.GP.Service.API.ServiceBuilder;
import com.ahmeddonkl.GP.Service.Model.RouteObject;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class AvailableBuses extends ActionBarActivity {

    private Firebase mFirebaseObject ;
    private ListView mBusesList;
    private BusAdapter mBusAdapter;
    private ProgressDialog mProgressDialog;

    static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    String mSOurce,mDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_buses);


        if(getIntent().hasExtra("destination")){
            mSOurce = getIntent().getStringExtra("source");
            mDestination = getIntent().getStringExtra("destination");
        }

        //linking list view
        mBusesList = (ListView)findViewById(R.id.buses_list);

        //CREATE new array from buses class
        List<Bus> busesResults=new ArrayList<>();
        mBusAdapter = new BusAdapter(this,R.layout.bus_item,busesResults);
        mBusesList.setAdapter(mBusAdapter);

        //initialize firebase
        Firebase.setAndroidContext(this);
        //connect to my firebase on server
        mFirebaseObject = new Firebase("https://brilliant-inferno-6981.firebaseio.com/");

        // list of buses go from source to destination
        if(mSOurce!=null)
        GetBuses(mSOurce,mDestination);
    }

    /**
     *
     * @param source
     * @param destination
     */
    private void GetBuses(String source , String destination){

        //show progress bar
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please Wait..");
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();

        //here i can access destination directly on firebase with ( child().child() )
        mFirebaseObject.child(source).child(destination).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String mRequestResult = snapshot.getValue().toString();
                System.out.println(mRequestResult);
                //fill my array from returned json string
                try {
                    getBusesDataFromJson(mRequestResult);
                } catch (JSONException e) {
                    Toast.makeText(AvailableBuses.this,"Error Parsing Data!",Toast.LENGTH_SHORT).show();
                    mProgressDialog.dismiss();
                    e.printStackTrace();
                }
            }

            @Override public void onCancelled(FirebaseError error) {
                Toast.makeText(AvailableBuses.this,"Check Your Internet Connection",Toast.LENGTH_SHORT).show();
                mProgressDialog.dismiss();
            }
        });
    }

    private void getBusesDataFromJson(String busesJsonStr)
            throws JSONException {

        // These are the names of the JSON objects that need to be extracted.
        final String BUSES_ARRAY = "buses";
        final String BUS_NUMBER_KEY = "bus_number";
        final String INTERVAL_KEY = "interval_time";
        final String NUM_OF_BUSES_KEY = "num_of_buses";
        final String PRICE_KEY = "price";
        final String START_KEY = "start_time";
        final String STATIONS_KEY = "stations";
        final String STATIONS_NAMES_KEY = "station_names";
        final String TRIP_DURATION_KEY = "trip_duration";

        double bus_number,num_of_buses,price,trip_duration;
        String arrival,start_time,interval_time,stations,stationName;

        //will contain the big object of string
        JSONObject busesJson = new JSONObject(busesJsonStr);

        //represent array of buses passes on this destination
        JSONArray BusesArray = busesJson.getJSONArray(BUSES_ARRAY);

        for (int j = 0; j < BusesArray.length(); j++){
            JSONObject bus = BusesArray.getJSONObject(j);

            //get data from object
            bus_number = bus.getDouble(BUS_NUMBER_KEY);
            interval_time = bus.getString(INTERVAL_KEY);
            num_of_buses = bus.getDouble(NUM_OF_BUSES_KEY);
            price = bus.getDouble(PRICE_KEY);
            start_time = bus.getString(START_KEY);
            stations = bus.getString(STATIONS_KEY);
            trip_duration = bus.getDouble(TRIP_DURATION_KEY);
            stationName = bus.getString(STATIONS_NAMES_KEY);

            Bus bus1 =new Bus(bus_number,interval_time,num_of_buses,price,start_time,stations,trip_duration,stationName);
            //get time and waiting and distance
            PerformBusCall(bus1);
        }
        mProgressDialog.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public int calcWaiting(Double overallDuration , String startTime ,Double duration ){

        String [] startArr = startTime.split("\\.", -1);

        Date start = new Date();
        start.setHours(Integer.valueOf(startArr[0]));
        start.setMinutes(Integer.valueOf(startArr[1]));

        Date now = new Date();

        Date startPlusOver = new Date();
        Date startPlusDur = new Date();
        while (true){
            startPlusOver.setHours(start.getHours());
            startPlusOver.setMinutes(start.getMinutes() + overallDuration.intValue());

            if(start.before(now) && now.before(startPlusOver)){
                startPlusDur.setHours(start.getHours());
                startPlusDur.setMinutes(start.getMinutes()+duration.intValue());
                if(now.before(startPlusDur)){
                    return  startPlusDur.getMinutes() - now.getMinutes();
                }
                else{
                    return  ((60 - now.getMinutes()) + duration.intValue());
                }
            }
            start.setMinutes(start.getMinutes()+overallDuration.intValue());
        }
    }

    public void PerformBusCall(final Bus bus){

        String [] stationsCord = bus.getmStationsCoordinates().split("\\|", -1);
        String [] stationsNames = bus.getmStations().split("\\|", -1);

        final int sourceIndex = arrayPosition(stationsNames,mSOurce);
        final int destinationIndex = arrayPosition(stationsNames,mDestination);

        String origin = stationsCord[0];
        String srcDesStations = "";
        String destination = stationsCord[stationsCord.length-1];
        String wayPoints="";
        for(int i=1;i<stationsCord.length-1;i++){
            wayPoints+=stationsCord[i]+"|";
        }

        for(int i=sourceIndex;i<=destinationIndex;i++){
            srcDesStations+=stationsNames[i]+" - ";
        }
        srcDesStations = srcDesStations.substring(0,srcDesStations.length()-1);
        wayPoints = wayPoints.substring(0,wayPoints.length()-1);

        origin=origin.replace("-",",");
        destination=destination.replace("-",",");
        wayPoints=wayPoints.replace("-",",");

        ServiceBuilder Builder = new ServiceBuilder();
        API.Routes service =Builder.BuildRoutes();
        final String finalSrcDesStations = srcDesStations;
        service.getRoutes(origin,destination,wayPoints,Constants.API_KRY, new retrofit.Callback<RouteObject>() {
            @Override
            public void success(RouteObject routeObject, Response response) {
                Double stationSourceDuration = getDisAndTime(routeObject,sourceIndex,destinationIndex,bus);

                bus.setWaiting(String.valueOf(calcWaiting(bus.getmTripDuration(),bus.getmStartTime(),stationSourceDuration))+" minutes");
                bus.setSouDestStations(finalSrcDesStations);
                mBusAdapter.add(bus);
                mBusAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(AvailableBuses.this,"Check Your Internet Connection",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private Double getDisAndTime(RouteObject routeObject,int sourceIndex,int destinationIndex,Bus bus) {

        Double duration =0.0, distance=0.0 ,stationSourceDuration=0.0;
        for(int i =0; i<routeObject.routes.get(0).legs.size();i++){
            RouteObject.Leg leg = routeObject.routes.get(0).legs.get(i);

            if( i < sourceIndex)
                stationSourceDuration += leg.duration.value;

            if(i >= sourceIndex && i<destinationIndex){
                duration += leg.duration.value;
                distance += leg.distance.value;
            }
        }

        distance = round(distance/1000,2);
        bus.setDistance(String.valueOf(distance)+" km");
        bus.setDuration(String.valueOf(duration.intValue()/60)+" minute");

        stationSourceDuration = stationSourceDuration/60;
        return stationSourceDuration;
    }

    int arrayPosition(String [] stationsName , String station){
        int index = -1;
        for (int i=0;i<stationsName.length;i++) {
            if (stationsName[i].equals(station)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
