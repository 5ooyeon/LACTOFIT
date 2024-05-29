package com.ssafit.model.service;

import java.util.List;
import java.util.Map;

import com.ssafit.model.dto.BoardLikes;

public interface BoardLikesService {
	boolean toggleLike(BoardLikes boardLikes);

	List<Map<String, Object>> getLikesCountByBoardId(int boardId);
}
