package site.metacoding.firstapp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.firstapp.domain.user.User;
import site.metacoding.firstapp.domain.user.UserDao;
import site.metacoding.firstapp.web.dto.request.user.JoinReqDto;
import site.metacoding.firstapp.web.dto.response.user.JoinRespDto;
import site.metacoding.firstapp.web.dto.response.user.PostRespDto;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserDao userDao;

	@Transactional
	public JoinRespDto 회원가입(JoinReqDto joinReqDto) {
		userDao.insert(joinReqDto.toUser());
		User userPS = userDao.findByUsername(joinReqDto.getUsername());
		JoinRespDto joinRespDto = new JoinRespDto(userPS);
		return joinRespDto;
	}

	public User 유저네임으로찾기(String username) {
		User userPS = userDao.findByUsername(username);
		return userPS;
	}

	public List<PostRespDto> 내가쓴게시글목록보기(Integer userId) {
		List<PostRespDto> psotRespDto = userDao.findPostList(userId);
		return psotRespDto;
	}

	public List<PostRespDto> 내가방문한게시글목록보기(Integer userId) {
		List<PostRespDto> psotRespDto = userDao.findVisitList(userId);
		return psotRespDto;
	}

	public List<PostRespDto> 좋아요한게시글목록보기(Integer userId) {
		List<PostRespDto> psotRespDto = userDao.findLoveList(userId);
		return psotRespDto;
	}
}
