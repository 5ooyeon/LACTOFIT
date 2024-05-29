package com.ssafit.model.dto;

import java.sql.Timestamp;

public class Follow {
	private int followId;
	private int userId;
	private int followingUserId;
	private Timestamp followRegDate;

	public Follow() {
	}

	public Follow(int followId, int userId, int followingUserId, Timestamp followRegDate) {
		this.followId = followId;
		this.userId = userId;
		this.followingUserId = followingUserId;
		this.followRegDate = followRegDate;
	}

	public int getFollowId() {
		return followId;
	}

	public void setFollowId(int followId) {
		this.followId = followId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getFollowingUserId() {
		return followingUserId;
	}

	public void setFollowingUserId(int followingUserId) {
		this.followingUserId = followingUserId;
	}

	public Timestamp getFollowRegDate() {
		return followRegDate;
	}

	public void setFollowRegDate(Timestamp followRegDate) {
		this.followRegDate = followRegDate;
	}

	@Override
	public String toString() {
		return "Follow [followId=" + followId + ", userId=" + userId + ", followingUserId=" + followingUserId
				+ ", followRegDate=" + followRegDate + "]";
	}

}
