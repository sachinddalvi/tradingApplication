package cam.sdalvi.trade.dto;

import cam.sdalvi.trade.exceptions.InvalidTradeException;

import java.util.Arrays;
import java.util.Locale;

public class TradeData {
	private Long tradeId;
	private String tradeType;
	private String account;
	private String securityCode;
	private int securityCount;

	public TradeData() {
	}

	public TradeData(Long tradeId, String tradeType, String account, String securityCode, int securityCount) {
		this.tradeId = tradeId;
		this.tradeType = tradeType;
		this.account = account;
		this.securityCode = securityCode;
		this.securityCount = securityCount;
	}

	public Long getTradeId() {
		return tradeId;
	}
	public void setTradeId(Long tradeId) {
		this.tradeId = tradeId;	}
	
	public String getTradeType() {
		return tradeType.toUpperCase(Locale.ROOT);
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType.toUpperCase(Locale.ROOT);
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
	public int getSecurityCount() {
		return securityCount;
	}
	public void setSecurityCount(int securityCount) {
		this.securityCount = securityCount;
	}

	public void validateTradeType() {
		if (this.tradeType.isEmpty())
			throw new InvalidTradeException("TradeType Cannot be null or empty");
		System.out.println(" Validating tradeType " +tradeType);
		Arrays.stream(TradeType.values()).forEach(e -> System.out.println("From Enum value "+e));
		Arrays.stream(TradeType.values()).filter(e -> e.toString().equals(tradeType)).findAny().orElseThrow(()->  new InvalidTradeException("TradeId is not Listed"));
			}

	public void validateAccount() {
		if (this.account.isEmpty())
			throw new InvalidTradeException("account Cannot be null or empty");
	}

	public void validateSecurityCode() {
		if (this.securityCode.isEmpty())
			throw new InvalidTradeException("SecurityCode Cannot be null or empty");
	}

	public void validateTradeId() {
		if (this.tradeId ==null)
			throw new InvalidTradeException("TradeId Cannot be null or empty");

	}

	@Override
	public String toString() {
		return "TradeData{" +
				"tradeId=" + tradeId +
				", tradeType='" + tradeType + '\'' +
				", account='" + account + '\'' +
				", securityCode='" + securityCode + '\'' +
				", securityCount=" + securityCount +
				'}';
	}
}
