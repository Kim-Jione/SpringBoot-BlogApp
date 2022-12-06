package site.metacoding.firstapp.web.dto.response.love;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoveRespDto {
	private Integer loveId;
	private Integer userId;
	private Integer postId;

	public LoveRespDto(Integer loveId, Integer userId, Integer postId) {
		this.loveId = loveId;
		this.userId = userId;
		this.postId = postId;
	}
}
