package site.metacoding.firstapp.web.dto.response.user;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.firstapp.web.dto.request.user.LoginReqDto;

@Getter
@Setter
public class LoginRespDto {
	private String username;
	private String password;

	public LoginRespDto(LoginReqDto loginReqDto) {
		this.username = loginReqDto.getUsername();
		this.password = loginReqDto.getPassword();
	}

	public LoginRespDto(SessionUserDto principal) {
		this.username = principal.getUsername();
		this.password = principal.getPassword();
	}
}
