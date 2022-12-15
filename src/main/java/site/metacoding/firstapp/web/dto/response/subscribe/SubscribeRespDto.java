package site.metacoding.firstapp.web.dto.response.subscribe;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscribeRespDto {
	private Integer subscribeId;
	private Integer fromUserId;
	private Integer toUserId;

	public SubscribeRespDto(Integer subscribeId, Integer userId, Integer toUserId) {
		this.subscribeId = subscribeId;
		this.fromUserId = userId;
		this.toUserId = toUserId;
	}
}
