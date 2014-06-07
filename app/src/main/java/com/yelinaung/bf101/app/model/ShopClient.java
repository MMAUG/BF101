package com.yelinaung.bf101.app.model;

import com.yelinaung.bf101.app.Config;
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
    @GET(Config.SHOP_URL) List<Shop> getAllShop();
  }
}
