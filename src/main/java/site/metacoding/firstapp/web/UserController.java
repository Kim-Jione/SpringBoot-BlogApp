package site.metacoding.firstapp.web;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.firstapp.domain.user.User;
import site.metacoding.firstapp.domain.user.UserDao;
import site.metacoding.firstapp.service.UserService;
import site.metacoding.firstapp.utill.JWTToken.CookieForToken;
import site.metacoding.firstapp.utill.JWTToken.CreateJWTToken;
import site.metacoding.firstapp.web.dto.CMRespDto;
import site.metacoding.firstapp.web.dto.request.user.JoinReqDto;
import site.metacoding.firstapp.web.dto.request.user.LoginReqDto;
import site.metacoding.firstapp.web.dto.response.user.InfoRespDto;
import site.metacoding.firstapp.web.dto.response.user.JoinRespDto;
import site.metacoding.firstapp.web.dto.response.user.LoginRespDto;
import site.metacoding.firstapp.web.dto.response.user.SessionUserDto;

@RequiredArgsConstructor
@RestController
public class UserController {
	private final UserService userService;
	private final HttpSession session;
	private final UserDao userDao;

	// 회원가입 페이지
	@GetMapping("/user/joinForm")
	public CMRespDto<?> joinForm() {
		return new CMRespDto<>(1, "회원가입 페이지 불러오기 성공", null);
	}

	// 회원가입 응답
	@PostMapping("/user/join")
	public @ResponseBody CMRespDto<?> join(@RequestBody JoinReqDto joinReqDto) {
		User userPS = userService.유저네임으로찾기(joinReqDto.getUsername());
		if (userPS != null) {
			return new CMRespDto<>(-1, "회원가입 실패", null);
		}
		JoinRespDto joinRespDto = userService.회원가입(joinReqDto);
		return new CMRespDto<>(1, "회원가입 성공", joinRespDto);
	}

	// 로그인 페이지
	@GetMapping("/user/loginForm")
	public CMRespDto<?> loginForm() {
		return new CMRespDto<>(1, "로그인 페이지 불러오기 성공", null);
	}

	// 로그인 응답
	@PostMapping("/user/login")
	public @ResponseBody CMRespDto<?> login(@RequestBody LoginReqDto loginReqDto, HttpServletResponse resp) {
		SessionUserDto principal = userService.로그인(loginReqDto);
		if (principal == null) {
			return new CMRespDto<>(-1, "로그인실패", null);
		}
		String token = CreateJWTToken.createToken(principal); // 로그인 될시 토큰생성
		resp.addHeader("Authorization", "Bearer " + token);
		resp.addCookie(CookieForToken.setCookie(token)); // 쿠키 객체를 웹 브라우저로 보낸다.

		session.setAttribute("principal", principal);
		return new CMRespDto<>(1, "로그인성공", principal);
	}

	// 로그아웃
	@GetMapping("/user/logout")
	public CMRespDto<?> logout() {
		SessionUserDto principal = (SessionUserDto) session.getAttribute("principal");
		if (principal == null) {
			return new CMRespDto<>(-1, "로그아웃 실패", null);
		}
		session.removeAttribute("principal");
		LoginRespDto loginRespDto = new LoginRespDto(principal);
		return new CMRespDto<>(1, "로그아웃 성공", loginRespDto);
	}

	// 회원정보 수정페이지
	@GetMapping("/user/updateForm")
	public CMRespDto<?> updateForm() {
		SessionUserDto principal = (SessionUserDto) session.getAttribute("principal");
		if (principal == null) {
			return new CMRespDto<>(-1, "로그인을 진행해주세요.", null);
		}
		InfoRespDto infoRespDto = userDao.findByUser(principal.getUserId());
		return new CMRespDto<>(1, "개인정보수정 페이지 불러오기 성공", infoRespDto);
	}

}