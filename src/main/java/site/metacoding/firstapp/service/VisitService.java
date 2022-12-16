package site.metacoding.firstapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.metacoding.firstapp.domain.visit.VisitDao;
import site.metacoding.firstapp.web.dto.response.user.MyVisitListDto;

@RequiredArgsConstructor
@Service
public class VisitService {

	private final VisitDao visitDao;

	public Integer 방문한Id불러오기(Integer userId, Integer postId) {
		return visitDao.findByUserIdAndPostId(userId, postId);
	}

	public void 방문기록추가하기(Integer userId, Integer postId) {
		visitDao.save(userId, postId);
	}

	public List<MyVisitListDto> 내가방문한게시글목록보기(Integer userId) {
		List<MyVisitListDto> psotRespDto = visitDao.findVisitList(userId);
		return psotRespDto;
	}

}
