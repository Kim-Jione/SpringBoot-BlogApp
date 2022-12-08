package site.metacoding.firstapp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.firstapp.domain.post.PostDao;
import site.metacoding.firstapp.web.dto.request.post.SaveReqDto;
import site.metacoding.firstapp.web.dto.request.post.UpdateReqDto;
import site.metacoding.firstapp.web.dto.response.post.DeleteRespDto;
import site.metacoding.firstapp.web.dto.response.post.DetailRespDto;
import site.metacoding.firstapp.web.dto.response.post.PostRespDto;
import site.metacoding.firstapp.web.dto.response.post.SaveRespDto;
import site.metacoding.firstapp.web.dto.response.post.UpdateRespDto;
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

	@Transactional
	public UpdateRespDto 게시글수정하기(UpdateReqDto updateReqDto, SessionUserDto principal) {
		postDao.update(updateReqDto.toEntity());
		UpdateRespDto updateRespDto = postDao.updateResult(principal.getUserId());
		return updateRespDto;
	}

	@Transactional
	public DeleteRespDto 게시글삭제하기(Integer postId) {
		DeleteRespDto deleteRespDto = postDao.deleteResult(postId);
		postDao.delete(postId);
		return deleteRespDto;
	}

	public DetailRespDto 게시글상세보기(Integer postId) {
		DetailRespDto detailRespDto = postDao.findByDetail(postId);
		return detailRespDto;
	}

	public List<PostRespDto> 내가쓴게시글목록보기(Integer userId) {
		List<PostRespDto> psotRespDto = postDao.findMyPostList(userId);
		return psotRespDto;
	}

}