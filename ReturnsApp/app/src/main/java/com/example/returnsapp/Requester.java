package com.example.returnsapp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Requester {
    static String id;
    static String status;
    static String URL = "https://dev-api.shping.com/serialization-service/reassignment/task";
    static String identityTicket = "63eb8926-e661-42c1-998d-3f008665c8e5";
    static String cacheControl = "no-cache";
    static String contentType = "application/json";
    static Proxy proxyTest;
    static String count;

    public static void sendPOSTForCodes() throws JSONException {
        proxyTest = new Proxy(Proxy.Type.HTTP,new InetSocketAddress("", 8080));
        OkHttpClient client = new OkHttpClient().newBuilder()
                //.proxy(proxyTest)
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        System.out.println(JSONParser.jsonObject.toString());
        RequestBody body = RequestBody.create(mediaType,
                JSONParser.jsonObject.toString());

        Request request = new Request.Builder()
                .url("")
                .method("POST", body)
                .addHeader("authenticateit_identity_ticket", "")
                .addHeader("cache-control", "no-cache")
                .addHeader("content-type", "application/json")
                .build();

        Response response = null;

        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String responceBody = null;

        try {
            responceBody = response.body().string();
            System.out.println(responceBody);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(responceBody);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            id = jsonObject.get("id").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void sendGETForCodesStatus() throws IOException, JSONException, InterruptedException {
        proxyTest = new Proxy(Proxy.Type.HTTP,new InetSocketAddress("", 8080));
        OkHttpClient client = new OkHttpClient().newBuilder()
                //.proxy(proxyTest)
                .build();

        Request request = new Request.Builder()
                .url("" + id)
                .method("GET", null)
                .addHeader("authenticateit_identity_ticket", "")
                .addHeader("cache-control", "no-cache")
                .addHeader("content-type", "application/json")
                .build()
                ;
        Response response = client.newCall(request).execute();
        String responceBody = null;
        try {
            responceBody = response.body().string();
            System.out.println(responceBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject(responceBody);

        while (jsonObject.has("error")) {
            Requester.sendGETForCodesStatus();
        }

        status = jsonObject.get("status").toString();
        System.out.println(status);
    }
    public static void sendGETForRepeat(String code) throws IOException, JSONException {
        proxyTest = new Proxy(Proxy.Type.HTTP,new InetSocketAddress("", 8080));
        OkHttpClient client = new OkHttpClient().newBuilder()
                .proxy(proxyTest)
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n\"op\": \"AND\",\n\"query\": \"serial_number==\\\""+ code +"\\\"\",\n\"take\": 1\n}");
        Request request = new Request.Builder()
                .url("")
                .method("POST", body)
                .addHeader("authenticateit_identity_ticket", "")
                .addHeader("cache-control", "no-cache")
                .addHeader("content-type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        String responceBody = null;
        try {
            responceBody = response.body().string();
            System.out.println(responceBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject(responceBody);
        count = jsonObject.get("count").toString();
    }

}
