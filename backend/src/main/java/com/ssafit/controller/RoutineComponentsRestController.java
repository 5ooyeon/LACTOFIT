package com.ssafit.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafit.model.dto.RoutineComponents;
import com.ssafit.model.service.RoutineService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/routinecomponents")
@Tag(name = "routineComponentsRestController", description = "루틴 컴포넌트 컨트롤러")
public class RoutineComponentsRestController {

	@Autowired
	private RoutineService routineService;

	@PutMapping("/{routineComponents_id}")
	@Operation(summary = "루틴 컴포넌트를 수정합니다.")
	public ResponseEntity<RoutineComponents> updateRoutineComponent(
			@PathVariable("routineComponents_id") int routineComponentsId,
			@RequestBody RoutineComponents routineComponents) {
		routineComponents.setRoutineComponentsId(routineComponentsId);
		routineService.updateRoutineComponent(routineComponents);
		RoutineComponents updated = routineService.getRoutineComponentById(routineComponentsId);

		if (updated != null) {
			return new ResponseEntity<>(updated, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/{routineComponents_id}")
	@Operation(summary = "루틴 컴포넌트를 삭제합니다.")
	public ResponseEntity<Void> deleteRoutineComponent(@PathVariable("routineComponents_id") int routineComponentsId) {
		routineService.deleteRoutineComponent(routineComponentsId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/{routineComponents_id}")
	@Operation(summary = "루틴 컴포넌트를 조회합니다.")
	public ResponseEntity<RoutineComponents> getRoutineComponentById(
			@PathVariable("routineComponents_id") int routineComponentsId) {
		RoutineComponents routineComponents = routineService.getRoutineComponentById(routineComponentsId);
		if (routineComponents != null) {
			return new ResponseEntity<>(routineComponents, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/routine/{routine_id}")
	@Operation(summary = "루틴 ID로 루틴 컴포넌트를 조회합니다.")
	public ResponseEntity<Map<String, Object>> getRoutineComponentsByRoutineId(
			@PathVariable("routine_id") int routineId) {
		List<Map<String, Object>> routineComponents = routineService.getRoutineComponentsByRoutineId(routineId);
		return new ResponseEntity(routineComponents, HttpStatus.OK);
	}

	@GetMapping("/")
	@Operation(summary = "모든 루틴 컴포넌트를 조회합니다.")
	public ResponseEntity<List<RoutineComponents>> getAllRoutineComponents() {
		List<RoutineComponents> routineComponents = routineService.getAllRoutineComponents();
		return new ResponseEntity<>(routineComponents, HttpStatus.OK);
	}
}
