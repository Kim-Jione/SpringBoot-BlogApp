package site.metacoding.firstapp.web;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import site.metacoding.firstapp.domain.user.User;
import site.metacoding.firstapp.domain.user.UserDao;
import site.metacoding.firstapp.service.UserService;
import site.metacoding.firstapp.utill.JWTToken.CookieForToken;
import site.metacoding.firstapp.utill.JWTToken.CreateJWTToken;
import site.metacoding.firstapp.utill.SHA256;
import site.metacoding.firstapp.web.dto.CMRespDto;
import site.metacoding.firstapp.web.dto.request.user.JoinReqDto;
import site.metacoding.firstapp.web.dto.request.user.LoginReqDto;
import site.metacoding.firstapp.web.dto.request.user.PasswordUpdateReqDto;
import site.metacoding.firstapp.web.dto.request.user.UpdateReqDto;
import site.metacoding.firstapp.web.dto.response.MailDTO;
import site.metacoding.firstapp.web.dto.response.user.InfoRespDto;
import site.metacoding.firstapp.web.dto.response.user.JoinRespDto;
import site.metacoding.firstapp.web.dto.response.user.LeaveRespDto;
import site.metacoding.firstapp.web.dto.response.user.SessionUserDto;
import site.metacoding.firstapp.web.dto.response.user.UpdateRespDto;

@RequiredArgsConstructor
@RestController
public class UserController {
	private final UserService userService;
	private final HttpSession session;
	private final SHA256 sha256;
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
	public @ResponseBody CMRespDto<?> login(@RequestBody LoginReqDto loginReqDto,
			HttpServletResponse resp) {
		SessionUserDto principal = userService.로그인(loginReqDto);
		if (principal == null) {
			return new CMRespDto<>(-1, "로그인실패", null);
		}
		String token = CreateJWTToken.createToken(principal); // 로그인 될시 토큰생성
		resp.addHeader("Authorization", "Bearer " + token); // 헤더에 토큰 추가
		resp.addCookie(CookieForToken.setCookie(token)); // 쿠키 객체를 웹 브라우저로 보낸다. 쿠키 저장소에 저장하게 함

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
		return new CMRespDto<>(1, "로그아웃 성공", principal);
	}

	// 회원정보 수정페이지
	@GetMapping("/s/user/updateForm")
	public CMRespDto<?> updateForm() {
		SessionUserDto principal = (SessionUserDto) session.getAttribute("principal");
		if (principal == null) {
			return new CMRespDto<>(-1, "로그인을 진행해주세요.", null);
		}
		InfoRespDto infoRespDto = userDao.findByUser(principal.getUserId());
		return new CMRespDto<>(1, "개인정보수정 페이지 불러오기 성공", infoRespDto);
	}

	// 회원정보 수정
	@PutMapping("/s/user/update")
	public CMRespDto<?> update(@RequestPart("file") MultipartFile file,
			@RequestPart("updateReqDto") UpdateReqDto updateReqDto) throws Exception {
		SessionUserDto principal = (SessionUserDto) session.getAttribute("principal");
		if (principal == null) {
			return new CMRespDto<>(-1, "로그인을 진행해주세요.", null);
		}
		User userPS = userDao.findById(updateReqDto.getUserId());
		if (userPS == null) {
			return new CMRespDto<>(-1, "해당 유저가 존재하지 않습니다.", null);
		}
		UpdateRespDto updateRespDto = userService.회원정보수정하기(updateReqDto, principal, file);
		return new CMRespDto<>(1, "게시글수정 성공", updateRespDto);
	}

	// 회원탈퇴
	@DeleteMapping("/s/user/leave/{userId}")
	public @ResponseBody CMRespDto<?> leave(@PathVariable Integer userId) {
		SessionUserDto principal = (SessionUserDto) session.getAttribute("principal");
		if (principal == null) {
			return new CMRespDto<>(-1, "로그인을 진행해주세요.", null);
		}
		User userPS = userDao.findById(userId);
		if (userPS == null) {
			return new CMRespDto<>(-1, "해당 유저가 존재하지 않습니다.", null);
		}

		LeaveRespDto leaveRespDto = userService.회원탈퇴하기(userId);
		return new CMRespDto<>(1, "회원탈퇴 성공", leaveRespDto);
	}

	// 비밀번호 수정
	@PutMapping("/s/user/password/update")
	public @ResponseBody CMRespDto<?> passwordUpdate(@RequestBody PasswordUpdateReqDto passwordUpdateReqDto) {
		SessionUserDto principal = (SessionUserDto) session.getAttribute("principal");
		String encPassword = sha256.encrypt(passwordUpdateReqDto.getPassword());
		passwordUpdateReqDto.setPassword(encPassword);

		if (principal == null) {
			return new CMRespDto<>(-1, "로그인을 진행해주세요.", null);
		}
		User userPS = userDao.findById(principal.getUserId());
		if (userPS == null) {
			return new CMRespDto<>(-1, "해당 유저가 존재하지 않습니다.", null);
		}

		userService.비밀번호수정하기(passwordUpdateReqDto, principal);
		return new CMRespDto<>(1, "비밀번호 수정 성공", null);
	}

	// 이메일 보내기
	@PostMapping("/user/sendEmail")
	public CMRespDto<?> sendEmail(String userEmail) {
		Integer userPS = userDao.findByUserEmail(userEmail);
		if (userPS == null) {
			return new CMRespDto<>(-1, "해당 이메일이 존재하지 않습니다.", null);
		}
		MailDTO mailDto = userService.임시비밀번호만들기(userEmail);
		userService.이메일보내기(mailDto);
		return new CMRespDto<>(1, "이메일 보내기 성공", null);
	}
}