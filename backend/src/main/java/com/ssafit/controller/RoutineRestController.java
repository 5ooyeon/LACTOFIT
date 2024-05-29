package com.ssafit.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafit.model.dto.Routine;
import com.ssafit.model.service.RoutineService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/routines")
@Tag(name = "routineRestController", description = "루틴 컨트롤러")
public class RoutineRestController {

	@Autowired
	private RoutineService routineService;

	@PostMapping("/")
	@Operation(summary = "루틴을 등록합니다.")
	public ResponseEntity<Routine> createRoutine(@RequestBody Routine routine) {
		int id = routineService.createRoutine(routine);
		routine.setRoutineId(id);
		return new ResponseEntity<>(routine, HttpStatus.CREATED);
	}

	@PutMapping("/{routine_id}")
	@Operation(summary = "루틴을 수정합니다.")
	public ResponseEntity<Routine> updateRoutine(@PathVariable("routine_id") int routineId,
			@RequestBody Routine routine) {
		routine.setRoutineId(routineId);
		routineService.updateRoutine(routine);
		Routine updatedRoutine = routineService.getRoutineById(routineId);
		if (updatedRoutine != null) {
			return new ResponseEntity<>(updatedRoutine, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/{routine_id}")
	@Operation(summary = "루틴을 삭제합니다.")
	public ResponseEntity<Void> deleteRoutine(@PathVariable("routine_id") int routineId) {
		routineService.deleteRoutine(routineId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/{routine_id}")
	@Operation(summary = "루틴(이름, 설명 등)을 조회합니다.")
	public ResponseEntity<Routine> getRoutineById(@PathVariable("routine_id") int routineId) {
		Routine routine = routineService.getRoutineById(routineId);
		if (routine != null) {
			return new ResponseEntity<>(routine, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/")
	@Operation(summary = "모든 루틴을 조회합니다.")
	public ResponseEntity<List<Routine>> getAllRoutines() {
		List<Routine> routines = routineService.getAllRoutines();
		return new ResponseEntity<>(routines, HttpStatus.OK);
	}

	@GetMapping("/users/{user_id}")
	@Operation(summary = "유저ID로 루틴을 조회합니다.")
	public ResponseEntity<List<Map<String, Object>>> getRoutinesByUserId(@PathVariable("user_id") int userId) {
		List<Map<String, Object>> routines = routineService.getRoutinesByUserId(userId);
		if (routines == null || routines.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(routines, HttpStatus.OK);
	}

	// 유저id > 유저가 한 componets group by part

	@GetMapping("analysis/{user_id}")
	@Operation(summary = "유저ID로 루틴 컴포넌츠를 조회하고 부위별로 분류합니다.")
	public ResponseEntity<?> getRoutineComponentsByUserId(@PathVariable("user_id") int userId) {
		List<Map<String, Object>> routinesComponents = routineService.getRoutineComponentsByUserId(userId);
		if (routinesComponents == null || routinesComponents.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(routinesComponents, HttpStatus.OK);
	}
}
