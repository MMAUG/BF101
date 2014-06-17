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
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.ArrayList;
import java.util.List;
import org.mmaug.bf101.R;
import org.mmaug.bf101.adapter.ShopListAdapter;
import org.mmaug.bf101.api.ShopAPI;
import org.mmaug.bf101.model.Shop;
import org.mmaug.bf101.utils.NetUtils;
import org.mmaug.bf101.utils.SharePrefUtils;
import org.mmaug.bf101.utils.StorageUtil;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends ActionBarActivity {
  @InjectView(R.id.listView) ListView shopListView;
  @InjectView(R.id.progressBar) ProgressBar mProgressBar;
  @InjectView(R.id.emptyView) View emptyView;
  @InjectView(R.id.loadingText) TextView loadingText;
  @InjectView(R.id.btnretry) Button retry;
  private Activity mActivity;
  private ArrayList<Shop> items;
  private StorageUtil storageUtil;
  private SharePrefUtils sharePrefUtils;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mActivity = this;
    ButterKnife.inject(this);
    storageUtil = StorageUtil.getInstance(this);
    sharePrefUtils = SharePrefUtils.getInstance(this);

    shopListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intentToDetail = new Intent(getApplicationContext(), DetailActivity.class);
        List<String> featureFood = items.get(position).feature_food;
        String[] branch = new String[items.get(position).locations.size()];
        String[] lat = new String[items.get(position).locations.size()];
        String[] lng = new String[items.get(position).locations.size()];
        for (int count = 0; count < items.get(position).locations.size(); count++) {
          branch[count] = items.get(position).locations.get(count).branch;
          lat[count] = items.get(position).locations.get(count).lat.toString();
          lng[count] = items.get(position).locations.get(count).lng.toString();
        }
        intentToDetail.putExtra("shopname", items.get(position).name);
        Bundle b = new Bundle();
        b.putStringArray("branch", branch);
        b.putStringArray("lat", lat);
        b.putStringArray("lng", lng);
        intentToDetail.putExtras(b);
        intentToDetail.putExtra("shopaddress", items.get(position).address);
        intentToDetail.putStringArrayListExtra("Feature", (ArrayList<String>) featureFood);
        startActivity(intentToDetail);
      }
    });
  }

  @Override protected void onResume() {
    super.onResume();
    if (sharePrefUtils.isFirstTime()) {
      loadData();
      sharePrefUtils.noMoreFirstTime();
    }
  }

  private void loadData() {
    if (NetUtils.isOnline(getApplicationContext())) {
      emptyView.setVisibility(View.VISIBLE);
      mProgressBar.setVisibility(View.VISIBLE);
      shopListView.setVisibility(View.GONE);
      loadingText.setVisibility(View.VISIBLE);

      ShopAPI.getInstance(this).getService().getAllShop(new Callback<List<Shop>>() {
        @Override
        public void success(List<Shop> result, Response response) {
          items = new ArrayList<Shop>();
          for (Shop shop : result) {
            items.add(shop);
          }
          storageUtil.SaveArrayListToSD("shop", items);
          mProgressBar.setVisibility(View.GONE);
          emptyView.setVisibility(View.GONE);
          loadingText.setVisibility(View.GONE);
          shopListView.setVisibility(View.VISIBLE);
          ShopListAdapter itemsAdapter = new ShopListAdapter(getApplicationContext(), items);
          itemsAdapter.notifyDataSetChanged();
          shopListView.setAdapter(itemsAdapter);
        }

        @Override
        public void failure(RetrofitError error) {
          Toast.makeText(getApplicationContext(), "Check your internet connection...",
              Toast.LENGTH_SHORT).show();
          retry.setVisibility(View.VISIBLE);
        }
      });
    } else {
      shopListView.setVisibility(View.VISIBLE);
      items = (ArrayList<Shop>) storageUtil.ReadArrayListFromSD("shop");
      ShopListAdapter itemsAdapter = new ShopListAdapter(getApplicationContext(), items);
      itemsAdapter.notifyDataSetChanged();
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
    switch (id) {
      case R.id.action_refresh:
        loadData();
        break;
    }
    return super.onOptionsItemSelected(item);
  }
}
