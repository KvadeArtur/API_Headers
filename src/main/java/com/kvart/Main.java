package com.kvart;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        /*
        1) Послать POST запрос на https://postman-echo.com/post
        где в BODY передать foo1=bar1&foo2=bar2 в формате x-www-form-urlencoded
        Ответ распарсить и вывести.
         */

        String URL = "https://postman-echo.com/post";
        String body = "foo1=bar1&foo2=bar2";

        String json = HttpUtil.sendRequest(URL, null, body);
        Gson gson = new Gson();
        JSON jsonPost = gson.fromJson(json, JSON.class);

        System.out.println(jsonPost);


        /*
        2*) Взять файл по прямой ссылке, например
        https://dl.dropboxusercontent.com/s/vxnydq4xjkmefrp/CLUVAL.java
        Скачать этот файл, за 2 запроса, первую половину и докачать вторую половину.
         */

        System.out.println();

        String URL2 = "https://dl.dropboxusercontent.com/s/vxnydq4xjkmefrp/CLUVAL.java";

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Range", "bytes=0-500");

        String download = HttpUtil.sendRequest(URL2, headers, null);

        headers.clear();
        headers.put("Range", "bytes=501-2212");
        download += HttpUtil.sendRequest(URL2, headers, null);

        System.out.println(download);


        /*
        3*) Сделать то же самое используя библиотеку okhttp3
         */

        System.out.println();

        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://dl.dropboxusercontent.com/s/vxnydq4xjkmefrp/CLUVAL.java")
                .get()
                .addHeader("Range", "bytes=0-500")
                .build();

        String result = "";
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        request = new Request.Builder()
                .url("https://dl.dropboxusercontent.com/s/vxnydq4xjkmefrp/CLUVAL.java")
                .get()
                .addHeader("Range", "bytes=501-2212")
                .build();

        try {
            response = okHttpClient.newCall(request).execute();
            result += response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(result);

    }

}
