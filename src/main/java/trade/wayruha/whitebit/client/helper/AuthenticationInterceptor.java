package trade.wayruha.whitebit.client.helper;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okio.Buffer;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import trade.wayruha.whitebit.APIConstant;
import trade.wayruha.whitebit.ClientConfig;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Formatter;

import static java.lang.String.format;
import static trade.wayruha.whitebit.APIConstant.*;

/**
 * A request interceptor that injects the API Key Header into requests and signs messages.
 */
@Slf4j
public class AuthenticationInterceptor implements Interceptor {
  private static final String REQUEST_JSON_PARAMETER_FORMAT = "\"request\":\"%s\"";
  private static final String MANDATORY_PARAM_NONCE = "\"nonce\":%d";
  private static final String MANDATORY_PARAM_NONCE_WINDOW = "\"nonceWindow\":%b";
  private static final char JSON_OBJECT_END = '}';
  private static final char COMMA = ',';

  private final String apiKey;
  private final String apiSecret;

  public AuthenticationInterceptor(String apiKey, String secretKey) {
    this.apiKey = apiKey;
    this.apiSecret = secretKey;
  }

  @NotNull
  @Override
  public Response intercept(Chain chain) throws IOException {
    final Request origRequest = chain.request();
    final boolean isSigned = origRequest.header(APIConstant.ENDPOINT_SECURITY_SIGNED) != null;
    if (!isSigned) return chain.proceed(origRequest);
    if (!origRequest.method().equalsIgnoreCase("POST"))
      throw new IllegalArgumentException("Only POST is allowed for signed requests and they must have a RequestBody:" + origRequest.url());

    final Request.Builder builder = origRequest.newBuilder();
    builder.removeHeader(APIConstant.ENDPOINT_SECURITY_SIGNED);
    final RequestBody body = origRequest.body();
    builder.addHeader(HEADER_ACCESS_KEY, apiKey);
    builder.addHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON);
    final String newBody = enrichBody(body, origRequest);
    final String encodedBody = encodeBody(newBody);
    builder.addHeader(HEADER_PAYLOAD, encodedBody);
    builder.addHeader(HEADER_SIGNATURE, sign(encodedBody, apiSecret));
    final MediaType MEDIA_TYPE_JSON = MediaType.get("application/json");
    builder.post(RequestBody.create(MEDIA_TYPE_JSON, newBody));
    return chain.proceed(builder.build());
  }

  private static String encodeBody(String enrichedBody) {
   return Base64.getEncoder().encodeToString(enrichedBody.getBytes());
 }

  /**
   * All signed endpoints requires sending some mandatory parameters
   * This method enrich existing RequestBody (json) with mandatory parameters.
   * Algorithm: find the end of the root object and prepend it with json parameters
   */
  private String enrichBody(RequestBody origBody, Request req){
    String bodyJson = bodyToString(origBody);
    if(StringUtils.isBlank(bodyJson)) bodyJson = "{}";

    final String url = req.url().encodedPath();
    final String actionPart = format(REQUEST_JSON_PARAMETER_FORMAT, url);
    final String noncePart = format(MANDATORY_PARAM_NONCE, ClientConfig.getCurrentTime());
    final String nonceWindowPart = format(MANDATORY_PARAM_NONCE_WINDOW, ClientConfig.isEnableNonceWindow());
    String mainPart = bodyJson.substring(0, bodyJson.lastIndexOf(JSON_OBJECT_END));
    return mainPart + (mainPart.length() > 2 ? COMMA : "") +
        actionPart + COMMA +
        noncePart + COMMA +
        nonceWindowPart + JSON_OBJECT_END;
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

