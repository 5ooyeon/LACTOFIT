package com.ssafit.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ssafit.model.dto.User;
import com.ssafit.model.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/oauth2")
@Tag(name = "authRestController", description = "소셜 : 구글 로그인 관리용 컨트롤러")
public class AuthRestController {

	@Value("${google.client.id}")
	private String clientId;

	@Value("${google.client.secret}")
	private String clientSecret;

	@Value("${google.redirect.uri}")
	private String redirectUri;

	@Autowired
	private UserService userService;

	@PostMapping("/callback")
	@Operation(summary = "FrontEnd에서 받은 구글로그인 유저의 정보를 DB와 세션에 저장하고 다시 FrontEnd로 보냅니다.")
	public ResponseEntity<Map<String, Object>> googleCallback(@RequestBody Map<String, String> requestBody,
			HttpSession session) {
		String code = requestBody.get("code");
		String redirectUri = requestBody.get("redirect_uri");
		RestTemplate restTemplate = new RestTemplate();

		// 유저 정보를 얻기 위한 access Token
		String tokenUrl = "https://oauth2.googleapis.com/token";
		Map<String, String> tokenRequest = new HashMap<>();
		tokenRequest.put("code", code);
		tokenRequest.put("client_id", clientId);
		tokenRequest.put("client_secret", clientSecret);
		tokenRequest.put("redirect_uri", redirectUri);
		tokenRequest.put("grant_type", "authorization_code");

		Map<String, String> tokenResponse = restTemplate.postForObject(tokenUrl, tokenRequest, Map.class);
		String accessToken = tokenResponse.get("access_token");

		String userInfoUrl = "https://www.googleapis.com/oauth2/v2/userinfo?access_token=" + accessToken;
		Map<String, String> userInfoResponse = restTemplate.getForObject(userInfoUrl, Map.class);

		// response에서 유저 정보 추출
		String openId = userInfoResponse.get("id");
		String nickname = userInfoResponse.get("name");
		String profileImage = userInfoResponse.get("picture");
		String birth = userInfoResponse.get("birthday");

		// 생년월일 파싱
		Date birthDate = null;
		if (birth != null) {
			birthDate = Date.valueOf(birth);
		}

		// DB에 유저를 추가하거나 수정
		User user = userService.getUserByTag(openId);
		if (user == null) {
			user = new User();
			user.setUserTag(openId);
			user.setUserNickname(nickname);
			user.setUserBirth(birthDate);
			user.setUserProfileImage(profileImage);
			userService.createUser(user);
			user = userService.getUserByUserTag(openId);
		}

		// 유저 정보 세션에 저장
		session.setAttribute("user", user);

		// 프론트로 유저 정보 response
		Map<String, Object> response = new HashMap<>();
		response.put("userId", user.getUserId());
		response.put("userTag", user.getUserTag());
		response.put("userNickname", user.getUserNickname());
		response.put("userProfileImage", user.getUserProfileImage());
		response.put("userBirth", user.getUserBirth() != null ? user.getUserBirth().toString() : null);
		response.put("accessToken", accessToken);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
