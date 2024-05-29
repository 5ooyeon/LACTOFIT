package com.ssafit.model.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafit.model.dao.ExerciseDao;
import com.ssafit.model.dao.RoutineComponentsDao;
import com.ssafit.model.dao.RoutineDao;
import com.ssafit.model.dto.Exercise;
import com.ssafit.model.dto.Routine;
import com.ssafit.model.dto.RoutineComponents;

@Service
public class RoutineServiceImpl implements RoutineService {

	@Autowired
	private ExerciseDao exerciseDao;

	@Autowired
	private RoutineDao routineDao;

	@Autowired
	private RoutineComponentsDao routineComponentsDao;

	@Override
	public Exercise getExerciseById(int exerciseId) {
		return exerciseDao.getExerciseById(exerciseId);
	}

	@Override
	public List<Exercise> getAllExercises() {
		return exerciseDao.getAllExercises();
	}

	@Override
	public int createRoutine(Routine routine) {
		routineDao.insertRoutine(routine);
		return routine.getRoutineId();
	}

	@Override
	public void updateRoutine(Routine routine) {
		routineDao.updateRoutine(routine);
	}

	@Override
	public void deleteRoutine(int routineId) {
		routineDao.deleteRoutine(routineId);
	}

	@Override
	public Routine getRoutineById(int routineId) {
		return routineDao.getRoutineById(routineId);
	}

	@Override
	public List<Routine> getAllRoutines() {
		return routineDao.getAllRoutines();
	}

	@Override
	public void addExercisesToRoutine(int routineId, List<RoutineComponents> exercises) {
		for (RoutineComponents exercise : exercises) {
			routineComponentsDao.insertRoutineComponent(exercise);
		}
	}

	@Override
	public void updateRoutineComponent(RoutineComponents routineComponents) {
		routineComponentsDao.updateRoutineComponent(routineComponents);
	}

	@Override
	public void deleteRoutineComponent(int routineComponentsId) {
		routineComponentsDao.deleteRoutineComponent(routineComponentsId);
	}

	@Override
	public RoutineComponents getRoutineComponentById(int routineComponentsId) {
		return routineComponentsDao.getRoutineComponentById(routineComponentsId);
	}

	@Override
	public List<Map<String, Object>> getRoutineComponentsByRoutineId(int routineId) {
		return routineComponentsDao.getRoutineComponentsByRoutineId(routineId);
	}

	@Override
	public List<RoutineComponents> getAllRoutineComponents() {
		return routineComponentsDao.getAllRoutineComponents();
	}

	@Override
	public List<List<Map<String, Object>>> getExercisesByRoutineId(int routineId) {
		return routineDao.getExercisesByRoutineId(routineId);
	}

	@Override
	public List<Map<String, Object>> getRoutinesByUserId(int userId) {
		List<Map<String, Object>> routines = routineDao.getRoutinesByUserId(userId);

		Map<String, Map<String, Object>> groupedRoutines = new LinkedHashMap<>();
		for (Map<String, Object> routine : routines) {
			String routineName = (String) routine.get("routineName");
			if (!groupedRoutines.containsKey(routineName)) {
				Map<String, Object> routineGroup = new LinkedHashMap<>();
				routineGroup.put("routineName", routineName);
				routineGroup.put("routineDesc", routine.get("routineDesc"));
				routineGroup.put("routineId", routine.get("routineId"));
				routineGroup.put("userId", routine.get("userId"));
				routineGroup.put("routineRegDate", routine.get("routineRegDate"));
				routineGroup.put("routineUpdateDate", routine.get("routineUpdateDate"));
				routineGroup.put("exercises", new ArrayList<Map<String, Object>>());
				groupedRoutines.put(routineName, routineGroup);
			}
			Map<String, Object> exercise = new LinkedHashMap<>();
			exercise.put("exerciseName", routine.get("exerciseName"));
			exercise.put("exerciseDesc", routine.get("exerciseDesc"));
			exercise.put("exerciseId", routine.get("exerciseId"));
			exercise.put("routineComponentsId", routine.get("routineComponentsId"));
			exercise.put("exercisePart", routine.get("exercisePart"));
			exercise.put("routineComponentsReps", routine.get("routineComponentsReps"));
			exercise.put("routineComponentsWeight", routine.get("routineComponentsWeight"));

			((List<Map<String, Object>>) groupedRoutines.get(routineName).get("exercises")).add(exercise);
		}

		return new ArrayList<>(groupedRoutines.values());
	}

	@Override
	public List<Exercise> getExercisesByPart(String part) {
		return exerciseDao.getExercisesByPart(part);
	}

	@Override
	public List<Exercise> searchExercises(String keyword) {
		return exerciseDao.searchExercises(keyword);
	}

	@Override
	public List<Map<String, Object>> getRoutineComponentsByUserId(int userId) {
		return routineComponentsDao.getRoutineComponentsByUserId(userId);
	}

	@Override
	public Exercise getExerciseDtoByExerciseTitle(String title) {
		// TODO Auto-generated method stub
		return exerciseDao.getExerciseByExerciseName(title);
	}

}
