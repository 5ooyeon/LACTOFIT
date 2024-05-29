package com.ssafit.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafit.model.dao.FollowDao;
import com.ssafit.model.dto.Follow;
import com.ssafit.model.dto.Notification;

@Service
public class FollowServiceImpl implements FollowService {

	@Autowired
	private FollowDao followDao;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private UserService userService;

	@Override
	public boolean toggleFollow(Follow follow) {
		Follow alreadyFollow = followDao.getFollowByUsers(follow.getUserId(), follow.getFollowingUserId());
		if (alreadyFollow != null) {
			followDao.deleteFollow(alreadyFollow.getFollowId(), follow.getUserId());
			return false;
		} else {
			followDao.createFollow(follow);
			Notification notification = new Notification();
			notification.setUserId(follow.getFollowingUserId());
			notification.setNotificationContent(
					userService.getUserById(follow.getUserId()).getUserNickname() + "(이)가 팔로우 하였습니다.");
			notification.setNotificationRead(false);
			notificationService.createNotification(notification);
			return true;
		}
	}

	@Override
	public void deleteFollow(int followId, int userId) {
		followDao.deleteFollow(followId, userId);
	}

	@Override
	public List<Map<String, Object>> getFollowers(int userId) {
		return followDao.getFollowersByUserId(userId);
	}

	@Override
	public List<Map<String, Object>> getFollowing(int followingUserId) {
		return followDao.getFollowingByUserId(followingUserId);
	}

	@Override
	public int countFollowers(int userId) {
		return followDao.countFollowers(userId);
	}

	@Override
	public int countFollowing(int userId) {
		return followDao.countFollowing(userId);
	}
}
