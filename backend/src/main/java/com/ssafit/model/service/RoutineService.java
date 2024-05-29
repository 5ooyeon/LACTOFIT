package com.ssafit.model.service;

import java.util.List;
import java.util.Map;

import com.ssafit.model.dto.Exercise;
import com.ssafit.model.dto.Routine;
import com.ssafit.model.dto.RoutineComponents;

public interface RoutineService {

	public Exercise getExerciseById(int exerciseId);

	public List<Exercise> getAllExercises();

	public int createRoutine(Routine routine);

	public void updateRoutine(Routine routine);

	public void deleteRoutine(int routineId);

	public Routine getRoutineById(int routineId);

	public List<Routine> getAllRoutines();

	public void addExercisesToRoutine(int routineId, List<RoutineComponents> exercises);

	public void updateRoutineComponent(RoutineComponents routineComponents);

	public void deleteRoutineComponent(int routineComponentsId);

	public RoutineComponents getRoutineComponentById(int routineComponentsId);

	public List<Map<String, Object>> getRoutineComponentsByRoutineId(int routineId);

	public List<RoutineComponents> getAllRoutineComponents();

	public List<List<Map<String, Object>>> getExercisesByRoutineId(int routineId);

	public List<Map<String, Object>> getRoutinesByUserId(int userId);

	List<Exercise> getExercisesByPart(String exercisePart);

	List<Exercise> searchExercises(String query);

	List<Map<String, Object>> getRoutineComponentsByUserId(int userId);

	public Exercise getExerciseDtoByExerciseTitle(String title);
}
