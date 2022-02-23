package cam.sdalvi.trade.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import cam.sdalvi.trade.dto.TradeResponse;

@Component
public class PositionBook {
	
	 private static final List<TradeResponse> CachedtradeResponseList = new ArrayList<TradeResponse>();

	public static List<TradeResponse> getCachedtraderesponselist() {
		return CachedtradeResponseList;
	}


}
