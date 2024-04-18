package com.infinitus.bms_oa.ipass.utils;

import okhttp3.*;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Mac;
import java.io.IOException;
import java.security.MessageDigest;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.time.ZoneOffset.UTC;
import static org.apache.commons.codec.binary.Base64.encodeBase64String;

public class HmacTest {
  // dev 以下配置要素需要找iPaaS管理员获取 -- 严建芳
  @Value("${IPASS.BASE_URL_DEV}")
  private  String BASE_URL_DEV ;

  @Value("${IPASS.I_PASS_SK_DEV}")
  private  String I_PASS_SK_DEV ;

  @Value("${IPASS.APP_KEY_DEV}")
  private  String APP_KEY_DEV ;

  @Value("${IPASS.I_PASS_AK_DEV}")
  private  String I_PASS_AK_DEV ;

  @Value("${IPASS.Ipass_addIntegral}")
  private  String Ipass_addIntegral ;

  @Value("${IPASS.Ipass_import}")
  private  String Ipass_import ;

  private static final DateTimeFormatter RFC_7231_FORMATTER = DateTimeFormatter
    .ofPattern("EEE, dd MMM yyyy HH:mm:ss O").withLocale(Locale.ENGLISH);
  private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

  @Test
  public void test () throws Exception{
    // POST 接口请求方式及参数，接入方根据请求的具体接口做相应的变动
    String method = "POST";
    String body = "{\"manufacturerCode\":\"200005\",\"manufacturerName\":\"中通生化制品有限公司\",\"manufacturerUserName\":\"MO0058888\",\"isQuality\":\"1\",\"plantCode\":\"P001\",\"items\":[{\"id\":\"72699\",\"materialCode\":\"18069-05\",\"materiaBatchNum\":\"20I07XBP01\",\"productionDate\":\"20230911PB\",\"sampleQuantity\":\"0\",\"testQuantity\":\"0\",\"newProdTestQuantity\":\"0\",\"mrpQuantity\":\"0\",\"qcType\":\"FP\",\"qcResultCode\":\"QUALIFIED\",\"designates\":[{\"num\":\"1\",\"designatesCode\":\"PN00189\",\"designatesValue\":\"符合规定色泽\"},{\"num\":\"2\",\"designatesCode\":\"PN00282\",\"designatesValue\":\"精华液均匀分散于膜布中\"},{\"num\":\"3\",\"designatesCode\":\"PN00286\",\"designatesValue\":\"符合规定香型\"},{\"num\":\"4\",\"designatesCode\":\"PN0032\",\"designatesValue\":\"6.36\"},{\"num\":\"5\",\"designatesCode\":\"PN0064\",\"designatesValue\":\"＜0.3\"},{\"num\":\"6\",\"designatesCode\":\"PN0074\",\"designatesValue\":\"＜10\"},{\"num\":\"7\",\"designatesCode\":\"PN0096\",\"designatesValue\":\"＜10\"},{\"num\":\"8\",\"designatesCode\":\"PN0260\",\"designatesValue\":\"20.9\"},{\"num\":\"9\",\"designatesCode\":\"PN0720\",\"designatesValue\":\"正常\"},{\"num\":\"10\",\"designatesCode\":\"PN0725\",\"designatesValue\":\"正常\"}]}]}";
    postReq(Ipass_addIntegral, body, APP_KEY_DEV, I_PASS_AK_DEV, I_PASS_SK_DEV, BASE_URL_DEV);

    // GET 接口请求方式及参数，接入方根据请求的具体接口做相应的变动
    String method1 = "GET";
    getReq(Ipass_import, APP_KEY_DEV, I_PASS_AK_DEV, I_PASS_SK_DEV, BASE_URL_DEV);

  }

  private void postReq(String api, String body, String appkey, String ak, String sk, String baseUrl) throws Exception{
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
    try (Response response = client.newCall(request).execute()) {
      System.out.println("请求码: " + response.code());
      System.out.println("请求结果: " + response.body().string());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void getReq(String api, String appkey, String ak, String sk, String baseUrl) {
    System.out.println("------------- GET --------------");
    String method = "GET";
    // 时间
    String date = RFC_7231_FORMATTER.format(ZonedDateTime.now(UTC));
    // HMAC 授权
    Mac mac = HmacUtils.getInitializedMac(HmacAlgorithms.HMAC_SHA_256, sk.getBytes(UTF_8));
    byte[] content = mac.doFinal(("date: " + date + "\n" + method + " " + api + " HTTP/1.1").getBytes(UTF_8));
    String signature = encodeBase64String(content);
    String authorization = "hmac username=\"" + ak + "\", algorithm=\"hmac-sha256\", headers=\"date request-line\", signature=\"" + signature + "\"";

    // 发起请求
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder()
      .url(baseUrl + api)
      .header("Authorization", authorization)
      .header("Date", date)
      .header("appkey", appkey)
      .get()
      .build();
    try (Response response = client.newCall(request).execute()) {
      System.out.println("请求结果: " + response.body().string());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
