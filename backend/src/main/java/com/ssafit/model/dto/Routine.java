package com.ssafit.model.dto;

import java.sql.Timestamp;
import java.util.List;

public class Routine {
	private int routineId;
	private int userId;
	private String routineName;
	private String routineDesc;
	private Timestamp routineRegDate;
	private Timestamp routineUpdateDate;

	public Routine() {
	}

	public Routine(int routineId, int userId, String routineName, String routineDesc, Timestamp routineRegDate,
			Timestamp routineUpdateDate) {
		this.routineId = routineId;
		this.userId = userId;
		this.routineName = routineName;
		this.routineDesc = routineDesc;
		this.routineRegDate = routineRegDate;
		this.routineUpdateDate = routineUpdateDate;
	}

	public int getRoutineId() {
		return routineId;
	}

	public void setRoutineId(int routineId) {
		this.routineId = routineId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getRoutineName() {
		return routineName;
	}

	public void setRoutineName(String routineName) {
		this.routineName = routineName;
	}

	public String getRoutineDesc() {
		return routineDesc;
	}

	public void setRoutineDesc(String routineDesc) {
		this.routineDesc = routineDesc;
	}

	public Timestamp getRoutineRegDate() {
		return routineRegDate;
	}

	public void setRoutineRegDate(Timestamp routineRegDate) {
		this.routineRegDate = routineRegDate;
	}

	public Timestamp getRoutineUpdateDate() {
		return routineUpdateDate;
	}

	public void setRoutineUpdateDate(Timestamp routineUpdateDate) {
		this.routineUpdateDate = routineUpdateDate;
	}

	@Override
	public String toString() {
		return "Routine [routineId=" + routineId + ", userId=" + userId + ", routineName=" + routineName
				+ ", routineDesc=" + routineDesc + ", routineRegDate=" + routineRegDate + ", routineUpdateDate="
				+ routineUpdateDate + "]";
	}

}
