package com.ssafit.model.service;

import com.ssafit.model.dao.UserDao;
import com.ssafit.model.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public void createUser(User user) {
		userDao.createUser(user);
	}

	@Override
	public User getUserById(int userId) {
		return userDao.getUserById(userId);
	}

	@Override
	public User getUserByTag(String userTag) {
		return userDao.getUserByTag(userTag);
	}

	@Override
	public void updateUser(User user) {
		userDao.updateUser(user);
	}

	@Override
	public void deleteUser(int userId) {
		userDao.deleteUser(userId);
	}

	@Override
	public void updateStreakColor(int userId, String userStreakColor) {
		userDao.updateStreakColor(userId, userStreakColor);
	}

	@Override
	public void updateStreakPrice(int userId, int userStreakPrice) {
		userDao.updateStreakPrice(userId, userStreakPrice);
	}

	@Override
	public User getUserByUserTag(String userTag) {
		return userDao.getUserByTag(userTag);
	}
}
