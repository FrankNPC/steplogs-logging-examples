package io.steplogs.example.web.model;

import java.util.Date;

public class User {
	
	private long id;
	
	private String name;
	
	private Integer age;
	
	private Date birthday;
	
	private String driverLisenceId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getDriverLisenceId() {
		return driverLisenceId;
	}

	public void setDriverLisenceId(String driverLisenceId) {
		this.driverLisenceId = driverLisenceId;
	}
	
}
