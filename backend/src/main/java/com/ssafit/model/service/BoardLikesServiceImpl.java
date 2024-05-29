package com.ssafit.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafit.model.dao.BoardLikesDao;
import com.ssafit.model.dao.BoardDao;
import com.ssafit.model.dto.BoardLikes;
import com.ssafit.model.dto.Board;
import com.ssafit.model.dto.Notification;

@Service
public class BoardLikesServiceImpl implements BoardLikesService {

	@Autowired
	private BoardLikesDao boardLikesDao;

	@Autowired
	private BoardDao boardDao;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private UserService userService;

	@Override
	public boolean toggleLike(BoardLikes boardLikes) {
		BoardLikes existingLike = boardLikesDao.getLikeByUsers(boardLikes.getUserId(), boardLikes.getBoardId());
		if (existingLike != null) {
			boardLikesDao.removeLike(boardLikes.getUserId(), boardLikes.getBoardId());
			return false;
		} else {
			boardLikesDao.addLike(boardLikes);

			// 게시글 정보 가져오기
			Board board = boardDao.getBoardById(boardLikes.getBoardId());

			// 알림 생성
			Notification notification = new Notification();
			notification.setUserId(board.getUserId());
			notification.setNotificationContent(userService.getUserById(boardLikes.getUserId()).getUserNickname()
					+ "(이)가 좋아요 하였습니다. : " + board.getBoardContent());
			notification.setNotificationRead(false);
			notificationService.createNotification(notification);

			return true;
		}
	}

	@Override
	public List<Map<String, Object>> getLikesCountByBoardId(int boardId) {
		return boardLikesDao.getLikesCountByBoardId(boardId);
	}
}
