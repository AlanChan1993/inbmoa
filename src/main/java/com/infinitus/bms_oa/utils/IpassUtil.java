package com.infinitus.bms_oa.utils;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;

import javax.crypto.Mac;
import java.io.IOException;
import java.security.MessageDigest;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.time.ZoneOffset.UTC;
import static org.apache.commons.codec.binary.Base64.encodeBase64String;

public class IpassUtil {

    private static final DateTimeFormatter RFC_7231_FORMATTER = DateTimeFormatter
            .ofPattern("EEE, dd MMM yyyy HH:mm:ss O").withLocale(Locale.ENGLISH);
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public JSONObject postReq(String api, String body, String appkey, String ak, String sk, String baseUrl) throws Exception{
        System.out.println("---------- POST ---------");

        String method = "POST";
        // 时间
        String date = RFC_7231_FORMATTER.format(ZonedDateTime.now(UTC));
        // 请求体摘要
        // 签名，计算方法为 hmac_sha_256(key, 日期 + 方法 + 路径 + 摘要)
        Mac mac = HmacUtils.getInitializedMac(HmacAlgorithms.HMAC_SHA_256, sk.getBytes(UTF_8));
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] hash = messageDigest.digest(body.getBytes(UTF_8));
        String digest = "SHA-256=" + encodeBase64String(hash);
        byte[] content = mac.doFinal(("date: " + date + "\n" + method + " " + api + " HTTP/1.1\ndigest: " + digest).getBytes(UTF_8));
        String signature = encodeBase64String(content);
        // HMAC 授权
        String authorization = "hmac username=\"" + ak + "\", algorithm=\"hmac-sha256\", headers=\"date request-line digest\", signature=\"" + signature + "\"";
        // 发起请求
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(JSON, body);
        Request request = new Request.Builder()
                .url(baseUrl + api)
                .header("Authorization", authorization)
                .header("Date", date)
                .header("Digest", digest)
                .header("appkey", appkey)
                .post(requestBody)
                .build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println("请求码: " + response.code());
            String result = response.body().string();
            System.out.println("请求结果: " + result);
            JSONObject jsonObject = (JSONObject) com.alibaba.fastjson.JSON.parse(result);
            return jsonObject;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public JSONObject postReq_Json(String api, JSONObject body, String appkey, String ak, String sk,
    String baseUrl) throws Exception{
        System.out.println("---------- POST ---------");

        String method = "POST";
        // 时间
        String date = RFC_7231_FORMATTER.format(ZonedDateTime.now(UTC));
        // 请求体摘要
        // 签名，计算方法为 hmac_sha_256(key, 日期 + 方法 + 路径 + 摘要)
        Mac mac = HmacUtils.getInitializedMac(HmacAlgorithms.HMAC_SHA_256, sk.getBytes(UTF_8));
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] hash = messageDigest.digest(body.getBytes(String.valueOf(UTF_8)));
        String digest = "SHA-256=" + encodeBase64String(hash);
        byte[] content = mac.doFinal(("date: " + date + "\n" + method + " " + api + " HTTP/1.1\ndigest: " + digest).getBytes(UTF_8));
        String signature = encodeBase64String(content);
        // HMAC 授权
        String authorization = "hmac username=\"" + ak + "\", algorithm=\"hmac-sha256\", headers=\"date request-line digest\", signature=\"" + signature + "\"";
        // 发起请求
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(JSON, String.valueOf(body));
        Request request = new Request.Builder()
                .url(baseUrl + api)
                .header("Authorization", authorization)
                .header("Date", date)
                .header("Digest", digest)
                .header("appkey", appkey)
                .post(requestBody)
                .build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println("请求码: " + response.code());
            String result = response.body().string();
            System.out.println("请求结果: " + result);
            JSONObject jsonObject = (JSONObject) com.alibaba.fastjson.JSON.parse(result);
            return jsonObject;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
