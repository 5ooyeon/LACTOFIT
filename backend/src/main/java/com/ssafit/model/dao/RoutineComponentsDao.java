package com.ssafit.model.dao;

import java.util.List;
import java.util.Map;

import com.ssafit.model.dto.RoutineComponents;

public interface RoutineComponentsDao {
	void insertRoutineComponent(RoutineComponents routineComponents);

	RoutineComponents getRoutineComponentById(int routineComponentsId);

	void updateRoutineComponent(RoutineComponents routineComponents);

	void deleteRoutineComponent(int routineComponentsId);

	List<Map<String, Object>> getRoutineComponentsByRoutineId(int routineId);

	List<RoutineComponents> getAllRoutineComponents();

	List<Map<String, Object>> getRoutineComponentsByUserId(int userId);

}
