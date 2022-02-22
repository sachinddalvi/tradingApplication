package cam.sdalvi.trade.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import cam.sdalvi.trade.service.PositionBookService;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class TradeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PositionBookService positionBookService;

    @BeforeEach
    void setUp() {
            }



    @Test
    @DisplayName("Test Trade for Buy and Cancel TradeTypes for same account & security")
    public void testBookPositionswithBuyAndCancelTrade() throws Exception {
        MvcResult result = (MvcResult) mockMvc.perform(post("/trade/bookPositions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[\t\n" +
                                        "  {\n" +
                                        "\t\t\"tradeId\": \"1\",\n" +
                                        "\t\t\"tradeType\": \"BUY\",\n" +
                                        "\t\t\"account\": \"ACC1\",\n" +
                                        "\t\t\"securityCode\": \"STK1\",\n" +
                                        "\t\t\"securityCount\": \"80\"\n" +
                                        "\t},\n" +
                                        "  \n" +
                                        "\t{\n" +
                                        "\t\t\"tradeId\": \"1\",\n" +
                                        "\t\t\"tradeType\": \"CANCEL\",\n" +
                                        "\t\t\"account\": \"ACC1\",\n" +
                                        "\t\t\"securityCode\": \"STK1\",\n" +
                                        "\t\t\"securityCount\": \"0\"\n" +
                                        "\t}\n" +
                                        "]"
                                ))

                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].*",hasSize(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].effectiveSecurities",is(0)))
                .andReturn();
            }

    @Test
    @DisplayName("Test Trade for Sell and Cancel TradeTypes for same account & security")
    public void testBookPositionswithSellAndCancelTrade() throws Exception {
        MvcResult result = (MvcResult) mockMvc.perform(post("/trade/bookPositions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[\t\n" +
                                "  {\n" +
                                "\t\t\"tradeId\": \"1\",\n" +
                                "\t\t\"tradeType\": \"CANCEL\",\n" +
                                "\t\t\"account\": \"ACC1\",\n" +
                                "\t\t\"securityCode\": \"STK1\",\n" +
                                "\t\t\"securityCount\": \"80\"\n" +
                                "\t},\n" +
                                "  \n" +
                                "\t{\n" +
                                "\t\t\"tradeId\": \"1\",\n" +
                                "\t\t\"tradeType\": \"CANCEL\",\n" +
                                "\t\t\"account\": \"ACC1\",\n" +
                                "\t\t\"securityCode\": \"STK1\",\n" +
                                "\t\t\"securityCount\": \"0\"\n" +
                                "\t}\n" +
                                "]"
                        ))

                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].*",hasSize(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].effectiveSecurities",is(0)))
                .andReturn();
    }

    @Test
    @DisplayName("Test Trade for Buy,Sell and Cancel TradeTypes")
    public void testBookPositionswithBuySellAndCancelTrades() throws Exception {
        MvcResult result = (MvcResult) mockMvc.perform(post("/trade/bookPositions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[\t\n" +
                                "  {\n" +
                                "\t\t\"tradeId\": \"1\",\n" +
                                "\t\t\"tradeType\": \"BUY\",\n" +
                                "\t\t\"account\": \"ACC1\",\n" +
                                "\t\t\"securityCode\": \"STK1\",\n" +
                                "\t\t\"securityCount\": \"80\"\n" +
                                "\t},\n" +
                                "  \n" +
                                "\t{\n" +
                                "\t\t\"tradeId\": \"1\",\n" +
                                "\t\t\"tradeType\": \"CANCEL\",\n" +
                                "\t\t\"account\": \"ACC1\",\n" +
                                "\t\t\"securityCode\": \"STK1\",\n" +
                                "\t\t\"securityCount\": \"0\"\n" +
                                "\t},\n" +
                                "    {\n" +
                                "\t\t\"tradeId\": \"2\",\n" +
                                "\t\t\"tradeType\": \"SELL\",\n" +
                                "\t\t\"account\": \"ACC2\",\n" +
                                "\t\t\"securityCode\": \"STK1\",\n" +
                                "\t\t\"securityCount\": \"10\"\n" +
                                "\t},\n" +
                                "\t{\n" +
                                "\t\t\"tradeId\": \"2\",\n" +
                                "\t\t\"tradeType\": \"CANCEL\",\n" +
                                "\t\t\"account\": \"ACC2\",\n" +
                                "\t\t\"securityCode\": \"STK1\",\n" +
                                "\t\t\"securityCount\": \"0\"\n" +
                                "\t},\n" +
                                "    {\n" +
                                "\t\t\"tradeId\": \"3\",\n" +
                                "\t\t\"tradeType\": \"BUY\",\n" +
                                "\t\t\"account\": \"ACC3\",\n" +
                                "\t\t\"securityCode\": \"STK10\",\n" +
                                "\t\t\"securityCount\": \"70\"\n" +
                                "\t},\n" +
                                "    {\n" +
                                "\t\t\"tradeId\": \"4\",\n" +
                                "\t\t\"tradeType\": \"BUY\",\n" +
                                "\t\t\"account\": \"ACC3\",\n" +
                                "\t\t\"securityCode\": \"STK10\",\n" +
                                "\t\t\"securityCount\": \"10\"\n" +
                                "\t}\n" +
                                "]"
                        ))

                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].*").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].*",hasSize(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].effectiveSecurities",is(80)))
                .andReturn();
    }

    @Test
    @DisplayName("Trades with Empty Data")
    public void testBookPositionswithEmptyData() throws Exception {
        MvcResult result = (MvcResult) mockMvc.perform(post("/trade/bookPositions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""
                        ))

                .andExpect(status().is4xxClientError())
                                .andReturn();
    }


}
