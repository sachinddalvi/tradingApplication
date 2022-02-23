package cam.sdalvi.trade.controller;

import cam.sdalvi.trade.dao.PositionBook;
import cam.sdalvi.trade.dto.TradeData;
import cam.sdalvi.trade.dto.TradeResponse;
import cam.sdalvi.trade.service.PositionBookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class TradeControllerMockTest {
    @Autowired
    private MockMvc mockMvc;

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
    @DisplayName("Validate Mock Buy Trades for same account & security")
    public void testBookPositions() throws Exception {
        List<TradeData> inputTradeRequestList = Arrays.asList(new TradeData(1L, "BUY", "ACC1", "STK1", 100),
                new TradeData(1L, "BUY", "ACC1", "STK1", 100),
                new TradeData(2L, "BUY", "ACC1", "STK1", 500));

        Mockito.when(mockPositionBookService.addTrade(inputTradeRequestList)).thenReturn(tradeResponseList);

        MvcResult result = mockMvc.perform(post("/trade/bookPositions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[\n" +
                                "      {\n" +
                                "        \"tradeId\": \"1\",\n" +
                                "        \"tradeType\": \"BUY\",\n" +
                                "        \"account\": \"ACC1\",\n" +
                                "\t\"securityCode\": \"STK1\",\n" +
                                "        \"securityCount\": \"1000\"\n" +
                                "      },\n" +
                                "      {\n" +
                                "        \"tradeId\": \"2\",\n" +
                                "        \"tradeType\": \"BUY\",\n" +
                                "        \"account\": \"ACC1\",\n" +
                                "\t\"securityCode\": \"STK1\",\n" +
                                "        \"securityCount\": \"300\"\n" +
                                "      }\n" +
                                "    ]"))

                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(0)))
                .andReturn();


    }
    
    
    @Test
    @DisplayName("Validate Mock Buy Trades for same account & security")
    public void testBookPositionException() throws Exception {
        List<TradeData> inputTradeRequestList = Arrays.asList(new TradeData(1L, "INVALID_TRDE_TYPE", "ACC1", "STK1", 100),
                new TradeData(1L, "BUY", "ACC1", "STK1", 100),
                new TradeData(2L, "BUY", "ACC1", "STK1", 500));

        Mockito.when(mockPositionBookService.addTrade(inputTradeRequestList)).thenReturn(tradeResponseList);

        MvcResult result = mockMvc.perform(post("/trade/bookPositions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[\n" +
                                "      {\n" +
                                "        \"tradeId\": \"1\",\n" +
                                "        \"tradeType\": \"INVALID_TRDE_TYPE\",\n" +
                                "        \"account\": \"ACC1\",\n" +
                                "\t\"securityCode\": \"STK1\",\n" +
                                "        \"securityCount\": \"1000\"\n" +
                                "      },\n" +
                                "      {\n" +
                                "        \"tradeId\": \"2\",\n" +
                                "        \"tradeType\": \"BUY\",\n" +
                                "        \"account\": \"ACC1\",\n" +
                                "\t\"securityCode\": \"STK1\",\n" +
                                "        \"securityCount\": \"300\"\n" +
                                "      }\n" +
                                "    ]"))

                .andExpect(status().isBadRequest())
                .andReturn();


    }
    
    

}
