package site.metacoding.firstapp.web;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.firstapp.service.PostService;
import site.metacoding.firstapp.web.dto.CMRespDto;
import site.metacoding.firstapp.web.dto.request.post.SaveReqDto;
import site.metacoding.firstapp.web.dto.response.post.SaveRespDto;
import site.metacoding.firstapp.web.dto.response.user.SessionUserDto;

@RequiredArgsConstructor
@RestController
public class PostController {
	private final HttpSession session;
	private final PostService postService;

	// 게시글 등록 페이지
	@GetMapping("/post/writeForm")
	public CMRespDto<?> writeForm(Model model) {
		SessionUserDto principal = (SessionUserDto) session.getAttribute("principal");
		if (principal == null) {
			return new CMRespDto<>(1, "로그인을 진행해주세요.", null);
		}
		return new CMRespDto<>(1, "게시글 등록 페이지 불러오기 성공", null);
	}

	// 게시글 등록 응답
	@PostMapping("/post/write")
	public @ResponseBody CMRespDto<?> write(@RequestBody SaveReqDto saveReqDto) {
		SessionUserDto principal = (SessionUserDto) session.getAttribute("principal");
		SaveRespDto saveRespDto = postService.게시글등록하기(saveReqDto, principal);
		return new CMRespDto<>(1, "게시글등록 성공", saveRespDto);
	}
}