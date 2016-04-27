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

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class AvailableBuses extends ActionBarActivity {

    private Firebase mFirebaseObject ;
    private ListView mBusesList;
    private BusAdapter mBusAdapter;
    private ProgressDialog mProgressDialog;

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

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please Wait..");
        mProgressDialog.setCancelable(false);
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
                    mProgressDialog.dismiss();
                    e.printStackTrace();
                }
            }

            @Override public void onCancelled(FirebaseError error) {
                mProgressDialog.dismiss();
            }
        });
    }

    private void getBusesDataFromJson(String busesJsonStr)
            throws JSONException {

        //CREATE new array from buses class
        List<Bus> busesResults=new ArrayList<>();

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

        double bus_number,num_of_buses,price;
        String arrival,start_time,interval_time,stations,trip_duration,stationName;

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
            trip_duration = bus.getString(TRIP_DURATION_KEY);
            stationName = bus.getString(STATIONS_NAMES_KEY);

            //get time and waiting and distance
            PerformBusCall(stations,stationName);

            busesResults.add(new Bus(bus_number,interval_time,num_of_buses,price,start_time,stations,trip_duration));
        }

        mBusAdapter = new BusAdapter(this,R.layout.bus_item,busesResults);
        mBusesList.setAdapter(mBusAdapter);

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

    public void PerformBusCall(String stations,String stationNames){

        String [] stationsCord = stations.split("\\|", -1);
        String [] stationsNames = stationNames.split("\\|", -1);

        int sourceIndex = arrayPosition(stationsNames,mSOurce);
        int destinationIndex = arrayPosition(stationsNames,mDestination);

        String origin = stationsCord[sourceIndex];
        String destination = stationsCord[destinationIndex];
        String wayPoints="";
        for(int i=sourceIndex+1;i<destinationIndex;i++){
            wayPoints+=stationsCord[i]+"|";
        }
        wayPoints = removeLastChar(wayPoints);

        origin=origin.replace("-",",");
        destination=destination.replace("-",",");
        wayPoints=wayPoints.replace("-",",");

        ServiceBuilder Builder = new ServiceBuilder();
        API.Routes service =Builder.BuildMovies();
        service.getRoutes(origin,destination,wayPoints,Constants.API_KRY, new retrofit.Callback<RouteObject>() {
            @Override
            public void success(RouteObject routeObject, Response response) {
               // routeObject.routes.get(0).legs
            }

            @Override
            public void failure(RetrofitError error) {

                Toast.makeText(AvailableBuses.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        });

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

    public String removeLastChar(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length()-1)=='x') {
            str = str.substring(0, str.length()-1);
        }
        return str;
    }
}
