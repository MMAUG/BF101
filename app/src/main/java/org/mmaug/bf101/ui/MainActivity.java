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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.mmaug.bf101.R;
import org.mmaug.bf101.adapter.ShopListAdapter;
import org.mmaug.bf101.api.ShopAPI;
import org.mmaug.bf101.model.Shop;
import org.mmaug.bf101.utils.NetUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends ActionBarActivity {
    @InjectView(R.id.listView)
    ListView shopListView;
    @InjectView(R.id.progressBar)
    ProgressBar mProgressBar;
    @InjectView(R.id.emptyView)
    View emptyView;
    @InjectView(R.id.loadingText)
    TextView loadingText;
    Activity mActivity;
    ArrayList<Shop> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;
        ButterKnife.inject(this);

        if (NetUtils.isOnline(getApplicationContext())) {
            emptyView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.VISIBLE);
            shopListView.setVisibility(View.VISIBLE);
            loadingText.setVisibility(View.VISIBLE);

            ShopAPI.getInstance(this).getService().getAllShop(new Callback<List<Shop>>() {
                @Override
                public void success(List<Shop> result, Response response) {
                    items = new ArrayList<Shop>();

                    for (Shop t : result) {
                        items.add(t);
                    }
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
                    Toast.makeText(getApplicationContext(), "Check your internet connection...", Toast.LENGTH_SHORT).show();
                }
            });
        }

        shopListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentToDetail = new Intent(getApplicationContext(), DetailActivity.class);
                List<String> featureFood = items.get(position).feature_food;
                intentToDetail.putExtra("shopname", items.get(position).name);
                intentToDetail.putExtra("shopaddress", items.get(position).address);
                intentToDetail.putStringArrayListExtra("Feature", (ArrayList<String>) featureFood);
                startActivity(intentToDetail);
            }
        });
    }
}
