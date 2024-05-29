package com.ssafit.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafit.model.dto.Board;
import com.ssafit.model.dto.BoardLikes;
import com.ssafit.model.dto.User;
import com.ssafit.model.service.BoardLikesService;
import com.ssafit.model.service.BoardService;
import com.ssafit.model.service.RoutineService;
import com.ssafit.model.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/boards")
@Tag(name = "boardRestController", description = "게시판(오.운.완) 컨트롤러")
public class BoardRestController {

	private static final String UPLOAD_DIR = "./../frontend/src/assets/uploads/";

	@Autowired
	private BoardService boardService;

	@Autowired
	private BoardLikesService boardLikesService;

	@Autowired
	private UserService userService;

	@Autowired
	private RoutineService routineService;

	@PostMapping("/")
	@Operation(summary = "게시물을 등록합니다.")
	public ResponseEntity<?> createBoard(@RequestParam("file") MultipartFile file, @RequestParam("userId") int userId,
			@RequestParam("routineId") int routineId, @RequestParam("boardContent") String boardContent,
			@RequestParam("boardVisibility") boolean boardVisibility) {
		// 파일명에 UUID 추가
		String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
		String fileExtension = getFileExtension(originalFileName);
		String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

		try {
			// 상대 경로를 절대 경로로 변환
			Path uploadPath = Paths.get(UPLOAD_DIR).toAbsolutePath().normalize();
			Path path = uploadPath.resolve(uniqueFileName);
			Files.createDirectories(path.getParent());
			Files.write(path, file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		Board board = new Board();
		board.setUserId(userId);
		board.setRoutineId(routineId);
		board.setBoardContent(boardContent);

		// 프론트엔드에서 접근할 수 있는 URL로 설정
		board.setBoardImgUrl("src/assets/uploads/" + uniqueFileName);

		board.setBoardViewCnt(0);
		board.setBoardVisibility(boardVisibility);

		// 게시글 등록
		boardService.createBoard(board);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	// 파일 확장자를 가져오는 메서드
	private String getFileExtension(String fileName) {
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
			return fileName.substring(fileName.lastIndexOf("."));
		} else {
			return "";
		}
	}

	@GetMapping("/{board_id}")
	@Operation(summary = "ID로 게시물을 조회합니다.")
	public ResponseEntity<?> getBoardById(@PathVariable("board_id") int boardId) {
		Board board = boardService.getBoardById(boardId);

		if (board != null) {
			Map<String, Object> map = new HashMap<>();
			User writer = userService.getUserById(board.getUserId());

			map.put("userId", writer.getUserId());
			map.put("userTag", writer.getUserTag());
			map.put("userNickname", writer.getUserNickname());
			map.put("board", board);
			map.put("RoutineComponents", routineService.getRoutineComponentsByRoutineId(board.getRoutineId()));

			return new ResponseEntity<>(map, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/")
	@Operation(summary = "모든 게시물을 조회합니다.")
	public ResponseEntity<?> getAllBoards() {
		List<Board> boards = boardService.getAllBoards();
		if (boards.isEmpty() || boards == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		List<Map<String, Object>> list = new ArrayList<>();
		for (Board b : boards) {
			Map<String, Object> map = new HashMap<>();
			User writer = userService.getUserById(b.getUserId());

			map.put("writer", writer);
			map.put("board", b);
			map.put("RoutineComponents", routineService.getRoutineComponentsByRoutineId(b.getRoutineId()));

			list.add(map);
		}

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping("/user/{user_id}")
	@Operation(summary = "UserId로 게시물 조회.")
	public ResponseEntity<?> getBoardByUserId(@PathVariable("user_id") int userId) {
		List<Board> boards = boardService.getBoardByUserId(userId);
		if (boards.isEmpty() || boards == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		List<Map<String, Object>> list = new ArrayList<>();
		for (Board b : boards) {
			Map<String, Object> map = new HashMap<>();
			User writer = userService.getUserById(b.getUserId());
			map.put("writer", writer);
			map.put("board", b);
			map.put("RoutineComponents", routineService.getRoutineComponentsByRoutineId(b.getRoutineId()));

			list.add(map);
		}

		return new ResponseEntity<>(list, HttpStatus.OK);
	}


	@GetMapping("/user/private/{user_id}")
	@Operation(summary = "UserId로 삭제된 게시물 포함 조회.")
	public ResponseEntity<?> getAllBoardByUserId(@PathVariable("user_id") int userId) {
		return new ResponseEntity<>(boardService.getAllBoardByUserId(userId), HttpStatus.OK);
	}
	
	@GetMapping("/following/{user_id}")
	@Operation(summary = "팔로잉 유저 게시물 조회.")
	public ResponseEntity<?> getFollowingBoardByUserId(@PathVariable("user_id") int userId) {
		List<Board> boards = boardService.getFollowingBoardByUserId(userId);
		if (boards.isEmpty() || boards == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		List<Map<String, Object>> list = new ArrayList<>();
		for (Board b : boards) {
			Map<String, Object> map = new HashMap<>();
			User writer = userService.getUserById(b.getUserId());
			map.put("writer", writer);
			map.put("board", b);
			map.put("RoutineComponents", routineService.getRoutineComponentsByRoutineId(b.getRoutineId()));

			list.add(map);
		}

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@PutMapping("/{board_id}")
	@Operation(summary = "게시물을 수정합니다.")
	public ResponseEntity<Board> updateBoard(@PathVariable("board_id") int boardId, @RequestBody Board board) {
		board.setBoardId(boardId);
		boardService.updateBoard(board);
		Board updatedBoard = boardService.getBoardById(boardId);
		if (updatedBoard != null) {
			return new ResponseEntity<>(updatedBoard, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/{board_id}")
	@Operation(summary = "게시물을 삭제합니다.")
	public ResponseEntity<Void> deleteBoard(@PathVariable("board_id") int boardId) {
		boardService.deleteBoard(boardId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("likes")
	@Operation(summary = "좋아요 순으로 모든 게시물을 조회합니다.")
	public ResponseEntity<?> getAllBoardsOrderByLikes() {
		List<Board> boards = boardService.getAllBoardsOrderByLikes();
		if (boards.isEmpty() || boards == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		List<Map<String, Object>> list = new ArrayList<>();
		for (Board b : boards) {
			Map<String, Object> map = new HashMap<>();
			User writer = userService.getUserById(b.getUserId());
			List<Map<String, Object>> likes = boardLikesService.getLikesCountByBoardId(b.getUserId());

			map.put("writer", writer);
			map.put("board", b);
			map.put("RoutineComponents", routineService.getRoutineComponentsByRoutineId(b.getRoutineId()));
			map.put("likes", likes);

			list.add(map);
		}

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
}
