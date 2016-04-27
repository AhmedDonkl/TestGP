package com.ahmeddonkl.GP;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    private Button mButtonGo;
    private android.widget.Spinner mSourceSpin,mDestinationSpin;
    ArrayList<String>sourceList = new ArrayList<>();
    ArrayList<String>destinationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSourceSpin=(android.widget.Spinner)findViewById(R.id.source_spinner);
        mDestinationSpin=(android.widget.Spinner)findViewById(R.id.destination_spinner);
        mButtonGo = (Button)findViewById(R.id.butn_go);

        sourceList.add("Giza");

        destinationList.add("Gamaa");
        destinationList.add("Elsayada_aisha");

        ArrayAdapter<String> sourceAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,sourceList);
        sourceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSourceSpin.setAdapter(sourceAdapter);

        ArrayAdapter<String> destinationAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,destinationList);
        destinationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDestinationSpin.setAdapter(destinationAdapter);

        mButtonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String source = mSourceSpin.getSelectedItem().toString();
                String destination = mDestinationSpin.getSelectedItem().toString();

                Intent intent = new Intent(MainActivity.this,AvailableBuses.class)
                        .putExtra("source",source)
                        .putExtra("destination", destination);
                startActivity(intent);
            }
        });
    }
}
