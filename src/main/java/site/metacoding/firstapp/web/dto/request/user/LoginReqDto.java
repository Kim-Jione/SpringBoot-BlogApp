package site.metacoding.firstapp.web.dto.request.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginReqDto {
	private String username;
	private String password;
}
