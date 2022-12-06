package site.metacoding.firstapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.firstapp.domain.post.PostDao;
import site.metacoding.firstapp.web.dto.request.post.SaveReqDto;
import site.metacoding.firstapp.web.dto.response.post.SaveRespDto;
import site.metacoding.firstapp.web.dto.response.user.SessionUserDto;

@RequiredArgsConstructor
@Service
public class PostService {
	private final PostDao postDao;

	@Transactional
	public SaveRespDto 게시글등록하기(SaveReqDto saveReqDto, SessionUserDto principal) {
		postDao.insert(saveReqDto.toEntity());
		SaveRespDto saveRespDto = postDao.saveResult(principal.getUserId());
		return saveRespDto;
	}
}
