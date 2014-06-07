package com.yelinaung.bf101.app.model;


import android.util.Log;

import com.google.gson.JsonArray;

import java.util.List;

import retrofit.http.GET;

/**
 * Created by yemonkyaw on 6/7/14.
 */

public class ShopClient {

   public static class Shop {
        public String name;
    }

    public interface ShopList {
        @GET("/shops.json")
        List<Shop> getAllShop();
    }
}
