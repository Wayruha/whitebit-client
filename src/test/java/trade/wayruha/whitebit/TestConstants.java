package trade.wayruha.whitebit;

public class TestConstants {
  public static final String API_KEY = "";
  public static final String API_SECRET = "";

  public static WBConfig getSimpleConfig(){
    final WBConfig wbConfig = new WBConfig(API_KEY, API_SECRET);
    wbConfig.setHttpLogRequestData(true);
    return wbConfig;
  }
}
