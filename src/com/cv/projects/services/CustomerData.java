package com.cv.projects.services;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CustomerData {
	int id;
	String name, dob, country, address, phoneno;
	
	public CustomerData() {
		// TODO Auto-generated constructor stub
	}
	
	@XmlAttribute
	public int getId () {
		return id;
	}
	public void setId (int id) {
		this.id = id;
	}
	
	@XmlElement
	public String getName () {
		return name;
	}
	public void setName (String name) {
		this.name = name;
	}
	
	@XmlElement
	public String getDob () {
		return dob;
	}
	public void setDob (String dob) {
		this.dob = dob;
	}
	
	@XmlElement
	public String getCountry () {
		return country;
	}
	public void setCountry (String country) {
		this.country = country;
	}
	
	@XmlElement
	public String getAddress (String address) {
		return address;
	}
	public void setAddress (String address) {
		this.address = address;
	}
	
	@XmlElement
	public String getPhoneno () {
		return phoneno;
	}
	public void setPhoneno (String phoneno) {
		this.phoneno = phoneno;
	}
}
