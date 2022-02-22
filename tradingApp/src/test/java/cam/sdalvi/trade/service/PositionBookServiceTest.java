package cam.sdalvi.trade.service;

import cam.sdalvi.trade.dto.TradeData;
import cam.sdalvi.trade.dto.TradeResponse;
import cam.sdalvi.trade.exceptions.InvalidTradeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class PositionBookServiceTest {

    @Autowired
    private PositionBookService positionBookActual;

    private List<TradeData> tradeRequestList;

    private List<TradeResponse> tradeResponseList;

    @BeforeEach
    void setUp() {
           }

    @Test
    @DisplayName("Validate Service with Null TradeData")
    public void testAddTrade() throws Exception {
    Assertions.assertThrows(InvalidTradeException.class, () -> positionBookActual.addTrade(null));
    }
}
