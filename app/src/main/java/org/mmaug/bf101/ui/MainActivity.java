package org.mmaug.bf101.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import org.mmaug.bf101.Config;
import com.yelinaung.bf101.app.R;

import org.mmaug.bf101.adapter.ShopListAdapter;
import org.mmaug.bf101.model.ShopClient;

import java.util.ArrayList;
import java.util.List;
import retrofit.RestAdapter;

public class MainActivity extends ActionBarActivity {
  @InjectView(R.id.listView) ListView shopListView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.inject(this);
    fetchTask fetchTask = new fetchTask();
    fetchTask.execute();
  }

  private class fetchTask extends AsyncTask<String, Void, List<ShopClient.Shop>> {

    @Override protected List<ShopClient.Shop> doInBackground(String... params) {
      // ToDo Need to catch Connection Problem Exception
      RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Config.BASE_URL).build();
      ShopClient.ShopList shopClient = restAdapter.create(ShopClient.ShopList.class);
      return shopClient.getAllShop();
    }

    @Override protected void onPostExecute(List<ShopClient.Shop> result) {
      super.onPostExecute(result);
      ArrayList<ShopClient.Shop> items = new ArrayList<ShopClient.Shop>();

      for (ShopClient.Shop t : result) {
        items.add(t);
      }

      ShopListAdapter itemsAdapter = new ShopListAdapter(getApplicationContext(), items);
      shopListView.setAdapter(itemsAdapter);
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    return id == R.id.action_settings || super.onOptionsItemSelected(item);
  }
}
