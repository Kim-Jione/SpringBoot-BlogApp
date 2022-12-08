package site.metacoding.firstapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.firstapp.domain.user.User;
import site.metacoding.firstapp.domain.user.UserDao;
import site.metacoding.firstapp.utill.SHA256;
import site.metacoding.firstapp.web.dto.request.user.JoinReqDto;
import site.metacoding.firstapp.web.dto.request.user.LoginReqDto;
import site.metacoding.firstapp.web.dto.response.user.JoinRespDto;
import site.metacoding.firstapp.web.dto.response.user.SessionUserDto;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserDao userDao;
	private final SHA256 sha256;

	@Transactional
	public JoinRespDto 회원가입(JoinReqDto joinReqDto) {
		String encPassword = sha256.encrypt(joinReqDto.getPassword());
		joinReqDto.setPassword(encPassword); // 회원가입으로 받은 비밀번호 암호화
		userDao.insert(joinReqDto.toUser());
		User userPS = userDao.findByUsername(joinReqDto.getUsername());
		JoinRespDto joinRespDto = new JoinRespDto(userPS);
		return joinRespDto;
	}

	public User 유저네임으로찾기(String username) {
		User userPS = userDao.findByUsername(username);
		return userPS;
	}

	@Transactional
	public SessionUserDto 로그인(LoginReqDto loginReqDto) {
		String encPassword = sha256.encrypt(loginReqDto.getPassword());
		User userPS = userDao.findByUsername(loginReqDto.getUsername());
		if (userPS == null) {
			return null;
		}
		if (userPS.getPassword().equals(encPassword)) {
			return new SessionUserDto(userPS);
		}
		throw new RuntimeException("아이디 혹은 패스워드가 잘못 입력되었습니다.");
	}

}
