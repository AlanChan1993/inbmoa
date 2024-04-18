package com.infinitus.bms_oa.ipass.utils;

import okhttp3.*;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.time.ZoneOffset.UTC;
import static org.apache.commons.codec.binary.Base64.encodeBase64String;
import static org.apache.commons.codec.digest.HmacAlgorithms.HMAC_SHA_256;

/**
 * Gapi工具类
 */
public class IpaasUtils {
    //创建OkHttpClient对象
    public static final OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(10L, TimeUnit.SECONDS).readTimeout(10L,TimeUnit.SECONDS).build();

    private static final Logger logger = LoggerFactory.getLogger(IpaasUtils.class);
    private static final DateTimeFormatter RFC_7231_FORMATTER = DateTimeFormatter .ofPattern("EEE, dd MMM yyyy HH:mm:ss O").withLocale(Locale.ENGLISH);
    private static final MediaType JSON_FORMAT = MediaType.get("application/json; charset=utf-8");
    private static MediaType MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");

    public static String postRequest(String iPaaSUserName, String iPaaSSecret, String iPaaSAppKey, String baseUrl, String url, String body) throws NoSuchAlgorithmException {
        String method = "POST";
        logger.info("请求地址:{},请求参数:{}" ,(baseUrl + url) , body);

        // 时间
        String date = RFC_7231_FORMATTER.format(ZonedDateTime.now(UTC));

        // 请求体摘要
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] hash = messageDigest.digest(body.getBytes(UTF_8));
        String digest = "SHA-256=" + encodeBase64String(hash);

        // 签名，计算方法为 hmac_sha_256(key, 日期 + 方法 + 路径 + 摘要)
        String signature = encodeBase64String(new HmacUtils(HMAC_SHA_256, iPaaSSecret)
                .hmac("date: " + date + "\n" + method + " " + url + " HTTP/1.1\ndigest: " + digest));

        // HMAC 授权
        String authorization = "hmac username=\"" + iPaaSUserName + "\", algorithm=\"hmac-sha256\", headers=\"date request-line digest\", signature=\"" + signature + "\"";

        // 发起请求
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE, body);
        Request request = new Request.Builder()
                .url(baseUrl + url)
                .header("Authorization", authorization)
                .header("Date", date)
                .header("Digest", digest)
                .header("appkey", iPaaSAppKey)
                .post(requestBody)
                .build();
        return executeHttp(request);
    }

    /**
     * ipaas get请求
     * @param iPaaSUserName ak
     * @param iPaaSSecret sk
     * @param iPaaSAppKey appkey
     * @param baseUrl 域名
     * @param url 路径
     * @return
     */
    public static String getRequest(String iPaaSUserName, String iPaaSSecret, String iPaaSAppKey, String baseUrl, String url) {
        String method = "GET";
        // 时间
        String date = RFC_7231_FORMATTER.format(ZonedDateTime.now(UTC));
        //    String date = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneOffset.UTC));
        // 时间 + 请求类型 + 请求uri 的加密
        String signature =
                encodeBase64String(
                        new HmacUtils(HmacAlgorithms.HMAC_SHA_256, iPaaSSecret)
                                .hmac("date: " + date + "\n" + method + " " + url + " HTTP/1.1"));
        // HMAC 授权
        String authorization = "hmac username=\"" + iPaaSUserName + "\", algorithm=\"hmac-sha256\", headers=\"date request-line\", signature=\"" + signature + "\"";
        // 发起请求
        Request request = new Request.Builder()
                .url(baseUrl + url)
                .header("Authorization", authorization)
                .header("Date", date)
                .header("appkey", iPaaSAppKey)
                .get()
                .build();
        return executeHttp(request);
    }

    public static String executeHttp(Request request){
        try(Response response = client.newCall(request).execute()) {
            String resultStr = Objects.requireNonNull(response.body()).string();
            logger.info("executeHttp-请求URL：{},响应数据：{}",request.url(),resultStr);
            return resultStr;
        } catch (Exception e) {
            logger.error("executeHttp-请求异常,请求URL:{}",request.url(),e);
        }
        return "";
    }
}
