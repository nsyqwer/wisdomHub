package com.nsy.util;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ApiClient {

    private static final String MEDIA_TYPE_JSON = "application/json; charset=utf-8";
    private final static OkHttpClient client = new OkHttpClient().newBuilder()
            .connectionPool(new ConnectionPool(100, 5, TimeUnit.MINUTES))
            .readTimeout(60*10, TimeUnit.SECONDS)
            .build();
    private static final String ERROR_MESSAGE = "Unexpected code: ";
    private final String baseUrl;

    public ApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String createPPT(String appId, String timestamp, String signature, String query,String author) throws IOException {
        validateParameters(appId, timestamp, signature, query);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("query", query);
        jsonObject.put("create_model", "topic");
        jsonObject.put("theme", "green");
        jsonObject.put("business_id", "my business_id");
        jsonObject.put("author", author);
        jsonObject.put("is_card_note", true);

        RequestBody body = RequestBody.create(jsonObject.toString(), MediaType.get(MEDIA_TYPE_JSON));

        Request request = buildPostRequest(baseUrl + "/api/aippt/create", appId, timestamp, signature, body);
        return executeRequest(request);
    }



    public String checkProgress(String appId, String timestamp, String signature, String sid) throws IOException {
        validateParameters(appId, timestamp, signature, sid);

        HttpUrl url = HttpUrl.parse(baseUrl).newBuilder()
                .addPathSegment("api")
                .addPathSegment("aippt")
                .addPathSegment("progress")
                .addQueryParameter("sid", sid)
                .build();

        Request request = buildGetRequest(url.toString(), appId, timestamp, signature);
        return executeRequest(request);
    }

    public String getTemplateList(String appId, String timestamp, String signature) throws IOException {
        validateParameters(appId, timestamp, signature);

        Request request = buildGetRequest(baseUrl + "/api/aippt/themeList", appId, timestamp, signature);
        return executeRequest(request);
    }

    private Request buildPostRequest(String url, String appId, String timestamp, String signature, RequestBody body) {
        return new Request.Builder()
                .url(url)
                .addHeader("appId", appId)
                .addHeader("timestamp", timestamp)
                .addHeader("signature", signature)
                .post(body)
                .build();
    }

    private Request buildGetRequest(String url, String appId, String timestamp, String signature) {
        return new Request.Builder()
                .url(url)
                .addHeader("appId", appId)
                .addHeader("timestamp", timestamp)
                .addHeader("signature", signature)
                .get()
                .build();
    }

    private String executeRequest(Request request) throws IOException {
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.out.println(response.body().string());
                throw new IOException(ERROR_MESSAGE + response);
            }
            return response.body().string();
        }
    }

    private void validateParameters(String... params) {
        for (String param : params) {
            if (param == null || param.isEmpty()) {
                throw new IllegalArgumentException("Parameter cannot be null or empty");
            }
        }
    }


}
