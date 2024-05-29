package com.ssafit.model.dao;

import java.util.List;
import com.ssafit.model.dto.Notification;

public interface NotificationDao {
	void createNotification(Notification notification);

	Notification getNotificationById(int notificationId);

	List<Notification> getNotificationsByUserId(int userId);

	void deleteNotification(int notificationId);

	void readNotification(int notificationId);

	// 읽은 알람 모두 제거하는 기능 만들기
	void deleteReadNotification(int userId);
}