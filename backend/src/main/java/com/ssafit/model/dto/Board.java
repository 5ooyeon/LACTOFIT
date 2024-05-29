package com.ssafit.model.dto;

import java.sql.Timestamp;

public class Board {
	
	private int boardId;
	private int userId;
	private int routineId;
	private String boardContent;
	private String boardImgUrl;
	private int boardViewCnt;
	private Timestamp boardRegDate;
	private Timestamp boardUpdateDate;
	private boolean boardVisibility;

	public Board() {
	};

	public Board(int boardId, int userId, int routineId, String boardContent, String boardImgUrl, int boardViewCnt,
			Timestamp boardRegDate, Timestamp boardUpdateDate, boolean boardVisibility) {
		this.boardId = boardId;
		this.userId = userId;
		this.routineId = routineId;
		this.boardContent = boardContent;
		this.boardImgUrl = boardImgUrl;
		this.boardViewCnt = boardViewCnt;
		this.boardRegDate = boardRegDate;
		this.boardUpdateDate = boardUpdateDate;
		this.boardVisibility = boardVisibility;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getRoutineId() {
		return routineId;
	}

	public void setRoutineId(int routineId) {
		this.routineId = routineId;
	}

	public String getBoardContent() {
		return boardContent;
	}

	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}

	public String getBoardImgUrl() {
		return boardImgUrl;
	}

	public void setBoardImgUrl(String boardImgUrl) {
		this.boardImgUrl = boardImgUrl;
	}

	public int getBoardViewCnt() {
		return boardViewCnt;
	}

	public void setBoardViewCnt(int boardViewCnt) {
		this.boardViewCnt = boardViewCnt;
	}

	public Timestamp getBoardRegDate() {
		return boardRegDate;
	}

	public void setBoardRegDate(Timestamp boardRegDate) {
		this.boardRegDate = boardRegDate;
	}

	public Timestamp getBoardUpdateDate() {
		return boardUpdateDate;
	}

	public void setBoardUpdateDate(Timestamp boardUpdateDate) {
		this.boardUpdateDate = boardUpdateDate;
	}

	public boolean isBoardVisibility() {
		return boardVisibility;
	}

	public void setBoardVisibility(boolean boardVisibility) {
		this.boardVisibility = boardVisibility;
	}

	@Override
	public String toString() {
		return "Board [boardId=" + boardId + ", userId=" + userId + ", routineId=" + routineId + ", boardContent="
				+ boardContent + ", boardImgUrl=" + boardImgUrl + ", boardViewCnt=" + boardViewCnt + ", boardRegDate="
				+ boardRegDate + ", boardUpdateDate=" + boardUpdateDate + ", boardVisibility=" + boardVisibility + "]";
	}

}
