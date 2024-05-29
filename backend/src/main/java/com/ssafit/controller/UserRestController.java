package com.ssafit.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
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
import com.ssafit.model.dto.User;
import com.ssafit.model.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/users")
@Tag(name = "userRestController", description = "사용자CRUD, 스트릭 컬러 및 가격 UPDATE")
public class UserRestController {
	
	private static final String UPLOAD_DIR = "./../frontend/src/assets/uploads/";

	@Autowired
	private UserService userService;

	@PostMapping("/")
	@Operation(summary = "사용자를 등록합니다.")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		userService.createUser(user);
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}

	@GetMapping("/{user_id}")
	@Operation(summary = "유저 정보를 불러옵니다.")
	public ResponseEntity<?> getUserProfile(@PathVariable("user_id") int userId) {
		User user = userService.getUserById(userId);
		if (user != null) {
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/update")
	@Operation(summary = "유저 정보를 수정합니다.")
	public ResponseEntity<?> updateUserProfile(@RequestParam("profileImage") MultipartFile file, @RequestParam("userId") int userId,
	        @RequestParam("nickname") String nickname) {
	    User user = userService.getUserById(userId);

	    /** 프로필 이미지 업데이트 저장 **/
	    System.out.println(nickname);
	    
	    // 파일명 수정
	    
	    if(file != null && !file.isEmpty()) {
		    String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
		    String fileExtension = getFileExtension(originalFileName);
		    String profileName = "profile" + fileExtension;

		    try {
		        // 상대 경로를 절대 경로로 변환
		        Path uploadPath = Paths.get(UPLOAD_DIR).toAbsolutePath().normalize();

		        // 사용자 폴더 경로 설정
		        Path userFolderPath = uploadPath.resolve(Integer.toString(userId));

		        // 사용자 폴더가 존재하지 않으면 생성
		        if (!Files.exists(userFolderPath)) {
		            Files.createDirectories(userFolderPath);
		        }

		        // 파일 저장 경로 설정
		        Path filePath = userFolderPath.resolve(profileName);
		        Files.write(filePath, file.getBytes());
			    
			    // 프론트엔드에서 접근할 수 있는 URL로 설정
			    user.setUserProfileImage("/src/assets/uploads/" + Integer.toString(userId) + "/" + profileName);
			    
		    } catch (IOException e) {
		        e.printStackTrace();
		        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		    }

	    }

	    user.setUserNickname(nickname);
	    userService.updateUser(user);
	    
		Map<String, Object> response = new HashMap<>();
		response.put("userId", user.getUserId());
		response.put("userTag", user.getUserTag());
		response.put("userNickname", user.getUserNickname());
		response.put("userProfileImage", user.getUserProfileImage());
		response.put("userBirth", user.getUserBirth() != null ? user.getUserBirth().toString() : null);
	    
	    return new ResponseEntity<>(response, HttpStatus.OK);

	}

	// 파일 확장자를 가져오는 메서드
	private String getFileExtension(String fileName) {
	    if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
	        return fileName.substring(fileName.lastIndexOf("."));
	    } else {
	        return "";
	    }
	}


	@DeleteMapping("/{user_id}")
	@Operation(summary = "유저 정보를 삭제합니다.")
	public ResponseEntity<?> deleteUser(@PathVariable("user_id") int userId) {
		userService.deleteUser(userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/streakColor/{user_id}")
	@Operation(summary = "유저의 스트릭 색상을 변경합니다.")
	public ResponseEntity<User> updateStreakColor(@PathVariable("user_id") int userId,
			@RequestParam String userStreakColor) {
		userService.updateStreakColor(userId, userStreakColor);
		User updatedUser = userService.getUserById(userId);
		if (updatedUser != null) {
			return new ResponseEntity<>(updatedUser, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/streakprice/{user_id}")
	@Operation(summary = "유저의 스트릭 뽑기 가격을 변경합니다.")
	public ResponseEntity<?> updateStreakPrice(@PathVariable("user_id") int userId,
			@RequestParam int userStreakPrice) {
		try {
			userService.updateStreakPrice(userId, userStreakPrice);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
}
