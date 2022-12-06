package site.metacoding.firstapp.web.dto.response.user;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.firstapp.domain.user.User;

@Getter
@Setter
public class JoinRespDto {
	private String username;
	private String nickname;
	private String password;
	private String email;

	public JoinRespDto(User userPS) {
		this.username = userPS.getUsername();
		this.nickname = userPS.getNickname();
		this.password = userPS.getPassword();
		this.email = userPS.getEmail();
	}
}