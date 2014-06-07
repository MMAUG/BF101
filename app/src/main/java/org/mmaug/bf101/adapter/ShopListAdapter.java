package org.mmaug.bf101.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yelinaung.bf101.app.R;

import org.mmaug.bf101.model.ShopClient;

import java.util.ArrayList;
import java.util.List;

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

    @Override public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.shop_list_item, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) view.findViewById(R.id.shop_name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        ShopClient.Shop model = getItem(position);
        holder.name.setText(model.name);
        // ToDo Need to set Myanmar Font in TextView

        return view;
    }

    @Override public int getCount() {
        return shops.size();
    }

    @Override public ShopClient.Shop getItem(int position) {
        return shops.get(position);
    }

    @Override public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView name;
    }
}
