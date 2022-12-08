package site.metacoding.firstapp.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.firstapp.domain.user.User;
import site.metacoding.firstapp.domain.user.UserDao;
import site.metacoding.firstapp.service.UserService;
import site.metacoding.firstapp.web.dto.CMRespDto;
import site.metacoding.firstapp.web.dto.request.user.JoinReqDto;
import site.metacoding.firstapp.web.dto.response.user.InfoRespDto;
import site.metacoding.firstapp.web.dto.response.user.JoinRespDto;

@RequiredArgsConstructor
@RestController
public class UserController {
	private final UserService userService;
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

	// 회원정보 수정페이지
	@GetMapping("/s/user/updateForm/{userId}")
	public CMRespDto<?> updateForm(@PathVariable Integer userId) {
		InfoRespDto infoRespDto = userDao.findByUser(userId);
		return new CMRespDto<>(1, "개인정보수정 페이지 불러오기 성공", infoRespDto);
	}

}