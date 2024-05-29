package com.ssafit.model.dto;

public class Exercise {
	private int exerciseId;
	private String exerciseName;
	private String exerciseDesc;
	private String exercisePart;

	public Exercise() {
	}

	public Exercise(int exerciseId, String exerciseName, String exerciseDesc, String exercisePart) {
		this.exerciseId = exerciseId;
		this.exerciseName = exerciseName;
		this.exerciseDesc = exerciseDesc;
		this.exercisePart = exercisePart;
	}

	public int getExerciseId() {
		return exerciseId;
	}

	public void setExerciseId(int exerciseId) {
		this.exerciseId = exerciseId;
	}

	public String getExerciseName() {
		return exerciseName;
	}

	public void setExerciseName(String exerciseName) {
		this.exerciseName = exerciseName;
	}

	public String getExerciseDesc() {
		return exerciseDesc;
	}

	public void setExerciseDesc(String exerciseDesc) {
		this.exerciseDesc = exerciseDesc;
	}

	public String getExercisePart() {
		return exercisePart;
	}

	public void setExercisePart(String exercisePart) {
		this.exercisePart = exercisePart;
	}

	@Override
	public String toString() {
		return "Exercise [exerciseId=" + exerciseId + ", exerciseName=" + exerciseName + ", exerciseDesc="
				+ exerciseDesc + ", exercisePart=" + exercisePart + "]";
	};

}
