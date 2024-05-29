package com.ssafit.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafit.model.dto.Exercise;
import com.ssafit.model.dto.RoutineComponents;
import com.ssafit.model.service.RoutineService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/exercises")
@Tag(name = "exerciseRestController", description = "운동 컨트롤러")
public class ExerciseRestController {

	@Autowired
	private RoutineService routineService;

	@GetMapping("/")
	@Operation(summary = "모든 운동을 조회합니다.")
	public ResponseEntity<List<Exercise>> getAllExercises() {
		List<Exercise> exercises = routineService.getAllExercises();
		return new ResponseEntity<>(exercises, HttpStatus.OK);
	}

	@PostMapping("/{routine_id}")
	@Operation(summary = "루틴에 운동을 추가합니다.")
	public ResponseEntity<Void> addExercisesToRoutine(@PathVariable("routine_id") int routineId,
			@RequestBody List<RoutineComponents> exercises) {
		routineService.addExercisesToRoutine(routineId, exercises);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/search/{exercise_part}")
	@Operation(summary = "특정 운동 부위의 운동만 반환합니다.")
	public ResponseEntity<List<Exercise>> getExercisesByPart(@PathVariable("exercise_part") String exercisePart) {
		List<Exercise> exercises = routineService.getExercisesByPart(exercisePart);
		if (exercises == null || exercises.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(exercises, HttpStatus.OK);
	}

	@GetMapping("/search")
	@Operation(summary = "운동명 또는 운동 부위에 포함된 운동을 반환합니다.")
	public ResponseEntity<List<Exercise>> searchExercises(@RequestParam("keyword") String keyword) {
		List<Exercise> exercises = routineService.searchExercises(keyword);
		if (exercises == null || exercises.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(exercises, HttpStatus.OK);
	}

	@GetMapping("/routine/{routine_id}")
	@Operation(summary = "루틴(운동)을 조회합니다.")
	public ResponseEntity<?> getExercisesByRoutineId(@PathVariable("routine_id") int routineId) {
		List<List<Map<String, Object>>> exercises = routineService.getExercisesByRoutineId(routineId);
		if (exercises == null || exercises.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(exercises, HttpStatus.OK);
	}
	
	@GetMapping("/searchbyname")
	public ResponseEntity<?> getExerciseInfoByExerciseTitle(@RequestParam("title") String title) {
		
		return new ResponseEntity<>(routineService.getExerciseDtoByExerciseTitle(title), HttpStatus.OK);
	}
}
