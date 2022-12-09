package site.metacoding.firstapp.web.dto.response.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateRespDto {
	private Integer userId;
	private String nickname;
	private String email;
	private String profileImg;
}
