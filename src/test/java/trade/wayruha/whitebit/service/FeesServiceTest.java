package trade.wayruha.whitebit.service;

import org.junit.Test;
import trade.wayruha.whitebit.TestConstants;
import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.dto.Fee;

import java.util.Map;

import static org.junit.Assert.*;

public class FeesServiceTest {

    WBConfig config = TestConstants.getSimpleConfig();

    FeesService service = new FeesService(config);

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
}
