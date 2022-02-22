package cam.sdalvi.trade.controller;

import cam.sdalvi.trade.dto.TradeData;
import cam.sdalvi.trade.dto.TradeResponse;
import cam.sdalvi.trade.exceptions.InvalidTradeException;
import cam.sdalvi.trade.service.PositionBookService;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/trade")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TradeController {
    @Autowired
    PositionBookService positionBookService;
    List<TradeResponse> tradeResponseList;

    @PostMapping(value = "/bookPositions")
    public ResponseEntity<List<TradeResponse>> bookPositions(@RequestBody List<TradeData> tradeRequestList) {
        try {
            if (Objects.nonNull(tradeRequestList) && !tradeRequestList.isEmpty()) {
                tradeRequestList.stream().forEach(tradeData -> {tradeData.validateAccount();
                    tradeData.validateTradeId();
                    tradeData.validateTradeType();
                    tradeData.validateSecurityCode();});
                System.out.println(" Controller Request----" + tradeRequestList);
                tradeResponseList = positionBookService.addTrade(tradeRequestList);
                System.out.println(" Controller Response----" + tradeResponseList);
                return new ResponseEntity<List<TradeResponse>>(tradeResponseList, HttpStatus.CREATED);
                }
             else{
                  return new ResponseEntity<List<TradeResponse>>(tradeResponseList, HttpStatus.BAD_REQUEST);
        }
            }
        catch (InvalidTradeException ie) {
            System.out.println(" Logging Exception "+ie.getMessage());
            return new ResponseEntity<List<TradeResponse>>(tradeResponseList, HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            System.out.println(" Logging Exception "+e.getMessage());
            return new ResponseEntity<List<TradeResponse>>(tradeResponseList, HttpStatus.BAD_REQUEST);
        }
    }


}
