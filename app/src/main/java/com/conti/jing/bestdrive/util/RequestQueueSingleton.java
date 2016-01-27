package com.conti.jing.bestdrive.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;

public class RequestQueueSingleton {
    private static Context mContext;
    private ImageLoader mImageLoader;
    private RequestQueue mRequestQueue;
    private Cache mRequestCache;

    private static class RequestQueueHolder {
        private static final RequestQueueSingleton INSTANCE = new RequestQueueSingleton();
    }

    private RequestQueueSingleton() {
        mRequestCache = getRequestCache();
        mRequestQueue = getRequestQueue();
        mImageLoader = getImageLoader();
    }

    public static RequestQueueSingleton getInstance(Context context) {
        if (mContext == null) {
            mContext = context;
        }
        return RequestQueueHolder.INSTANCE;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
//            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
            HurlStack hurlStack = new HurlStack();
            Network network = new BasicNetwork(hurlStack);
            mRequestQueue = new RequestQueue(mRequestCache, network);
        }
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        if (mImageLoader == null) {
            ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache() {
                private final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(20);

                @Override
                public Bitmap getBitmap(String url) {
                    return lruCache.get(url);
                }

                @Override
                public void putBitmap(String url, Bitmap bitmap) {
                    lruCache.put(url, bitmap);
                }
            };
            mImageLoader = new ImageLoader(mRequestQueue, imageCache);
        }
        return mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }

    public Cache getRequestCache() {
        if (mRequestCache == null) {
            mRequestCache = new DiskBasedCache(mContext.getCacheDir(), 1024 * 1024);
        }
        return mRequestCache;
    }

    public boolean destroyRequestQueue(RequestQueue requestQueue, final Object requestTag, Cache requestCache){
        if (requestQueue != null) {
            requestQueue.cancelAll(requestTag);
            requestCache.clear();
            requestQueue.stop();
            return true;
        }
        return false;
    }
}
