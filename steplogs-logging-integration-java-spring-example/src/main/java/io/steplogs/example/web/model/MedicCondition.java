package io.steplogs.example.web.model;

public class MedicCondition {
	
	private long id;
	
	private long timeCreated;
	
	private String deceases;
	
	private long userId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTimeCreated() {
		return timeCreated;
	}

	public void setTimeCreated(long timeCreated) {
		this.timeCreated = timeCreated;
	}

	public String getDeceases() {
		return deceases;
	}

	public void setDeceases(String deceases) {
		this.deceases = deceases;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

}
