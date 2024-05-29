package com.ssafit.model.dto;

public class RoutineComponents {
	private int routineComponentsId;
	private int exerciseId;
	private int routineId;
	private int routineComponentsReps;
	private int routineComponentsWeight;
	private Exercise exercise;

	public RoutineComponents() {
	};

	public RoutineComponents(int routineComponentsId, int exerciseId, int routineId, int routineComponentsReps,
			int routineComponentsWeight, Exercise exercise) {
		this.routineComponentsId = routineComponentsId;
		this.exerciseId = exerciseId;
		this.routineId = routineId;
		this.routineComponentsReps = routineComponentsReps;
		this.routineComponentsWeight = routineComponentsWeight;
		this.exercise = exercise;
	}

	public int getRoutineComponentsId() {
		return routineComponentsId;
	}

	public void setRoutineComponentsId(int routineComponentsId) {
		this.routineComponentsId = routineComponentsId;
	}

	public int getExerciseId() {
		return exerciseId;
	}

	public void setExerciseId(int exerciseId) {
		this.exerciseId = exerciseId;
	}

	public int getRoutineId() {
		return routineId;
	}

	public void setRoutineId(int routineId) {
		this.routineId = routineId;
	}

	public int getRoutineComponentsReps() {
		return routineComponentsReps;
	}

	public void setRoutineComponentsReps(int routineComponentsReps) {
		this.routineComponentsReps = routineComponentsReps;
	}

	public int getRoutineComponentsWeight() {
		return routineComponentsWeight;
	}

	public void setRoutineComponentsWeight(int routineComponentsWeight) {
		this.routineComponentsWeight = routineComponentsWeight;
	}

	@Override
	public String toString() {
		return "RoutineComponents [routineComponentsId=" + routineComponentsId + ", exerciseId=" + exerciseId
				+ ", routineId=" + routineId + ", routineComponentsReps=" + routineComponentsReps
				+ ", routineComponentsWeight=" + routineComponentsWeight + ", exercise=" + exercise + "]";
	}

}
