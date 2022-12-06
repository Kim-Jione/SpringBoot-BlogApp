package site.metacoding.firstapp.web;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.firstapp.service.LoveService;
import site.metacoding.firstapp.web.dto.CMRespDto;
import site.metacoding.firstapp.web.dto.response.love.DeleteRespDto;
import site.metacoding.firstapp.web.dto.response.love.LoveRespDto;
import site.metacoding.firstapp.web.dto.response.user.SessionUserDto;

@RequiredArgsConstructor
@RestController
public class LoveController {
	private final HttpSession session;
	private final LoveService loveService;

	// 게시글 좋아요 응답
	@PostMapping("/love/{postId}")
	public @ResponseBody CMRespDto<?> insertLove(@PathVariable Integer postId) {
		SessionUserDto principal = (SessionUserDto) session.getAttribute("principal");
		if (principal == null) {
			return new CMRespDto<>(-1, "로그인을 진행해주세요.", null);
		}
		LoveRespDto loveRespDto = loveService.좋아요(principal.getUserId(), postId);

		return new CMRespDto<>(1, "좋아요 성공", loveRespDto);
	}
	
	// 게시글 싫어요 응답
	@DeleteMapping("/love/delete/{loveId}")
	public @ResponseBody CMRespDto<?> deleteLove(@PathVariable Integer loveId) {
		SessionUserDto principal = (SessionUserDto) session.getAttribute("principal");
		if (principal == null) {
			return new CMRespDto<>(-1, "로그인을 진행해주세요.", null);
		}
		DeleteRespDto deleteRespDto = loveService.좋아요취소(loveId);

		return new CMRespDto<>(1, "좋아요 취소 성공", deleteRespDto);
	}

}
