package com.ssafit.model.dto;

import java.sql.Timestamp;

public class BoardLikes {
	private int boardLikesId;
	private int userId;
	private int boardId;
	private Timestamp boardLikesRegDate;

	public BoardLikes() {
	}

	public BoardLikes(int boardLikesId, int userId, int boardId, Timestamp boardLikesRegDate) {
		this.boardLikesId = boardLikesId;
		this.userId = userId;
		this.boardId = boardId;
		this.boardLikesRegDate = boardLikesRegDate;
	}

	public int getBoardLikesId() {
		return boardLikesId;
	}

	public void setBoardLikesId(int boardLikesId) {
		this.boardLikesId = boardLikesId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public Timestamp getBoardLikesRegDate() {
		return boardLikesRegDate;
	}

	public void setBoardLikesRegDate(Timestamp boardLikesRegDate) {
		this.boardLikesRegDate = boardLikesRegDate;
	}

	@Override
	public String toString() {
		return "BoardLikes [boardLikesId=" + boardLikesId + ", userId=" + userId + ", boardId=" + boardId
				+ ", boardLikesRegDate=" + boardLikesRegDate + "]";
	}
}
