package org.mmaug.bf101.api;

import android.content.Context;

import org.mmaug.bf101.Config;

import retrofit.RestAdapter;

/**
 * Created by SH on 14/Jun/2014.
 */
public class ShopAPI {
  private static ShopAPI mInstance;
  private Context mContext;
  private ShopService mService;

  public ShopAPI(Context context) {
    this.mContext = context;

    final RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.BASIC).setEndpoint(Config.BASE_URL).build();
    mService = restAdapter.create(ShopService.class);
  }

  public static ShopAPI getInstance(Context context) {
    if (mInstance == null) {
      mInstance = new ShopAPI(context);
    }
    return mInstance;
  }

  public ShopService getService() {
    return mService;
  }
}
