package com.ahmeddonkl.GP;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;

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

        //make list of sources
        sourceList.add("Duki");
        sourceList.add("Elmohandseen");

        //make list of destination
        destinationList.add("Elsayada_aisha");
        destinationList.add("Ben_elsarayat");

        //make default array adapter of spinner source
        ArrayAdapter<String> sourceAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,sourceList);
        sourceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSourceSpin.setAdapter(sourceAdapter);

        //make default array adapter of spinner destination
        ArrayAdapter<String> destinationAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,destinationList);
        destinationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDestinationSpin.setAdapter(destinationAdapter);

        mButtonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the source and destination selected by user
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
