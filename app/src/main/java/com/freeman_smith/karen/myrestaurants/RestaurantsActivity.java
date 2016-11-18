package com.freeman_smith.karen.myrestaurants;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;

public class RestaurantsActivity extends AppCompatActivity {
    private static final String TAG = RestaurantsActivity.class.getSimpleName();
    @Bind(R.id.locationTextView) TextView mLocationTextView;
    @Bind(R.id.listView) ListView mListView;

    public ArrayList<Restaurant> mRestaurants = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        String location = intent.getStringExtra("location");
        getRestaurants(location);
        mLocationTextView.setText("Here are all the restaurants near: " + location);

    }

    private void getRestaurants(String location) {
        final YelpService yelpService = new YelpService();
        yelpService.findRestaurants(location, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    mRestaurants = yelpService.processResults(response);
                    String jsonData = response.body().string();
                    Log.v(TAG, jsonData);


                    RestaurantsActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String[] restaurantNames = new String[mRestaurants.size()];
                            for(int i=0; i<restaurantNames.length; i++) {
                                restaurantNames[i] = mRestaurants.get(i).getName();
                            }

                            ArrayAdapter adapter = new ArrayAdapter(RestaurantsActivity.this, android.R.layout.simple_list_item_1, restaurantNames);
                            mListView.setAdapter(adapter);

                            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    String restaurant = ((TextView)view).getText().toString();
                                    Toast.makeText(RestaurantsActivity.this, restaurant, Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });


                } catch(IOException e) {
                    e.printStackTrace();
                }
            }

        });
    }
}
