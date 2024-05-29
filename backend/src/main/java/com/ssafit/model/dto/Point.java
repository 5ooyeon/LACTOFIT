package com.ssafit.model.dto;

import java.sql.Timestamp;

public class Point {
	private int pointId;
	private int userId;
	private int pointAmount;
	private String pointDesc;
	private Timestamp pointRegDate;

	public Point() {
	}

	public Point(int pointId, int userId, int pointAmount, String pointDesc, Timestamp pointRegDate) {
		this.pointId = pointId;
		this.userId = userId;
		this.pointAmount = pointAmount;
		this.pointDesc = pointDesc;
		this.pointRegDate = pointRegDate;
	}

	public int getPointId() {
		return pointId;
	}

	public void setPointId(int pointId) {
		this.pointId = pointId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getPointAmount() {
		return pointAmount;
	}

	public void setPointAmount(int pointAmount) {
		this.pointAmount = pointAmount;
	}

	public String getPointDesc() {
		return pointDesc;
	}

	public void setPointDesc(String pointDesc) {
		this.pointDesc = pointDesc;
	}

	public Timestamp getPointRegDate() {
		return pointRegDate;
	}

	public void setPointRegDate(Timestamp pointRegDate) {
		this.pointRegDate = pointRegDate;
	}

	@Override
	public String toString() {
		return "Point [pointId=" + pointId + ", userId=" + userId + ", pointAmount=" + pointAmount + ", pointDesc="
				+ pointDesc + ", pointRegDate=" + pointRegDate + "]";
	}

	

}
