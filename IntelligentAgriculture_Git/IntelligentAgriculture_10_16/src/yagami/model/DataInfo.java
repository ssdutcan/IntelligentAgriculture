package yagami.model;

import java.io.Serializable;

public class DataInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String time;
	private String CO2;
	private String temperature;
	private String humidity;
	private String light;
	
	public DataInfo(String id, String time, String co2, String temperature,
			String humidity, String light) {
		super();
		this.id = id;
		this.time = time;
		CO2 = co2;
		this.temperature = temperature;
		this.humidity = humidity;
		this.light = light;
	}

	public DataInfo() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCO2() {
		return CO2;
	}

	public void setCO2(String co2) {
		CO2 = co2;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getLight() {
		return light;
	}

	public void setLight(String light) {
		this.light = light;
	}
	
	
	
}	
