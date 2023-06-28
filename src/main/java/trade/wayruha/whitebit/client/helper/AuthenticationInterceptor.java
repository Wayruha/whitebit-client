package trade.wayruha.whitebit.client.helper;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okio.Buffer;
import org.jetbrains.annotations.NotNull;
import trade.wayruha.whitebit.client.APIConstant;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Instant;
import java.util.Base64;
import java.util.Formatter;

import static java.util.Objects.isNull;
import static trade.wayruha.whitebit.client.APIConstant.*;

@Slf4j
public class AuthenticationInterceptor implements Interceptor {
  private final String apiKey;
  private final String apiSecret;

  public AuthenticationInterceptor(String apiKey, String secretKey) {
    this.apiKey = apiKey;
    this.apiSecret = secretKey;
  }

  @NotNull
  @Override
  public Response intercept(Chain chain) throws IOException {
    Request origRequest = chain.request();
    String method = origRequest.method();
    //check
    final Request.Builder builder = origRequest.newBuilder();
    final boolean isSigned = origRequest.header(APIConstant.ENDPOINT_SECURITY_SIGNED) != null;
    builder.removeHeader(APIConstant.ENDPOINT_SECURITY_SIGNED);
    if (isSigned) {
      final RequestBody body = origRequest.body();
      if (isNull(body) || body.contentLength() == 0 || !method.equalsIgnoreCase("POST"))
        throw new IllegalArgumentException("Only POST is allowed for signed requests and they must have a RequestBody:" + origRequest.url());
      // encode RequestBody
      builder.addHeader(HEADER_ACCESS_KEY, apiKey);
      builder.addHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON);
      final String encodedBody = encodeBody(body);
      builder.addHeader(HEADER_PAYLOAD, encodedBody);
      builder.addHeader(HEADER_SIGNATURE, sign(encodedBody, apiSecret));
    }
    return chain.proceed(builder.build());
  }

 /* private Request encodeUrl(Request request) {
    String timestamp = Instant.now().toEpochMilli() + "";
    HttpUrl url = request.url();
    HttpUrl.Builder urlBuilder = url
        .newBuilder()
        .setQueryParameter("timestamp", timestamp);
    String queryParams = urlBuilder.build().query();
    final String signature = SignatureUtil.actualSignature(queryParams, apiSecret);
    urlBuilder.setQueryParameter("timestamp", signature);
    return request.newBuilder()
        .addHeader(HEADER_ACCESS_KEY, apiKey)
        .url(urlBuilder.build()).build();
  }*/

  private String encodeBody(RequestBody origBody) {
    final String str = bodyToString(origBody);
    return Base64.getEncoder().encodeToString(str.getBytes());
  }

  @SneakyThrows
  private String sign(String data, String secretKey) {
    final SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), APIConstant.CRYPTO_HMAC_SHA512);
    final Mac mac = Mac.getInstance(APIConstant.CRYPTO_HMAC_SHA512);
    mac.init(secretKeySpec);

    byte[] bytes = mac.doFinal(data.getBytes());
    final Formatter formatter = new Formatter();
    for (byte b : bytes) {
      formatter.format("%02x", b);
    }
    return formatter.toString();
  }

  private String bodyToString(RequestBody body) {
    try {
      Buffer buffer = new Buffer();
      body.writeTo(buffer);
      return buffer.readString(Charset.defaultCharset());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }
}

