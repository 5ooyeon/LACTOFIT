package com.ssafit.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ssafit.model.dto.Follow;

public interface FollowDao {

	void createFollow(Follow follow);

	void deleteFollow(@Param("followId") int followId, @Param("userId") int userId);

	Follow getFollowByUsers(@Param("userId") int userId, @Param("followingUserId") int followingUserId);

	List<Map<String, Object>> getFollowersByUserId(int userId);

	List<Map<String, Object>> getFollowingByUserId(int followingUserId);

	int countFollowers(int userId);

	int countFollowing(int userId);
}
