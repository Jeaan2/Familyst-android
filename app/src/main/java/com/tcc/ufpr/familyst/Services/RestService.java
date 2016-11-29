package com.tcc.ufpr.familyst.Services;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RestService {

        private static RestService mInstance;
        private RequestQueue mRequestQueue;
        private static Context mCtx;

        private RestService(Context context) {
            mCtx = context;
            mRequestQueue = getRequestQueue();
        }

        public static synchronized RestService getInstance(Context context) {
            if (mInstance == null) {
                mInstance = new RestService(context);
            }
            return mInstance;
        }

        public RequestQueue getRequestQueue() {
            if (mRequestQueue == null) {
                mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
            }
            return mRequestQueue;
        }

        public <T> void addToRequestQueue(Request<T> req) {
            getRequestQueue().add(req);
        }
}

