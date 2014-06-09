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

package org.mmaug.bf101.model;

import org.mmaug.bf101.Config;

import java.util.List;

import retrofit.http.GET;

/**
 * Created by yemonkyaw on 6/7/14.
 */

public class ShopClient {

    public static class Shop {
        public String name;
        public String address;
        public List<String> feature_food;
        public Double latitude;
        public Double longitude;
        public Boolean newshop;

    }


    public interface ShopList {
        @GET(Config.SHOP_URL)
        List<Shop> getAllShop();
    }
}
