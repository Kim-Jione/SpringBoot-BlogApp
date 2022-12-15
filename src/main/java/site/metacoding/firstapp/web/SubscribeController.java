package site.metacoding.firstapp.web;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.firstapp.domain.user.User;
import site.metacoding.firstapp.domain.user.UserDao;
import site.metacoding.firstapp.service.SubscribeService;
import site.metacoding.firstapp.web.dto.CMRespDto;
import site.metacoding.firstapp.web.dto.response.subscribe.SubscribeRespDto;
import site.metacoding.firstapp.web.dto.response.user.SessionUserDto;

@RequiredArgsConstructor
@RestController
public class SubscribeController {
	private final SubscribeService subscribeService;
	private final HttpSession session;
	private final UserDao userDao;

	// 구독 응답
	@PostMapping("/s/subscribe/{toUserId}") // 구독한 회원
	public @ResponseBody CMRespDto<?> subscribe(@PathVariable Integer toUserId) {

		SessionUserDto principal = (SessionUserDto) session.getAttribute("principal");

		if (principal == null) {
			return new CMRespDto<>(-1, "로그인을 진행해주세요.", null);
		}

		Integer fromUserId = principal.getUserId();

		if (principal.getUserId() == toUserId) {
			return new CMRespDto<>(-1, "자신은 구독이 안됩니다.", null);
		}

		User userPS = userDao.findByUsersId(toUserId);
		if (userPS == null) {
			return new CMRespDto<>(-1, "해당 유저가 존재하지 않습니다.", null);
		}

		Integer subscribeId = subscribeService.구독Id불러오기(fromUserId, toUserId);

		if (subscribeId == null) {
			subscribeService.구독하기(fromUserId, toUserId);

			subscribeId = subscribeService.구독Id불러오기(fromUserId, toUserId);

			SubscribeRespDto subscribeRespDto = new SubscribeRespDto(subscribeId, fromUserId, toUserId);
			return new CMRespDto<>(1, "구독 성공", subscribeRespDto);
		}
		SubscribeRespDto subscribeRespDto = new SubscribeRespDto(subscribeId, fromUserId, toUserId);
		subscribeService.구독취소(subscribeId);
		return new CMRespDto<>(1, "구독 취소 성공", subscribeRespDto);
	}

}