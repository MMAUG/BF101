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

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yelinaung.bf101.app.R;

import org.mmaug.bf101.adapter.FeatureFoodListAdapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class DetailActivity extends ActionBarActivity {

    @InjectView(R.id.list)
    ListView featureListView;

    @InjectView(R.id.headerText)
    TextView TitleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        List<String> featureFoodlist = getIntent().getStringArrayListExtra("Feature");
        String feature[] = new String[featureFoodlist.size()];
        int i =0;
        ButterKnife.inject(this);
        while (i<featureFoodlist.size()) {
            feature[i]=featureFoodlist.get(i);
            i++;
        }
        View headerView = ((LayoutInflater)this.getSystemService(this.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.header, null, false);
        FeatureFoodListAdapter featureFoodListAdapter = new FeatureFoodListAdapter(this,feature);
        featureListView.setAdapter(featureFoodListAdapter);
        featureListView.addHeaderView(headerView);

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
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }
}
