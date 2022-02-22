package cam.sdalvi.trade.service;

import cam.sdalvi.trade.dto.TradeData;
import cam.sdalvi.trade.dto.TradeResponse;
import org.springframework.stereotype.Component;

import java.util.List;

public interface PositionBookService {

    public List<TradeResponse> addTrade(List <TradeData> tradeDataList);
}
