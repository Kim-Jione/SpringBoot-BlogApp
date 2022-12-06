package site.metacoding.firstapp.web.dto.response.love;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoveRespDto {
	private Integer loveId;
	private Integer userId;
	private Integer postId;

	public LoveRespDto(Integer userId, Integer postId) {
		this.userId = userId;
		this.postId = postId;
	}

}
