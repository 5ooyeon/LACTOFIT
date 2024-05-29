package com.ssafit.model.dao;

import com.ssafit.model.dto.User;
import org.apache.ibatis.annotations.Mapper;

public interface UserDao {
	void createUser(User user);

	User getUserById(int userId);

	User getUserByTag(String userTag);

	void updateUser(User user);

	void deleteUser(int userId);

	void updateStreakColor(int userId, String userStreakColor);

	void updateStreakPrice(int userId, int userStreakPrice);
}
