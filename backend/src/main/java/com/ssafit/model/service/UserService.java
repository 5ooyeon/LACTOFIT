package com.ssafit.model.service;

import com.ssafit.model.dto.User;

public interface UserService {
	void createUser(User user);

	User getUserById(int userId);

	User getUserByTag(String userTag);

	void updateUser(User user);

	void deleteUser(int userId);

	void updateStreakColor(int userId, String userStreakColor);

	void updateStreakPrice(int userId, int userStreakPrice);

	User getUserByUserTag(String userTag);
}
