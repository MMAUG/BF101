package com.yelinaung.bf101.app;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.yelinaung.bf101.app.model.ShopClient;

import java.util.List;

import retrofit.RestAdapter;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TweetTask tweetTask = new TweetTask();
        tweetTask.execute();


    }

    private class TweetTask extends AsyncTask<String, Void, List<ShopClient.Shop>> {

        @Override
        protected List<ShopClient.Shop> doInBackground(String... params) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(Config.BASE_URL)
                    .build();
            ShopClient.ShopList shopClient = restAdapter.create(ShopClient.ShopList.class);
            return shopClient.getAllShop();
        }

        @Override
        protected void onPostExecute(List<ShopClient.Shop> result) {
            super.onPostExecute(result);
            for (ShopClient.Shop t : result) {
                Log.e("Shop", t.name);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }
}
