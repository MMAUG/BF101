package org.mmaug.bf101.api;

import org.mmaug.bf101.Config;
import org.mmaug.bf101.model.Shop;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by SH on 14/Jun/2014.
 */
public interface ShopService {
    @GET(Config.SHOP_URL)
    void getAllShop(Callback<List<Shop>> callback);
}
