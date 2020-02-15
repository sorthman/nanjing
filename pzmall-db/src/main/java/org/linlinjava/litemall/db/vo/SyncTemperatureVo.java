package org.linlinjava.litemall.db.vo;

public class SyncTemperatureVo {

	private String name;
	private String idCard;
	private String phone;
	private String arrivalDate;
	private String temp;
	private String measureDatetime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getMeasureDatetime() {
		return measureDatetime;
	}

	public void setMeasureDatetime(String measureDatetime) {
		this.measureDatetime = measureDatetime;
	}

}
