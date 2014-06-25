package org.mmaug.bf101.api;

import android.content.Context;
import org.mmaug.bf101.Config;
import retrofit.RestAdapter;

/**
 * Created by SH on 14/Jun/2014.
 */
public class ShopAPI {
  private static ShopAPI mInstance;
  private ShopService mService;

  public ShopAPI() {

    final RestAdapter restAdapter =
        new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.BASIC)
            .setEndpoint(Config.BASE_URL)
            .build();
    mService = restAdapter.create(ShopService.class);
  }

  public static ShopAPI getInstance() {
    if (mInstance == null) {
      mInstance = new ShopAPI();
    }
    return mInstance;
  }

  public ShopService getService() {
    return mService;
  }
}
