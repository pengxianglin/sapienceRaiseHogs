/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, VerCompat 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.newhope.smartpig.base.cache.serializer;

import com.google.gson.Gson;

//import javax.inject.Inject;
//import javax.inject.Singleton;

public class JsonSerializer {

  private final Gson gson = new Gson();
  
  public JsonSerializer() {}

  public <T> String serialize(T t) {
    String jsonString = gson.toJson(t);
    return jsonString;
  }

  public <T> T deserialize(String jsonString, Class<T> type) {
    T userEntity = gson.fromJson(jsonString, type);
    return userEntity;
  }
}
