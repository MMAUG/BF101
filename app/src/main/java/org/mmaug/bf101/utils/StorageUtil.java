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

package org.mmaug.bf101.utils;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class StorageUtil {

  private Context mContext;

  public static StorageUtil getInstance(Context context) {
    return new StorageUtil(context);
  }

  private StorageUtil(Context context) {
    mContext = context;
  }

  public Object ReadArrayListFromSD(String filename) {
    try {
      FileInputStream fis = mContext.openFileInput(filename + ".dat");
      ObjectInputStream ois = new ObjectInputStream(fis);
      Object obj = ois.readObject();

      fis.close();
      return obj;

    } catch (Exception e) {

      return new ArrayList<Object>();
    }
  }

  public <E> void SaveArrayListToSD(String filename, ArrayList<E> list) {
    try {
      FileOutputStream fos = mContext.openFileOutput(filename + ".dat", Context.MODE_PRIVATE);
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(list);
      fos.close();
    } catch (Exception e) {
      Log.e("Exception", e.toString());
    }
  }


}
