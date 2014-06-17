/*
 * Copyright 2014 Ye Lin Aung
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

package org.mmaug.bf101.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ye Lin Aung on 14/04/14.
 */
public class SharePrefUtils {
  private static SharePrefUtils pref;
  protected SharedPreferences mSharePreferences;
  protected SharedPreferences.Editor mEditor;

  public SharePrefUtils(Context context) {
    mSharePreferences = context.getSharedPreferences("CPref", 0);
    mEditor = mSharePreferences.edit();
  }

  public static SharePrefUtils getInstance(Context context) {
    if (pref == null) {
      pref = new SharePrefUtils(context);
    }
    return pref;
  }

  public boolean isFirstTime() {
    return mSharePreferences.getBoolean("firstTime", true);
  }

  public void noMoreFirstTime() {
    mEditor.putBoolean("firstTime", false).commit();
  }
}
