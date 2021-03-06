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

package org.mmaug.bf101.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.mmaug.bf101.R;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class FeatureFoodListAdapter extends BaseAdapter {
    Context mContext;
    Typeface font;
    private String[] featureFood;

    public FeatureFoodListAdapter(Context context, String[] featureFood) {
        this.featureFood = featureFood;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return featureFood.length;
    }

    @Override
    public Object getItem(int position) {
        return featureFood[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater mInflater =
                (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = mInflater.inflate(R.layout.feature_list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder.fetureFoodName.setText(featureFood[position]);
        font = Typeface.createFromAsset(mContext.getAssets(), "fonts/zawgyi.ttf");
        holder.fetureFoodName.setTypeface(font);
        return convertView;
    }


    static class ViewHolder {
        @InjectView(R.id.featureFoodName)
        TextView fetureFoodName;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}

