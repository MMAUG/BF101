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

import com.yelinaung.bf101.app.R;

import org.mmaug.bf101.model.ShopClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * This file is created by Nyan Lynn Htut
 * for BF101 on 6/8/2014.
 */

public class ShopListAdapter extends BaseAdapter {

    private final Context mContext;
    private List<ShopClient.Shop> shops;

    public ShopListAdapter(Context context, ArrayList<ShopClient.Shop> shops) {
        this.mContext = context;
        this.shops = shops;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater mInflater =
                (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = mInflater.inflate(R.layout.shop_list_item, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        ShopClient.Shop model = getItem(position);
        holder.name.setText(model.name);
        Typeface font = Typeface.createFromAsset(mContext.getAssets(), "fonts/zawgyi.ttf");
        holder.name.setTypeface(font);
        holder.address.setText(model.address);
        holder.address.setTypeface(font);
        if(model.newshop == true){
            holder.newshop.setVisibility(View.VISIBLE);
        }
        return view;
    }

    @Override
    public int getCount() {
        return shops.size();
    }

    @Override
    public ShopClient.Shop getItem(int position) {
        return shops.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        @InjectView(R.id.shop_name)
        TextView name;
        @InjectView(R.id.shop_address)
        TextView address;
        @InjectView(R.id.newshop)
        TextView newshop;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
