package trade.wayruha.whitebit;

public class TestConstants {
  public static final String API_KEY = "";
  public static final String API_SECRET = "";

  public static WBConfig getSimpleConfig(){
    return new WBConfig(API_KEY, API_SECRET);
  }
}
