package com.ssafit.model.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ssafit.model.dto.Follow;

public interface FollowService {

	boolean toggleFollow(Follow follow);

	void deleteFollow(@Param("followId") int followId, @Param("userId") int userId);

	List<Map<String, Object>> getFollowers(int userId);

	List<Map<String, Object>> getFollowing(int followingUserId);

	public int countFollowers(int userId);

	public int countFollowing(int userId);
}
