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

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;
import java.util.List;
import org.mmaug.bf101.R;
import org.mmaug.bf101.adapter.FeatureFoodListAdapter;

public class DetailActivity extends ActionBarActivity {

  @InjectView(R.id.list) ListView featureListView;
  @InjectView(R.id.headerText) TextView shopNameTextView;
  @InjectView(R.id.action_bar) TextView actionBar;
  @InjectView(R.id.Shop_Address) TextView shopAddressTextView;

  String shopName;
  String shopAddress;
  MapView map;
  Typeface font;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getSupportActionBar().setHomeButtonEnabled(true);
    getSupportActionBar().setDisplayShowCustomEnabled(true);
    getSupportActionBar().setCustomView(R.layout.actionbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    setContentView(R.layout.activity_detail);
    ButterKnife.inject(this);
    try {
      MapsInitializer.initialize(this);
      map = (MapView) findViewById(R.id.mapView);
      map.onCreate(savedInstanceState);
      map.onResume();
      final LatLng yangon = new LatLng(16.774745, 96.150649);
      map.getMap().getUiSettings().setMyLocationButtonEnabled(false);
      map.getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(yangon, 10));
      map.getMap().setMyLocationEnabled(true);
    } catch (GooglePlayServicesNotAvailableException ignored) {
    }
    font = Typeface.createFromAsset(this.getAssets(), "fonts/zawgyi.ttf");
    shopName = " " + getIntent().getStringExtra("shopname");
    shopAddress = getIntent().getStringExtra("shopaddress");
    shopNameTextView.setText(shopName);
    shopAddressTextView.setText(shopAddress);
    shopNameTextView.setTypeface(font);
    actionBar.setText(shopName);
    actionBar.setTypeface(font);
    IconGenerator iconFactory = new IconGenerator(this);
    iconFactory.setStyle(IconGenerator.STYLE_GREEN);
    Bundle b = this.getIntent().getExtras();
    String[] latArray = b.getStringArray("lat");
    String[] lngArray = b.getStringArray("lng");
    String[] branchArray = b.getStringArray("branch");
    for (int count = 0; count < latArray.length; count++) {
      addIcon(iconFactory, branchArray[count],
          new LatLng(Double.parseDouble(latArray[count]), Double.parseDouble(lngArray[count])),
          "id");
    }

    shopAddressTextView.setTypeface(font);
    List<String> featureFoodList = getIntent().getStringArrayListExtra("Feature");
    String feature[] = new String[featureFoodList.size()];
    int i = 0;
    while (i < featureFoodList.size()) {
      feature[i] = featureFoodList.get(i);
      i++;
    }
    View headerView =
        ((LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.header,
            featureListView, false);
    FeatureFoodListAdapter featureFoodListAdapter = new FeatureFoodListAdapter(this, feature);
    featureListView.addHeaderView(headerView);
    featureListView.setAdapter(featureFoodListAdapter);
  }

  private void addIcon(IconGenerator iconFactory, String text, LatLng position, String id) {
    TextView mmTextMapView = new TextView(this);
    mmTextMapView.setTypeface(font);
    mmTextMapView.setText(text);
    iconFactory.setContentView(mmTextMapView);

    MarkerOptions markerOptions = new MarkerOptions().
        icon(BitmapDescriptorFactory.fromBitmap(
            iconFactory.makeIcon(mmTextMapView.getText().toString()))).
        position(position).
        snippet(id).
        anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());
    map.getMap().addMarker(markerOptions);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.detail, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    switch (item.getItemId()) {
      case android.R.id.home:
        this.onBackPressed();
        return false;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
