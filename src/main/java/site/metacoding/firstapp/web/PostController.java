package site.metacoding.firstapp.web;

import javax.servlet.http.HttpSession;

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
import site.metacoding.firstapp.web.dto.response.post.DeleteRespDto;
import site.metacoding.firstapp.web.dto.response.post.DetailRespDto;
import site.metacoding.firstapp.web.dto.response.post.SaveRespDto;
import site.metacoding.firstapp.web.dto.response.post.UpdateRespDto;
import site.metacoding.firstapp.web.dto.response.user.SessionUserDto;

@RequiredArgsConstructor
@RestController
public class PostController {
	private final HttpSession session;
	private final PostService postService;
	private final PostDao postDao;
	private final VisitService visitService;

	// 게시글등록 페이지
	@GetMapping("/post/writeForm")
	public CMRespDto<?> writeForm() {
		SessionUserDto principal = (SessionUserDto) session.getAttribute("principal");
		if (principal == null) {
			return new CMRespDto<>(-1, "로그인을 진행해주세요.", null);
		}
		return new CMRespDto<>(1, "게시글 등록 페이지 불러오기 성공", null);
	}

	// 게시글 등록 응답
	@PostMapping("/post/write")
	public @ResponseBody CMRespDto<?> write(@RequestBody SaveReqDto saveReqDto) {
		SessionUserDto principal = (SessionUserDto) session.getAttribute("principal");
		if (principal == null) {
			return new CMRespDto<>(-1, "로그인을 진행해주세요.", null);
		}
		SaveRespDto saveRespDto = postService.게시글등록하기(saveReqDto, principal);
		return new CMRespDto<>(1, "게시글등록 성공", saveRespDto);
	}

	// 게시글수정 페이지
	@GetMapping("/post/updateForm/{postId}")
	public CMRespDto<?> updateForm(@PathVariable Integer postId) {
		SessionUserDto principal = (SessionUserDto) session.getAttribute("principal");
		if (principal == null) {
			return new CMRespDto<>(-1, "로그인을 진행해주세요.", null);
		}
		UpdateRespDto updateRespDto = postDao.findByUserIdAndPostId(principal.getUserId(), postId);
		if(updateRespDto==null){
			return new CMRespDto<>(-1, "내가 쓴 글이 아닙니다.", null);
		}
		System.out.println("디버그 "+ updateRespDto.getPostTitle());
		return new CMRespDto<>(1, "게시글 수정 페이지 불러오기 성공", updateRespDto);
	}

	// 게시글 수정 응답
	@PutMapping("/post/update")
	public @ResponseBody CMRespDto<?> update(@RequestBody UpdateReqDto updateReqDto) {
		SessionUserDto principal = (SessionUserDto) session.getAttribute("principal");
		if (principal == null) {
			return new CMRespDto<>(-1, "로그인을 진행해주세요.", null);
		}
		UpdateRespDto updateRespDto = postService.게시글수정하기(updateReqDto, principal);
		return new CMRespDto<>(1, "게시글수정 성공", updateRespDto);
	}

	// 게시글 삭제 응답
	@DeleteMapping("/post/delete/{postId}")
	public @ResponseBody CMRespDto<?> delete(@PathVariable Integer postId) {
		SessionUserDto principal = (SessionUserDto) session.getAttribute("principal");
		if (principal == null) {
			return new CMRespDto<>(-1, "로그인을 진행해주세요.", null);
		}
		DeleteRespDto deleteRespDto = postService.게시글삭제하기(postId);
		return new CMRespDto<>(1, "게시글 삭제 성공", deleteRespDto);
	}

	// 게시글 상세보기 페이지
	@GetMapping("/post/detailForm/{postId}")
	public CMRespDto<?> detailForm(@PathVariable Integer postId) {
		SessionUserDto principal = (SessionUserDto) session.getAttribute("principal");
		if (principal == null) {
			return new CMRespDto<>(-1, "로그인을 진행해주세요.", null);
		}
		Integer visitId = visitService.방문한Id불러오기(principal.getUserId(), postId);
		if (visitId == null) {
			visitService.방문기록추가하기(principal.getUserId(), postId);
			Visit visit = new Visit(visitId,principal.getUserId(), postId);
			return new CMRespDto<>(1, "게시글 상세보기 페이지 불러오기및 방문기록추가 성공", visit);
		}
		DetailRespDto detailRespDto = postService.게시글상세보기(postId);
		return new CMRespDto<>(1, "게시글 상세보기 페이지 불러오기 성공", detailRespDto);
	}
}