/*
 * Copyright 2014 MMAUG (Myanmar Android User Group)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mmaug.bf101.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.yelinaung.bf101.app.R;

import org.mmaug.bf101.Config;
import org.mmaug.bf101.adapter.ShopListAdapter;
import org.mmaug.bf101.model.ShopClient;
import org.mmaug.bf101.utils.NetUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import retrofit.RestAdapter;

public class MainActivity extends ActionBarActivity {
    @InjectView(R.id.listView)
    ListView shopListView;
    Activity mActivity;

    @OnItemClick(R.id.listView)
    public void toDetailActivity()
    {
        Intent intentToDetail = new Intent(this, DetailActivity.class);
        startActivity(intentToDetail);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;
        ButterKnife.inject(this);

        if (NetUtils.isOnline(getApplicationContext())) {
            fetchTask fetchTask = new fetchTask();
            fetchTask.execute();
        }

    }


    private class fetchTask extends AsyncTask<String, Void, List<ShopClient.Shop>> {

        @Override
        protected List<ShopClient.Shop> doInBackground(String... params) {
            // ToDo Need to catch Connection Problem Exception
            RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Config.BASE_URL).build();
            ShopClient.ShopList shopClient = restAdapter.create(ShopClient.ShopList.class);
            return shopClient.getAllShop();
        }

        @Override
        protected void onPostExecute(List<ShopClient.Shop> result) {
            super.onPostExecute(result);
            ArrayList<ShopClient.Shop> items = new ArrayList<ShopClient.Shop>();

            for (ShopClient.Shop t : result) {
                items.add(t);
            }

            ShopListAdapter itemsAdapter = new ShopListAdapter(mActivity, items);
            itemsAdapter.notifyDataSetChanged();
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
