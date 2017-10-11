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
package com.newhope.smartpig.base.cache;

import android.content.Context;


import com.newhope.smartpig.base.cache.serializer.JsonSerializer;

import java.io.File;

//import javax.inject.Inject;

public class CacheImpl implements ICache {

    private static final String SETTINGS_FILE_NAME = "SETTINGS";
    private static final String SETTINGS_KEY_LAST_CACHE_UPDATE = "last_cache_update";


    private static final long EXPIRATION_TIME = 60 * 10 * 1000;

    private final Context context;
    private final File cacheDir;
    private final JsonSerializer serializer;
    private final FileManager fileManager;

    /**
     * Constructor of the class {@link CacheImpl}.
     *
     * @param context             A
     * @param userCacheSerializer {@link JsonSerializer} for object serialization.
     * @param fileManager         {@link FileManager} for saving serialized objects to the file system.
     */
    //@Inject
    public CacheImpl(Context context, JsonSerializer userCacheSerializer,
                     FileManager fileManager) {
        if (context == null || userCacheSerializer == null || fileManager == null) {
            throw new IllegalArgumentException("Invalid null parameter");
        }
        this.context = context.getApplicationContext();
        this.cacheDir = this.context.getCacheDir();
        this.serializer = userCacheSerializer;
        this.fileManager = fileManager;
    }

    @Override
    public CacheEntity get(final String key) {

        File userEntityFile = CacheImpl.this.buildFile(key);
        String fileContent = CacheImpl.this.fileManager.readFileContent(userEntityFile);
        CacheEntity entity = CacheImpl.this.serializer.deserialize(fileContent,
                CacheEntity.class);
        return entity;
    }

    @Override
    public void put(CacheEntity cacheEntity) {
        String key = cacheEntity.getKey();
        if (cacheEntity != null) {
            File userEntitiyFile = this.buildFile(key);
            if (!isCached(key)) {
                String jsonString = this.serializer.serialize(cacheEntity);
                CacheWriter write = new CacheWriter(this.fileManager, userEntitiyFile,
                        jsonString);
                write.run();
                setLastCacheUpdateTimeMillis();
            }
        }
    }

    @Override
    public boolean isCached(String key) {
        File userEntitiyFile = this.buildFile(key);
        return this.fileManager.exists(userEntitiyFile);
    }

    @Override
    public boolean isExpired() {
        long currentTime = System.currentTimeMillis();
        long lastUpdateTime = this.getLastCacheUpdateTimeMillis();

        boolean expired = ((currentTime - lastUpdateTime) > EXPIRATION_TIME);

        if (expired) {
            this.evictAll();
        }

        return expired;
    }

    @Override
    public void evictAll() {
        CacheEvictor evictor = new CacheEvictor(this.fileManager, this.cacheDir);
        evictor.run();

    }

    private File buildFile(String key) {
        StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder.append(this.cacheDir.getPath());
        fileNameBuilder.append(File.separator);
        fileNameBuilder.append(key);

        return new File(fileNameBuilder.toString());
    }

    /**
     * Set in millis, the last time the cache was accessed.
     */
    private void setLastCacheUpdateTimeMillis() {
        long currentMillis = System.currentTimeMillis();
        this.fileManager.writeToPreferences(this.context, SETTINGS_FILE_NAME,
                SETTINGS_KEY_LAST_CACHE_UPDATE, currentMillis);
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private long getLastCacheUpdateTimeMillis() {
        return this.fileManager.getFromPreferences(this.context, SETTINGS_FILE_NAME,
                SETTINGS_KEY_LAST_CACHE_UPDATE);
    }

    /**
     * {@link Runnable} class for writing to disk.
     */
    private static class CacheWriter implements Runnable {
        private final FileManager fileManager;
        private final File fileToWrite;
        private final String fileContent;

        CacheWriter(FileManager fileManager, File fileToWrite, String fileContent) {
            this.fileManager = fileManager;
            this.fileToWrite = fileToWrite;
            this.fileContent = fileContent;
        }

        @Override
        public void run() {
            this.fileManager.writeToFile(fileToWrite, fileContent);
        }
    }

    /**
     * {@link Runnable} class for evicting all the cached files
     */
    private static class CacheEvictor implements Runnable {
        private final FileManager fileManager;
        private final File cacheDir;

        CacheEvictor(FileManager fileManager, File cacheDir) {
            this.fileManager = fileManager;
            this.cacheDir = cacheDir;
        }

        @Override
        public void run() {
            this.fileManager.clearDirectory(this.cacheDir);
        }
    }
}
