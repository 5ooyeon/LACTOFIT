package com.ssafit.model.service;

import java.util.List;

import com.ssafit.model.dto.Board;

public interface BoardService {
	public void createBoard(Board board);

	public Board getBoardById(int boardId);

	public List<Board> getAllBoards();

	public List<Board> getAllBoardsOrderByLikes();

	public List<Board> getBoardByUserId(int userId);

	public List<Board> getFollowingBoardByUserId(int userId);

	public void updateBoard(Board board);

	public void deleteBoard(int boardId);

	public List<Board> getAllBoardByUserId(int userId);
}
