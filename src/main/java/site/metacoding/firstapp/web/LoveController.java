package site.metacoding.firstapp.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.firstapp.service.LoveService;
import site.metacoding.firstapp.web.dto.CMRespDto;
import site.metacoding.firstapp.web.dto.response.love.LoveRespDto;
import site.metacoding.firstapp.web.dto.response.love.PostRespDto;
import site.metacoding.firstapp.web.dto.response.user.SessionUserDto;

@RequiredArgsConstructor
@RestController
public class LoveController {
	private final HttpSession session;
	private final LoveService loveService;

	// 게시글 좋아요 응답
	@PostMapping("/love/{postId}") // 좋아요한 게시글
	public @ResponseBody CMRespDto<?> love(@PathVariable Integer postId) {
		SessionUserDto principal = (SessionUserDto) session.getAttribute("principal");

		if (principal == null) {
			return new CMRespDto<>(-1, "로그인을 진행해주세요.", null);
		}
		Integer loveId = loveService.좋아요Id불러오기(principal.getUserId(), postId);

		if (loveId == null) {
			loveService.좋아요(principal.getUserId(), postId);
			loveId = loveService.좋아요Id불러오기(principal.getUserId(), postId);
			LoveRespDto loveRespDto = new LoveRespDto(loveId, principal.getUserId(), postId);
			return new CMRespDto<>(1, "좋아요 성공", loveRespDto);
		}

		LoveRespDto loveRespDto = new LoveRespDto(loveId, principal.getUserId(), postId);
		loveService.좋아요취소(loveId);

		return new CMRespDto<>(1, "좋아요 취소 성공", loveRespDto);
	}

	// 좋아요한 게시글 목록 페이지
	@GetMapping("/love/listForm")
	public @ResponseBody CMRespDto<?> loveListForm() {
		SessionUserDto principal = (SessionUserDto) session.getAttribute("principal");

		if (principal == null) {
			return new CMRespDto<>(-1, "로그인을 진행해주세요.", null);
		}

		List<PostRespDto> postRespDto = loveService.좋아요한게시글목록보기(principal.getUserId());
		return new CMRespDto<>(1, "좋아요 목록 페이지 성공", postRespDto);
	}

}