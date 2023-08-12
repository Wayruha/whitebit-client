package trade.wayruha.whitebit.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.Test;
import trade.wayruha.whitebit.TestConstants;
import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.domain.Market;
import trade.wayruha.whitebit.dto.Asset;

import java.util.Map;

import static org.junit.Assert.assertNotNull;

public class AssetServiceTest {

    WBConfig config = TestConstants.getSimpleConfig();

    AssetService service = new AssetService(config);

    final String symbol = "BTC";

    @Test
    public void test_getAssetMap(){
        Map<String, Asset> response = service.getAssets();
        assertNotNull(response);

        Asset asset = response.get(symbol);

        assertNotNull(asset.getName());
    }
}
