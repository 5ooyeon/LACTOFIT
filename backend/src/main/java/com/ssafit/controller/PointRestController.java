package com.ssafit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafit.model.dto.Point;
import com.ssafit.model.service.PointService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/points")
@Tag(name = "pointRestController", description = "포인트 획득/사용/총량 ")
public class PointRestController {

	@Autowired
	private PointService pointService;

	@PostMapping("/")
	@Operation(summary = "포인트 획득/사용을 기록합니다.")
	public ResponseEntity<Void> addPoint(@RequestBody Point point) {
		pointService.trackPoint(point);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("/{user_id}")
	@Operation(summary = "포인트 총량을 출력합니다.")
	public ResponseEntity<Integer> getPointsSumByUserId(@PathVariable("user_id") int userId) {
		int pointsSum = pointService.getPointsSumByUserId(userId);
		return new ResponseEntity<>(pointsSum, HttpStatus.OK);
	}

	@GetMapping("/desc/{user_id}")
	@Operation(summary = "포인트 기록을 출력합니다.")
	public ResponseEntity<List<Point>> getPointDescByUserId(@PathVariable("user_id") int userId) {
		List<Point> pointDesc = pointService.getPointDescByUserId(userId);
		if (pointDesc == null || pointDesc.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(pointDesc, HttpStatus.OK);
	}
}
