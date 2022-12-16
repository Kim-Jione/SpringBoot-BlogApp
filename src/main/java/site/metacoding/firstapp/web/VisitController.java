package site.metacoding.firstapp.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.firstapp.service.VisitService;
import site.metacoding.firstapp.web.dto.CMRespDto;
import site.metacoding.firstapp.web.dto.response.user.MyVisitListDto;
import site.metacoding.firstapp.web.dto.response.user.SessionUserDto;

@RequiredArgsConstructor
@RestController
public class VisitController {
	private final HttpSession session;
	private final VisitService visitService;

	// 내가 방문한 게시글 목록 페이지
	@GetMapping("/s/visit/listForm")
	public @ResponseBody CMRespDto<?> visitListForm() {
		SessionUserDto principal = (SessionUserDto) session.getAttribute("principal");

		if (principal == null) {
			return new CMRespDto<>(-1, "로그인을 진행해주세요.", null);
		}

		List<MyVisitListDto> myVisitListDto = visitService.내가방문한게시글목록보기(principal.getUserId());
		return new CMRespDto<>(1, "내가 쓴 게시글 목록 페이지 성공", myVisitListDto);
	}

}