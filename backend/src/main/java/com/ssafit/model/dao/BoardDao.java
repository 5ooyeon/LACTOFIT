package com.ssafit.model.dao;

import java.util.List;

import com.ssafit.model.dto.Board;

public interface BoardDao {
	void createBoard(Board board);

	Board getBoardById(int boardId);

	List<Board> getBoardByUserId(int userId);

	List<Board> getFollowingBoardByUserId(int userId);

	List<Board> getAllBoards();

	List<Board> getAllBoardsOrderByLikes();

	void updateBoard(Board board);

	void deleteBoard(int boardId);

	List<Board> getAllBoardByUserId(int userId);
}
