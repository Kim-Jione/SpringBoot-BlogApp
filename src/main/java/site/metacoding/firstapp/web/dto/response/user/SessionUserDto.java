package site.metacoding.firstapp.web.dto.response.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionUserDto {
	private Integer userId;
	private String username;
	private String password;
	private String nickname;
	private String email;
}
