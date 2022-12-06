package site.metacoding.firstapp.web;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.firstapp.domain.post.PostDao;
import site.metacoding.firstapp.service.PostService;
import site.metacoding.firstapp.web.dto.CMRespDto;
import site.metacoding.firstapp.web.dto.request.post.SaveReqDto;
import site.metacoding.firstapp.web.dto.request.post.UpdateReqDto;
import site.metacoding.firstapp.web.dto.response.post.DeleteRespDto;
import site.metacoding.firstapp.web.dto.response.post.SaveRespDto;
import site.metacoding.firstapp.web.dto.response.post.UpdateRespDto;
import site.metacoding.firstapp.web.dto.response.user.SessionUserDto;

@RequiredArgsConstructor
@RestController
public class PostController {
	private final HttpSession session;
	private final PostService postService;
	private final PostDao postDao;

	// 게시글등록 페이지
	@GetMapping("/post/writeForm")
	public CMRespDto<?> writeForm(Model model) {
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
	@GetMapping("/post/updateForm")
	public CMRespDto<?> updateForm(Model model) {
		SessionUserDto principal = (SessionUserDto) session.getAttribute("principal");
		if (principal == null) {
			return new CMRespDto<>(-1, "로그인을 진행해주세요.", null);
		}
		return new CMRespDto<>(1, "게시글 수정 페이지 불러오기 성공", null);
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

}