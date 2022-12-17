package site.metacoding.firstapp.web.dto.response.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InfoRespDto {
	private Integer userId;
	private String nickname;
	private String email;
	private String profileImg;
}
