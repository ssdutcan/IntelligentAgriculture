package yagami.model;

import java.io.Serializable;

public class MarketInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String marketName;
	private String marketCrop;
	
	public MarketInfo() {
		super();
	}

	public MarketInfo(String id, String marketName, String marketCrop) {
		super();
		this.id = id;
		this.marketName = marketName;
		this.marketCrop = marketCrop;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public String getMarketCrop() {
		return marketCrop;
	}

	public void setMarketCrop(String marketCrop) {
		this.marketCrop = marketCrop;
	}
	
	public String toString() {
		return "marketInfo [id=" + id + ", marketName=" + marketName + ", marketCrop="
		+ marketCrop + "]";
	}
	
}
