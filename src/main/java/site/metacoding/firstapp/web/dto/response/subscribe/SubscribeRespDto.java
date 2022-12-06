package site.metacoding.firstapp.web.dto.response.subscribe;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscribeRespDto {
	private Integer subscribeId;
	private Integer userId;
	private Integer usersId;

	public SubscribeRespDto(Integer subscribeId, Integer userId, Integer usersId) {
		this.subscribeId = subscribeId;
		this.userId = userId;
		this.usersId = usersId;
	}
}
