package site.metacoding.firstapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.firstapp.domain.love.Love;
import site.metacoding.firstapp.domain.love.LoveDao;
import site.metacoding.firstapp.web.dto.response.love.DeleteRespDto;
import site.metacoding.firstapp.web.dto.response.love.LoveRespDto;

@RequiredArgsConstructor
@Service
public class LoveService {

	private final LoveDao loveDao;

	@Transactional
	public LoveRespDto 좋아요(Integer userId, Integer postId) {
		Love love = new Love(userId, postId);
		loveDao.insert(love);
		LoveRespDto loveRespDto = loveDao.loveResult(userId, postId);
		return loveRespDto;
	}

	@Transactional
	public DeleteRespDto 좋아요취소(Integer loveId) {
		DeleteRespDto deleteRespDto = loveDao.deleteResult(loveId);
		loveDao.delete(loveId);
		return deleteRespDto;
	}

}
