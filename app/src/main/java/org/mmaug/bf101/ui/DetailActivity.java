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

import android.content.Context;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
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
  @InjectView(R.id.action_bar) TextView actionBarText;
  @InjectView(R.id.Shop_Address) TextView shopAddressTextView;

  private MapView map;
  private Typeface font;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActionBar actionBar = getSupportActionBar();

    actionBar.setHomeButtonEnabled(true);
    actionBar.setDisplayShowCustomEnabled(true);
    actionBar.setCustomView(R.layout.actionbar);
    actionBar.setDisplayHomeAsUpEnabled(true);

    setContentView(R.layout.activity_detail);

    ButterKnife.inject(this);
    MapsInitializer.initialize(this);

    map = (MapView) findViewById(R.id.mapView);
    map.onCreate(savedInstanceState);
    map.onResume();

    final LatLng yangon = new LatLng(16.774745, 96.150649);

    map.getMap().getUiSettings().setMyLocationButtonEnabled(false);
    map.getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(yangon, 10));
    map.getMap().setMyLocationEnabled(true);

    font = Typeface.createFromAsset(this.getAssets(), "fonts/zawgyi.ttf");

    String shopName = " " + getIntent().getStringExtra(getString(R.string.extra_shopname));
    String shopAddress = getIntent().getStringExtra(getString(R.string.extra_shopaddress));

    shopNameTextView.setText(shopName);
    shopAddressTextView.setText(shopAddress);
    shopNameTextView.setTypeface(font);
    actionBarText.setText(shopName);
    actionBarText.setTypeface(font);

    IconGenerator iconFactory = new IconGenerator(this);
    iconFactory.setStyle(IconGenerator.STYLE_WHITE);

    Bundle b = this.getIntent().getExtras();
    String[] latArray = b.getStringArray("lat");
    String[] lngArray = b.getStringArray("lng");
    String[] branchArray = b.getStringArray("branch");

    //for generating marker
    //To Get User Current Location from GPS
    LocationManager locationManager =
        (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    double lat =
        locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLatitude();
    double lng =
        locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLongitude();

    android.location.Location location = new android.location.Location("current");
    location.setLatitude(lat);
    location.setLongitude(lng);
    Double[] distance = new Double[latArray.length];

    for (int count = 0; count < latArray.length; count++) {
      addIcon(iconFactory, branchArray[count],
          new LatLng(Double.parseDouble(latArray[count]), Double.parseDouble(lngArray[count])),
          "id");

      android.location.Location locationShop = new android.location.Location("current");
      locationShop.setLatitude(Double.parseDouble(latArray[count]));
      locationShop.setLongitude(Double.parseDouble(lngArray[count]));
      distance[count] = (double) location.distanceTo(locationShop);
    }

    // TODO we need to show distance from user current location and convert to kilometer
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
    ViewGroup.LayoutParams layoutParams =
        new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);
    mmTextMapView.setLayoutParams(layoutParams);
    mmTextMapView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
    mmTextMapView.setTextColor(getResources().getColor(R.color.text_color));
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
