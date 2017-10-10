/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p/>
 * Licensed under the Apache License, VerCompat 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.pengxl.sapienceraisehogs.base.cache;

public interface ICache {

    static final String KEY_PREFIX = "cache_";

    CacheEntity get(String key);

    void put(CacheEntity entity);

    boolean isCached(String key);

    boolean isExpired();

    void evictAll();

    class Factory{
        public static ICache build(){
            return new SQLCacheImpl();
        }
        public static String buildKey(String param){
            return KEY_PREFIX+param;
        }
    }
}
