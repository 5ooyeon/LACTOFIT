package com.ssafit.model.dao;

import java.util.List;

import com.ssafit.model.dto.Exercise;

public interface ExerciseDao {
	Exercise getExerciseById(int exerciseId);

	List<Exercise> getAllExercises();

	List<Exercise> getExercisesByPart(String part);

	List<Exercise> searchExercises(String keyword);

	Exercise getExerciseByExerciseName(String name);
}
