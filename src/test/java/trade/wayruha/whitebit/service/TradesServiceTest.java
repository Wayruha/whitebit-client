package trade.wayruha.whitebit.service;

import org.junit.Test;
import trade.wayruha.whitebit.TestConstants;
import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.domain.Market;
import trade.wayruha.whitebit.dto.Trade;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TradesServiceTest {

    WBConfig config = TestConstants.getSimpleConfig();

    TradesService service = new TradesService(config);

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
