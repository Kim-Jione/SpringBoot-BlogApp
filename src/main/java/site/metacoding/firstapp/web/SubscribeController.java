package site.metacoding.firstapp.web;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.firstapp.service.SubscribeService;
import site.metacoding.firstapp.web.dto.CMRespDto;
import site.metacoding.firstapp.web.dto.response.subscribe.SubscribeRespDto;

@RequiredArgsConstructor
@RestController
public class SubscribeController {
	private final SubscribeService subscribeService;
	private final HttpSession session;

	// 구독 응답
	@PostMapping("/s/subscribe/{usersId}/{userId}") // 구독한 회원
	public @ResponseBody CMRespDto<?> subscribe(@PathVariable Integer usersId, @PathVariable Integer userId) {

		if (userId == usersId) {
			return new CMRespDto<>(-1, "자신은 구독이 안됩니다.", null);
		}

		Integer subscribeId = subscribeService.구독Id불러오기(userId, usersId);

		if (subscribeId == null) {
			subscribeService.구독하기(userId, usersId);

			subscribeId = subscribeService.구독Id불러오기(userId, usersId);

			SubscribeRespDto subscribeRespDto = new SubscribeRespDto(subscribeId, userId, usersId);
			return new CMRespDto<>(1, "구독 성공", subscribeRespDto);
		}
		SubscribeRespDto subscribeRespDto = new SubscribeRespDto(subscribeId, userId, usersId);
		subscribeService.구독취소(subscribeId);
		return new CMRespDto<>(1, "구독 취소 성공", subscribeRespDto);
	}
	
}