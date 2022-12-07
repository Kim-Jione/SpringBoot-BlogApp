package site.metacoding.firstapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.metacoding.firstapp.domain.love.Love;
import site.metacoding.firstapp.domain.love.LoveDao;
import site.metacoding.firstapp.web.dto.response.love.PostRespDto;

@RequiredArgsConstructor
@Service
public class LoveService {

	private final LoveDao loveDao;

	public Integer 좋아요Id불러오기(Integer userId, Integer postId) {
		return loveDao.findByUserIdAndPostId(userId, postId);
	}

	public void 좋아요(Integer userId, Integer postId) {
		Love love = new Love(userId, postId);
		loveDao.insert(love);
	}

	public void 좋아요취소(Integer loveId) {
		loveDao.delete(loveId);
	}

	public List<PostRespDto> 좋아요한게시글목록보기(Integer userId) {
		List<PostRespDto> psotRespDto = loveDao.findLoveList(userId);
		return psotRespDto;
	}

}
