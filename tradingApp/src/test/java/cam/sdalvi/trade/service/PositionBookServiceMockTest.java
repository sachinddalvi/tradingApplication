package cam.sdalvi.trade.service;

import cam.sdalvi.trade.dto.TradeData;
import cam.sdalvi.trade.dto.TradeResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class PositionBookServiceMockTest {

    @MockBean
    private PositionBookService mockPositionBookService;

    private List<TradeData> tradeRequestList;

    private List<TradeResponse> tradeResponseList;

    @BeforeEach
    void setUp() {
        tradeResponseList = Arrays.asList(new TradeResponse("ACC1", "STK1", 600,
                Arrays.asList(
                        new TradeData(1L, "BUY", "ACC1", "STK1", 100),
                        new TradeData(2L, "BUY", "ACC1", "STK1", 500))));
    }

    @Test
    @DisplayName("Validate Service with Buy Trades for same account & security")
    public void testAddTrade() throws Exception {
        List<TradeData> inputTradeRequestList = Arrays.asList(new TradeData(1L, "BUY", "ACC1", "STK1", 100),
                new TradeData(1L, "BUY", "ACC1", "STK1", 100),
                new TradeData(2L, "BUY", "ACC1", "STK1", 500));

        Mockito.when(mockPositionBookService.addTrade(inputTradeRequestList)).thenReturn(tradeResponseList);
        assertEquals(inputTradeRequestList.get(0).getAccount(), tradeResponseList.get(0).getAccount());
    }

}
