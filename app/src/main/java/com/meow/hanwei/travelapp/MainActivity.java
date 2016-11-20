package com.meow.hanwei.travelapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText hotel = (EditText) findViewById(R.id.hotel);
        final EditText budget = (EditText) findViewById(R.id.budget);
        Button start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent plan = new Intent(MainActivity.this, Search.class);
                // Todo: add stuff to the bundle
                plan.putExtra("Hotel", hotel.getText().toString());
                plan.putExtra("Budget", budget.getText().toString());
                startActivity(plan);
            }
        });
    }
}