package yagami.model;

import java.io.Serializable;

public class FarmInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	
	public FarmInfo(String name) {
		super();
		this.name = name;
	}

	public FarmInfo() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
