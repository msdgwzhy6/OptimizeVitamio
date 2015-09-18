package com.lnyp.volleyproject;

import com.alibaba.fastjson.JSON;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;

public class FastJsonRequest<T> extends Request<T> {

    private Response.Listener listener;

    private Class<T> clazz;

    public FastJsonRequest(int method, String url, Class<T> clazz, Response.Listener listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = listener;
        this.clazz = clazz;
    }

    public FastJsonRequest(String url, Class clazz, Response.Listener listener, Response.ErrorListener errorListener) {
        this(0, url, clazz, listener, errorListener);
        this.listener = listener;
        this.clazz = clazz;
    }


    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            //得到结果
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            // 解析
            return Response.success(JSON.parseObject(parsed, clazz), HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        this.listener.onResponse(response);
    }
}
