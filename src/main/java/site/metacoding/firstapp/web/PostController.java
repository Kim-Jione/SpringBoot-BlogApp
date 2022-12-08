package site.metacoding.firstapp.web;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.firstapp.domain.post.Post;
import site.metacoding.firstapp.domain.post.PostDao;
import site.metacoding.firstapp.domain.visit.Visit;
import site.metacoding.firstapp.service.PostService;
import site.metacoding.firstapp.service.VisitService;
import site.metacoding.firstapp.web.dto.CMRespDto;
import site.metacoding.firstapp.web.dto.request.post.SaveReqDto;
import site.metacoding.firstapp.web.dto.request.post.UpdateReqDto;
import site.metacoding.firstapp.web.dto.response.post.BusinessListDto;
import site.metacoding.firstapp.web.dto.response.post.DailyListDto;
import site.metacoding.firstapp.web.dto.response.post.DeleteRespDto;
import site.metacoding.firstapp.web.dto.response.post.DetailRespDto;
import site.metacoding.firstapp.web.dto.response.post.ListRespDto;
import site.metacoding.firstapp.web.dto.response.post.PostRespDto;
import site.metacoding.firstapp.web.dto.response.post.SaveRespDto;
import site.metacoding.firstapp.web.dto.response.post.UpdateRespDto;

@RequiredArgsConstructor
@RestController
public class PostController {
	private final PostService postService;
	private final PostDao postDao;
	private final VisitService visitService;

	// 게시글등록 페이지
	@GetMapping("/s/post/writeForm/{userId}")
	public CMRespDto<?> writeForm(@PathVariable Integer userId) {
		return new CMRespDto<>(1, "게시글 등록 페이지 불러오기 성공", null);
	}

	// 게시글 등록 응답
	@PostMapping("/s/post/write/{userId}")
	public @ResponseBody CMRespDto<?> write(@RequestBody SaveReqDto saveReqDto, @PathVariable Integer userId) {
		SaveRespDto saveRespDto = postService.게시글등록하기(saveReqDto, userId);
		return new CMRespDto<>(1, "게시글등록 성공", saveRespDto);
	}

	// 게시글수정 페이지
	@GetMapping("/s/post/updateForm/{postId}/{userId}")
	public CMRespDto<?> updateForm(@PathVariable Integer postId, @PathVariable Integer userId) {

		UpdateRespDto updateRespDto = postDao.findByUserIdAndPostId(userId, postId);
		if (updateRespDto == null) {
			return new CMRespDto<>(-1, "내가 쓴 글이 아닙니다.", null);
		}
		return new CMRespDto<>(1, "게시글 수정 페이지 불러오기 성공", updateRespDto);
	}

	// 게시글 수정 응답
	@PutMapping("/s/post/update/{userId}")
	public @ResponseBody CMRespDto<?> update(@RequestBody UpdateReqDto updateReqDto, @PathVariable Integer userId) {

		Post postPS = postDao.findById(updateReqDto.getPostId());
		if (postPS == null) {
			return new CMRespDto<>(-1, "해당 게시글이 존재하지 않습니다.", null);
		}
		if (userId != postPS.getUserId()) {
			return new CMRespDto<>(-1, "본인이 작성한 게시글이 아닙니다.", null);
		}
		UpdateRespDto updateRespDto = postService.게시글수정하기(updateReqDto, userId);
		return new CMRespDto<>(1, "게시글수정 성공", updateRespDto);
	}

	// 게시글 삭제 응답
	@DeleteMapping("/s/post/delete/{postId}/{userId}")
	public @ResponseBody CMRespDto<?> delete(@PathVariable Integer postId, @PathVariable Integer userId) {
		DeleteRespDto deleteRespDto = postService.게시글삭제하기(postId);
		return new CMRespDto<>(1, "게시글 삭제 성공", deleteRespDto);
	}

	// 게시글 상세보기 페이지
	@GetMapping("/s/post/detailForm/{postId}/{userId}")
	public CMRespDto<?> detailForm(@PathVariable Integer postId, @PathVariable Integer userId) {
		Integer visitId = visitService.방문한Id불러오기(userId, postId);
		if (visitId == null) {
			visitService.방문기록추가하기(userId, postId);
			Visit visit = new Visit(visitId, userId, postId);
			return new CMRespDto<>(1, "게시글 상세보기 페이지 불러오기및 방문기록추가 성공", visit);
		}
		DetailRespDto detailRespDto = postService.게시글상세보기(postId);
		return new CMRespDto<>(1, "게시글 상세보기 페이지 불러오기 성공", detailRespDto);
	}

	// 내가 쓴 게시글 목록 페이지
	@GetMapping("/s/post/myListForm/{userId}")
	public @ResponseBody CMRespDto<?> myListForm(@PathVariable Integer userId) {
		List<PostRespDto> postRespDto = postService.내가쓴게시글목록보기(userId);
		return new CMRespDto<>(1, "내가 쓴 게시글 목록 페이지 성공", postRespDto);
	}

	// 일상 목록 페이지
	@GetMapping("/s/post/dailyListForm/{userId}")
	public @ResponseBody CMRespDto<?> dailyListForm(@PathVariable Integer userId) {
		List<DailyListDto> dailyListDto = postDao.findDailyList();
		return new CMRespDto<>(1, "일상 목록 페이지 불러오기 성공", dailyListDto);
	}

	// 비즈니스 목록 페이지
	@GetMapping("/s/post/businessListForm/{userId}")
	public @ResponseBody CMRespDto<?> businessListForm(@PathVariable Integer userId) {
		List<BusinessListDto> businessListDto = postDao.findBusinessList();
		return new CMRespDto<>(1, "비즈니스 목록 페이지 불러오기 성공", businessListDto);
	}

	// 메인 목록 페이지
	@GetMapping("/s/post/listForm/{userId}")
	public @ResponseBody CMRespDto<?> listForm(String keyword, @PathVariable Integer userId) {
		
		if (keyword == null || keyword.isEmpty()) {
			List<ListRespDto> listRespDto = postDao.findPostList(null);
			return new CMRespDto<>(1, "메인 목록 페이지 불러오기 성공", listRespDto);
		}
		List<ListRespDto> listRespDto = postDao.findPostList(keyword);
		return new CMRespDto<>(1, "검색 목록 페이지 불러오기 성공", listRespDto);

	}

}