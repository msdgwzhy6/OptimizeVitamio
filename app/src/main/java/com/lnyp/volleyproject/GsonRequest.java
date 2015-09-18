package com.lnyp.volleyproject;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;


public class GsonRequest<T> extends Request<T> {

    private Response.Listener<T> mListener;

    private Gson mGosn;

    private Class<T> clazz;


    public GsonRequest(int method, String url, Class<T> clazz, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.mListener = listener;
        this.clazz = clazz;
        mGosn = new Gson();
    }

    public GsonRequest(String url, Class<T> clazz, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        this(0, url, clazz, listener, errorListener);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            //得到结果
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            // 解析
            return Response.success(mGosn.fromJson(parsed, clazz), HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        this.mListener.onResponse(response);
    }
}
