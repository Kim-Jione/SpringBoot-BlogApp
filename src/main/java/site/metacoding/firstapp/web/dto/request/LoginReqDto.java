package site.metacoding.firstapp.web.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginReqDto {
	private String username;
	private String password;
}
