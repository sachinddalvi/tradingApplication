package cam.sdalvi.trade.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cam.sdalvi.trade.dao.PositionBook;
import cam.sdalvi.trade.dto.TradeData;
import cam.sdalvi.trade.dto.TradeResponse;
import cam.sdalvi.trade.exceptions.InvalidTradeException;

@Service
public class PositionBookServiceImpl implements PositionBookService {


	@Autowired
    PositionBook potionBook;
    List<TradeResponse> tradeResponseList;
    Map<String, List<TradeData>> groupByTradeType;
    Map<String, Map<String, List<TradeData>>> groupByAccountAndSecurity;
    TradeResponse tradeResponse;
    Integer boughtSecurities=0 ;
    Integer soldSecurities =0;


    /***
     * This method performs business logic to store trades in a PositionBook
     * @param tradeDataList
     * @returns tradeRespone to Controller
     */
    public List<TradeResponse> addTrade(List<TradeData> tradeDataList) {
        groupByAccountAndSecurity = findGroupByAccountAndSecurity(tradeDataList);
        tradeResponseList = new ArrayList<TradeResponse>();
        groupByAccountAndSecurity.values().stream().forEach(m -> {
            m.values().stream().collect(Collectors.toList()).
                    forEach(e -> {
                        tradeResponse = new TradeResponse();
                        if (e.size() > 1) {
                            groupByTradeType = findGroupByTradeType(e);
                            if (!groupByTradeType.isEmpty() && groupByTradeType.containsKey("BUY")) {
                                boughtSecurities = findSecurityCountForTradeType(groupByTradeType, "BUY");
                            }
                            if (!groupByTradeType.isEmpty() && groupByTradeType.containsKey("SELL")) {
                                soldSecurities = findSecurityCountForTradeType(groupByTradeType, "SELL");
                            }
                            boughtSecurities = findSecurityCountForCancelledTradeType(groupByTradeType, "BUY", boughtSecurities);
                            soldSecurities = findSecurityCountForCancelledTradeType(groupByTradeType, "SELL", soldSecurities);
                            tradeResponse.setEffectiveSecurities((boughtSecurities - soldSecurities));
                            e.stream().forEach((e1) -> {
                                tradeResponse.setAccount(e1.getAccount());
                                tradeResponse.setSecurityCode(e1.getSecurityCode());
                                tradeResponse.setTradeDataList(e);
                            });
                            tradeResponseList.add(tradeResponse);
                            potionBook.getCachedtraderesponselist().add(tradeResponse);
                        } else if (e.size() == 1) {
                            tradeResponse.setTradeDataList(e);
                            e.stream().forEach((e1) -> {
                                tradeResponse.setEffectiveSecurities(e1.getSecurityCount());
                                tradeResponse.setAccount(e1.getAccount());
                                tradeResponse.setSecurityCode(e1.getSecurityCode());
                                tradeResponseList.add(tradeResponse);
                                potionBook.getCachedtraderesponselist().add(tradeResponse);
                                System.out.println(e);
                            });
                        }
                    });
        });
        return tradeResponseList;

    }

    /**
     * @param tradeDataList
     * @return
     */
    public Map<String, List<TradeData>> findGroupByTradeType(List<TradeData> tradeDataList) {
        return tradeDataList.stream().filter(trade -> trade != null)
                .collect(Collectors.groupingBy(TradeData::getTradeType));

    }

    /**
     * @param groupByTradeType
     * @param tradeType
     * @return
     */
    public Integer findSecurityCountForTradeType(Map<String, List<TradeData>> groupByTradeType, String tradeType) {
        return groupByTradeType.get(tradeType).stream().filter(t -> t != null).collect(Collectors.summingInt(TradeData::getSecurityCount));
    }

    /**
     * @param groupByTradeType
     * @param tradeType
     * @param securityCount
     * @return
     */
    public Integer findSecurityCountForCancelledTradeType(Map<String, List<TradeData>> groupByTradeType, String tradeType, Integer securityCount) {
        if (!groupByTradeType.isEmpty() && groupByTradeType.containsKey("CANCEL") && groupByTradeType.containsKey(tradeType)) {

            List<TradeData> cancelBoughtTrade = groupByTradeType.get(tradeType).stream().filter(buy -> groupByTradeType.get("CANCEL").stream()
                    .anyMatch(cancel -> cancel.getTradeId().equals(buy.getTradeId()))).collect(Collectors.toList());
            System.out.println("Filtered List for cancelled trades" + cancelBoughtTrade);

            securityCount = securityCount - cancelBoughtTrade.stream().filter(buy -> buy.getTradeType().equals(tradeType)).collect(Collectors.summingInt(TradeData::getSecurityCount));

        }
        return securityCount;
    }
    /***
     *
     * @param tradeDataList
     * @return
     */
    public  Map<String, List<TradeData>> findGroupByAccount(List<TradeData> tradeDataList) {
        return tradeDataList.stream().filter(trade -> trade != null)
                .collect(Collectors.groupingBy(TradeData::getAccount));

    }



    /**
     *
     * @param tradeDataList
     * @return
     */
    public  Map<String, Map<String, List<TradeData>>> findGroupByAccountAndSecurity(List <TradeData> tradeDataList) {
        if (tradeDataList ==null || tradeDataList.isEmpty()) {
            System.out.println("Null or Empty input Trade data");
            throw new InvalidTradeException("Null or Empty input Trade data");
        }
        return tradeDataList.stream()
                .collect(Collectors.groupingBy(TradeData::getAccount,Collectors.groupingBy(TradeData::getSecurityCode)));




    }

}
