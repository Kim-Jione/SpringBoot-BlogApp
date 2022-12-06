package site.metacoding.firstapp.web.dto.response;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.firstapp.web.dto.request.LoginReqDto;

@Getter
@Setter
public class LoginRespDto {
	private String username;
	private String password;

	public LoginRespDto(LoginReqDto loginReqDto) {
		this.username = loginReqDto.getUsername();
		this.password = loginReqDto.getPassword();
	}
}
