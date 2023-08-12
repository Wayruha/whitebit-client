package trade.wayruha.whitebit.service;

import org.junit.Test;
import trade.wayruha.whitebit.TestConstants;
import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.domain.Market;
import trade.wayruha.whitebit.dto.Asset;
import trade.wayruha.whitebit.dto.Fee;
import trade.wayruha.whitebit.dto.Trade;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class PublicServiceTest {

    WBConfig config = TestConstants.getSimpleConfig();

    PublicService service = new PublicService(config);

    final String symbol = "BTC";

    @Test
    public void test_getFees() {
        Map<String, Fee> response = service.getFees();
        assertNotNull(response);

        String ticker = "AAVE";

        Fee fee = response.get("AAVE");

        assertEquals(fee.getTicker(), ticker);
        assertTrue(fee.isDepositable());

        assertNotNull(fee.getWithdraw().getMaxAmount());
    }

    @Test
    public void test_getAssetMap(){
        Map<String, Asset> response = service.getAssets();
        assertNotNull(response);

        Asset asset = response.get(symbol);

        assertNotNull(asset.getName());
        assertNotNull(asset.getMaxDeposit());

        assertNotNull(asset.getNetworks().getDefaultNetwork());

        assertNotNull(asset.getLimits().getDeposit());
    }

    @Test
    public void test_getTrades(){
        List<Trade> response = service.getTrades(Market.parse("BTC_USDT"), "sell");

        assertNotNull(response);

        Trade trade = response.get(0);

        assertEquals(trade.getType(), "sell");
    }

    @Test
    public void test_getBuyTrades(){
        List<Trade> response = service.getTrades(Market.parse("BTC_USDT"), "buy");

        assertNotNull(response);

        Trade trade = response.get(0);

        assertEquals(trade.getType(), "buy");
    }
}
