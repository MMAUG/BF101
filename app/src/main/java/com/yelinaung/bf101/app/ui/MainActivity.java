package com.yelinaung.bf101.app.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yelinaung.bf101.app.Config;
import com.yelinaung.bf101.app.model.ShopClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.RestAdapter;
import com.yelinaung.bf101.app.R;

public class MainActivity extends ActionBarActivity {
    @InjectView(R.id.listView)
    ListView shopListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        fetchTask fetchTask = new fetchTask();
        fetchTask.execute();


    }

    private class fetchTask extends AsyncTask<String, Void, List<ShopClient.Shop>> {

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
            List<String> item = new ArrayList<String>();

            for (ShopClient.Shop t : result) {
                item.add(t.name.toString());
            }
            //ToDo replace with ListeItemAdapter
            ArrayAdapter<String> itemsAdapter =
                    new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, item);
            shopListView.setAdapter(itemsAdapter);
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
