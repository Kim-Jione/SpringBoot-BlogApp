package site.metacoding.firstapp.web.dto.response.love;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteRespDto {
	private Integer loveId;
	private Integer userId;
	private Integer postId;
}
