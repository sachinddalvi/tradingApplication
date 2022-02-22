package cam.sdalvi.trade.dto;

import java.util.List;

public class TradeResponse {
	

	private String account;
	private String securityCode;
	private int effectiveSecurities;
	private List <TradeData> tradeDataList;


	public TradeResponse() {
	}

	public TradeResponse(String account, String securityCode, int effectiveSecurities, List<TradeData> tradeDataList) {
		this.account = account;
		this.securityCode = securityCode;
		this.effectiveSecurities = effectiveSecurities;
		this.tradeDataList = tradeDataList;
	}



	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getSecurityCode() {
		return securityCode;
	}
	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}
	public int getEffectiveSecurities() {
		return effectiveSecurities;
	}
	public void setEffectiveSecurities(int effectiveSecurities) {
		this.effectiveSecurities = effectiveSecurities;
	}
	public List<TradeData> getTradeDataList() {
		return tradeDataList;
	}
	public void setTradeDataList(List<TradeData> tradeDataList) {
		this.tradeDataList = tradeDataList;
	}


	@Override
	public String toString() {
		return "TradeResponse{" +
				"account='" + account + '\'' +
				", securityCode='" + securityCode + '\'' +
				", effectiveSecurities=" + effectiveSecurities +
				", tradeDataList=" + tradeDataList +
				'}';
	}
}
