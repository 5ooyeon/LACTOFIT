package com.ssafit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafit.model.dto.Notification;
import com.ssafit.model.service.NotificationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/notifications")
@Tag(name = "notificationRestController", description = "알림 컨트롤러")
public class NotificationRestController {

	@Autowired
	private NotificationService notificationService;

	@PostMapping("/")
	@Operation(summary = "알림을 생성합니다.")
	public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
		notificationService.createNotification(notification);
		return new ResponseEntity<>(notification, HttpStatus.CREATED);
	}

	@GetMapping("/detail/{notification_id}")
	@Operation(summary = "상세 알림을 조회합니다.")
	public ResponseEntity<Notification> getNotificationById(@PathVariable("notification_id") int notificationId) {
		Notification notification = notificationService.getNotificationById(notificationId);
		if (notification != null) {
			return new ResponseEntity<>(notification, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/{user_id}")
	@Operation(summary = "전체 알림을 조회합니다.")
	public ResponseEntity<List<Notification>> getNotificationsByUserId(@PathVariable("user_id") int userId) {
		List<Notification> notifications = notificationService.getNotificationsByUserId(userId);
		return new ResponseEntity<>(notifications, HttpStatus.OK);
	}

	@DeleteMapping("/{notification_id}")
	@Operation(summary = "알림을 삭제합니다.")
	public ResponseEntity<?> deleteNotification(@PathVariable("notification_id") int notificationId) {
		notificationService.deleteNotification(notificationId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping("/{notification_id}")
	@Operation(summary = "알림 읽음처리 합니다.")
	public ResponseEntity<?> readNotification(@PathVariable("notification_id") int notificationId) {
		notificationService.readNotification(notificationId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/read/{user_id}")
	@Operation(summary = "읽은 알림을 삭제합니다.")
	public ResponseEntity<?> deleteReadNotification(@PathVariable("user_id") int userId) {
		notificationService.deleteReadNotification(userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
