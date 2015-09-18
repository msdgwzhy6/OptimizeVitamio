package com.lnyp.volleyproject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends Activity {

    private static final String url = "http://www.imooc.com/api/teacher?type=4&num=30";

    private RequestQueue requestQueue;

    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = (ImageView) findViewById(R.id.img);

        requestQueue = Volley.newRequestQueue(this);
    }

    private void sendGetReq() {

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                System.out.println(s);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        requestQueue.add(stringRequest);
    }

    private void sendPostReq() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                System.out.println(s);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> datas = new HashMap<>();
                datas.put("name", "李宁");

                return datas;
            }
        };
    }


    private void sendJsonReq() {

        //JsonObjectRequest(String url, JSONObject jsonRequest, Listener<JSONObject> listener, ErrorListener errorListener)
        JsonObjectRequest jsonRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                System.out.println(jsonObject.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        requestQueue.add(jsonRequest);
    }

    private void sendImgReq() {
        //ImageRequest(String url, Listener<Bitmap> listener, int maxWidth, int maxHeight, ScaleType scaleType, Config decodeConfig, ErrorListener errorListener)
        ImageRequest imageRequest = new ImageRequest("http://img.mukewang.com/55237dcc0001128c06000338-300-170.jpg"
                , new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                img.setImageBitmap(bitmap);
            }
        },
                0,
                0,
                ImageView.ScaleType.CENTER, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                img.setImageResource(R.mipmap.ic_launcher);
            }
        });

        requestQueue.add(imageRequest);
    }

    private void imgLoaderReq() {

        ImageLoader imageLoader = new ImageLoader(requestQueue, new MyImageCache());

        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(img, R.mipmap.ic_launcher, R.mipmap.ic_launcher);

        imageLoader.get("http://img.mukewang.com/55237dcc0001128c06000338-300-170.jpg", imageListener, 200, 200);
    }
}
